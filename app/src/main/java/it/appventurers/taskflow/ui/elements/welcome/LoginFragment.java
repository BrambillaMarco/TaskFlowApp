package it.appventurers.taskflow.ui.elements.welcome;

import static it.appventurers.taskflow.util.Constant.EMAIL_ADDRESS;
import static it.appventurers.taskflow.util.Constant.ENCRYPTED_SHARED_PREFERENCES_FILE;
import static it.appventurers.taskflow.util.Constant.PASSWORD;
import static it.appventurers.taskflow.util.Constant.TOKEN;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.data.repository.user.UserRepository;
import it.appventurers.taskflow.databinding.FragmentLoginBinding;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.User;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModel;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModelFactory;
import it.appventurers.taskflow.util.ClassBuilder;
import it.appventurers.taskflow.util.EncryptedSharedPreferencesUtil;

/**
 * Login fragment class
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private NavController navController;

    private EncryptedSharedPreferencesUtil encryptedSharedPreferences;

    private UserViewModel userViewModel;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        encryptedSharedPreferences = new EncryptedSharedPreferencesUtil(requireContext());

        UserRepository userRepository = ClassBuilder.getClassBuilder().getUserRepository(
                requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginButton.setOnClickListener(view1 -> {
            binding.emailTextLayout.setError(null);
            binding.passwordTextLayout.setError(null);

            String email = binding.emailEditText.getText()
                    .toString()
                    .trim();
            String password = binding.passwordEditText.getText()
                    .toString()
                    .trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                binding.loginButton.setVisibility(View.INVISIBLE);
                binding.loginProgress.setVisibility(View.VISIBLE);

                userViewModel.signIn(email, password);
                userViewModel.getUserData().observe(getViewLifecycleOwner(), result -> {
                    if (result.isSuccess()) {
                        User user = ((Result.UserSuccess) result).getUser();
                        saveRemoteDataToLocal(user, email, password);
                        try {
                            String savedEmail = encryptedSharedPreferences.readCredentialInformationEncrypted(
                                    ENCRYPTED_SHARED_PREFERENCES_FILE,
                                    EMAIL_ADDRESS + user.getUId());
                            String savedPassword = encryptedSharedPreferences.readCredentialInformationEncrypted(
                                    ENCRYPTED_SHARED_PREFERENCES_FILE,
                                    PASSWORD + user.getUId());
                            if (savedEmail != null && savedPassword != null) {
                                if (savedEmail.equals(email) && savedPassword.equals(password)) {
                                    navController.navigate(R.id.action_loginFragment_to_mainActivity);
                                    requireActivity().finish();
                                } else {
                                    Snackbar.make(view,
                                            getString(R.string.error_login),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            } else {
                                Snackbar.make(view,
                                        getString(R.string.error_login),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        } catch (GeneralSecurityException | IOException e) {
                            Snackbar.make(view,
                                    getString(R.string.error_reading),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        String error = ((Result.Fail) result).getError();
                        Snackbar.make(view,
                                error,
                                Snackbar.LENGTH_SHORT).show();
                    }

                    binding.loginButton.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.INVISIBLE);
                    binding.emailEditText.setText(null);
                    binding.passwordEditText.setText(null);
                });
            } else {
                binding.emailTextLayout.setError(getString(R.string.error_credentials_empty));
                binding.passwordTextLayout.setError(getString(R.string.error_credentials_empty));
            }
        });

        binding.forgotPasswordButton.setOnClickListener(view1 ->
                navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment));

        binding.registerButton.setOnClickListener(view1 ->
                navController.navigate(R.id.action_loginFragment_to_registerFragment));
    }

    private void saveRemoteDataToLocal(User user, String email, String password) {
        try {
            encryptedSharedPreferences.saveCredentialInformationEncrypted(
                    ENCRYPTED_SHARED_PREFERENCES_FILE,
                    EMAIL_ADDRESS + user.getUId(),
                    email);
            encryptedSharedPreferences.saveCredentialInformationEncrypted(
                    ENCRYPTED_SHARED_PREFERENCES_FILE,
                    PASSWORD + user.getUId(),
                    password);
            encryptedSharedPreferences.saveCredentialInformationEncrypted(
                    ENCRYPTED_SHARED_PREFERENCES_FILE,
                    TOKEN + user.getUId(),
                    user.getUId());
        } catch (GeneralSecurityException | IOException e) {
            Snackbar.make(requireView(),
                    getString(R.string.error_saving),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
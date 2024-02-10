package it.appventurers.taskflow.ui.elements.welcome;

import static it.appventurers.taskflow.util.Constant.EMAIL_ADDRESS;
import static it.appventurers.taskflow.util.Constant.ENCRYPTED_SHARED_PREFERENCES_FILE;
import static it.appventurers.taskflow.util.Constant.PASSWORD;
import static it.appventurers.taskflow.util.Constant.TOKEN;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.FragmentRegisterBinding;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.User;
import it.appventurers.taskflow.ui.viewmodel.UserViewModel;
import it.appventurers.taskflow.util.ClassBuilder;
import it.appventurers.taskflow.util.EncryptedSharedPreferencesUtil;

/**
 * Register fragment class
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private EncryptedSharedPreferencesUtil encryptedSharedPreferences;
    private UserViewModel userViewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = NavHostFragment.findNavController(this);
        encryptedSharedPreferences = new EncryptedSharedPreferencesUtil(requireContext());
        userViewModel = new UserViewModel(
                ClassBuilder.getClassBuilder()
                        .getUserRepository(requireActivity().getApplication()));

        binding.registerButton.setOnClickListener(view1 -> {
            binding.emailTextLayout.setError(null);
            binding.passwordTextLayout.setError(null);
            binding.confirmPasswordTextLayout.setError(null);

            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();
            String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();

            if (areCredentialsOk(email, password, confirmPassword)) {
                binding.registerButton.setVisibility(View.INVISIBLE);
                binding.registerProgress.setVisibility(View.VISIBLE);

                userViewModel.signUp(email, password);
                userViewModel.getUserData().observe(getViewLifecycleOwner(), result -> {
                    if (result.isSuccess()) {
                        User user = ((Result.UserSuccess) result).getUser();
                        saveRemoteDataToLocal(user, email, password);

                        Snackbar.make(
                                view,
                                getString(R.string.account_created),
                                Snackbar.LENGTH_SHORT)
                                .show();
                        navController.navigateUp();
                    } else {
                        String error = ((Result.Fail) result).getError();
                        Snackbar.make(view,
                                error,
                                Snackbar.LENGTH_SHORT).show();

                        binding.registerButton.setVisibility(View.VISIBLE);
                        binding.registerProgress.setVisibility(View.INVISIBLE);
                        binding.emailEditText.setText(null);
                        binding.passwordEditText.setText(null);
                        binding.confirmPasswordEditText.setText(null);
                    }
                });
            }
        });
    }

    private void saveRemoteDataToLocal(User user, String email, String password) {
        try {
            encryptedSharedPreferences.saveCredentialInformationEncrypted(
                    ENCRYPTED_SHARED_PREFERENCES_FILE, EMAIL_ADDRESS + user.getuId(), email);
            encryptedSharedPreferences.saveCredentialInformationEncrypted(
                    ENCRYPTED_SHARED_PREFERENCES_FILE, PASSWORD + user.getuId(), password);
            encryptedSharedPreferences.saveCredentialInformationEncrypted(
                    ENCRYPTED_SHARED_PREFERENCES_FILE, TOKEN + user.getuId(), user.getuId());
            Log.d("ciao", EMAIL_ADDRESS + user.getuId());
        } catch (GeneralSecurityException | IOException e) {
            Snackbar.make(requireView(),
                    getString(R.string.error_saving),
                    Snackbar.LENGTH_SHORT).show();

            binding.registerButton.setVisibility(View.VISIBLE);
            binding.registerProgress.setVisibility(View.INVISIBLE);
            binding.emailEditText.setText(null);
            binding.passwordEditText.setText(null);
            binding.confirmPasswordEditText.setText(null);
        }
    }

    private boolean areCredentialsOk(String email, String password, String confirmPassword) {
        if (!email.isEmpty()) {
            if (!password.isEmpty() && !confirmPassword.isEmpty()) {
                if (password.equals(confirmPassword)) {
                    if (password.length() >= 8) {
                        return true;
                    } else {
                        binding.passwordTextLayout.setError(getString(R.string.error_password_short));
                        return false;
                    }
                } else {
                    binding.confirmPasswordTextLayout.setError(getString(R.string.error_not_equals));
                    return false;
                }
            } else {
                binding.passwordTextLayout.setError(getString(R.string.error_password_empty));
                binding.confirmPasswordTextLayout.setError(getString(R.string.error_password_empty));
                return false;
            }
        } else {
            binding.emailTextLayout.setError(getString(R.string.error_email_empty));
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
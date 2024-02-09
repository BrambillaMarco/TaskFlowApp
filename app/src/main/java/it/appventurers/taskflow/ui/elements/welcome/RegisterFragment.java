package it.appventurers.taskflow.ui.elements.welcome;

import static it.appventurers.taskflow.util.Constant.EMAIL_ADDRESS;
import static it.appventurers.taskflow.util.Constant.ENCRYPTED_SHARED_PREFERENCES_FILE;
import static it.appventurers.taskflow.util.Constant.PASSWORD;
import static it.appventurers.taskflow.util.Constant.TOKEN;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.security.GeneralSecurityException;

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

            if (!email.isEmpty()) {
                if (!password.isEmpty() && !confirmPassword.isEmpty()) {
                    if (password.length() >= 8 && confirmPassword.length() >= 8) {
                        if (password.equals(confirmPassword)) {
                            binding.registerButton.setVisibility(View.INVISIBLE);
                            binding.registerProgress.setVisibility(View.VISIBLE);
                            userViewModel.signUp(email, password);
                            userViewModel.getUserData().observe(getViewLifecycleOwner(), result -> {
                                if (result.isSuccess()) {
                                    User user = ((Result.UserSuccess) result).getUser();
                                    try {
                                        encryptedSharedPreferences.saveCredentialInformationEncrypted(
                                                ENCRYPTED_SHARED_PREFERENCES_FILE, EMAIL_ADDRESS, email);
                                        encryptedSharedPreferences.saveCredentialInformationEncrypted(
                                                ENCRYPTED_SHARED_PREFERENCES_FILE, PASSWORD, password);
                                        encryptedSharedPreferences.saveCredentialInformationEncrypted(
                                                ENCRYPTED_SHARED_PREFERENCES_FILE, TOKEN, user.getuId());
                                        Snackbar.make(view,
                                                "Account registered",
                                                Snackbar.LENGTH_SHORT).show();
                                        navController.navigateUp();
                                    } catch (GeneralSecurityException | IOException e) {
                                        Snackbar.make(view,
                                                "Unable to register the account",
                                                Snackbar.LENGTH_SHORT).show();
                                        binding.registerButton.setVisibility(View.VISIBLE);
                                        binding.registerProgress.setVisibility(View.INVISIBLE);
                                        binding.emailEditText.setText(null);
                                        binding.passwordEditText.setText(null);
                                        binding.confirmPasswordEditText.setText(null);
                                    }
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
                        } else {
                            binding.passwordTextLayout.setError("Password must be equals");
                            binding.confirmPasswordTextLayout.setError("Password must be equals");
                            binding.passwordEditText.setText(null);
                            binding.confirmPasswordEditText.setText(null);
                        }
                    } else {
                        binding.passwordTextLayout.setError("Password must be 8 character long");
                        binding.confirmPasswordTextLayout.setError("Password must be 8 character long");
                        binding.passwordEditText.setText(null);
                        binding.confirmPasswordEditText.setText(null);
                    }
                } else {
                    binding.passwordTextLayout.setError("Password cannot be empty");
                    binding.confirmPasswordTextLayout.setError("Password cannot be empty");
                }
            } else {
                binding.emailTextLayout.setError("Email cannot be empty");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
package it.appventurers.taskflow.ui.elements.main;

import static it.appventurers.taskflow.util.Constant.EMAIL_ADDRESS;
import static it.appventurers.taskflow.util.Constant.ENCRYPTED_SHARED_PREFERENCES_FILE;
import static it.appventurers.taskflow.util.Constant.PASSWORD;
import static it.appventurers.taskflow.util.Constant.TOKEN;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.FragmentAccountBinding;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.User;
import it.appventurers.taskflow.ui.elements.welcome.WelcomeActivity;
import it.appventurers.taskflow.ui.viewmodel.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.UserViewModel;
import it.appventurers.taskflow.util.ClassBuilder;
import it.appventurers.taskflow.util.EncryptedSharedPreferencesUtil;

/**
 * Account fragment class
 */
public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private EncryptedSharedPreferencesUtil encryptedSharedPreferences;
    private UserViewModel userViewModel;
    private DataViewModel dataViewModel;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new UserViewModel(
                ClassBuilder.getClassBuilder().getUserRepository(requireActivity().getApplication()));
        encryptedSharedPreferences = new EncryptedSharedPreferencesUtil(requireContext());
        dataViewModel = new DataViewModel(
                ClassBuilder.getClassBuilder()
                        .getDataRepository(requireActivity().getApplication()));
        try {
            String password = encryptedSharedPreferences.readCredentialInformationEncrypted(ENCRYPTED_SHARED_PREFERENCES_FILE,
                    PASSWORD + userViewModel.getLoggedUser().getuId());
            binding.passwordEditText.setText(password);
        } catch (GeneralSecurityException | IOException e) {
            binding.passwordEditText.setText("");
            binding.passwordTextLayout.setError(getString(R.string.error_retrieve_password));
        }
        binding.emailEditText.setText(userViewModel.getLoggedUser().getEmail());

        binding.logoutButton.setOnClickListener(view1 -> {
            userViewModel.logout();
            userViewModel.getUserData().observe(getViewLifecycleOwner(), result -> {
                if (result.isSuccess()) {
                    Intent intent = new Intent(getContext(), WelcomeActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    String error = ((Result.Fail) result).getError();
                    Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
                }
            });
        });

        binding.deleteButton.setOnClickListener(view1 -> {
            try {
                encryptedSharedPreferences.deleteCredentialInformationEncrypted(ENCRYPTED_SHARED_PREFERENCES_FILE,
                        EMAIL_ADDRESS + userViewModel.getLoggedUser().getuId());
                encryptedSharedPreferences.deleteCredentialInformationEncrypted(ENCRYPTED_SHARED_PREFERENCES_FILE,
                        PASSWORD + userViewModel.getLoggedUser().getuId());
                encryptedSharedPreferences.deleteCredentialInformationEncrypted(ENCRYPTED_SHARED_PREFERENCES_FILE,
                        TOKEN + userViewModel.getLoggedUser().getuId());
            } catch (GeneralSecurityException | IOException e) {
                Snackbar.make(view,
                        getString(R.string.error_deleting),
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
            userViewModel.deleteUser();
            userViewModel.getUserData().observe(getViewLifecycleOwner(), result -> {
                if (result.isSuccess()) {

                    Snackbar.make(
                            view,
                            getString(R.string.account_deleted),
                            Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), WelcomeActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    String error = ((Result.Fail) result).getError();
                    Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
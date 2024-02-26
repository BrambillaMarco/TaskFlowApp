package it.appventurers.taskflow.ui.elements.main;

import static it.appventurers.taskflow.util.Constant.EMAIL_ADDRESS;
import static it.appventurers.taskflow.util.Constant.ENCRYPTED_SHARED_PREFERENCES_FILE;
import static it.appventurers.taskflow.util.Constant.ENGLISH;
import static it.appventurers.taskflow.util.Constant.ITALIANO;
import static it.appventurers.taskflow.util.Constant.LANGUAGE;
import static it.appventurers.taskflow.util.Constant.PASSWORD;
import static it.appventurers.taskflow.util.Constant.THEME_DARK;
import static it.appventurers.taskflow.util.Constant.TOKEN;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.data.repository.data.DataRepository;
import it.appventurers.taskflow.data.repository.user.UserRepository;
import it.appventurers.taskflow.databinding.FragmentAccountBinding;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.ui.elements.welcome.WelcomeActivity;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModelFactory;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModel;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModelFactory;
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

        encryptedSharedPreferences = new EncryptedSharedPreferencesUtil(requireContext());

        UserRepository userRepository = ClassBuilder.getClassBuilder()
                .getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        DataRepository dataRepository = ClassBuilder.getClassBuilder()
                .getDataRepository(requireActivity().getApplication());
        dataViewModel = new ViewModelProvider(
                requireActivity(),
                new DataViewModelFactory(dataRepository)).get(DataViewModel.class);
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

        try {
            String password = encryptedSharedPreferences.readCredentialInformationEncrypted(ENCRYPTED_SHARED_PREFERENCES_FILE,
                    PASSWORD + userViewModel.getLoggedUser().getUId());
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
                        EMAIL_ADDRESS + userViewModel.getLoggedUser().getUId());
                encryptedSharedPreferences.deleteCredentialInformationEncrypted(ENCRYPTED_SHARED_PREFERENCES_FILE,
                        PASSWORD + userViewModel.getLoggedUser().getUId());
                encryptedSharedPreferences.deleteCredentialInformationEncrypted(ENCRYPTED_SHARED_PREFERENCES_FILE,
                        TOKEN + userViewModel.getLoggedUser().getUId());
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
    public void onDestroy () {
        super.onDestroy();
        binding = null;
    }
}
package it.appventurers.taskflow.ui.elements.main;

import static it.appventurers.taskflow.util.Constant.ENCRYPTED_SHARED_PREFERENCES_FILE;
import static it.appventurers.taskflow.util.Constant.ENGLISH;
import static it.appventurers.taskflow.util.Constant.ITALIANO;
import static it.appventurers.taskflow.util.Constant.LANGUAGE;
import static it.appventurers.taskflow.util.Constant.THEME_DARK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.FragmentSettingsBinding;
import it.appventurers.taskflow.util.EncryptedSharedPreferencesUtil;

/**
 * Settings Fragment
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private EncryptedSharedPreferencesUtil encryptedSharedPreferences;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        encryptedSharedPreferences = new EncryptedSharedPreferencesUtil(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.languageButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.english_button) {
                try {
                    encryptedSharedPreferences.updateCredentialInformationEncrypted(
                            ENCRYPTED_SHARED_PREFERENCES_FILE,
                            LANGUAGE,
                            ENGLISH);

                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                } catch (GeneralSecurityException | IOException ignored) {
                    Log.d("ciao", "errore nel update lingua inglese");
                }
            } else if (checkedId == R.id.italian_button) {
                try {
                    encryptedSharedPreferences.updateCredentialInformationEncrypted(ENCRYPTED_SHARED_PREFERENCES_FILE,
                            LANGUAGE,
                            ITALIANO);

                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                } catch (GeneralSecurityException | IOException ignored) {

                }
            }
        });

        binding.themeButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.light_button) {
                try {
                    encryptedSharedPreferences.updateCredentialInformationEncrypted(
                            ENCRYPTED_SHARED_PREFERENCES_FILE,
                            THEME_DARK,
                            "false");

                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                } catch (GeneralSecurityException | IOException ignored) {

                }
            } else if (checkedId == R.id.dark_button) {
                try {
                    encryptedSharedPreferences.updateCredentialInformationEncrypted(
                            ENCRYPTED_SHARED_PREFERENCES_FILE,
                            THEME_DARK,
                            "true");

                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                } catch (GeneralSecurityException | IOException ignored) {

                }
            }
        });
    }
}
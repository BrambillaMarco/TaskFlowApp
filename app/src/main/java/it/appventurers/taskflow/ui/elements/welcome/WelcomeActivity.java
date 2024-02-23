package it.appventurers.taskflow.ui.elements.welcome;

import static it.appventurers.taskflow.util.Constant.ENCRYPTED_SHARED_PREFERENCES_FILE;
import static it.appventurers.taskflow.util.Constant.ENGLISH;
import static it.appventurers.taskflow.util.Constant.ITALIANO;
import static it.appventurers.taskflow.util.Constant.LANGUAGE;
import static it.appventurers.taskflow.util.Constant.PERMISSION_CODE;
import static it.appventurers.taskflow.util.Constant.THEME_DARK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Locale;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.data.repository.user.UserRepository;
import it.appventurers.taskflow.databinding.ActivityWelcomeBinding;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModel;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModelFactory;
import it.appventurers.taskflow.util.ClassBuilder;
import it.appventurers.taskflow.util.EncryptedSharedPreferencesUtil;

public class WelcomeActivity extends AppCompatActivity {

    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Locale locale = LocaleList.getDefault().get(0);

        EncryptedSharedPreferencesUtil encryptedSharedPreferences = new
                EncryptedSharedPreferencesUtil(this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.welcome_fragment_container);
        NavController navController = navHostFragment.getNavController();

        UserRepository userRepository = ClassBuilder.getClassBuilder().getUserRepository(getApplication());
        UserViewModel userViewModel = new ViewModelProvider(
                this,
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        if ((ActivityCompat.checkSelfPermission(
                getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(WelcomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_CODE);
        }



        String language;
        if (locale.getDisplayLanguage().equals(ITALIANO)) {
            language = ITALIANO;
        } else {
            language = ENGLISH;
        }

        int currentNightMode = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean theme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
        try {
            encryptedSharedPreferences.saveCredentialInformationEncrypted(
                    ENCRYPTED_SHARED_PREFERENCES_FILE,
                    LANGUAGE, language);
            encryptedSharedPreferences.saveCredentialInformationEncrypted(
                    ENCRYPTED_SHARED_PREFERENCES_FILE,
                    THEME_DARK, String.valueOf(theme));
        } catch (GeneralSecurityException | IOException ignored) {

        }

        if (userViewModel.getLoggedUser() != null) {
            navController.navigate(R.id.mainActivity);
            finish();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            if ((grantResults.length > 0) &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();

            }
        }
    }
}
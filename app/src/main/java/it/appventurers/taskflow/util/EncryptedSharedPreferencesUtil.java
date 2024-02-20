package it.appventurers.taskflow.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class EncryptedSharedPreferencesUtil {

    private final Context context;

    public EncryptedSharedPreferencesUtil(Context context) {
        this.context = context;
    }

    public void saveCredentialInformationEncrypted(String encryptedSharedPreferencesName,
                                               String key, String value)
            throws GeneralSecurityException, IOException {
        MasterKey mainKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                context, encryptedSharedPreferencesName, mainKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void deleteCredentialInformationEncrypted(String encryptedSharedPreferencesName,
                                                   String key)
            throws GeneralSecurityException, IOException {
        MasterKey mainKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                context, encryptedSharedPreferencesName, mainKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void updateCredentialInformationEncrypted(String encryptedSharedPreferencesName,
                                                     String key, String newValue)
            throws GeneralSecurityException, IOException {
        deleteCredentialInformationEncrypted(encryptedSharedPreferencesName, key);
        saveCredentialInformationEncrypted(encryptedSharedPreferencesName, key, newValue);
    }

    public String readCredentialInformationEncrypted(String encryptedSharedPreferencesName,
                                                String key)
            throws GeneralSecurityException, IOException {
        MasterKey mainKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                context, encryptedSharedPreferencesName, mainKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        return sharedPreferences.getString(key, null);
    }
}

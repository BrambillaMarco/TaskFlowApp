package it.appventurers.taskflow.data.repository.user;


import static it.appventurers.taskflow.util.Constant.ENCRYPTED_SHARED_PREFERENCES_FILE;
import static it.appventurers.taskflow.util.Constant.TOKEN;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import it.appventurers.taskflow.data.source.user.BaseRemoteUserAuth;
import it.appventurers.taskflow.database.AppDatabase;
import it.appventurers.taskflow.database.UserDao;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.User;
import it.appventurers.taskflow.util.EncryptedSharedPreferencesUtil;

public class UserRepository implements IUserCallback{

    private final BaseRemoteUserAuth remoteUserAuth;
    private final MutableLiveData<Result> userData;
    private Context context;
    private UserDao userDao;
    private final AppDatabase appDatabase;




    public UserRepository(Context context, BaseRemoteUserAuth remoteUserAuth) {
        this.context = context.getApplicationContext();
        this.remoteUserAuth = remoteUserAuth;
        this.remoteUserAuth.setBaseRemoteUserAuth(this);
        userData = new MutableLiveData<>();
        this.appDatabase = AppDatabase.getDatabase(context);
        this.userDao = appDatabase.userDao();
    }

    public MutableLiveData<Result> getUserData() {
        return userData;
    }

    public User getLoggedUser() {
            return remoteUserAuth.getLoggedUser();
    }



    public void signUp(String email, String password) {
        remoteUserAuth.signUp(email, password);
    }

    public void signIn(String email, String password) {
        remoteUserAuth.signIn(email, password);
    }

    public void logout() {
        remoteUserAuth.logout();
    }

    public void deleteUser() {
        remoteUserAuth.deleteUser();
    }

    public void updatePassword(String password) {
        remoteUserAuth.updatePassword(password);
    }

    public void updateEmail(String newEmail, String oldEmail, String password) {
        remoteUserAuth.updateEmail(newEmail, oldEmail, password);
    }

    public void retrievePassword(String email) {
        remoteUserAuth.retrievePassword(email);
    }

    @Override
    public void onSuccessAuthUser(User user) {
        Result.UserSuccess result = new Result.UserSuccess(user);
        userData.postValue(result);
    }

    @Override
    public void onFailure(String error) {
        Result.Fail result = new Result.Fail(error);
        userData.postValue(result);
    }

    @Override
    public void onSuccessLogout() {
        Result.UserSuccess result = new Result.UserSuccess(null);
        userData.postValue(result);
    }

    @Override
    public void onSuccessUpdatePassword() {
        Result.UserSuccess result = new Result.UserSuccess(null);
        userData.postValue(result);
    }

    @Override
    public void onSuccessUpdateEmail(String email) {
        Result.UserSuccess result = new Result.UserSuccess(null);
        userData.postValue(result);
    }

    @Override
    public void onSuccessRetrievePassword() {
        Result.UserSuccess result = new Result.UserSuccess(null);
        userData.postValue(result);
    }

    @Override
    public void onSuccessDeleteUser() {
        Result.UserSuccess result = new Result.UserSuccess(null);
        userData.postValue(result);
    }
}

package it.appventurers.taskflow.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import it.appventurers.taskflow.data.source.BaseRemoteUserAuth;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.User;

public class UserRepository implements IUserCallback{

    private final BaseRemoteUserAuth remoteUserAuth;
    private final MutableLiveData<Result> userData;

    public UserRepository(BaseRemoteUserAuth remoteUserAuth) {
        this.remoteUserAuth = remoteUserAuth;
        this.remoteUserAuth.setBaseRemoteUserAuth(this);
        userData = new MutableLiveData<>();
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

    public void signInWithGoogle(String token) {
        remoteUserAuth.signInWithGoogle(token);
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

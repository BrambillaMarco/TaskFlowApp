package it.appventurers.taskflow.data.source;

import it.appventurers.taskflow.data.repository.user.IUserCallback;
import it.appventurers.taskflow.model.User;

public abstract class BaseRemoteUserAuth {

    protected IUserCallback userCallback;

    public void setBaseRemoteUserAuth(IUserCallback userCallback) {
        this.userCallback = userCallback;
    }

    public abstract User getLoggedUser();

    public abstract void signUp(String email, String password);

    public abstract void signIn(String email, String password);

    public abstract void signInWithGoogle(String idToken);

    public abstract void updatePassword(String password);

    public abstract void updateEmail(String newEmail, String oldEmail, String password);

    public abstract void retrievePassword(String email);

    public abstract void logout();

    public abstract void deleteUser();
}

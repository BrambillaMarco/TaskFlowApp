package it.appventurers.taskflow.data.repository;

import it.appventurers.taskflow.model.User;

public interface IUserCallback {

    void onSuccessAuthUser(User user);
    void onFailure(String error);
    void onSuccessLogout();
    void onSuccessUpdatePassword();
    void onSuccessUpdateEmail();
    void onSuccessRetrievePassword();
    void onSuccessDeleteUser();
}

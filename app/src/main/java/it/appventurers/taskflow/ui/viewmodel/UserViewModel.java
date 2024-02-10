package it.appventurers.taskflow.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.appventurers.taskflow.data.repository.UserRepository;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.User;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    private MutableLiveData<Result> userData;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getLoggedUser() {
        return userRepository.getLoggedUser();
    }

    public void signUp(String email, String password) {
        userRepository.signUp(email, password);
        setUserData();
    }

    public void signIn(String email, String password) {
        userRepository.signIn(email, password);
        setUserData();
    }

    public void signInWithGoogle(String token) {
        userRepository.signInWithGoogle(token);
        setUserData();
    }

    public void retrievePassword(String email) {
        userRepository.retrievePassword(email);
        setUserData();
    }

    public void logout() {
        userRepository.logout();
        setUserData();
    }

    public void deleteUser() {
        userRepository.deleteUser();
        setUserData();
    }

    public MutableLiveData<Result> getUserData() {
        return userData;
    }

    private void setUserData() {
        userData = userRepository.getUserData();
    }
}

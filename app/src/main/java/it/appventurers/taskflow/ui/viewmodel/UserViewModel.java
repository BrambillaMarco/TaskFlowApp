package it.appventurers.taskflow.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.appventurers.taskflow.data.repository.UserRepository;
import it.appventurers.taskflow.model.Result;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    private MutableLiveData<Result> userData;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public MutableLiveData<Result> getUserData() {
        return userData;
    }

    public void setUserData() {
        userData = userRepository.getUserData();
    }
}

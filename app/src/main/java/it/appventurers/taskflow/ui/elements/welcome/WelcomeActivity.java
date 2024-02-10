package it.appventurers.taskflow.ui.elements.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.ActivityWelcomeBinding;
import it.appventurers.taskflow.ui.viewmodel.UserViewModel;
import it.appventurers.taskflow.util.ClassBuilder;

public class WelcomeActivity extends AppCompatActivity {

    private ActivityWelcomeBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.welcome_fragment_container);
        NavController navController = navHostFragment.getNavController();
        userViewModel = new UserViewModel(
                ClassBuilder.getClassBuilder().getUserRepository(getApplication()));

        if (userViewModel.getLoggedUser() != null) {
            navController.navigate(R.id.mainActivity);
            finish();
        }
    }
}
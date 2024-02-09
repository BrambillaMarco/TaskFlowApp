package it.appventurers.taskflow.ui.elements.create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.ActivityCreateBinding;

public class CreateActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.create_fragment_container);
        navController = navHostFragment.getNavController();
        String fragmentToLoad = getIntent().getStringExtra(String.valueOf(R.string.fragment_to_load));

        if ("HabitFragment".equals(fragmentToLoad)) {
            navController.navigate(R.id.createHabitFragment);
        } else if ("DailyFragment".equals(fragmentToLoad)) {
            navController.navigate(R.id.createDailyFragment);
        } else if ("ToDoFragment".equals(fragmentToLoad)) {
            navController.navigate(R.id.createToDoFragment);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
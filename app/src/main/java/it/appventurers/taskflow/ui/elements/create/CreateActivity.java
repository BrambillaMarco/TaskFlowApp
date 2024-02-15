package it.appventurers.taskflow.ui.elements.create;

import static it.appventurers.taskflow.util.Constant.LOAD_FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.ActivityCreateBinding;
import it.appventurers.taskflow.ui.elements.main.MainActivity;

public class CreateActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;
    private NavController navController;
    private String fragmentToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.create_fragment_container);
        navController = navHostFragment.getNavController();
        fragmentToLoad = getIntent().getStringExtra(LOAD_FRAGMENT);

        if ("HabitFragment".equals(fragmentToLoad)) {
            navController.popBackStack();
            navController.navigate(R.id.createHabitFragment);
        } else if ("DailyFragment".equals(fragmentToLoad)) {
            navController.popBackStack();
            navController.navigate(R.id.createDailyFragment);
        } else if ("ToDoFragment".equals(fragmentToLoad)) {
            navController.popBackStack();
            navController.navigate(R.id.createToDoFragment);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra(LOAD_FRAGMENT, "HabitFragment");
        startActivity(intent);
        finish();
    }
}
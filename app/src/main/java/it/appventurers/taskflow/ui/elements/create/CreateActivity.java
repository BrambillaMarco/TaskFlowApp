package it.appventurers.taskflow.ui.elements.create;

import static it.appventurers.taskflow.util.Constant.DAILY_FRAGMENT;
import static it.appventurers.taskflow.util.Constant.HABIT;
import static it.appventurers.taskflow.util.Constant.HABIT_FRAGMENT;
import static it.appventurers.taskflow.util.Constant.LOAD_FRAGMENT;
import static it.appventurers.taskflow.util.Constant.TO_DO_FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.ActivityCreateBinding;
import it.appventurers.taskflow.ui.elements.main.MainActivity;

public class CreateActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.create_fragment_container);
        NavController navController = navHostFragment.getNavController();

        String fragmentToLoad = getIntent().getStringExtra(LOAD_FRAGMENT);
        Bundle bundle = getIntent().getBundleExtra(HABIT);

        if (HABIT_FRAGMENT.equals(fragmentToLoad)) {
            navController.popBackStack();
            navController.navigate(R.id.createHabitFragment, bundle);
        } else if (DAILY_FRAGMENT.equals(fragmentToLoad)) {
            navController.popBackStack();
            navController.navigate(R.id.createDailyFragment);
        } else if (TO_DO_FRAGMENT.equals(fragmentToLoad)) {
            navController.popBackStack();
            navController.navigate(R.id.createToDoFragment);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra(LOAD_FRAGMENT, HABIT_FRAGMENT);
        startActivity(intent);
        finish();
    }
}
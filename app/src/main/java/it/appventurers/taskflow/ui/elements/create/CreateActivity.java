package it.appventurers.taskflow.ui.elements.create;

import static it.appventurers.taskflow.util.Constant.HABIT;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.create_fragment_container);
        NavController navController = navHostFragment.getNavController();


        Bundle bundle = getIntent().getBundleExtra(HABIT);
        navController.popBackStack();
        navController.navigate(R.id.createHabitFragment, bundle);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
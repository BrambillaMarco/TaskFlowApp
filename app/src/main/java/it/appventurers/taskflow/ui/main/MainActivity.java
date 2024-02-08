package it.appventurers.taskflow.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.ActivityMainBinding;
import it.appventurers.taskflow.ui.create.CreateActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_fragment_container);
        navController = navHostFragment.getNavController();

        binding.mainBottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.habit_item) {
                navController.navigate(R.id.habitFragment, null,
                        new NavOptions.Builder()
                                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                                .build());
                item.setChecked(true);
                item.setIcon(R.drawable.habit_filled);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.daily_item)
                        .setIcon(R.drawable.daily_outline);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.to_do_item)
                        .setIcon(R.drawable.to_do_outlined);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.account_item)
                        .setIcon(R.drawable.account_outlined);
                binding.createButton.show();
            } else if (item.getItemId() == R.id.daily_item) {
                navController.navigate(R.id.dailyFragment, null,
                        new NavOptions.Builder()
                                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                                .build());
                item.setChecked(true);
                item.setIcon(R.drawable.daily_filled);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.habit_item)
                        .setIcon(R.drawable.habit_outlined);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.to_do_item)
                        .setIcon(R.drawable.to_do_outlined);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.account_item)
                        .setIcon(R.drawable.account_outlined);
                binding.createButton.show();
            } else if (item.getItemId() == R.id.to_do_item) {
                navController.navigate(R.id.toDoFragment, null,
                        new NavOptions.Builder()
                                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                                .build());
                item.setChecked(true);
                item.setIcon(R.drawable.to_do_filled);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.daily_item)
                        .setIcon(R.drawable.daily_outline);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.habit_item)
                        .setIcon(R.drawable.habit_outlined);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.account_item)
                        .setIcon(R.drawable.account_outlined);
                binding.createButton.show();
            } else if (item.getItemId() == R.id.account_item) {
                navController.navigate(R.id.accountFragment, null,
                        new NavOptions.Builder()
                                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                                .build());
                item.setChecked(true);
                item.setIcon(R.drawable.account_filled);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.daily_item)
                        .setIcon(R.drawable.daily_outline);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.to_do_item)
                        .setIcon(R.drawable.to_do_outlined);
                binding.mainBottomNavigationView.getMenu().findItem(R.id.habit_item)
                        .setIcon(R.drawable.habit_outlined);
                binding.createButton.hide();
            }
            return false;
        });

        binding.createButton.setOnClickListener(view1 -> {
            if (binding.mainBottomNavigationView.getSelectedItemId() == R.id.habit_item) {
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                intent.putExtra(String.valueOf(R.string.fragment_to_load), "HabitFragment");
                startActivity(intent);
            } else if (binding.mainBottomNavigationView.getSelectedItemId() == R.id.daily_item) {
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                intent.putExtra(String.valueOf(R.string.fragment_to_load), "DailyFragment");
                startActivity(intent);
            } else if (binding.mainBottomNavigationView.getSelectedItemId() == R.id.to_do_item) {
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                intent.putExtra(String.valueOf(R.string.fragment_to_load), "ToDoFragment");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (binding.mainBottomNavigationView.getSelectedItemId() == R.id.habit_item) {
            finish();
        } else {
            binding.mainBottomNavigationView.setSelectedItemId(R.id.habit_item);
            binding.mainBottomNavigationView.getMenu().findItem(R.id.habit_item)
                    .setIcon(R.drawable.habit_filled);
            binding.mainBottomNavigationView.getMenu().findItem(R.id.daily_item)
                    .setIcon(R.drawable.daily_outline);
            binding.mainBottomNavigationView.getMenu().findItem(R.id.to_do_item)
                    .setIcon(R.drawable.to_do_outlined);
            binding.mainBottomNavigationView.getMenu().findItem(R.id.account_item)
                    .setIcon(R.drawable.account_outlined);
            binding.createButton.show();
            navController.navigate(R.id.habitFragment);
        }
    }
}
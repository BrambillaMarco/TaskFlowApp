package it.appventurers.taskflow.ui.elements.main;

import static it.appventurers.taskflow.util.Constant.DAILY_FRAGMENT;
import static it.appventurers.taskflow.util.Constant.DAY;
import static it.appventurers.taskflow.util.Constant.HABIT_FRAGMENT;
import static it.appventurers.taskflow.util.Constant.LOAD_FRAGMENT;
import static it.appventurers.taskflow.util.Constant.TO_DO_FRAGMENT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.data.repository.data.DataRepository;
import it.appventurers.taskflow.data.repository.user.UserRepository;
import it.appventurers.taskflow.data.repository.weather.WeatherRepository;
import it.appventurers.taskflow.databinding.ActivityMainBinding;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.Weather;
import it.appventurers.taskflow.ui.elements.create.CreateActivity;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModelFactory;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModel;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModelFactory;
import it.appventurers.taskflow.ui.viewmodel.weather.WeatherViewModel;
import it.appventurers.taskflow.ui.viewmodel.weather.WeatherViewModelFactory;
import it.appventurers.taskflow.util.ClassBuilder;
import it.appventurers.taskflow.util.WeatherIconUtil;

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

        WeatherRepository weatherRepository = ClassBuilder.getClassBuilder()
                .getWeatherRepository(getApplication());
        WeatherViewModel weatherViewModel = new ViewModelProvider(
                this,
                new WeatherViewModelFactory(weatherRepository)).get(WeatherViewModel.class);

        UserRepository userRepository = ClassBuilder.getClassBuilder()
                .getUserRepository(getApplication());
        UserViewModel userViewModel = new ViewModelProvider(
                this,
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        DataRepository dataRepository = ClassBuilder.getClassBuilder()
                .getDataRepository(getApplication());
        DataViewModel dataViewModel = new ViewModelProvider(
                this,
                new DataViewModelFactory(dataRepository)).get(DataViewModel.class);

        FusedLocationProviderClient fusedLocationClient = LocationServices
                .getFusedLocationProviderClient(this);

        String fragmentToLoad = getIntent().getStringExtra(LOAD_FRAGMENT);

        if (HABIT_FRAGMENT.equals(fragmentToLoad)) {
            navController.navigate(R.id.habitFragment);
        } else if (DAILY_FRAGMENT.equals(fragmentToLoad)) {
            navController.navigate(R.id.dailyFragment);
        } else if (TO_DO_FRAGMENT.equals(fragmentToLoad)) {
            navController.navigate(R.id.toDoFragment);
        }

        dataViewModel.getUserInfo(userViewModel.getLoggedUser());
        dataViewModel.getUserInfo().observe(this, user -> {
            binding.currentLifeText.setText(String.valueOf(user.getCurrentLife()));
            binding.maxLifeText.setText(String.valueOf(user.getLife()));
            binding.currentLevelText.setText(String.valueOf(user.getLevel()));
            binding.lifeProgress.setProgress(100 * user.getCurrentLife() / user.getLife());
            binding.xpProgress.setProgress(user.getXp());
        });

        if ((ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED)) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String position = latitude + "," + longitude;

                    weatherViewModel.getWeatherInfo(position);
                    weatherViewModel.getWeatherData().observe(this, result -> {
                        if (result.isSuccess()) {
                            Weather weather = ((Result.WeatherSuccess) result).getWeather();

                            binding.cityText.setText(weather.getCity());
                            binding.degreesText.setText(weather.getTemperature());
                            if (weather.getDay().equals(DAY)) {
                                binding.weatherImage.setImageResource(
                                        WeatherIconUtil.changeImageDay(weather.getCode()));
                            } else {
                                binding.weatherImage.setImageResource(
                                        WeatherIconUtil.changeImageNight(weather.getCode()));
                            }
                        } else {
                            String error = ((Result.Fail) result).getError();
                            Snackbar.make(view,
                                    error,
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(e -> {
                Snackbar.make(view,
                        getString(R.string.error_position),
                        Snackbar.LENGTH_SHORT).show();
            });
        }

        binding.mainBottomNavigationView.setOnItemSelectedListener(item -> {
            int selectedItem = item.getItemId();

            if (selectedItem == R.id.habit_item) {
                navController.navigate(R.id.habitFragment, null,
                        new NavOptions.Builder()
                                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                                .build());
                item.setChecked(true);
                item.setIcon(R.drawable.habit_filled);
                updateBottomNavigationIcons(R.id.habit_item);
                binding.createButton.show();
            } else if (selectedItem == R.id.daily_item) {
                navController.navigate(R.id.dailyFragment, null,
                        new NavOptions.Builder()
                                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                                .build());
                item.setChecked(true);
                item.setIcon(R.drawable.daily_filled);
                updateBottomNavigationIcons(R.id.daily_item);
                binding.createButton.show();
            } else if (selectedItem == R.id.to_do_item) {
                navController.navigate(R.id.toDoFragment, null,
                        new NavOptions.Builder()
                                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                                .build());
                item.setChecked(true);
                item.setIcon(R.drawable.to_do_filled);
                updateBottomNavigationIcons(R.id.to_do_item);
                binding.createButton.show();
            } else if (selectedItem == R.id.account_item) {
                navController.navigate(R.id.accountFragment, null,
                        new NavOptions.Builder()
                                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                                .build());
                item.setChecked(true);
                item.setIcon(R.drawable.account_filled);
                updateBottomNavigationIcons(R.id.account_item);
                binding.createButton.hide();
            }
            return false;
        });

        binding.createButton.setOnClickListener(view1 -> {
            if (binding.mainBottomNavigationView.getSelectedItemId() == R.id.habit_item) {
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                intent.putExtra(LOAD_FRAGMENT, HABIT_FRAGMENT);
                startActivity(intent);
            } else if (binding.mainBottomNavigationView.getSelectedItemId() == R.id.daily_item) {
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                intent.putExtra(LOAD_FRAGMENT, DAILY_FRAGMENT);
                startActivity(intent);
            } else if (binding.mainBottomNavigationView.getSelectedItemId() == R.id.to_do_item) {
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                intent.putExtra(LOAD_FRAGMENT, TO_DO_FRAGMENT);
                startActivity(intent);
            }
        });
    }

    private void updateBottomNavigationIcons(int selectedItemId) {
        int[] itemIds = {R.id.habit_item, R.id.daily_item,
                R.id.to_do_item, R.id.account_item};
        int[] icons = {R.drawable.habit_outlined, R.drawable.daily_outline,
                R.drawable.to_do_outlined, R.drawable.account_outlined};

        for (int i = 0; i < itemIds.length; i++) {
            if (itemIds[i] != selectedItemId) {
                binding.mainBottomNavigationView.getMenu()
                        .findItem(itemIds[i])
                        .setIcon(icons[i]);
            }
        }
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
            updateBottomNavigationIcons(R.id.habit_item);
        }
    }
}
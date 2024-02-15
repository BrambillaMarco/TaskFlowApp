package it.appventurers.taskflow.ui.elements.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import it.appventurers.taskflow.databinding.ActivityMainBinding;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.Weather;
import it.appventurers.taskflow.ui.elements.create.CreateActivity;
import it.appventurers.taskflow.ui.viewmodel.WeatherViewModel;
import it.appventurers.taskflow.util.ClassBuilder;
import it.appventurers.taskflow.util.WeatherIconUtil;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1;
    private ActivityMainBinding binding;
    private NavController navController;
    private WeatherViewModel weatherViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_fragment_container);
        navController = navHostFragment.getNavController();
        weatherViewModel = new WeatherViewModel(
                ClassBuilder.getClassBuilder().getWeatherRepository(getApplication()));
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String position = latitude + "," + longitude;
                weatherViewModel.getWeatherInfo(position);
                weatherViewModel.getWeatherData().observe(this, result -> {
                    if (result.isSuccess()) {
                        weather = ((Result.WeatherSuccess) result).getWeather();
                        binding.cityText.setText(weather.getCity());
                        binding.degreesText.setText(weather.getTemperature());
                        if (weather.getDay().equals("1")) {
                            binding.weatherImage.setImageResource(
                                    WeatherIconUtil.changeImageDay(
                                            weather.getCode()));
                        } else {
                            binding.weatherImage.setImageResource(
                                    WeatherIconUtil.changeImageNight(
                                            weather.getCode()));
                        }
                    } else {
                        String error = ((Result.Fail) result).getError();
                        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(e -> {

        });


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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Please provide the permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
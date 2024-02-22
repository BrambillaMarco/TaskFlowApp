package it.appventurers.taskflow.ui.viewmodel.weather;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.appventurers.taskflow.data.repository.weather.WeatherRepository;

public class WeatherViewModelFactory implements ViewModelProvider.Factory{

    private final WeatherRepository weatherRepository;

    public WeatherViewModelFactory(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeatherViewModel(weatherRepository);
    }
}

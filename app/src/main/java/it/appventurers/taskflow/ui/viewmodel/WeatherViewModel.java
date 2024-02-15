package it.appventurers.taskflow.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.appventurers.taskflow.data.repository.weather.WeatherRepository;
import it.appventurers.taskflow.model.Result;

public class WeatherViewModel extends ViewModel {

    private final WeatherRepository weatherRepository;
    private MutableLiveData<Result> weatherData;


    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void getWeatherInfo(String position) {
        weatherRepository.getWeatherInfo(position);
        setWeatherData();
    }

    public MutableLiveData<Result> getWeatherData() {
        return weatherData;
    }

    private void setWeatherData() {
        weatherData = weatherRepository.getWeatherData();
    }
}

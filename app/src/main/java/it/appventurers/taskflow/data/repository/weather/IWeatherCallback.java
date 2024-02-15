package it.appventurers.taskflow.data.repository.weather;

import it.appventurers.taskflow.model.Weather;

public interface IWeatherCallback {

    void onSuccessWeather(Weather body);
    void onFailure(String error);
}

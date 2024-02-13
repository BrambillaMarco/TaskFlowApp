package it.appventurers.taskflow.data.source.weather;

import it.appventurers.taskflow.data.repository.weather.IWeatherCallback;
import it.appventurers.taskflow.data.repository.weather.WeatherRepository;

public abstract class BaseWeatherRemote {

    protected IWeatherCallback weatherCallback;

    public void setBaseWeatherRemote(IWeatherCallback weatherRepository) {
        this.weatherCallback = weatherRepository;
    }

    public abstract void getWeatherInfo(String position);
}

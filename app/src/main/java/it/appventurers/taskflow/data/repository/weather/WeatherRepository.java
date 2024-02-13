package it.appventurers.taskflow.data.repository.weather;

import androidx.lifecycle.MutableLiveData;

import it.appventurers.taskflow.data.source.weather.BaseWeatherRemote;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.Weather;

public class WeatherRepository implements IWeatherCallback{

    private final BaseWeatherRemote weatherRemote;
    private final MutableLiveData<Result> weatherData;

    public WeatherRepository(BaseWeatherRemote weatherRemote) {
        this.weatherRemote = weatherRemote;
        this.weatherRemote.setBaseWeatherRemote(this);
        weatherData = new MutableLiveData<>();
    }

    public MutableLiveData<Result> getWeatherData() {
        return weatherData;
    }

    public void getWeatherInfo(String position) {
        weatherRemote.getWeatherInfo(position);
    }

    @Override
    public void onSuccessWeather(Weather weather) {
        Result.WeatherSuccess result = new Result.WeatherSuccess(weather);
        weatherData.postValue(result);
    }

    @Override
    public void onFailure(String error) {
        Result.Fail result = new Result.Fail(error);
        weatherData.postValue(result);
    }
}

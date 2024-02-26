package it.appventurers.taskflow.data.source.weather;

import android.app.Application;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import it.appventurers.taskflow.model.Weather;
import it.appventurers.taskflow.service.IWeatherApiService;
import it.appventurers.taskflow.util.ClassBuilder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRemote extends BaseWeatherRemote{

    private final IWeatherApiService weatherApiService;
    private final String apiKey;

    public WeatherRemote(String apiKey) {
        this.apiKey = apiKey;
        this.weatherApiService = ClassBuilder.getClassBuilder().getWeatherApiService();
    }

    @Override
    public void getWeatherInfo(String position) {
        Call<ResponseBody> weatherResponse = weatherApiService.getWeatherInfo(apiKey, position, "no");
        weatherResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONObject location = jsonResponse.getJSONObject("location");
                        JSONObject current = jsonResponse.getJSONObject("current");
                        String city = location.getString("name");
                        String temperature = current.getString("temp_c");
                        String code = current.getJSONObject("condition").getString("code");
                        String day = current.getString("is_day");

                        Weather weather = new Weather();
                        weather.setCity(city);
                        weather.setTemperature(temperature);
                        weather.setCode(code);
                        weather.setDay(day);
                        weatherCallback.onSuccessWeather(weather);
                    }
                } catch (IOException | JSONException e) {
                    weatherCallback.onFailure(e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Weather weather = new Weather();
                weather.setCity("Offline");
                weather.setTemperature("15");
                weather.setCode("3000");
                weather.setDay("true");
                weatherCallback.onSuccessWeather(weather);
            }
        });
    }
}

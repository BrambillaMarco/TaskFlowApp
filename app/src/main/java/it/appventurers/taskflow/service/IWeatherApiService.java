package it.appventurers.taskflow.service;

import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Weather;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherApiService {

    @GET("current.json")
    Call<ResponseBody> getWeatherInfo(
        @Query("key") String key,
        @Query("q") String position,
        @Query("aqi") String no);
}

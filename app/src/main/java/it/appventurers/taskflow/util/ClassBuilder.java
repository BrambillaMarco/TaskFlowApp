package it.appventurers.taskflow.util;

import android.app.Application;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.data.repository.data.DataRepository;
import it.appventurers.taskflow.data.repository.user.UserRepository;
import it.appventurers.taskflow.data.repository.weather.WeatherRepository;
import it.appventurers.taskflow.data.source.data.BaseRemoteData;
import it.appventurers.taskflow.data.source.user.BaseRemoteUserAuth;
import it.appventurers.taskflow.data.source.data.RemoteData;
import it.appventurers.taskflow.data.source.user.RemoteUserAuth;
import it.appventurers.taskflow.data.source.weather.BaseWeatherRemote;
import it.appventurers.taskflow.service.IWeatherApiService;
import it.appventurers.taskflow.data.source.weather.WeatherRemote;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassBuilder {

    private static ClassBuilder CLASS_BUILDER = null;

    private ClassBuilder(){}

    public static synchronized ClassBuilder getClassBuilder() {
        if (CLASS_BUILDER == null) {
            CLASS_BUILDER = new ClassBuilder();
        }
        return CLASS_BUILDER;
    }

    public UserRepository getUserRepository(Application application) {
        BaseRemoteUserAuth baseRemoteUserAuth = new RemoteUserAuth();
        return new UserRepository(application.getApplicationContext(), baseRemoteUserAuth);
    }

    public DataRepository getDataRepository(Application application) {
        BaseRemoteData baseRemoteData = new RemoteData();
        return new DataRepository(application.getApplicationContext(), baseRemoteData);
    }

    public WeatherRepository getWeatherRepository(Application application) {
        BaseWeatherRemote baseWeatherRemote = new WeatherRemote(application.getString(R.string.weather_api_key));
        return new WeatherRepository(baseWeatherRemote);
    }

    public IWeatherApiService getWeatherApiService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_WEATHER_SERVER).
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(IWeatherApiService.class);
    }
}

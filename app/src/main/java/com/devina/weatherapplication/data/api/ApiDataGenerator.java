package com.devina.weatherapplication.data.api;

import com.devina.weatherapplication.utils.WeatherUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDataGenerator {

    private String mBaseUrl;

    public ApiDataGenerator(String baseUrl)
    {
        mBaseUrl=baseUrl;
    }

    private Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl(WeatherUtils.WEATHER_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private Retrofit retrofit = builder.build();

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}

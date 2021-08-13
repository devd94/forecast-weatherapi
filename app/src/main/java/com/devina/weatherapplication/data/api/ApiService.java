package com.devina.weatherapplication.data.api;

import com.devina.weatherapplication.data.model.AutocompleteOption;
import com.devina.weatherapplication.data.model.CurrentForecast;
import com.devina.weatherapplication.data.model.DayForecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET(ApiEndpoint.CURRENT_FORECAST_ENDPOINT)
    public Call<CurrentForecast> getCurrentForecast(
            @Query("key") String key,
            @Query("q") String paramQ,
            @Query("aqi") String paramAqi
    );

    @GET(ApiEndpoint.SEARCH_AUTOCOMPLETE_ENDPOINT)
    public Call<List<AutocompleteOption>> getAutocompleteOptions(
            @Query("key") String key,
            @Query("q") String paramQ
    );

    @GET(ApiEndpoint.DAY_FORECAST_ENDPOINT)
    public Call<DayForecast> getNextDaysForecast(
            @Query("key") String key,
            @Query("q") String paramQ,
            @Query("days") String days,
            @Query("aqi") String paramAqi,
            @Query("alerts") String paramAlerts
    );
}

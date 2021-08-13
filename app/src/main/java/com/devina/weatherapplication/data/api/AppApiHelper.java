package com.devina.weatherapplication.data.api;

import com.devina.weatherapplication.data.api.request.CurrentForecastRequest;
import com.devina.weatherapplication.data.api.request.DayForecastRequest;
import com.devina.weatherapplication.data.api.request.SearchAutocompleteRequest;
import com.devina.weatherapplication.data.model.CurrentForecast;
import com.devina.weatherapplication.utils.WeatherUtils;

import java.util.Observable;
import java.util.Observer;

public class AppApiHelper implements ApiHelper {

    private ApiParams mApiParams;
    private ApiDataGenerator mApiDataGenerator;
    private ApiResponse mApiResponse;

    public AppApiHelper()
    {
        mApiDataGenerator=new ApiDataGenerator(WeatherUtils.WEATHER_API_BASE_URL);
        mApiResponse=new ApiResponse();
    }

    @Override
    public ApiDataGenerator getApiDataGenerator() {
        return mApiDataGenerator;
    }

    @Override
    public void setApiParams(String paramQ, String paramDays, String paramAqi, String paramAlerts) {

        mApiParams=new ApiParams();
        mApiParams.setParamQ(paramQ);
        mApiParams.setParamDays(paramDays);
        mApiParams.setParamAqi(paramAqi);
        mApiParams.setParamAlerts(paramAlerts);
    }

    @Override
    public ApiParams getApiParams() {
        return mApiParams;
    }

    @Override
    public ApiResponse getApiResponse() {
        return mApiResponse;
    }

    @Override
    public void doCurrentForecastRequestCall(CurrentForecastRequest request) {

        request.requestCurrentForecast();
    }

    @Override
    public void doSearchAutocompleteRequestCall(SearchAutocompleteRequest request) {

        request.requestSearchAutocompleteInput();
    }

    @Override
    public void doNextDayForecastRequestCall(DayForecastRequest request) {

        request.requestDayForecast();
    }
}

package com.devina.weatherapplication.data.api;

import com.devina.weatherapplication.data.api.request.CurrentForecastRequest;
import com.devina.weatherapplication.data.api.request.DayForecastRequest;
import com.devina.weatherapplication.data.api.request.SearchAutocompleteRequest;

public interface ApiHelper {

    ApiDataGenerator getApiDataGenerator();

    ApiParams getApiParams();
    void setApiParams(String paramQ, String paramDays, String paramAqi, String paramAlerts);

    ApiResponse getApiResponse();

    void doCurrentForecastRequestCall(CurrentForecastRequest request);

    void doSearchAutocompleteRequestCall(SearchAutocompleteRequest request);

    void doNextDayForecastRequestCall(DayForecastRequest request);
}

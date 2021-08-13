package com.devina.weatherapplication.data.api;

import com.devina.weatherapplication.data.api.response.CurrentForecastResponse;
import com.devina.weatherapplication.data.api.response.DayForecastResponse;
import com.devina.weatherapplication.data.api.response.SearchAutocompleteResponse;
import com.devina.weatherapplication.utils.WeatherUtils;

import java.util.Observable;

public class ApiResponse extends Observable {

    private CurrentForecastResponse currentForecastResponse;
    private SearchAutocompleteResponse searchAutocompleteResponse;
    private DayForecastResponse dayForecastResponse;

    public CurrentForecastResponse getCurrentForecastResponse() {
        return currentForecastResponse;
    }

    public void setCurrentForecastResponse(CurrentForecastResponse currentForecastResponse) {
        this.currentForecastResponse = currentForecastResponse;

        setChanged();
        notifyObservers(WeatherUtils.CURRENT_FORECAST_REQUEST);
    }

    public SearchAutocompleteResponse getSearchAutocompleteResponse() {
        return searchAutocompleteResponse;
    }

    public void setSearchAutocompleteResponse(SearchAutocompleteResponse searchAutocompleteResponse) {
        this.searchAutocompleteResponse = searchAutocompleteResponse;

        setChanged();
        notifyObservers(WeatherUtils.SEARCH_AUTOCOMPLETE_REQUEST);
    }

    public DayForecastResponse getDayForecastResponse() {
        return dayForecastResponse;
    }

    public void setDayForecastResponse(DayForecastResponse dayForecastResponse) {
        this.dayForecastResponse = dayForecastResponse;

        setChanged();
        notifyObservers(WeatherUtils.NEXT_FORECAST_REQUEST);
    }
}

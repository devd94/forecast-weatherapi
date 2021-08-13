package com.devina.weatherapplication.data;

import com.devina.weatherapplication.data.api.ApiHelper;
import com.devina.weatherapplication.data.api.response.CurrentForecastResponse;
import com.devina.weatherapplication.data.model.AutocompleteOption;
import com.devina.weatherapplication.data.model.CurrentForecast;
import com.devina.weatherapplication.data.model.DayForecast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public interface DataManager extends ApiHelper, Observer {

    void addObserverClass(Observer o);

    CurrentForecast getCurrentForecastData();

    List<AutocompleteOption> getAutocompleteData();

    DayForecast getNextDayForecastData();

    void requestData(int requestCode);
}

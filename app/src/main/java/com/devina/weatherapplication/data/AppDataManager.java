package com.devina.weatherapplication.data;

import android.util.Log;

import com.devina.weatherapplication.data.api.ApiDataGenerator;
import com.devina.weatherapplication.data.api.ApiHelper;
import com.devina.weatherapplication.data.api.ApiParams;
import com.devina.weatherapplication.data.api.ApiResponse;
import com.devina.weatherapplication.data.api.AppApiHelper;
import com.devina.weatherapplication.data.api.request.CurrentForecastRequest;
import com.devina.weatherapplication.data.api.request.DayForecastRequest;
import com.devina.weatherapplication.data.api.request.SearchAutocompleteRequest;
import com.devina.weatherapplication.data.api.response.CurrentForecastResponse;
import com.devina.weatherapplication.data.api.response.DayForecastResponse;
import com.devina.weatherapplication.data.api.response.SearchAutocompleteResponse;
import com.devina.weatherapplication.data.model.AutocompleteOption;
import com.devina.weatherapplication.data.model.CurrentForecast;
import com.devina.weatherapplication.data.model.DayForecast;
import com.devina.weatherapplication.utils.WeatherUtils;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AppDataManager extends Observable implements DataManager {

    private static DataManager dmInstance;

    private ApiHelper mApiHelper;

    private CurrentForecast currentForecast;
    private List<AutocompleteOption> autocompleteOptionList;
    private DayForecast dayForecast;

    private AppDataManager()
    {
        mApiHelper=new AppApiHelper();

        mApiHelper.getApiResponse().addObserver(this);
    }

    public static DataManager getInstance()
    {
        if(dmInstance == null)
        {
            dmInstance = new AppDataManager();
        }

        return dmInstance;
    }

    @Override
    public void addObserverClass(Observer o) {
        addObserver(o);
    }

    @Override
    public ApiDataGenerator getApiDataGenerator() {
        return mApiHelper.getApiDataGenerator();
    }

    @Override
    public void setApiParams(String paramQ, String paramDays, String paramAqi, String paramAlerts) {

        mApiHelper.setApiParams(paramQ, paramDays, paramAqi, paramAlerts);

    }

    @Override
    public ApiParams getApiParams() {
        return mApiHelper.getApiParams();
    }

    @Override
    public ApiResponse getApiResponse() {
        return mApiHelper.getApiResponse();
    }

    @Override
    public void requestData(int requestCode) {

        switch (requestCode)
        {
            case WeatherUtils.CURRENT_FORECAST_REQUEST:
                doCurrentForecastRequestCall(new CurrentForecastRequest(mApiHelper));
                break;

            case WeatherUtils.SEARCH_AUTOCOMPLETE_REQUEST:
                doSearchAutocompleteRequestCall(new SearchAutocompleteRequest(mApiHelper));
                break;

            case WeatherUtils.NEXT_FORECAST_REQUEST:
                doNextDayForecastRequestCall(new DayForecastRequest(mApiHelper));
        }
    }

    @Override
    public void doCurrentForecastRequestCall(CurrentForecastRequest request) {

        mApiHelper.doCurrentForecastRequestCall(request);
    }

    @Override
    public void doSearchAutocompleteRequestCall(SearchAutocompleteRequest request) {

        mApiHelper.doSearchAutocompleteRequestCall(request);
    }

    @Override
    public void doNextDayForecastRequestCall(DayForecastRequest request) {
        mApiHelper.doNextDayForecastRequestCall(request);
    }

    @Override
    public void update(Observable o, Object arg) {

        switch ((int)arg)
        {
            case WeatherUtils.CURRENT_FORECAST_REQUEST:
                CurrentForecastResponse currentForecastResponse=mApiHelper.getApiResponse().getCurrentForecastResponse();

                if(currentForecastResponse.isSuccess() && currentForecastResponse.getCurrentForecast()!=null)
                {
                    currentForecast=currentForecastResponse.getCurrentForecast();
                }
                else
                {
                    //showError
                    Log.e("weatherApiErr:", "fore "+currentForecastResponse.getFailureMsg());
                }

                setChanged();
                notifyObservers(WeatherUtils.CURRENT_FORECAST_REQUEST);

                break;
            case WeatherUtils.SEARCH_AUTOCOMPLETE_REQUEST:
                SearchAutocompleteResponse autocompleteResponse=mApiHelper.getApiResponse().getSearchAutocompleteResponse();

                if(autocompleteResponse.isSuccess() && autocompleteResponse.getAutocompleteOptionList()!=null
                        && autocompleteResponse.getAutocompleteOptionList().size()>0)
                {
                    autocompleteOptionList=autocompleteResponse.getAutocompleteOptionList();
                }
                else
                {
                    //show error
                    Log.e("weatherApiErr:", "auto "+autocompleteResponse.getFailureMsg());
                }

                setChanged();
                notifyObservers(WeatherUtils.SEARCH_AUTOCOMPLETE_REQUEST);

                break;

            case WeatherUtils.NEXT_FORECAST_REQUEST:
                DayForecastResponse dayForecastResponse=mApiHelper.getApiResponse().getDayForecastResponse();

                if(dayForecastResponse.isSuccess() && dayForecastResponse!=null)
                {
                    dayForecast=dayForecastResponse.getDayForecast();
                }
                else
                {
                    //show error
                    Log.e("weatherApiErr:", "next "+dayForecastResponse.getFailureMsg());
                }

                setChanged();
                notifyObservers(WeatherUtils.NEXT_FORECAST_REQUEST);

                break;
        }
    }

    @Override
    public CurrentForecast getCurrentForecastData() {

        if(currentForecast!=null)
        {
            return currentForecast;
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<AutocompleteOption> getAutocompleteData() {

        if(autocompleteOptionList != null && autocompleteOptionList.size()>0)
        {
            return autocompleteOptionList;
        }
        else
        {
            return null;
        }
    }

    @Override
    public DayForecast getNextDayForecastData() {

        if(dayForecast != null)
        {
            return dayForecast;
        }
        else
        {
            return null;
        }
    }
}

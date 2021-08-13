package com.devina.weatherapplication.ui.presenter;

import android.location.LocationManager;

import com.devina.weatherapplication.data.DataManager;
import com.devina.weatherapplication.data.model.AutocompleteOption;
import com.devina.weatherapplication.data.model.CurrentForecast;
import com.devina.weatherapplication.ui.contract.HomeMainContract;
import com.devina.weatherapplication.utils.WeatherUtils;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class HomePresenter implements HomeMainContract.Presenter, Observer {

    private HomeMainContract.MainView mManinView;
    private HomeMainContract.SearchView mSearchView;
    private DataManager mDataManager;

    public HomePresenter(HomeMainContract.MainView mainView, HomeMainContract.SearchView view, DataManager dataManager)
    {
        mManinView=mainView;
        mSearchView =view;
        mDataManager=dataManager;

        mDataManager.addObserverClass(this);

        mManinView.setPresenter(this);
        mSearchView.setPresenter(this);
    }

    @Override
    public void initForecastRequest(String paramQ) {

        mManinView.showProgress();

//        requestCurrentForecast(paramQ, "yes");
        requestMultipleDaysForecast(paramQ, WeatherUtils.NUMBER_OF_FORECAST_DAYS);
    }

    private void requestCurrentForecast(String paramQ, String aqiFlag)
    {
        mDataManager.setApiParams(paramQ, "", aqiFlag, "");
        mDataManager.requestData(WeatherUtils.CURRENT_FORECAST_REQUEST);
    }

    private void requestMultipleDaysForecast(String paramQ, String paramDays)
    {
        mDataManager.setApiParams(paramQ, paramDays, "no", "no");
        mDataManager.requestData(WeatherUtils.NEXT_FORECAST_REQUEST);
    }

    @Override
    public void onSearchButtonClick() {

        String searchText= mSearchView.getSearchText();

        if(searchText.equalsIgnoreCase(""))
        {
            mManinView.showMessage("Please type a city to search");
        }
        else
        {
            requestMultipleDaysForecast(searchText,WeatherUtils.NUMBER_OF_FORECAST_DAYS);
        }
    }

    @Override
    public void setCurrentLatLon(double latitude, double longitude) {


        initForecastRequest(latitude+","+longitude);
    }

    @Override
    public void onSearchTextChange() {

        String searchText=mSearchView.getSearchText();

        //get autocomplete data
//        mManinView.showProgress();
//        requestSearchAutocomplete(searchText);
    }

    private void requestSearchAutocomplete(String paramQ)
    {
        mDataManager.setApiParams(paramQ, "","", "");
        mDataManager.requestData(WeatherUtils.SEARCH_AUTOCOMPLETE_REQUEST);
    }

    @Override
    public void getSearchAutocompleteOptions(List<AutocompleteOption> autocompleteOptionList) {

        if(autocompleteOptionList!=null && autocompleteOptionList.size()>0)
        {
            String optNameStr="Name= ", optCountryStr="Country= ";

            for(AutocompleteOption option : autocompleteOptionList)
            {
                optNameStr=optNameStr+option.getName()+",\n";
                optCountryStr=optCountryStr+option.getCountry()+",\n";
            }

            mManinView.showMessage(optNameStr);
//            mManinView.showMessage(optCountryStr);
        }
        else
        {
            mManinView.showMessage("No data");
        }
    }

    @Override
    public void destroy() {

        mSearchView =null;
        mDataManager=null;
    }

    @Override
    public void update(Observable o, Object arg) {

        switch ((int)arg)
        {
            case WeatherUtils.NEXT_FORECAST_REQUEST:
                mManinView.hideProgress();
                break;

            case WeatherUtils.SEARCH_AUTOCOMPLETE_REQUEST:
                mManinView.hideProgress();
                getSearchAutocompleteOptions(mDataManager.getAutocompleteData());
                break;


        }
    }
}

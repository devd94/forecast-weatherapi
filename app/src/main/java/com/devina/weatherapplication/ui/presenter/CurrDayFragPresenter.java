package com.devina.weatherapplication.ui.presenter;

import android.content.res.AssetManager;

import com.devina.weatherapplication.WeatherApplication;
import com.devina.weatherapplication.data.DataManager;
import com.devina.weatherapplication.data.model.CurrentForecast;
import com.devina.weatherapplication.data.model.DayForecast;
import com.devina.weatherapplication.ui.contract.CurrDayFragContract;
import com.devina.weatherapplication.utils.CommonMethods;
import com.devina.weatherapplication.utils.WeatherUtils;

import java.util.Observable;
import java.util.Observer;

public class CurrDayFragPresenter implements CurrDayFragContract.Presenter, Observer {

    private CurrDayFragContract.CurrForecastBaseView mCurrForecastView;
    private CurrDayFragContract.TempChangeView mTempChangeView;
    private DataManager mDataManager;

    CurrentForecast currentForecast;

    public CurrDayFragPresenter(CurrDayFragContract.CurrForecastBaseView currForecastView,
                                CurrDayFragContract.TempChangeView tempChangeView, DataManager dataManager)
    {
        mCurrForecastView=currForecastView;
        mTempChangeView=tempChangeView;
        mDataManager=dataManager;

        mDataManager.addObserverClass(this);

        mCurrForecastView.setPresenter(this);
        mTempChangeView.setPresenter(this);
    }

    @Override
    public void getForecastData(CurrentForecast forecast) {

        if(forecast!=null)
        {
            String date=forecast.getLocation().getLocaltime();

            date=formatDateTime(date);

            mCurrForecastView.setForecastData(
                    forecast.getLocation().getName()+", "+forecast.getLocation().getCountry(),
                    date,
                    forecast.getCurrent().getTempCelsius()+"",
                    forecast.getCurrent().getFeelsLikeCelsius()+"",
                    forecast.getCurrent().getWindKph()+"",
                    forecast.getCurrent().getWindDegree()+"",
                    forecast.getCurrent().getWindDir()+"",
                    forecast.getCurrent().getHumidity()+"",
                    forecast.getCurrent().getCloud()+"",
                    forecast.getCurrent().getPrecipMM()+"",
                    forecast.getCurrent().getCondition().getIcon()
                    );
        }

    }

    private String formatDateTime(String dateTime)
    {
        String date=dateTime.substring(0, dateTime.indexOf(' '));
        String time=dateTime.substring(dateTime.indexOf(' ')+1);

        date=CommonMethods.formatDateString(date);
//        time= CommonMethods.formatTimeString(time);

        return date;
    }

    @Override
    public void onTempChangeButtonClick() {

        if(mTempChangeView.isChangeToCelsius())
        {
            //get celsius temperature
            String tempCelsius=currentForecast.getCurrent().getTempCelsius()+"";
            String feelsLikeCelsius=currentForecast.getCurrent().getFeelsLikeCelsius()+"";

            //set celsius temperature
            mTempChangeView.setActualTemperature(true, tempCelsius);
            mTempChangeView.setFeelsLikeTemperature(true, feelsLikeCelsius);
        }
        else
        {
            //get fahrenheit temperature
            String tempFahrenheit=currentForecast.getCurrent().getTempFahrenheit()+"";
            String feelsLikeFahrenheit=currentForecast.getCurrent().getFeelsLikeFahrenheit()+"";

            //set fahrenheit temperature
            mTempChangeView.setActualTemperature(false, tempFahrenheit);
            mTempChangeView.setFeelsLikeTemperature(false, feelsLikeFahrenheit);
        }
    }

    @Override
    public void destroy() {

        currentForecast=null;
        mCurrForecastView=null;
        mTempChangeView=null;
        mDataManager=null;
    }

    @Override
    public void update(Observable o, Object arg) {

        switch ((int)arg)
        {
            case WeatherUtils.CURRENT_FORECAST_REQUEST:

//                getForecastData(mDataManager.getCurrentForecastData());

                break;

            case WeatherUtils.NEXT_FORECAST_REQUEST:

                DayForecast forecast=mDataManager.getNextDayForecastData();

                if(forecast!=null)
                {
                    currentForecast=new CurrentForecast();
                    currentForecast.setLocation(forecast.getLocation());
                    currentForecast.setCurrent(forecast.getCurrent());

                    getForecastData(currentForecast);
                }

                break;

        }
    }

}

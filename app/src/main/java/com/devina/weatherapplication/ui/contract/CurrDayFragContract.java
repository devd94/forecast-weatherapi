package com.devina.weatherapplication.ui.contract;

import com.devina.weatherapplication.base.MvpPresenter;
import com.devina.weatherapplication.base.MvpView;
import com.devina.weatherapplication.data.model.CurrentForecast;

public interface CurrDayFragContract {

    interface CurrForecastBaseView extends MvpView<Presenter>
    {
        void setForecastData(String location, String date, String actualTempCelsius,
                             String feelsLikeCelsiusTemp, String windSpeed, String windDeg,
                             String windDir, String humidity, String cloud, String precip, String icon);


    }

    interface TempChangeView extends MvpView<Presenter>
    {
        boolean isChangeToCelsius();
        void setActualTemperature(boolean isCelsius, String temperature);
        void setFeelsLikeTemperature(boolean isCelsius, String temperature);
    }

    interface Presenter extends MvpPresenter
    {
        void getForecastData(CurrentForecast forecast);

        void onTempChangeButtonClick();

        void destroy();
    }
}

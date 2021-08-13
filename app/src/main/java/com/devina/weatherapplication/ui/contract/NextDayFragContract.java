package com.devina.weatherapplication.ui.contract;

import com.devina.weatherapplication.base.MvpPresenter;
import com.devina.weatherapplication.base.MvpView;
import com.devina.weatherapplication.data.model.DayForecast;

public interface NextDayFragContract {

    interface View extends MvpView<Presenter>
    {
        void setForecastData(DayForecast dayForecast);
        void setLocationText(String location);
    }

    interface Presenter extends MvpPresenter
    {
        void getForecastData(DayForecast dayForecast);
        void destroy();
    }
}

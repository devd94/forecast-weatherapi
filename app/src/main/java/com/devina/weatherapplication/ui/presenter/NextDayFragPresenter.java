package com.devina.weatherapplication.ui.presenter;

import com.devina.weatherapplication.data.DataManager;
import com.devina.weatherapplication.data.model.DayForecast;
import com.devina.weatherapplication.ui.contract.NextDayFragContract;
import com.devina.weatherapplication.utils.WeatherUtils;

import java.util.Observable;
import java.util.Observer;

public class NextDayFragPresenter implements NextDayFragContract.Presenter, Observer {

    private NextDayFragContract.View nextDayFragView;
    private DataManager dataManager;

    public NextDayFragPresenter(NextDayFragContract.View nextDayFragView, DataManager dataManager)
    {
        this.nextDayFragView=nextDayFragView;
        this.dataManager=dataManager;

        dataManager.addObserverClass(this);

        nextDayFragView.setPresenter(this);
    }

    @Override
    public void getForecastData(DayForecast dayForecast) {

        if(dayForecast != null)
        {
            nextDayFragView.setForecastData(dayForecast);
            nextDayFragView.setLocationText(dayForecast.getLocation().getName()
                    + ", " +dayForecast.getLocation().getCountry());
        }

    }

    @Override
    public void destroy() {

        nextDayFragView=null;
        dataManager=null;
    }

    @Override
    public void update(Observable o, Object arg) {

        switch ((int)arg)
        {
            case WeatherUtils.NEXT_FORECAST_REQUEST:
                getForecastData(dataManager.getNextDayForecastData());

                break;
        }
    }
}

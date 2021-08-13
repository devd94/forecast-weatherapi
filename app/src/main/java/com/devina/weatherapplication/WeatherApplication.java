package com.devina.weatherapplication;

import android.app.Application;
import android.content.Context;

import com.devina.weatherapplication.data.AppDataManager;
import com.devina.weatherapplication.data.DataManager;

public class WeatherApplication extends Application {

    private DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mDataManager= AppDataManager.getInstance();
    }

    public static WeatherApplication get(Context context)
    {
        return (WeatherApplication) context.getApplicationContext();
    }

    public DataManager getDataManager()
    {
        return mDataManager;
    }

}

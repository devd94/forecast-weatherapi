package com.devina.weatherapplication.ui.presenter;

import android.os.Handler;

import com.devina.weatherapplication.ui.contract.SplashContract;
import com.devina.weatherapplication.utils.WeatherUtils;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View mView;

    public SplashPresenter(SplashContract.View view)
    {
        mView=view;

        mView.setPresenter(this);
    }

    @Override
    public void initApp() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mView.nextActivity();
            }
        }, WeatherUtils.SPLASH_TIME_OUT);
    }

    @Override
    public void destroy() {

        mView=null;
    }
}

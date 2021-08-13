package com.devina.weatherapplication.ui.contract;

import com.devina.weatherapplication.base.MvpPresenter;
import com.devina.weatherapplication.base.MvpView;

public interface SplashContract {

    interface View extends MvpView<Presenter>
    {
        void nextActivity();
    }

    interface Presenter extends MvpPresenter
    {
        void initApp();

        void destroy();
    }
}

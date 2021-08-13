package com.devina.weatherapplication.base;

public interface MvpView<P extends MvpPresenter> {

    void setPresenter(P presenter);
}

package com.devina.weatherapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.devina.weatherapplication.R;
import com.devina.weatherapplication.ui.contract.SplashContract;
import com.devina.weatherapplication.ui.presenter.SplashPresenter;

public class SplashActivity extends AppCompatActivity implements SplashContract.View{

    SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialise();
    }

    private void initialise()
    {
        new SplashPresenter(this);

        mPresenter.initApp();
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {

        this.mPresenter=presenter;
    }

    @Override
    public void nextActivity() {

        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {

        mPresenter.destroy();

        super.onDestroy();
    }
}
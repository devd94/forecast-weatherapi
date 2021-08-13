package com.devina.weatherapplication.data.api.request;

import android.util.Log;

import com.devina.weatherapplication.data.api.ApiHelper;
import com.devina.weatherapplication.data.api.ApiService;
import com.devina.weatherapplication.data.api.response.CurrentForecastResponse;
import com.devina.weatherapplication.data.model.CurrentForecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentForecastRequest {

    private ApiHelper mApiHelper;

    public CurrentForecastRequest(ApiHelper appApiHelper)
    {
        mApiHelper =appApiHelper;
    }

    public void requestCurrentForecast()
    {
        ApiService service= mApiHelper.getApiDataGenerator().createService(ApiService.class);

        Call<CurrentForecast> asyncCall =service.getCurrentForecast(mApiHelper.getApiParams().getApiKey(),
                mApiHelper.getApiParams().getParamQ(), mApiHelper.getApiParams().getParamAqi());

        asyncCall.enqueue(new Callback<CurrentForecast>() {
            @Override
            public void onResponse(Call<CurrentForecast> call, Response<CurrentForecast> response) {

                if(response.isSuccessful())
                {
                    CurrentForecast currentForecast= response.body();

                    CurrentForecastResponse forecastResponse=new CurrentForecastResponse();

                    forecastResponse.setSuccess(true);
                    forecastResponse.setCurrentForecast(currentForecast);
                    forecastResponse.setFailureMsg("null");

                    mApiHelper.getApiResponse().setCurrentForecastResponse(forecastResponse);
                }
                else
                {
                    CurrentForecastResponse forecastResponse=new CurrentForecastResponse();

                    forecastResponse.setSuccess(false);
                    forecastResponse.setCurrentForecast(null);
                    forecastResponse.setFailureMsg(response.message());

                    mApiHelper.getApiResponse().setCurrentForecastResponse(forecastResponse);
                }
            }

            @Override
            public void onFailure(Call<CurrentForecast> call, Throwable t) {

                CurrentForecastResponse forecastResponse=new CurrentForecastResponse();

                forecastResponse.setSuccess(false);
                forecastResponse.setCurrentForecast(null);
                forecastResponse.setFailureMsg(t.getMessage());

                mApiHelper.getApiResponse().setCurrentForecastResponse(forecastResponse);
            }
        });
    }
}

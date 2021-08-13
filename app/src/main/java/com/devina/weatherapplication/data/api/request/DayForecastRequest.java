package com.devina.weatherapplication.data.api.request;

import android.util.Log;

import com.devina.weatherapplication.data.api.ApiHelper;
import com.devina.weatherapplication.data.api.ApiService;
import com.devina.weatherapplication.data.api.response.DayForecastResponse;
import com.devina.weatherapplication.data.model.DayForecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayForecastRequest {

    private ApiHelper mApiHelper;

    public DayForecastRequest(ApiHelper apiHelper)
    {
        mApiHelper=apiHelper;
    }

    public void requestDayForecast()
    {
        ApiService service= mApiHelper.getApiDataGenerator().createService(ApiService.class);

        Call<DayForecast> asyncCall = service.getNextDaysForecast(mApiHelper.getApiParams().getApiKey(),
                mApiHelper.getApiParams().getParamQ(), mApiHelper.getApiParams().getParamDays(),
                mApiHelper.getApiParams().getParamAqi(), mApiHelper.getApiParams().getParamAlerts());

        Log.e("weatherApiErr:", "next "+asyncCall.request().url().toString());

        asyncCall.enqueue(new Callback<DayForecast>() {
            @Override
            public void onResponse(Call<DayForecast> call, Response<DayForecast> response) {

                Log.e("weatherApiErr:", "next "+response.code());

                if(response.isSuccessful())
                {
                    DayForecast dayForecast=response.body();

                    DayForecastResponse dayForecastResponse=new DayForecastResponse();
                    dayForecastResponse.setSuccess(true);
                    dayForecastResponse.setDayForecast(dayForecast);
                    dayForecastResponse.setFailureMsg("null");

                    mApiHelper.getApiResponse().setDayForecastResponse(dayForecastResponse);
                }
                else
                {
                    DayForecastResponse dayForecastResponse=new DayForecastResponse();
                    dayForecastResponse.setSuccess(false);
                    dayForecastResponse.setDayForecast(null);
                    dayForecastResponse.setFailureMsg(response.message());

                    mApiHelper.getApiResponse().setDayForecastResponse(dayForecastResponse);
                }
            }

            @Override
            public void onFailure(Call<DayForecast> call, Throwable t) {

                DayForecastResponse dayForecastResponse=new DayForecastResponse();
                dayForecastResponse.setSuccess(false);
                dayForecastResponse.setDayForecast(null);
                dayForecastResponse.setFailureMsg(t.getMessage());

                mApiHelper.getApiResponse().setDayForecastResponse(dayForecastResponse);

            }
        });

    }
}

package com.devina.weatherapplication.data.api.response;

import com.devina.weatherapplication.data.model.DayForecast;

public class DayForecastResponse {

    private boolean isSuccess;
    private DayForecast dayForecast;
    private String failureMsg;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public DayForecast getDayForecast() {
        return dayForecast;
    }

    public void setDayForecast(DayForecast dayForecast) {
        this.dayForecast = dayForecast;
    }

    public String getFailureMsg() {
        return failureMsg;
    }

    public void setFailureMsg(String failureMsg) {
        this.failureMsg = failureMsg;
    }
}

package com.devina.weatherapplication.data.api.response;

import com.devina.weatherapplication.data.model.CurrentForecast;

public class CurrentForecastResponse {

    private boolean isSuccess;
    private CurrentForecast currentForecast;
    private String failureMsg;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public CurrentForecast getCurrentForecast() {
        return currentForecast;
    }

    public void setCurrentForecast(CurrentForecast currentForecast) {
        this.currentForecast = currentForecast;
    }

    public String getFailureMsg() {
        return failureMsg;
    }

    public void setFailureMsg(String failureMsg) {
        this.failureMsg = failureMsg;
    }
}

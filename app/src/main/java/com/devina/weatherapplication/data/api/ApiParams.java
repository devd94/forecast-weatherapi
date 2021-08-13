package com.devina.weatherapplication.data.api;

import com.devina.weatherapplication.utils.WeatherUtils;

public class ApiParams {

    private String apiKey;
    private String paramQ;
    private String paramAqi;
    private String paramAlerts;
    private String paramDays;

    public ApiParams()
    {
        apiKey= WeatherUtils.WEATHER_API_KEY;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getParamQ() {
        return paramQ;
    }

    public void setParamQ(String paramQ) {
        this.paramQ = paramQ;
    }

    public String getParamAqi() {
        return paramAqi;
    }

    public void setParamAqi(String paramAqi) {
        this.paramAqi = paramAqi;
    }

    public String getParamAlerts() {
        return paramAlerts;
    }

    public void setParamAlerts(String paramAlerts) {
        this.paramAlerts = paramAlerts;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getParamDays() {
        return paramDays;
    }

    public void setParamDays(String paramDays) {
        this.paramDays = paramDays;
    }
}

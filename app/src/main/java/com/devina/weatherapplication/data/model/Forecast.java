package com.devina.weatherapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    @Expose
    @SerializedName("forecastday")
    private List<ForecastObj> forecastObjList;

    public List<ForecastObj> getForecastObjList() {
        return forecastObjList;
    }

    public void setForecastObjList(List<ForecastObj> forecastObjList) {
        this.forecastObjList = forecastObjList;
    }
}

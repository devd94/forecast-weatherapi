package com.devina.weatherapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastObj {

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("day")
    private Day day;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}

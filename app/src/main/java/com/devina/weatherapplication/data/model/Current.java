package com.devina.weatherapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current {

    @Expose
    @SerializedName("temp_c")
    private double tempCelsius;

    @Expose
    @SerializedName("temp_f")
    private double tempFahrenheit;

    @Expose
    @SerializedName("is_day")
    private int isDay;

    @Expose
    @SerializedName("condition")
    private Condition condition;

    @Expose
    @SerializedName("wind_mph")
    private double windMph;

    @Expose
    @SerializedName("wind_kph")
    private double windKph;

    @Expose
    @SerializedName("wind_degree")
    private double windDegree;

    @Expose
    @SerializedName("wind_dir")
    private String windDir;

    @Expose
    @SerializedName("precip_mm")
    private double precipMM;

    @Expose
    @SerializedName("humidity")
    private double humidity;

    @Expose
    @SerializedName("cloud")
    private double cloud;

    @Expose
    @SerializedName("feelslike_c")
    private double feelsLikeCelsius;

    @Expose
    @SerializedName("feelslike_f")
    private double feelsLikeFahrenheit;

    public double getTempCelsius() {
        return tempCelsius;
    }

    public void setTempCelsius(double tempCelsius) {
        this.tempCelsius = tempCelsius;
    }

    public double getTempFahrenheit() {
        return tempFahrenheit;
    }

    public void setTempFahrenheit(double tempFahrenheit) {
        this.tempFahrenheit = tempFahrenheit;
    }

    public int getIsDay() {
        return isDay;
    }

    public void setIsDay(int isDay) {
        this.isDay = isDay;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public double getWindMph() {
        return windMph;
    }

    public void setWindMph(double windMph) {
        this.windMph = windMph;
    }

    public double getWindKph() {
        return windKph;
    }

    public void setWindKph(double windKph) {
        this.windKph = windKph;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public double getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(double precipMM) {
        this.precipMM = precipMM;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getCloud() {
        return cloud;
    }

    public void setCloud(double cloud) {
        this.cloud = cloud;
    }

    public double getFeelsLikeCelsius() {
        return feelsLikeCelsius;
    }

    public void setFeelsLikeCelsius(double feelsLikeCelsius) {
        this.feelsLikeCelsius = feelsLikeCelsius;
    }

    public double getFeelsLikeFahrenheit() {
        return feelsLikeFahrenheit;
    }

    public void setFeelsLikeFahrenheit(double feelsLikeFahrenheit) {
        this.feelsLikeFahrenheit = feelsLikeFahrenheit;
    }
}

package com.devina.weatherapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {

    @Expose
    @SerializedName("maxtemp_c")
    private double maxTempC;

    @Expose
    @SerializedName("maxtemp_f")
    private double maxTempF;

    @Expose
    @SerializedName("mintemp_c")
    private double minTempC;

    @Expose
    @SerializedName("mintemp_f")
    private double minTempF;

    @Expose
    @SerializedName("condition")
    private Condition condition;

    public double getMaxTempC() {
        return maxTempC;
    }

    public void setMaxTempC(double maxTempC) {
        this.maxTempC = maxTempC;
    }

    public double getMaxTempF() {
        return maxTempF;
    }

    public void setMaxTempF(double maxTempF) {
        this.maxTempF = maxTempF;
    }

    public double getMinTempC() {
        return minTempC;
    }

    public void setMinTempC(double minTempC) {
        this.minTempC = minTempC;
    }

    public double getMinTempF() {
        return minTempF;
    }

    public void setMinTempF(double minTempF) {
        this.minTempF = minTempF;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}

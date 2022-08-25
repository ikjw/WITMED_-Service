package com.example.test.entity;

public class dish {
    public String name;
    public Float calorie;
    public Float probability;
    public Boolean has_calorie;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCalorie() {
        return calorie;
    }

    public void setCalorie(Float calorie) {
        this.calorie = calorie;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    public Boolean getHas_calorie() {
        return has_calorie;
    }

    public void setHas_calorie(Boolean has_calorie) {
        this.has_calorie = has_calorie;
    }
}

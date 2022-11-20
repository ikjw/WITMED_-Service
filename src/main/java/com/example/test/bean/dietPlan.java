package com.example.test.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class dietPlan {
    private int id;
    @JsonProperty("uUID")
    private String uUID;
    @JsonProperty("dUID")
    private String dUID;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private float mainFood;
    private float vegetables;
    private float fruits;
    private float meat_egg;
    private float soybeans;
    private float dairy;
    private float nuts;
    private float oils;
    private String details;
    private int machineId;
}

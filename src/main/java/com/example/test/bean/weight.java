package com.example.test.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class weight {
    @JsonProperty("UID")
    private String UID;
    private double value;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private int source;
    private String detail;
}

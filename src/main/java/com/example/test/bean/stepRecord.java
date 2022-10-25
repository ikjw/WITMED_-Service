package com.example.test.bean;

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
public class stepRecord {
    @JsonProperty("UID")
    private String UID;
    private LocalDateTime start;
    private LocalDateTime end;
    private int step;
    private int source;
}

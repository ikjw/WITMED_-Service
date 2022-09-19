package com.example.test.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class bloodOxygen {
    private String UID;
    private double value;
    private LocalDateTime time;
    private int source;
    private String detail;
}

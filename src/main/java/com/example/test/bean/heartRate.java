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
public class heartRate {
    private String UID;
    private int value;
    private LocalDateTime time;
    private int source;
    private String detail;
}

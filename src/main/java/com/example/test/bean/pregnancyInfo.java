package com.example.test.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class pregnancyInfo {
    String UID;
    double ppHeight;
    double ppWeight;
    LocalDate pDate;
    int numberOfFetuses;
    int partiy;
}

package com.example.test.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate pDate;
    int numberOfFetuses;
    int partiy;
}

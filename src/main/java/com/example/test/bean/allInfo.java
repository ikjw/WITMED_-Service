package com.example.test.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class allInfo {
    userInfo userInfo;
    pregnancyInfo pregnancyInfo;
    double height;
    double weight;
}

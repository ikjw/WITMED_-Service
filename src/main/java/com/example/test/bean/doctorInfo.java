package com.example.test.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class doctorInfo {
    @JsonProperty("UID")
    private String UID;
    private String name;
    private int gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;
    private String faceBase64;
    private String domain;
    private String profile;
}

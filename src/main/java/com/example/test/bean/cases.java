package com.example.test.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class cases {
    private int id;
    @JsonProperty("UID")
    private String UID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime time;
    String img;
}

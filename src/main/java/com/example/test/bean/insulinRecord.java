package com.example.test.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class insulinRecord {
    @JsonProperty("UID")
    String UID;
    medicine M;
    int count;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime time;
    int source;
    String detail;
}

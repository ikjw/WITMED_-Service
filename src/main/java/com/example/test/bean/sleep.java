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
public class sleep {
    private String UID;
    private LocalDateTime start;
    private LocalDateTime end;
    private int type;
    private int source;
    private String detail;
}

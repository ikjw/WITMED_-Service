package com.example.test.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class invitationCode {
    String dUID;
    String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creatTime;
    int state;
    userInfo uUID;
}

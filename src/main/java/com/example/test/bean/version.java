package com.example.test.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class version {
    private int id;
    private String name;
    private int versionCode;
    private String versionName;
    private int maxCompatibleVersion;
    private String description;
    private String detailDescription;
}

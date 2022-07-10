package com.example.test.service;

import com.example.test.entity.Disease;

import java.util.List;

public interface IDiseaseService {
    List<Disease> get(String name);
}

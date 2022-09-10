package com.example.test.old.old_service;

import com.example.test.old.entity.Disease;

import java.util.List;

public interface IDiseaseService {
    List<Disease> get(String keyword,int page);
}

package com.example.test.service;

import com.example.test.entity.Index;

public interface IIndexService {
    void add(Index index);

    Index search(Index index);

    void update(Index index);
}

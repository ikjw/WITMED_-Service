package com.example.test.old.old_service;

import com.example.test.old.entity.Index;

public interface IIndexService {
    void add(Index index);

    Index search(Index index);

    void update(Index index);
}

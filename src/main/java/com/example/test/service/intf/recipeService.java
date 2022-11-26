package com.example.test.service.intf;

import com.example.test.bean.recipe;
import com.example.test.bean.recipeCollection;

import java.time.LocalDateTime;
import java.util.List;

public interface recipeService {
    List<recipe> query(String keyWord,boolean name,boolean raw,int pageIndex,int pageCount);

    int insert(String UID, int dishId, LocalDateTime time);

    int delete(String UID, int dishId);

    List<recipeCollection> queryCollection(String UID);

    recipe queryById(int id);
}

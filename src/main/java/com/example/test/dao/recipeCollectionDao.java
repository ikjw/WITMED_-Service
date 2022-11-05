package com.example.test.dao;

import com.example.test.bean.recipe;
import com.example.test.bean.recipeCollection;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface recipeCollectionDao {
    int insert(String UID,int dishId,String time);

    int delete(String UID,int dishId);

    recipeCollection getTime(String UID, int dishId);
}

package com.example.test.dao;

import com.example.test.bean.recipe;
import com.example.test.bean.recipeCollection;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface recipeDao {
    List<recipe> queryByName(String keyWord,int ignoreNum,int pageCount);

    List<recipe> queryByRaw(String keyWord,int ignoreNum,int pageCount);

    List<recipe> queryByNameAndRaw(String keyWord,int ignoreNum,int pageCount);

    List<recipe> queryByCollection(String UID);

    recipe queryById(int id);
    int updateImage(int id, String image);
    int updateName(int id,String newName);

}

package com.example.test.service.intf;

import com.example.test.bean.recipeRecommend;

import java.io.IOException;

public interface recipeRecommendService {
    recipeRecommend getRecommend(String UID) throws IOException, InterruptedException;
    void likeRecommend(String UID);
    recipeRecommend changeRecommend(String UID) throws IOException, InterruptedException;
}

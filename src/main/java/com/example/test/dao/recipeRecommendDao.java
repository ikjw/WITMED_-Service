package com.example.test.dao;

import com.example.test.bean.recipeRecommend;
import org.springframework.stereotype.Repository;

@Repository
public interface recipeRecommendDao {
    /**
     * 获取饮食方案对应的推荐方案(如果有多条，返回时间最近的一条）
     */
    recipeRecommend getByDietPlan(int dietPlanId);

    /**
     * 更新推荐方案,只更新state和changeTime字段
     */
    int update(recipeRecommend rr);

    /**
     * 插入一条新记录
     */
    int insert(recipeRecommend rr);

    /**
     * 获取id对应的推荐方案
     */
    recipeRecommend getById(int id);
}

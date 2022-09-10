package com.example.test.old.old_dao;

import com.example.test.old.entity.Disease;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiseaseDao {
    /**
     * 实现分页时，
     * ignoreNum=(pageIndex-1)*numOfpage
     * @param keyword 检索关键词
     * @param ignoreNum 需要忽略的数量
     * @param numOfpage 每页的数量
     * @return
     */
    List<Disease> get(@Param("keyword")String keyword,@Param("ignoreNum")int ignoreNum,@Param("numOfpage") int numOfpage);
}

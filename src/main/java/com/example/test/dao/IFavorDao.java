package com.example.test.dao;

import com.example.test.entity.favor;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableAutoConfiguration
@Repository
public interface IFavorDao {
    int addfavor(@Param("username") String username, @Param("foodid") int foodid);
    int deletefavor(@Param("username") String username, @Param("foodid") int foodid);
    List<favor> searchfavor(@Param("username") String username, @Param("foodid") int foodid);
    List<favor> favorlist(@Param("username") String username);
}

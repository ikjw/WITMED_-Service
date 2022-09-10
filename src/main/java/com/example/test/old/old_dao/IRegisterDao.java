package com.example.test.old.old_dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegisterDao {
    void register(@Param("username")String username, @Param("password")String password);
    int registerDoctor(@Param("username")String username, @Param("password")String password,@Param("role") int role);
}

package com.example.test.dao;

import com.example.test.entity.Login;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoginDao {
    Login login(@Param("username") String username, @Param("password") String password);
    Login loginuser(@Param("username") String username, @Param("password") String password, @Param("role") int role);
    Login query(@Param("username") String username);
}


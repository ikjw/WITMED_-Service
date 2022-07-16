package com.example.test.dao;

import com.example.test.entity.Login;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoginDao {
    int insert(@Param("username")String username,@Param("password") String password,@Param("role") int role);
    Login queryByAll(@Param("username") String username, @Param("password") String password, @Param("role") int role);
    Login queryByUsername(@Param("username") String username);
}


package com.example.test.old.old_dao;

import com.example.test.old.entity.DoctorInfo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDoctorInfoDao {
    DoctorInfo query(@Param("username")String username);

    int insert(@Param("username")String username,@Param("name") String name,
                @Param("sex") Integer sex,@Param("age") Integer age,
                @Param("domain") String domain,@Param("profile") String profile);

    int update(@Param("username") String username,
                @Param("name") String name,@Param("sex") Integer sex,
                @Param("age") Integer age,@Param("domain") String domain,
                @Param("profile") String profile);

    int delete(@Param("username")String username);

    List<DoctorInfo> getList();
}

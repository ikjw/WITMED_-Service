package com.example.test.dao;

import com.example.test.entity.Information;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInformationDao {
    Information get(@Param("username") String username);

    void addnew(@Param("username") String username,@Param("clientName") String clientName,@Param("birth") int birth, @Param("height1") double height1,
                @Param("weight1") double weight1, @Param("date") String date,
                @Param("num") String num, @Param("age") int age,@Param("energy") String energy,
                @Param("aveenergy") String aveenergy, @Param("enweight") String enweight,
                @Param("lenweight") String lenweight,@Param("diseases")String diseases,
                @Param("ffm")double ffm,@Param("muscle") double muscle,@Param("protein") double protein,@Param("UCrE_24") double UCrE_24);

    void updatedis(@Param("username")String username, @Param("diseases")String diseases);

    Information getdis(@Param("username")String username);

    void update(@Param("username") String username,@Param("birth") int birth, @Param("height1") double height1,
                @Param("weight1") double weight1, @Param("date") String date,
                @Param("num") String num, @Param("age") int age,@Param("energy") String energy,
                @Param("aveenergy") String aveenergy, @Param("enweight") String enweight,
                @Param("lenweight") String lenweight,
                @Param("ffm")double ffm,@Param("muscle") double muscle,@Param("protein") double protein,@Param("UCrE_24") double UCrE_24);

    List<Information> getPatientByDoctor(@Param("doctorUsername") String doctorUsername);

    List<Information>  getAll();
}

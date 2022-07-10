package com.example.test.dao;

import com.example.test.entity.dietplan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdietplanDao {
    int add(@Param("targetUsername") String targetUsername,@Param("doctorUsername") String doctorUsername,
            @Param("start")String start,@Param("end") String end,@Param("totalEnergy") int totalEnergy,
            @Param("totalFood")int totalFood,@Param("mainFood")String mainFood,@Param("vegetables") String vegetables,
            @Param("fruit") String fruit,@Param("meatAndEgg") String meatAndEgg,@Param("bean") String bean,
            @Param("milk") String milk,@Param("nut")String nut,@Param("oil") String oil,@Param("salt")String salt);

    List<dietplan> queryByPatient(@Param("targetUsername") String targetUsername);

    List<dietplan> queryByDoctor(@Param("doctorUsername") String doctorUsername);

    List<dietplan> queryByDoctorAndPatient(@Param("targetUsername") String targetUsername,@Param("doctorUsername") String doctorUsername);

}

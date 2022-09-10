package com.example.test.old.old_dao;

import com.example.test.old.entity.Index;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IIndexDao {
    void add(@Param("id") String id, @Param("icf") double icf, @Param("ecf") double ecf,
             @Param("muscle") double muscle, @Param("protein") double protein,
             @Param("ffweight") double ffweight, @Param("perfat") double perfat,
             @Param("brate") double brate);

    Index search(@Param("id") String id);

    void update(@Param("id") String id, @Param("icf") double icf, @Param("ecf") double ecf,
                @Param("muscle") double muscle, @Param("protein") double protein,
                @Param("ffweight") double ffweight, @Param("perfat") double perfat,
                @Param("brate") double brate);
}

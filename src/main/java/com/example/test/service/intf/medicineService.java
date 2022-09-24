package com.example.test.service.intf;

import com.example.test.bean.medicine;

import java.util.List;

public interface medicineService {
    /**
     *
     * @param keyWord 不能为空
     * @param pageIndex >=1,页数
     * @param pageCount >0，每页的数量
     * @return 查询结果
     */
    List<medicine> query(String keyWord,int pageIndex,int pageCount);

    /**
     * 根据mID查询对应的药品
     * @param mID
     * @return
     */
    medicine queryBymID(int mID);
}

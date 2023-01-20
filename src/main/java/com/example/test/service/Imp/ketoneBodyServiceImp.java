package com.example.test.service.Imp;

import com.example.test.bean.ketoneBody;
import com.example.test.service.intf.ketoneBodyService;
import com.example.test.utils.CheckPreCondition;
import com.example.test.dao.ketoneBodyDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class ketoneBodyServiceImp implements ketoneBodyService {
    @Resource
    ketoneBodyDao ketoneBodyDao;

    @Override
    public int batchInsert(List<ketoneBody> ketoneBodyList, String mUID) {
        CheckPreCondition.notNull(mUID);
        int count = 0;
        for (ketoneBody ketoneBody : ketoneBodyList) {
            count += insert(ketoneBody, mUID);
        }
        return count;
    }

    @Override
    public int insert(ketoneBody kb, String mUID) {
        CheckPreCondition.notNull(mUID);
        if (kb.getValue() != 0 && kb.getValue() != 1)
            return 0;
        kb.setUID(mUID);
        int result = 0;
        try {
            result = ketoneBodyDao.insert(kb);
        } catch (DuplicateKeyException e) {
            log.debug(e.getMessage());
        }
        return result;
    }

    @Override
    public List<ketoneBody> query(String mUID, LocalDateTime from, LocalDateTime to) {
        CheckPreCondition.notNull(mUID);
        CheckPreCondition.notNull(from);
        CheckPreCondition.notNull(to);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ketoneBodyDao.query(mUID, from.format(fm), to.format(fm));
    }

    @Override
    public ketoneBody queryRecent(String mUID) {
        CheckPreCondition.notNull(mUID);
        return ketoneBodyDao.queryRecent(mUID);
    }
}

package com.example.test.service.Imp;

import com.example.test.bean.recipe;
import com.example.test.bean.recipeCollection;
import com.example.test.config.envConfig;
import com.example.test.dao.recipeCollectionDao;
import com.example.test.dao.recipeDao;
import com.example.test.service.intf.recipeService;
import com.example.test.utils.CheckPreCondition;
import com.example.test.utils.ImageToBase64Util;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class recipeServiceImp implements recipeService {
    @Resource
    envConfig config;
    @Resource
    recipeDao recipeDao;
    @Resource
    recipeCollectionDao recipeCollectionDao;
    @Override
    public List<recipe> query(String keyWord,boolean name,boolean raw,int pageIndex,int pageCount){
        CheckPreCondition.notNull(keyWord);
        if(pageIndex<1) throw new RuntimeException("pageIndex>=1 ！");
        if(pageCount<=0) throw new RuntimeException("pageCount>0 !");
        if (!raw&&!name)
            return null;
        else if (name&&!raw)
            return recipeDao.queryByName(keyWord,(pageIndex-1)*pageCount,pageCount);
        else if (!name)
            return recipeDao.queryByRaw(keyWord,(pageIndex-1)*pageCount,pageCount);
        else
            return recipeDao.queryByNameAndRaw(keyWord,(pageIndex-1)*pageCount,pageCount);
    }
    @Override
    public int insert(String UID, int dishId, LocalDateTime time){
        CheckPreCondition.notNull(UID);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return recipeCollectionDao.insert(UID,dishId, time.format(fm));
    }
    @Override
    public int delete(String UID,int dishId){
        CheckPreCondition.notNull(UID);
        return recipeCollectionDao.delete(UID,dishId);
    }
    @Override
    public List<recipeCollection> queryCollection(String UID){
        CheckPreCondition.notNull(UID);
        List<recipe> recipeList = recipeDao.queryByCollection(UID);
        List<recipeCollection> recipeCollectionList = new ArrayList<>();
        for (recipe recipe : recipeList) {
            recipeCollection rc = new recipeCollection();
            rc.setDish(recipe);
            recipeCollection time = recipeCollectionDao.getTime(UID,recipe.getId());
            rc.setUID(UID);
            rc.setTime(time.getTime());
            recipeCollectionList.add(rc);
        }
        return recipeCollectionList;
    }

    @Override
    public recipe queryById(int id) {
        return recipeDao.queryById(id);
    }
    @Override
    public int update(String newName,JSONArray imgs,int id) {
        CheckPreCondition.notNull(newName);
        CheckPreCondition.notNull(imgs);
        int success = recipeDao.updateName(id, newName);
        JSONArray jsonArray = imgs;
        for(int i=0;i<jsonArray.size();i++){
            String str = jsonArray.getString(i);
            if(!isFileName(str)){
                File file = ImageToBase64Util.convertBase64ToFile(str,config.getRecipeImage());
                String fileName = file.getName();
                jsonArray.set(i,fileName);
            }
        }
        return success*recipeDao.updateImage(id, jsonArray.toString());
    }
    @Override
    public int updateName(String newName,int id){
        CheckPreCondition.notNull(newName);
        return recipeDao.updateName(id, newName);
    }
    private boolean isFileName(String str){
        return str.matches("[0-9]*-[0-9]*.[A-Za-z]*") || str.matches("[0-9]*.[A-Za-z]*");
    }
}

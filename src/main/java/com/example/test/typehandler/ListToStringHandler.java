package com.example.test.typehandler;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.test.bean.recipe;
import com.example.test.service.intf.recipeService;
import com.example.test.utils.fastJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.*;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Slf4j
public class ListToStringHandler extends BaseTypeHandler<List<Object>> {

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<Object> data, JdbcType jdbcType) throws SQLException {
        log.info("ListToStringHandler...");
        String str = "[]";
        if(data.size() !=0){
            if(data.get(0).getClass().equals(recipe.class)){
                List<Integer> tmp  = new ArrayList<>();
                for(Object r : data){
                    tmp.add(((recipe)r).getId());
                }
                str = fastJsonUtils.objectToJsonStr(tmp);
            }else{
                str = fastJsonUtils.objectToJsonStr(data);
            }
        }
        preparedStatement.setString(i,str);
    }

    @SneakyThrows
    @Override
    public List<Object> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String sqlJson = resultSet.getString(s);
        if(sqlJson != null){
            List<Object> lst = null;
            try {
                lst = new ArrayList<>(getRecipes(sqlJson));
            }catch (Exception e){
                log.info("非recipe类型");
                lst = new ArrayList<>(getList(sqlJson));
            }
            return lst;
        }
        return new ArrayList<>();
    }

    @SneakyThrows
    @Override
    public List<Object> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String sqlJson = resultSet.getString(i);
        if(sqlJson != null){
            List<Object> lst = null;
            try {
                lst = new ArrayList<>(getRecipes(sqlJson));
            }catch (Exception e){
                log.info("非recipe类型");
                lst = new ArrayList<>(getList(sqlJson));
            }
            return lst;
        }
        return new ArrayList<>();
    }

    @SneakyThrows
    @Override
    public List<Object> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String sqlJson = callableStatement.getString(i);
        if(sqlJson != null){
            List<Object> lst = null;
            try {
                lst = new ArrayList<>(getRecipes(sqlJson));
            }catch (Exception e){
                log.info("非recipe类型");
                lst = new ArrayList<>(getList(sqlJson));
            }
            return lst;
        }
        return new ArrayList<>();
    }

    private List<recipe> getRecipes(String str) throws JsonProcessingException {
        List<Integer> tmp = fastJsonUtils.jsonStrToObjectList(str,Integer[].class);
        List<recipe> recipes = new ArrayList<>();
        recipeService service = SpringUtil.getBean(recipeService.class);
        for(Integer k : tmp){
            recipe r = service.queryById(k);
            if(r!=null){
                recipes.add(r);
            }
        }
        return recipes;
    }
    private List<String> getList(String str) throws JsonProcessingException {
        if(str == null || str.length() == 0) return new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(str);
        List<String> lst = new ArrayList<>();
        for (int i = 0 ; i < jsonArray.size();i++)
        {
            String s  = jsonArray.getString(i);
            lst.add(s);
        }
        return lst;
    }
}

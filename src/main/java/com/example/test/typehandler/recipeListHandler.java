package com.example.test.typehandler;

import cn.hutool.extra.spring.SpringUtil;
import com.example.test.bean.recipe;
import com.example.test.service.intf.recipeService;
import com.example.test.utils.fastJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class recipeListHandler extends BaseTypeHandler<List<recipe>> {

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<recipe> recipes, JdbcType jdbcType) throws SQLException {
        List<Integer> tmp  = new ArrayList<>();
        for(recipe r : recipes){
                tmp.add(r.getId());
        }
        String str = fastJsonUtils.objectToJsonStr(tmp);
        preparedStatement.setString(i,str);
    }

    @SneakyThrows
    @Override
    public List<recipe> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String sqlJson = resultSet.getString(s);
        return getRecipes(sqlJson);
    }

    @SneakyThrows
    @Override
    public List<recipe> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String sqlJson = resultSet.getString(i);
        if(sqlJson != null){
            return getRecipes(sqlJson);
        }
        return new ArrayList<>();
    }

    @SneakyThrows
    @Override
    public List<recipe> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String sqlJson = callableStatement.getString(i);
        if(sqlJson != null){
            return getRecipes(sqlJson);
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
}

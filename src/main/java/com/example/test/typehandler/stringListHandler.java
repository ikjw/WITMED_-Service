package com.example.test.typehandler;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.test.bean.recipe;
import com.example.test.service.intf.recipeService;
import com.example.test.utils.ImageToBase64Util;
import com.example.test.utils.fastJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 用以mysql中json格式的字段，进行转换的自定义转换器，转换为实体类的JSONObject属性
 * MappedTypes注解中的类代表此转换器可以自动转换为的java对象
 * MappedJdbcTypes注解中设置的是对应的jdbctype
 */

@MappedTypes(JSONObject.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class stringListHandler extends BaseTypeHandler<List<String>>{

    //设置非空参数
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.valueOf(parameter.toString()));
    }
    //根据列名，获取可以为空的结果
    @SneakyThrows
    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String sqlJson = rs.getString(columnName);
        if (null != sqlJson&&!sqlJson.equals("")){
            return getList(sqlJson);
        }
        return null;
    }
    //根据列索引，获取可以为空的结果
    @SneakyThrows
    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String sqlJson = rs.getString(columnIndex);
        if (null != sqlJson&&!sqlJson.equals("")){
            return getList(sqlJson);
        }
        return null;
    }

    @Override
    @SneakyThrows
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String sqlJson = cs.getString(columnIndex);
        if (null != sqlJson&&!sqlJson.equals("")){
            return getList(sqlJson);
        }
        return null;
    }
    private List<String> getList(String str) throws JsonProcessingException {
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

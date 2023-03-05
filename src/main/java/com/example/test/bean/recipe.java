package com.example.test.bean;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.test.typehandler.ObjectJsonHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sf.json.JSONObject;


import java.util.List;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class recipe {
    int id;
    String name; // 菜名
    List<String> cookMethod; // 烹饪方式
    String cookTime; //烹饪时间
    Float calorie; // 卡路里 单位：千卡
    Float carbohydrate; // 碳水化合物 单位：克
    Float protein; //蛋白质 单位：克
    Float fat; //脂肪 单位：克
    Float cholesterol;//胆固醇 单位：克
    Float dietaryFiber; //膳食纤维 单位：克
    Map<String,Float> minerals; //矿物质元素
    Map<String,Float> vitamin; //维生素
    Map<String,Float> others; //其它
    Map<String,String> mainMaterials; // 主要原料
    Map<String,String> accessories; // 辅料
    List<String> notCalculated; //无法计算的食物
    List<String> img;
}

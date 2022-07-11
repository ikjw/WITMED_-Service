package com.example.test.controller;

import com.example.test.entity.Bg;
import com.example.test.entity.Weight;
import com.example.test.service.IBgService;
import com.example.test.service.IWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/weight")
public class WeightController {

    @Autowired
    private IWeightService iWeightService;

    /**
     *
     * @param weight 三个字段都不能为null
     * @return code = 0 失败 大于0成功
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private Map<String,Object> add(@RequestBody Weight weight) {
        Map<String,Object> map = new HashMap<>();
        int flag = iWeightService.add(weight.getUsername(), weight.getWeight(), weight.getDate());
        map.put("code",flag);
        return map;
    }

    /**
     *
     * @param map 需要包含 “username”字段
     * @return username的所有体重记录，
     *         没有查询到返回 “[]”
     */
    @RequestMapping(value = "/getWeightList", method = RequestMethod.POST)
    private List<Weight> getWeightList(@RequestBody Map<String,Object> map) {
        return iWeightService.getWeightList((String)map.get("username"));
    }

    /**
     *
     * @param map 需要包含"username"字段
     * @return username对应的最近的一次体重记录
     */
    @RequestMapping(value = "/getRecentWeight", method = RequestMethod.POST)
    private Weight getRecentWeight(@RequestBody Map<String,Object> map) {
        return iWeightService.getRecentWeight((String)map.get("username"));
    }
}

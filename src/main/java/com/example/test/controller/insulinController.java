package com.example.test.controller;


import com.example.test.entity.insulin;
import com.example.test.service.IBgService;
import com.example.test.service.IinsulinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/insulin")
public class insulinController {
    @Autowired
    private IinsulinService insulinService;
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Map<String,Object> insert(@RequestBody insulin in){
        Map<String,Object> map = new HashMap<>();
        map.put("code",insulinService.insert(in));
        return map;
    }

    @RequestMapping(value = "/getRecent", method = RequestMethod.POST)
    public insulin getRecent(@RequestBody Map<String,Object> map){
        String username = (String) map.get("username");
        return insulinService.getRecent(username);
    }

}

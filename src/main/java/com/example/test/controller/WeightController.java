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

import java.util.List;

@RestController
@RequestMapping(value = "/weight")
public class WeightController {

    @Autowired
    private IWeightService iWeightService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private Boolean add(@RequestBody Weight weight) {
        return iWeightService.add(weight.getId(), weight.getWeight(), weight.getWeek());
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    private List<Weight> view(@RequestBody Weight weight) {
        return iWeightService.view(weight.getId());
    }
}

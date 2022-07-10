package com.example.test.controller;

import com.example.test.entity.Bg;
import com.example.test.service.IBgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bg")
public class BgController {

    @Autowired
    private IBgService iBgService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private Boolean add(@RequestBody Bg bg) {
        return iBgService.add(bg.getId(), bg.getBg_value(), bg.getTime(), bg.getTime_slot());
    }

    @RequestMapping(value = "/day", method = RequestMethod.POST)
    private List<Bg> day(@RequestBody Bg bg) {
        return iBgService.day(bg.getId(), bg.getTime());
    }

    @RequestMapping(value = "/week", method = RequestMethod.POST)
    private List<Bg> week(@RequestBody Bg bg) {
        return iBgService.week(bg.getId(), bg.getTime());
    }

    @RequestMapping(value = "/month", method = RequestMethod.POST)
    private List<Bg> month(@RequestBody Bg bg) {
        return iBgService.month(bg.getId(), bg.getTime());
    }
}
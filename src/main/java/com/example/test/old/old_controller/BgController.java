package com.example.test.old.old_controller;

import com.example.test.old.old_service.IBgService;
import com.example.test.controller.intf.IPermission;
import com.example.test.old.entity.Bg;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/bg")
public class BgController implements IPermission {

    @Resource
    private IBgService iBgService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private Boolean add(@RequestBody Bg bg) {
        return iBgService.add(bg.getUsername(), bg.getBg_value(), bg.getTime(), bg.getTime_slot());
    }

    @RequestMapping(value = "/day", method = RequestMethod.POST)
    private List<Bg> day(@RequestBody Bg bg) {
        return iBgService.day(bg.getUsername(), bg.getTime());
    }

    @RequestMapping(value = "/week", method = RequestMethod.POST)
    private List<Bg> week(@RequestBody Bg bg) {
        return iBgService.week(bg.getUsername(), bg.getTime());
    }

    @RequestMapping(value = "/month", method = RequestMethod.POST)
    private List<Bg> month(@RequestBody Bg bg) {
        return iBgService.month(bg.getUsername(), bg.getTime());
    }
}
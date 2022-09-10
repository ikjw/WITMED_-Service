package com.example.test.old.old_controller;

import com.example.test.controller.intf.IPermission;
import com.example.test.old.old_service.ISportService;
import com.example.test.old.entity.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/sport")
public class SportController implements IPermission {
    @Autowired
    private ISportService iSportService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private Boolean add(@RequestBody Sport sport){
        return iSportService.add(sport.getUser(), sport.getId(), sport.getCal(), sport.getCdate(), sport.getTime_slot());
    }
    @RequestMapping(value = "/day", method = RequestMethod.POST)
    private List<Sport> day(@RequestBody Sport sport) {
        return iSportService.day(sport.getUser(), sport.getCdate());
    }
}

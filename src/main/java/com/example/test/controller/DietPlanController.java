package com.example.test.controller;

import com.example.test.controller.intf.IPermission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/dietplan/")
public class DietPlanController implements IPermission {

}

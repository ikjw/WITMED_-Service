package com.example.test.controller;

import com.example.test.entity.Disease;
import com.example.test.service.IDiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/diseases")
public class DiseasesController {
    @Autowired
    private IDiseaseService iDiseaseService;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    private List<Disease> get(@RequestBody Disease disease){
        return iDiseaseService.get(disease.getName());
    }
}
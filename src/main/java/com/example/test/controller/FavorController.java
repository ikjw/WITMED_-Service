package com.example.test.controller;

import com.example.test.entity.favor;
import com.example.test.service.IFavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableAutoConfiguration
@RestController
@RequestMapping(value="/food")
public class FavorController {
@Autowired
    private IFavorService iFavorService;
@RequestMapping(value = "/addfavor", method = RequestMethod.POST)
    private int addfavor(@RequestParam String username, @RequestParam int foodid){return iFavorService.addfavor(username, foodid);}
@RequestMapping(value = "/deletefavor", method = RequestMethod.POST)
    private int deletefavor(@RequestParam String username, @RequestParam int foodid){return iFavorService.deletefavor(username, foodid);}
@RequestMapping(value = "/searchfavor", method = RequestMethod.POST)
    private List<favor> searchfavor(@RequestParam String username, @RequestParam int foodid){return iFavorService.searchfavor(username, foodid);}
@RequestMapping(value = "/favorlist", method = RequestMethod.POST)
    private List<favor> favorlist(@RequestParam String username){return iFavorService.favorlist(username);}
}

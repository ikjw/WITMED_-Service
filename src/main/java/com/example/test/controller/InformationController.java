package com.example.test.controller;

import com.example.test.entity.Information;
import com.example.test.service.IInformationService;
import com.example.test.utils.MySessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/information")
public class InformationController {
    @Autowired
    private IInformationService iInformationService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    private Information get(HttpServletRequest request, @RequestBody Information information) {
        MySessionContext myc = MySessionContext.getInstance();
        String sessionid = request.getHeader("Cookie");
        HttpSession session = myc.getSession(sessionid);
        if (session == null) {//会话超时
            /*myc.AddSession(request.getSession());
            Information inform = new Information();
            inform.setUsername("!@#");
            return inform;*/
            return iInformationService.get(information.getUsername());
        } else {
            return iInformationService.get(information.getUsername());
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private void add(HttpServletRequest request, @RequestBody Information information) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(MySessionContext.getSessiontime());//有效时间
        session.setAttribute("username", information.getUsername());
        MySessionContext myc = MySessionContext.getInstance();
        myc.AddSession(session);
        iInformationService.addnew(information);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    private void update(HttpServletRequest request, @RequestBody Information information) {
        MySessionContext myc = MySessionContext.getInstance();
        String sessionid = request.getHeader("Cookie");
        HttpSession session = myc.getSession(sessionid);
        if (session == null) {//会话超时
            myc.AddSession(request.getSession());
            iInformationService.update(information);
        } else {
            /* Information inform = get(request, information);
            String temp=inform.getWeight2();
            String[] tempw=temp.split(";");
            int len = Integer.parseInt(inform.getNum());*/
            iInformationService.update(information);
        }
    }

    @RequestMapping(value = "/getdis",method = RequestMethod.POST)
    private Information getdis(HttpServletRequest request,@RequestBody Information information){
        MySessionContext myc = MySessionContext.getInstance();
        String sessionid = request.getHeader("Cookie");
        HttpSession session = myc.getSession(sessionid);
        if(session == null){
           /* myc.AddSession(request.getSession());
            Information temp = new Information();
            temp.setUsername("!@#");*/
            return iInformationService.getdis(information.getUsername());
        }else{
            return iInformationService.getdis(information.getUsername());
        }
    }
    @RequestMapping(value = "/updatedis", method = RequestMethod.POST)
    private void updatedis(HttpServletRequest request, @RequestBody Information information) {
        MySessionContext myc = MySessionContext.getInstance();
        String sessionid = request.getHeader("Cookie");
        HttpSession session = myc.getSession(sessionid);
        if (session == null) {
            iInformationService.updatedis(information.getUsername(), information.getDiseases());
        } else {
            iInformationService.updatedis(information.getUsername(), information.getDiseases());
        }
    }
    @RequestMapping(value = "/getPatientsByDoctor",method = RequestMethod.POST)
    private List<Information> getPatientsByDoctor(@RequestBody Map<String,Object> map){
        return iInformationService.getPatientByDoctor((String)map.get("doctorUsername"));
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    private List<Information> getAll(){
        return  iInformationService.getAll();
    }
}
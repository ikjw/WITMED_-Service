package com.example.test.old.old_controller;

import com.example.test.controller.intf.IPermission;
import com.example.test.old.old_service.IIndexService;
import com.example.test.old.entity.Index;
import com.example.test.old.old_utils.MySessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/index")
public class IndexController implements IPermission {
    @Autowired
    private IIndexService iIndexService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private void add(HttpServletRequest request, @RequestBody Index index) {
        iIndexService.add(index);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    private Index search(HttpServletRequest request, @RequestBody Index index) {
        MySessionContext myc = MySessionContext.getInstance();
        String sessionid = request.getHeader("Cookie");
        HttpSession session = myc.getSession(sessionid);
        if (session == null) {//会话超时
            myc.AddSession(request.getSession());
            return new Index();
        } else {
            return iIndexService.search(index);
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    private void update(HttpServletRequest request, @RequestBody Index index) {
        MySessionContext myc = MySessionContext.getInstance();
        String sessionid = request.getHeader("Cookie");
        HttpSession session = myc.getSession(sessionid);
        if(session == null){
            myc.AddSession(request.getSession());
        }else {
            iIndexService.update(index);
        }
    }
}

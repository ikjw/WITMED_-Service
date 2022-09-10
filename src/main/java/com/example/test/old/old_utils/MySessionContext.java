package com.example.test.old.old_utils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class MySessionContext {
    private static MySessionContext instance;
    private final HashMap<String, HttpSession> mymap;
    private static final int sessiontime = 60*60*60;

    public static int getSessiontime() {
        return sessiontime;
    }

    private MySessionContext() {
        mymap = new HashMap<>();
    }

    public static MySessionContext getInstance() {
        if (instance == null) {
            instance = new MySessionContext();
        }
        return instance;
    }

    public synchronized void AddSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public synchronized void DelSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    public synchronized HttpSession getSession(String session_id) {
        if (session_id == null) return null;
        return mymap.get(session_id);
    }

}

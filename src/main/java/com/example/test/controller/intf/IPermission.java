package com.example.test.controller.intf;

public interface IPermission {
    default boolean hasPermission(String username,int role,String URI){
        return true;
    }
}

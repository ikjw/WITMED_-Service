package com.example.test.utils;

public class CheckPreCondition{
    public static void notNull(Object obj){
        if(obj == null) {
            throw new NullPointerException();
        }
    }
}

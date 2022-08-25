package com.example.test.entity;

import java.util.List;

public class dishResult {
    public String log_id;
    public int result_num;
    public List<dish> result;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<dish> getResult() {
        return result;
    }

    public void setResult(List<dish> result) {
        this.result = result;
    }
}

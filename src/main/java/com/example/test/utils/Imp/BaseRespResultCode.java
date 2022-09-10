package com.example.test.utils.Imp;

import com.example.test.utils.intf.IRespResultCode;

public enum BaseRespResultCode implements IRespResultCode {
    /**
     * 成功
     */
    OK(1,"成功","成功"),
    /**
     * 系统异常
     */
    SYS_EXCEPTION(-1,"服务器发生故障，请稍后重试","系统异常"),
    /**
     * 参数不合法
     */
    ERR_PARAM_NOT_LEGAL(2,"参数不合法","参数不合法"),
    /**
     * 登录超时（未登录）
     */
    LOGIN_TIMEOUT(3,"登录超时","登录超时"),
    /**
     * 无访问权限
     */
    PERMISSION_DENIED(4,"无访问权限","无访问权限");
    private int code;
    private String message;
    private String detailMessage;
    private BaseRespResultCode(int code,String message,String detailMessage){
        this.code = code;
        this.message = message;
        this.detailMessage = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getDetailMessage() {
        return detailMessage;
    }
}

package com.example.test.utils.Imp;

import com.example.test.utils.intf.IRespResultCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;


import java.io.Serializable;
@ToString
@Data
public class RespResult<T> implements Serializable {
    private int code = BaseRespResultCode.OK.getCode();
    private String message = BaseRespResultCode.OK.getMessage();
    private String detailMessage = BaseRespResultCode.OK.getDetailMessage();
    private T data;
    private String env;
    private String session;

    public RespResult(int code,String message,String detailMessage,T data,String env,String session){
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
        this.data = data;
        this.env = env;
        this.session = session;
    }

    public RespResult(IRespResultCode resultCode, T data, String env, String session){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.detailMessage = resultCode.getDetailMessage();
        this.data = data;
        this.env = env;
        this.session = session;
    }

    public RespResult(T data,String env,String session){
        this.data = data;
        this.env = env;
        this.session = session;
    }
    @JsonIgnore
    public boolean isOk(){
        return BaseRespResultCode.OK.getCode() == this.code;
    }
    @JsonIgnore
    public boolean isNotOK(){
        return !isOk();
    }
}

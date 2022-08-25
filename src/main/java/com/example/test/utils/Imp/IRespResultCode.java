package com.example.test.utils.Imp;

public interface IRespResultCode {
    /**
     * 结果码
     * @return
     */
    public Integer getCode();

    /**
     * 返回消息
     * @return
     */
    public String getMessage();

    /**
     * 详细错误信息（开发者）
     * @return
     */
    public String getDetailMessage();
}

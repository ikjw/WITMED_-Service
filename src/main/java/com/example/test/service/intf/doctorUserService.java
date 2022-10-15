package com.example.test.service.intf;

public interface doctorUserService {
    /**
     * 医患信息绑定
     * pre-condition:
     * uUID不为空
     * post-condition:
     * @param dUID 医生标识
     * @param uUID 患者标识
     * @return 0成功 1失败
     */
    int bind(String dUID,String uUID);
    /**
     * pre-condition:
     * at不为空
     * post-condition：
     *
     * @param  uUID
     * @return 1成功 0失败
     */
    int unbind(String uUID);
}

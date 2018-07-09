package com.linkhand.fenxiao.feng.login;

/**
 * 获取验证码的实体类
 */

public class Register {

    /**
     * code : 100
     * info : 8827
     * success : 发送成功
     */

    private int code;
    private String info;
    private String success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}

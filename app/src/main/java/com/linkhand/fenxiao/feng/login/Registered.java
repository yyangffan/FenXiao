package com.linkhand.fenxiao.feng.login;

/**
 * Created by 11860_000 on 2018/1/19.
 */

public class Registered {


    /**
     * code : 500
     * success : 邀请码不存在
     */

    private int code;
    private String success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Registered{" +
                "code=" + code +
                ", success='" + success + '\'' +
                '}';
    }
}

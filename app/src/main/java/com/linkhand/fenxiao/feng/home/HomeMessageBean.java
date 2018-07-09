package com.linkhand.fenxiao.feng.home;

/**
 * Created by 杨帆 on 2018/6/25.
 */

public class HomeMessageBean {

    /**
     * code : 200
     * info : 0
     * ar_count : 0
     * success : 无未查阅的信息
     */

    private int code;
    private String info;
    private String ar_count;
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

    public String getAr_count() {
        return ar_count;
    }

    public void setAr_count(String ar_count) {
        this.ar_count = ar_count;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}

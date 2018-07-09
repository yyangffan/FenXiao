package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by user on 2018/4/23.
 */

public class OrderInfoResponse {

    /**
     * code : 100
     * info : ["68","69"]
     * success : 订单添加成功
     */

    private int code;
    private String success;
    private List<String> info;

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

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}

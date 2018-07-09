package com.linkhand.fenxiao.feng.home;

/**
 * Created by user on 2018/4/26.
 */

public class VipXiaDanResponse {
    /**
     * code : 100
     * vip_order_id : 6
     * success : 返回成功
     */

    private int code;
    private String vip_order_id;
    private String success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVip_order_id() {
        return vip_order_id;
    }

    public void setVip_order_id(String vip_order_id) {
        this.vip_order_id = vip_order_id;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}

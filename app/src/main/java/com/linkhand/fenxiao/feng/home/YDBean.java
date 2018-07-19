package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 杨帆 on 2018/7/19.
 */

public class YDBean {

    /**
     * code : 100
     * info : ["/public/uploadCode/15-3-1527926719.png","/public/uploads/2018-07-19/c4fca5cbe6ffe6b40170c09e7219f2ce.jpg"]
     * success : 返回成功
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

package com.linkhand.fenxiao.bean;

import java.util.List;

/**
 * Created by user on 2018/5/7.
 */

public class InviteBean {

    /**
     * code : 100
     * info : ["/public/uploadCode/13-2-1528078669.png","/public/uploadCode/13-3-1528078670.png","/public/uploadCode/13-4-1528078670.png"]
     * inviter : OxJA
     * success : 返回成功
     */

    private String code;
    private String inviter;
    private String success;
    private List<String> info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
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

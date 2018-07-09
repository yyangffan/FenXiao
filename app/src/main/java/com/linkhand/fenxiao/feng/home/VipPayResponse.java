package com.linkhand.fenxiao.feng.home;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018/5/25.
 */

public class VipPayResponse {

    /**
     * code : 100
     * info_zfb :
     * info_wx : {"appid":"wx0e520cf074c1db30","noncestr":"jv45t0z0z26a87dq607dyb78t2fy6eud","package":"Sign=WXPay","prepayid":"wx251628306276045df12995102812884420","partnerid":"1504402841","timestamp":"1527236910","sign":"2EF8B37D4E28B15651C26D0E25BB27D5"}
     * success : 支付成功
     */

    private int code;
    private String info_zfb;
    private InfoWxBean info_wx;
    private String success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo_zfb() {
        return info_zfb;
    }

    public void setInfo_zfb(String info_zfb) {
        this.info_zfb = info_zfb;
    }

    public InfoWxBean getInfo_wx() {
        return info_wx;
    }

    public void setInfo_wx(InfoWxBean info_wx) {
        this.info_wx = info_wx;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public static class InfoWxBean {
        /**
         * appid : wx0e520cf074c1db30
         * noncestr : jv45t0z0z26a87dq607dyb78t2fy6eud
         * package : Sign=WXPay
         * prepayid : wx251628306276045df12995102812884420
         * partnerid : 1504402841
         * timestamp : 1527236910
         * sign : 2EF8B37D4E28B15651C26D0E25BB27D5
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String prepayid;
        private String partnerid;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}

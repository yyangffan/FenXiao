package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/1/13.
 */

public class NewRecommendedVipGoods {


    /**
     * code : 100
     * success : 获取成功
     * info : [{"vip_id":"1","vip_name":"名字","vip_money":"123.00","vip_desc":"阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿","vip_intro":"阿发沙发沙发沙 ","vip_is_top ":"1 ","vip_add_time ":"","vip_up_time ":"","vimg_url ":"3e b94ea.jpg ","vip_num ":0}]
     */

    private int code;
    private String success;
    private List<InfoBean> info;

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

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * vip_id : 1
         * vip_name : 名字
         * vip_money : 123.00
         * vip_desc : 阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿
         * vip_intro : 阿发沙发沙发沙
         * vip_is_top  : 1
         * vip_add_time  :
         * vip_up_time  :
         * vimg_url  : 3e b94ea.jpg
         * vip_num  : 0
         */

        private String vip_id;
        private String vip_name;
        private String vip_money;
        private String vip_desc;
        private String vip_intro;
        private String vip_is_top;
        private String vip_add_time;
        private String vip_up_time;
        private String vimg_url;
        private int vip_num;

        public String getVip_id() {
            return vip_id;
        }

        public void setVip_id(String vip_id) {
            this.vip_id = vip_id;
        }

        public String getVip_name() {
            return vip_name;
        }

        public void setVip_name(String vip_name) {
            this.vip_name = vip_name;
        }

        public String getVip_money() {
            return vip_money;
        }

        public void setVip_money(String vip_money) {
            this.vip_money = vip_money;
        }

        public String getVip_desc() {
            return vip_desc;
        }

        public void setVip_desc(String vip_desc) {
            this.vip_desc = vip_desc;
        }

        public String getVip_intro() {
            return vip_intro;
        }

        public void setVip_intro(String vip_intro) {
            this.vip_intro = vip_intro;
        }

        public String getVip_is_top() {
            return vip_is_top;
        }

        public void setVip_is_top(String vip_is_top) {
            this.vip_is_top = vip_is_top;
        }

        public String getVip_add_time() {
            return vip_add_time;
        }

        public void setVip_add_time(String vip_add_time) {
            this.vip_add_time = vip_add_time;
        }

        public String getVip_up_time() {
            return vip_up_time;
        }

        public void setVip_up_time(String vip_up_time) {
            this.vip_up_time = vip_up_time;
        }

        public String getVimg_url() {
            return vimg_url;
        }

        public void setVimg_url(String vimg_url) {
            this.vimg_url = vimg_url;
        }

        public int getVip_num() {
            return vip_num;
        }

        public void setVip_num(int vip_num) {
            this.vip_num = vip_num;
        }
    }
}

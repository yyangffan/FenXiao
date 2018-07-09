package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/3/7.
 */

public class GroupListFeng {

    /**
     * code : 100
     * success : 返回成功
     * info : [{"good_id":"10","good_name":"开小差休闲零食大礼包美式球型玉米爆米花袋装","good_type":"1","img_url":"/public/uploads/20180420/daeb0163449aba426bfbd15286438011.jpg","money_mater":"20.00","money_son":"33.00","all_num":16}]
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
         * good_id : 10
         * good_name : 开小差休闲零食大礼包美式球型玉米爆米花袋装
         * good_type : 1
         * img_url : /public/uploads/20180420/daeb0163449aba426bfbd15286438011.jpg
         * money_mater : 20.00
         * money_son : 33.00
         * all_num : 16
         */

        private String good_id;
        private String good_name;
        private String good_type;
        private String img_url;
        private String money_mater;
        private String money_son;
        private int all_num;

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public String getGood_name() {
            return good_name;
        }

        public void setGood_name(String good_name) {
            this.good_name = good_name;
        }

        public String getGood_type() {
            return good_type;
        }

        public void setGood_type(String good_type) {
            this.good_type = good_type;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getMoney_mater() {
            return money_mater;
        }

        public void setMoney_mater(String money_mater) {
            this.money_mater = money_mater;
        }

        public String getMoney_son() {
            return money_son;
        }

        public void setMoney_son(String money_son) {
            this.money_son = money_son;
        }

        public int getAll_num() {
            return all_num;
        }

        public void setAll_num(int all_num) {
            this.all_num = all_num;
        }
    }
}

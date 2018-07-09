package com.linkhand.fenxiao.feng.mine;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/27.
 */

public class CollectionFeng {


    /**
     * code : 100
     * success : 返回成功
     * info : [{"house_id":"21","good_id":"4","good_name":"商品1","good_is_top":"1","img_url":"/public/uploads/20171227/a6220b4ef6f7acdd4bf1431729d91dae.jpg","good_mater_money":"10.00","good_son_money":"10.00"},{"house_id":"22","good_id":"7","good_name":"阶梯价格商品","good_is_top":"1","img_url":"/public/uploads/20180125/684a45f65543e44a3660dfd83e1359e2.jpg","good_mater_money":"100.00","good_son_money":"100.00"}]
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
         * house_id : 21
         * good_id : 4
         * good_name : 商品1
         * good_is_top : 1
         * img_url : /public/uploads/20171227/a6220b4ef6f7acdd4bf1431729d91dae.jpg
         * good_mater_money : 10.00
         * good_son_money : 10.00
         */

        private String house_id;
        private String good_id;
        private String good_name;
        private String good_is_top;
        private String img_url;
        private String good_mater_money;
        private String good_son_money;

        public String getHouse_id() {
            return house_id;
        }

        public void setHouse_id(String house_id) {
            this.house_id = house_id;
        }

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

        public String getGood_is_top() {
            return good_is_top;
        }

        public void setGood_is_top(String good_is_top) {
            this.good_is_top = good_is_top;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getGood_mater_money() {
            return good_mater_money;
        }

        public void setGood_mater_money(String good_mater_money) {
            this.good_mater_money = good_mater_money;
        }

        public String getGood_son_money() {
            return good_son_money;
        }

        public void setGood_son_money(String good_son_money) {
            this.good_son_money = good_son_money;
        }
    }
}

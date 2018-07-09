package com.linkhand.fenxiao.feng.mine;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/28.
 */

public class ShoppingCartListFeng {


    /**
     * code : 100
     * info : [{"cart_id":"28","good_id":"4","user_id":"1","speci_ids":"1,3","cart_mater":"300.00","cart_son":"300.00","cart_num":"10","cart_add_time":"1520990052","cart_state":"1","good_name":"商品1","img_url":"/public/uploads/20171227/a6220b4ef6f7acdd4bf1431729d91dae.jpg","good_state":1,"speci":[{"gsp_value":"蓝色色色色色色色色色","gsp_id":"1","speci_name":"颜色"},{"gsp_value":"100","gsp_id":"3","speci_name":"尺寸"}]},{"cart_id":"29","good_id":"7","user_id":"1","speci_ids":"47,50","cart_mater":"110.00","cart_son":"110.00","cart_num":"1","cart_add_time":"1520931780","cart_state":"1","good_name":"阶梯价格商品","img_url":"/public/uploads/20180125/684a45f65543e44a3660dfd83e1359e2.jpg","good_state":1,"speci":[{"gsp_value":"紫色","gsp_id":"47","speci_name":"颜色"},{"gsp_value":"大","gsp_id":"50","speci_name":"大小"}]}]
     * allmater : 410
     * allson : 410
     */

    private int code;
    private int allmater;
    private int allson;
    private List<InfoBean> info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getAllmater() {
        return allmater;
    }

    public void setAllmater(int allmater) {
        this.allmater = allmater;
    }

    public int getAllson() {
        return allson;
    }

    public void setAllson(int allson) {
        this.allson = allson;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * cart_id : 28
         * good_id : 4
         * user_id : 1
         * speci_ids : 1,3
         * cart_mater : 300.00
         * cart_son : 300.00
         * cart_num : 10
         * cart_add_time : 1520990052
         * cart_state : 1
         * good_name : 商品1
         * img_url : /public/uploads/20171227/a6220b4ef6f7acdd4bf1431729d91dae.jpg
         * good_state : 1
         * speci : [{"gsp_value":"蓝色色色色色色色色色","gsp_id":"1","speci_name":"颜色"},{"gsp_value":"100","gsp_id":"3","speci_name":"尺寸"}]
         */

        private String cart_id;
        private String good_id;
        private String user_id;
        private String speci_ids;
        private String cart_mater;
        private String cart_son;
        private String cart_num;
        private String cart_add_time;
        private String cart_state;
        private String good_name;
        private String img_url;
        private int good_state;
        private List<SpeciBean> speci;

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSpeci_ids() {
            return speci_ids;
        }

        public void setSpeci_ids(String speci_ids) {
            this.speci_ids = speci_ids;
        }

        public String getCart_mater() {
            return cart_mater;
        }

        public void setCart_mater(String cart_mater) {
            this.cart_mater = cart_mater;
        }

        public String getCart_son() {
            return cart_son;
        }

        public void setCart_son(String cart_son) {
            this.cart_son = cart_son;
        }

        public String getCart_num() {
            return cart_num;
        }

        public void setCart_num(String cart_num) {
            this.cart_num = cart_num;
        }

        public String getCart_add_time() {
            return cart_add_time;
        }

        public void setCart_add_time(String cart_add_time) {
            this.cart_add_time = cart_add_time;
        }

        public String getCart_state() {
            return cart_state;
        }

        public void setCart_state(String cart_state) {
            this.cart_state = cart_state;
        }

        public String getGood_name() {
            return good_name;
        }

        public void setGood_name(String good_name) {
            this.good_name = good_name;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public int getGood_state() {
            return good_state;
        }

        public void setGood_state(int good_state) {
            this.good_state = good_state;
        }

        public List<SpeciBean> getSpeci() {
            return speci;
        }

        public void setSpeci(List<SpeciBean> speci) {
            this.speci = speci;
        }

        public static class SpeciBean {
            /**
             * gsp_value : 蓝色色色色色色色色色
             * gsp_id : 1
             * speci_name : 颜色
             */

            private String gsp_value;
            private String gsp_id;
            private String speci_name;

            public String getGsp_value() {
                return gsp_value;
            }

            public void setGsp_value(String gsp_value) {
                this.gsp_value = gsp_value;
            }

            public String getGsp_id() {
                return gsp_id;
            }

            public void setGsp_id(String gsp_id) {
                this.gsp_id = gsp_id;
            }

            public String getSpeci_name() {
                return speci_name;
            }

            public void setSpeci_name(String speci_name) {
                this.speci_name = speci_name;
            }
        }
    }
}

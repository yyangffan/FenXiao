package com.linkhand.fenxiao.feng.mine;

import java.util.List;

/**
 * Created by 杨帆 on 2018/6/22.
 */

public class ShoppingCartListNewFeng {

    /**
     * code : 100
     * info : [{"cart_id":"145","good_id":"39","user_id":"22","speci_ids":"133","cart_mater":"300.00","cart_son":"300.00","cart_num":"1","cart_add_time":"1529654121","cart_state":"1","good_name":"t恤男短袖圆领宽松夏季休闲纯棉纯色潮流百搭白打底衫青年黑体恤","good_is_top":"1","img_url":"/public/uploads/2018-06-20/4beb34767a9817bae9aea66fb102fe33.jpg","good_state":1,"speci":[{"gsp_value":"红色","gsp_id":"133","speci_name":"颜色"}]},{"cart_id":"144","good_id":"39","user_id":"22","speci_ids":"131","cart_mater":"310.00","cart_son":"310.00","cart_num":"1","cart_add_time":"1529654119","cart_state":"1","good_name":"t恤男短袖圆领宽松夏季休闲纯棉纯色潮流百搭白打底衫青年黑体恤","good_is_top":"1","img_url":"/public/uploads/2018-06-20/4beb34767a9817bae9aea66fb102fe33.jpg","good_state":1,"speci":[{"gsp_value":"蓝色","gsp_id":"131","speci_name":"颜色"}]},{"cart_id":"143","good_id":"24","user_id":"22","speci_ids":"134","cart_mater":"30.00","cart_son":"35.00","cart_num":"1","cart_add_time":"1529654095","cart_state":"1","good_name":"团购商品1号","good_is_top":"1","img_url":"/public/uploads/2018-06-15/6d0325c7a91d56b40131466d95ec8a66.jpg","good_state":1,"speci":[{"gsp_value":"大包","gsp_id":"134","speci_name":"大小"}]}]
     * not_cart : [{"cart_id":"140","good_id":"24","user_id":"22","speci_ids":"129","cart_mater":"30.00","cart_son":"35.00","cart_num":"1","cart_add_time":"1529653844","cart_state":"1","good_name":"团购商品1号","good_is_top":"1","img_url":"/public/uploads/2018-06-15/6d0325c7a91d56b40131466d95ec8a66.jpg"},{"cart_id":"122","good_id":"24","user_id":"22","speci_ids":"106","cart_mater":"30.00","cart_son":"35.00","cart_num":"1","cart_add_time":"1529466959","cart_state":"1","good_name":"团购商品1号","good_is_top":"1","img_url":"/public/uploads/2018-06-15/6d0325c7a91d56b40131466d95ec8a66.jpg"},{"cart_id":"121","good_id":"24","user_id":"22","speci_ids":"107","cart_mater":"40.00","cart_son":"50.00","cart_num":"2","cart_add_time":"1529466955","cart_state":"1","good_name":"团购商品1号","good_is_top":"1","img_url":"/public/uploads/2018-06-15/6d0325c7a91d56b40131466d95ec8a66.jpg"}]
     * cart_count : 3
     * allmater : 640
     * allson : 645
     */

    private int code;
    private int cart_count;
    private String allmater;
    private String  allson;
    private List<InfoBean> info;
    private List<NotCartBean> not_cart;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCart_count() {
        return cart_count;
    }

    public void setCart_count(int cart_count) {
        this.cart_count = cart_count;
    }

    public String getAllmater() {
        return allmater;
    }

    public void setAllmater(String allmater) {
        this.allmater = allmater;
    }

    public String getAllson() {
        return allson;
    }

    public void setAllson(String allson) {
        this.allson = allson;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public List<NotCartBean> getNot_cart() {
        return not_cart;
    }

    public void setNot_cart(List<NotCartBean> not_cart) {
        this.not_cart = not_cart;
    }

    public static class InfoBean {
        public InfoBean(String cart_id, String good_id, String user_id, String speci_ids, String cart_mater,
                        String cart_son, String cart_num, String cart_add_time, String cart_state, String good_name, String good_is_top, String img_url, int good_state,List<SpeciBean> speci) {
            this.cart_id = cart_id;
            this.good_id = good_id;
            this.user_id = user_id;
            this.speci_ids = speci_ids;
            this.cart_mater = cart_mater;
            this.cart_son = cart_son;
            this.cart_num = cart_num;
            this.cart_add_time = cart_add_time;
            this.cart_state = cart_state;
            this.good_name = good_name;
            this.good_is_top = good_is_top;
            this.img_url = img_url;
            this.good_state = good_state;
            this.speci=speci;
        }

        /**
         * cart_id : 145
         * good_id : 39
         * user_id : 22
         * speci_ids : 133
         * cart_mater : 300.00
         * cart_son : 300.00
         * cart_num : 1
         * cart_add_time : 1529654121
         * cart_state : 1
         * good_name : t恤男短袖圆领宽松夏季休闲纯棉纯色潮流百搭白打底衫青年黑体恤
         * good_is_top : 1
         * img_url : /public/uploads/2018-06-20/4beb34767a9817bae9aea66fb102fe33.jpg
         * good_state : 1
         * speci : [{"gsp_value":"红色","gsp_id":"133","speci_name":"颜色"}]
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
        private String good_is_top;
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
             * gsp_value : 红色
             * gsp_id : 133
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

    public static class NotCartBean {
        /**
         * cart_id : 140
         * good_id : 24
         * user_id : 22
         * speci_ids : 129
         * cart_mater : 30.00
         * cart_son : 35.00
         * cart_num : 1
         * cart_add_time : 1529653844
         * cart_state : 1
         * good_name : 团购商品1号
         * good_is_top : 1
         * img_url : /public/uploads/2018-06-15/6d0325c7a91d56b40131466d95ec8a66.jpg
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
        private String good_is_top;
        private String img_url;
        private List<InfoBean.SpeciBean> speci;

        public List<InfoBean.SpeciBean> getSpeci() {
            return speci;
        }

        public void setSpeci(List<InfoBean.SpeciBean> speci) {
            this.speci = speci;
        }

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
    }
}

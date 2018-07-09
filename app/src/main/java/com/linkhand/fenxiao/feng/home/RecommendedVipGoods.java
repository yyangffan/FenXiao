package com.linkhand.fenxiao.feng.home;

/**
 * Created by 11860_000 on 2018/1/13.
 */

public class RecommendedVipGoods {

    /**
     * code : 100
     * info : {"vip_name":"vip商品11213","vip_money":"4991","vip_img":"/public/uploads/20180105/7607fc8918e6270dbd597e47f34d763e.png","vip_mater":"1201","vip_son":"1001","vip_desc":"哈哈哈哈阿斯达啊沙发上","have_vip":"1","vip_num":40}
     */

    private int code;
    private InfoBean info;
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }



    public static class InfoBean {
        /**
         * vip_name : vip商品11213
         * vip_money : 4991
         * vip_img : /public/uploads/20180105/7607fc8918e6270dbd597e47f34d763e.png
         * vip_mater : 1201
         * vip_son : 1001
         * vip_desc : 哈哈哈哈阿斯达啊沙发上
         * have_vip : 1
         * vip_num : 40
         */

        private String vip_name;
        private String vip_money;
        private String vip_img;
        private String vip_mater;
        private String vip_son;
        private String vip_desc;
        private String have_vip;
        private int vip_num;

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

        public String getVip_img() {
            return vip_img;
        }

        public void setVip_img(String vip_img) {
            this.vip_img = vip_img;
        }

        public String getVip_mater() {
            return vip_mater;
        }

        public void setVip_mater(String vip_mater) {
            this.vip_mater = vip_mater;
        }

        public String getVip_son() {
            return vip_son;
        }

        public void setVip_son(String vip_son) {
            this.vip_son = vip_son;
        }

        public String getVip_desc() {
            return vip_desc;
        }

        public void setVip_desc(String vip_desc) {
            this.vip_desc = vip_desc;
        }

        public String getHave_vip() {
            return have_vip;
        }

        public void setHave_vip(String have_vip) {
            this.have_vip = have_vip;
        }

        public int getVip_num() {
            return vip_num;
        }

        public void setVip_num(int vip_num) {
            this.vip_num = vip_num;
        }

        @Override
        public String toString() {
            return "InfoBean{" +
                    "vip_name='" + vip_name + '\'' +
                    ", vip_money='" + vip_money + '\'' +
                    ", vip_img='" + vip_img + '\'' +
                    ", vip_mater='" + vip_mater + '\'' +
                    ", vip_son='" + vip_son + '\'' +
                    ", vip_desc='" + vip_desc + '\'' +
                    ", have_vip='" + have_vip + '\'' +
                    ", vip_num=" + vip_num +
                    '}';
        }
    }
}

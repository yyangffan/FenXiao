package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by user on 2018/4/26.
 */

public class VipDetailResponse {

    /**
     * code : 100
     * info : {"vip_order_id":6,"vip_good_id":15,"vip_good_name":"夏天韩版运动帽男士棒球帽速干鸭舌帽单款男帽子新春夏季帽子 男式","vip_good_val":"75","vip_order_money":"27.90","vimg_url":"/public/uploads/20180420/c5c558ad70eebafcb28dedf1a6267e25.jpg","speci":[{"vsp_id":75,"speci_name":"颜色","vsp_value":"黑色"}]}
     * success : 订单返回成功
     */

    private int code;
    private InfoBean info;
    private String success;

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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public static class InfoBean {
        /**
         * vip_order_id : 6
         * vip_good_id : 15
         * vip_good_name : 夏天韩版运动帽男士棒球帽速干鸭舌帽单款男帽子新春夏季帽子 男式
         * vip_good_val : 75
         * vip_order_money : 27.90
         * vimg_url : /public/uploads/20180420/c5c558ad70eebafcb28dedf1a6267e25.jpg
         * speci : [{"vsp_id":75,"speci_name":"颜色","vsp_value":"黑色"}]
         */

        private String vip_order_id;
        private String vip_good_id;
        private String vip_good_name;
        private String vip_good_val;
        private String vip_order_money;
        private String vimg_url;
        private List<SpeciBean> speci;

        public String getVip_order_id() {
            return vip_order_id;
        }

        public void setVip_order_id(String vip_order_id) {
            this.vip_order_id = vip_order_id;
        }

        public String getVip_good_id() {
            return vip_good_id;
        }

        public void setVip_good_id(String vip_good_id) {
            this.vip_good_id = vip_good_id;
        }

        public String getVip_good_name() {
            return vip_good_name;
        }

        public void setVip_good_name(String vip_good_name) {
            this.vip_good_name = vip_good_name;
        }

        public String getVip_good_val() {
            return vip_good_val;
        }

        public void setVip_good_val(String vip_good_val) {
            this.vip_good_val = vip_good_val;
        }

        public String getVip_order_money() {
            return vip_order_money;
        }

        public void setVip_order_money(String vip_order_money) {
            this.vip_order_money = vip_order_money;
        }

        public String getVimg_url() {
            return vimg_url;
        }

        public void setVimg_url(String vimg_url) {
            this.vimg_url = vimg_url;
        }

        public List<SpeciBean> getSpeci() {
            return speci;
        }

        public void setSpeci(List<SpeciBean> speci) {
            this.speci = speci;
        }

        public static class SpeciBean {
            /**
             * vsp_id : 75
             * speci_name : 颜色
             * vsp_value : 黑色
             */

            private int vsp_id;
            private String speci_name;
            private String vsp_value;

            public int getVsp_id() {
                return vsp_id;
            }

            public void setVsp_id(int vsp_id) {
                this.vsp_id = vsp_id;
            }

            public String getSpeci_name() {
                return speci_name;
            }

            public void setSpeci_name(String speci_name) {
                this.speci_name = speci_name;
            }

            public String getVsp_value() {
                return vsp_value;
            }

            public void setVsp_value(String vsp_value) {
                this.vsp_value = vsp_value;
            }
        }
    }
}

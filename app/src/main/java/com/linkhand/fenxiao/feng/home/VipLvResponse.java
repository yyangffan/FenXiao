package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by user on 2018/5/15.
 */

public class VipLvResponse {

    /**
     * code : 100
     * info : {"vip_order_id":124,"vip_good_id":15,"vip_good_name":"夏天韩版运动帽男士棒球帽速干鸭舌帽单款男帽子新春夏季帽子 男式","vip_good_val":"81","vip_order_money":"27.90","vip_order_status":2,"status_str":"待发货","vimg_url":"/public/uploads/2018-07-02/6b8499522dd5d007980f5216fd42d6ce.jpg","speci":[{"vsp_id":81,"speci_name":"颜色","vsp_value":"军绿色"}]}
     * usedata : [{"uc_id":1,"use_id":3,"uc_city1":6,"uc_city2":59,"uc_city3":729,"uc_detail":"aSDVD","uc_num":12,"uc_pwd":"12421","uc_down_time":"","use_name":"卡券2","use_money":"100.00","use_img":"/public/uploads/2018-07-02/1779bdb46505786addf9e415b4ea651b.jpg","use_down_time":"1530516737","uh_num":3,"num_state":"剩余9次","city_str":"内蒙古自治区-呼和浩特市-回民区"},{"uc_id":2,"use_id":3,"uc_city1":5,"uc_city2":50,"uc_city3":622,"uc_detail":"阿尔法狗火锅味 ","uc_num":14,"uc_pwd":"23231","uc_down_time":"","use_name":"卡券2","use_money":"100.00","use_img":"/public/uploads/2018-07-02/1779bdb46505786addf9e415b4ea651b.jpg","use_down_time":"1530516737","uh_num":"","num_state":"剩余14次","city_str":"山西省-阳泉市-矿区"},{"uc_id":3,"use_id":5,"uc_city1":4,"uc_city2":37,"uc_city3":416,"uc_detail":"十多个法生产","uc_num":11,"uc_pwd":"12412","uc_down_time":"","use_name":"卡券5","use_money":"30.00","use_img":"/public/uploads/2018-07-03/42d59da67c09e32c0ede15bc21fec60f.jpg","use_down_time":"1532707200","uh_num":"","num_state":"剩余11次","city_str":"河北省-石家庄市-桥东区"},{"uc_id":4,"use_id":5,"uc_city1":4,"uc_city2":37,"uc_city3":434,"uc_detail":"十多个法生产asvasfasdasdas","uc_num":11,"uc_pwd":"12412","uc_down_time":"","use_name":"卡券5","use_money":"30.00","use_img":"/public/uploads/2018-07-03/42d59da67c09e32c0ede15bc21fec60f.jpg","use_down_time":"1532707200","uh_num":"","num_state":"剩余11次","city_str":"河北省-石家庄市-藁城市"},{"uc_id":5,"use_id":5,"uc_city1":4,"uc_city2":44,"uc_city3":547,"uc_detail":"qqqqqqqqqqqqqqqqqqqqqqq","uc_num":11,"uc_pwd":"12412asdf","uc_down_time":"","use_name":"卡券5","use_money":"30.00","use_img":"/public/uploads/2018-07-03/42d59da67c09e32c0ede15bc21fec60f.jpg","use_down_time":"1532707200","uh_num":"","num_state":"剩余11次","city_str":"河北省-承德市-双滦区"}]
     * success : 订单返回成功
     */

    private int code;
    private InfoBean info;
    private String success;
    private List<UsedataBean> usedata;

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

    public List<UsedataBean> getUsedata() {
        return usedata;
    }

    public void setUsedata(List<UsedataBean> usedata) {
        this.usedata = usedata;
    }

    public static class InfoBean {
        /**
         * vip_order_id : 124
         * vip_good_id : 15
         * vip_good_name : 夏天韩版运动帽男士棒球帽速干鸭舌帽单款男帽子新春夏季帽子 男式
         * vip_good_val : 81
         * vip_order_money : 27.90
         * vip_order_status : 2
         * status_str : 待发货
         * vimg_url : /public/uploads/2018-07-02/6b8499522dd5d007980f5216fd42d6ce.jpg
         * speci : [{"vsp_id":81,"speci_name":"颜色","vsp_value":"军绿色"}]
         */

        private int vip_order_id;
        private int vip_good_id;
        private String vip_good_name;
        private String vip_good_val;
        private String vip_order_money;
        private int vip_order_status;
        private String status_str;
        private String vimg_url;
        private List<SpeciBean> speci;

        public int getVip_order_id() {
            return vip_order_id;
        }

        public void setVip_order_id(int vip_order_id) {
            this.vip_order_id = vip_order_id;
        }

        public int getVip_good_id() {
            return vip_good_id;
        }

        public void setVip_good_id(int vip_good_id) {
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

        public int getVip_order_status() {
            return vip_order_status;
        }

        public void setVip_order_status(int vip_order_status) {
            this.vip_order_status = vip_order_status;
        }

        public String getStatus_str() {
            return status_str;
        }

        public void setStatus_str(String status_str) {
            this.status_str = status_str;
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
             * vsp_id : 81
             * speci_name : 颜色
             * vsp_value : 军绿色
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

    public static class UsedataBean {
        /**
         * uc_id : 1
         * use_id : 3
         * uc_city1 : 6
         * uc_city2 : 59
         * uc_city3 : 729
         * uc_detail : aSDVD
         * uc_num : 12
         * uc_pwd : 12421
         * uc_down_time :
         * use_name : 卡券2
         * use_money : 100.00
         * use_img : /public/uploads/2018-07-02/1779bdb46505786addf9e415b4ea651b.jpg
         * use_down_time : 1530516737
         * uh_num : 3
         * num_state : 剩余9次
         * city_str : 内蒙古自治区-呼和浩特市-回民区
         */

        private String uc_id;
        private String use_id;
        private String uc_city1;
        private String uc_city2;
        private String uc_city3;
        private String uc_detail;
        private String uc_num;
        private String uc_pwd;
        private String uc_down_time;
        private String use_name;
        private String use_money;
        private String use_img;
        private String use_down_time;
        private String uh_num;
        private String num_state;
        private String city_str;

        public UsedataBean(String uc_id, String use_name, String use_money, String num_state, String city_str,String use_img) {
            this.uc_id = uc_id;
            this.use_name = use_name;
            this.use_money = use_money;
            this.num_state = num_state;
            this.city_str = city_str;
            this.use_img = use_img;
        }

        public String getUc_id() {
            return uc_id;
        }

        public void setUc_id(String uc_id) {
            this.uc_id = uc_id;
        }

        public String getUse_id() {
            return use_id;
        }

        public void setUse_id(String use_id) {
            this.use_id = use_id;
        }

        public String getUc_city1() {
            return uc_city1;
        }

        public void setUc_city1(String uc_city1) {
            this.uc_city1 = uc_city1;
        }

        public String getUc_city2() {
            return uc_city2;
        }

        public void setUc_city2(String uc_city2) {
            this.uc_city2 = uc_city2;
        }

        public String getUc_city3() {
            return uc_city3;
        }

        public void setUc_city3(String uc_city3) {
            this.uc_city3 = uc_city3;
        }

        public String getUc_detail() {
            return uc_detail;
        }

        public void setUc_detail(String uc_detail) {
            this.uc_detail = uc_detail;
        }

        public String getUc_num() {
            return uc_num;
        }

        public void setUc_num(String uc_num) {
            this.uc_num = uc_num;
        }

        public String getUc_pwd() {
            return uc_pwd;
        }

        public void setUc_pwd(String uc_pwd) {
            this.uc_pwd = uc_pwd;
        }

        public String getUc_down_time() {
            return uc_down_time;
        }

        public void setUc_down_time(String uc_down_time) {
            this.uc_down_time = uc_down_time;
        }

        public String getUse_name() {
            return use_name;
        }

        public void setUse_name(String use_name) {
            this.use_name = use_name;
        }

        public String getUse_money() {
            return use_money;
        }

        public void setUse_money(String use_money) {
            this.use_money = use_money;
        }

        public String getUse_img() {
            return use_img;
        }

        public void setUse_img(String use_img) {
            this.use_img = use_img;
        }

        public String getUse_down_time() {
            return use_down_time;
        }

        public void setUse_down_time(String use_down_time) {
            this.use_down_time = use_down_time;
        }

        public String getUh_num() {
            return uh_num;
        }

        public void setUh_num(String uh_num) {
            this.uh_num = uh_num;
        }

        public String getNum_state() {
            return num_state;
        }

        public void setNum_state(String num_state) {
            this.num_state = num_state;
        }

        public String getCity_str() {
            return city_str;
        }

        public void setCity_str(String city_str) {
            this.city_str = city_str;
        }
    }
}

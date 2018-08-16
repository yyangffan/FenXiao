package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 杨帆 on 2018/8/11.
 */

public class UsedKqBean {

    /**
     * code : 100
     * info : [{"uc_id":"8","use_img":"/public/uploads/2018-07-26/867f8c04551174036cf37a34a8f73f04.jpg","use_name":"卡券3","use_money":"200.00","use_down_time":"1532577921","uc_city1":"4","uc_city2":"41","uc_city3":"485","uc_num":"4","uh_num":"4","city_str":"河北省-邢台市-临城县","num_state":"已使用4张,暂无剩余券"},{"uc_id":"7","use_img":"/public/uploads/2018-07-26/307fe2aa45f0a889bf5d48609fbdd017.jpg","use_name":"卡券2","use_money":"100.00","use_down_time":"1532574616","uc_city1":"4","uc_city2":"41","uc_city3":"485","uc_num":"12","uh_num":"2","city_str":"河北省-邢台市-临城县","num_state":"已使用2张,剩余10张"}]
     * success : 参数丢失
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
         * uc_id : 8
         * use_img : /public/uploads/2018-07-26/867f8c04551174036cf37a34a8f73f04.jpg
         * use_name : 卡券3
         * use_money : 200.00
         * use_down_time : 1532577921
         * uc_city1 : 4
         * uc_city2 : 41
         * uc_city3 : 485
         * uc_num : 4
         * uh_num : 4
         * city_str : 河北省-邢台市-临城县
         * num_state : 已使用4张,暂无剩余券
         */

        private String uc_id;
        private String use_img;
        private String use_name;
        private String use_money;
        private String use_down_time;
        private String uc_city1;
        private String uc_city2;
        private String uc_city3;
        private String uc_num;
        private String uh_num;
        private String city_str;
        private String num_state;

        public String getUc_id() {
            return uc_id;
        }

        public void setUc_id(String uc_id) {
            this.uc_id = uc_id;
        }

        public String getUse_img() {
            return use_img;
        }

        public void setUse_img(String use_img) {
            this.use_img = use_img;
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

        public String getUse_down_time() {
            return use_down_time;
        }

        public void setUse_down_time(String use_down_time) {
            this.use_down_time = use_down_time;
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

        public String getUc_num() {
            return uc_num;
        }

        public void setUc_num(String uc_num) {
            this.uc_num = uc_num;
        }

        public String getUh_num() {
            return uh_num;
        }

        public void setUh_num(String uh_num) {
            this.uh_num = uh_num;
        }

        public String getCity_str() {
            return city_str;
        }

        public void setCity_str(String city_str) {
            this.city_str = city_str;
        }

        public String getNum_state() {
            return num_state;
        }

        public void setNum_state(String num_state) {
            this.num_state = num_state;
        }
    }
}

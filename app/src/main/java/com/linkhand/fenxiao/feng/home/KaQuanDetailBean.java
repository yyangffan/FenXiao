package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 杨帆 on 2018/7/4.
 */

public class KaQuanDetailBean {

    /**
     * code : 100
     * success : 数据返回
     * info : {"uc_id":"1","use_id":"3","uc_city1":"6","uc_city2":"59","uc_city3":"729","uc_detail":"aSDVD","uc_num":"12","uc_pwd":"12421","uc_down_time":"","use_name":"卡券2","use_money":"100.00","use_img":["/public/uploads/2018-07-02/1779bdb46505786addf9e415b4ea651b.jpg","/public/uploads/2018-07-02/a7a35d569e53ae99316031215bf6adb3.jpg","/public/uploads/2018-07-02/d537e2930c4d61e72ca85859e3a679a1.jpg","/public/uploads/2018-07-02/c93c1688d2ecb6a869e5ce574d52e069.jpg"],"use_content":"<p><img src=\"http://sjfx.linghangnc.com/ueditor/php/upload/image/20180702/1530515726125786.jpg\" title=\"1530515726125786.jpg\"/><\/p><p><img src=\"http://sjfx.linghangnc.com/ueditor/php/upload/image/20180702/1530515726548267.jpg\" title=\"1530515726548267.jpg\"/><\/p><p><img src=\"http://sjfx.linghangnc.com/ueditor/php/upload/image/20180702/1530515726252596.jpg\" title=\"1530515726252596.jpg\"/><\/p><p><img src=\"http://sjfx.linghangnc.com/ueditor/php/upload/image/20180702/1530515726410394.jpg\" title=\"1530515726410394.jpg\"/><\/p><p><br/><\/p>","use_down_time":"1530516737","city_str":"内蒙古自治区-呼和浩特市-回民区","num_state":"剩余9次"}
     */

    private int code;
    private String success;
    private InfoBean info;

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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
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
         * use_img : ["/public/uploads/2018-07-02/1779bdb46505786addf9e415b4ea651b.jpg","/public/uploads/2018-07-02/a7a35d569e53ae99316031215bf6adb3.jpg","/public/uploads/2018-07-02/d537e2930c4d61e72ca85859e3a679a1.jpg","/public/uploads/2018-07-02/c93c1688d2ecb6a869e5ce574d52e069.jpg"]
         * use_content : <p><img src="http://sjfx.linghangnc.com/ueditor/php/upload/image/20180702/1530515726125786.jpg" title="1530515726125786.jpg"/></p><p><img src="http://sjfx.linghangnc.com/ueditor/php/upload/image/20180702/1530515726548267.jpg" title="1530515726548267.jpg"/></p><p><img src="http://sjfx.linghangnc.com/ueditor/php/upload/image/20180702/1530515726252596.jpg" title="1530515726252596.jpg"/></p><p><img src="http://sjfx.linghangnc.com/ueditor/php/upload/image/20180702/1530515726410394.jpg" title="1530515726410394.jpg"/></p><p><br/></p>
         * use_down_time : 1530516737
         * city_str : 内蒙古自治区-呼和浩特市-回民区
         * num_state : 剩余9次
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
        private String use_content;
        private String use_down_time;
        private String city_str;
        private String num_state;
        private List<String> use_img;

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

        public String getUse_content() {
            return use_content;
        }

        public void setUse_content(String use_content) {
            this.use_content = use_content;
        }

        public String getUse_down_time() {
            return use_down_time;
        }

        public void setUse_down_time(String use_down_time) {
            this.use_down_time = use_down_time;
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

        public List<String> getUse_img() {
            return use_img;
        }

        public void setUse_img(List<String> use_img) {
            this.use_img = use_img;
        }
    }
}

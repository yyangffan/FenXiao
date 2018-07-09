package com.linkhand.fenxiao.feng.home;

/**
 * Created by 11860_000 on 2018/2/24.
 */

public class ObtainUpdateAddressFeng {

    /**
     * code : 100
     * info : {"site_id":"22","user_id":"6","site_name":"测试","site_tel":"15731158396","site_city1":"辽宁省","site_city2":"沈阳市","site_city3":"沈河区","site_detail":"醉","site_is_first":"0","site_is_del":"1"}
     * success : 搜索成功
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
         * site_id : 22
         * user_id : 6
         * site_name : 测试
         * site_tel : 15731158396
         * site_city1 : 辽宁省
         * site_city2 : 沈阳市
         * site_city3 : 沈河区
         * site_detail : 醉
         * site_is_first : 0
         * site_is_del : 1
         */

        private String site_id;
        private String user_id;
        private String site_name;
        private String site_tel;
        private String site_city1;
        private String site_city2;
        private String site_city3;
        private String site_detail;
        private String site_is_first;
        private String site_is_del;
        private String city1_id;
        private String city2_id;
        private String city3_id;

        public String getCity1_id() {
            return city1_id;
        }

        public void setCity1_id(String city1_id) {
            this.city1_id = city1_id;
        }

        public String getCity2_id() {
            return city2_id;
        }

        public void setCity2_id(String city2_id) {
            this.city2_id = city2_id;
        }

        public String getCity3_id() {
            return city3_id;
        }

        public void setCity3_id(String city3_id) {
            this.city3_id = city3_id;
        }

        public String getSite_id() {
            return site_id;
        }

        public void setSite_id(String site_id) {
            this.site_id = site_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

        public String getSite_tel() {
            return site_tel;
        }

        public void setSite_tel(String site_tel) {
            this.site_tel = site_tel;
        }

        public String getSite_city1() {
            return site_city1;
        }

        public void setSite_city1(String site_city1) {
            this.site_city1 = site_city1;
        }

        public String getSite_city2() {
            return site_city2;
        }

        public void setSite_city2(String site_city2) {
            this.site_city2 = site_city2;
        }

        public String getSite_city3() {
            return site_city3;
        }

        public void setSite_city3(String site_city3) {
            this.site_city3 = site_city3;
        }

        public String getSite_detail() {
            return site_detail;
        }

        public void setSite_detail(String site_detail) {
            this.site_detail = site_detail;
        }

        public String getSite_is_first() {
            return site_is_first;
        }

        public void setSite_is_first(String site_is_first) {
            this.site_is_first = site_is_first;
        }

        public String getSite_is_del() {
            return site_is_del;
        }

        public void setSite_is_del(String site_is_del) {
            this.site_is_del = site_is_del;
        }
    }
}

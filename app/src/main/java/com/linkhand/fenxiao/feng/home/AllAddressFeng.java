package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/24.
 */

public class AllAddressFeng {

    /**
     * code : 100
     * info : [{"site_id":"2","user_id":"1","site_name":"啊啊","site_tel":"13733284374","site_city1":"河北","site_city2":"石家庄","site_city3":"裕华区","site_detail":"啊啊啊啊啊","site_is_first":"1","site_is_del":"1"},{"site_id":"3","user_id":"1","site_name":"哈哈","site_tel":"13732287455","site_city1":"河北","site_city2":"石家庄","site_city3":"裕华区","site_detail":"啊哈哈哈哈哈哈","site_is_first":"0","site_is_del":"1"},{"site_id":"6","user_id":"1","site_name":"名","site_tel":"15731158255","site_city1":"2","site_city2":"33","site_city3":"378","site_detail":"详细地址","site_is_first":"0","site_is_del":"1"},{"site_id":"7","user_id":"1","site_name":"名","site_tel":"15731158255","site_city1":"2","site_city2":"33","site_city3":"378","site_detail":"详细地址的","site_is_first":"0","site_is_del":"1"}]
     * success : 获取成功
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
         * site_id : 2
         * user_id : 1
         * site_name : 啊啊
         * site_tel : 13733284374
         * site_city1 : 河北
         * site_city2 : 石家庄
         * site_city3 : 裕华区
         * site_detail : 啊啊啊啊啊
         * site_is_first : 1
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

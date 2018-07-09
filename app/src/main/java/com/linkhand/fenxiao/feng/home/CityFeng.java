package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/23.
 */

public class CityFeng {

    /**
     * code : 100
     * info : [{"city_id":"33","city_code":"110100","city_name":"市辖区","city_pid":"2","city_name_en":"Shixiaqu","city_shou_en":"2"},{"city_id":"34","city_code":"110200","city_name":"县","city_pid":"2","city_name_en":"Xian","city_shou_en":"2"}]
     */

    private int code;
    private List<InfoBean> info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * city_id : 33
         * city_code : 110100
         * city_name : 市辖区
         * city_pid : 2
         * city_name_en : Shixiaqu
         * city_shou_en : 2
         */

        private String city_id;
        private String city_code;
        private String city_name;
        private String city_pid;
        private String city_name_en;
        private String city_shou_en;

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_pid() {
            return city_pid;
        }

        public void setCity_pid(String city_pid) {
            this.city_pid = city_pid;
        }

        public String getCity_name_en() {
            return city_name_en;
        }

        public void setCity_name_en(String city_name_en) {
            this.city_name_en = city_name_en;
        }

        public String getCity_shou_en() {
            return city_shou_en;
        }

        public void setCity_shou_en(String city_shou_en) {
            this.city_shou_en = city_shou_en;
        }
    }
}

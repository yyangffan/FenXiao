package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/11.
 */

public class ProvinceFeng {

    /**
     * code : 100
     * info : [{"city_id":"2","city_code":"110000","city_name":"北京市","city_pid":"1","city_name_en":"Beijing Shi","city_shou_en":"BJ"},{"city_id":"3","city_code":"120000","city_name":"天津市","city_pid":"1","city_name_en":"Tianjin Shi","city_shou_en":"TJ"},{"city_id":"4","city_code":"130000","city_name":"河北省","city_pid":"1","city_name_en":"Hebei Sheng","city_shou_en":"HE"},{"city_id":"5","city_code":"140000","city_name":"山西省","city_pid":"1","city_name_en":"Shanxi Sheng ","city_shou_en":"SX"},{"city_id":"6","city_code":"150000","city_name":"内蒙古自治区","city_pid":"1","city_name_en":"Nei Mongol Zizhiqu","city_shou_en":"NM"},{"city_id":"7","city_code":"210000","city_name":"辽宁省","city_pid":"1","city_name_en":"Liaoning Sheng","city_shou_en":"LN"},{"city_id":"8","city_code":"220000","city_name":"吉林省","city_pid":"1","city_name_en":"Jilin Sheng","city_shou_en":"JL"},{"city_id":"9","city_code":"230000","city_name":"黑龙江省","city_pid":"1","city_name_en":"Heilongjiang Sheng","city_shou_en":"HL"},{"city_id":"10","city_code":"310000","city_name":"上海市","city_pid":"1","city_name_en":"Shanghai Shi","city_shou_en":"SH"},{"city_id":"11","city_code":"320000","city_name":"江苏省","city_pid":"1","city_name_en":"Jiangsu Sheng","city_shou_en":"JS"},{"city_id":"12","city_code":"330000","city_name":"浙江省","city_pid":"1","city_name_en":"Zhejiang Sheng","city_shou_en":"ZJ"},{"city_id":"13","city_code":"340000","city_name":"安徽省","city_pid":"1","city_name_en":"Anhui Sheng","city_shou_en":"AH"},{"city_id":"14","city_code":"350000","city_name":"福建省","city_pid":"1","city_name_en":"Fujian Sheng ","city_shou_en":"FJ"},{"city_id":"15","city_code":"360000","city_name":"江西省","city_pid":"1","city_name_en":"Jiangxi Sheng","city_shou_en":"JX"},{"city_id":"16","city_code":"370000","city_name":"山东省","city_pid":"1","city_name_en":"Shandong Sheng ","city_shou_en":"SD"},{"city_id":"17","city_code":"410000","city_name":"河南省","city_pid":"1","city_name_en":"Henan Sheng","city_shou_en":"HA"},{"city_id":"18","city_code":"420000","city_name":"湖北省","city_pid":"1","city_name_en":"Hubei Sheng","city_shou_en":"HB"},{"city_id":"19","city_code":"430000","city_name":"湖南省","city_pid":"1","city_name_en":"Hunan Sheng","city_shou_en":"HN"},{"city_id":"20","city_code":"440000","city_name":"广东省","city_pid":"1","city_name_en":"Guangdong Sheng","city_shou_en":"GD"},{"city_id":"21","city_code":"450000","city_name":"广西壮族自治区","city_pid":"1","city_name_en":"Guangxi Zhuangzu Zizhiqu","city_shou_en":"GX"},{"city_id":"22","city_code":"460000","city_name":"海南省","city_pid":"1","city_name_en":"Hainan Sheng","city_shou_en":"HI"},{"city_id":"23","city_code":"500000","city_name":"重庆市","city_pid":"1","city_name_en":"Chongqing Shi","city_shou_en":"CQ"},{"city_id":"24","city_code":"510000","city_name":"四川省","city_pid":"1","city_name_en":"Sichuan Sheng","city_shou_en":"SC"},{"city_id":"25","city_code":"520000","city_name":"贵州省","city_pid":"1","city_name_en":"Guizhou Sheng","city_shou_en":"GZ"},{"city_id":"26","city_code":"530000","city_name":"云南省","city_pid":"1","city_name_en":"Yunnan Sheng","city_shou_en":"YN"},{"city_id":"27","city_code":"540000","city_name":"西藏自治区","city_pid":"1","city_name_en":"Xizang Zizhiqu","city_shou_en":"XZ"},{"city_id":"28","city_code":"610000","city_name":"陕西省","city_pid":"1","city_name_en":"Shanxi Sheng ","city_shou_en":"SN"},{"city_id":"29","city_code":"620000","city_name":"甘肃省","city_pid":"1","city_name_en":"Gansu Sheng","city_shou_en":"GS"},{"city_id":"30","city_code":"630000","city_name":"青海省","city_pid":"1","city_name_en":"Qinghai Sheng","city_shou_en":"QH"},{"city_id":"31","city_code":"640000","city_name":"宁夏回族自治区","city_pid":"1","city_name_en":"Ningxia Huizu Zizhiqu","city_shou_en":"NX"},{"city_id":"32","city_code":"650000","city_name":"新疆维吾尔自治区","city_pid":"1","city_name_en":"Xinjiang Uygur Zizhiqu","city_shou_en":"XJ"}]
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
         * city_id : 2
         * city_code : 110000
         * city_name : 北京市
         * city_pid : 1
         * city_name_en : Beijing Shi
         * city_shou_en : BJ
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

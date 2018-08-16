package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 杨帆 on 2018/8/10.
 */

public class FenCaiFlBean {

    /**
     * code : 100
     * info : [{"art_cat_id":"1","art_cat_name":"企业信息","art_cat_sort":"1"},{"art_cat_id":"2","art_cat_name":"商家信息","art_cat_sort":"2"},{"art_cat_id":"3","art_cat_name":"新闻热点","art_cat_sort":"3"},{"art_cat_id":"4","art_cat_name":"娱乐热线","art_cat_sort":"4"},{"art_cat_id":"5","art_cat_name":"国际新闻","art_cat_sort":"5"},{"art_cat_id":"6","art_cat_name":"游戏更新","art_cat_sort":"6"},{"art_cat_id":"7","art_cat_name":"体育快报","art_cat_sort":"7"}]
     * success : 数据返回
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
         * art_cat_id : 1
         * art_cat_name : 企业信息
         * art_cat_sort : 1
         */

        private String art_cat_id;
        private String art_cat_name;
        private String art_cat_sort;

        public String getArt_cat_id() {
            return art_cat_id;
        }

        public void setArt_cat_id(String art_cat_id) {
            this.art_cat_id = art_cat_id;
        }

        public String getArt_cat_name() {
            return art_cat_name;
        }

        public void setArt_cat_name(String art_cat_name) {
            this.art_cat_name = art_cat_name;
        }

        public String getArt_cat_sort() {
            return art_cat_sort;
        }

        public void setArt_cat_sort(String art_cat_sort) {
            this.art_cat_sort = art_cat_sort;
        }
    }
}

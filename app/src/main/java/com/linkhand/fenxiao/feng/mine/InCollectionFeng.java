package com.linkhand.fenxiao.feng.mine;

import java.util.List;

/**
 * Created by 11860_000 on 2018/3/16.
 */

public class InCollectionFeng {

    /**
     * code : 100
     * success : 返回成功
     * info : [{"iha_id":"20","idea_id":"6","idea_name":"意向更新","idea_mater":"200.00","idea_son":"300.00","idea_img_url":"/public/uploads/20180125/ca82842d067e5f764f8db60a7c9423cc.png"},{"iha_id":"21","idea_id":"7","idea_name":"zcc12","idea_mater":"1.00","idea_son":"1.00","idea_img_url":"/public/uploads/20180125/f5ab262ab9b050478fab654b126bacbb.png"}]
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
         * iha_id : 20
         * idea_id : 6
         * idea_name : 意向更新
         * idea_mater : 200.00
         * idea_son : 300.00
         * idea_img_url : /public/uploads/20180125/ca82842d067e5f764f8db60a7c9423cc.png
         */

        private String iha_id;
        private String idea_id;
        private String idea_name;
        private String idea_mater;
        private String idea_son;
        private String idea_img_url;

        public String getIha_id() {
            return iha_id;
        }

        public void setIha_id(String iha_id) {
            this.iha_id = iha_id;
        }

        public String getIdea_id() {
            return idea_id;
        }

        public void setIdea_id(String idea_id) {
            this.idea_id = idea_id;
        }

        public String getIdea_name() {
            return idea_name;
        }

        public void setIdea_name(String idea_name) {
            this.idea_name = idea_name;
        }

        public String getIdea_mater() {
            return idea_mater;
        }

        public void setIdea_mater(String idea_mater) {
            this.idea_mater = idea_mater;
        }

        public String getIdea_son() {
            return idea_son;
        }

        public void setIdea_son(String idea_son) {
            this.idea_son = idea_son;
        }

        public String getIdea_img_url() {
            return idea_img_url;
        }

        public void setIdea_img_url(String idea_img_url) {
            this.idea_img_url = idea_img_url;
        }
    }
}

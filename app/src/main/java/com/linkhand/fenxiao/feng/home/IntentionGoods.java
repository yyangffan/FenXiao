package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/1/13.
 */

public class IntentionGoods {


    /**
     * code : 100
     * info : [{"idea_id":"2","idea_name":"123","idea_mater":"123.00","idea_son":"13.00","idea_money":"123.00","idea_num":"123","idea_nownum":"0","idea_intro":"123","idea_img":"/public/uploads/20180105/a3676a7e544bab2996925ad2419eba5b.png","idea_is_top":"1","idea_time":"1515137793","is_open":0,"is_have":0}]
     */

    private int code;
    private List<InfoBean> info;
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

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

    @Override
    public String toString() {
        return "IntentionGoods{" +
                "code=" + code +
                ", info=" + info +
                '}';
    }

    public static class InfoBean {
        /**
         * idea_id : 2
         * idea_name : 123
         * idea_mater : 123.00
         * idea_son : 13.00
         * idea_money : 123.00
         * idea_num : 123
         * idea_nownum : 0
         * idea_intro : 123
         * idea_img : /public/uploads/20180105/a3676a7e544bab2996925ad2419eba5b.png
         * idea_is_top : 1
         * idea_time : 1515137793
         * is_open : 0
         * is_have : 0
         */

        private String idea_id;
        private String idea_name;
        private String idea_mater;
        private String idea_son;
        private String idea_money;
        private String idea_num;
        private String idea_nownum;
        private String idea_intro;
        private Object idea_img;
        private String idea_is_top;
        private String idea_time;
        private int is_open;
        private int is_have;

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

        public String getIdea_money() {
            return idea_money;
        }

        public void setIdea_money(String idea_money) {
            this.idea_money = idea_money;
        }

        public String getIdea_num() {
            return idea_num;
        }

        public void setIdea_num(String idea_num) {
            this.idea_num = idea_num;
        }

        public String getIdea_nownum() {
            return idea_nownum;
        }

        public void setIdea_nownum(String idea_nownum) {
            this.idea_nownum = idea_nownum;
        }

        public String getIdea_intro() {
            return idea_intro;
        }

        public void setIdea_intro(String idea_intro) {
            this.idea_intro = idea_intro;
        }

        public Object getIdea_img() {
            return idea_img;
        }

        public void setIdea_img(Object idea_img) {
            this.idea_img = idea_img;
        }

        public String getIdea_is_top() {
            return idea_is_top;
        }

        public void setIdea_is_top(String idea_is_top) {
            this.idea_is_top = idea_is_top;
        }

        public String getIdea_time() {
            return idea_time;
        }

        public void setIdea_time(String idea_time) {
            this.idea_time = idea_time;
        }

        public int getIs_open() {
            return is_open;
        }

        public void setIs_open(int is_open) {
            this.is_open = is_open;
        }

        public int getIs_have() {
            return is_have;
        }

        public void setIs_have(int is_have) {
            this.is_have = is_have;
        }

        @Override
        public String toString() {
            return "InfoBean{" +
                    "idea_id='" + idea_id + '\'' +
                    ", idea_name='" + idea_name + '\'' +
                    ", idea_mater='" + idea_mater + '\'' +
                    ", idea_son='" + idea_son + '\'' +
                    ", idea_money='" + idea_money + '\'' +
                    ", idea_num='" + idea_num + '\'' +
                    ", idea_nownum='" + idea_nownum + '\'' +
                    ", idea_intro='" + idea_intro + '\'' +
                    ", idea_img='" + idea_img + '\'' +
                    ", idea_is_top='" + idea_is_top + '\'' +
                    ", idea_time='" + idea_time + '\'' +
                    ", is_open=" + is_open +
                    ", is_have=" + is_have +
                    '}';
        }
    }
}

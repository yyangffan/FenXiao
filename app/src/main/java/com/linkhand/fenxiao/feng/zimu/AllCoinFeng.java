package com.linkhand.fenxiao.feng.zimu;

import java.util.List;

/**
 * Created by 11860_000 on 2018/3/1.
 */

public class AllCoinFeng {

    /**
     * code : 100
     * info : [{"curr_id":"2","user_id":"1","curr_mater_num":"22.00","curr_son_money":"2.00","curr_state":"1","curr_state_time":"1516867198","user_name":"第一"},{"curr_id":"3","user_id":"6","curr_mater_num":"1.00","curr_son_money":"2.00","curr_state":"1","curr_state_time":"1519442606","user_name":"15731158255"},{"curr_id":"4","user_id":"6","curr_mater_num":"1.00","curr_son_money":"1.00","curr_state":"1","curr_state_time":"1519442725","user_name":"15731158255"},{"curr_id":"5","user_id":"6","curr_mater_num":"1.00","curr_son_money":"1.00","curr_state":"1","curr_state_time":"1519442785","user_name":"15731158255"},{"curr_id":"6","user_id":"6","curr_mater_num":"1.00","curr_son_money":"1.00","curr_state":"1","curr_state_time":"1519443133","user_name":"15731158255"},{"curr_id":"1","user_id":"0","curr_mater_num":"1.00","curr_son_money":"1.00","curr_state":"1","curr_state_time":"1519442162","user_name":"官方"}]
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

    public static class InfoBean {
        /**
         * curr_id : 2
         * user_id : 1
         * curr_mater_num : 22.00
         * curr_son_money : 2.00
         * curr_state : 1
         * curr_state_time : 1516867198
         * user_name : 第一
         */

        private String curr_id;
        private String user_id;
        private String curr_mater_num;
        private String curr_son_money;
        private String curr_state;
        private String curr_state_time;
        private String user_name;

        public String getCurr_id() {
            return curr_id;
        }

        public void setCurr_id(String curr_id) {
            this.curr_id = curr_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCurr_mater_num() {
            return curr_mater_num;
        }

        public void setCurr_mater_num(String curr_mater_num) {
            this.curr_mater_num = curr_mater_num;
        }

        public String getCurr_son_money() {
            return curr_son_money;
        }

        public void setCurr_son_money(String curr_son_money) {
            this.curr_son_money = curr_son_money;
        }

        public String getCurr_state() {
            return curr_state;
        }

        public void setCurr_state(String curr_state) {
            this.curr_state = curr_state;
        }

        public String getCurr_state_time() {
            return curr_state_time;
        }

        public void setCurr_state_time(String curr_state_time) {
            this.curr_state_time = curr_state_time;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}

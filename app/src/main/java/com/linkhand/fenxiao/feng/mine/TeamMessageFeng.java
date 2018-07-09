package com.linkhand.fenxiao.feng.mine;

import java.util.List;

/**
 * Created by 11860_000 on 2018/3/2.
 */

public class TeamMessageFeng {

    /**
     * code : 100
     * success : 获取成功
     * info : {"user_id":"2","user_reb_id":"1","user_grade":"2","user_name":"boss","user_tel":"13363822221","user_top":"","user_head_img":"/public/uploads/20180523/ae60f777ae8a82f9330366f8a37f69c0.jpg","user_is_vip":"1","user_referr1":"3","user_referr2":"4","user_referr3":"5","sign_time":"1526883008","top_name":"","rebate_str":"铜的2级","ref_head_img":["/public/uploads/initimg.png","/public/uploads/initimg.png","/public/uploads/initimg.png"]}
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
         * user_id : 2
         * user_reb_id : 1
         * user_grade : 2
         * user_name : boss
         * user_tel : 13363822221
         * user_top :
         * user_head_img : /public/uploads/20180523/ae60f777ae8a82f9330366f8a37f69c0.jpg
         * user_is_vip : 1
         * user_referr1 : 3
         * user_referr2 : 4
         * user_referr3 : 5
         * sign_time : 1526883008
         * top_name :
         * rebate_str : 铜的2级
         * ref_head_img : ["/public/uploads/initimg.png","/public/uploads/initimg.png","/public/uploads/initimg.png"]
         */

        private String user_id;
        private String user_reb_id;
        private String user_grade;
        private String user_name;
        private String user_tel;
        private String user_top;
        private String user_head_img;
        private String user_is_vip;
        private String user_referr1;
        private String user_referr2;
        private String user_referr3;
        private String sign_time;
        private String top_name;
        private String rebate_str;
        private List<String> ref_head_img;
        private String ref_num;
        private String team_num;

        public String getTeam_num() {
            return team_num;
        }

        public void setTeam_num(String team_num) {
            this.team_num = team_num;
        }

        public String getRef_num() {
            return ref_num;
        }

        public void setRef_num(String ref_num) {
            this.ref_num = ref_num;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_reb_id() {
            return user_reb_id;
        }

        public void setUser_reb_id(String user_reb_id) {
            this.user_reb_id = user_reb_id;
        }

        public String getUser_grade() {
            return user_grade;
        }

        public void setUser_grade(String user_grade) {
            this.user_grade = user_grade;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_tel() {
            return user_tel;
        }

        public void setUser_tel(String user_tel) {
            this.user_tel = user_tel;
        }

        public String getUser_top() {
            return user_top;
        }

        public void setUser_top(String user_top) {
            this.user_top = user_top;
        }

        public String getUser_head_img() {
            return user_head_img;
        }

        public void setUser_head_img(String user_head_img) {
            this.user_head_img = user_head_img;
        }

        public String getUser_is_vip() {
            return user_is_vip;
        }

        public void setUser_is_vip(String user_is_vip) {
            this.user_is_vip = user_is_vip;
        }

        public String getUser_referr1() {
            return user_referr1;
        }

        public void setUser_referr1(String user_referr1) {
            this.user_referr1 = user_referr1;
        }

        public String getUser_referr2() {
            return user_referr2;
        }

        public void setUser_referr2(String user_referr2) {
            this.user_referr2 = user_referr2;
        }

        public String getUser_referr3() {
            return user_referr3;
        }

        public void setUser_referr3(String user_referr3) {
            this.user_referr3 = user_referr3;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getTop_name() {
            return top_name;
        }

        public void setTop_name(String top_name) {
            this.top_name = top_name;
        }

        public String getRebate_str() {
            return rebate_str;
        }

        public void setRebate_str(String rebate_str) {
            this.rebate_str = rebate_str;
        }

        public List<String> getRef_head_img() {
            return ref_head_img;
        }

        public void setRef_head_img(List<String> ref_head_img) {
            this.ref_head_img = ref_head_img;
        }
    }
}

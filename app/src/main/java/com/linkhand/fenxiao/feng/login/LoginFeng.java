package com.linkhand.fenxiao.feng.login;

/**
 * Created by 11860_000 on 2018/1/19.
 */

public class LoginFeng {

    /**
     * code : 100
     * success : 登录成功
     * info : 47
     * user : {"user_id":"47","user_reb_id":"1","user_grade":"5","user_name":"测试","user_tel":"15731158266","user_pwd":"e10adc3949ba59abbe56e057f20f883e","user_inviter":"uQyB","user_mater_money":"997.00","user_son_money":"999.00","user_son_frost":"0.00","user_referr1":"","user_referr2":"","user_referr3":"","user_top":"16","user_is_team":"1","upto_time":"1521016269","user_is_admin":"0","user_is_vip":"1","user_head_img":"/public/uploads/20180316/453544033fffa4327d6a345739f31d8f.jpg","token":"","sign_time":"1521007035","real_name":"","user_card":"220322199607290574","is_real":"1"}
     */

    private int code;
    private String success;
    private String info;
    private UserBean user;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * user_id : 47
         * user_reb_id : 1
         * user_grade : 5
         * user_name : 测试
         * user_tel : 15731158266
         * user_pwd : e10adc3949ba59abbe56e057f20f883e
         * user_inviter : uQyB
         * user_mater_money : 997.00
         * user_son_money : 999.00
         * user_son_frost : 0.00
         * user_referr1 :
         * user_referr2 :
         * user_referr3 :
         * user_top : 16
         * user_is_team : 1
         * upto_time : 1521016269
         * user_is_admin : 0
         * user_is_vip : 1
         * user_head_img : /public/uploads/20180316/453544033fffa4327d6a345739f31d8f.jpg
         * token :
         * sign_time : 1521007035
         * real_name :
         * user_card : 220322199607290574
         * is_real : 1
         */

        private String user_id;
        private String user_reb_id;
        private String user_grade;
        private String user_name;
        private String user_tel;
        private String user_pwd;
        private String user_inviter;
        private String user_mater_money;
        private String user_son_money;
        private String user_son_frost;
        private String user_referr1;
        private String user_referr2;
        private String user_referr3;
        private String user_top;
        private String user_is_team;
        private String upto_time;
        private String user_is_admin;
        private String user_is_vip;
        private String user_head_img;
        private String token;
        private String sign_time;
        private String real_name;
        private String user_card;
        private String is_real;
        private String old_token;
        private String new_token;
        private String user_pay_pwd;

        public String getUser_pay_pwd() {
            return user_pay_pwd;
        }

        public void setUser_pay_pwd(String user_pay_pwd) {
            this.user_pay_pwd = user_pay_pwd;
        }

        public String getNew_token() {
            return new_token;
        }

        public void setNew_token(String new_token) {
            this.new_token = new_token;
        }

        public String getOld_token() {
            return old_token;
        }

        public void setOld_token(String old_token) {
            this.old_token = old_token;
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

        public String getUser_pwd() {
            return user_pwd;
        }

        public void setUser_pwd(String user_pwd) {
            this.user_pwd = user_pwd;
        }

        public String getUser_inviter() {
            return user_inviter;
        }

        public void setUser_inviter(String user_inviter) {
            this.user_inviter = user_inviter;
        }

        public String getUser_mater_money() {
            return user_mater_money;
        }

        public void setUser_mater_money(String user_mater_money) {
            this.user_mater_money = user_mater_money;
        }

        public String getUser_son_money() {
            return user_son_money;
        }

        public void setUser_son_money(String user_son_money) {
            this.user_son_money = user_son_money;
        }

        public String getUser_son_frost() {
            return user_son_frost;
        }

        public void setUser_son_frost(String user_son_frost) {
            this.user_son_frost = user_son_frost;
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

        public String getUser_top() {
            return user_top;
        }

        public void setUser_top(String user_top) {
            this.user_top = user_top;
        }

        public String getUser_is_team() {
            return user_is_team;
        }

        public void setUser_is_team(String user_is_team) {
            this.user_is_team = user_is_team;
        }

        public String getUpto_time() {
            return upto_time;
        }

        public void setUpto_time(String upto_time) {
            this.upto_time = upto_time;
        }

        public String getUser_is_admin() {
            return user_is_admin;
        }

        public void setUser_is_admin(String user_is_admin) {
            this.user_is_admin = user_is_admin;
        }

        public String getUser_is_vip() {
            return user_is_vip;
        }

        public void setUser_is_vip(String user_is_vip) {
            this.user_is_vip = user_is_vip;
        }

        public String getUser_head_img() {
            return user_head_img;
        }

        public void setUser_head_img(String user_head_img) {
            this.user_head_img = user_head_img;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getUser_card() {
            return user_card;
        }

        public void setUser_card(String user_card) {
            this.user_card = user_card;
        }

        public String getIs_real() {
            return is_real;
        }

        public void setIs_real(String is_real) {
            this.is_real = is_real;
        }
    }
}

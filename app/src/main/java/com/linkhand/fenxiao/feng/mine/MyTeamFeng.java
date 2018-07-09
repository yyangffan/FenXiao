package com.linkhand.fenxiao.feng.mine;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 11860_000 on 2018/3/1.
 */

public class MyTeamFeng {

    /**
     * code : 100
     * success : 获取成功
     * info : {"0":[{"user_id":"3","user_reb_id":"1","user_grade":"2","user_name":"第二-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"1","user_referr1":"6","user_referr2":"7","user_referr3":"8","top_user_name":"第一"},{"user_id":"4","user_reb_id":"1","user_grade":"2","user_name":"第二-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"1","user_referr1":"9","user_referr2":"10","user_referr3":"12","top_user_name":"第一"},{"user_id":"5","user_reb_id":"1","user_grade":"2","user_name":"第二-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"1","user_referr1":"13","user_referr2":"14","user_referr3":"15","top_user_name":"第一"}],"1":[{"user_id":"6","user_reb_id":"1","user_grade":"3","user_name":"15731158255","user_tel":"15731158255","user_head_img":"","user_is_vip":"1","user_top":"3","user_referr1":"16","user_referr2":"17","user_referr3":"18","top_user_name":"第二-1"},{"user_id":"7","user_reb_id":"1","user_grade":"3","user_name":"第三-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"3","user_referr1":"19","user_referr2":"20","user_referr3":"21","top_user_name":"第二-1"},{"user_id":"8","user_reb_id":"1","user_grade":"3","user_name":"18813139617","user_tel":"18813139617","user_head_img":"","user_is_vip":"1","user_top":"3","user_referr1":"22","user_referr2":"23","user_referr3":"24","top_user_name":"第二-1"},{"user_id":"9","user_reb_id":"1","user_grade":"3","user_name":"第三-4","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"4","user_referr1":"25","user_referr2":"26","user_referr3":"27","top_user_name":"第二-2"},{"user_id":"10","user_reb_id":"1","user_grade":"3","user_name":"第三-5","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"4","user_referr1":"28","user_referr2":"29","user_referr3":"30","top_user_name":"第二-2"},{"user_id":"12","user_reb_id":"1","user_grade":"3","user_name":"第三-6","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"4","user_referr1":"31","user_referr2":"32","user_referr3":"33","top_user_name":"第二-2"},{"user_id":"13","user_reb_id":"1","user_grade":"3","user_name":"第三-7","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"5","user_referr1":"34","user_referr2":"35","user_referr3":"36","top_user_name":"第二-3"},{"user_id":"14","user_reb_id":"1","user_grade":"3","user_name":"第三-8","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"5","user_referr1":"37","user_referr2":"38","user_referr3":"39","top_user_name":"第二-3"},{"user_id":"15","user_reb_id":"1","user_grade":"3","user_name":"第三-9","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"5","user_referr1":"40","user_referr2":"41","user_referr3":"","top_user_name":"第二-3"}],"2":[{"user_id":"16","user_reb_id":"1","user_grade":"4","user_name":"第四-1-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"6","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"15731158255"},{"user_id":"17","user_reb_id":"1","user_grade":"4","user_name":"第四-1-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"6","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"15731158255"},{"user_id":"18","user_reb_id":"1","user_grade":"4","user_name":"第四-1-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"6","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"15731158255"},{"user_id":"19","user_reb_id":"1","user_grade":"4","user_name":"第四-2-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"7","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-2"},{"user_id":"20","user_reb_id":"1","user_grade":"4","user_name":"第四-2-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"7","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-2"},{"user_id":"21","user_reb_id":"1","user_grade":"4","user_name":"第四-2-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"7","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-2"},{"user_id":"22","user_reb_id":"1","user_grade":"4","user_name":"第四-3-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"8","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"18813139617"},{"user_id":"23","user_reb_id":"1","user_grade":"4","user_name":"第三-3-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"8","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"18813139617"},{"user_id":"24","user_reb_id":"1","user_grade":"4","user_name":"第三-3-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"8","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"18813139617"},{"user_id":"25","user_reb_id":"1","user_grade":"4","user_name":"第三-4-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"9","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-4"},{"user_id":"26","user_reb_id":"1","user_grade":"4","user_name":"第三-4-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"9","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-4"},{"user_id":"27","user_reb_id":"1","user_grade":"4","user_name":"第三-4-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"9","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-4"},{"user_id":"28","user_reb_id":"1","user_grade":"4","user_name":"第三-5-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"10","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-5"},{"user_id":"29","user_reb_id":"1","user_grade":"4","user_name":"第三-5-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"10","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-5"},{"user_id":"30","user_reb_id":"1","user_grade":"4","user_name":"第三-5-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"10","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-5"},{"user_id":"31","user_reb_id":"1","user_grade":"4","user_name":"第三-6-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"12","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-6"},{"user_id":"32","user_reb_id":"1","user_grade":"4","user_name":"第三-6-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"12","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-6"},{"user_id":"33","user_reb_id":"1","user_grade":"4","user_name":"第三-6-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"12","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-6"},{"user_id":"34","user_reb_id":"1","user_grade":"4","user_name":"第三-7-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"13","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-7"},{"user_id":"35","user_reb_id":"1","user_grade":"4","user_name":"第三-7-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"13","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-7"},{"user_id":"36","user_reb_id":"1","user_grade":"4","user_name":"第三-7-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"13","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-7"},{"user_id":"37","user_reb_id":"1","user_grade":"4","user_name":"第三-8-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"14","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-8"},{"user_id":"38","user_reb_id":"1","user_grade":"4","user_name":"第三-8-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"14","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-8"},{"user_id":"39","user_reb_id":"1","user_grade":"4","user_name":"第三-8-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"14","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-8"},{"user_id":"40","user_reb_id":"1","user_grade":"4","user_name":"第三-9-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"15","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-9"},{"user_id":"41","user_reb_id":"1","user_grade":"4","user_name":"第三-9-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"15","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-9"}],"user":{"user_id":"1","user_reb_id":"1","user_grade":"1","user_name":"第一","user_tel":"1","user_pwd":"c4ca4238a0b923820dcc509a6f75849b","user_inviter":"1111","user_mater_money":"39.00","user_son_money":"61.00","user_son_frost":"0.00","user_referr1":"3","user_referr2":"4","user_referr3":"5","user_top":"0","user_is_team":"1","upto_time":"1515486600","user_is_admin":"0","user_is_vip":"1","user_head_img":"","token":"","sign_time":"1515486599"},"grpname":["下一级","下二级","下三级"]}
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
         * 0 : [{"user_id":"3","user_reb_id":"1","user_grade":"2","user_name":"第二-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"1","user_referr1":"6","user_referr2":"7","user_referr3":"8","top_user_name":"第一"},{"user_id":"4","user_reb_id":"1","user_grade":"2","user_name":"第二-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"1","user_referr1":"9","user_referr2":"10","user_referr3":"12","top_user_name":"第一"},{"user_id":"5","user_reb_id":"1","user_grade":"2","user_name":"第二-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"1","user_referr1":"13","user_referr2":"14","user_referr3":"15","top_user_name":"第一"}]
         * 1 : [{"user_id":"6","user_reb_id":"1","user_grade":"3","user_name":"15731158255","user_tel":"15731158255","user_head_img":"","user_is_vip":"1","user_top":"3","user_referr1":"16","user_referr2":"17","user_referr3":"18","top_user_name":"第二-1"},{"user_id":"7","user_reb_id":"1","user_grade":"3","user_name":"第三-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"3","user_referr1":"19","user_referr2":"20","user_referr3":"21","top_user_name":"第二-1"},{"user_id":"8","user_reb_id":"1","user_grade":"3","user_name":"18813139617","user_tel":"18813139617","user_head_img":"","user_is_vip":"1","user_top":"3","user_referr1":"22","user_referr2":"23","user_referr3":"24","top_user_name":"第二-1"},{"user_id":"9","user_reb_id":"1","user_grade":"3","user_name":"第三-4","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"4","user_referr1":"25","user_referr2":"26","user_referr3":"27","top_user_name":"第二-2"},{"user_id":"10","user_reb_id":"1","user_grade":"3","user_name":"第三-5","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"4","user_referr1":"28","user_referr2":"29","user_referr3":"30","top_user_name":"第二-2"},{"user_id":"12","user_reb_id":"1","user_grade":"3","user_name":"第三-6","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"4","user_referr1":"31","user_referr2":"32","user_referr3":"33","top_user_name":"第二-2"},{"user_id":"13","user_reb_id":"1","user_grade":"3","user_name":"第三-7","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"5","user_referr1":"34","user_referr2":"35","user_referr3":"36","top_user_name":"第二-3"},{"user_id":"14","user_reb_id":"1","user_grade":"3","user_name":"第三-8","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"5","user_referr1":"37","user_referr2":"38","user_referr3":"39","top_user_name":"第二-3"},{"user_id":"15","user_reb_id":"1","user_grade":"3","user_name":"第三-9","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"5","user_referr1":"40","user_referr2":"41","user_referr3":"","top_user_name":"第二-3"}]
         * 2 : [{"user_id":"16","user_reb_id":"1","user_grade":"4","user_name":"第四-1-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"6","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"15731158255"},{"user_id":"17","user_reb_id":"1","user_grade":"4","user_name":"第四-1-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"6","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"15731158255"},{"user_id":"18","user_reb_id":"1","user_grade":"4","user_name":"第四-1-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"6","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"15731158255"},{"user_id":"19","user_reb_id":"1","user_grade":"4","user_name":"第四-2-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"7","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-2"},{"user_id":"20","user_reb_id":"1","user_grade":"4","user_name":"第四-2-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"7","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-2"},{"user_id":"21","user_reb_id":"1","user_grade":"4","user_name":"第四-2-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"7","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-2"},{"user_id":"22","user_reb_id":"1","user_grade":"4","user_name":"第四-3-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"8","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"18813139617"},{"user_id":"23","user_reb_id":"1","user_grade":"4","user_name":"第三-3-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"8","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"18813139617"},{"user_id":"24","user_reb_id":"1","user_grade":"4","user_name":"第三-3-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"8","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"18813139617"},{"user_id":"25","user_reb_id":"1","user_grade":"4","user_name":"第三-4-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"9","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-4"},{"user_id":"26","user_reb_id":"1","user_grade":"4","user_name":"第三-4-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"9","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-4"},{"user_id":"27","user_reb_id":"1","user_grade":"4","user_name":"第三-4-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"9","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-4"},{"user_id":"28","user_reb_id":"1","user_grade":"4","user_name":"第三-5-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"10","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-5"},{"user_id":"29","user_reb_id":"1","user_grade":"4","user_name":"第三-5-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"10","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-5"},{"user_id":"30","user_reb_id":"1","user_grade":"4","user_name":"第三-5-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"10","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-5"},{"user_id":"31","user_reb_id":"1","user_grade":"4","user_name":"第三-6-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"12","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-6"},{"user_id":"32","user_reb_id":"1","user_grade":"4","user_name":"第三-6-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"12","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-6"},{"user_id":"33","user_reb_id":"1","user_grade":"4","user_name":"第三-6-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"12","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-6"},{"user_id":"34","user_reb_id":"1","user_grade":"4","user_name":"第三-7-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"13","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-7"},{"user_id":"35","user_reb_id":"1","user_grade":"4","user_name":"第三-7-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"13","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-7"},{"user_id":"36","user_reb_id":"1","user_grade":"4","user_name":"第三-7-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"13","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-7"},{"user_id":"37","user_reb_id":"1","user_grade":"4","user_name":"第三-8-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"14","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-8"},{"user_id":"38","user_reb_id":"1","user_grade":"4","user_name":"第三-8-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"14","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-8"},{"user_id":"39","user_reb_id":"1","user_grade":"4","user_name":"第三-8-3","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"14","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-8"},{"user_id":"40","user_reb_id":"1","user_grade":"4","user_name":"第三-9-1","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"15","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-9"},{"user_id":"41","user_reb_id":"1","user_grade":"4","user_name":"第三-9-2","user_tel":"1","user_head_img":"","user_is_vip":"1","user_top":"15","user_referr1":"","user_referr2":"","user_referr3":"","top_user_name":"第三-9"}]
         * user : {"user_id":"1","user_reb_id":"1","user_grade":"1","user_name":"第一","user_tel":"1","user_pwd":"c4ca4238a0b923820dcc509a6f75849b","user_inviter":"1111","user_mater_money":"39.00","user_son_money":"61.00","user_son_frost":"0.00","user_referr1":"3","user_referr2":"4","user_referr3":"5","user_top":"0","user_is_team":"1","upto_time":"1515486600","user_is_admin":"0","user_is_vip":"1","user_head_img":"","token":"","sign_time":"1515486599"}
         * grpname : ["下一级","下二级","下三级"]
         */

        private UserBean user;
        @SerializedName("0")
        private List<_$0Bean> _$0;
        @SerializedName("1")
        private List<_$1Bean> _$1;
        @SerializedName("2")
        private List<_$2Bean> _$2;
        private List<String> grpname;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<_$0Bean> get_$0() {
            return _$0;
        }

        public void set_$0(List<_$0Bean> _$0) {
            this._$0 = _$0;
        }

        public List<_$1Bean> get_$1() {
            return _$1;
        }

        public void set_$1(List<_$1Bean> _$1) {
            this._$1 = _$1;
        }

        public List<_$2Bean> get_$2() {
            return _$2;
        }

        public void set_$2(List<_$2Bean> _$2) {
            this._$2 = _$2;
        }

        public List<String> getGrpname() {
            return grpname;
        }

        public void setGrpname(List<String> grpname) {
            this.grpname = grpname;
        }

        public static class UserBean {
            /**
             * user_id : 1
             * user_reb_id : 1
             * user_grade : 1
             * user_name : 第一
             * user_tel : 1
             * user_pwd : c4ca4238a0b923820dcc509a6f75849b
             * user_inviter : 1111
             * user_mater_money : 39.00
             * user_son_money : 61.00
             * user_son_frost : 0.00
             * user_referr1 : 3
             * user_referr2 : 4
             * user_referr3 : 5
             * user_top : 0
             * user_is_team : 1
             * upto_time : 1515486600
             * user_is_admin : 0
             * user_is_vip : 1
             * user_head_img :
             * token :
             * sign_time : 1515486599
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
            private String rebate_str;

            public String getRebate_str() {
                return rebate_str;
            }

            public void setRebate_str(String rebate_str) {
                this.rebate_str = rebate_str;
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
        }

        public static class _$0Bean {
            /**
             * user_id : 3
             * user_reb_id : 1
             * user_grade : 2
             * user_name : 第二-1
             * user_tel : 1
             * user_head_img :
             * user_is_vip : 1
             * user_top : 1
             * user_referr1 : 6
             * user_referr2 : 7
             * user_referr3 : 8
             * top_user_name : 第一
             */

            private String user_id;
            private String user_reb_id;
            private String user_grade;
            private String user_name;
            private String user_tel;
            private String user_head_img;
            private String user_is_vip;
            private String user_top;
            private String user_referr1;
            private String user_referr2;
            private String user_referr3;
            private String top_user_name;
            private String referr1_head;
            private String referr2_head;
            private String referr3_head;

            public String getReferr1_head() {
                return referr1_head;
            }

            public void setReferr1_head(String referr1_head) {
                this.referr1_head = referr1_head;
            }

            public String getReferr2_head() {
                return referr2_head;
            }

            public void setReferr2_head(String referr2_head) {
                this.referr2_head = referr2_head;
            }

            public String getReferr3_head() {
                return referr3_head;
            }

            public void setReferr3_head(String referr3_head) {
                this.referr3_head = referr3_head;
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

            public String getUser_top() {
                return user_top;
            }

            public void setUser_top(String user_top) {
                this.user_top = user_top;
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

            public String getTop_user_name() {
                return top_user_name;
            }

            public void setTop_user_name(String top_user_name) {
                this.top_user_name = top_user_name;
            }
        }

        public static class _$1Bean {
            /**
             * user_id : 6
             * user_reb_id : 1
             * user_grade : 3
             * user_name : 15731158255
             * user_tel : 15731158255
             * user_head_img :
             * user_is_vip : 1
             * user_top : 3
             * user_referr1 : 16
             * user_referr2 : 17
             * user_referr3 : 18
             * top_user_name : 第二-1
             */

            private String user_id;
            private String user_reb_id;
            private String user_grade;
            private String user_name;
            private String user_tel;
            private String user_head_img;
            private String user_is_vip;
            private String user_top;
            private String user_referr1;
            private String user_referr2;
            private String user_referr3;
            private String top_user_name;
            private String referr1_head;
            private String referr2_head;
            private String referr3_head;

            public String getReferr1_head() {
                return referr1_head;
            }

            public void setReferr1_head(String referr1_head) {
                this.referr1_head = referr1_head;
            }

            public String getReferr2_head() {
                return referr2_head;
            }

            public void setReferr2_head(String referr2_head) {
                this.referr2_head = referr2_head;
            }

            public String getReferr3_head() {
                return referr3_head;
            }

            public void setReferr3_head(String referr3_head) {
                this.referr3_head = referr3_head;
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

            public String getUser_top() {
                return user_top;
            }

            public void setUser_top(String user_top) {
                this.user_top = user_top;
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

            public String getTop_user_name() {
                return top_user_name;
            }

            public void setTop_user_name(String top_user_name) {
                this.top_user_name = top_user_name;
            }
        }

        public static class _$2Bean {
            /**
             * user_id : 16
             * user_reb_id : 1
             * user_grade : 4
             * user_name : 第四-1-1
             * user_tel : 1
             * user_head_img :
             * user_is_vip : 1
             * user_top : 6
             * user_referr1 :
             * user_referr2 :
             * user_referr3 :
             * top_user_name : 15731158255
             */

            private String user_id;
            private String user_reb_id;
            private String user_grade;
            private String user_name;
            private String user_tel;
            private String user_head_img;
            private String user_is_vip;
            private String user_top;
            private String user_referr1;
            private String user_referr2;
            private String user_referr3;
            private String top_user_name;
            private String referr1_head;
            private String referr2_head;
            private String referr3_head;

            public String getReferr1_head() {
                return referr1_head;
            }

            public void setReferr1_head(String referr1_head) {
                this.referr1_head = referr1_head;
            }

            public String getReferr2_head() {
                return referr2_head;
            }

            public void setReferr2_head(String referr2_head) {
                this.referr2_head = referr2_head;
            }

            public String getReferr3_head() {
                return referr3_head;
            }

            public void setReferr3_head(String referr3_head) {
                this.referr3_head = referr3_head;
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

            public String getUser_top() {
                return user_top;
            }

            public void setUser_top(String user_top) {
                this.user_top = user_top;
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

            public String getTop_user_name() {
                return top_user_name;
            }

            public void setTop_user_name(String top_user_name) {
                this.top_user_name = top_user_name;
            }
        }
    }
}

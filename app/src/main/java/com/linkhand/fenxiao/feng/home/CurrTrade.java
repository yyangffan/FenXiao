package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * 字母币兑换记录实体类
 */

public class CurrTrade {

    /**
     * code : 100
     * info : [{"trade_id":"655","trade_mater_money":"-10.00","trade_son_money":"0.00","trade_type":"5","trade_time":"1529398801","type_str":"发布购物券信息"},{"trade_id":"657","trade_mater_money":"2.00","trade_son_money":"-2.00","trade_type":"7","trade_time":"1529398819","type_str":"购物券兑换"}]
     * success : 返回成功
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
         * trade_id : 655
         * trade_mater_money : -10.00
         * trade_son_money : 0.00
         * trade_type : 5
         * trade_time : 1529398801
         * type_str : 发布购物券信息
         */

        private String trade_id;
        private String trade_mater_money;
        private String trade_son_money;
        private String trade_type;
        private String trade_time;
        private String type_str;

        public InfoBean(String trade_id, String trade_mater_money, String trade_son_money, String trade_type, String trade_time, String type_str) {
            this.trade_id = trade_id;
            this.trade_mater_money = trade_mater_money;
            this.trade_son_money = trade_son_money;
            this.trade_type = trade_type;
            this.trade_time = trade_time;
            this.type_str = type_str;
        }

        public String getTrade_id() {
            return trade_id;
        }

        public void setTrade_id(String trade_id) {
            this.trade_id = trade_id;
        }

        public String getTrade_mater_money() {
            return trade_mater_money;
        }

        public void setTrade_mater_money(String trade_mater_money) {
            this.trade_mater_money = trade_mater_money;
        }

        public String getTrade_son_money() {
            return trade_son_money;
        }

        public void setTrade_son_money(String trade_son_money) {
            this.trade_son_money = trade_son_money;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getTrade_time() {
            return trade_time;
        }

        public void setTrade_time(String trade_time) {
            this.trade_time = trade_time;
        }

        public String getType_str() {
            return type_str;
        }

        public void setType_str(String type_str) {
            this.type_str = type_str;
        }
    }
}

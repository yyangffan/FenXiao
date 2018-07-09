package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 杨帆 on 2018/6/25.
 */

public class MineJlBean {

    /**
     * code : 100
     * info : [{"trade_id":708,"money":"60.00","trade_type":13,"trade_time":"1529547530"},{"trade_id":707,"money":"20.00","trade_type":13,"trade_time":"1529547529"},{"trade_id":704,"money":"10.00","trade_type":13,"trade_time":"1529547379"},{"trade_id":705,"money":"20.00","trade_type":13,"trade_time":"1529547379"},{"trade_id":703,"money":"10.00","trade_type":12,"trade_time":"1529545601"},{"trade_id":680,"money":"100.00","trade_type":8,"trade_time":"1529464950","type_str":"充值"},{"trade_id":675,"money":"4.00","trade_type":1,"trade_time":"1529464070","type_str":"返利"}]
     * success : 返回成功
     */

    private int  code;
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
         * trade_id : 708
         * money : 60.00
         * trade_type : 13
         * trade_time : 1529547530
         * type_str : 充值
         * unit
         */

        private String trade_id;
        private String money;
        private String trade_type;
        private String trade_time;
        private String type_str;
        private String unit;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getTrade_id() {
            return trade_id;
        }

        public void setTrade_id(String trade_id) {
            this.trade_id = trade_id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
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

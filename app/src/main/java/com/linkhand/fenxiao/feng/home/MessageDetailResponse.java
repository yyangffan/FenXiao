package com.linkhand.fenxiao.feng.home;

/**
 * Created by user on 2018/4/27.
 */

public class MessageDetailResponse {

    /**
     * code : 100
     * info : {"trade_id":493,"user_id":46,"trade_mater_money":"0.00","trade_son_money":"0.00","trade_money":"-27.90","trade_type":4,"trade_state":1,"order_number":"","trade_time":"1524793265","trade_text":"您升级vip所购买的商品夏天韩版运动帽男士棒球帽速干鸭舌帽单款男帽子新春夏季帽子 男式已购买成功，升级vip成功，请前往vip商品订单查看","trade_look":0}
     * success : 获取成功
     */

    private int code;
    private InfoBean info;
    private String success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public static class InfoBean {
        /**
         * trade_id : 493
         * user_id : 46
         * trade_mater_money : 0.00
         * trade_son_money : 0.00
         * trade_money : -27.90
         * trade_type : 4
         * trade_state : 1
         * order_number :
         * trade_time : 1524793265
         * trade_text : 您升级vip所购买的商品夏天韩版运动帽男士棒球帽速干鸭舌帽单款男帽子新春夏季帽子 男式已购买成功，升级vip成功，请前往vip商品订单查看
         * trade_look : 0
         */

        private int trade_id;
        private int user_id;
        private String trade_mater_money;
        private String trade_son_money;
        private String trade_money;
        private int trade_type;
        private int trade_state;
        private String order_number;
        private String trade_time;
        private String trade_text;
        private int trade_look;

        public int getTrade_id() {
            return trade_id;
        }

        public void setTrade_id(int trade_id) {
            this.trade_id = trade_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public String getTrade_money() {
            return trade_money;
        }

        public void setTrade_money(String trade_money) {
            this.trade_money = trade_money;
        }

        public int getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(int trade_type) {
            this.trade_type = trade_type;
        }

        public int getTrade_state() {
            return trade_state;
        }

        public void setTrade_state(int trade_state) {
            this.trade_state = trade_state;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getTrade_time() {
            return trade_time;
        }

        public void setTrade_time(String trade_time) {
            this.trade_time = trade_time;
        }

        public String getTrade_text() {
            return trade_text;
        }

        public void setTrade_text(String trade_text) {
            this.trade_text = trade_text;
        }

        public int getTrade_look() {
            return trade_look;
        }

        public void setTrade_look(int trade_look) {
            this.trade_look = trade_look;
        }
    }
}

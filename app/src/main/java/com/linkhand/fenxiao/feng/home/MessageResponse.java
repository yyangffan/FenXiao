package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by user on 2018/4/27.
 */

public class MessageResponse {

    /**
     * code : 100
     * info : [{"trade_id":493,"trade_text":"您升级vip所购买的商品夏天韩版运动帽男士棒球帽速干鸭舌帽单款男帽子新春夏季帽子 男式已购买成功，升级vip成功，请前往vip商品订单查看","trade_time":"1524793265","trade_look":0},{"trade_id":492,"trade_text":"您所购买的商品开小差休闲零食大礼包美式球型玉米爆米花袋装,开小差休闲零食大礼包美式球型玉米爆米花袋装,已支付成功，请前往订单查看","trade_time":"1524791832","trade_look":0},{"trade_id":491,"trade_text":"您所购买的商品开小差休闲零食大礼包美式球型玉米爆米花袋装,开小差休闲零食大礼包美式球型玉米爆米花袋装,已支付成功，请前往订单查看","trade_time":"1524729017","trade_look":0}]
     * success : 获取成功
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
         * trade_id : 493
         * trade_text : 您升级vip所购买的商品夏天韩版运动帽男士棒球帽速干鸭舌帽单款男帽子新春夏季帽子 男式已购买成功，升级vip成功，请前往vip商品订单查看
         * trade_time : 1524793265
         * trade_look : 0
         */

        private int trade_id;
        private String trade_text;
        private String trade_time;
        private int trade_look;

        public int getTrade_id() {
            return trade_id;
        }

        public void setTrade_id(int trade_id) {
            this.trade_id = trade_id;
        }

        public String getTrade_text() {
            return trade_text;
        }

        public void setTrade_text(String trade_text) {
            this.trade_text = trade_text;
        }

        public String getTrade_time() {
            return trade_time;
        }

        public void setTrade_time(String trade_time) {
            this.trade_time = trade_time;
        }

        public int getTrade_look() {
            return trade_look;
        }

        public void setTrade_look(int trade_look) {
            this.trade_look = trade_look;
        }
    }
}

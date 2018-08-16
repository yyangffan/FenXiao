package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 杨帆 on 2018/6/27.
 */

public class TuiHBean {
    /**
     * code : 100
     * success : 查询成功
     * info : [{"order_id":359,"order_batch":"39-1","order_number":"20180620141640102339","order_kuaidi_number":"","order_kuaidi":"","user_id":23,"good_id":39,"site_name":"赵日成","site_tel":"13363822224","site_city":"河北省石家庄市无极县","site_detail":"哪里","order_good_name":"t恤男短袖圆领宽松夏季休闲纯棉纯色潮流百搭白打底衫青年黑体恤","order_good_num":1,"order_speci_vals":"124","order_mater_money":"310.00","order_son_money":"310.00","order_money":"0.00","order_state":2,"order_down_time":"1529475400","order_send_time":"","order_ok_time":"","order_del":0,"order_quit":1,"img_url":"/public/uploads/2018-06-20/4beb34767a9817bae9aea66fb102fe33.jpg","speci":[{"gsp_value":"蓝色","speci_name":"颜色"}]},{"order_id":362,"order_batch":"39-2","order_number":"20180620143129102339","order_kuaidi_number":"asdfasasd","order_kuaidi":"61","user_id":23,"good_id":39,"site_name":"赵日成","site_tel":"13363822224","site_city":"河北省石家庄市无极县","site_detail":"哪里","order_good_name":"t恤男短袖圆领宽松夏季休闲纯棉纯色潮流百搭白打底衫青年黑体恤","order_good_num":3,"order_speci_vals":"124","order_mater_money":"930.00","order_son_money":"930.00","order_money":"0.00","order_state":5,"order_down_time":"1529476289","order_send_time":"1529477891","order_ok_time":"","order_del":0,"order_quit":2,"img_url":"/public/uploads/2018-06-20/4beb34767a9817bae9aea66fb102fe33.jpg","speci":[{"gsp_value":"蓝色","speci_name":"颜色"}]}]
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
         * order_id : 359
         * order_batch : 39-1
         * order_number : 20180620141640102339
         * order_kuaidi_number :
         * order_kuaidi :
         * user_id : 23
         * good_id : 39
         * site_name : 赵日成
         * site_tel : 13363822224
         * site_city : 河北省石家庄市无极县
         * site_detail : 哪里
         * order_good_name : t恤男短袖圆领宽松夏季休闲纯棉纯色潮流百搭白打底衫青年黑体恤
         * order_good_num : 1
         * order_speci_vals : 124
         * order_mater_money : 310.00
         * order_son_money : 310.00
         * order_money : 0.00
         * order_state : 2
         * order_down_time : 1529475400
         * order_send_time :
         * order_ok_time :
         * order_del : 0
         * order_quit : 1
         * img_url : /public/uploads/2018-06-20/4beb34767a9817bae9aea66fb102fe33.jpg
         * speci : [{"gsp_value":"蓝色","speci_name":"颜色"}]
         */

        private int order_id;
        private String order_batch;
        private String order_number;
        private String order_kuaidi_number;
        private String order_kuaidi;
        private int user_id;
        private int good_id;
        private String site_name;
        private String site_tel;
        private String site_city;
        private String site_detail;
        private String order_good_name;
        private int order_good_num;
        private String order_speci_vals;
        private String order_mater_money;
        private String order_son_money;
        private String order_money;
        private int order_state;
        private String order_down_time;
        private String order_send_time;
        private String order_ok_time;
        private int order_del;
        private int order_quit;
        private String img_url;
        private String state_str;
        private List<SpeciBean> speci;

        public String getState_str() {
            return state_str;
        }

        public void setState_str(String state_str) {
            this.state_str = state_str;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_batch() {
            return order_batch;
        }

        public void setOrder_batch(String order_batch) {
            this.order_batch = order_batch;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getOrder_kuaidi_number() {
            return order_kuaidi_number;
        }

        public void setOrder_kuaidi_number(String order_kuaidi_number) {
            this.order_kuaidi_number = order_kuaidi_number;
        }

        public String getOrder_kuaidi() {
            return order_kuaidi;
        }

        public void setOrder_kuaidi(String order_kuaidi) {
            this.order_kuaidi = order_kuaidi;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getGood_id() {
            return good_id;
        }

        public void setGood_id(int good_id) {
            this.good_id = good_id;
        }

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

        public String getSite_tel() {
            return site_tel;
        }

        public void setSite_tel(String site_tel) {
            this.site_tel = site_tel;
        }

        public String getSite_city() {
            return site_city;
        }

        public void setSite_city(String site_city) {
            this.site_city = site_city;
        }

        public String getSite_detail() {
            return site_detail;
        }

        public void setSite_detail(String site_detail) {
            this.site_detail = site_detail;
        }

        public String getOrder_good_name() {
            return order_good_name;
        }

        public void setOrder_good_name(String order_good_name) {
            this.order_good_name = order_good_name;
        }

        public int getOrder_good_num() {
            return order_good_num;
        }

        public void setOrder_good_num(int order_good_num) {
            this.order_good_num = order_good_num;
        }

        public String getOrder_speci_vals() {
            return order_speci_vals;
        }

        public void setOrder_speci_vals(String order_speci_vals) {
            this.order_speci_vals = order_speci_vals;
        }

        public String getOrder_mater_money() {
            return order_mater_money;
        }

        public void setOrder_mater_money(String order_mater_money) {
            this.order_mater_money = order_mater_money;
        }

        public String getOrder_son_money() {
            return order_son_money;
        }

        public void setOrder_son_money(String order_son_money) {
            this.order_son_money = order_son_money;
        }

        public String getOrder_money() {
            return order_money;
        }

        public void setOrder_money(String order_money) {
            this.order_money = order_money;
        }

        public int getOrder_state() {
            return order_state;
        }

        public void setOrder_state(int order_state) {
            this.order_state = order_state;
        }

        public String getOrder_down_time() {
            return order_down_time;
        }

        public void setOrder_down_time(String order_down_time) {
            this.order_down_time = order_down_time;
        }

        public String getOrder_send_time() {
            return order_send_time;
        }

        public void setOrder_send_time(String order_send_time) {
            this.order_send_time = order_send_time;
        }

        public String getOrder_ok_time() {
            return order_ok_time;
        }

        public void setOrder_ok_time(String order_ok_time) {
            this.order_ok_time = order_ok_time;
        }

        public int getOrder_del() {
            return order_del;
        }

        public void setOrder_del(int order_del) {
            this.order_del = order_del;
        }

        public int getOrder_quit() {
            return order_quit;
        }

        public void setOrder_quit(int order_quit) {
            this.order_quit = order_quit;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public List<SpeciBean> getSpeci() {
            return speci;
        }

        public void setSpeci(List<SpeciBean> speci) {
            this.speci = speci;
        }

        public static class SpeciBean {
            /**
             * gsp_value : 蓝色
             * speci_name : 颜色
             */

            private String gsp_value;
            private String speci_name;

            public String getGsp_value() {
                return gsp_value;
            }

            public void setGsp_value(String gsp_value) {
                this.gsp_value = gsp_value;
            }

            public String getSpeci_name() {
                return speci_name;
            }

            public void setSpeci_name(String speci_name) {
                this.speci_name = speci_name;
            }
        }
    }
}

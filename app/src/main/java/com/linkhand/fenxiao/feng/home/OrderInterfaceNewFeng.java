package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by user on 2018/4/23.
 */

public class OrderInterfaceNewFeng {

    /**
     * code : 100
     * info : [{"order_id":"94","good_id":"10","user_id":"6","order_good_name":"开小差休闲零食大礼包美式球型玉米爆米花袋装","order_good_num":"1","order_mater_money":"25.00","order_son_money":"43.00","order_speci_vals":"77","good_img":"/public/uploads/20180420/daeb0163449aba426bfbd15286438011.jpg","speci":[{"gsp_id":"77","speci_name":"大小","gsp_value":"大包"}]}]
     * site : {"site_id":"22","site_name":"测试","site_tel":"15731158396","site_city":"辽宁省辽宁省辽宁省","site_detail":"醉"}
     * allsMoney : 25
     * allsson : 43
     * success : 返回成功
     */

    private int code;
    private int allsMoney;
    private int allsson;
    private String success;
    private List<InfoBean> info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getAllsMoney() {
        return allsMoney;
    }

    public void setAllsMoney(int allsMoney) {
        this.allsMoney = allsMoney;
    }

    public int getAllsson() {
        return allsson;
    }

    public void setAllsson(int allsson) {
        this.allsson = allsson;
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
        public InfoBean(String order_good_num,String order_id, String good_id, String order_good_name, String order_mater_money, String order_son_money, String order_speci_vals, String good_img,List<SpeciBean> lv) {
            this.order_good_num=order_good_num;
            this.order_id = order_id;
            this.good_id = good_id;
            this.order_good_name = order_good_name;
            this.order_mater_money = order_mater_money;
            this.order_son_money = order_son_money;
            this.order_speci_vals = order_speci_vals;
            this.good_img = good_img;
            this.speci=lv;
        }

        /**
         * order_id : 94
         * good_id : 10
         * user_id : 6
         * order_good_name : 开小差休闲零食大礼包美式球型玉米爆米花袋装
         * order_good_num : 1
         * order_mater_money : 25.00
         * order_son_money : 43.00
         * order_speci_vals : 77
         * good_img : /public/uploads/20180420/daeb0163449aba426bfbd15286438011.jpg
         * speci : [{"gsp_id":"77","speci_name":"大小","gsp_value":"大包"}]
         */


        private String order_id;
        private String good_id;
        private String user_id;
        private String order_good_name;
        private String order_good_num;
        private String order_mater_money;
        private String order_son_money;
        private String order_speci_vals;
        private String good_img;
        private List<SpeciBean> speci;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOrder_good_name() {
            return order_good_name;
        }

        public void setOrder_good_name(String order_good_name) {
            this.order_good_name = order_good_name;
        }

        public String getOrder_good_num() {
            return order_good_num;
        }

        public void setOrder_good_num(String order_good_num) {
            this.order_good_num = order_good_num;
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

        public String getOrder_speci_vals() {
            return order_speci_vals;
        }

        public void setOrder_speci_vals(String order_speci_vals) {
            this.order_speci_vals = order_speci_vals;
        }

        public String getGood_img() {
            return good_img;
        }

        public void setGood_img(String good_img) {
            this.good_img = good_img;
        }

        public List<SpeciBean> getSpeci() {
            return speci;
        }

        public void setSpeci(List<SpeciBean> speci) {
            this.speci = speci;
        }

        public static class SpeciBean {
            public SpeciBean(String gsp_id, String speci_name, String gsp_value) {
                this.gsp_id = gsp_id;
                this.speci_name = speci_name;
                this.gsp_value = gsp_value;
            }

            /**
             * gsp_id : 77
             * speci_name : 大小
             * gsp_value : 大包
             */


            private String gsp_id;
            private String speci_name;
            private String gsp_value;

            public String getGsp_id() {
                return gsp_id;
            }

            public void setGsp_id(String gsp_id) {
                this.gsp_id = gsp_id;
            }

            public String getSpeci_name() {
                return speci_name;
            }

            public void setSpeci_name(String speci_name) {
                this.speci_name = speci_name;
            }

            public String getGsp_value() {
                return gsp_value;
            }

            public void setGsp_value(String gsp_value) {
                this.gsp_value = gsp_value;
            }
        }
    }
}

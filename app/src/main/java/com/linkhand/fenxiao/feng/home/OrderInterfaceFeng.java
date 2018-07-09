package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/28.
 */

public class OrderInterfaceFeng {

    /**
     * code : 100
     * info : [{"good_id":"4","good_name":"商品1","good_img":"/public/uploads/20171227/a6220b4ef6f7acdd4bf1431729d91dae.jpg","order_num":"1","onemater":30,"oneson":30,"allmater":30,"allson":30,"speci":[{"gsp_id":"1","speci_name":"颜色","gsp_value":"蓝色色色色色色色色色"},{"gsp_id":"3","speci_name":"尺寸","gsp_value":"100"}]}]
     * allsMoney : 30
     * allsson : 30
     */

    private int code;
    private int allsMoney;
    private int allsson;
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

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * good_id : 4
         * good_name : 商品1
         * good_img : /public/uploads/20171227/a6220b4ef6f7acdd4bf1431729d91dae.jpg
         * order_num : 1
         * onemater : 30
         * oneson : 30
         * allmater : 30
         * allson : 30
         * speci : [{"gsp_id":"1","speci_name":"颜色","gsp_value":"蓝色色色色色色色色色"},{"gsp_id":"3","speci_name":"尺寸","gsp_value":"100"}]
         */

        private String good_id;
        private String good_name;
        private String good_img;
        private String order_num;
        private int onemater;
        private int oneson;
        private int allmater;
        private int allson;
        private List<SpeciBean> speci;

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public String getGood_name() {
            return good_name;
        }

        public void setGood_name(String good_name) {
            this.good_name = good_name;
        }

        public String getGood_img() {
            return good_img;
        }

        public void setGood_img(String good_img) {
            this.good_img = good_img;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public int getOnemater() {
            return onemater;
        }

        public void setOnemater(int onemater) {
            this.onemater = onemater;
        }

        public int getOneson() {
            return oneson;
        }

        public void setOneson(int oneson) {
            this.oneson = oneson;
        }

        public int getAllmater() {
            return allmater;
        }

        public void setAllmater(int allmater) {
            this.allmater = allmater;
        }

        public int getAllson() {
            return allson;
        }

        public void setAllson(int allson) {
            this.allson = allson;
        }

        public List<SpeciBean> getSpeci() {
            return speci;
        }

        public void setSpeci(List<SpeciBean> speci) {
            this.speci = speci;
        }

        public static class SpeciBean {
            /**
             * gsp_id : 1
             * speci_name : 颜色
             * gsp_value : 蓝色色色色色色色色色
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

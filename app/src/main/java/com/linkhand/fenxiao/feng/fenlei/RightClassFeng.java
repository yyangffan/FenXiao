package com.linkhand.fenxiao.feng.fenlei;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/26.
 */

public class RightClassFeng {


    /**
     * code : 100
     * info : {"cate":[{"cate_id":"23","cate_name":"神器1","cate_pid":"20","cate_img":"/public/uploads/20180210/0a510182fc839324fefbf9d40aa60bbc.jpg","cate_is_del":"1"},{"cate_id":"24","cate_name":"神器2","cate_pid":"20","cate_img":"/public/uploads/20180210/15d5d24357101ce47ccce1c57ee9109a.jpg","cate_is_del":"1"},{"cate_id":"25","cate_name":"神器3","cate_pid":"20","cate_img":"/public/uploads/20180210/3e66cad9078272e231b7229aa11229fb.jpg","cate_is_del":"1"}],"brand":[{"brand_id":"1","brand_name":"品牌1","brand_img":"/public/uploads/20180210/9fbfdf5987f6556ebf54a05e45009de5.png","brand_is_del":"1"},{"brand_id":"2","brand_name":"品牌二","brand_img":"/public/uploads/20180210/137dd621c3bf53e855be8e57457e68b1.jpg","brand_is_del":"1"},{"brand_id":"3","brand_name":"吃吃吃吃","brand_img":"/public/uploads/20180210/e868a75d0a6750c0fc8b7277ede9996f.jpg","brand_is_del":"1"}],"title":["分类","品牌"]}
     * success : 返回成功
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
        private List<CateBean> cate;
        private List<BrandBean> brand;
        private List<String> title;

        public List<CateBean> getCate() {
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
        }

        public List<BrandBean> getBrand() {
            return brand;
        }

        public void setBrand(List<BrandBean> brand) {
            this.brand = brand;
        }

        public List<String> getTitle() {
            return title;
        }

        public void setTitle(List<String> title) {
            this.title = title;
        }

        public static class CateBean {
            /**
             * cate_id : 23
             * cate_name : 神器1
             * cate_pid : 20
             * cate_img : /public/uploads/20180210/0a510182fc839324fefbf9d40aa60bbc.jpg
             * cate_is_del : 1
             */

            private String cate_id;
            private String cate_name;
            private String cate_pid;
            private String cate_img;
            private String cate_is_del;

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public String getCate_name() {
                return cate_name;
            }

            public void setCate_name(String cate_name) {
                this.cate_name = cate_name;
            }

            public String getCate_pid() {
                return cate_pid;
            }

            public void setCate_pid(String cate_pid) {
                this.cate_pid = cate_pid;
            }

            public String getCate_img() {
                return cate_img;
            }

            public void setCate_img(String cate_img) {
                this.cate_img = cate_img;
            }

            public String getCate_is_del() {
                return cate_is_del;
            }

            public void setCate_is_del(String cate_is_del) {
                this.cate_is_del = cate_is_del;
            }
        }

        public static class BrandBean {
            /**
             * brand_id : 1
             * brand_name : 品牌1
             * brand_img : /public/uploads/20180210/9fbfdf5987f6556ebf54a05e45009de5.png
             * brand_is_del : 1
             */

            private String brand_id;
            private String brand_name;
            private String brand_img;
            private String brand_is_del;

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public String getBrand_img() {
                return brand_img;
            }

            public void setBrand_img(String brand_img) {
                this.brand_img = brand_img;
            }

            public String getBrand_is_del() {
                return brand_is_del;
            }

            public void setBrand_is_del(String brand_is_del) {
                this.brand_is_del = brand_is_del;
            }
        }
    }
}

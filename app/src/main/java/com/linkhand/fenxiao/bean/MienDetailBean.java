package com.linkhand.fenxiao.bean;

/**
 * Created by user on 2018/5/5.
 */

public class MienDetailBean {

    /**
     * code : 200
     * info : {"ar_id":1,"ar_title":"标题","ar_img_url":"/public/uploads/20180420/bff9382972f36b47a39dd7e23544944c.png","ar_content":"啊沙发沙发沙发沙发擦拭发生大事发生 < /p>","ar_mater":"10.00","ar_son":"0.00","ar_sort":5,"praise":1,"ar_add_time":"1","ar_is_del":1,"is_praise":1}
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
        /**
         * ar_id : 1
         * ar_title : 标题
         * ar_img_url : /public/uploads/20180420/bff9382972f36b47a39dd7e23544944c.png
         * ar_content : 啊沙发沙发沙发沙发擦拭发生大事发生 < /p>
         * ar_mater : 10.00
         * ar_son : 0.00
         * ar_sort : 5
         * praise : 1
         * ar_add_time : 1
         * ar_is_del : 1
         * is_praise : 1
         */

        private int ar_id;
        private String ar_title;
        private String ar_img_url;
        private String ar_content;
        private String ar_mater;
        private String ar_son;
        private int ar_sort;
        private String praise;
        private String ar_add_time;
        private int ar_is_del;
        private int is_praise;

        public int getAr_id() {
            return ar_id;
        }

        public void setAr_id(int ar_id) {
            this.ar_id = ar_id;
        }

        public String getAr_title() {
            return ar_title;
        }

        public void setAr_title(String ar_title) {
            this.ar_title = ar_title;
        }

        public String getAr_img_url() {
            return ar_img_url;
        }

        public void setAr_img_url(String ar_img_url) {
            this.ar_img_url = ar_img_url;
        }

        public String getAr_content() {
            return ar_content;
        }

        public void setAr_content(String ar_content) {
            this.ar_content = ar_content;
        }

        public String getAr_mater() {
            return ar_mater;
        }

        public void setAr_mater(String ar_mater) {
            this.ar_mater = ar_mater;
        }

        public String getAr_son() {
            return ar_son;
        }

        public void setAr_son(String ar_son) {
            this.ar_son = ar_son;
        }

        public int getAr_sort() {
            return ar_sort;
        }

        public void setAr_sort(int ar_sort) {
            this.ar_sort = ar_sort;
        }

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public String getAr_add_time() {
            return ar_add_time;
        }

        public void setAr_add_time(String ar_add_time) {
            this.ar_add_time = ar_add_time;
        }

        public int getAr_is_del() {
            return ar_is_del;
        }

        public void setAr_is_del(int ar_is_del) {
            this.ar_is_del = ar_is_del;
        }

        public int getIs_praise() {
            return is_praise;
        }

        public void setIs_praise(int is_praise) {
            this.is_praise = is_praise;
        }
    }
}

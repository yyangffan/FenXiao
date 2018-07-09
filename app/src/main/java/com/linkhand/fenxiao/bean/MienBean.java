package com.linkhand.fenxiao.bean;

import java.util.List;

/**
 * Created by user on 2018/5/5.
 */

public class MienBean {

    /**
     * code : 100
     * info : [{"ar_id":1,"ar_title":"标题","ar_img_url":"/public/uploads/20180420/bff9382972f36b47a39dd7e23544944c.png","ar_add_time":"1","praise":1},{"ar_id":2,"ar_title":"标题1","ar_img_url":"/public/uploads/20180420/2e59eedee09a1f2a0ca7b7fcbedd6ed3.jpg","ar_add_time":"","praise":3}]
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
         * ar_id : 1
         * ar_title : 标题
         * ar_img_url : /public/uploads/20180420/bff9382972f36b47a39dd7e23544944c.png
         * ar_add_time : 1
         * praise : 1
         * is_have 是否已查阅  1:已查阅   0:未查阅
         */

        private String ar_id;
        private String ar_title;
        private String ar_img_url;
        private String ar_add_time;
        private String praise;
        private String is_have;

        public String getIs_have() {
            return is_have;
        }

        public void setIs_have(String is_have) {
            this.is_have = is_have;
        }

        public String getAr_id() {
            return ar_id;
        }

        public void setAr_id(String ar_id) {
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

        public String getAr_add_time() {
            return ar_add_time;
        }

        public void setAr_add_time(String ar_add_time) {
            this.ar_add_time = ar_add_time;
        }

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }
    }
}

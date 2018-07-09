package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/28.
 */

public class SlideshowFeng {

    /**
     * code : 100
     * success : 返回成功
     * info : [{"sl_id":"2","sl_img_url":"/public/uploads/20180227/8d53e44a11a397163c045a900df712b8.jpg","sl_type":"2","sl_jump":"2","sl_sort":"1"},{"sl_id":"1","sl_img_url":"/public/uploads/20180126/ad3e3e916a2911afeb4bdf3a73eb94ea.jpg","sl_type":"1","sl_jump":"21","sl_sort":"1"}]
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
         * sl_id : 2
         * sl_img_url : /public/uploads/20180227/8d53e44a11a397163c045a900df712b8.jpg
         * sl_type : 2
         * sl_jump : 2
         * sl_sort : 1
         */

        private String sl_id;
        private String sl_img_url;
        private String sl_type;
        private String sl_jump;
        private String sl_sort;

        public String getSl_id() {
            return sl_id;
        }

        public void setSl_id(String sl_id) {
            this.sl_id = sl_id;
        }

        public String getSl_img_url() {
            return sl_img_url;
        }

        public void setSl_img_url(String sl_img_url) {
            this.sl_img_url = sl_img_url;
        }

        public String getSl_type() {
            return sl_type;
        }

        public void setSl_type(String sl_type) {
            this.sl_type = sl_type;
        }

        public String getSl_jump() {
            return sl_jump;
        }

        public void setSl_jump(String sl_jump) {
            this.sl_jump = sl_jump;
        }

        public String getSl_sort() {
            return sl_sort;
        }

        public void setSl_sort(String sl_sort) {
            this.sl_sort = sl_sort;
        }
    }
}

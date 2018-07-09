package com.linkhand.fenxiao.feng.fenlei;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/26.
 */

public class LeftClassFeng {


    /**
     * code : 100
     * info : [{"cate_id":"19","cate_name":"零食","cate_pid":"0","cate_img":"/public/uploads/20180210/ae0cf1becf321829c9899365c2018b91.jpg","cate_is_del":"1"},{"cate_id":"20","cate_name":"神器","cate_pid":"0","cate_img":"/public/uploads/20180210/8f7eef4a1e8b6bffdf536619f0d4e065.jpg","cate_is_del":"1"}]
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
         * cate_id : 19
         * cate_name : 零食
         * cate_pid : 0
         * cate_img : /public/uploads/20180210/ae0cf1becf321829c9899365c2018b91.jpg
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
}

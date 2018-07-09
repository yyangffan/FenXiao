package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 杨帆 on 2018/6/26.
 */

public class LiuYanBean {

    /**
     * code : 100
     * success : 返回数据
     * info : [{"msg_id":5,"idea_id":13,"user_id":23,"msg_text":"啊啊啊啊啊奥奥奥奥奥奥奥所付所3","user_name":"赵日成","user_head_img":"/public/uploads/2018-06-21/ff3dd54189b891e26e94b27d050f935b.jpg"},{"msg_id":4,"idea_id":13,"user_id":23,"msg_text":"啊啊啊啊啊奥奥奥奥奥奥奥所付所3","user_name":"赵日成","user_head_img":"/public/uploads/2018-06-21/ff3dd54189b891e26e94b27d050f935b.jpg"},{"msg_id":3,"idea_id":13,"user_id":23,"msg_text":"啊啊啊啊啊奥奥奥奥奥奥奥所付所2","user_name":"赵日成","user_head_img":"/public/uploads/2018-06-21/ff3dd54189b891e26e94b27d050f935b.jpg"},{"msg_id":2,"idea_id":13,"user_id":23,"msg_text":"啊啊啊啊啊奥奥奥奥奥奥奥所付所1","user_name":"赵日成","user_head_img":"/public/uploads/2018-06-21/ff3dd54189b891e26e94b27d050f935b.jpg"},{"msg_id":1,"idea_id":13,"user_id":23,"msg_text":"啊啊啊啊啊奥奥奥奥奥奥奥所付所","user_name":"赵日成","user_head_img":"/public/uploads/2018-06-21/ff3dd54189b891e26e94b27d050f935b.jpg"}]
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
         * msg_id : 5
         * idea_id : 13
         * user_id : 23
         * msg_text : 啊啊啊啊啊奥奥奥奥奥奥奥所付所3
         * user_name : 赵日成
         * user_head_img : /public/uploads/2018-06-21/ff3dd54189b891e26e94b27d050f935b.jpg
         */

        private int msg_id;
        private int idea_id;
        private int user_id;
        private String msg_text;
        private String user_name;
        private String user_head_img;

        public int getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(int msg_id) {
            this.msg_id = msg_id;
        }

        public int getIdea_id() {
            return idea_id;
        }

        public void setIdea_id(int idea_id) {
            this.idea_id = idea_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getMsg_text() {
            return msg_text;
        }

        public void setMsg_text(String msg_text) {
            this.msg_text = msg_text;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_head_img() {
            return user_head_img;
        }

        public void setUser_head_img(String user_head_img) {
            this.user_head_img = user_head_img;
        }
    }
}

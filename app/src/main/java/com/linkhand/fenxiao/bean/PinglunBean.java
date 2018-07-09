package com.linkhand.fenxiao.bean;

import java.util.List;

/**
 * Created by 杨帆 on 2018/6/25.
 */

public class PinglunBean {
    /**
     * code : 100
     * info : [{"evaluate_id":57,"evaluate_content":"看见","user_name":"测试","user_head_img":"/public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg","eimg":["/public/uploads/20180321/14c79ff78754e57fd4b5ad5395953edb.jpg","/public/uploads/20180321/e98379d9452309e1a3e3ff267f4a99de.jpg","/public/uploads/20180321/088141a0492581bb8da481f9a0e68cd2.png"]},{"evaluate_id":56,"evaluate_content":"看见","user_name":"测试","user_head_img":"/public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg","eimg":[]},{"evaluate_id":55,"evaluate_content":"图裂了","user_name":"测试","user_head_img":"/public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg","eimg":["/public/uploads/20180324/f3cb1a3ddae5729b70830812f657ef51.jpg","/public/uploads/20180324/a0a637747c788cf0d9f5143c7b11ac7a.jpg","/public/uploads/20180324/3b8bc983d1d6cc69cfc3312c194d19a2.jpg","/public/uploads/20180324/f33ee2b789f63a49af26c29bccdcc4fa.jpg"]}]
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
        public InfoBean(String evaluate_content, String user_name, String user_head_img) {
            this.evaluate_content = evaluate_content;
            this.user_name = user_name;
            this.user_head_img = user_head_img;
        }

        /**
         * evaluate_id : 57
         * evaluate_content : 看见
         * user_name : 测试
         * user_head_img : /public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg
         * eimg : ["/public/uploads/20180321/14c79ff78754e57fd4b5ad5395953edb.jpg","/public/uploads/20180321/e98379d9452309e1a3e3ff267f4a99de.jpg","/public/uploads/20180321/088141a0492581bb8da481f9a0e68cd2.png"]
         */


        private int evaluate_id;
        private String evaluate_content;
        private String user_name;
        private String user_head_img;
        private List<String> eimg;

        public int getEvaluate_id() {
            return evaluate_id;
        }

        public void setEvaluate_id(int evaluate_id) {
            this.evaluate_id = evaluate_id;
        }

        public String getEvaluate_content() {
            return evaluate_content;
        }

        public void setEvaluate_content(String evaluate_content) {
            this.evaluate_content = evaluate_content;
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

        public List<String> getEimg() {
            return eimg;
        }

        public void setEimg(List<String> eimg) {
            this.eimg = eimg;
        }
    }
}

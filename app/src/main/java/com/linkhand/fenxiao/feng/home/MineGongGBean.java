package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * 主页循环显示公告实体类
 */

public class MineGongGBean {

    /**
     * code : 100
     * info : [{"no_id":1,"no_text":"公告1公告1公告1公告1公告1公告1","no_time":1530068805},{"no_id":2,"no_text":"公告2要长一点公告2要长一点公告2要长一点公告2要长一点公告2要长一点公告2要长一点公告2要长一点公告2要长一点公告2要长一点公告2要长一点公告2要长一点","no_time":1530068805},{"no_id":1,"no_text":"公告33333333333333333333333","no_time":1530068805},{"no_id":1,"no_text":"公告4444啊啊啊啊啊啊啊啊啊","no_time":1530068805}]
     * success : 数据返回
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
         * no_id : 1
         * no_text : 公告1公告1公告1公告1公告1公告1
         * no_time : 1530068805
         */

        private int no_id;
        private String no_text;
        private int no_time;

        public int getNo_id() {
            return no_id;
        }

        public void setNo_id(int no_id) {
            this.no_id = no_id;
        }

        public String getNo_text() {
            return no_text;
        }

        public void setNo_text(String no_text) {
            this.no_text = no_text;
        }

        public int getNo_time() {
            return no_time;
        }

        public void setNo_time(int no_time) {
            this.no_time = no_time;
        }
    }
}

package com.linkhand.fenxiao.bean;

/**
 * Created by 杨帆 on 2018/8/1.
 */

public class ShareBean {

    /**
     * code : 100
     * info : {"link":"http://sjfx.linghangnc.com/index.php/sjapi/share/share_idea_html?idea_id=12","title":"友趣团购","desc":"来自友趣团购的分享"}
     * success : 地址返回
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
         * link : http://sjfx.linghangnc.com/index.php/sjapi/share/share_idea_html?idea_id=12
         * title : 友趣团购
         * desc : 来自友趣团购的分享
         */

        private String link;
        private String title;
        private String desc;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}

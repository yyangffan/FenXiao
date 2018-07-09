package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/3/8.
 */

public class IdeaGoodsDetailsFeng {

    /**
     * code : 100
     * info : {"idea_id":"10","idea_name":"夏季遮阳帽男速干登山帽大檐渔夫帽防晒帽户外钓鱼帽太阳帽帽子男","idea_mater":"40.00","idea_son":"60.00","idea_money":"55.00","idea_num":"30","idea_nownum":"2","idea_intro":"<p><img src=\"http://sjfx.linghangnc.com/ueditor/php/upload/image/20180421/1524295276210878.jpg\" title=\"1524295276210878.jpg\"/><\/p><p><img src=\"http://sjfx.linghangnc.com/ueditor/php/upload/image/20180421/1524295276949886.jpg\" title=\"1524295276949886.jpg\"/><\/p><p><img src=\"http://sjfx.linghangnc.com/ueditor/php/upload/image/20180421/1524295276182783.jpg\" title=\"1524295276182783.jpg\"/><\/p><p><br/><\/p>","idea_img":"/public/uploads/20180421/ebcf83fe2e0a9f73e1bcbed3b72f324d.jpg","idea_is_top":"1","idea_time":"1524295281","idea_end_time":"1525017600","img":[{"idea_img_url":"/public/uploads/20180421/7374987d808136dfe6b5d7b307f38620.jpg"},{"idea_img_url":"/public/uploads/20180421/2e9506679de93f870f7c3fad44f3fa62.jpg"}],"eval":[{"evaluate_id":"57","evaluate_content":"看见","user_name":"测试","user_head_img":"/public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg","eimg":["/public/uploads/20180321/14c79ff78754e57fd4b5ad5395953edb.jpg","/public/uploads/20180321/e98379d9452309e1a3e3ff267f4a99de.jpg","/public/uploads/20180321/088141a0492581bb8da481f9a0e68cd2.png"]},{"evaluate_id":"56","evaluate_content":"看见","user_name":"测试","user_head_img":"/public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg","eimg":[]},{"evaluate_id":"55","evaluate_content":"图裂了","user_name":"测试","user_head_img":"/public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg","eimg":["/public/uploads/20180324/f3cb1a3ddae5729b70830812f657ef51.jpg","/public/uploads/20180324/a0a637747c788cf0d9f5143c7b11ac7a.jpg","/public/uploads/20180324/3b8bc983d1d6cc69cfc3312c194d19a2.jpg","/public/uploads/20180324/f33ee2b789f63a49af26c29bccdcc4fa.jpg"]}],"have":1,"house_have":0}
     */

    private int code;
    private InfoBean info;

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

    public static class InfoBean {
        /**
         * idea_id : 10
         * idea_name : 夏季遮阳帽男速干登山帽大檐渔夫帽防晒帽户外钓鱼帽太阳帽帽子男
         * idea_mater : 40.00
         * idea_son : 60.00
         * idea_money : 55.00
         * idea_num : 30
         * idea_nownum : 2
         * idea_intro : <p><img src="http://sjfx.linghangnc.com/ueditor/php/upload/image/20180421/1524295276210878.jpg" title="1524295276210878.jpg"/></p><p><img src="http://sjfx.linghangnc.com/ueditor/php/upload/image/20180421/1524295276949886.jpg" title="1524295276949886.jpg"/></p><p><img src="http://sjfx.linghangnc.com/ueditor/php/upload/image/20180421/1524295276182783.jpg" title="1524295276182783.jpg"/></p><p><br/></p>
         * idea_img : /public/uploads/20180421/ebcf83fe2e0a9f73e1bcbed3b72f324d.jpg
         * idea_is_top : 1
         * idea_time : 1524295281
         * idea_end_time : 1525017600
         * img : [{"idea_img_url":"/public/uploads/20180421/7374987d808136dfe6b5d7b307f38620.jpg"},{"idea_img_url":"/public/uploads/20180421/2e9506679de93f870f7c3fad44f3fa62.jpg"}]
         * eval : [{"evaluate_id":"57","evaluate_content":"看见","user_name":"测试","user_head_img":"/public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg","eimg":["/public/uploads/20180321/14c79ff78754e57fd4b5ad5395953edb.jpg","/public/uploads/20180321/e98379d9452309e1a3e3ff267f4a99de.jpg","/public/uploads/20180321/088141a0492581bb8da481f9a0e68cd2.png"]},{"evaluate_id":"56","evaluate_content":"看见","user_name":"测试","user_head_img":"/public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg","eimg":[]},{"evaluate_id":"55","evaluate_content":"图裂了","user_name":"测试","user_head_img":"/public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg","eimg":["/public/uploads/20180324/f3cb1a3ddae5729b70830812f657ef51.jpg","/public/uploads/20180324/a0a637747c788cf0d9f5143c7b11ac7a.jpg","/public/uploads/20180324/3b8bc983d1d6cc69cfc3312c194d19a2.jpg","/public/uploads/20180324/f33ee2b789f63a49af26c29bccdcc4fa.jpg"]}]
         * have : 1
         * house_have : 0
         */

        private String idea_id;
        private String idea_name;
        private String idea_mater;
        private String idea_son;
        private String idea_money;
        private String idea_num;
        private String idea_nownum;
        private String idea_intro;
        private String idea_img;
        private String idea_is_top;
        private String idea_time;
        private String idea_end_time;
        private int have;
        private int house_have;
        private List<ImgBean> img;
        private List<EvalBean> eval;

        public String getIdea_id() {
            return idea_id;
        }

        public void setIdea_id(String idea_id) {
            this.idea_id = idea_id;
        }

        public String getIdea_name() {
            return idea_name;
        }

        public void setIdea_name(String idea_name) {
            this.idea_name = idea_name;
        }

        public String getIdea_mater() {
            return idea_mater;
        }

        public void setIdea_mater(String idea_mater) {
            this.idea_mater = idea_mater;
        }

        public String getIdea_son() {
            return idea_son;
        }

        public void setIdea_son(String idea_son) {
            this.idea_son = idea_son;
        }

        public String getIdea_money() {
            return idea_money;
        }

        public void setIdea_money(String idea_money) {
            this.idea_money = idea_money;
        }

        public String getIdea_num() {
            return idea_num;
        }

        public void setIdea_num(String idea_num) {
            this.idea_num = idea_num;
        }

        public String getIdea_nownum() {
            return idea_nownum;
        }

        public void setIdea_nownum(String idea_nownum) {
            this.idea_nownum = idea_nownum;
        }

        public String getIdea_intro() {
            return idea_intro;
        }

        public void setIdea_intro(String idea_intro) {
            this.idea_intro = idea_intro;
        }

        public String getIdea_img() {
            return idea_img;
        }

        public void setIdea_img(String idea_img) {
            this.idea_img = idea_img;
        }

        public String getIdea_is_top() {
            return idea_is_top;
        }

        public void setIdea_is_top(String idea_is_top) {
            this.idea_is_top = idea_is_top;
        }

        public String getIdea_time() {
            return idea_time;
        }

        public void setIdea_time(String idea_time) {
            this.idea_time = idea_time;
        }

        public String getIdea_end_time() {
            return idea_end_time;
        }

        public void setIdea_end_time(String idea_end_time) {
            this.idea_end_time = idea_end_time;
        }

        public int getHave() {
            return have;
        }

        public void setHave(int have) {
            this.have = have;
        }

        public int getHouse_have() {
            return house_have;
        }

        public void setHouse_have(int house_have) {
            this.house_have = house_have;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public List<EvalBean> getEval() {
            return eval;
        }

        public void setEval(List<EvalBean> eval) {
            this.eval = eval;
        }

        public static class ImgBean {
            /**
             * idea_img_url : /public/uploads/20180421/7374987d808136dfe6b5d7b307f38620.jpg
             */

            private String idea_img_url;

            public String getIdea_img_url() {
                return idea_img_url;
            }

            public void setIdea_img_url(String idea_img_url) {
                this.idea_img_url = idea_img_url;
            }
        }

        public static class EvalBean {
            /**
             * evaluate_id : 57
             * evaluate_content : 看见
             * user_name : 测试
             * user_head_img : /public/uploads/20180322/8568c59aab2075f03c63cc72b5680a53.jpg
             * eimg : ["/public/uploads/20180321/14c79ff78754e57fd4b5ad5395953edb.jpg","/public/uploads/20180321/e98379d9452309e1a3e3ff267f4a99de.jpg","/public/uploads/20180321/088141a0492581bb8da481f9a0e68cd2.png"]
             */

            private String evaluate_id;
            private String evaluate_content;
            private String user_name;
            private String user_head_img;
            private List<String> eimg;

            public String getEvaluate_id() {
                return evaluate_id;
            }

            public void setEvaluate_id(String evaluate_id) {
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
}

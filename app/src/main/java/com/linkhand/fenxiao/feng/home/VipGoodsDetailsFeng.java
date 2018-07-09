package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/3/22.
 */

public class VipGoodsDetailsFeng {


    /**
     * code : 100
     * success : 返回成功
     * info : {"vip_id":"1","vip_name":"名字","vip_money":"123.00","vip_desc":"阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿","vip_intro":"阿发沙发沙发沙发","vip_is_top":"1","vip_add_time":"","vip_up_time":"","vimg_url":"/public/uploads/20180126/ad3e3e916a2911afeb4bdf3a73eb94ea.jpg","img":[{"vimg_url":"/public/uploads/20180126/ad3e3e916a2911afeb4bdf3a73eb94ea.jpg"},{"vimg_url":"/public/uploads/20180126/684d05c99f8c63334b44d08c910970e4.png"},{"vimg_url":"/public/uploads/20180126/b2b682d37b08ce72f1dd3334ee58f98d.jpg"},{"vimg_url":"/public/uploads/20180126/73a56d5f5af188aa26d32ff5b53b8f5d.png"}],"speci":[{"speci_id":"2","speci_name":"尺寸","speci_vals":[{"vsp_id":"37","vsp_value":"大的","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/69b3b47f6edaef3a9dc16b544fd08cfb.jpg"},{"vsp_id":"38","vsp_value":"小的","vsp_money":"5.00","vsp_img":"/public/uploads/20180126/b1e5bb68f8428d03dae6ca033ac06e4c.png"}]},{"speci_id":"2","speci_name":"尺寸","speci_vals":[{"vsp_id":"37","vsp_value":"大的","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/69b3b47f6edaef3a9dc16b544fd08cfb.jpg"},{"vsp_id":"38","vsp_value":"小的","vsp_money":"5.00","vsp_img":"/public/uploads/20180126/b1e5bb68f8428d03dae6ca033ac06e4c.png"}]},{"speci_id":"1","speci_name":"颜色","speci_vals":[{"vsp_id":"39","vsp_value":"紫色","vsp_money":"0.00","vsp_img":"/public/uploads/20180126/9b4c6ec232a0fe0653583b27b1b07056.jpg"},{"vsp_id":"40","vsp_value":"灰色","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/2249c179e156ad7fecfbf93e63ecc43d.jpg"},{"vsp_id":"41","vsp_value":"蓝色","vsp_money":"20.00","vsp_img":"/public/uploads/20180126/281b76e9b93f3068f29f3f77bead96e9.jpg"}]},{"speci_id":"1","speci_name":"颜色","speci_vals":[{"vsp_id":"39","vsp_value":"紫色","vsp_money":"0.00","vsp_img":"/public/uploads/20180126/9b4c6ec232a0fe0653583b27b1b07056.jpg"},{"vsp_id":"40","vsp_value":"灰色","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/2249c179e156ad7fecfbf93e63ecc43d.jpg"},{"vsp_id":"41","vsp_value":"蓝色","vsp_money":"20.00","vsp_img":"/public/uploads/20180126/281b76e9b93f3068f29f3f77bead96e9.jpg"}]},{"speci_id":"1","speci_name":"颜色","speci_vals":[{"vsp_id":"39","vsp_value":"紫色","vsp_money":"0.00","vsp_img":"/public/uploads/20180126/9b4c6ec232a0fe0653583b27b1b07056.jpg"},{"vsp_id":"40","vsp_value":"灰色","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/2249c179e156ad7fecfbf93e63ecc43d.jpg"},{"vsp_id":"41","vsp_value":"蓝色","vsp_money":"20.00","vsp_img":"/public/uploads/20180126/281b76e9b93f3068f29f3f77bead96e9.jpg"}]}],"all_num":3}
     */

    private int code;
    private String success;
    private InfoBean info;

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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * vip_id : 1
         * vip_name : 名字
         * vip_money : 123.00
         * vip_desc : 阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿阿
         * vip_intro : 阿发沙发沙发沙发
         * vip_is_top : 1
         * vip_add_time :
         * vip_up_time :
         * vimg_url : /public/uploads/20180126/ad3e3e916a2911afeb4bdf3a73eb94ea.jpg
         * img : [{"vimg_url":"/public/uploads/20180126/ad3e3e916a2911afeb4bdf3a73eb94ea.jpg"},{"vimg_url":"/public/uploads/20180126/684d05c99f8c63334b44d08c910970e4.png"},{"vimg_url":"/public/uploads/20180126/b2b682d37b08ce72f1dd3334ee58f98d.jpg"},{"vimg_url":"/public/uploads/20180126/73a56d5f5af188aa26d32ff5b53b8f5d.png"}]
         * speci : [{"speci_id":"2","speci_name":"尺寸","speci_vals":[{"vsp_id":"37","vsp_value":"大的","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/69b3b47f6edaef3a9dc16b544fd08cfb.jpg"},{"vsp_id":"38","vsp_value":"小的","vsp_money":"5.00","vsp_img":"/public/uploads/20180126/b1e5bb68f8428d03dae6ca033ac06e4c.png"}]},{"speci_id":"2","speci_name":"尺寸","speci_vals":[{"vsp_id":"37","vsp_value":"大的","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/69b3b47f6edaef3a9dc16b544fd08cfb.jpg"},{"vsp_id":"38","vsp_value":"小的","vsp_money":"5.00","vsp_img":"/public/uploads/20180126/b1e5bb68f8428d03dae6ca033ac06e4c.png"}]},{"speci_id":"1","speci_name":"颜色","speci_vals":[{"vsp_id":"39","vsp_value":"紫色","vsp_money":"0.00","vsp_img":"/public/uploads/20180126/9b4c6ec232a0fe0653583b27b1b07056.jpg"},{"vsp_id":"40","vsp_value":"灰色","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/2249c179e156ad7fecfbf93e63ecc43d.jpg"},{"vsp_id":"41","vsp_value":"蓝色","vsp_money":"20.00","vsp_img":"/public/uploads/20180126/281b76e9b93f3068f29f3f77bead96e9.jpg"}]},{"speci_id":"1","speci_name":"颜色","speci_vals":[{"vsp_id":"39","vsp_value":"紫色","vsp_money":"0.00","vsp_img":"/public/uploads/20180126/9b4c6ec232a0fe0653583b27b1b07056.jpg"},{"vsp_id":"40","vsp_value":"灰色","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/2249c179e156ad7fecfbf93e63ecc43d.jpg"},{"vsp_id":"41","vsp_value":"蓝色","vsp_money":"20.00","vsp_img":"/public/uploads/20180126/281b76e9b93f3068f29f3f77bead96e9.jpg"}]},{"speci_id":"1","speci_name":"颜色","speci_vals":[{"vsp_id":"39","vsp_value":"紫色","vsp_money":"0.00","vsp_img":"/public/uploads/20180126/9b4c6ec232a0fe0653583b27b1b07056.jpg"},{"vsp_id":"40","vsp_value":"灰色","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/2249c179e156ad7fecfbf93e63ecc43d.jpg"},{"vsp_id":"41","vsp_value":"蓝色","vsp_money":"20.00","vsp_img":"/public/uploads/20180126/281b76e9b93f3068f29f3f77bead96e9.jpg"}]}]
         * all_num : 3
         */

        private String vip_id;
        private String vip_name;
        private String vip_money;
        private String vip_desc;
        private String vip_intro;
        private String vip_is_top;
        private String vip_add_time;
        private String vip_up_time;
        private String vimg_url;
        private int all_num;
        private List<ImgBean> img;
        private List<SpeciBean> speci;

        public String getVip_id() {
            return vip_id;
        }

        public void setVip_id(String vip_id) {
            this.vip_id = vip_id;
        }

        public String getVip_name() {
            return vip_name;
        }

        public void setVip_name(String vip_name) {
            this.vip_name = vip_name;
        }

        public String getVip_money() {
            return vip_money;
        }

        public void setVip_money(String vip_money) {
            this.vip_money = vip_money;
        }

        public String getVip_desc() {
            return vip_desc;
        }

        public void setVip_desc(String vip_desc) {
            this.vip_desc = vip_desc;
        }

        public String getVip_intro() {
            return vip_intro;
        }

        public void setVip_intro(String vip_intro) {
            this.vip_intro = vip_intro;
        }

        public String getVip_is_top() {
            return vip_is_top;
        }

        public void setVip_is_top(String vip_is_top) {
            this.vip_is_top = vip_is_top;
        }

        public String getVip_add_time() {
            return vip_add_time;
        }

        public void setVip_add_time(String vip_add_time) {
            this.vip_add_time = vip_add_time;
        }

        public String getVip_up_time() {
            return vip_up_time;
        }

        public void setVip_up_time(String vip_up_time) {
            this.vip_up_time = vip_up_time;
        }

        public String getVimg_url() {
            return vimg_url;
        }

        public void setVimg_url(String vimg_url) {
            this.vimg_url = vimg_url;
        }

        public int getAll_num() {
            return all_num;
        }

        public void setAll_num(int all_num) {
            this.all_num = all_num;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public List<SpeciBean> getSpeci() {
            return speci;
        }

        public void setSpeci(List<SpeciBean> speci) {
            this.speci = speci;
        }

        public static class ImgBean {
            /**
             * vimg_url : /public/uploads/20180126/ad3e3e916a2911afeb4bdf3a73eb94ea.jpg
             */

            private String vimg_url;

            public String getVimg_url() {
                return vimg_url;
            }

            public void setVimg_url(String vimg_url) {
                this.vimg_url = vimg_url;
            }
        }

        public static class SpeciBean {
            /**
             * speci_id : 2
             * speci_name : 尺寸
             * speci_vals : [{"vsp_id":"37","vsp_value":"大的","vsp_money":"10.00","vsp_img":"/public/uploads/20180126/69b3b47f6edaef3a9dc16b544fd08cfb.jpg"},{"vsp_id":"38","vsp_value":"小的","vsp_money":"5.00","vsp_img":"/public/uploads/20180126/b1e5bb68f8428d03dae6ca033ac06e4c.png"}]
             */

            private String speci_id;
            private String speci_name;
            private List<SpeciValsBean> speci_vals;

            public String getSpeci_id() {
                return speci_id;
            }

            public void setSpeci_id(String speci_id) {
                this.speci_id = speci_id;
            }

            public String getSpeci_name() {
                return speci_name;
            }

            public void setSpeci_name(String speci_name) {
                this.speci_name = speci_name;
            }

            public List<SpeciValsBean> getSpeci_vals() {
                return speci_vals;
            }

            public void setSpeci_vals(List<SpeciValsBean> speci_vals) {
                this.speci_vals = speci_vals;
            }

            public static class SpeciValsBean {
                /**
                 * vsp_id : 37
                 * vsp_value : 大的
                 * vsp_money : 10.00
                 * vsp_img : /public/uploads/20180126/69b3b47f6edaef3a9dc16b544fd08cfb.jpg
                 */

                private String vsp_id;
                private String vsp_value;
                private String vsp_money;
                private String vsp_img;

                public String getVsp_id() {
                    return vsp_id;
                }

                public void setVsp_id(String vsp_id) {
                    this.vsp_id = vsp_id;
                }

                public String getVsp_value() {
                    return vsp_value;
                }

                public void setVsp_value(String vsp_value) {
                    this.vsp_value = vsp_value;
                }

                public String getVsp_money() {
                    return vsp_money;
                }

                public void setVsp_money(String vsp_money) {
                    this.vsp_money = vsp_money;
                }

                public String getVsp_img() {
                    return vsp_img;
                }

                public void setVsp_img(String vsp_img) {
                    this.vsp_img = vsp_img;
                }
            }
        }
    }
}

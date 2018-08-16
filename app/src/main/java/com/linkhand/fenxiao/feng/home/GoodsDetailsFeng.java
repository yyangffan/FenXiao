package com.linkhand.fenxiao.feng.home;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/26.
 */

public class GoodsDetailsFeng {


    /**
     * code : 100
     * info : {"good_id":"5","good_name":"ssssss","cate_id":"20","cate_two_id":"23","good_mater_money":"100.00","good_son_money":"100.00","brand_id":"3","good_max_num":"1000","good_min_num":"800","good_start_time":"1515568260","good_end_time":"1517328000","good_astrict":"2","idea_id":"0","good_is_top":"1","good_content":"","good_add_time":"1515568260","good_is_hot":"1","img":[{"img_url":"/public/uploads/20180108/5c0937be42768f626c94e44a6b33476b.jpg"},{"img_url":"/public/uploads/20180108/f2c105f4bb7443eb8eb334048fc9b458.jpg"},{"img_url":"/public/uploads/20180108/57ffb739286b018f35994d81e7b08b3e.jpg"},{"img_url":"/public/uploads/20180108/ea24343de0318cdf9e7fc83604a6f79b.jpg"}],"money":{"money_id":"17","good_id":"5","money_num":"100","money_mater":"111.00","money_son":"11.00"},"speci":[{"speci_id":"2","speci_name":"尺寸","speci_vals":[{"gsp_id":"25","gsp_value":"大","gsp_mater_money":"100.00","gsp_son_money":"-20.00","gsp_img":"/public/uploads/20180108/849a45ac38d33016689fad880f5d7a15.png"},{"gsp_id":"26","gsp_value":"小","gsp_mater_money":"0.00","gsp_son_money":"0.00","gsp_img":"/public/uploads/20180108/454e8d3026370b720394ed7a7f268048.jpg"}]}],"attr":[{"attr_name":"长度","attr_val":"200m"},{"attr_name":"型号","attr_val":"xss"}],"all_num":0}
     */

    private int code;
    private InfoBean info;
    private List<BuyRuleBean> buy_rule;

    public List<BuyRuleBean> getBuy_rule() {
        return buy_rule;
    }

    public void setBuy_rule(List<BuyRuleBean> buy_rule) {
        this.buy_rule = buy_rule;
    }

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
         * good_id : 5
         * good_name : ssssss
         * cate_id : 20
         * cate_two_id : 23
         * good_mater_money : 100.00
         * good_son_money : 100.00
         * brand_id : 3
         * good_max_num : 1000
         * good_min_num : 800
         * good_start_time : 1515568260
         * good_end_time : 1517328000
         * good_astrict : 2
         * idea_id : 0
         * good_is_top : 1
         * good_content :
         * good_add_time : 1515568260
         * good_is_hot : 1
         * img : [{"img_url":"/public/uploads/20180108/5c0937be42768f626c94e44a6b33476b.jpg"},{"img_url":"/public/uploads/20180108/f2c105f4bb7443eb8eb334048fc9b458.jpg"},{"img_url":"/public/uploads/20180108/57ffb739286b018f35994d81e7b08b3e.jpg"},{"img_url":"/public/uploads/20180108/ea24343de0318cdf9e7fc83604a6f79b.jpg"}]
         * money : {"money_id":"17","good_id":"5","money_num":"100","money_mater":"111.00","money_son":"11.00"}
         * speci : [{"speci_id":"2","speci_name":"尺寸","speci_vals":[{"gsp_id":"25","gsp_value":"大","gsp_mater_money":"100.00","gsp_son_money":"-20.00","gsp_img":"/public/uploads/20180108/849a45ac38d33016689fad880f5d7a15.png"},{"gsp_id":"26","gsp_value":"小","gsp_mater_money":"0.00","gsp_son_money":"0.00","gsp_img":"/public/uploads/20180108/454e8d3026370b720394ed7a7f268048.jpg"}]}]
         * attr : [{"attr_name":"长度","attr_val":"200m"},{"attr_name":"型号","attr_val":"xss"}]
         * all_num : 0
         */

        private String good_id;
        private String good_name;
        private String cate_id;
        private String cate_two_id;
        private String good_mater_money;
        private String good_son_money;
        private String brand_id;
        private String good_max_num;
        private String good_min_num;
        private String good_start_time;
        private String good_end_time;
        private String good_astrict;
        private String idea_id;
        private String good_is_top;
        private String good_content;
        private String good_add_time;
        private String good_is_hot;
        private MoneyBean money;
        private String is_fan;
        private String fan_str;
        private int all_num;
        private int house;
        private String good_stock;
        private List<ImgBean> img;
        private List<SpeciBean> speci;
        private List<AttrBean> attr;

        public String getGood_stock() {
            return good_stock;
        }

        public void setGood_stock(String good_stock) {
            this.good_stock = good_stock;
        }

        public String getIs_fan() {
            return is_fan;
        }

        public void setIs_fan(String is_fan) {
            this.is_fan = is_fan;
        }

        public String getFan_str() {
            return fan_str;
        }

        public void setFan_str(String fan_str) {
            this.fan_str = fan_str;
        }

        public int getHouse() {
            return house;
        }

        public void setHouse(int house) {
            this.house = house;
        }

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public String getGood_name() {
            return good_name;
        }

        public void setGood_name(String good_name) {
            this.good_name = good_name;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getCate_two_id() {
            return cate_two_id;
        }

        public void setCate_two_id(String cate_two_id) {
            this.cate_two_id = cate_two_id;
        }

        public String getGood_mater_money() {
            return good_mater_money;
        }

        public void setGood_mater_money(String good_mater_money) {
            this.good_mater_money = good_mater_money;
        }

        public String getGood_son_money() {
            return good_son_money;
        }

        public void setGood_son_money(String good_son_money) {
            this.good_son_money = good_son_money;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getGood_max_num() {
            return good_max_num;
        }

        public void setGood_max_num(String good_max_num) {
            this.good_max_num = good_max_num;
        }

        public String getGood_min_num() {
            return good_min_num;
        }

        public void setGood_min_num(String good_min_num) {
            this.good_min_num = good_min_num;
        }

        public String getGood_start_time() {
            return good_start_time;
        }

        public void setGood_start_time(String good_start_time) {
            this.good_start_time = good_start_time;
        }

        public String getGood_end_time() {
            return good_end_time;
        }

        public void setGood_end_time(String good_end_time) {
            this.good_end_time = good_end_time;
        }

        public String getGood_astrict() {
            return good_astrict;
        }

        public void setGood_astrict(String good_astrict) {
            this.good_astrict = good_astrict;
        }

        public String getIdea_id() {
            return idea_id;
        }

        public void setIdea_id(String idea_id) {
            this.idea_id = idea_id;
        }

        public String getGood_is_top() {
            return good_is_top;
        }

        public void setGood_is_top(String good_is_top) {
            this.good_is_top = good_is_top;
        }

        public String getGood_content() {
            return good_content;
        }

        public void setGood_content(String good_content) {
            this.good_content = good_content;
        }

        public String getGood_add_time() {
            return good_add_time;
        }

        public void setGood_add_time(String good_add_time) {
            this.good_add_time = good_add_time;
        }

        public String getGood_is_hot() {
            return good_is_hot;
        }

        public void setGood_is_hot(String good_is_hot) {
            this.good_is_hot = good_is_hot;
        }

        public MoneyBean getMoney() {
            return money;
        }

        public void setMoney(MoneyBean money) {
            this.money = money;
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

        public List<AttrBean> getAttr() {
            return attr;
        }

        public void setAttr(List<AttrBean> attr) {
            this.attr = attr;
        }

        public static class MoneyBean {
            /**
             * money_id : 17
             * good_id : 5
             * money_num : 100
             * money_mater : 111.00
             * money_son : 11.00
             */

            private String money_id;
            private String good_id;
            private String money_num;
            private String money_mater;
            private String money_son;

            public String getMoney_id() {
                return money_id;
            }

            public void setMoney_id(String money_id) {
                this.money_id = money_id;
            }

            public String getGood_id() {
                return good_id;
            }

            public void setGood_id(String good_id) {
                this.good_id = good_id;
            }

            public String getMoney_num() {
                return money_num;
            }

            public void setMoney_num(String money_num) {
                this.money_num = money_num;
            }

            public String getMoney_mater() {
                return money_mater;
            }

            public void setMoney_mater(String money_mater) {
                this.money_mater = money_mater;
            }

            public String getMoney_son() {
                return money_son;
            }

            public void setMoney_son(String money_son) {
                this.money_son = money_son;
            }
        }

        public static class ImgBean {
            /**
             * img_url : /public/uploads/20180108/5c0937be42768f626c94e44a6b33476b.jpg
             */

            private String img_url;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }

        public static class SpeciBean {
            /**
             * speci_id : 2
             * speci_name : 尺寸
             * speci_vals : [{"gsp_id":"25","gsp_value":"大","gsp_mater_money":"100.00","gsp_son_money":"-20.00","gsp_img":"/public/uploads/20180108/849a45ac38d33016689fad880f5d7a15.png"},{"gsp_id":"26","gsp_value":"小","gsp_mater_money":"0.00","gsp_son_money":"0.00","gsp_img":"/public/uploads/20180108/454e8d3026370b720394ed7a7f268048.jpg"}]
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
                 * gsp_id : 25
                 * gsp_value : 大
                 * gsp_mater_money : 100.00
                 * gsp_son_money : -20.00
                 * gsp_img : /public/uploads/20180108/849a45ac38d33016689fad880f5d7a15.png
                 */

                private String gsp_id;
                private String gsp_value;
                private String gsp_mater_money;
                private String gsp_son_money;
                private String gsp_img;

                public String getGsp_id() {
                    return gsp_id;
                }

                public void setGsp_id(String gsp_id) {
                    this.gsp_id = gsp_id;
                }

                public String getGsp_value() {
                    return gsp_value;
                }

                public void setGsp_value(String gsp_value) {
                    this.gsp_value = gsp_value;
                }

                public String getGsp_mater_money() {
                    return gsp_mater_money;
                }

                public void setGsp_mater_money(String gsp_mater_money) {
                    this.gsp_mater_money = gsp_mater_money;
                }

                public String getGsp_son_money() {
                    return gsp_son_money;
                }

                public void setGsp_son_money(String gsp_son_money) {
                    this.gsp_son_money = gsp_son_money;
                }

                public String getGsp_img() {
                    return gsp_img;
                }

                public void setGsp_img(String gsp_img) {
                    this.gsp_img = gsp_img;
                }
            }
        }

        public static class AttrBean {
            /**
             * attr_name : 长度
             * attr_val : 200m
             */

            private String attr_name;
            private String attr_val;

            public String getAttr_name() {
                return attr_name;
            }

            public void setAttr_name(String attr_name) {
                this.attr_name = attr_name;
            }

            public String getAttr_val() {
                return attr_val;
            }

            public void setAttr_val(String attr_val) {
                this.attr_val = attr_val;
            }
        }
    }

    public static class BuyRuleBean {
        /**
         * rebate_name : 金的
         * day_type : 每年
         * buy_num : 3件
         */

        private String rebate_name;
        private String day_type;
        private String buy_num;

        public String getRebate_name() {
            return rebate_name;
        }

        public void setRebate_name(String rebate_name) {
            this.rebate_name = rebate_name;
        }

        public String getDay_type() {
            return day_type;
        }

        public void setDay_type(String day_type) {
            this.day_type = day_type;
        }

        public String getBuy_num() {
            return buy_num;
        }

        public void setBuy_num(String buy_num) {
            this.buy_num = buy_num;
        }
    }
}

package com.linkhand.fenxiao.info;


import com.linkhand.fenxiao.bean.InviteBean;
import com.linkhand.fenxiao.bean.MienBean;
import com.linkhand.fenxiao.bean.MienDainZanBean;
import com.linkhand.fenxiao.bean.MienDetailBean;
import com.linkhand.fenxiao.bean.PinglunBean;
import com.linkhand.fenxiao.feng.AllConfigFeng;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.fenlei.LeftClassFeng;
import com.linkhand.fenxiao.feng.fenlei.RightClassFeng;
import com.linkhand.fenxiao.feng.home.AllAddressFeng;
import com.linkhand.fenxiao.feng.home.CityFeng;
import com.linkhand.fenxiao.feng.home.CurrTrade;
import com.linkhand.fenxiao.feng.home.DefaultAddressFeng;
import com.linkhand.fenxiao.feng.home.DingDanResponse;
import com.linkhand.fenxiao.feng.home.GoodsDetailsFeng;
import com.linkhand.fenxiao.feng.home.GoodsRMBFeng;
import com.linkhand.fenxiao.feng.home.GroupListFeng;
import com.linkhand.fenxiao.feng.home.HomeMessageBean;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.home.IdeaGoodsDetailsFeng;
import com.linkhand.fenxiao.feng.home.IntentionGoods;
import com.linkhand.fenxiao.feng.home.KaQuanDetailBean;
import com.linkhand.fenxiao.feng.home.LiuYanBean;
import com.linkhand.fenxiao.feng.home.MessageDetailResponse;
import com.linkhand.fenxiao.feng.home.MessageResponse;
import com.linkhand.fenxiao.feng.home.MineGongGBean;
import com.linkhand.fenxiao.feng.home.MineJlBean;
import com.linkhand.fenxiao.feng.home.NewRecommendedVipGoods;
import com.linkhand.fenxiao.feng.home.ObtainUpdateAddressFeng;
import com.linkhand.fenxiao.feng.home.OrderInfoResponse;
import com.linkhand.fenxiao.feng.home.OrderInterfaceFeng;
import com.linkhand.fenxiao.feng.home.OrderInterfaceNewFeng;
import com.linkhand.fenxiao.feng.home.ProvinceFeng;
import com.linkhand.fenxiao.feng.home.RecommendedGoods;
import com.linkhand.fenxiao.feng.home.RecommendedVipGoods;
import com.linkhand.fenxiao.feng.home.SlideshowFeng;
import com.linkhand.fenxiao.feng.home.TuiHBean;
import com.linkhand.fenxiao.feng.home.VipDetailResponse;
import com.linkhand.fenxiao.feng.home.VipGoodsDetailsFeng;
import com.linkhand.fenxiao.feng.home.VipLvResponse;
import com.linkhand.fenxiao.feng.home.VipMoneyBean;
import com.linkhand.fenxiao.feng.home.VipPayResponse;
import com.linkhand.fenxiao.feng.home.VipXiaDanResponse;
import com.linkhand.fenxiao.feng.home.YDBean;
import com.linkhand.fenxiao.feng.login.LoginFeng;
import com.linkhand.fenxiao.feng.login.Register;
import com.linkhand.fenxiao.feng.login.Registered;
import com.linkhand.fenxiao.feng.mine.AllOrderFeng;
import com.linkhand.fenxiao.feng.mine.CollectionFeng;
import com.linkhand.fenxiao.feng.mine.InCollectionFeng;
import com.linkhand.fenxiao.feng.mine.MyTeamFeng;
import com.linkhand.fenxiao.feng.mine.PersonalMessageFeng;
import com.linkhand.fenxiao.feng.mine.ShoppingCartListFeng;
import com.linkhand.fenxiao.feng.mine.ShoppingCartListNewFeng;
import com.linkhand.fenxiao.feng.mine.TeamMessageFeng;
import com.linkhand.fenxiao.feng.zimu.AllCoinFeng;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 11860_000 on 2017/9/15.
 */

public interface InfoData {

    //订单列表
    @FormUrlEncoded
    @POST("order/get_order")
    Call<DingDanResponse> getDingdanList(@FieldMap Map<String, Object> map);

    //我的订单  取消商品订单
    @FormUrlEncoded
    @POST("order/quit_order")
    Call<ReturnFeng> getReturnGoods(@FieldMap Map<String, Object> map);

    //我的订单  确认收货
    @FormUrlEncoded
    @POST("order/get_receipt")
    Call<ReturnFeng> getConfirmReceipt(@FieldMap Map<String, Object> map);

    //我的收藏(收藏列表)   团购
    @FormUrlEncoded
    @POST("product/house")
    Call<CollectionFeng> getGroupCollectionOrder(@FieldMap Map<String, Object> map);

    //我的收藏(收藏列表)   意向
    @FormUrlEncoded
    @POST("product/house")
    Call<InCollectionFeng> getInCollectionOrder(@FieldMap Map<String, Object> map);

    //取消收藏
    @FormUrlEncoded
    @POST("product/quit_house")
    Call<ReturnFeng> getCancelCollection(@FieldMap Map<String, Object> map);

    //意向商品分享
    @FormUrlEncoded
    @POST("idea/share_idea")
    Call<HttpResponse> getShareIdea(@FieldMap Map<String, Object> map);

    //普通商品商品分享
    @FormUrlEncoded
    @POST("product/share_pro")
    Call<HttpResponse> getSharePro(@FieldMap Map<String, Object> map);

    //收藏商品
    @FormUrlEncoded
    @POST("product/add_house")
    Call<ReturnFeng> getCollectGoods(@FieldMap Map<String, Object> map);


    //我的订单  待评价
    @FormUrlEncoded
    @POST("order/get_pingjia")
    Call<AllOrderFeng> getEvaluationOrder(@FieldMap Map<String, Object> map);

    //(原来首页vip商品)VIP商品详情
    @FormUrlEncoded
    @POST("product/get_vip")
    Call<RecommendedVipGoods> getVipRecommended(@FieldMap Map<String, Object> map);

    //获取轮播图
    @FormUrlEncoded
    @POST("config/get_slide")
    Call<SlideshowFeng> getSlideshow(@FieldMap Map<String, Object> map);

    //首页vip商品
    @FormUrlEncoded
    @POST("product/get_vip")
    Call<NewRecommendedVipGoods> getNewVipRecommended(@FieldMap Map<String, Object> map);

    //vip商品详情
    @FormUrlEncoded
    @POST("product/get_vip_detail")
    Call<VipGoodsDetailsFeng> getVipGoodsDetails(@FieldMap Map<String, Object> map);

    //vip下单获取支付单号
    @FormUrlEncoded
    @POST("order/vip_add_order")
    Call<VipXiaDanResponse> getVipDanhan(@FieldMap Map<String, Object> map);

    //vip支付宝支付
    @FormUrlEncoded
    @POST("pay/vip_pay")
    Call<VipPayResponse> getZhifubao(@FieldMap Map<String, Object> map);

    //推荐商品列表
    @FormUrlEncoded
    @POST("product/get_hot")
    Call<RecommendedGoods> getRecommended(@FieldMap Map<String, Object> map);

    //意向商品列表
    @FormUrlEncoded
    @POST("idea/get_list")
    Call<IntentionGoods> getIntention(@FieldMap Map<String, Object> map);

    //商品详情
    @FormUrlEncoded
    @POST("product/get_details")
    Call<GoodsDetailsFeng> getGoodsDetails(@FieldMap Map<String, Object> map);

    //意见商品详情
    @FormUrlEncoded
    @POST("idea/get_details")
    Call<IdeaGoodsDetailsFeng> getIdeaGoodsDetails(@FieldMap Map<String, Object> map);

    //商品订单价格
    @FormUrlEncoded
    @POST("order/order_money")
    Call<GoodsRMBFeng> getGoodsRMB(@FieldMap Map<String, Object> map);

    //普通商品订单价格进行支付
    @FormUrlEncoded
    @POST("pay/order_pay")
    Call<VipPayResponse> getOrderPay(@FieldMap Map<String, Object> map);

    //加入购物车
    @FormUrlEncoded
    @POST("order/add_cart")
    Call<ReturnFeng> getInsertShoppingCart(@FieldMap Map<String, Object> map);

    //购物车列表
    @FormUrlEncoded
    @POST("order/get_cart")
    Call<ShoppingCartListFeng> getShoppingCartList(@FieldMap Map<String, Object> map);

    //购物车列表新
    @FormUrlEncoded
    @POST("order/get_cart")
    Call<ShoppingCartListNewFeng> getShoppingCartNewList(@FieldMap Map<String, Object> map);

    //删除购物车商品
    @FormUrlEncoded
    @POST("order/del_cart")
    Call<ReturnFeng> getRemoveGoods(@FieldMap Map<String, Object> map);

    //修改购物车商品数量
    @FormUrlEncoded
    @POST("order/add_cart_num")
    Call<ReturnFeng> getUpdateGoodNum(@FieldMap Map<String, Object> map);

    //下单页面
    @FormUrlEncoded
    @POST("order/new_order")
    Call<OrderInterfaceFeng> getOrderInterface(@FieldMap Map<String, Object> map);

    //直接购买获取订单号
    @FormUrlEncoded
    @POST("order/new_order")
    Call<OrderInfoResponse> getOrderInfo(@FieldMap Map<String, Object> map);

    //购物车购买获取订单号
    @FormUrlEncoded
    @POST("order/cart_to_order")
    Call<OrderInfoResponse> getCartInfo(@FieldMap Map<String, Object> map);

    //下单页面数据
    @FormUrlEncoded
    @POST("order/order_details")
    Call<OrderInterfaceFeng> getOderDetails(@FieldMap Map<String, Object> map);

    //普通商品新下单页面数据
    @FormUrlEncoded
    @POST("order/order_details")
    Call<OrderInterfaceNewFeng> getNewOderDetails(@FieldMap Map<String, Object> map);

    //vip商品下单页面数据
    @FormUrlEncoded
    @POST("order/vip_ready_order")
    Call<VipDetailResponse> getVipDetail(@FieldMap Map<String, Object> map);

    //关注意向
    @FormUrlEncoded
    @POST("idea/add_idea")
    Call<ReturnFeng> getAddIntention(@FieldMap Map<String, Object> map);

    //取消意向
    @FormUrlEncoded
    @POST("idea/del_idea")
    Call<ReturnFeng> getDeleteIntention(@FieldMap Map<String, Object> map);


    //注册
    @FormUrlEncoded
    @POST("user/add_user")
    Call<Registered> getRegistered(@FieldMap Map<String, Object> map);

    //获取验证码
    @FormUrlEncoded
    @POST("index/get_send_code")
    Call<Register> getRegister(@FieldMap Map<String, Object> map);

    //登录
    @FormUrlEncoded
    @POST("user/user_login")
    Call<LoginFeng> getLogin(@FieldMap Map<String, Object> map);

    //设置支付密码
    @FormUrlEncoded
    @POST("user/save_pay_pwd")
    Call<HttpResponse> getSavepayPwd(@FieldMap Map<String, Object> map);

    //修改密码
    @FormUrlEncoded
    @POST("user/save_pwd")
    Call<ReturnFeng> getUpdatePsw(@FieldMap Map<String, Object> map);

    //省（一级地区列表）
    @FormUrlEncoded
    @POST("config/get_city")
    Call<ProvinceFeng> getProvince(@FieldMap Map<String, Object> map);

    //市（二级地区列表）区（三级地区列表）
    @FormUrlEncoded
    @POST("config/get_down_city")
    Call<CityFeng> getCity(@FieldMap Map<String, Object> map);

    //收货地址列表
    @FormUrlEncoded
    @POST("site/get_index")
    Call<AllAddressFeng> getAllAddress(@FieldMap Map<String, Object> map);

    //添加收货地址
    @FormUrlEncoded
    @POST("site/add_site")
    Call<ReturnFeng> getInsertAddress(@FieldMap Map<String, Object> map);

    //获取需要修改的收货地址
    @FormUrlEncoded
    @POST("site/edit_site")
    Call<ObtainUpdateAddressFeng> getObtainUpdateAddress(@FieldMap Map<String, Object> map);

    //修改收货地址
    @FormUrlEncoded
    @POST("site/update_site")
    Call<ReturnFeng> getUpdateAddress(@FieldMap Map<String, Object> map);

    //删除收货地址
    @FormUrlEncoded
    @POST("site/del_site")
    Call<ReturnFeng> getDeleteAddress(@FieldMap Map<String, Object> map);

    //设置默认收货地址
    @FormUrlEncoded
    @POST("site/site_is_first")
    Call<ReturnFeng> getDefaultAddress(@FieldMap Map<String, Object> map);

    //获取默认收货地址
    @FormUrlEncoded
    @POST("site/get_first")
    Call<DefaultAddressFeng> getObtainDefaultAddress(@FieldMap Map<String, Object> map);

    //获取选中的收货地址(地址id获取信息)
    @FormUrlEncoded
    @POST("site/get_one_site")
    Call<DefaultAddressFeng> getSelectedAddress(@FieldMap Map<String, Object> map);

    //生成订单
    @FormUrlEncoded
    @POST("order/add_order")
    Call<ReturnFeng> getCreateOrder(@FieldMap Map<String, Object> map);

    //子母币列表
    @FormUrlEncoded
    @POST("product/get_curr")
    Call<AllCoinFeng> getAllCoin(@FieldMap Map<String, Object> map);

    //发布子母币
    @FormUrlEncoded
    @POST("product/add_curr")
    Call<ReturnFeng> getreleaseCoin(@FieldMap Map<String, Object> map);

    //个人信息
    @FormUrlEncoded
    @POST("user/get_user")
    Call<PersonalMessageFeng> getInformation(@FieldMap Map<String, Object> map);

    //完善信息
    @FormUrlEncoded
    @POST("user/save_user")
    Call<ReturnFeng> getUpdateMessage(@FieldMap Map<String, Object> map);

    //一级分类（左侧列表）
    @FormUrlEncoded
    @POST("product/get_one_cate")
    Call<LeftClassFeng> getLeftClass(@FieldMap Map<String, Object> map);

    //二级分类（右侧列表）
    @FormUrlEncoded
    @POST("product/get_down_cate")
    Call<RightClassFeng> getRightClass(@FieldMap Map<String, Object> map);

    //团队成员
    @FormUrlEncoded
    @POST("user/get_team")
    Call<MyTeamFeng> getMyTeam(@FieldMap Map<String, Object> map);

    //团队成员信息
    @FormUrlEncoded
    @POST("user/get_user_detils")
    Call<TeamMessageFeng> getMyTeamMessage(@FieldMap Map<String, Object> map);

    //开团专区列表
    @FormUrlEncoded
    @POST("product/get_pro")
    Call<GroupListFeng> getGroupList(@FieldMap Map<String, Object> map);

    //购买母币
    @FormUrlEncoded
    @POST("product/buy_curr")
    Call<ReturnFeng> getBuyMu(@FieldMap Map<String, Object> map);

    //购买母币（官方）
    @FormUrlEncoded
    @POST("product/buy_curr_admin")
    Call<ReturnFeng> getOfficialBuyMu(@FieldMap Map<String, Object> map);

    //实名认证--新
    @POST("user/user_real")
    Call<ReturnFeng> getNewApprove(@Body RequestBody Body);

    //获取网站配置信息
    @FormUrlEncoded
    @POST("config/web_conf")
    Call<AllConfigFeng> getAllConfig(@FieldMap Map<String, Object> map);

    //所有评论
    @FormUrlEncoded
    @POST("evaluate/get_index")
    Call<ReturnFeng> getAllComments(@FieldMap Map<String, Object> map);


    @POST("user/save_head_img")
    Call<ReturnFeng> upLoad(@Body RequestBody Body);

    @POST("evaluate/add_evaluate")
    Call<ReturnFeng> upLoadComments(
//            @Url() String url,
            @Body RequestBody Body);

    //邀请界面
    @FormUrlEncoded
    @POST("index/user_code")
    Call<InviteBean> getUserCode(@FieldMap Map<String, Object> map);

    //消息列表
    @FormUrlEncoded
    @POST("trade/get_trade_list")
    Call<MessageResponse> getMessageList(@FieldMap Map<String, Object> map);

    //单条消息详情
    @FormUrlEncoded
    @POST("trade/get_trade_one")
    Call<MessageDetailResponse> getMessageDetailList(@FieldMap Map<String, Object> map);

    //修改消息查看状态   单条or所有
    @FormUrlEncoded
    @POST("trade/get_trade_look")
    Call<HttpResponse> getMessageIsRead(@FieldMap Map<String, Object> map);

    //企业风采列表
    @FormUrlEncoded
    @POST("article/article_list")
    Call<MienBean> getMienMsg(@FieldMap Map<String, Object> map);

    //企业风采文章详情
    @FormUrlEncoded
    @POST("article/article_detils")
    Call<MienDetailBean> getMienDetailMsg(@FieldMap Map<String, Object> map);

    //企业风采文章点赞
    @FormUrlEncoded
    @POST("article/article_praise")
    Call<MienDainZanBean> getMienDianZan(@FieldMap Map<String, Object> map);

    //二级密码验证
    @FormUrlEncoded
    @POST("index/send_pay_pwd")
    Call<HttpResponse> getPayPwd(@FieldMap Map<String, Object> map);

    //充值
    @FormUrlEncoded
    @POST("pay/recharge")
    Call<VipPayResponse> getChongzhi(@FieldMap Map<String, Object> map);

    //提现
    @FormUrlEncoded
    @POST("pay/tixian")
    Call<HttpResponse> getTiXian(@FieldMap Map<String, Object> map);

    //vip订单查询
    @FormUrlEncoded
    @POST("order/vip_get_order")
    Call<VipLvResponse> getVipLv(@FieldMap Map<String, Object> map);

    //是否有未读消息
    @FormUrlEncoded
    @POST("trade/get_trade_count")
    Call<HomeMessageBean> getTradeCount(@FieldMap Map<String, Object> map);

    //删除消息
    @FormUrlEncoded
    @POST("trade/get_trade_del")
    Call<HttpResponse> getTradeDel(@FieldMap Map<String, Object> map);

    //取消字母币发布
    @FormUrlEncoded
    @POST("product/curr_cancel")
    Call<HttpResponse> getCurrCancel(@FieldMap Map<String, Object> map);

    //字母币交易记录
    @FormUrlEncoded
    @POST("trade/curr_trade")
    Call<CurrTrade> getCurrTrade(@FieldMap Map<String, Object> map);

    //交易记录
    @FormUrlEncoded
    @POST("trade/finance_trade")
    Call<MineJlBean> getFinanceTrade(@FieldMap Map<String, Object> map);

    //充值/提现记录
    @FormUrlEncoded
    @POST("trade/recharge_trade")
    Call<CurrTrade> getRechargeTrade(@FieldMap Map<String, Object> map);

    //商品评论接口
    @FormUrlEncoded
    @POST("evaluate/get_index")
    Call<PinglunBean> getPinglun(@FieldMap Map<String, Object> map);

    //支付订单前  的 金额验证
    @FormUrlEncoded
    @POST("pay/order_pay_money")
    Call<HttpResponse> getPayMoney(@FieldMap Map<String, Object> map);

    //意向商品留言数据
    @FormUrlEncoded
    @POST("evaluate/idea_message_list")
    Call<LiuYanBean> getMessAgeList(@FieldMap Map<String, Object> map);

    //添加留言
    @FormUrlEncoded
    @POST("evaluate/idea_message_add")
    Call<HttpResponse> getMessAgeAdd(@FieldMap Map<String, Object> map);

    //签到
    @FormUrlEncoded
    @POST("trade/sign_in")
    Call<HttpResponse> getSignIn(@FieldMap Map<String, Object> map);

    //退货列表
    @FormUrlEncoded
    @POST("order/get_quit_list")
    Call<TuiHBean> getQuitList(@FieldMap Map<String, Object> map);

    //退货申请
    @FormUrlEncoded
    @POST("order/quit_order")
    Call<HttpResponse> getQuitOrder(@FieldMap Map<String, Object> map);

    //主页的公告
    @FormUrlEncoded
    @POST("config/get_notice")
    Call<MineGongGBean> getGetNotice(@FieldMap Map<String, Object> map);

    //用户删除订单
    @FormUrlEncoded
    @POST("order/del_order")
    Call<HttpResponse> getDelOrder(@FieldMap Map<String, Object> map);

    //卡券详情
    @FormUrlEncoded
    @POST("order/use_detail_get")
    Call<KaQuanDetailBean> getUseDetailGet(@FieldMap Map<String, Object> map);

    //使用卡券
    @FormUrlEncoded
    @POST("order/use_open")
    Call<HttpResponse> getUseOpen(@FieldMap Map<String, Object> map);

    //上级邀请人修改
    @FormUrlEncoded
    @POST("user/inviter_update")
    Call<HttpResponse> getINviterUpdate(@FieldMap Map<String, Object> map);

    //登录下线推送
    @FormUrlEncoded
    @POST("user/loginout")
    Call<HttpResponse> getLoginOut(@FieldMap Map<String, Object> map);

    //引导页
    @FormUrlEncoded
    @POST("config/get_pic")
    Call<YDBean> getPic(@FieldMap Map<String, Object> map);

    //vip商品价格
    @FormUrlEncoded
    @POST("order/get_vip_money")
    Call<VipMoneyBean> getVipMoney(@FieldMap Map<String, Object> map);


}

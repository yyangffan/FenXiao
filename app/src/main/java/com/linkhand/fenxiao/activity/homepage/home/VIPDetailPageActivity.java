package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.MyVipClassLVAdapter;
import com.linkhand.fenxiao.adapter.home.ParameterAdapter;
import com.linkhand.fenxiao.dialog.MyDialogApprove;
import com.linkhand.fenxiao.dialog.MyPayWap;
import com.linkhand.fenxiao.feng.home.MyGoodsFeng;
import com.linkhand.fenxiao.feng.home.VipGoodsDetailsFeng;
import com.linkhand.fenxiao.feng.home.VipXiaDanResponse;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.LeftLVIn;
import com.linkhand.fenxiao.utils.MyImageLoader;
import com.linkhand.fenxiao.utils.MyListView;
import com.linkhand.fenxiao.views.MyWevClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.umeng.socialize.UMShareAPI;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.linkhand.fenxiao.R.id.detail_purchasing_id;


public class VIPDetailPageActivity extends BaseActicity implements View.OnClickListener, LeftLVIn {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout mReturn;//返回
    String mUserId;//个人id
    @Bind(R.id.details_banner)
    Banner mBanner;
    @Bind(R.id.Details_title_id)
    TextView mGoodsTitle;
    @Bind(R.id.Details_zirmb_id)
    TextView mGoodsRMB;
    @Bind(detail_purchasing_id)
    TextView mBuy;//购买
    @Bind(R.id.synopsis_id)
    TextView mSynopsis;//介绍

    TextView mOrderSum;//订单件数
    InfoData service;
    VipGoodsDetailsFeng.InfoBean bean;//商品数据
    String mVipId;//vip商品id
    List<Uri> list;
    List<String> titleList;
    ParameterAdapter mParameterAdapter;//商品参数
    @Bind(R.id.wv)
    WebView mWv;

    private DisplayImageOptions options;
    String mUserIsVip; //是否vip  0否  1是
    String mUserReal;//是否认证  0否  1是
    MyPayWap dialogs;//支付dialog
    String mVipMater = "xxx";//返母币
    String mVipSon = "xxx";//返子币
    //popupWindow----
    View vu;
    PopupWindow popupWindow;
    LinearLayout return_id1;//退出加入购物车的dialog
    TextView mPopPurchasing;//popupWindow的立刻购买
    TextView mSingle_rmb;//弹窗单价
    LinearLayout mTypesLayout;//总类别
    MyListView mAllListView;//总类别
    TextView mColorTv;//请选择颜色
    ImageView mFigure;//小图片
    List<VipGoodsDetailsFeng.InfoBean.SpeciBean> speciBeanList;
    List<MyGoodsFeng> myList;
    MyGoodsFeng myMap;
    MyVipClassLVAdapter mListViewAdapter;
    private String vip_speci_vals = "";
    private String mVip_order_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vipdetail_page);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
        onClicks();
//        onRoastingTu();//轮播图
        onMessage();
    }

    public void initView() {
        mReturn = (LinearLayout) findViewById(R.id.home_return_id2);
        mOrderSum = (TextView) findViewById(R.id.order_sum_id);//订单件数
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            mVipId = intent.getStringExtra("vip_id"); //vip商品id
        }
        editor.remove("addressId").commit();
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
        mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);//返回
        mBuy.setOnClickListener(this);//购买
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_return_id2://返回
                this.finish();
                break;
            case detail_purchasing_id://购买
                //是否认证  0否  1是
                mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
                Log.e("yh", "是否vip--" + mUserIsVip);
                if (mUserIsVip.equals("1")) {
                    Toast.makeText(this, "当前已是vip!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (mUserReal.equals("0")) {//是否认证  0否  1是
                        onApprove();//实名认证
                        return;
                    }
                }
                onShoppings();//立刻购买popupWindow
                break;
            case R.id.detail_purchasing_id2:
                getVipDingdan();
                break;
            case R.id.return_id1:
                popupWindow.dismiss();
                break;
        }
    }

    /*获取vip支付订单号*/
    public void getVipDingdan() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("vip_id", mVipId);
        map.put("vip_speci_vals", vip_speci_vals);
        Call<VipXiaDanResponse> call = service.getVipDanhan(map);
        call.enqueue(new Callback<VipXiaDanResponse>() {
            @Override
            public void onResponse(Call<VipXiaDanResponse> call, Response<VipXiaDanResponse> response) {
                VipXiaDanResponse h = response.body();
                if (h.getCode() == 100) {
                    mVip_order_id = h.getVip_order_id();
                    //跳转详情页 之后弹出支付的dialog   onDialogs();//支付dialog
                    Intent intent = new Intent(VIPDetailPageActivity.this, ConfirmOrderActivity.class);
                    intent.putExtra("danhao", mVip_order_id);
                    intent.putExtra("isVip", true);
                    startActivity(intent);
                    VIPDetailPageActivity.this.finish();
                } else {
                    Toast.makeText(VIPDetailPageActivity.this, h.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VipXiaDanResponse> call, Throwable t) {

            }
        });
    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("vip_id", mVipId);//商品id
        Call<VipGoodsDetailsFeng> call = service.getVipGoodsDetails(map);
        call.enqueue(new Callback<VipGoodsDetailsFeng>() {
            @Override
            public void onResponse(Call<VipGoodsDetailsFeng> call, Response<VipGoodsDetailsFeng> response) {
                VipGoodsDetailsFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                if (code == 100) {
                    bean = pcfeng.getInfo();
                    List<VipGoodsDetailsFeng.InfoBean.ImgBean> imgs = bean.getImg();//轮播图

                    String good_name = bean.getVip_name();//商品名称
                    String synopsis = bean.getVip_desc();//介绍
                    String moneyRMB = bean.getVip_money();//价格
//                    List<GoodsDetailsFeng.InfoBean.AttrBean> attrBeen = bean.getAttr();//属性（产品参数）
                    String good_content = bean.getVip_intro();//图文详情

                    mGoodsTitle.setText(good_name);
                    mGoodsRMB.setText(moneyRMB);
//                    mOrderSum.setText(money_num + "件");//订单件数
                    onRoastingTu(imgs);//轮播图
                    if (good_content != null) {
                        mWv.getSettings().setJavaScriptEnabled(true);
                        mWv.setWebViewClient(new MyWevClient());
                        mWv.loadDataWithBaseURL(null,good_content,"text/html","utf-8",null);
                    }
                    if (synopsis != null) {
                        mSynopsis.setText(synopsis + "");
                    }
                } else {
                    Toast.makeText(VIPDetailPageActivity.this, "xx", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VipGoodsDetailsFeng> call, Throwable t) {

            }
        });
    }


    public void onShoppings() {//加入购物车popupWindow   //1 加入购物车    2立刻购买
        vu = LayoutInflater.from(this).inflate(R.layout.activity_dialog_merchandise, null);
        return_id1 = (LinearLayout) vu.findViewById(R.id.return_id1);
        return_id1.setOnClickListener(VIPDetailPageActivity.this);
        mPopPurchasing = (TextView) vu.findViewById(R.id.detail_purchasing_id2);
        mPopPurchasing.setOnClickListener(VIPDetailPageActivity.this);
        RelativeLayout number = (RelativeLayout) vu.findViewById(R.id.number_rlayout_id);
        number.setVisibility(View.GONE);
        mSingle_rmb = (TextView) vu.findViewById(R.id.single_rmb);//弹窗单价
        mTypesLayout = (LinearLayout) vu.findViewById(R.id.home_type_llayout_id);//总类别
        mAllListView = (MyListView) vu.findViewById(R.id.home_type_listview_id);//总类别
        mAllListView.setFocusable(false);
        mColorTv = (TextView) vu.findViewById(R.id.dialog_tvcolor_id);//请选择颜色
        mFigure = (ImageView) vu.findViewById(R.id.merchandise_tu_id);//小图片
        mPopPurchasing.setText("立刻购买");
        popupWindow = new PopupWindow(vu);
//        int width =mSchool.getWidth();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        int height = 500;
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        //设置点击外边区域消失
        popupWindow.setOutsideTouchable(true);
        //设置点击后消失
        popupWindow.setTouchable(true);
        //是否具有获取焦点的能力
        popupWindow.setFocusable(true);//必须写
        setBackgroundAlpha(0.5f);
        //设置PopupWindow中的位置
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
        popupWindow.showAtLocation(vu, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        if (bean != null) {
            List<VipGoodsDetailsFeng.InfoBean.ImgBean> imgBeen = bean.getImg();
            if (imgBeen.size() != 0) {
                String thumb = C.TU + imgBeen.get(0).getVimg_url();
                Glide.with(VIPDetailPageActivity.this)
                        .load(thumb)
                        .into(mFigure);

            }
            speciBeanList = bean.getSpeci();
            setData(speciBeanList);
        }

    }

    private void setData(List<VipGoodsDetailsFeng.InfoBean.SpeciBean> speciBeanList) {
        if (speciBeanList.size() != 0) {
            myList = new ArrayList<>();
            for (int i = 0; i < speciBeanList.size(); i++) {
                myMap = new MyGoodsFeng();
                myMap.setMyid(speciBeanList.get(i).getSpeci_vals().get(0).getVsp_id());
                myMap.setMyname(speciBeanList.get(i).getSpeci_vals().get(0).getVsp_value());
                myMap.setMyallname(speciBeanList.get(i).getSpeci_name());
                String thumb = speciBeanList.get(i).getSpeci_vals().get(0).getVsp_img() + "";
                if (!thumb.equals("")) {
                    thumb = C.TU + thumb;
                    Glide.with(VIPDetailPageActivity.this)
                            .load(thumb)
                            .into(mFigure);
                }
                myList.add(myMap);
            }
            vip_speci_vals = myList.get(0).getMyid();
            mListViewAdapter = new MyVipClassLVAdapter(VIPDetailPageActivity.this, speciBeanList, myList);//设置默认的
            mAllListView.setAdapter(mListViewAdapter);
            mListViewAdapter.setOnItems(VIPDetailPageActivity.this);
//        mAdapterRight.notifyDataSetChanged();
        }
//        goodsRMB();//商品价格
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = (this).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        (this).getWindow().setAttributes(lp);
    }

    public void onRoastingTu(List<VipGoodsDetailsFeng.InfoBean.ImgBean> imgs) {//轮播图
        list = new ArrayList<>();
        titleList = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            Uri uri = Uri.parse(C.TU + imgs.get(i).getVimg_url());
            list.add(uri);
//            titleList.add(titles[i]);
        }
        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置
        mBanner.setIndicatorGravity(Gravity.CENTER);
        //设置图片加载器
        mBanner.setImageLoader(new MyImageLoader());
        //设置图片集合
        mBanner.setImages(list);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合
//        mBanner.setBannerTitles(titleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(2000);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    public void onApprove() {//实名认证
        MyDialogApprove dialog = new MyDialogApprove(VIPDetailPageActivity.this);
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                //把固定不变的url写到这里
                .baseUrl(C.HOSTIP)
                //支持返回字符串,先写字符串
                .addConverterFactory(ScalarsConverterFactory.create())
                //支持解析返回的json，后写json解析
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建一个接口的实现类
        service = retrofit.create(InfoData.class);
        //解析html(图文)
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        editor.remove("addressId").commit();
    }

    @Override
    public void onGVItemClick(TextView mTextView, final List<Map<String, Object>> list) {
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gsp_id = (String) list.get(0).get("gsp_id");//规格id
                String gsp_value = (String) list.get(0).get("gsp_value");//规格名
                int place = (int) list.get(0).get("place");//类型位置
                int positions = (int) list.get(0).get("position");//规格位置
                String img = (String) list.get(0).get("img");//图片名
                Log.e("yh", "gsp_id--" + gsp_id + "--gsp_value--" + gsp_value + "--img--" + img);
//                mColorTv.setText(speciBeanList.get(position).getSpeci_name());
                if (gsp_id.equals(myList.get(place).getMyid())) {

                } else {
                    vip_speci_vals = gsp_id;
                    if (!img.equals("")) {
                        String thumb = C.TU + img;
//                        Log.e("yh", "thumb--" + thumb);
                        Glide.with(VIPDetailPageActivity.this)
                                .load(thumb)
                                .into(mFigure);
                    }

                    myList.get(place).setMyid(gsp_id);
                    myList.get(place).setMyname(gsp_value);
                    mListViewAdapter.notifyDataSetChanged();
//                    goodsRMB();//商品价格
                }
            }
        });
    }

}

package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.GradeRecyAdapter;
import com.linkhand.fenxiao.adapter.home.DetailPageAdapter;
import com.linkhand.fenxiao.adapter.home.GoodsDetailsAdapter;
import com.linkhand.fenxiao.adapter.home.MyClassLVAdapter;
import com.linkhand.fenxiao.adapter.home.ParameterAdapter;
import com.linkhand.fenxiao.bean.PinglunBean;
import com.linkhand.fenxiao.dialog.DialogUpgradeVIP;
import com.linkhand.fenxiao.dialog.MyViewPagDialog;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.fenlei.Category;
import com.linkhand.fenxiao.feng.home.GoodsDetailsFeng;
import com.linkhand.fenxiao.feng.home.GoodsRMBFeng;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.home.MyGoodsFeng;
import com.linkhand.fenxiao.feng.home.OrderInfoResponse;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.LeftLVIn;
import com.linkhand.fenxiao.utils.MyImageLoader;
import com.linkhand.fenxiao.utils.MyListView;
import com.linkhand.fenxiao.utils.MyRecycleView;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.utils.youmeng.ShareUtils;
import com.linkhand.fenxiao.views.MyWevClient;
import com.luck.picture.lib.photoview.PhotoView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

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

/********************************************************************
 @version: 1.0.0
 @description: 开团商品详情
 @author: user
 @time: 2018/5/21 11:10
 @变更历史:
 ********************************************************************/
public class DetailPageActivity extends BaseActicity implements View.OnClickListener, LeftLVIn {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout mReturn;//返回
    List<Fragment> mList;//片段
    TextView mShoppingCart;//加入购物车
    TextView mPurchasing;//立刻购买
    View vu;
    PopupWindow popupWindow;
    LinearLayout return_id1;//退出加入购物车的dialog
    @Bind(R.id.wv)
    WebView mWv;
    @Bind(R.id.tabLayout_id)
    TabLayout mTabLayoutId;
    @Bind(R.id.upgrade_recy)
    MyRecycleView mUpgradeRecy;
    @Bind(R.id.upgrade_smart)
    SmartRefreshLayout mUpgradeSmart;
    @Bind(R.id.order_kucun)
    TextView mOrderKucun;
    private TextView mtv_xiangou;
    TextView mPopPurchasing;//popupWindow的立刻购买
    Button mJia, mJian;//弹窗 加 减
    TextView mNumber;//弹窗数
    TextView mSingle_rmb;//弹窗单价
    int number = 1;
    String mUserId;//个人id
    LinearLayout mTypesLayout;//总类别
    MyListView mAllListView;//总类别
    MyClassLVAdapter mListViewAdapter;
    DetailPageAdapter adapter;
    @Bind(R.id.details_banner)
    Banner mBanner;
    @Bind(R.id.Details_title_id)
    TextView mGoodsTitle;
    @Bind(R.id.Details_zirmb_id)
    TextView mGoodsZiRMB;
    @Bind(R.id.Details_murmb_id)
    TextView mGoodsMuRMB;
    @Bind(R.id.detail_collect_id)
    ImageView mCollect;//收藏图片
    @Bind(R.id.details_listview_id)
    MyListView mListview;//产品参数
    @Bind(R.id.Details_zibitext_id)
    TextView mDetailsZibitext;//子币
    @Bind(R.id.Details_mutext_id)
    TextView mDetailsMutext;//母币
    @Bind(R.id.upgrade_zengsong)
    TextView mUpgradeZengsong;

    LinearLayout mCollectLayout;//收藏
    LinearLayout mShare;//分享
    TextView mOrderSum;//订单件数

    private ArrayList<Category> itemList = null;
    private LinearLayout.LayoutParams lp_gd = null;
    private LinearLayout.LayoutParams lp_tv = null;
    TextView mColorTv;//请选择颜色
    InfoData service;
    GoodsDetailsAdapter mAdapter;
    int isPass;//1 加入购物车    2立刻购买
    GoodsDetailsFeng.InfoBean bean;//商品数据
    List<GoodsDetailsFeng.InfoBean.SpeciBean> speciBeanList;//全部类型
    int house;//是否已收藏 1为已收藏  0未收藏
    ImageView mFigure;//小图片

    String mMoodId;//商品id
    List<Uri> list;
    String[] titles = {"标题1", "标题2", "标题3", "标题4"};
    List<String> titleList;

    List<MyGoodsFeng> myList;
    MyGoodsFeng myMap;
    ParameterAdapter mParameterAdapter;//商品参数
    private DisplayImageOptions options;
    String Mater_name = "母币";//母币名称
    String Son_name = "子币";//子币名称
    private String mShare_url = "";
    private int page = 0;
    private GradeRecyAdapter mGradeAdapter;
    private List<PinglunBean.InfoBean> mPingList;
    private List<Map<String, Object>> mMapList;
    private String guige_imgv = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        ButterKnife.bind(this);
        initRetrofit();
        initView();
        onClicks();
//        onRoastingTu();//轮播图
        onMessage();
    }

    public void initView() {
        mReturn = (LinearLayout) findViewById(R.id.home_return_id2);
        mShoppingCart = (TextView) findViewById(R.id.detail_shoppingcart_id);
        mPurchasing = (TextView) findViewById(R.id.detail_purchasing_id);
        mOrderSum = (TextView) findViewById(R.id.order_sum_id);//订单件数
        mCollectLayout = (LinearLayout) findViewById(R.id.detail_collect_llayout_id);//收藏
        mShare = (LinearLayout) findViewById(R.id.upgrade_share);//分享

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mListview.setFocusable(false);
        mUserId = preferences.getString("user_id", "");
        Mater_name = preferences.getString("Mater_name", "母币");//母币名称
        Son_name = preferences.getString("Son_name", "子币");//子币名称
        mDetailsZibitext.setText(Son_name);//子币
        mDetailsMutext.setText(Mater_name);//母币
        Intent intent = getIntent();
        if (intent != null) {
            mMoodId = intent.getStringExtra("good_id"); //商品id
            Log.e("yh", "mMoodId---" + mMoodId);
        }
        editor.remove("addressId").commit();
        mPingList = new ArrayList<>();
        mGradeAdapter = new GradeRecyAdapter(this, mPingList);
        LinearLayoutManager lineaManager = new LinearLayoutManager(this);
        lineaManager.setOrientation(LinearLayoutManager.VERTICAL);
        mUpgradeRecy.setLayoutManager(lineaManager);
        mUpgradeRecy.setAdapter(mGradeAdapter);

        mUpgradeSmart.setEnableRefresh(false);
        mUpgradeSmart.setEnableLoadmore(false);
        mTabLayoutId.addTab(mTabLayoutId.newTab().setText("图文详情"));
        mTabLayoutId.addTab(mTabLayoutId.newTab().setText("评论"));
        mTabLayoutId.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    mWv.setVisibility(View.VISIBLE);
                    mUpgradeRecy.setVisibility(View.GONE);
                    mUpgradeSmart.setEnableLoadmore(false);
                } else {
                    mWv.setVisibility(View.GONE);
                    mUpgradeRecy.setVisibility(View.VISIBLE);
                    mUpgradeSmart.setEnableLoadmore(true);
                    page = 0;
                    getPinglun();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mUpgradeSmart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getPinglun();
            }
        });
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);//返回
        mShoppingCart.setOnClickListener(this);//加入购物车
        mPurchasing.setOnClickListener(this);//立刻购买
        mCollectLayout.setOnClickListener(this);//收藏
        mShare.setOnClickListener(this);//分享
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.detail_collect_llayout_id://收藏
                if (house == 1) {//是否已收藏 1为已收藏  0未收藏
                    cancelCollect();//取消收藏商品
                } else if (house == 0) {
                    collect();//收藏商品
                }
                break;
            case R.id.upgrade_share://分享
                onShare();//分享
                break;
            case R.id.home_return_id2://返回
                this.finish();
                break;
            case R.id.detail_shoppingcart_id://加入购物车
                isPass = 1;//1 加入购物车    2立刻购买
                onShoppings();
                break;
            case R.id.detail_purchasing_id://立刻购买
                isPass = 2;//1 加入购物车    2立刻购买
                onShoppings();
                break;
            case R.id.return_id1://退出加入购物车的dialog
                popupWindow.dismiss();
                break;
            case R.id.detail_purchasing_id2://popupWindow的立刻购买
                if (mUserId.equals("")) {
                    DialogUpgradeVIP dialogs = new DialogUpgradeVIP(this);
//                    dialogs.setCanceledOnTouchOutside(false);//点击空白处是否消失
                    dialogs.show();
                } else {
                    if (isPass == 1) {//1 加入购物车    2立刻购买
                        AddCart();//加入购物车
                    } else if (isPass == 2) {
                        onBuyNow();//立刻购买
                    }

                }

                break;
            case R.id.detail_addition_id://加
                if (bean != null) {
                    if (bean.getGood_astrict().equals("0")) {
                        number = number + 1;
                    } else {
                        if (number == Integer.parseInt(bean.getGood_astrict())) {
                            number = Integer.parseInt(bean.getGood_astrict());
                        } else {
                            number = number + 1;
                        }
                    }
                    mNumber.setText(number + "");
                }
                break;
            case R.id.detail_subtraction_id://减
                if (number == 1) {
                    number = 1;
                } else {
                    number = number - 1;
                }
                mNumber.setText(number + "");
                break;


        }
    }

    /*获取评论数据*/
    public void getPinglun() {
        Map<String, Object> map = new HashMap<>();
        map.put("good_id", mMoodId);
        map.put("pag", page);

        Call<PinglunBean> call = service.getPinglun(map);
        call.enqueue(new Callback<PinglunBean>() {
            @Override
            public void onResponse(Call<PinglunBean> call, Response<PinglunBean> response) {
                PinglunBean pinglun = response.body();
                mUpgradeSmart.finishLoadmore();
                mUpgradeSmart.finishRefresh();
                if (pinglun.getCode() == 100) {
                    if (page == 0) {
                        mPingList.clear();
                        mPingList.addAll(pinglun.getInfo());
                    } else {
                        for (PinglunBean.InfoBean bean : pinglun.getInfo()) {
                            mPingList.add(bean);
                        }
                    }
                    mGradeAdapter.notifyDataSetChanged();
                    page++;

                } else if (pinglun.getCode() == 200) {
                    ToastUtil.showToast(DetailPageActivity.this, pinglun.getSuccess());
                } else {
                    mPingList.clear();
                    mGradeAdapter.notifyDataSetChanged();
                    ToastUtil.showToast(DetailPageActivity.this, pinglun.getSuccess());
                }
            }

            @Override
            public void onFailure(Call<PinglunBean> call, Throwable t) {
                mUpgradeSmart.finishLoadmore();
                mUpgradeSmart.finishRefresh();
                ToastUtil.showToast(DetailPageActivity.this, "网络异常");
            }
        });
    }

    public void onShoppings() {//加入购物车popupWindow   //1 加入购物车    2立刻购买
        number = 1;
        vu = LayoutInflater.from(this).inflate(R.layout.activity_dialog_merchandise, null);
        return_id1 = (LinearLayout) vu.findViewById(R.id.return_id1);
        mtv_xiangou = (TextView) vu.findViewById(R.id.detail_bottom_xiangou);
        return_id1.setOnClickListener(DetailPageActivity.this);
        mPopPurchasing = (TextView) vu.findViewById(R.id.detail_purchasing_id2);
        mPopPurchasing.setOnClickListener(DetailPageActivity.this);
//                mJia = (ImageView) vu.findViewById(R.id.detail_addition_id);//加
//        mJia.setOnClickListener(DetailPageActivity.this);//加
//        mJian = (ImageView) vu.findViewById(R.id.detail_subtraction_id);//减
//        mJian.setOnClickListener(DetailPageActivity.this);//减
        mJia = (Button) vu.findViewById(R.id.detail_addition_id);//加
        mJia.setOnClickListener(DetailPageActivity.this);//加
        mJian = (Button) vu.findViewById(R.id.detail_subtraction_id);//减
        mJian.setOnClickListener(DetailPageActivity.this);//减
        mNumber = (TextView) vu.findViewById(R.id.detail_number_id);//数
        mSingle_rmb = (TextView) vu.findViewById(R.id.single_rmb);//弹窗单价
        mTypesLayout = (LinearLayout) vu.findViewById(R.id.home_type_llayout_id);//总类别
        mAllListView = (MyListView) vu.findViewById(R.id.home_type_listview_id);//总类别
        mAllListView.setFocusable(false);
        mColorTv = (TextView) vu.findViewById(R.id.dialog_tvcolor_id);//请选择颜色
        mFigure = (ImageView) vu.findViewById(R.id.merchandise_tu_id);//小图片
        if (isPass == 1) {//1 加入购物车    2立刻购买
            mPopPurchasing.setText("加入购物车");
        } else if (isPass == 2) {
            mPopPurchasing.setText("立刻购买");
        }

        popupWindow = new PopupWindow(vu);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);//必须写

        setBackgroundAlpha(0.5f);
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
            List<GoodsDetailsFeng.InfoBean.ImgBean> imgBeen = bean.getImg();
            if (imgBeen.size() != 0) {
                String thumb = C.TU + imgBeen.get(0).getImg_url();
//                        Log.e("yh", "thumb--" + thumb);
                Glide.with(DetailPageActivity.this)
                        .load(thumb)
                        .into(mFigure);

            }
            speciBeanList = bean.getSpeci();
            setData(speciBeanList);
            if (bean.getGood_astrict().equals("0")) {
                mtv_xiangou.setText("");
            } else {
                mtv_xiangou.setText("(限购" + bean.getGood_astrict() + "件)");
            }
        }
        mFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<View> mlist = new ArrayList<>();
                List<String> mdizhi = new ArrayList<>();
                if (guige_imgv.equals("")) {
                    mdizhi.add(C.TU + bean.getImg().get(0).getImg_url());
                    PhotoView photoView = new PhotoView(DetailPageActivity.this);
                    Glide.with(DetailPageActivity.this).load(C.TU + bean.getImg().get(0).getImg_url()).into(photoView);
                    mlist.add(photoView);
                } else {
                    mdizhi.add(guige_imgv);
                    PhotoView photoView = new PhotoView(DetailPageActivity.this);
                    Glide.with(DetailPageActivity.this).load(guige_imgv).into(photoView);
                    mlist.add(photoView);
                }
                MyViewPagDialog myViewPagDialog = new MyViewPagDialog(DetailPageActivity.this, mlist, mdizhi, 0);
                myViewPagDialog.show();
            }
        });

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


    private void setData(List<GoodsDetailsFeng.InfoBean.SpeciBean> speciBeanList) {
        if (speciBeanList.size() != 0) {
            myList = new ArrayList<>();
            for (int i = 0; i < speciBeanList.size(); i++) {
                myMap = new MyGoodsFeng();
                /*去掉默认选中规格*/
//                myMap.setMyid(speciBeanList.get(i).getSpeci_vals().get(0).getGsp_id());
//                myMap.setMyname(speciBeanList.get(i).getSpeci_vals().get(0).getGsp_value());
                myMap.setMyid("");
                myMap.setMyname("");
                myMap.setMyallname(speciBeanList.get(i).getSpeci_name());
                String thumb = speciBeanList.get(i).getSpeci_vals().get(0).getGsp_img() + "";
                if (!thumb.equals("")) {
                    thumb = C.TU + thumb;
//                        Log.e("yh", "thumb--" + thumb);
                    Glide.with(DetailPageActivity.this)
                            .load(thumb)
                            .into(mFigure);
                }
                myList.add(myMap);
            }
            mListViewAdapter = new MyClassLVAdapter(DetailPageActivity.this, speciBeanList, myList);//设置默认的
            mAllListView.setAdapter(mListViewAdapter);
            mListViewAdapter.setOnItems(DetailPageActivity.this);
//        mAdapterRight.notifyDataSetChanged();
        }
        goodsRMB();//商品价格
    }

    public void goodsRMB() {//商品价格
        String specId = "";//选中的规格id
        if (speciBeanList != null & speciBeanList.size() != 0) {
            if (myList != null) {
                for (int i = 0; i < myList.size(); i++) {
                    if (specId.equals("")) {
                        specId = myList.get(i).getMyid();
                    } else {
                        specId = specId + "," + myList.get(i).getMyid();
                    }
                }
                Log.e("yh", "specId--" + specId);
            }
        }
        onGoodsRMB(specId);//商品价格
    }

    public void onGoodsRMB(String specId) {//商品价格
        Log.e("yh", "商品id--" + mMoodId + "--数量--" + mNumber.getText().toString() + "--所选规格id 用，隔开--" + specId);
        Map<String, Object> map = new HashMap<>();
        map.put("good_id", mMoodId);//商品id
        map.put("order_num", mNumber.getText().toString());//数量
        map.put("speci_vals", specId);//所选规格id 用，隔开
        Call<GoodsRMBFeng> call = service.getGoodsRMB(map);
        call.enqueue(new Callback<GoodsRMBFeng>() {
            @Override
            public void onResponse(Call<GoodsRMBFeng> call, Response<GoodsRMBFeng> response) {
                GoodsRMBFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    int allMater = pcfeng.getAllmater();//总母币
                    int allSon = pcfeng.getAllson();//总子币
                    int oneMater = pcfeng.getOnemater();//单价母币（商品加规格）
                    int oneSon = pcfeng.getOneson();//单价子币（商品加规格）
                    Log.e("yh", "总母币--" + allMater + "--总子币--" + allSon + "--单价母币--" + oneMater + "--单价子币--" + oneSon);
                    mSingle_rmb.setText("¥" + oneSon + Son_name + " " + oneMater + Mater_name);
                } else {
                    Toast.makeText(DetailPageActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GoodsRMBFeng> call, Throwable t) {

            }
        });
    }


    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("good_id", mMoodId);//商品id
        map.put("user_id", mUserId);
        Call<GoodsDetailsFeng> call = service.getGoodsDetails(map);
        call.enqueue(new Callback<GoodsDetailsFeng>() {
            @Override
            public void onResponse(Call<GoodsDetailsFeng> call, Response<GoodsDetailsFeng> response) {
                GoodsDetailsFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                if (code == 100) {
                    bean = pcfeng.getInfo();
                    List<GoodsDetailsFeng.InfoBean.ImgBean> imgs = bean.getImg();//轮播图

                    house = bean.getHouse();//是否已收藏 1为已收藏  0未收藏
                    String good_name = bean.getGood_name();//商品名称
                    GoodsDetailsFeng.InfoBean.MoneyBean moneyBean = bean.getMoney();
                    String money_mater = moneyBean.getMoney_mater();//母币价格
                    String money_son = moneyBean.getMoney_son();//子币价格
                    String money_num = bean.getAll_num() + "";//下单人数
                    List<GoodsDetailsFeng.InfoBean.AttrBean> attrBeen = bean.getAttr();//属性（产品参数）
                    String good_content = bean.getGood_content();//图文详情

                    if (house == 1) {//是否已收藏 1为已收藏  0未收藏
                        mCollect.setImageResource(R.drawable.collection_two);
                    } else if (house == 0) {
                        mCollect.setImageResource(R.drawable.collection);
                    }
                    mGoodsTitle.setText(good_name);
                    mGoodsZiRMB.setText(money_son);
                    mGoodsMuRMB.setText(money_mater);
                    mOrderSum.setText(money_num + "件");//订单件数
                    if (imgs != null) {
                        onRoastingTu(imgs);//轮播图
                    }
                    //产品参数
                    mParameterAdapter = new ParameterAdapter(DetailPageActivity.this, attrBeen);
                    mListview.setAdapter(mParameterAdapter);
                    //图文详情
                    Log.e("yh", "图文详情--" + good_content);
                    if (good_content != null) {
                        mWv.setWebViewClient(new MyWevClient());
                        mWv.getSettings().setJavaScriptEnabled(true);
                        mWv.loadDataWithBaseURL(null, good_content, "text/html", "utf-8", null);
                    }
                    String is_fan = bean.getIs_fan();
                    if (is_fan.equals("1")) {
                        mUpgradeZengsong.setVisibility(View.VISIBLE);
                        mUpgradeZengsong.setText(bean.getFan_str());
                    } else if (is_fan.equals("0")) {
                        mUpgradeZengsong.setVisibility(View.GONE);
                    }
                    String good_stock = bean.getGood_stock();
                    if (!good_stock.equals("0")) {
                        mOrderKucun.setText("库存：" + good_stock);
                    }
                } else {
//                    Toast.makeText(DetailPageActivity.this, "xx", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GoodsDetailsFeng> call, Throwable t) {

            }
        });
        Map<String, Object> detail_map = new HashMap<>();
        detail_map.put("good_id", mMoodId);
        Call<HttpResponse> detail_call = service.getSharePro(detail_map);
        detail_call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    mShare_url = httpResponse.getInfo();
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {

            }
        });

    }

    public void AddCart() {//加入购物车
        String specId = "";//选中的规格id
        if (speciBeanList != null) {
            if (speciBeanList.size() != 0) {
                if (myList != null) {
                    for (int i = 0; i < myList.size(); i++) {
                        if (specId.equals("")) {
                            specId = myList.get(i).getMyid();
                        } else {
                            specId = specId + "," + myList.get(i).getMyid();
                        }
                    }
                    Log.e("yh", "specId--" + specId);
                }
            }
        }
        if (specId != null && specId.equals("")) {
            ToastUtil.showToast(DetailPageActivity.this, "请选择商品规格");
            return;
        } else if (specId == null) {
            ToastUtil.showToast(DetailPageActivity.this, "请选择商品规格");
            return;
        }
        onAddCart(specId);//加入购物车
    }

    public void onBuyNow() {//立刻购买
        String specId = "";//选中的规格id
        if (speciBeanList != null & speciBeanList.size() != 0) {
            if (myList != null) {
                for (int i = 0; i < myList.size(); i++) {
                    if (specId.equals("")) {
                        specId = myList.get(i).getMyid();
                    } else {
                        specId = specId + "," + myList.get(i).getMyid();
                    }
                }
                Log.e("yh", "specId--" + specId);
            }
        }
        if (specId != null && specId.equals("")) {
            ToastUtil.showToast(DetailPageActivity.this, "请选择商品规格");
            return;
        } else if (specId == null) {
            ToastUtil.showToast(DetailPageActivity.this, "请选择商品规格");
            return;
        }
//        Intent intent = new Intent(this, ConfirmOrderActivity.class);
        if (bean != null) {
            String good_name = bean.getGood_name();//商品名称
            GoodsDetailsFeng.InfoBean.MoneyBean moneybeans = bean.getMoney();//全部价格
            String money_maters = moneybeans.getMoney_mater();//母币价格
            String money_sons = moneybeans.getMoney_son();//子币价格
            String nums = mNumber.getText().toString();//选中数量
//            intent.putExtra("nums", nums);
//            intent.putExtra("goods_id", mMoodId); //商品id
//            intent.putExtra("specId", specId); //选中的规格id
            xiadan(mMoodId, specId, nums);
        }
//        startActivity(intent);
    }

    public void xiadan(String goodsid, String specId, String nums) {
        Map<String, Object> map = new HashMap<>();
        map.put("good_id", goodsid);//商品id
        map.put("speci_vals", specId);//所选规格id 用,隔开
        map.put("order_num", nums);//数量
        map.put("user_id", mUserId);
        Call<OrderInfoResponse> call = service.getOrderInfo(map);
        Log.e("xiadan参数", "?good_id=" + goodsid + "&" + "speci_vals=" + specId + "&order_num=" + nums + "&user_id=" + mUserId);
        call.enqueue(new Callback<OrderInfoResponse>() {
            @Override
            public void onResponse(Call<OrderInfoResponse> call, Response<OrderInfoResponse> response) {
                OrderInfoResponse pcfeng = response.body();
                int code = pcfeng.getCode();
                if (code == 100) {
                    Intent intent = new Intent(DetailPageActivity.this, ConfirmOrderActivity.class);
                    String danhao_str = "";
                    for (String str : pcfeng.getInfo()) {
                        danhao_str = str + ",";
                    }
                    intent.putExtra("danhao", danhao_str.substring(0, danhao_str.length() - 1));
                    startActivity(intent);
                    popupWindow.dismiss();
                } else {
                    ToastUtil.showToast(DetailPageActivity.this, pcfeng.getSuccess());
                }

            }

            @Override
            public void onFailure(Call<OrderInfoResponse> call, Throwable t) {
                ToastUtil.showToast(DetailPageActivity.this, "网络异常");
            }
        });
    }


    public void onAddCart(String specId) {//加入购物车
        Log.e("yh", "加入购物车--" + "--mUserId--" + mUserId + "--商品id--" + mMoodId + "--所选规格id--" + specId + "--数量--" + mNumber.getText().toString());
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("good_id", mMoodId);//商品id
        map.put("speci_id", specId);//	所选规格id 用，隔开
        map.put("num", mNumber.getText().toString());//数量
        Call<ReturnFeng> call = service.getInsertShoppingCart(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(DetailPageActivity.this, success, Toast.LENGTH_SHORT).show();
                    number = 1;
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(DetailPageActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Toast.makeText(DetailPageActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void collect() {//收藏商品
        Map<String, Object> map = new HashMap<>();
        map.put("good_id", mMoodId);//商品id
        map.put("user_id", mUserId);
        map.put("type", "1");//1:团购 2：意向
        Call<ReturnFeng> call = service.getCollectGoods(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(DetailPageActivity.this, success, Toast.LENGTH_SHORT).show();
                    onMessage();
                } else {
                    Toast.makeText(DetailPageActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Toast.makeText(DetailPageActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelCollect() {//取消收藏商品
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("good_id", mMoodId);//商品id
        map.put("type", 1);//1:商品 2：意向
        Call<ReturnFeng> call = service.getCancelCollection(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(DetailPageActivity.this, success, Toast.LENGTH_SHORT).show();
                    onMessage();
                } else {
                    Toast.makeText(DetailPageActivity.this, success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Toast.makeText(DetailPageActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onShare() {//分享
        if (!mShare_url.equals("")) {
            //this  连接地址   标题  内容     网络图片路径     本地缩略图路径    不用面板要打开的地方
            ShareUtils.shareWeb(DetailPageActivity.this, mShare_url, "友趣团购", "", "", R.mipmap.logo, SHARE_MEDIA.QQ);
        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    public void onRoastingTu(List<GoodsDetailsFeng.InfoBean.ImgBean> imgs) {//轮播图
        mMapList = new ArrayList<>();
        list = new ArrayList<>();
        titleList = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            Uri uri = Uri.parse(C.TU + imgs.get(i).getImg_url());
            list.add(uri);
            Map<String, Object> map = new HashMap<>();
            map.put("image", C.TU + imgs.get(i).getImg_url());
            mMapList.add(map);
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
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                List<View> mlist = new ArrayList<>();
                List<String> mdizhi = new ArrayList<>();
                for (Map<String, Object> map : mMapList) {
                    PhotoView photoView = new PhotoView(DetailPageActivity.this);
                    Glide.with(DetailPageActivity.this).load(map.get("image")).into(photoView);
                    mlist.add(photoView);
                    mdizhi.add((String) map.get("image"));
                }
                MyViewPagDialog myViewPagDialog = new MyViewPagDialog(DetailPageActivity.this, mlist, mdizhi, position);
                myViewPagDialog.show();
            }
        });
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
        number = 1;
    }

    @Override
    public void onGVItemClick(final TextView mTextView, final List<Map<String, Object>> list) {
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
                    if (!img.equals("")) {
                        String thumb = C.TU + img;
                        guige_imgv = C.TU + img;
//                        Log.e("yh", "thumb--" + thumb);
                        Glide.with(DetailPageActivity.this)
                                .load(thumb)
                                .into(mFigure);
                    }

                    myList.get(place).setMyid(gsp_id);
                    myList.get(place).setMyname(gsp_value);
                    mListViewAdapter.notifyDataSetChanged();
                    goodsRMB();//商品价格
                }
            }
        });
    }
}

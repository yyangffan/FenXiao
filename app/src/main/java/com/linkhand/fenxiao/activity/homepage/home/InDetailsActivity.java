package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.GradeRecyAdapter;
import com.linkhand.fenxiao.bean.PinglunBean;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.home.IdeaGoodsDetailsFeng;
import com.linkhand.fenxiao.feng.home.LiuYanBean;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.MyImageLoader;
import com.linkhand.fenxiao.utils.MyRecycleView;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.utils.youmeng.ShareUtils;
import com.linkhand.fenxiao.views.MyWevClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
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

/********************************************************************
 @version: 1.0.0
 @description: 意向商品详情
 @author: user
 @time: 2018/5/21 11:10
 @变更历史:
 ********************************************************************/
public class InDetailsActivity extends BaseActicity implements View.OnClickListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout mReturn;//返回
    TabLayout mTabLayout;
    //    List<Fragment> mList;//片段
    TextView mPurchasing;//立刻购买
    String mUserId;//个人id
    @Bind(R.id.details_banner)
    Banner mBanner;
    @Bind(R.id.detail_collect_id)
    ImageView mCollect;//收藏图片
    @Bind(R.id.indetails_zi_sum)
    TextView mZiSum;
    @Bind(R.id.indetails_mu_sum)
    TextView mMuSum;
    @Bind(R.id.think_sum_id)
    TextView mThinkSum;
    @Bind(R.id.indetails_name_id)
    TextView mIndetailsName;
    @Bind(R.id.wv)
    WebView mWv;
    @Bind(R.id.details_liuyan)
    LinearLayout mDetailsLiuyan;
    @Bind(R.id.details_recy)
    MyRecycleView mDetailsRecy;
    @Bind(R.id.upgrade_smart)
    SmartRefreshLayout mUpgradeSmart;

    LinearLayout mLLayoutCollect;//收藏
    LinearLayout mShare;//分享
    InfoData service;
    IdeaGoodsDetailsFeng.InfoBean bean;//商品数据
    int house;//是否已收藏 1 是  0 否
    int have = 0;//是否已关注  1是  0否
    String mMoodId;//商品id
    List<Uri> list;
    List<String> titleList;

    private DisplayImageOptions options;
    private String mShare_url = "";


    private GradeRecyAdapter mGradeAdapter;
    private List<PinglunBean.InfoBean> mPingList;
    private AlertDialog mLiuyanDialog;
    private int page = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_details);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
        onClicks();
        onMessage();
        configLiuYanDialog();
    }


    public void initView() {
        mLLayoutCollect = (LinearLayout) findViewById(R.id.detail_collect_llayout_id);//收藏
        mReturn = (LinearLayout) findViewById(R.id.home_return_id3);
        mTabLayout = (TabLayout) findViewById(R.id.detailPage_TabLayout_id);
        mPurchasing = (TextView) findViewById(R.id.detail_purchasing_id);
        mShare = (LinearLayout) findViewById(R.id.details_share);//分享

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        mTabLayout.addTab(mTabLayout.newTab().setText("图文详情"));
        mTabLayout.addTab(mTabLayout.newTab().setText("留言"));
        //取得从上一个Activity当中传递过来的Intent对象
        Intent intent = getIntent();
        //从Intent当中根据key取得value

        if (intent != null) {
            mMoodId = intent.getStringExtra("idea_id"); //商品id
        }
        editor.remove("addressId").commit();

        mPingList = new ArrayList<>();
        mGradeAdapter = new GradeRecyAdapter(this, mPingList);
        LinearLayoutManager lineaManager = new LinearLayoutManager(this);
        lineaManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDetailsRecy.setLayoutManager(lineaManager);
        mDetailsRecy.setAdapter(mGradeAdapter);
        mUpgradeSmart.setEnableRefresh(false);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {//图文详情
                    mWv.setVisibility(View.VISIBLE);
                    mDetailsRecy.setVisibility(View.GONE);
                    mUpgradeSmart.setEnableLoadmore(false);
                } else { //评论
                    page = 0;
                    getLiuyan();
                    mWv.setVisibility(View.GONE);
                    mDetailsRecy.setVisibility(View.VISIBLE);
                    mUpgradeSmart.setEnableLoadmore(true);
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
                ++page;
                getLiuyan();
            }
        });

    }

    public void onClicks() {
        mReturn.setOnClickListener(this);//返回
        mPurchasing.setOnClickListener(this);//立刻购买
        mLLayoutCollect.setOnClickListener(this);//收藏
        mShare.setOnClickListener(this);//分享
        mDetailsLiuyan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.detail_collect_llayout_id://收藏
                if (house == 1) {//是否已关注  1是  0否
                    cancelCollect();//取消关注商品
                } else if (house == 0) {
                    collect();//关注商品
                }
                break;
            case R.id.details_share://分享
                onShare();//分享
                break;
            case R.id.home_return_id3://返回
                this.finish();
                break;
            case R.id.detail_purchasing_id://立刻购买
                if (have == 1) {//是否已关注  1是  0否
                    onCancelOrder();//取消订购（取消意向）
                } else {
                    onOrder();//立即订购(关注意向)
                }
                break;
            case R.id.details_liuyan://留言弹窗
                mLiuyanDialog.show();
                break;
        }
    }

    /*初始化留言弹窗*/
    public void configLiuYanDialog() {
        mLiuyanDialog = new AlertDialog.Builder(this).create();
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_liuyan, null);
        final EditText editText = (EditText) v.findViewById(R.id.dialog_liuyan_edt);
        TextView mtv_cancel = (TextView) v.findViewById(R.id.dialog_liuyan_cancel);
        TextView mtv_commit = (TextView) v.findViewById(R.id.dialog_liuyan_commit);
        ImageView mimgv_dis = (ImageView) v.findViewById(R.id.dialog_liuyan_imgv);
        mLiuyanDialog.setView(v);
        mLiuyanDialog.setCanceledOnTouchOutside(false);
        mimgv_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLiuyanDialog.dismiss();
            }
        });
        mtv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLiuyanDialog.dismiss();
            }
        });
        mtv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (s != null && s.length() != 0) {
                    commitLiuyan(s);
                    editText.setText("");
                    mLiuyanDialog.dismiss();
                } else {
                    ToastUtil.showToast(InDetailsActivity.this, "请填写您的留言");
                }
            }
        });
    }

    public void commitLiuyan(String liuyan) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("idea_id", mMoodId);
        map.put("msg_text", liuyan);
        Call<HttpResponse> call = service.getMessAgeAdd(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    page = 0;
                    getLiuyan();
                } else {
                    ToastUtil.showToast(InDetailsActivity.this, httpResponse.getSuccess());
                }

            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(InDetailsActivity.this, "网络异常");
            }
        });


    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("idea_id", mMoodId);//商品id
        map.put("user_id", mUserId);
        Call<IdeaGoodsDetailsFeng> call = service.getIdeaGoodsDetails(map);
        call.enqueue(new Callback<IdeaGoodsDetailsFeng>() {
            @Override
            public void onResponse(Call<IdeaGoodsDetailsFeng> call, Response<IdeaGoodsDetailsFeng> response) {
                IdeaGoodsDetailsFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                if (code == 100) {
                    bean = pcfeng.getInfo();
                    List<IdeaGoodsDetailsFeng.InfoBean.ImgBean> imgs = bean.getImg();//轮播图

                    house = bean.getHouse_have();//是否已收藏  1是  0否
                    have = bean.getHave();//是否已关注  1是  0否
                    String good_name = bean.getIdea_name();//商品名称
                    String money_mater = bean.getIdea_mater();//母币价格
                    String money_son = bean.getIdea_son();//子币价格
                    String money_num = bean.getIdea_nownum();//当前收藏人数


                    mZiSum.setText(money_son);
                    mMuSum.setText(money_mater);
                    mThinkSum.setText(money_num + "人想买");
                    mIndetailsName.setText(good_name);

                    if (house == 1) {//是否已收藏  1是  0否
                        mCollect.setImageResource(R.drawable.collection_two);
                    } else if (house == 0) {
                        mCollect.setImageResource(R.drawable.collection);
                    }
                    if (have == 1) {//是否已关注  1是  0否
                        mPurchasing.setText("取消订购");
                    } else {
                        mPurchasing.setText("立即订购");
                    }
                    String content = bean.getIdea_intro();
                    if (content != null) {
                        mWv.setVisibility(View.VISIBLE);
                        mDetailsRecy.setVisibility(View.GONE);
                        mUpgradeSmart.setEnableLoadmore(false);
                        mWv.setWebViewClient(new MyWevClient());
                        mWv.getSettings().setJavaScriptEnabled(true);
                        mWv.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
                    }
                    onRoastingTu(imgs);//轮播图
                } else {
                    Toast.makeText(InDetailsActivity.this, "xx", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<IdeaGoodsDetailsFeng> call, Throwable t) {
                Toast.makeText(InDetailsActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
        Map<String, Object> share_map = new HashMap<>();
        share_map.put("idea_id", mMoodId);
        Call<HttpResponse> share_call = service.getShareIdea(share_map);
        share_call.enqueue(new Callback<HttpResponse>() {
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

    /*获取留言数据*/
    public void getLiuyan() {
        Map<String, Object> map = new HashMap<>();
        map.put("idea_id", mMoodId);
        map.put("pag", page);
        Call<LiuYanBean> call = service.getMessAgeList(map);
        call.enqueue(new Callback<LiuYanBean>() {
            @Override
            public void onResponse(Call<LiuYanBean> call, Response<LiuYanBean> response) {
                mUpgradeSmart.finishRefresh();
                mUpgradeSmart.finishLoadmore();
                LiuYanBean liuYanBean = response.body();
                if (liuYanBean.getCode() == 100) {
                    if (page == 0) {
                        mPingList.clear();
                    }
                    for (int i = 0; i < liuYanBean.getInfo().size(); i++) {
                        LiuYanBean.InfoBean liuyan_info = liuYanBean.getInfo().get(i);
                        PinglunBean.InfoBean infoBean = new PinglunBean.InfoBean(liuyan_info.getMsg_text(), liuyan_info.getUser_name(), liuyan_info.getUser_head_img());
                        mPingList.add(infoBean);
                    }
                    mGradeAdapter.notifyDataSetChanged();
                } else if (liuYanBean.getCode() == 200) {
                    ToastUtil.showToast(InDetailsActivity.this, liuYanBean.getSuccess());
                } else {
                    mPingList.clear();
                    mGradeAdapter.notifyDataSetChanged();
                    ToastUtil.showToast(InDetailsActivity.this, liuYanBean.getSuccess());
                }

            }

            @Override
            public void onFailure(Call<LiuYanBean> call, Throwable t) {
                mUpgradeSmart.finishRefresh();
                mUpgradeSmart.finishLoadmore();
                ToastUtil.showToast(InDetailsActivity.this, "网络异常");
            }
        });

    }

    public void onCancelOrder() {//取消订购（取消意向）
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("idea_id", mMoodId);
        Call<ReturnFeng> call = service.getDeleteIntention(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(InDetailsActivity.this, success, Toast.LENGTH_SHORT).show();
//                    InDetailsActivity.this.finish();
                    onMessage();
                } else {
                    Toast.makeText(InDetailsActivity.this, success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    public void onOrder() {//立即订购(关注意向)
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("idea_id", mMoodId);
        Call<ReturnFeng> call = service.getAddIntention(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(InDetailsActivity.this, success, Toast.LENGTH_SHORT).show();
//                    InDetailsActivity.this.finish();
                    onMessage();
                } else {
                    Toast.makeText(InDetailsActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }


    public void collect() {//收藏商品
        Map<String, Object> map = new HashMap<>();
        map.put("good_id", mMoodId);//商品id
        map.put("user_id", mUserId);
        map.put("type", "2");//1:商品 2：意向
        Call<ReturnFeng> call = service.getCollectGoods(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(InDetailsActivity.this, success, Toast.LENGTH_SHORT).show();
                    onMessage();
                } else {
                    Toast.makeText(InDetailsActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    public void cancelCollect() {//取消收藏
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("good_id", mMoodId);//商品id
        map.put("type", "2");//1:商品 2：意向
        Call<ReturnFeng> call = service.getCancelCollection(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(InDetailsActivity.this, success, Toast.LENGTH_SHORT).show();
                    onMessage();
                } else {
                    Toast.makeText(InDetailsActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    public void onShare() {//分享
        if (!mShare_url.equals("")) {
            //this  连接地址   标题  内容     网络图片路径     本地缩略图路径    不用面板要打开的地方
            ShareUtils.shareWeb(InDetailsActivity.this, mShare_url, "友趣团购", "", "", R.mipmap.logo, SHARE_MEDIA.QQ);
        } else {

        }
    }

    public void onRoastingTu(List<IdeaGoodsDetailsFeng.InfoBean.ImgBean> imgs) {//轮播图
        list = new ArrayList<>();
        titleList = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            Uri uri = Uri.parse(C.TU + imgs.get(i).getIdea_img_url());
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
    }


}

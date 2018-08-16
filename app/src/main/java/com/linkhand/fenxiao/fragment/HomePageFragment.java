package com.linkhand.fenxiao.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.HomeGongGActivity;
import com.linkhand.fenxiao.activity.homepage.home.DetailPageActivity;
import com.linkhand.fenxiao.activity.homepage.home.InDetailsActivity;
import com.linkhand.fenxiao.activity.homepage.home.IntentionActivity;
import com.linkhand.fenxiao.activity.homepage.home.MienActivity;
import com.linkhand.fenxiao.activity.homepage.home.OpenGroupActivity;
import com.linkhand.fenxiao.activity.homepage.home.SearchActivity;
import com.linkhand.fenxiao.activity.homepage.home.TicketActivity;
import com.linkhand.fenxiao.activity.homepage.home.VIPDetailPageActivity;
import com.linkhand.fenxiao.activity.news.NewsActivity;
import com.linkhand.fenxiao.adapter.HomePageVipGoodsAdapter;
import com.linkhand.fenxiao.adapter.HomeRecyAdapter;
import com.linkhand.fenxiao.dialog.MyDialog;
import com.linkhand.fenxiao.dialog.MyDialogApprove;
import com.linkhand.fenxiao.dialog.MyDialogLogin;
import com.linkhand.fenxiao.dialog.MyDialogVip;
import com.linkhand.fenxiao.feng.AllConfigFeng;
import com.linkhand.fenxiao.feng.home.HomeMessageBean;
import com.linkhand.fenxiao.feng.home.MineGongGBean;
import com.linkhand.fenxiao.feng.home.RecommendedGoods;
import com.linkhand.fenxiao.feng.home.SlideshowFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.HomeInfo;
import com.linkhand.fenxiao.info.callback.HomeVipInfo;
import com.linkhand.fenxiao.utils.MyImageLoader;
import com.linkhand.fenxiao.utils.MyRecycleView;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.views.vertical_view.MarqueeView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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

/**
 * 首页
 * A simple {@link Fragment} subclass
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener, HomeInfo, HomeVipInfo {

    Banner mBanner;//轮播图
    List<Uri> list;
    List<Map<String, Object>> mBanner_ids;//轮播图的id
    List<String> titleList;

    LinearLayout mIntention;//意向商品
    LinearLayout mOpengroup;//开团专区
    LinearLayout mInvite;//邀请好友
    LinearLayout mTicket;//返券专区
    RelativeLayout mMien;//企业风采
    FrameLayout mNews;//消息通知
    LinearLayout mSearch;//搜索
    @Bind(R.id.qyfc_red)
    TextView mQyfcRed;
    @Bind(R.id.home_page_marquee)
    MarqueeView mHomePageMarquee;
    @Bind(R.id.home_recy_tuijian)
    MyRecycleView mHomeRecyTuijian;

    private TextView mtv_red;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    InfoData service; //接口的实现类
    HomePageVipGoodsAdapter mVipAdapter;
    int mIsOne = 0;//获取是否第一次进首页instructions须知  1是提示完了
    RefreshLayout refreshLayout;//上下拉
    String mUserIsVip; //是否vip  0否  1是
    private List<String> mGongGstrs;

    private HomeRecyAdapter mHomeRecyAdapter;
    private List<RecommendedGoods.InfoBean> beanList;
    public String mUserReal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, v);
        initEver();
        initRetrofit();//初始化retrofit
        init(v);
        onUpDowns();//上下拉
        onClicks();
        slideshow();//获取轮播图
//        onVipMessage();//vip购买
        onMessage();
        onAnnouncement();//平台须知
        getHaveMsg();//是否有未读消息
        getGongG();//获取要显示的公告
        return v;
    }

    public void initEver() {
        beanList = new ArrayList<>();
        mHomeRecyAdapter = new HomeRecyAdapter(this.getActivity(), beanList);
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };/*这个是瀑布流 ，没有用到*/
        GridLayoutManager manager = new GridLayoutManager(this.getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mHomeRecyTuijian.setLayoutManager(manager);
        mHomeRecyTuijian.addItemDecoration(new DividerGridItemDecoration(this.getActivity(), R.drawable.gray_juxing));
        mHomeRecyTuijian.setAdapter(mHomeRecyAdapter);
        mHomeRecyAdapter.setOnItemClickListener(new HomeRecyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    String id = beanList.get(position).getGood_id();
                    Intent intent = new Intent(HomePageFragment.this.getActivity(), DetailPageActivity.class);
                    intent.putExtra("good_id", id);
                    startActivity(intent);
                }
            }
        });
    }


    public void init(View v) {
        mGongGstrs = new ArrayList<>();
        mBanner = (Banner) v.findViewById(R.id.banner);
        mIntention = (LinearLayout) v.findViewById(R.id.intention_llayout_id);//意向商品
        mOpengroup = (LinearLayout) v.findViewById(R.id.opengroup_llayout_id);//开团专区
        mInvite = (LinearLayout) v.findViewById(R.id.invite_llayout_id);//邀请好友
        mTicket = (LinearLayout) v.findViewById(R.id.ticket_llayout_id);//返券专区
        mMien = (RelativeLayout) v.findViewById(R.id.mien_llayout_id);//企业风采
        mSearch = (LinearLayout) v.findViewById(R.id.home_search_id);//搜索
        mtv_red = (TextView) v.findViewById(R.id.xiaoxi_red);

        mNews = (FrameLayout) v.findViewById(R.id.fenxiao_news_ll_id);//消息通知
        refreshLayout = (RefreshLayout) v.findViewById(R.id.refreshLayout);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");//获取个人id
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
        mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
        //获取是否第一次进首页instructions须知  1是提示完了
        mIsOne = preferences.getInt("instructions", 0);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    if (mBanner_ids != null) {
                        Map<String, Object> map = mBanner_ids.get(position);
                        String sl_jump = (String) map.get("sl_jump");
                        String sl_type = (String) map.get("sl_type");
                        if (sl_type.equals("1")) {//团购商品详情
                            Intent intent = new Intent(HomePageFragment.this.getActivity(), DetailPageActivity.class);
                            intent.putExtra("good_id", sl_jump);
                            startActivity(intent);
                        } else if (sl_type.equals("2")) {//意向商品详情
                            Intent intent = new Intent(HomePageFragment.this.getActivity(), InDetailsActivity.class);
                            intent.putExtra("idea_id", sl_jump);
                            startActivity(intent);
                        } else {
//                        ToastUtil.showToast(HomePageFragment.this.getActivity(), "无跳转");
                        }
                    }
                }
            }
        });
        mHomePageMarquee.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                HomePageFragment.this.startActivity(new Intent(HomePageFragment.this.getActivity(), HomeGongGActivity.class));
            }
        });

    }

    public void onClicks() {
        mIntention.setOnClickListener(HomePageFragment.this);//意向商品
        mOpengroup.setOnClickListener(HomePageFragment.this);//开团专区
        mInvite.setOnClickListener(HomePageFragment.this);//邀请好友
        mNews.setOnClickListener(HomePageFragment.this);//消息通知
        mSearch.setOnClickListener(HomePageFragment.this);//搜索
        mTicket.setOnClickListener(HomePageFragment.this);//返券专区
        mMien.setOnClickListener(HomePageFragment.this);//企业风采
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intention_llayout_id://意向商品
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    Intent intent = new Intent(HomePageFragment.this.getActivity(), IntentionActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.opengroup_llayout_id://开团专区
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    Intent intent = new Intent(HomePageFragment.this.getActivity(), OpenGroupActivity.class);
                    intent.putExtra("is_type", "1");
                    startActivity(intent);
                }
                break;
            case R.id.invite_llayout_id://金牌专区
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    Intent intent = new Intent(HomePageFragment.this.getActivity(), OpenGroupActivity.class);
                    intent.putExtra("is_type", "2");
                    startActivity(intent);
                }
                break;

            case R.id.ticket_llayout_id://返券专区
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    Intent intent = new Intent(HomePageFragment.this.getActivity(), TicketActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mien_llayout_id://企业风采
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    Intent intent = new Intent(HomePageFragment.this.getActivity(), MienActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fenxiao_news_ll_id://消息通知
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    Intent intent = new Intent(HomePageFragment.this.getActivity(), NewsActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.home_search_id://搜索
                mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    if (mUserIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        Intent intent = new Intent(HomePageFragment.this.getActivity(), SearchActivity.class);
                        startActivity(intent);
                    } else if (mUserIsVip.equals("0")) {
                        onIsLoginVip();//购买vip
                    }
                }
                break;
        }
    }

    /*获取公告列表*/
    public void getGongG() {
        Map<String, Object> map = new HashMap<>();
        map.put("list_type", "1");
        Call<MineGongGBean> call = service.getGetNotice(map);
        call.enqueue(new Callback<MineGongGBean>() {
            @Override
            public void onResponse(Call<MineGongGBean> call, Response<MineGongGBean> response) {
                MineGongGBean bean = response.body();
                mGongGstrs.clear();
                if (bean.getCode() == 100) {
                    for (MineGongGBean.InfoBean infoBean : bean.getInfo()) {
                        mGongGstrs.add(infoBean.getNo_text().length() > 18 ? infoBean.getNo_text().substring(0, 18) + "..." : infoBean.getNo_text());
                    }
                    if (mHomePageMarquee != null) {
                        mHomePageMarquee.startWithList(mGongGstrs, R.anim.anim_bottom_in, R.anim.anim_top_out);
                    }

                } else {
                    for (int i = 0; i < 2; i++) {
                        mGongGstrs.add("暂无公告");
                    }
                    if (mHomePageMarquee != null) {
                        mHomePageMarquee.startWithList(mGongGstrs, R.anim.anim_bottom_in, R.anim.anim_top_out);
                    }
                }
            }

            @Override
            public void onFailure(Call<MineGongGBean> call, Throwable t) {

            }
        });


    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        Call<RecommendedGoods> call = service.getRecommended(map);
        call.enqueue(new Callback<RecommendedGoods>() {
            @Override
            public void onResponse(Call<RecommendedGoods> call, Response<RecommendedGoods> response) {
                RecommendedGoods pcfeng = response.body();
                int code = pcfeng.getCode();
                beanList.clear();
                if (code == 100) {
                    beanList.addAll(pcfeng.getInfo());
                } else {
//                    ToastUtil.showToast(HomePageFragment.this.getActivity(),pcfeng.getSuccess());
                }
                mHomeRecyAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<RecommendedGoods> call, Throwable t) {
                ToastUtil.showToast(HomePageFragment.this.getActivity(), "网络异常");
            }
        });
    }

    /*是否有未读消息 info为未读消息数量  ar_count为企业风采数量*/
    public void getHaveMsg() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<HomeMessageBean> call = service.getTradeCount(map);
        call.enqueue(new Callback<HomeMessageBean>() {
            @Override
            public void onResponse(Call<HomeMessageBean> call, Response<HomeMessageBean> response) {
                HomeMessageBean httpr = response.body();
                if (httpr.getCode() == 100) {
                    String info = httpr.getInfo();
                    if (Integer.parseInt(info) > 99) {
                        info = "99+";
                    }
                    mtv_red.setText(info);
                    mtv_red.setVisibility(info.equals("0") ? View.INVISIBLE : View.VISIBLE);

                    String ar_count = httpr.getAr_count();
                    if (Integer.parseInt(ar_count) > 99) {
                        ar_count = "99+";
                    }
                    if (mQyfcRed != null) {
                        mQyfcRed.setText(ar_count);
                        mQyfcRed.setVisibility(ar_count.equals("0") ? View.INVISIBLE : View.VISIBLE);
                    }
                } else if (httpr.getCode() == 200) {
                    if (mtv_red != null) {
                        mtv_red.setVisibility(View.INVISIBLE);
                    }
                    if (mQyfcRed != null) {
                        mQyfcRed.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeMessageBean> call, Throwable t) {
                ToastUtil.showToast(HomePageFragment.this.getActivity(), "网络异常");
            }
        });


    }


    public void onAnnouncement() {//平台须知
        mUserId = preferences.getString("user_id", "");
        Map<String, Object> map = new HashMap<>();
        Call<AllConfigFeng> call = service.getAllConfig(map);
        call.enqueue(new Callback<AllConfigFeng>() {
            @Override
            public void onResponse(Call<AllConfigFeng> call, Response<AllConfigFeng> response) {
                AllConfigFeng pcfeng = response.body();
                int code = pcfeng.getCode();
                if (code == 100) {
                    AllConfigFeng.InfoBean bean = pcfeng.getInfo();
                    String Mater_name = bean.getMater_name();//母币名称
                    String Son_name = bean.getSon_name();//子币名称
                    //存入子母币名称
                    editor.putString("Mater_name", Mater_name);
                    editor.putString("Son_name", Son_name);
                    editor.commit();
                    if (!mUserId.equals("")) {
                        if (mIsOne == 0) {
                            String notices = bean.getNotice() + "";//第一次进入系统公告
                            MyDialog dialog = new MyDialog(getActivity());
                            dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
                            dialog.show();
                            TextView notice = (TextView) dialog.findViewById(R.id.config_notice_id);//公告须知内容
                            ScrollView scrol = (ScrollView) dialog.findViewById(R.id.dilog_scroll);
                            ViewGroup.LayoutParams layoutParams = scrol.getLayoutParams();
                            if (notices.length() > 240) {
                                layoutParams.height=600;
                                scrol.setLayoutParams(layoutParams);
                            }
                            notice.setText(notices);
                            mIsOne = 1;
                            //存入instructions须知  1是提示完了
                            editor.putInt("instructions", 1);
                            editor.commit();
                        }
                    }


                } else {
//                    Toast.makeText(HomePageFragment.this.getActivity(), "xx", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllConfigFeng> call, Throwable t) {

            }
        });

    }

    public void onIsLogin() {//登录注册去
        MyDialogLogin dialog = new MyDialogLogin(getActivity());
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
    }

    public void onIsLoginVip() {//购买vip
        MyDialogVip dialog = new MyDialogVip(getActivity());
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
    }

    public void onApprove() {//实名认证
        MyDialogApprove dialog = new MyDialogApprove(this.getActivity());
        dialog.show();
    }

    public void onUpDowns() {//上下拉
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Log.e("yh", "下");
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Log.e("yh", "上");
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    public void slideshow() {//获取轮播图
        Map<String, Object> map = new HashMap<>();
        Call<SlideshowFeng> call = service.getSlideshow(map);
        call.enqueue(new Callback<SlideshowFeng>() {
            @Override
            public void onResponse(Call<SlideshowFeng> call, Response<SlideshowFeng> response) {
                SlideshowFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                if (code == 100) {
                    List<SlideshowFeng.InfoBean> beanList = pcfeng.getInfo();
                    onRoastingTu(beanList);
                } else {
//                    Toast.makeText(HomePageFragment.this.getActivity(), "xx", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SlideshowFeng> call, Throwable t) {

            }
        });
    }

    public void onRoastingTu(List<SlideshowFeng.InfoBean> beanList) {//轮播图
        list = new ArrayList<>();
        mBanner_ids = new ArrayList<>();
        titleList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            Uri uri = Uri.parse(C.TU + beanList.get(i).getSl_img_url());
            list.add(uri);
            Map<String, Object> map = new HashMap<>();
            map.put("sl_jump", beanList.get(i).getSl_jump());
            map.put("sl_type", beanList.get(i).getSl_type());
            mBanner_ids.add(map);
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
        mBanner.setBannerAnimation(Transformer.Default);
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
    }

    @Override
    public void onItemClicks(RelativeLayout mRelativeLayout, final List<Map<String, Object>> list) {

        mRelativeLayout.setOnClickListener(new View.OnClickListener() {//详情页
            @Override
            public void onClick(View v) {
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    String id = list.get(0).get("id").toString();//商品id
                    Intent intent = new Intent(HomePageFragment.this.getActivity(), DetailPageActivity.class);
                    intent.putExtra("good_id", id);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //获取是否第一次进首页instructions须知  1是提示完了
//        editor.remove("instructions").commit();
    }

    //vip购买详情
    @Override
    public void onVipItemClicks(RelativeLayout mRelativeLayout, final List<Map<String, Object>> list) {
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserId.equals("")) {
                    onIsLogin();//登录注册去
                } else {
                    String vip_id = list.get(0).get("vip_id").toString();//商品id
                    Intent intent = new Intent(HomePageFragment.this.getActivity(), VIPDetailPageActivity.class);
                    intent.putExtra("vip_id", vip_id);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
//        onVipMessage();//vip购买
        onMessage();
    }

    public void OnRefresh() {
        mUserId = preferences.getString("user_id", "");
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
        mIsOne = preferences.getInt("instructions", 0);        //获取是否第一次进首页instructions须知  1是提示完了
//        onVipMessage();//vip购买
        onMessage();
        getHaveMsg();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

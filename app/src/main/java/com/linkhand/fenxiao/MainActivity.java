package com.linkhand.fenxiao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.fenxiao.activity.homepage.HomePageActivity;
import com.linkhand.fenxiao.dialog.MvpAdapter;
import com.linkhand.fenxiao.feng.AllConfigFeng;
import com.linkhand.fenxiao.feng.home.YDBean;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.utils.UpdateManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/********************************************************************
 @version: 1.0.0
 @description: 启动页--引导页
 @author: 杨帆
 @time: 2018/7/19 14:56
 @变更历史:
 ********************************************************************/

public class MainActivity extends BaseActicity {
    @Bind(R.id.mian_tv_finish)
    TextView mMainTvFinish;
    @Bind(R.id.main_mvp)
    ViewPager mMainMvp;
    @Bind(R.id.main_ll)
    LinearLayout mMainLl;

    private List<View> mViews;
    private List<ImageView> mImageViews;
    private MvpAdapter mMvpAdapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private boolean mShowStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkUpdate();
        getMastSon();
    }

    public void checkUpdate() {
        UpdateManager updateManager = new UpdateManager(this, false);
        updateManager.checkUpdate();
        updateManager.setOnCancelClickListener(new UpdateManager.OnCancelClickListener() {
            @Override
            public void OnCancelClickListener() {
                MainActivity.this.finish();
            }
        });
        updateManager.setIsUpDateListener(new UpdateManager.IsUpDateListener() {
            @Override
            public void IsUpDateListener(boolean isUpdate) {
                if (!isUpdate) {
                    initEver();
                }
            }
        });
    }

    /*获取引导页*/
    public void initEver() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mShowStart = preferences.getBoolean("isShowStart", true);
        if (!mShowStart) {
            startActivity(new Intent(MainActivity.this, HomePageActivity.class));
            finish();
            return;
        }

        mViews = new ArrayList<>();
        mImageViews = new ArrayList<>();

        mMvpAdapter = new MvpAdapter(this, mViews, null);
        mMainMvp.setAdapter(mMvpAdapter);
        mMainMvp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mImageViews.size(); i++) {
                    if (i == position) {
                        mImageViews.get(i).setImageResource(R.drawable.indicator_select);
                    } else {
                        mImageViews.get(i).setImageResource(R.drawable.indicator_unselect);
                    }
                }
                if (position == (mImageViews.size() - 1)) {
                    mMainTvFinish.setVisibility(View.VISIBLE);
                } else {
                    mMainTvFinish.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getYinDao();
    }


    @OnClick(R.id.mian_tv_finish)
    public void onViewClicked() {
        startActivity(new Intent(MainActivity.this, HomePageActivity.class));
        editor.putBoolean("isShowStart", false);
        editor.commit();
        finish();
    }

    public void getMastSon() {
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

                } else {
                }

            }

            @Override
            public void onFailure(Call<AllConfigFeng> call, Throwable t) {

            }
        });
    }

    /*获取引导页图片*/
    public void getYinDao() {
        Call<YDBean> call = service.getPic(new HashMap<String, Object>());
        call.enqueue(new Callback<YDBean>() {
            @Override
            public void onResponse(Call<YDBean> call, Response<YDBean> response) {
                YDBean ydBean = response.body();
                int code = ydBean.getCode();
                if (code == 100) {
                    setMsg(ydBean.getInfo());
                }

            }

            @Override
            public void onFailure(Call<YDBean> call, Throwable t) {
                ToastUtil.showToast(MainActivity.this, "网络异常");
            }
        });
    }

    /*设置数据*/
    public void setMsg(List<String> mStrings) {
        mMainLl.removeAllViews();
        mImageViews.clear();
        for (int i = 0; i < mStrings.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load(C.TU + mStrings.get(i)).into(imageView);
            mViews.add(imageView);
            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            layoutParams.leftMargin = 15;
            layoutParams.rightMargin = 15;
            img.setLayoutParams(layoutParams);
            if (i == 0) {
                img.setImageResource(R.drawable.indicator_select);
            } else {
                img.setImageResource(R.drawable.indicator_unselect);
            }
            mImageViews.add(img);
            mMainLl.addView(img);
        }
        mMvpAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkUpdate();
    }

}

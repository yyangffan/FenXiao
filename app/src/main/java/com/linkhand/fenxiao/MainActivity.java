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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private int[] mInts;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private boolean mShowStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initEver();
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

        mInts = new int[]{R.drawable.intention_goods_img, R.drawable.banner, R.drawable.upload_img};
        mViews = new ArrayList<>();
        mImageViews = new ArrayList<>();

        for (int i = 0; i < mInts.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(mInts[i]);
            mViews.add(imageView);
            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(38, 38);
            layoutParams.leftMargin = 12;
            layoutParams.rightMargin = 12;
            img.setLayoutParams(layoutParams);
            if (i == 0) {
                img.setImageResource(R.drawable.indicator_select);
            } else {
                img.setImageResource(R.drawable.indicator_unselect);
            }
            mImageViews.add(img);
            mMainLl.addView(img);
        }


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

    /*获取引导页图片*/
    public void getYinDao() {
//        Call<HttpResponse> call=service.

//        setMsg();
    }

    /*设置数据*/
    public void setMsg(List<String> mStrings) {
        for (int i = 0; i < mStrings.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load(C.TU + mStrings.get(i)).into(imageView);
            mViews.add(imageView);
            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(38, 38);
            layoutParams.leftMargin = 12;
            layoutParams.rightMargin = 12;
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


}

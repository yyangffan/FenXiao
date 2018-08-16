package com.linkhand.fenxiao.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.dialog.EveryDialog;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.fragment.mineorder.All0rderFragment;
import com.linkhand.fenxiao.fragment.mineorder.IsAppraiseFragment;
import com.linkhand.fenxiao.fragment.mineorder.IsPayFragment;
import com.linkhand.fenxiao.fragment.mineorder.IsShippingFragment;
import com.linkhand.fenxiao.fragment.mineorder.IsTheGoodsFragment;
import com.linkhand.fenxiao.fragment.mineorder.IsWaitFragment;
import com.linkhand.fenxiao.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends BaseActicity implements View.OnClickListener {
    List<Fragment> mList;
    TabLayout mTabLayout;
    ImageView mReturn;//返回
    ImageView mimgv_what;
    int mClick;//点击数

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private All0rderFragment mAll0rderFragment;
    private IsWaitFragment mIsWaitFragment;
    private IsAppraiseFragment mIsAppraiseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        init();
        onClicks();
        onTabLayout();
    }

    public void init() {
        mTabLayout = (TabLayout) findViewById(R.id.mine_tabLayout_id);
        mimgv_what = (ImageView) findViewById(R.id.imgv_what);
        mReturn = (ImageView) findViewById(R.id.mine_return_id);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mList = new ArrayList<>();
        mAll0rderFragment = new All0rderFragment();
        mIsWaitFragment = new IsWaitFragment();
        mIsAppraiseFragment = new IsAppraiseFragment();
        mList.add(mAll0rderFragment);//全部
        mList.add(mIsWaitFragment);//待付款
        mList.add(new IsPayFragment());//拼团中
        mList.add(new IsShippingFragment());//待发货
        mList.add(new IsTheGoodsFragment());//待收货
        mList.add(mIsAppraiseFragment);//待评价
        mTabLayout.addTab(mTabLayout.newTab().setText("全部"));
        mTabLayout.addTab(mTabLayout.newTab().setText("待付款"));
        mTabLayout.addTab(mTabLayout.newTab().setText("拼团中"));
        mTabLayout.addTab(mTabLayout.newTab().setText("待发货"));
        mTabLayout.addTab(mTabLayout.newTab().setText("待收货"));
        mTabLayout.addTab(mTabLayout.newTab().setText("待评价"));
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);
        mimgv_what.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_return_id://返回
                this.finish();
                break;
            case R.id.imgv_what:
                getMessage();
                break;
        }
    }

    /*获取说明内容*/
    public void getMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("get_type", "7");
        Call<HttpResponse> call = service.getDescGet(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    new EveryDialog().showRemind(MyOrderActivity.this, "（不在提示）", "确定", "说明", httpResponse.getInfo(), new EveryDialog.OnTvClickListener() {
                        @Override
                        public void OnSureClickListener() {
                            editor.putBoolean("pintuan_show",false).commit();
                        }
                    });

                } else {
                    ToastUtil.showToast(MyOrderActivity.this, httpResponse.getSuccess());
                }

            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(MyOrderActivity.this, "网络异常");
            }
        });
    }

    public void onTabLayout() {
        //获取点击那个
        mClick = preferences.getInt("isClick", 0);
        if(mClick==2){
            if(preferences.getBoolean("pintuan_show",true)) {
                getMessage();
            }
            mimgv_what.setVisibility(View.VISIBLE);
        }
//        if (mClick == 0) {//全部
        getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id, mList.get(mClick)).commit();
        mTabLayout.getTabAt(mClick).select();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment = mList.get(position);
                //存入mAllPartTime判断点击哪个
                editor.putInt("isClick", position);
                editor.commit();
                if(position==2){
                    if(preferences.getBoolean("pintuan_show",true)) {
                        getMessage();
                    }
                    mimgv_what.setVisibility(View.VISIBLE);
                }else {
                    mimgv_what.setVisibility(View.GONE);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id, fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mAll0rderFragment != null && mAll0rderFragment.isVisible()) {
            mAll0rderFragment.page=0;
            mAll0rderFragment.onMessage();
        }
        if (mIsWaitFragment != null && mIsWaitFragment.isVisible()) {
            mIsWaitFragment.page=0;
            mIsWaitFragment.onMessage();
        }
        if (mIsAppraiseFragment != null && mIsAppraiseFragment.isVisible()) {
            mIsAppraiseFragment.page=0;
            mIsAppraiseFragment.onMessage();
        }
    }
}

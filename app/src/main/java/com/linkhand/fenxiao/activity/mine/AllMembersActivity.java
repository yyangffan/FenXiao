package com.linkhand.fenxiao.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.fragment.mine.AllMembersFragment;
import com.linkhand.fenxiao.fragment.mine.AlreadyRechargeFragment;
import com.linkhand.fenxiao.fragment.mine.AlreadyRegistrationFragment;

import java.util.ArrayList;
import java.util.List;

public class AllMembersActivity extends BaseActicity implements View.OnClickListener {
    List<Fragment> mList;
    TabLayout mTabLayout;
    LinearLayout mReturn;//返回
    int mClick;//点击数

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_members);
        init();
        onClicks();
        onTabLayout();
    }

    public void init() {
        mTabLayout = (TabLayout) findViewById(R.id.mine_tabLayout_id3);
        mReturn = (LinearLayout) findViewById(R.id.allmembers_return_id);


        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mList = new ArrayList<>();
        mList.add(new AllMembersFragment());//全部
        mList.add(new AlreadyRechargeFragment());//已充值
        mList.add(new AlreadyRegistrationFragment());//已注册
        mTabLayout.addTab(mTabLayout.newTab().setText("全部"));
        mTabLayout.addTab(mTabLayout.newTab().setText("已充值"));
        mTabLayout.addTab(mTabLayout.newTab().setText("已注册"));
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);
    }

    public void onTabLayout() {

        //获取点击那个
        mClick = preferences.getInt("isMembersClick", 0);
        Log.e("yh", "mClick--" + mClick);
        if (mClick == 0) {//全部
            Fragment fragment = new AllMembersFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id3, fragment).commit();

            mTabLayout.getTabAt(0).select();
        } else if (mClick == 1) {//已充值
            Fragment fragment = new AlreadyRechargeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id3, fragment).commit();

            mTabLayout.getTabAt(1).select();
        } else if (mClick == 2) {//已注册
            Fragment fragment = new AlreadyRegistrationFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id3, fragment).commit();

            mTabLayout.getTabAt(2).select();
        }


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment = mList.get(position);

                //存入mAllPartTime判断点击哪个
                editor.putInt("isMembersClick", position);
                editor.commit();

                getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id3, fragment).commit();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allmembers_return_id://返回
                this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.remove("isMembersClick").commit();
    }
}

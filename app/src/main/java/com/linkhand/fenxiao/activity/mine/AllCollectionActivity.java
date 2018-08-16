package com.linkhand.fenxiao.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.fragment.mine.BulkCollectionFragment;
import com.linkhand.fenxiao.fragment.mine.IntentionCollectionFragment;

import java.util.ArrayList;
import java.util.List;

public class AllCollectionActivity extends BaseActicity implements View.OnClickListener {
    List<Fragment> mList;
    TabLayout mTabLayout;
    LinearLayout mReturn;//返回
//    int mClick;//点击数

    //    SharedPreferences preferences;
//    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_collection);
        init();
        onClicks();
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_return_id2://返回
                this.finish();
                break;
        }
    }

    public void init() {
        mTabLayout = (TabLayout) findViewById(R.id.mine_tabLayout_id);
        mReturn = (LinearLayout) findViewById(R.id.home_return_id2);
        mList = new ArrayList<>();
        mList.add(new BulkCollectionFragment());//团购商品
        mList.add(new IntentionCollectionFragment());//意向商品
        mTabLayout.addTab(mTabLayout.newTab().setText("商品"));
        mTabLayout.addTab(mTabLayout.newTab().setText("意向"));
        getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id, mList.get(0)).commit();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment = mList.get(position);

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

}

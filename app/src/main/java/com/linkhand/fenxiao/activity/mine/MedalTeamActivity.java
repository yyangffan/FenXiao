package com.linkhand.fenxiao.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.mine.MyTeamFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.RoundImageView;

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

public class MedalTeamActivity extends BaseActicity implements View.OnClickListener {
    List<Fragment> mList;
    TabLayout mTabLayout;
    LinearLayout mReturn;//返回
    int mClick;//点击数
    TextView mAllMembers;//全部成员
    InfoData service;
    String mUserId;//个人id

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RoundImageView mThumb;//头像
    TextView mTeamName;//名称
    TextView mTeamLevels;//层级（铜1）
    @Bind(R.id.details_ids)
    LinearLayout mDetails;//详情

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medal_team);
        ButterKnife.bind(this);
        init();
        onClicks();
        initRetrofit();
        onMessage();//成员详情
    }


    public void init() {
        mTabLayout = (TabLayout) findViewById(R.id.mine_tabLayout_id2);
        mReturn = (LinearLayout) findViewById(R.id.fenxiao_return_id7);
        mAllMembers = (TextView) findViewById(R.id.allMembers);//全部成员
        mThumb = (RoundImageView) findViewById(R.id.roundImageView_id);//头像
        mTeamName = (TextView) findViewById(R.id.team_name);//名称
        mTeamLevels = (TextView) findViewById(R.id.team_level_id);//层级（铜1）


        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);

    }

    public void onTabLayoutList(List<String> tabName) {
        mList = new ArrayList<>();
        mList.add(new OneDeckFragment());//一级(铜1)
        mList.add(new TwoDeckFragment());//一级(铜2)
        mList.add(new ThreeFragment());//一级(铜3)
        mTabLayout.addTab(mTabLayout.newTab().setText(tabName.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabName.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabName.get(2)));

        onTabLayout();
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);
        mAllMembers.setOnClickListener(this);
        mDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenxiao_return_id7://返回
                this.finish();
                break;
            case R.id.details_ids://详情
                Intent intents = new Intent(MedalTeamActivity.this, MembersDetailActivity.class);//成员详情页
                intents.putExtra("user_id", mUserId);
                startActivity(intents);
                break;
            case R.id.allMembers://全部成员
                Intent intent = new Intent(this, AllMembersActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void onTabLayout() {
        //获取点击那个
        mClick = preferences.getInt("isDeckClick", 0);
        Log.e("yh", "mClick--" + mClick);
        if (mClick == 0) {//一级(铜1)
            Fragment fragment = new OneDeckFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id2, fragment).commit();

            mTabLayout.getTabAt(0).select();
        } else if (mClick == 1) {//一级(铜2)
            Fragment fragment = new TwoDeckFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id2, fragment).commit();

            mTabLayout.getTabAt(1).select();
        } else if (mClick == 2) {//一级(铜3)
            Fragment fragment = new ThreeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id2, fragment).commit();

            mTabLayout.getTabAt(2).select();
        }


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment = mList.get(position);

                //存入mAllPartTime判断点击哪个
                editor.putInt("isDeckClick", position);
                editor.commit();

                getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id2, fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void onMessage() {//成员详情
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<MyTeamFeng> call = service.getMyTeam(map);
        call.enqueue(new Callback<MyTeamFeng>() {
            @Override
            public void onResponse(Call<MyTeamFeng> call, Response<MyTeamFeng> response) {
                MyTeamFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    MyTeamFeng.InfoBean infoBean = pcfeng.getInfo();
                    MyTeamFeng.InfoBean.UserBean userBean = infoBean.getUser();
                    String thumb = userBean.getUser_head_img() + "";//头像
                    String user_name = userBean.getUser_name();//账号名称
                    String team_level = userBean.getUser_grade();//团队层级
                    mTeamName.setText(user_name);
                    mTeamLevels.setText(team_level);
                    if (thumb.equals("") | thumb.equals("null")) {

                    } else {
                        thumb = C.TU + thumb;
                        RequestOptions requestOptions=new RequestOptions();
                        requestOptions.placeholder(R.drawable.default_portrait).error(R.drawable.default_portrait);
                        Glide.with(MedalTeamActivity.this).load(thumb).apply(requestOptions).into(mThumb);
                    }

                    List<String> tabName = infoBean.getGrpname();
                    onTabLayoutList(tabName);
                } else {
                    Toast.makeText(MedalTeamActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MyTeamFeng> call, Throwable t) {

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
    }

}

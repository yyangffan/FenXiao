package com.linkhand.fenxiao.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.mine.PersonalMessageFeng;
import com.linkhand.fenxiao.fragment.MineJlActivity;
import com.linkhand.fenxiao.fragment.mine.RechargeFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IsRechargeActivity extends BaseActicity implements View.OnClickListener {
    @Bind(R.id.son_number_id)
    TextView mSonNumberId;
    @Bind(R.id.Mother_number_id)
    TextView mMotherNumberId;
    @Bind(R.id.recharge_jilu)
    TextView mRechargeJilu;


//    List<Fragment> mList;
//    TabLayout mTabLayout;
    LinearLayout mReturn;//返回
    int mClick;//点击数
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private String mZi = "";
    private String mMother = "";
    String Mater_name = "母币";//母币名称
    String Son_name = "子币";//子币名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_recharge);
        ButterKnife.bind(this);
        init();
        onClicks();
        onTabLayout();
    }

    public void init() {
        Intent intent = this.getIntent();
        if (intent != null) {
            mZi = intent.getStringExtra("zi");
            mMother = intent.getStringExtra("mother");
        }
        mSonNumberId.setText(mZi);
        mMotherNumberId.setText(mMother);
//        mTabLayout = (TabLayout) findViewById(R.id.mine_tabLayout_id4);
        mReturn = (LinearLayout) findViewById(R.id.allmembers_return_id2);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        Mater_name = preferences.getString("Mater_name", "母币");//母币名称
        Son_name = preferences.getString("Son_name", "子币");//子币名称
//        mList = new ArrayList<>();
//        mList.add(new RechargeFragment());//充值
//        mList.add(new RegistrationFragment());//提现
//        mTabLayout.addTab(mTabLayout.newTab().setText("充值"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("提现"));
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);
        mRechargeJilu.setOnClickListener(this);
    }

    public void onTabLayout() {
        //获取点击那个
        mClick = preferences.getInt("isRechargeClick", 0);
        Log.e("yh", "mClick--" + mClick);
//        if (mClick == 0) {//充值
            Fragment fragment = new RechargeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id4, fragment).commit();

//            mTabLayout.getTabAt(0).select();
//        } else if (mClick == 1) {//提现
//            Fragment fragment = new RegistrationFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id4, fragment).commit();
//
//            mTabLayout.getTabAt(1).select();
//        }


//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int position = tab.getPosition();
//                Fragment fragment = mList.get(position);
//
//                //存入mAllPartTime判断点击哪个
//                editor.putInt("isRechargeClick", position);
//                editor.commit();
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.mine_fragment_id4, fragment).commit();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    /*充值成功后调用--更新数据*/
    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<PersonalMessageFeng> call = service.getInformation(map);
        call.enqueue(new Callback<PersonalMessageFeng>() {
            @Override
            public void onResponse(Call<PersonalMessageFeng> call, Response<PersonalMessageFeng> response) {
                PersonalMessageFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                if (code == 100) {
                    PersonalMessageFeng.InfoBean mBean = pcfeng.getInfo();
                    String mater_money = mBean.getUser_mater_money();//母币
                    String son_money = mBean.getUser_son_money();//子币
                    if (mSonNumberId != null) {
                        mSonNumberId.setText(son_money+Son_name);
                    }
                    if (mMotherNumberId != null) {
                        mMotherNumberId.setText(mater_money+Mater_name);
                    }
                } else {
//                    Toast.makeText(MineFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PersonalMessageFeng> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allmembers_return_id2://返回
                this.finish();
                break;
            case R.id.recharge_jilu:
                Bundle bundle=new Bundle();
                bundle.putString("what","3");//MineJlActivity标示跳转
                Intent intent = new Intent(this,MineJlActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onMessage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.remove("isRechargeClick").commit();
    }
}

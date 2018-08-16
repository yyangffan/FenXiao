package com.linkhand.fenxiao;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.StatusBarUtils;
import com.umeng.analytics.MobclickAgent;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 11860_000 on 2017/12/15.
 */

public class BaseActicity extends AppCompatActivity {
    public InfoData service;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public String mUserId;//个人id
    public String mUserReal;
    public String pay_pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
        pay_pwd = preferences.getString("pay_pwd", "0");//是否设置支付密码 0否 1是
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.gray_c);//状态栏
        //添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initRetrofit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
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
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
        pay_pwd = preferences.getString("pay_pwd", "0");//是否设置支付密码 0否 1是

    }
}

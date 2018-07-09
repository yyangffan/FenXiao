package com.linkhand.fenxiao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.linkhand.fenxiao.activity.homepage.HomePageActivity;

public class MainActivity extends BaseActicity {
    Intent intent;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String mUserId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    public void initView() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");

        Log.e("yh", "mUserId--" + mUserId);
//        if (mUserId.equals("")) {
//            intent = new Intent(this, .class);//登录
//            startActivity(intent);
//        } else {
            intent = new Intent(this, HomePageActivity.class);//主页
            startActivity(intent);
//        }

        this.finish();
    }
}

package com.linkhand.fenxiao.activity.currency;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.AllConfigFeng;
import com.linkhand.fenxiao.feng.ReturnFeng;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostedActivity extends BaseActicity implements View.OnClickListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id

    @Bind(R.id.fenxiao_return_id3)
    LinearLayout mReturn;//返回
    @Bind(R.id.fenxiao_release)
    TextView mRelease;
    @Bind(R.id.coin_sum_etid)
    EditText mCoinSum;//数量
    @Bind(R.id.coin_sum_etid2)
    EditText mCoinRMB;//单价
    @Bind(R.id.coin_describe_etid)
    EditText mDescribe;//描述
    String Mater_name = "母币";//母币名称
    String Son_name = "子币";//子币名称
    @Bind(R.id.posted_note)
    TextView mPostedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted);
        ButterKnife.bind(this);
        init();
        onClicks();
        getNote();
    }

    public void init() {
        mPostedNote.requestFocus();
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);
        //获取子母币名称
        Mater_name = preferences.getString("Mater_name", "母币");//母币名称
        Son_name = preferences.getString("Son_name", "子币");//子币名称
        mCoinSum.setHint("请输入" + Mater_name + "数量");
        mCoinRMB.setHint("请输入" + Son_name + "单价");
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);//返回
        mRelease.setOnClickListener(this);//发布
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenxiao_return_id3://返回
                this.finish();
                break;
            case R.id.fenxiao_release://发布
                onRelease();
                break;

        }
    }

    public void onRelease() {
        String sum = mCoinSum.getText() + "";//发布母币数量
        String rmb = mCoinRMB.getText() + "";//子币价格
        String describe = mDescribe.getText() + "";//描述
        if (sum.equals("") | rmb.equals("")) {
            Toast.makeText(this, "请填全发布信息", Toast.LENGTH_SHORT).show();
        } else {
            onMessage();
        }
    }


    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("curr_mater_num", mCoinSum.getText().toString());//发布母币数量
        map.put("curr_son_money", mCoinRMB.getText().toString());//子币价格
//        map.put("curr_intro", mDescribe.getText().toString());//描述

        Call<ReturnFeng> call = service.getreleaseCoin(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(PostedActivity.this, success, Toast.LENGTH_SHORT).show();
                    PostedActivity.this.finish();
                } else {
                    Toast.makeText(PostedActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Toast.makeText(PostedActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*获取发布的说明*/
    public void getNote() {
        Call<AllConfigFeng> call = service.getAllConfig(new HashMap<String, Object>());
        call.enqueue(new Callback<AllConfigFeng>() {
            @Override
            public void onResponse(Call<AllConfigFeng> call, Response<AllConfigFeng> response) {
                AllConfigFeng allConfigFeng = response.body();
                int code = allConfigFeng.getCode();
                if (code == 100) {
                    String exchange_note = allConfigFeng.getInfo().getExchange_note();
                    mPostedNote.setText(exchange_note);
                } else {
//                    Toast.makeText(SetUpTheActivity.this, allConfigFeng., Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllConfigFeng> call, Throwable t) {
                Toast.makeText(PostedActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

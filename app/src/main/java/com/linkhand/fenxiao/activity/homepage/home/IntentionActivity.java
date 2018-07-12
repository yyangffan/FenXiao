package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.IntentionAdapter;
import com.linkhand.fenxiao.dialog.MyDialogVip;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.home.IntentionGoods;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.DetailsInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class IntentionActivity extends BaseActicity implements DetailsInfo, View.OnClickListener {
    ListView mListView;
    LinearLayout mReturn;//返回
    IntentionAdapter mAdapter;
    InfoData service;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    String mUserIsVip; //是否vip  0否  1是

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intention);
        init();
        initRetrofit();
        onClicks();
        onMessage(0);//0正常查   1更新数据
    }

    public void init() {
        mListView = (ListView) findViewById(R.id.intention_lv_id);
        mReturn = (LinearLayout) findViewById(R.id.intention_return_id);//返回
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
        mReturn.setOnClickListener(this);
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);//返回
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_return_id://返回
                this.finish();
                break;
            case R.id.intention_return_id:
                this.finish();
                break;
        }
    }

    public void onMessage(final int isPass) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<IntentionGoods> call = service.getIntention(map);
        call.enqueue(new Callback<IntentionGoods>() {
            @Override
            public void onResponse(Call<IntentionGoods> call, Response<IntentionGoods> response) {
                IntentionGoods pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                Log.e("yh", "code--" + code);
                if (code == 100) {
                    List<IntentionGoods.InfoBean> mBean = pcfeng.getInfo();
                    if (isPass == 0) {//0正常查   1更新数据
                        mAdapter = new IntentionAdapter(IntentionActivity.this, mBean);
                        mListView.setAdapter(mAdapter);
                        mAdapter.setOnItemClicks(IntentionActivity.this);
                    } else if (isPass == 1) {
                        mAdapter.setData(mBean);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
//                    Toast.makeText(IntentionActivity.this, "xx", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IntentionGoods> call, Throwable t) {

            }
        });
    }

    public void onCancelOrder(String idea_id) {//取消订购（取消意向）
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("idea_id", idea_id);
        Call<ReturnFeng> call = service.getDeleteIntention(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(IntentionActivity.this, success, Toast.LENGTH_SHORT).show();
//                    IntentionActivity.this.mAdapter.notifyDataSetChanged();
                    onMessage(1);//0正常查   1更新数据
                } else {
                    Toast.makeText(IntentionActivity.this, success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    public void onOrder(String idea_id) {//立即订购(关注意向)
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("idea_id", idea_id);
        Call<ReturnFeng> call = service.getAddIntention(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(IntentionActivity.this, success, Toast.LENGTH_SHORT).show();
//                    IntentionActivity.this.mAdapter.notifyDataSetChanged();
                    onMessage(1);//0正常查   1更新数据
                } else {
                    Toast.makeText(IntentionActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

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

    public void onIsLoginVip() {
        MyDialogVip dialog = new MyDialogVip(this);
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        onMessage(0);//0正常查   1更新数据
    }

    @Override
    public void onItemDetailsClicks(LinearLayout mLinearLayout, TextView mTextView, final List<Map<String, Object>> list) {
        mLinearLayout.setOnClickListener(new View.OnClickListener() {//进详情页
            @Override
            public void onClick(View v) {
                String idea_id = list.get(0).get("idea_id").toString();//商品id
                String is_have = list.get(0).get("is_have").toString();//是否订购(是否已关注 1是 0否)
                Intent intent = new Intent(IntentionActivity.this, InDetailsActivity.class);
                intent.putExtra("idea_id", idea_id);
                startActivity(intent);


            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {//订购
            @Override
            public void onClick(View v) {
                String idea_id = list.get(0).get("idea_id").toString();//商品id
                String is_have = list.get(0).get("is_have").toString();//是否订购(是否已关注 1是 0否)
                if (mUserIsVip.equals("1")) {
                    if (is_have.equals("1")) {
                        onCancelOrder(idea_id);//取消订购
                    } else if (is_have.equals("0")) {
                        onOrder(idea_id);//立即订购
                    }
                } else{
                    onIsLoginVip();//购买vip
                }
            }
        });

    }
}

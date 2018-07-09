package com.linkhand.fenxiao.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.DetailPageActivity;
import com.linkhand.fenxiao.adapter.mine.CollectionAdapter;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.CollectionInfo;

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

public class CollectionActivity extends BaseActicity implements View.OnClickListener, CollectionInfo {

    @Bind(R.id.collection_return)
    LinearLayout mReturn;//返回
    @Bind(R.id.collection_listview)
    ListView mListView;
    InfoData service;
    CollectionAdapter mAdapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
        onClicks();
        onMessage(0);//0正常查   1更新数据
    }

    public void initView() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);//返回
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collection_return://返回
                this.finish();
                break;
        }
    }


    public void onMessage(final int isPass) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("user_id", mUserId);
//        Call<CollectionFeng> call = service.getAllCollectionOrder(map);
//        call.enqueue(new Callback<CollectionFeng>() {
//            @Override
//            public void onResponse(Call<CollectionFeng> call, Response<CollectionFeng> response) {
//                CollectionFeng pcfeng = response.body();
//                Log.e("yh", pcfeng + "--");
//                int code = pcfeng.getCode();
//                String success = pcfeng.getSuccess();
//                if (code == 100) {
//                    List<CollectionFeng.InfoBean> beanList = pcfeng.getInfo();
//                    if (isPass == 0) {//0正常查   1更新数据
//                        mAdapter = new CollectionAdapter(CollectionActivity.this, beanList);
//                        mListView.setAdapter(mAdapter);
//                        mAdapter.setOnItemClicks(CollectionActivity.this);
//                    } else if (isPass == 1) {
//                        mAdapter.setData(beanList);
//                        mAdapter.notifyDataSetChanged();
//                    }
//
//
//                } else {
//                    mListView.setAdapter(null);
//                    Toast.makeText(CollectionActivity.this, success, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<CollectionFeng> call, Throwable t) {
//
//            }
//        });
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

    public void CancelCollection(String good_id) {//取消收藏
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("good_id", good_id);
        Call<ReturnFeng> call = service.getCancelCollection(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(CollectionActivity.this, success, Toast.LENGTH_SHORT).show();
                    onMessage(1);//0正常查   1更新数据
                } else {
                    Toast.makeText(CollectionActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemDetailsClicks(RelativeLayout mRelativeLayout, TextView mTextView, final List<Map<String, Object>> list) {
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String good_id = (String) list.get(0).get("good_id");//商品id
                Intent intent = new Intent(CollectionActivity.this, DetailPageActivity.class);
                intent.putExtra("good_id", good_id);
                startActivity(intent);
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {//取消收藏
            @Override
            public void onClick(View v) {
                String good_id = (String) list.get(0).get("good_id");//商品id
                CancelCollection(good_id);
            }
        });


    }
}

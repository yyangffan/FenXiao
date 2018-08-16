package com.linkhand.fenxiao.activity.homepage.home;

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

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.HomePageVipGoodsAdapter;
import com.linkhand.fenxiao.bean.MessageEvent;
import com.linkhand.fenxiao.feng.home.NewRecommendedVipGoods;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.HomeVipInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class AllVipGoodsActivity extends BaseActicity implements View.OnClickListener, HomeVipInfo {

    @Bind(R.id.open_group_return_id)
    LinearLayout mReturn;
    @Bind(R.id.open_group_lv_id)
    ListView mListview;
    @Bind(R.id.open_group_my)
    TextView mOpenGroupMy;
    InfoData service;
    String mUserId;//个人id
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    HomePageVipGoodsAdapter mVipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_vip_goods);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
        onVipMessage();
        EventBus.getDefault().register(this);
    }

    public void initView() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);

        mReturn.setOnClickListener(AllVipGoodsActivity.this);
        mOpenGroupMy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_group_return_id://返回
                this.finish();
                break;
            case R.id.open_group_my:
                startActivity(new Intent(AllVipGoodsActivity.this, MyVipActivity.class));
                break;
        }
    }

    public void onVipMessage() {//vip
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);//-------------------------------------------------
        Call<NewRecommendedVipGoods> call = service.getNewVipRecommended(map);
        call.enqueue(new Callback<NewRecommendedVipGoods>() {
            @Override
            public void onResponse(Call<NewRecommendedVipGoods> call, Response<NewRecommendedVipGoods> response) {
                NewRecommendedVipGoods pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
//                    Toast.makeText(HomePageFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                    List<NewRecommendedVipGoods.InfoBean> mBean = pcfeng.getInfo();
                    mVipAdapter = new HomePageVipGoodsAdapter(AllVipGoodsActivity.this, mBean);
                    mListview.setAdapter(mVipAdapter);
                    mVipAdapter.setOnAllVipItemClicks(AllVipGoodsActivity.this);


                } else {
//                    Toast.makeText(HomePageFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewRecommendedVipGoods> call, Throwable t) {

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

    @Override
    public void onVipItemClicks(RelativeLayout mRelativeLayout, final List<Map<String, Object>> list) {
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vip_id = list.get(0).get("vip_id").toString();//商品id
//                    Intent intent = new Intent(HomePageFragment.this.getActivity(), UpgradeVIPActivity.class);
//                    startActivity(intent);
                Intent intent = new Intent(AllVipGoodsActivity.this, VIPDetailPageActivity.class);
                intent.putExtra("vip_id", vip_id);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleMessage(MessageEvent msg) {
        Bundle bundle = new Bundle();
        switch (msg.getMessage()) {
            case "finish":
                AllVipGoodsActivity.this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

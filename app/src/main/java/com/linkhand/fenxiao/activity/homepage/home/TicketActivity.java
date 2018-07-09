package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.OpenGroupAdapter;
import com.linkhand.fenxiao.feng.home.GroupListFeng;
import com.linkhand.fenxiao.info.callback.HomeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/********************************************************************
 @version: 1.0.0
 @description: 返券专区--Adapter可以使用主页HomePageFragment的推荐商品中的HomePageFragmentAdapter
 @author: user
 @time: 2018/4/21 15:58
 @变更历史:
 ********************************************************************/
public class TicketActivity extends BaseActicity implements HomeInfo {

    @Bind(R.id.open_group_return_id)
    LinearLayout mReturn;
    @Bind(R.id.open_group_lv_id)
    ListView mListView;
    @Bind(R.id.activity_ticket)
    LinearLayout mTicket;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    OpenGroupAdapter mAdapter;
    private List<GroupListFeng.InfoBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        ButterKnife.bind(this);
        initView();
        onMessage();
    }

    public void initView() {
        list = new ArrayList<>();
        mAdapter = new OpenGroupAdapter(TicketActivity.this, list);
        mListView.setAdapter(mAdapter);
        mAdapter.setHomeInfo(TicketActivity.this);
        preferences = TicketActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
    }

    @OnClick({R.id.open_group_return_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_group_return_id:
                this.finish();
                break;
        }
    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("is_fan", "1");
        Call<GroupListFeng> call = service.getGroupList(map);
        call.enqueue(new Callback<GroupListFeng>() {
            @Override
            public void onResponse(Call<GroupListFeng> call, Response<GroupListFeng> response) {
                GroupListFeng pcfeng = response.body();
                int code = pcfeng.getCode();
                if (code == 100) {
                    list.clear();
                    List<GroupListFeng.InfoBean> beanList = pcfeng.getInfo();
                    list.addAll(beanList);
                    mAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(TicketActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GroupListFeng> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemClicks(RelativeLayout mRelativeLayout, final List<Map<String, Object>> list) {
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = (String) list.get(0).get("id");//(商品id)
                Intent intent = new Intent(TicketActivity.this, DetailPageActivity.class);
                intent.putExtra("good_id", id);
                startActivity(intent);
            }
        });
    }
}

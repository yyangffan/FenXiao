package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.MienAdapter;
import com.linkhand.fenxiao.bean.MienBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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

public class MienActivity extends BaseActicity {

    @Bind(R.id.open_group_return_id)
    LinearLayout mReturn;
    @Bind(R.id.open_group_lv_id)
    ListView mListView;
    @Bind(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    MienAdapter mAdapter;
    List<MienBean.InfoBean> mList;//测试数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mien);
        ButterKnife.bind(this);
        initView();
        onMessage();
    }

    public void initView() {
        mSmartRefresh.setEnableRefresh(false);
        mSmartRefresh.setEnableLoadmore(false);
        mList = new ArrayList<>();
        mAdapter = new MienAdapter(MienActivity.this, mList);
        mListView.setAdapter(mAdapter);

        preferences = MienActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ar_id = mList.get(position).getAr_id();
                Intent intent = new Intent(MienActivity.this, MienDetailsAcyivity.class);//详情页
                intent.putExtra("ar_id", ar_id);
                startActivity(intent);
            }
        });

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
        map.put("user_id", mUserId);
        Call<MienBean> call = service.getMienMsg(map);
        call.enqueue(new Callback<MienBean>() {
            @Override
            public void onResponse(Call<MienBean> call, Response<MienBean> response) {
                MienBean mienBean = response.body();
                if (mienBean.getCode() == 100) {
                    mList.clear();
                    mList.addAll(mienBean.getInfo());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MienActivity.this, mienBean.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MienBean> call, Throwable t) {
                Toast.makeText(MienActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onMessage();
    }
}

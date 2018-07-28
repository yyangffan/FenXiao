package com.linkhand.fenxiao.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.GongGRecyAdapter;
import com.linkhand.fenxiao.feng.home.MineGongGBean;
import com.linkhand.fenxiao.utils.ToastUtil;
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

public class HomeGongGActivity extends BaseActicity {
    @Bind(R.id.gongg_return_id4)
    LinearLayout mGonggReturnId4;
    @Bind(R.id.gongg_recy)
    RecyclerView mGonggRecy;
    @Bind(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;

    private List<MineGongGBean.InfoBean> mInfoBeanList;
    private GongGRecyAdapter mGongGRecyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_gong_g);
        ButterKnife.bind(this);
        initEver();
    }

    @OnClick({R.id.gongg_return_id4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gongg_return_id4:
                this.finish();
                break;
        }
    }

    public void initEver() {
        mSmartRefresh.setEnableRefresh(false);
        mSmartRefresh.setEnableLoadmore(false);
        mInfoBeanList = new ArrayList<>();
        mGongGRecyAdapter = new GongGRecyAdapter(this, mInfoBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mGonggRecy.setLayoutManager(linearLayoutManager);
        mGonggRecy.setAdapter(mGongGRecyAdapter);

        onMessage();
    }

    /*获取全部公告数据*/
    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("lisst_type", "2");
        Call<MineGongGBean> call = service.getGetNotice(map);
        call.enqueue(new Callback<MineGongGBean>() {
            @Override
            public void onResponse(Call<MineGongGBean> call, Response<MineGongGBean> response) {
                MineGongGBean mineGongGBean = response.body();
                if (mineGongGBean.getCode() == 100) {
                    mInfoBeanList.clear();
                    mInfoBeanList.addAll(mineGongGBean.getInfo());
                    mGongGRecyAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showToast(HomeGongGActivity.this, mineGongGBean.getSuccess());
                }
            }

            @Override
            public void onFailure(Call<MineGongGBean> call, Throwable t) {
                ToastUtil.showToast(HomeGongGActivity.this, "网络异常");
            }
        });


    }

}

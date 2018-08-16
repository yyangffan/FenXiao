package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.KaQuanRecyAdapter;
import com.linkhand.fenxiao.feng.home.UsedKqBean;
import com.linkhand.fenxiao.feng.home.VipLvResponse;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

public class VipSearchActivity extends BaseActicity{

    @Bind(R.id.my_vip_recy)
    RecyclerView mMyVipRecy;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @Bind(R.id.myvip_back)
    LinearLayout mMyvipBack;

    private KaQuanRecyAdapter mKaQuanRecyAdapter;
    private List<VipLvResponse.UsedataBean> mUsedataBeenList;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_search);
        ButterKnife.bind(this);
        initEver();
    }

    public void initEver() {
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMyVipRecy.setLayoutManager(linearManager);

        mUsedataBeenList = new ArrayList<>();
        mKaQuanRecyAdapter = new KaQuanRecyAdapter(this, mUsedataBeenList);
        mMyVipRecy.setAdapter(mKaQuanRecyAdapter);
        mKaQuanRecyAdapter.setItemClickListener(new KaQuanRecyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                VipLvResponse.UsedataBean usedataBean = mUsedataBeenList.get(position);
                Intent intent = new Intent(VipSearchActivity.this, KaQuanDetailActivity.class);
                intent.putExtra("uc_id", usedataBean.getUc_id());
                startActivity(intent);
            }
        });
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                getData();
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++page;
                getData();
            }
        });

    }

    @OnClick({R.id.myvip_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myvip_back:
                this.finish();
                break;
        }
    }

    /*获取已使用卡券数据*/
    public void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("pag", page);
        Call<UsedKqBean> call = service.getUseOver(map);
        call.enqueue(new Callback<UsedKqBean>() {
            @Override
            public void onResponse(Call<UsedKqBean> call, Response<UsedKqBean> response) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                UsedKqBean bean = response.body();
                if (bean.getCode() == 100) {
                    if (page == 0) {
                        mUsedataBeenList.clear();
                    }
                    for(UsedKqBean.InfoBean useInfo:bean.getInfo()){
                        VipLvResponse.UsedataBean usedataBean=new VipLvResponse.UsedataBean(useInfo.getUc_id(),useInfo.getUse_name()
                                ,useInfo.getUse_money(),useInfo.getNum_state(),useInfo.getCity_str(),useInfo.getUse_img());
                        mUsedataBeenList.add(usedataBean);
                    }
                    mKaQuanRecyAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showToast(VipSearchActivity.this, bean.getSuccess());
                }
            }

            @Override
            public void onFailure(Call<UsedKqBean> call, Throwable t) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                ToastUtil.showToast(VipSearchActivity.this, "网络异常");
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }
}

package com.linkhand.fenxiao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.CurrTradeAdapter;
import com.linkhand.fenxiao.feng.home.CurrTrade;
import com.linkhand.fenxiao.feng.home.MineJlBean;
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

/********************************************************************
 @version: 1.0.0
 @description: 这个界面被复用 我的记录、交易记录、历史记录（充值/提现记录）
 @author: 通过intent中携带的Bundle中的what来区分  1-我的记录 2-交易记录  3-充值/交易记录
 @time: 2018/6/20 9:17
 @变更历史:
 ********************************************************************/

public class MineJlActivity extends BaseActicity {

    @Bind(R.id.mine_jl_back)
    LinearLayout mMineJlBack;
    @Bind(R.id.tabLayout_id)
    TabLayout mTabLayoutId;
    @Bind(R.id.mine_jl_recy)
    RecyclerView mMineJlRecy;
    @Bind(R.id.mine_title)
    TextView mMineTitle;
    @Bind(R.id.mine_smart)
    SmartRefreshLayout mMineSmart;

    private List<CurrTrade.InfoBean> mlist_bean;
    private CurrTradeAdapter mAdapter;
    private String mWhat = "1";
    private String mSon;
    private String mTitle;
    private int page = 0;
    private String pos = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_jl);
        ButterKnife.bind(this);
        init();

    }


    public void init() {

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mWhat = extras.getString("what");
            mSon = extras.getString("son");
            mTitle = extras.getString("title");
        }
        if (mWhat.equals("1")) {
            mTabLayoutId.setVisibility(View.VISIBLE);
            mMineTitle.setText("我的记录");
            onMessage("0");
            pos="0";
            mTabLayoutId.addTab(mTabLayoutId.newTab().setText("全部"));
            mTabLayoutId.addTab(mTabLayoutId.newTab().setText("我的发布"));
            mTabLayoutId.addTab(mTabLayoutId.newTab().setText("我的出售"));
            mTabLayoutId.addTab(mTabLayoutId.newTab().setText("我的兑换"));
            mTabLayoutId.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    page=0;
                    int position = tab.getPosition();
                    pos = position + "";
                    onMessage(position + "");
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        } else if (mWhat.equals("2")) {
            mMineTitle.setText(mTitle);
            getJiaoYiJl(mSon, pos);
//            if (!mSon.equals("3")) {
            mTabLayoutId.addTab(mTabLayoutId.newTab().setText("收入"));
            mTabLayoutId.addTab(mTabLayoutId.newTab().setText("减少"));
            mTabLayoutId.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    page=0;
                    int position = tab.getPosition();
                    if (position == 0) {
                        getJiaoYiJl(mSon, "1");
                        pos = "1";
                    } else {
                        getJiaoYiJl(mSon, "2");
                        pos = "2";
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
//            }else {
//                mTabLayoutId.setVisibility(View.GONE);
//            }
        } else if (mWhat.equals("3")) {
            mTabLayoutId.setVisibility(View.GONE);
            mMineTitle.setText("历史记录");
            getChongTiJl();
        }

        mlist_bean = new ArrayList<>();
        mAdapter = new CurrTradeAdapter(this, mlist_bean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMineJlRecy.setLayoutManager(linearLayoutManager);
        mMineJlRecy.setAdapter(mAdapter);

//        mMineSmart.setEnableRefresh(false);
//        mMineSmart.setEnableLoadmore(false);
        mMineSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                if (mWhat.equals("1")) {/*我的记录购(物券兑换那)*/
                    onMessage(pos);
                } else if (mWhat.equals("2")) {/*个人中心那*/
                    getJiaoYiJl(mSon, pos);
                } else if (mWhat.equals("3")) {/*历史记录(充值/提现记录那)*/
                    getChongTiJl();
                }

            }
        });
        mMineSmart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++page;
                if (mWhat.equals("1")) {
                    onMessage(pos);
                } else if (mWhat.equals("2")) {
                    getJiaoYiJl(mSon, pos);
                } else if (mWhat.equals("3")) {
                    getChongTiJl();
                }
            }
        });


    }

    @OnClick({R.id.mine_jl_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_jl_back:
                this.finish();
                break;
        }
    }

    /*我的记录数据*/
    public void onMessage(String what) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("type", what);//0:全部 1:我的发布 2:我的出售 3:我的兑换
        map.put("pag", page);
        Call<CurrTrade> call = service.getCurrTrade(map);
        call.enqueue(new Callback<CurrTrade>() {
            @Override
            public void onResponse(Call<CurrTrade> call, Response<CurrTrade> response) {
                mMineSmart.finishRefresh();
                mMineSmart.finishLoadmore();
                CurrTrade curr = response.body();
                if (curr.getCode() == 100) {
                    if(page==0) {
                        mlist_bean.clear();
                        mlist_bean.addAll(curr.getInfo());
                    }else {
                        for(CurrTrade.InfoBean infoBean:curr.getInfo()){
                            mlist_bean.add(infoBean);
                        }
                    }
                } else {
                    if(page==0) {
                        mlist_bean.clear();
                    }
                    ToastUtil.showToast(MineJlActivity.this, curr.getSuccess());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CurrTrade> call, Throwable t) {
                mMineSmart.finishRefresh();
                mMineSmart.finishLoadmore();
                ToastUtil.showToast(MineJlActivity.this, "网络异常");
            }
        });
    }

    /*交易记录*/
    public void getJiaoYiJl(String son, String sj) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("list_type", son);
        map.put("is_add", sj);
        map.put("pag", page);

        Call<MineJlBean> call = service.getFinanceTrade(map);
        call.enqueue(new Callback<MineJlBean>() {
            @Override
            public void onResponse(Call<MineJlBean> call, Response<MineJlBean> response) {
                mMineSmart.finishRefresh();
                mMineSmart.finishLoadmore();
                MineJlBean curr = response.body();
                if (curr.getCode() == 100) {
                    if (page == 0) {
                        mlist_bean.clear();
                    }
                    for (MineJlBean.InfoBean infoBean : curr.getInfo()) {
                        CurrTrade.InfoBean curr_bean = new CurrTrade.InfoBean(infoBean.getTrade_id(), "没有",
                                infoBean.getMoney(), infoBean.getUnit(), infoBean.getTrade_time(), infoBean.getType_str());
                        mlist_bean.add(curr_bean);
                    }
                } else {
                    if(page==0) {
                        mlist_bean.clear();
                    }
                    ToastUtil.showToast(MineJlActivity.this, curr.getSuccess());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MineJlBean> call, Throwable t) {
                mMineSmart.finishRefresh();
                mMineSmart.finishLoadmore();
                ToastUtil.showToast(MineJlActivity.this, "网络异常");
            }
        });
    }

    /*充值/提现记录*/
    public void getChongTiJl() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("pag", page);
        Call<CurrTrade> call = service.getRechargeTrade(map);
        call.enqueue(new Callback<CurrTrade>() {
            @Override
            public void onResponse(Call<CurrTrade> call, Response<CurrTrade> response) {
                mMineSmart.finishRefresh();
                mMineSmart.finishLoadmore();
                CurrTrade curr = response.body();
                if (curr.getCode() == 100) {
                    mlist_bean.clear();
                    mlist_bean.addAll(curr.getInfo());
                } else {
                    if(page==0) {
                        mlist_bean.clear();
                    }
                    ToastUtil.showToast(MineJlActivity.this, curr.getSuccess());
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<CurrTrade> call, Throwable t) {
                mMineSmart.finishRefresh();
                mMineSmart.finishLoadmore();
                ToastUtil.showToast(MineJlActivity.this, "网络异常");
            }
        });
    }


}

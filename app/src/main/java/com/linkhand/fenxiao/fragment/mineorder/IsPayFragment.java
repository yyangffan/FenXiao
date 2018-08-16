package com.linkhand.fenxiao.fragment.mineorder;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.mine.All0rderFragmentAdapter;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.home.DingDanResponse;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.AllOrderInfo;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 待付款 订单
 * A simple {@link Fragment} subclass.
 */
public class IsPayFragment extends BaseFragment implements View.OnClickListener, AllOrderInfo {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    All0rderFragmentAdapter mAdapter;
    ListView mListView;
    ImageView mReturn;//返回
    LinearLayout mLinearLayout;//标题
    LinearLayout mll;
    InfoData service;
    private List<DingDanResponse.InfoBean> mListBean;
    SmartRefreshLayout mSmartRefreshLayout;
    private int page = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_is_pay, container, false);
        init(v);
        onClicks();
        initRetrofit();
        onMessage();
        return v;
    }


    public void init(View v) {
        mListBean = new ArrayList<>();
        mListView = (ListView) v.findViewById(R.id.isPay_listview_id);
        mLinearLayout = (LinearLayout) v.findViewById(R.id.mine_llayout_gone_id1);
        mReturn = (ImageView) v.findViewById(R.id.mine_return_id2);
        mll = (LinearLayout) v.findViewById(R.id.ispay_ll);
        mSmartRefreshLayout = (SmartRefreshLayout) v.findViewById(R.id.smartRefresh);
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("keyDown", "1");  //存入返回判断  1不提示
        editor.commit();
        mUserId = preferences.getString("user_id", "");
        int mIsOk = preferences.getInt("isTitles", 0);  //获取订单标题是否隐藏  0不隐藏（单个订单）  1隐藏（全部订单）
        mAdapter = new All0rderFragmentAdapter(IsPayFragment.this.getActivity(), mListBean);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnGroupItemClicks(IsPayFragment.this);
        if (mIsOk == 1) {
            mLinearLayout.setVisibility(View.GONE);
        }
        mll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                onMessage();
            }
        });
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++page;
                onMessage();
            }
        });
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_return_id2://返回
                IsPayFragment.this.getActivity().onBackPressed();
                break;
        }
    }

    /*删除订单*/
    public void userDelete(String or_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", or_id);
        Call<HttpResponse> call = service.getDelOrder(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    page=0;
                    onMessage();
                }
                ToastUtil.showToast(IsPayFragment.this.getActivity(), httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(IsPayFragment.this.getActivity(), "网络异常");
            }
        });
    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("order_state", "1");
        map.put("pag", page);
        Call<DingDanResponse> call = service.getDingdanList(map);
        call.enqueue(new Callback<DingDanResponse>() {
            @Override
            public void onResponse(Call<DingDanResponse> call, Response<DingDanResponse> response) {
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadmore();
                DingDanResponse feng = response.body();
                int code = feng.getCode();
                if (code == 100) {
                    if (page == 0) {
                        mListBean.clear();
                        mListBean.addAll(feng.getInfo());
                    } else {
                        for (DingDanResponse.InfoBean infoBean : feng.getInfo()) {
                            mListBean.add(infoBean);
                        }
                    }
                } else {
                    if (page == 0) {
                        mListBean.clear();
                    }
                    Toast.makeText(IsPayFragment.this.getActivity(), feng.getSuccess(), Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DingDanResponse> call, Throwable t) {
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadmore();
                ToastUtil.showToast(IsPayFragment.this.getActivity(), "网络异常");
            }
        });

    }


    public void onReturnGood(String order_id) {//取消商品订单
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("order_id", order_id);//订单id
        Call<ReturnFeng> call = service.getReturnGoods(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng feng = response.body();
                Log.e("yh", feng + "");
                int code = feng.getCode();
                String success = feng.getSuccess();
                if (code == 100) {
                    Toast.makeText(IsPayFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                    page=0;
                    onMessage();
                } else {
                    Toast.makeText(IsPayFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClicks(LinearLayout mLinearLayout, TextView mTextView, final List<Map<String, Object>> list) {
//        mLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                list.get(0).get("good_state");//订单状态  0.未付款 1.拼团中 2.待发货 3.待收货 4.待评价 5.团购取消
//                String good_id= (String) list.get(0).get("good_id");//商品id
//                String order_id= (String) list.get(0).get("order_id");//订单id
//                Intent intent = new Intent(IsPayFragment.this.getActivity(), DetailPageActivity.class);
//                intent.putExtra("good_id", good_id);
//                startActivity(intent);
//            }
//        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消

                new ShowRemindDialog().showRemind(IsPayFragment.this.getActivity(), "确定", "取消", "提示", "确认取消该拼团?\n退货:不退购物券", R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
                    @Override
                    public void OnSureClickListener() {
//                list.get(0).get("good_state");//订单状态  0.未付款 1.拼团中 2.待发货 3.待收货 4.待评价 5.团购取消
                        String good_id = (String) list.get(0).get("good_id");//商品id
                        String order_id = (String) list.get(0).get("order_id");//订单id
                        onReturnGood(order_id); //取消商品订单
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    IsPayFragment.this.getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        page=0;
    }

}
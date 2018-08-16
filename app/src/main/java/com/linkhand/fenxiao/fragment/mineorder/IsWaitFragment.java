package com.linkhand.fenxiao.fragment.mineorder;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.ConfirmOrderActivity;
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
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 待付款界面
 */
public class IsWaitFragment extends Fragment implements AllOrderInfo {
    @Bind(R.id.mine_return_id2)
    ImageView mMineReturnId2;
    @Bind(R.id.textView2)
    TextView mTextView2;
    @Bind(R.id.mine_llayout_gone_id1)
    LinearLayout mMineLlayoutGoneId1;
    @Bind(R.id.isPay_listview_id)
    SlideAndDragListView mListView;
    @Bind(R.id.iswait_ll)
    LinearLayout mIswaitLl;

    InfoData service;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    All0rderFragmentAdapter mAdapter;
    List<DingDanResponse.InfoBean> mlistBean;
    @Bind(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;
    public int page = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_is_wait, container, false);
        ButterKnife.bind(this, view);
        initRetrofit();
        initWhat();
        return view;
    }

    public void initWhat() {
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //存入返回判断  1不提示
        editor.putString("keyDown", "1");
        editor.commit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        int mIsOk = preferences.getInt("isTitles", 0);
        if (mIsOk == 1) {
            mMineLlayoutGoneId1.setVisibility(View.GONE);
        }
        mlistBean = new ArrayList<>();
        Menu menu = new Menu(false, 0);//第1个参数表示滑动 item 是否能滑的过头 true 表示过头，false 表示不过头
        menu.addItem(new MenuItem.Builder().setWidth(200)
                .setBackground(new ColorDrawable(Color.RED))
                .setText("删除")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize(20)
                .build());
        mListView.setMenu(menu);
        mListView.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
            @Override
            public int onMenuItemClick(View v, final int itemPosition, int buttonPosition, int direction) {
                switch (direction) {
                    case MenuItem.DIRECTION_RIGHT:
                        userDelete(mlistBean.get(itemPosition).getOrder_id());
                        break;
                    default:
                        return Menu.ITEM_NOTHING;
                }
                return Menu.ITEM_NOTHING;
            }
        });
        mAdapter = new All0rderFragmentAdapter(IsWaitFragment.this.getActivity(), mlistBean);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnIsWait(IsWaitFragment.this);
        onMessage();
        mIswaitLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(IsWaitFragment.this.getActivity(), ConfirmOrderActivity.class);
                intent.putExtra("danhao", mlistBean.get(position).getOrder_id());
                startActivity(intent);
            }
        });
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                onMessage();
            }
        });
        mSmartRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++page;
                onMessage();
            }
        });

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
                ToastUtil.showToast(IsWaitFragment.this.getActivity(), httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(IsWaitFragment.this.getActivity(), "网络异常");
            }
        });
    }

    @OnClick({R.id.mine_return_id2, R.id.textView2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_return_id2:
                IsWaitFragment.this.getActivity().onBackPressed();
                break;
        }
    }


    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("order_state", "0");
        map.put("pag",page);
        Call<DingDanResponse> call = service.getDingdanList(map);
        call.enqueue(new Callback<DingDanResponse>() {
            @Override
            public void onResponse(Call<DingDanResponse> call, Response<DingDanResponse> response) {
                mSmartRefresh.finishRefresh();
                mSmartRefresh.finishLoadmore();
                DingDanResponse feng = response.body();
                int code = feng.getCode();
                String success = feng.getSuccess();
                if (code == 100) {
                    if (page == 0) {
                        mlistBean.clear();
                        mlistBean.addAll(feng.getInfo());
                    }else {
                        for(DingDanResponse.InfoBean infoBean:feng.getInfo()){
                            mlistBean.add(infoBean);
                        }
                    }
                } else {
                    if(page==0) {
                        mlistBean.clear();
                    }
                    Toast.makeText(IsWaitFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DingDanResponse> call, Throwable t) {
                mSmartRefresh.finishRefresh();
                mSmartRefresh.finishLoadmore();
                ToastUtil.showToast(IsWaitFragment.this.getActivity(), "网络异常");
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
                    Toast.makeText(IsWaitFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                    page=0;
                    onMessage();
                } else {
                    Toast.makeText(IsWaitFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClicks(LinearLayout mLinearLayout, TextView mTextView, final List<Map<String, Object>> list) {
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消
                new ShowRemindDialog().showRemind(IsWaitFragment.this.getActivity(), "确定", "取消", "提示", "确认取消该订单?", R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
                    @Override
                    public void OnSureClickListener() {
                        //                list.get(0).get("good_state");//订单状态  0.未付款 1.拼团中 2.待发货 3.待收货 4.待评价 5.团购取消
                        String order_id = (String) list.get(0).get("order_id");//订单id
                        onReturnGood(order_id);
                    }
                });
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        page=0;
    }


}

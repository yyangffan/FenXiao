package com.linkhand.fenxiao.fragment.mineorder;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.mine.EvaluationActivity;
import com.linkhand.fenxiao.adapter.mine.All0rderFragmentAdapter;
import com.linkhand.fenxiao.feng.home.DingDanResponse;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.AllOrderInfo;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

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
 * 待评价 订单
 * A simple {@link Fragment} subclass.
 */
public class IsAppraiseFragment extends BaseFragment implements View.OnClickListener, AllOrderInfo {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    All0rderFragmentAdapter mAdapter;
    SlideAndDragListView mListView;
    ImageView mReturn;//返回
    LinearLayout mLinearLayout;//标题
    LinearLayout mll;
    InfoData service;
    private List<DingDanResponse.InfoBean> mListBean;
    private AlertDialog mTh_dialog;
    private String order_id = "";//进行退货时的id
    SmartRefreshLayout mSmartRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_is_appraise, container, false);
        initView(v);
        onClicks();
        initRetrofit();
        onMessage();
        configReasonDialog();
        return v;
    }

    public void initView(View v) {
        mListBean = new ArrayList<>();
        mAdapter = new All0rderFragmentAdapter(IsAppraiseFragment.this.getActivity(), mListBean);
        mAdapter.setOnEvaluationItemClicks(IsAppraiseFragment.this);
        mListView = (SlideAndDragListView) v.findViewById(R.id.isAppraise_listview_id);
        mReturn = (ImageView) v.findViewById(R.id.mine_return_id5);
        mLinearLayout = (LinearLayout) v.findViewById(R.id.mine_llayout_gone_id4);
        mll = (LinearLayout) v.findViewById(R.id.appraise_ll);
        mSmartRefreshLayout = (SmartRefreshLayout) v.findViewById(R.id.smartRefresh);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //存入返回判断  1不提示
        editor.putString("keyDown", "1");
        editor.commit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);
        //获取订单标题是否隐藏  0不隐藏（单个订单）  1隐藏（全部订单）
        int mIsOk = preferences.getInt("isTitles", 0);
        Log.e("yh", "mIsOk--" + mIsOk);
        if (mIsOk == 1) {
            mLinearLayout.setVisibility(View.GONE);
        }
        mll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
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
                        userDelete(mListBean.get(itemPosition).getOrder_id());
                        break;
                    default:
                        return Menu.ITEM_NOTHING;
                }
                return Menu.ITEM_NOTHING;
            }
        });
        mListView.setAdapter(mAdapter);
        mAdapter.setOnTuiHListener(new All0rderFragmentAdapter.OnTuiHListener() {
            @Override
            public void OnTuiHListener(int position) {
                mTh_dialog.show();
                order_id = mListBean.get(position).getOrder_id();
            }
        });
        mSmartRefreshLayout.setEnableLoadmore(false);
        mSmartRefreshLayout.setEnableRefresh(false);
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
                    onMessage();
                }
                ToastUtil.showToast(IsAppraiseFragment.this.getActivity(), httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(IsAppraiseFragment.this.getActivity(), "网络异常");
            }
        });
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_return_id5://返回
                IsAppraiseFragment.this.getActivity().onBackPressed();
                break;
        }
    }

    public void configReasonDialog() {
        mTh_dialog = new AlertDialog.Builder(this.getActivity()).create();
        View v = LayoutInflater.from(this.getActivity()).inflate(R.layout.dialog_liuyan, null);

        TextView mtv_title = (TextView) v.findViewById(R.id.dialog_liuyan_title);
        final EditText medt = (EditText) v.findViewById(R.id.dialog_liuyan_edt);
        TextView mtv_cancel = (TextView) v.findViewById(R.id.dialog_liuyan_cancel);
        TextView mtv_sure = (TextView) v.findViewById(R.id.dialog_liuyan_commit);
        ImageView mimgv = (ImageView) v.findViewById(R.id.dialog_liuyan_imgv);
        mTh_dialog.setView(v);
        mTh_dialog.setCanceledOnTouchOutside(false);
        mtv_title.setText("退货");
        medt.setHint("请填写退货理由...");

        mimgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTh_dialog.dismiss();
            }
        });
        mtv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTh_dialog.dismiss();
            }
        });
        mtv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = medt.getText().toString();
                if (reason != null && reason.length() != 0) {
                    mTh_dialog.dismiss();
                    toTuiH(reason);
                } else {
                    ToastUtil.showToast(IsAppraiseFragment.this.getActivity(), "请填写退货理由");
                }

            }
        });
    }

    /*进行退货*/
    public void toTuiH(String reason) {
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", order_id);
        map.put("user_id", mUserId);
        map.put("quit_text", reason);
        Call<HttpResponse> call = service.getQuitOrder(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    onMessage();
                }
                ToastUtil.showToast(IsAppraiseFragment.this.getActivity(), httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(IsAppraiseFragment.this.getActivity(), "网络异常");
            }
        });
    }


    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("order_state", "4");

        Call<DingDanResponse> call = service.getDingdanList(map);
        call.enqueue(new Callback<DingDanResponse>() {
            @Override
            public void onResponse(Call<DingDanResponse> call, Response<DingDanResponse> response) {
                DingDanResponse feng = response.body();
                Log.e("yh", feng + "");
                int code = feng.getCode();
                String success = feng.getSuccess();
                if (code == 100) {
                    mListBean.clear();
                    mListBean.addAll(feng.getInfo());
                    mAdapter.notifyDataSetChanged();
                } else {
                    mListBean.clear();
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(IsAppraiseFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DingDanResponse> call, Throwable t) {
                Toast.makeText(IsAppraiseFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
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
//                list.get(0).get("good_state");//订单状态  0.未付款 1.拼团中 2.待发货 3.待收货 4.待评价 5.团购取消
//                String good_id = (String) list.get(0).get("good_id");//商品id
//                String order_id = (String) list.get(0).get("order_id");//订单id
//                Intent intent = new Intent(IsAppraiseFragment.this.getActivity(), DetailPageActivity.class);
//                intent.putExtra("good_id", good_id);
//                startActivity(intent);
//            }
//        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(0).get("good_state");//订单状态  0.未付款 1.拼团中 2.待发货 3.待收货 4.待评价 5.团购取消
                String good_id = (String) list.get(0).get("good_id");//商品id
                String title = (String) list.get(0).get("title");//商品名称
                String order_id = (String) list.get(0).get("order_id");//订单id
                onEvaluation(good_id, title, order_id);//评价(商品id,商品名称)
            }
        });
    }

    public void onEvaluation(String good_id, String title, String order_id) {//评价(商品id,商品名称)
        Intent intent = new Intent(IsAppraiseFragment.this.getActivity(), EvaluationActivity.class);
        intent.putExtra("good_id", good_id);
        intent.putExtra("order_id", order_id);
        intent.putExtra("title", title);
        startActivity(intent);
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
                    IsAppraiseFragment.this.getActivity().onBackPressed();

                    return true;
                }
                return false;
            }
        });
    }

}

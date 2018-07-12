package com.linkhand.fenxiao.fragment.mineorder;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.ConfirmOrderActivity;
import com.linkhand.fenxiao.activity.mine.EvaluationActivity;
import com.linkhand.fenxiao.adapter.mine.All0rderFragmentAdapter;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.ReturnFeng;
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
 * 全部订单
 * A simple {@link Fragment} subclass.
 */
public class All0rderFragment extends BaseFragment implements AllOrderInfo {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    All0rderFragmentAdapter mAdapter;
    SlideAndDragListView mListView;
    InfoData service;
    private List<DingDanResponse.InfoBean> mListBean;
    private AlertDialog mTh_dialog;
    private String order_id = "";//进行退货时的id
    SmartRefreshLayout mSmartRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all0rder, container, false);
        init(v);
        initRetrofit();//初始化Retrofit
        onMessage();//获取内容
        configReasonDialog();
        return v;
    }

    public void init(View v) {
        mListBean = new ArrayList<>();
        mAdapter = new All0rderFragmentAdapter(All0rderFragment.this.getActivity(), mListBean);
        mAdapter.setOnItemClicks(this);
        mListView = (SlideAndDragListView) v.findViewById(R.id.allorder_listview_id);
        mSmartRefreshLayout = (SmartRefreshLayout) v.findViewById(R.id.smartRefresh);
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String order_state = mListBean.get(position).getOrder_state();
                if (order_state.equals("0")) {//如果是待付款需要跳转到付款界面
                    Intent intent = new Intent(All0rderFragment.this.getActivity(), ConfirmOrderActivity.class);
                    intent.putExtra("danhao", mListBean.get(position).getOrder_id());
                    startActivity(intent);
                }
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
                        String order_state = mListBean.get(itemPosition).getOrder_state();
                        if (order_state.equals("4")) {
                            userDelete(mListBean.get(itemPosition).getOrder_id());
                        } else {
                            ToastUtil.showToast(All0rderFragment.this.getActivity(), "只可删除交易完成的订单");
                        }
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
                order_id = mListBean.get(position).getOrder_id();
                mTh_dialog.show();
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
                ToastUtil.showToast(All0rderFragment.this.getActivity(), httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(All0rderFragment.this.getActivity(), "网络异常");
            }
        });
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
                    ToastUtil.showToast(All0rderFragment.this.getActivity(), "请填写退货理由");
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
                ToastUtil.showToast(All0rderFragment.this.getActivity(), httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(All0rderFragment.this.getActivity(), "网络异常");
            }
        });


    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("order_state", "-1");
        Call<DingDanResponse> call = service.getDingdanList(map);
        call.enqueue(new Callback<DingDanResponse>() {
            @Override
            public void onResponse(Call<DingDanResponse> call, Response<DingDanResponse> response) {
                DingDanResponse feng = response.body();
                int code = feng.getCode();
                if (code == 100) {
                    mListBean.clear();
                    mListBean.addAll(feng.getInfo());
                } else {
                    mListBean.clear();
                    Toast.makeText(All0rderFragment.this.getActivity(), feng.getSuccess(), Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DingDanResponse> call, Throwable t) {
                Toast.makeText(All0rderFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
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
//                String id = (String) list.get(0).get("good_id");//商品id
//                Intent intent = new Intent(All0rderFragment.this.getActivity(), DetailPageActivity.class);
//                intent.putExtra("good_id", id);
//                startActivity(intent);
//            }
//        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String good_state = (String) list.get(0).get("good_state");//订单状态  0.未付款 1.拼团中 2.待发货 3.待收货 4.待评价 5.团购取消
                String good_id = (String) list.get(0).get("good_id");//商品id
                String order_id = (String) list.get(0).get("order_id");//订单id
                String title = (String) list.get(0).get("title");//商品名称
                if (good_state.equals("0")) {
                    showWhatDialog(order_id, "确认取消该订单?", good_state);
                } else if (good_state.equals("1")) {
                    showWhatDialog(order_id, "确认取消该拼团?", good_state);
                } else if (good_state.equals("4")) {
                    Intent intent = new Intent(All0rderFragment.this.getActivity(), EvaluationActivity.class);
                    intent.putExtra("good_id", good_id);
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("title", title);
                    startActivity(intent);
                } else if (good_state.equals("3")) {
                    showWhatDialog(order_id, "确认收货?", good_state);
                }
            }
        });
    }

    public void showWhatDialog(final String order_id, String note, final String state) {

        new ShowRemindDialog().showRemind(All0rderFragment.this.getActivity(), "确定", "取消", "提示", note, R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
            @Override
            public void OnSureClickListener() {
                if (state.equals("0") || state.equals("1")) {
                    onReturnGood(order_id);
                } else if (state.equals("3")) {
                    onConfirmReceipt(order_id);//确认收货
                }
            }
        });

    }

    /*取消商品订单*/
    public void onReturnGood(String order_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("order_id", order_id);//订单id
        Call<ReturnFeng> call = service.getReturnGoods(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng feng = response.body();
                int code = feng.getCode();
                String success = feng.getSuccess();
                if (code == 100) {
                    onMessage();
                    Toast.makeText(All0rderFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(All0rderFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Toast.makeText(All0rderFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*确认收货*/
    public void onConfirmReceipt(String order_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("order_id", order_id);
        Call<ReturnFeng> call = service.getConfirmReceipt(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng feng = response.body();
                int code = feng.getCode();
                String success = feng.getSuccess();
                if (code == 100) {
                    Toast.makeText(All0rderFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                    onMessage();
                } else {
                    Toast.makeText(All0rderFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Toast.makeText(All0rderFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

package com.linkhand.fenxiao.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.currency.PostedActivity;
import com.linkhand.fenxiao.activity.login.LoginActivity;
import com.linkhand.fenxiao.activity.mine.SetPwdActivity;
import com.linkhand.fenxiao.adapter.currency.CurrencyAdapter;
import com.linkhand.fenxiao.dialog.MyDialogApprove;
import com.linkhand.fenxiao.dialog.MyDialogLashSum;
import com.linkhand.fenxiao.dialog.MyDialogPay;
import com.linkhand.fenxiao.dialog.MyDialogVip;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.zimu.AllCoinFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.ProvinceInfo;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.views.PasswordInputView;
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

import static java.lang.Integer.parseInt;

/**
 * 子母币兑换
 * A simple {@link Fragment} subclass.
 */
public class ExchangeFragment extends BaseFragment implements View.OnClickListener, ProvinceInfo {
    ListView mListView;
    TextView mPosted;//发布
    private TabLayout mTabLayout;
    private TextView mtv_left;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserIsVip;//是否vip  0否  1是
    String mUserId;//个人id
    CurrencyAdapter mAdapter;
    RefreshLayout refreshLayout;//上下拉
    InfoData service;
    EditText editText;//兑换母币数量
    double mdouble = 0;//支付子币数

    List<Map<String, Object>> mList;
    String Mater_name = "母币";//母币名称
    String Son_name = "子币";//子币名称
    private String isWhat = "2";
    public String mUserReal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exchange, container, false);
        init(v);
        initRetrofit();
        onUpDowns();
        onClicks();
        onMessage("2");

        return v;
    }

    public void init(View v) {
        mListView = (ListView) v.findViewById(R.id.currency_lv_id);
        mPosted = (TextView) v.findViewById(R.id.currency_posted_id);//发布
        refreshLayout = (RefreshLayout) v.findViewById(R.id.refreshLayout);//上下拉
        mTabLayout = (TabLayout) v.findViewById(R.id.tabLayout_id);
        mtv_left = (TextView) v.findViewById(R.id.exchange_mine_jilu);
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove("keyDown").commit();  //存入返回判断  1不提示
        mUserId = preferences.getString("user_id", "");
        Mater_name = preferences.getString("Mater_name", "母币");//母币名称
        Son_name = preferences.getString("Son_name", "子币");//子币名称
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
        mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
        mTabLayout.addTab(mTabLayout.newTab().setText("兑换列表"));
        mTabLayout.addTab(mTabLayout.newTab().setText("发布列表"));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        onMessage("2");
                        break;
                    case 1:
                        onMessage("1");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void onClicks() {
        mPosted.setOnClickListener(ExchangeFragment.this);//发布
        mtv_left.setOnClickListener(this);//我的记录
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.currency_posted_id://发布
                mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
                onRelease();//发布
                break;
            case R.id.exchange_mine_jilu://我的记录
                Bundle bundle = new Bundle();
                bundle.putString("what", "1");//标示
                Intent intent = new Intent(ExchangeFragment.this.getActivity(), MineJlActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    public void onRelease() {//发布
        if (mUserId.equals("")) {//是否登录
            onLogin();//去登陆
        } else {
            if (mUserIsVip.equals("0")) {//是否vip  0否  1是
                onPrompt();//提示信息
            } else {
                if (mUserReal.equals("0")) {//是否认证  0否  1是
                    onApprove();
                    return;
                }
                Intent intent = new Intent(ExchangeFragment.this.getActivity(), PostedActivity.class);
                startActivity(intent);
            }
        }
    }

    public void onUpDowns() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                ExchangeFragment.this.onRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ExchangeFragment.this.onRefresh();
            }
        });
    }

    /*字母币兑换列表*/
    public void onMessage(String what) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("my_type", what);//1 我的发布  2 其他人发布
        isWhat = what;
        if (service != null) {
            Call<AllCoinFeng> call = service.getAllCoin(map);
            call.enqueue(new Callback<AllCoinFeng>() {
                @Override
                public void onResponse(Call<AllCoinFeng> call, Response<AllCoinFeng> response) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadmore();
                    AllCoinFeng pcfeng = response.body();
                    int code = pcfeng.getCode();
                    if (code == 100) {
                        List<AllCoinFeng.InfoBean> beanList = pcfeng.getInfo();
                        mAdapter = new CurrencyAdapter(ExchangeFragment.this.getActivity(), beanList);
                        mListView.setAdapter(mAdapter);
                        mAdapter.setOnItemClicks(ExchangeFragment.this);
                    } else {
                        mAdapter = new CurrencyAdapter(ExchangeFragment.this.getActivity(), new ArrayList<AllCoinFeng.InfoBean>());
                        mListView.setAdapter(mAdapter);
                        mAdapter.setOnItemClicks(ExchangeFragment.this);
//                    Toast.makeText(ExchangeFragment.this.getActivity(), pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<AllCoinFeng> call, Throwable t) {
                    if (refreshLayout != null) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadmore();
                    }
                    if (ExchangeFragment.this != null && ExchangeFragment.this.getActivity() != null) {
                        Toast.makeText(ExchangeFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            initRetrofit();
        }
    }


    public void onDialogSum(final String currId, final String str, final String user_id) {//兑换（子母币id，子币单价,剩余数量,发布者id0为官方）
        final MyDialogLashSum dialog = new MyDialogLashSum(ExchangeFragment.this.getActivity());
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
        ImageView returns = (ImageView) dialog.findViewById(R.id.swop_retuen_id);//关闭
        editText = (EditText) dialog.findViewById(R.id.swop_et_id);//兑换母币数量
        final TextView sum = (TextView) dialog.findViewById(R.id.swop_sum_id);//需支付xx子币
        TextView confirm = (TextView) dialog.findViewById(R.id.swop_confirm_id);//确定
        editText.setHint("请输入兑换" + Mater_name + "数量");
        onGuangBiao();
        returns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(s.toString() + "").equals("")) {
                    int content = parseInt(s.toString());
                    mdouble = content * Double.parseDouble(str);
                    sum.setText("需支付" + mdouble + Son_name);
                } else {
                    sum.setText("需支付0.0" + Son_name);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText() + "";
                if (content.equals("")) {
                    Toast.makeText(ExchangeFragment.this.getActivity(), "请输入兑换数量", Toast.LENGTH_SHORT).show();
                } else {
//                    ExchangeFragment.this.onDialogPay();//兑换(购买支付)
                    dialog.dismiss();
//                    int mSurplusNum = (int) Double.parseDouble(surplusNum);
//                    int num = Integer.parseInt(content);
//                    if (mSurplusNum >= num) {
                    onDialogPay(mdouble, currId, content, user_id);//兑换(购买支付)     （金额,子母币id,兑换数量,发布者id0为官方）
//                    } else {
//                        Toast.makeText(ExchangeFragment.this.getActivity(), "剩余不足", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
    }

    /*购物券兑换的最后一步*/
    public void onDialogPay(double mdouble, final String currId, final String content, final String user_id) {//兑换(购买支付)（金额,子母币id,兑换数量,发布者id0为官方）
        final MyDialogPay dialog = new MyDialogPay(ExchangeFragment.this.getActivity());
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
        TextView pay = (TextView) dialog.findViewById(R.id.dialog_pay_id);//xx(子币数量)
        TextView cancel = (TextView) dialog.findViewById(R.id.dialog_cancel_id);//取消
        TextView confirm = (TextView) dialog.findViewById(R.id.dialog_confirm_id);//确定
        TextView ziName = (TextView) dialog.findViewById(R.id.zi_text_id);//子币名称
        ziName.setText(Son_name + "？");
        pay.setText(mdouble + "");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showPassDialog(new OnWhatsTheResult() {
                    @Override
                    public void OnWhatsTheResult(boolean go) {
                        if (go) {
                            if (user_id.equals("0")) {//发布者id    0为官方
                                onOfficialExchange(currId, content);//官方兑换 (子母币id,兑换数量)
                            } else {
                                onExchange(currId, content);//兑换 (子母币id,兑换数量)
                            }
                        }
                    }
                });
            }
        });
    }

    /*显示输入二级密码的弹窗*/
    public void showPassDialog(final OnWhatsTheResult listener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(ExchangeFragment.this.getActivity()).create();
        View v = LayoutInflater.from(ExchangeFragment.this.getActivity()).inflate(R.layout.dialog_pass, null);
        final PasswordInputView pass = (PasswordInputView) v.findViewById(R.id.again_paypswd_pet);
        alertDialog.setView(v);
        alertDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showKeyboard(pass);
            }
        },300);
//        alertDialog.setContentView(v);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    commitPass(pass.getText().toString(), listener);
                    alertDialog.dismiss();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    //弹出软键盘
    public void showKeyboard(EditText editText) {
        //其中editText为dialog中的输入框的 EditText
        if(editText!=null){
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) ExchangeFragment.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }
    /*验证二级密码是否正确*/
    public void commitPass(String pay_pwd, final OnWhatsTheResult listener) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("pay_pwd", pay_pwd);
        Call<HttpResponse> call = service.getPayPwd(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    listener.OnWhatsTheResult(true);
                } else if (httpResponse.getCode() == 201) {//未设置
                    listener.OnWhatsTheResult(false);
                    Toast.makeText(ExchangeFragment.this.getActivity(), httpResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ExchangeFragment.this.getActivity(), SetPwdActivity.class);
                    startActivity(intent);
                } else {
                    listener.OnWhatsTheResult(false);
                    Toast.makeText(ExchangeFragment.this.getActivity(), httpResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                listener.OnWhatsTheResult(false);
                Toast.makeText(ExchangeFragment.this.getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*验证二级密码的回调*/
    public interface OnWhatsTheResult {
        void OnWhatsTheResult(boolean go);

    }


    public void onExchange(String currId, String content) {//购买母币(子母币id,兑换数量)
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("curr_id", currId);//订单id
        map.put("buy_mater", content);//购买母币数量
        Call<ReturnFeng> call = service.getBuyMu(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    onMessage("2");
                }
                Toast.makeText(ExchangeFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    public void onOfficialExchange(String currId, String content) {//购买母币官方兑换(子母币id,兑换数量)
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("curr_id", currId);//订单id
        map.put("buy_mater", content);//购买母币数量
        Call<ReturnFeng> call = service.getOfficialBuyMu(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    onMessage("2");
                }
                Toast.makeText(ExchangeFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    public void onGuangBiao() {
        editText.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
        editText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    editText.setCursorVisible(true);// 再次点击显示光标
                }

                return false;
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

    public void onLogin() {//去登陆
        Intent intent = new Intent(ExchangeFragment.this.getActivity(), LoginActivity.class);//登录
        startActivity(intent);
    }

    public void onPrompt() {//提示信息购买vip
        MyDialogVip dialog = new MyDialogVip(getActivity());
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
//        Toast.makeText(ExchangeFragment.this.getActivity(), "请先购买vip!", Toast.LENGTH_SHORT).show();
    }
    public void onApprove() {//实名认证
        MyDialogApprove dialog = new MyDialogApprove(this.getActivity());
        dialog.show();
    }

    @Override
    public void onProvinceItemClicks(TextView mContent, final List<Map<String, Object>> list) {
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//兑换
                if (mUserId.equals("")) {//是否登录
                    onLogin();//去登陆
                } else {
                    if (mUserIsVip.equals("0")) {//是否vip  0否  1是
                        onPrompt();//提示信息
                    } else {
                        mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        final String curr_id = (String) list.get(0).get("curr_id");//子母币id
                        String curr_son_money = (String) list.get(0).get("curr_son_money");//子币单价
                        String curr_mater_num = (String) list.get(0).get("curr_mater_num");//剩余(发布)母币
                        String user_id = (String) list.get(0).get("user_id");//发布者id  0为官方
                        if (user_id.equals(mUserId)) {//撤销发布
                            new ShowRemindDialog().showRemind(ExchangeFragment.this.getActivity(), "确定", "取消", "提示", "取消发布？", R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
                                @Override
                                public void OnSureClickListener() {
                                    cancelFabu(curr_id);
                                }
                            });

                        } else {//进行兑换
                            onDialogSum(curr_id, curr_son_money, user_id);//兑换（子母币id，子币单价,剩余数量,发布者id0为官方）
                        }
                    }
                }


            }
        });
    }

    /*取消发布*/
    public void cancelFabu(String curr_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("curr_id", curr_id);
        Call<HttpResponse> call = service.getCurrCancel(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    onMessage("1");
                }
                ToastUtil.showToast(ExchangeFragment.this.getActivity(), httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(ExchangeFragment.this.getActivity(), "网络异常");
            }
        });
    }

    public void onRefresh() {
        onMessage(isWhat);
    }

    @Override
    public void onStart() {
        super.onStart();
        //是否vip  0否  1是
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
        Log.e("yh", "是否vip--" + mUserIsVip);
    }

}

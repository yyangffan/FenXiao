package com.linkhand.fenxiao.fragment.mine;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.MyApplication;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.PayResult;
import com.linkhand.fenxiao.dialog.MyPayWap;
import com.linkhand.fenxiao.feng.AllConfigFeng;
import com.linkhand.fenxiao.feng.home.VipPayResponse;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.Logger;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 充值
 */
public class RechargeFragment extends BaseFragment implements View.OnClickListener {
    EditText mRecharge;//充值金额
    TextView mDetermine;//确定
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Bind(R.id.note)
    TextView mNote;
    private String mUserId;
    InfoData service;
    private MyPayWap dialogs;
    private String mMoney = "";
    public static final int SDK_PAY_FLAG = 110;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeFragment.this.getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                        RechargeFragment.this.getActivity().finish();
                        Logger.i("pay", "支付结果成功--->" + resultInfo);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeFragment.this.getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                        Logger.i("pay", "支付结果失败--->" + resultInfo);
                    }
                    break;
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recharge, container, false);
        initRetrofit();
        init(v);
        onClicks();
        onGuangBiao();//光标不显
        ButterKnife.bind(this, v);
        return v;
    }

    public void init(View v) {
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        mRecharge = (EditText) v.findViewById(R.id.mine_recharge_et_id);
        mDetermine = (TextView) v.findViewById(R.id.mine_recharge_determine_id);//确定

        getNote();
    }

    public void onClicks() {
        mDetermine.setOnClickListener(this);//确定
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_recharge_determine_id:
                mMoney = mRecharge.getText().toString();
                if (mMoney == null || mMoney.equals("")) {
                    ToastUtil.showToast(this.getActivity(), "请输入充值金额");
                    return;
                }
                onDialogs("选择支付方式");

                break;
            case R.id.zhifubao_id://Dialog微信支付
                toChongzhi("2", mMoney);
                dialogs.dismiss();
                break;
            case R.id.weixin_id://Dialog支付宝支付
                toChongzhi("1", mMoney);
                dialogs.dismiss();
                break;
            case R.id.return_id2://Dialog取消
                dialogs.dismiss();
                break;

        }
    }

    /*支付宝微信支付的选择弹出框*/
    public void onDialogs(String note) {//支付dialog
        dialogs = new MyPayWap(this.getActivity());//支付
        dialogs.setCanceledOnTouchOutside(true);//点击空白处是否消失
        dialogs.show();
        final LinearLayout mZhiFuBao = (LinearLayout) dialogs.findViewById(R.id.zhifubao_id);//支付宝支付
        final LinearLayout mWeiXin = (LinearLayout) dialogs.findViewById(R.id.weixin_id);//微信支付
        TextView mtv_title = (TextView) dialogs.findViewById(R.id.mydialog_pay_title);
//        final LinearLayout mYuE = (LinearLayout) dialogs.findViewById(R.id.yue_id);//余额支付
        final ImageView mReturns = (ImageView) dialogs.findViewById(R.id.return_id2);//取消
        mtv_title.setText(note);
        mZhiFuBao.setOnClickListener(this);
        mWeiXin.setOnClickListener(this);
//        mYuE.setOnClickListener(UpgradeVIPActivity.this);
        mReturns.setOnClickListener(this);
    }

    //获取充值参数   rec_type  充值方式， 1:支付宝 2:微信
    public void toChongzhi(final String what, String money) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("son_money", money);
        map.put("rec_type", what);//	充值方式， 1:支付宝 2:微信
        Call<VipPayResponse> call = service.getChongzhi(map);
        call.enqueue(new Callback<VipPayResponse>() {
            @Override
            public void onResponse(Call<VipPayResponse> call, Response<VipPayResponse> response) {
                final VipPayResponse h = response.body();
                int code = h.getCode();
                if (code == 100) {
                    if (what.equals("1")) {//支付宝支付
                        Runnable payRunnable =
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        PayTask alipay = new PayTask(RechargeFragment.this.getActivity());
                                        Map<String, String> result = alipay.payV2(h.getInfo_zfb(), true);
                                        Log.i("msp", result.toString());
                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    } else if (what.equals("2")) {//微信支付
                        PayReq req = new PayReq();
                        req.appId = h.getInfo_wx().getAppid();
                        req.partnerId = h.getInfo_wx().getPartnerid();
                        req.prepayId = h.getInfo_wx().getPrepayid();
                        req.nonceStr = h.getInfo_wx().getNoncestr();
                        req.timeStamp = h.getInfo_wx().getTimestamp();
                        req.packageValue = h.getInfo_wx().getPackageX();
                        req.sign = h.getInfo_wx().getSign();
                        MyApplication.getInstance().getApi().sendReq(req);
                    }
                } else {
//                    Toast.makeText(ShoppingActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<VipPayResponse> call, Throwable t) {
                Toast.makeText(RechargeFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*获取充值的说明*/
    public void getNote() {
        Call<AllConfigFeng> call = service.getAllConfig(new HashMap<String, Object>());
        call.enqueue(new Callback<AllConfigFeng>() {
            @Override
            public void onResponse(Call<AllConfigFeng> call, Response<AllConfigFeng> response) {
                AllConfigFeng allConfigFeng = response.body();
                int code = allConfigFeng.getCode();
                if (code == 100) {
                    String fin_not = allConfigFeng.getInfo().getFin_not();//充值说明
                    if(mNote!=null) {
                        mNote.setText(fin_not);
                    }

                }

            }

            @Override
            public void onFailure(Call<AllConfigFeng> call, Throwable t) {
                Toast.makeText(RechargeFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void onGuangBiao() {
        mRecharge.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
        // 编辑框设置触摸监听
        mRecharge.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    mRecharge.setCursorVisible(true);// 再次点击显示光标
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

package com.linkhand.fenxiao.activity.homepage.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.MyApplication;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.HomePageActivity;
import com.linkhand.fenxiao.activity.mine.SetPwdActivity;
import com.linkhand.fenxiao.adapter.home.PlaceOrderAdapter;
import com.linkhand.fenxiao.bean.MessageEvent;
import com.linkhand.fenxiao.dialog.MyDialogUpgradeVip;
import com.linkhand.fenxiao.dialog.MyPayWap;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.home.DefaultAddressFeng;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.home.MyGoodsFeng;
import com.linkhand.fenxiao.feng.home.OrderInterfaceNewFeng;
import com.linkhand.fenxiao.feng.home.VipDetailResponse;
import com.linkhand.fenxiao.feng.home.VipPayResponse;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.Logger;
import com.linkhand.fenxiao.utils.MyListView;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.views.PasswordInputView;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ConfirmOrderActivity extends BaseActicity implements View.OnClickListener {
    //  implements  , AllConfirmOrderInfo
    LinearLayout mReturn;//返回
    RelativeLayout mAddress;//地址
    TextView mText;//color
    int number = 1;
    TextView mAllRNB;//合计金额

    TextView mConfirmPay;//确认支付

    InfoData service;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id

    String singleRMBs;
    int singleRMB;
    LinearLayout mWeiXin, mZhiFuBao, mYuE;//微信支付   支付宝支付   余额支付
    TextView mWeiXinTu, mZhiFuBaoTu, mYuETu;//微信,支付宝,余额  选中图
    TextView mName;//默认收货人名称
    TextView mPhone;//默认收货人电话
    TextView mDefaultAddress;//默认收货人地址

    List<MyGoodsFeng> receiveList = null;//接收的list数据
    String nums; //商品选中数量
    String mGoodsId;//商品id
    String specId;//选中的规格id
    String sited_id = "";//收货地址id
    String allMater;//总价母币
    String allSon;//总价子币
    int oneMater;//单价母币
    int oneSon;//单价子币
    @Bind(R.id.order_listview_id)
    MyListView mListview;
    PlaceOrderAdapter mAdapter;

    List<OrderInterfaceNewFeng.InfoBean> infoBeanList;//总数据
    @Bind(R.id.insert_address)
    TextView mInAddress;//没地址时的提示字
    TextView morderZi;//收货地址字
    String Mater_name = "母币";//母币名称
    String Son_name = "子币";//子币名称
    private String mDanhao;
    private boolean isVip = false;
    private MyPayWap dialogs;
    public static final int SDK_PAY_FLAG = 110;
    String mVipMater = "xxx";//返母币
    String mVipSon = "xxx";//返子币

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
                        Toast.makeText(ConfirmOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        if (isVip) {
                            onYesDialogs();//支付成功dialog
                        }else {
                            finish();
                        }
                        Logger.i("pay", "支付结果成功--->" + resultInfo);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        Logger.i("pay", "支付结果失败--->" + resultInfo);
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        initRetrofit();
        initView();
        onClicks();
        onColors();//给text中的字加颜色
        onaddress();//获取默认地址
//        onMessage();//获取订单信息
        EventBus.getDefault().register(this);
    }

    public void initView() {
        infoBeanList = new ArrayList<>();
        mReturn = (LinearLayout) findViewById(R.id.order_return_id);
        mText = (TextView) findViewById(R.id.order_tv_id2);
        mName = (TextView) findViewById(R.id.order_username_id);//默认收货人名称
        mPhone = (TextView) findViewById(R.id.order_phone_id);//默认收货人电话
        mDefaultAddress = (TextView) findViewById(R.id.order_address_ids);//默认收货人地址
        mAddress = (RelativeLayout) findViewById(R.id.order_address_id);//地址
        mAllRNB = (TextView) findViewById(R.id.confirmorder_allRMB);//合计金额
        mConfirmPay = (TextView) findViewById(R.id.confirm_pay_id);//确认支付
        morderZi = (TextView) findViewById(R.id.order_tv_id);//收货人地址字

        mWeiXin = (LinearLayout) findViewById(R.id.confirm_weixin_id);//微信支付
        mZhiFuBao = (LinearLayout) findViewById(R.id.confirm_zhifubao_id);//支付宝支付
        mYuE = (LinearLayout) findViewById(R.id.confirm_yue_id);//余额支付
        mWeiXinTu = (TextView) findViewById(R.id.confirm_weixin_iv_id);//微信支付选中图
        mZhiFuBaoTu = (TextView) findViewById(R.id.confirm_zhifubao_iv_id);//支付宝支付选中图
        mYuETu = (TextView) findViewById(R.id.confirm_yue_iv_id);//余额支付选中图
        mListview.setFocusable(false);

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove("addressId");/*只是在这里进行使用，所以每次需要清空它*/
        editor.commit();
        mUserId = preferences.getString("user_id", "");
        Mater_name = preferences.getString("Mater_name", "母币");//母币名称
        Son_name = preferences.getString("Son_name", "子币");//子币名称

        Intent intent = this.getIntent();
        if (intent != null) {
            mDanhao = intent.getStringExtra("danhao");
            isVip = intent.getBooleanExtra("isVip", false);
            nums = intent.getStringExtra("nums"); //选中数量
            mGoodsId = intent.getStringExtra("goods_id"); //商品id
            specId = intent.getStringExtra("specId"); //选中的规格id
            Log.e("yh", "商品选中数量--" + nums + "--商品id--" + mGoodsId + "--选中的规格id--" + specId);
        }
        /*判断是否是vip界面详情跳过来的支付*/
        if (isVip) {
            getVipXiaDan();
        } else {
            getOrderDetail();
        }

    }

    public void onClicks() {
        mReturn.setOnClickListener(this);
        mAddress.setOnClickListener(this);
        mConfirmPay.setOnClickListener(this);
        mWeiXin.setOnClickListener(this);
        mZhiFuBao.setOnClickListener(this);
        mYuE.setOnClickListener(this);

    }

    public void onColors() {
        String string01 = mText.getText().toString();
        SpannableString spannableString01 = new SpannableString(string01);
        ForegroundColorSpan colorSpan01 = new ForegroundColorSpan(getResources().getColor(R.color.colorred));
        spannableString01.setSpan(colorSpan01, 5, spannableString01.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mText.setText(spannableString01);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_pay_id://确认支付
                if (sited_id.equals("")) {
                    Toast.makeText(this, "请设置收货地址", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmOrderActivity.this, AddressActivity.class);
                    startActivity(intent);
                } else {
                    if (isVip) {
                        onDialogs("请选择支付方式");
                    } else {
                        /*逻辑修改--*/
//                        showPassDialog();
                        getPayMoney();
                    }
                }
                break;
            case R.id.order_address_id://地址
                Intent intent = new Intent(ConfirmOrderActivity.this, AddressActivity.class);
                String addressId = preferences.getString("addressId", "");
                if(addressId.equals("")){
                    addressId=sited_id;
                }
                intent.putExtra("addressId",addressId);
                startActivity(intent);
                break;
            case R.id.order_return_id://返回
                this.finish();
                break;
            case R.id.zhifubao_id://Dialog微信支付
                if (isVip) {
                    togetZhifu(mDanhao, "2");
                } else {
                    getOrderDingdan(2);
                }
                dialogs.dismiss();
                break;
            case R.id.weixin_id://Dialog支付宝支付
                if (isVip) {
                    togetZhifu(mDanhao, "1");
                } else {
                    getOrderDingdan(1);
                }
                dialogs.dismiss();
                break;
            case R.id.return_id2://Dialog取消
                dialogs.dismiss();
                break;
        }
    }

    /*普通商品的订单详情页数据*/
    public void getOrderDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("order_ids", mDanhao);//商品id
        Call<OrderInterfaceNewFeng> call = service.getNewOderDetails(map);
        call.enqueue(new Callback<OrderInterfaceNewFeng>() {
            @Override
            public void onResponse(Call<OrderInterfaceNewFeng> call, Response<OrderInterfaceNewFeng> response) {
                OrderInterfaceNewFeng pcfeng = response.body();
                int code = pcfeng.getCode();
                if (code == 100) {
                    infoBeanList = pcfeng.getInfo();
                    mAdapter = new PlaceOrderAdapter(ConfirmOrderActivity.this, infoBeanList);
                    mListview.setAdapter(mAdapter);
                    allMater = pcfeng.getAllsMoney() + "";//总价母币
                    allSon = pcfeng.getAllsson() + "";//总价子币
                    mAllRNB.setText("¥" + allMater + Mater_name + "\n¥" + allSon + Son_name);//全部金额
                } else {
                }

            }

            @Override
            public void onFailure(Call<OrderInterfaceNewFeng> call, Throwable t) {
                Log.e("接口出错了0", t.toString());

            }
        });
    }

    /*vip商品下单详情页面数据*/
    public void getVipXiaDan() {
        Map<String, Object> map = new HashMap<>();
        map.put("vip_order_id", mDanhao);
        Log.e("vip_order_id", mDanhao);
        Call<VipDetailResponse> call = service.getVipDetail(map);
        call.enqueue(new Callback<VipDetailResponse>() {
            @Override
            public void onResponse(Call<VipDetailResponse> call, Response<VipDetailResponse> response) {
                VipDetailResponse vipDetailResponse = response.body();
                int code = vipDetailResponse.getCode();
                if (code == 100) {
                    VipDetailResponse.InfoBean info = vipDetailResponse.getInfo();
                    infoBeanList.clear();
                    List<OrderInterfaceNewFeng.InfoBean.SpeciBean> mlv = new ArrayList<>();
                    if (info.getSpeci() != null && info.getSpeci().size() != 0) {
                        for (VipDetailResponse.InfoBean.SpeciBean bean : info.getSpeci()) {
                            mlv.add(new OrderInterfaceNewFeng.InfoBean.SpeciBean(bean.getVsp_id() + "", bean.getSpeci_name(), bean.getVsp_value()));
                        }
                    }
                    infoBeanList.add(new OrderInterfaceNewFeng.InfoBean("1", info.getVip_order_id(), info.getVip_good_id(), info.getVip_good_name(),
                            info.getVip_good_val(), info.getVip_order_money(), "不知道", info.getVimg_url(), mlv));
                    mAdapter = new PlaceOrderAdapter(ConfirmOrderActivity.this, infoBeanList);
                    mListview.setAdapter(mAdapter);
                    allMater = info.getVip_good_val();//总价母币
                    allSon = info.getVip_order_money();//总价子币
                    mVipMater = info.getVip_good_val();
                    mVipSon = info.getVip_order_money();
                    mAllRNB.setText(allSon + "元");//全部金额

                } else {
                    Toast.makeText(ConfirmOrderActivity.this, vipDetailResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VipDetailResponse> call, Throwable t) {

            }
        });
    }

    /*支付订单前  的 金额验证*/
    public void getPayMoney() {
//        getPayMoney
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("order_ids", mDanhao);

        Call<HttpResponse> call = service.getPayMoney(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                final HttpResponse httpResponseo = response.body();
                if (httpResponseo.getCode() == 100) {//子母币都够， 提示话费价格是否支付，验证支付密码
                    new ShowRemindDialog().showRemind(ConfirmOrderActivity.this, "确定", "取消", "提示", httpResponseo.getSuccess(), R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
                        @Override
                        public void OnSureClickListener() {
                            showPassDialog();
                        }
                    }).setCancelListener(new ShowRemindDialog.OnTvCancelListener() {
                        @Override
                        public void OnTvCancelListener() {
                            ConfirmOrderActivity.this.finish();
                        }
                    });

                } else if (httpResponseo.getCode() == 201) {//母币不足，提示是否去兑换，跳转z子母币兑换
                    new ShowRemindDialog().showRemind(ConfirmOrderActivity.this, "确定", "取消", "提示", httpResponseo.getSuccess(), R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
                        @Override
                        public void OnSureClickListener() {
                            Intent intent = new Intent(ConfirmOrderActivity.this, HomePageActivity.class);
                            intent.putExtra("what", "2");
                            editor.putString("what", "2");
                            editor.commit();
                            ConfirmOrderActivity.this.startActivity(intent);
                            ConfirmOrderActivity.this.finish();

                        }
                    }).setCancelListener(new ShowRemindDialog.OnTvCancelListener() {
                        @Override
                        public void OnTvCancelListener() {
                            ConfirmOrderActivity.this.finish();
                        }
                    });

                } else if (httpResponseo.getCode() == 202) {//子币不足，提示微信支付宝支付，选择调用支付
                    new ShowRemindDialog().showRemind(ConfirmOrderActivity.this, "确定", "取消", "提示", httpResponseo.getSuccess(), R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
                        @Override
                        public void OnSureClickListener() {
                            onDialogs(httpResponseo.getSuccess());
                        }
                    }).setCancelListener(new ShowRemindDialog.OnTvCancelListener() {
                        @Override
                        public void OnTvCancelListener() {
                            ConfirmOrderActivity.this.finish();
                        }
                    });

                } else {
                    ToastUtil.showToast(ConfirmOrderActivity.this, httpResponseo.getSuccess());
                }


            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(ConfirmOrderActivity.this, "网络异常");
            }
        });


    }


    /*显示输入二级密码的弹窗*/
    public void showPassDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_pass, null);
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
                    commitPass(pass.getText().toString());
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
            InputMethodManager inputManager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }
    /*验证二级密码是否正确*/
    public void commitPass(String pay_pwd) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("pay_pwd", pay_pwd);
        Call<HttpResponse> call = service.getPayPwd(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    getOrderDingdan(0);
                } else if (httpResponse.getCode() == 201) {//未设置
                    Toast.makeText(ConfirmOrderActivity.this, httpResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmOrderActivity.this, SetPwdActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ConfirmOrderActivity.this, httpResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Toast.makeText(ConfirmOrderActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /*普通订单支付  iswhat=0 余额支付 1支付宝支付  2微信支付*/
    public void getOrderDingdan(final int isWhat) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("site_id", sited_id);
        map.put("order_ids", mDanhao);
        if (isWhat != 0) {
            map.put("pay_type", isWhat + "");
        }
        Call<VipPayResponse> call = service.getOrderPay(map);
        call.enqueue(new Callback<VipPayResponse>() {
            @Override
            public void onResponse(Call<VipPayResponse> call, Response<VipPayResponse> response) {
                final VipPayResponse h = response.body();
                int code = h.getCode();
                if (code == 100) {
                    if (isWhat == 1) {//支付宝支付
                        Runnable payRunnable =
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        PayTask alipay = new PayTask(ConfirmOrderActivity.this);
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
                    } else if (isWhat == 2) {//微信支付
                        PayReq req = new PayReq();
                        req.appId = h.getInfo_wx().getAppid();
                        req.partnerId = h.getInfo_wx().getPartnerid();
                        req.prepayId = h.getInfo_wx().getPrepayid();
                        req.nonceStr = h.getInfo_wx().getNoncestr();
                        req.timeStamp = h.getInfo_wx().getTimestamp();
                        req.packageValue = h.getInfo_wx().getPackageX();
                        req.sign = h.getInfo_wx().getSign();
                        MyApplication.getInstance().getApi().sendReq(req);
                    } else if (isWhat == 0) {//余额支付
                        Toast.makeText(ConfirmOrderActivity.this, h.getSuccess(), Toast.LENGTH_SHORT).show();
                        ConfirmOrderActivity.this.finish();
                    }
                } else if (code == 201) {
                    onDialogs(h.getSuccess());
                } else {
                    Toast.makeText(ConfirmOrderActivity.this, h.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VipPayResponse> call, Throwable t) {

            }
        });
    }


    /*支付宝微信支付的选择弹出框*/
    public void onDialogs(String note) {//支付dialog
        dialogs = new MyPayWap(this);//支付
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

    /*vip支付宝、微信支付*/
    public void togetZhifu(String vipOrderId, final String isWhat) {
        Map<String, Object> map = new HashMap<>();
        map.put("vip_order_id", vipOrderId);
        map.put("vip_pay_type", isWhat);
        Call<VipPayResponse> call = service.getZhifubao(map);
        call.enqueue(new Callback<VipPayResponse>() {
            @Override
            public void onResponse(Call<VipPayResponse> call, Response<VipPayResponse> response) {
                final VipPayResponse h = response.body();
                if (h.getCode() == 100) {
                    if (isWhat.equals("1")) {//支付宝支付
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
                                Map<String, String> result = alipay.payV2(h.getInfo_zfb(), true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    } else {//微信支付
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
                } else if (h.getCode() == 301) {/*弹窗修改推荐人*/
                    configYaoqingDialog(h.getSuccess());
                } else {
                    ToastUtil.showToast(ConfirmOrderActivity.this, h.getSuccess());
                }
            }

            @Override
            public void onFailure(Call<VipPayResponse> call, Throwable t) {
                ToastUtil.showToast(ConfirmOrderActivity.this, "网络异常");
            }
        });
    }

    public void configYaoqingDialog(String content) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_yaoqing, null);
        dialog.setView(v);

        TextView mtv_title = (TextView) v.findViewById(R.id.dialog_yaoqing_title);
        TextView mtv_content = (TextView) v.findViewById(R.id.dialog_yaoqing_content);
        final EditText medt = (EditText) v.findViewById(R.id.dialog_yaoqing_edt);
        TextView mtv_sure = (TextView) v.findViewById(R.id.dialog_yaoqing_sure);
        if (content != null && !content.equals("")) {
            mtv_content.setText(content);
        }
        mtv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yaoqingma = medt.getText().toString();
                if (yaoqingma == null || yaoqingma.equals("")) {
                    ToastUtil.showToast(ConfirmOrderActivity.this, "请输入邀请码");
                    return;
                }
                changeYaoQing(yaoqingma);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    /*修改邀请码*/
    public void changeYaoQing(String inviter) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("inviter", inviter);
        Call<HttpResponse> call = service.getINviterUpdate(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                ToastUtil.showToast(ConfirmOrderActivity.this, httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(ConfirmOrderActivity.this, "网络异常");
            }
        });


    }

    /*vip支付成功的弹窗*/
    public void onYesDialogs() {//支付成功dialog
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //是否vip  0否  1是
        editor.putString("userIsVip", "1");//-------------------测试-------------------------
        editor.commit();
        MyDialogUpgradeVip dialog = new MyDialogUpgradeVip(this, mVipMater, mVipSon);//支付成功
        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.setOnBtClickListener(new MyDialogUpgradeVip.OnBtClickListener() {
            @Override
            public void OnBtCLickListener(int id) {
                switch (id) {
                    case R.id.dialogvip_determine_id:
                        ConfirmOrderActivity.this.finish();
                        break;
                }
            }
        });
        dialog.show();
    }


    public void onMessage() {//获取订单信息(下单页面)
    }

    /*获取默认地址*/
    public void onaddress() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<DefaultAddressFeng> call = service.getObtainDefaultAddress(map);
        call.enqueue(new Callback<DefaultAddressFeng>() {
            @Override
            public void onResponse(Call<DefaultAddressFeng> call, Response<DefaultAddressFeng> response) {
                DefaultAddressFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    mAddress.setVisibility(View.VISIBLE);
                    DefaultAddressFeng.InfoBean bean = pcfeng.getInfo();
                    String site_city1 = bean.getSite_city1();//省
                    String site_city2 = bean.getSite_city2();//市
                    String site_city3 = bean.getSite_city3();//区
                    String site_detail = bean.getSite_detail();//详细信息
                    String site_name = bean.getSite_name();//名称
                    String site_tel = bean.getSite_tel();//电话
                    sited_id = bean.getSite_id();//收货地址id

                    mName.setText(site_name);//默认收货人名称
                    mPhone.setText(site_tel);//默认收货人电话
                    mDefaultAddress.setText(site_city1 + " " + site_city2 + site_city3+site_detail);//默认收货人地址
                } else {
//                    Toast.makeText(ConfirmOrderActivity.this, success, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(ConfirmOrderActivity.this, "清选择收货地址", Toast.LENGTH_SHORT).show();
                    mInAddress.setVisibility(View.VISIBLE);
                    mPhone.setVisibility(View.GONE);
                    mName.setVisibility(View.GONE);
                    morderZi.setVisibility(View.GONE);
                    mDefaultAddress.setVisibility(View.GONE);
//                    Intent intent = new Intent(ConfirmOrderActivity.this, AddressActivity.class);
//                    intent.putExtra("judgeAddress", "1");//1  没有地址
//                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<DefaultAddressFeng> call, Throwable t) {

            }
        });
    }

    /*获取选中地址*/
    public void onSelectedAddress(String addressId) {
        Map<String, Object> map = new HashMap<>();
        map.put("site_id", addressId);//选中地址id
        Call<DefaultAddressFeng> call = service.getSelectedAddress(map);
        call.enqueue(new Callback<DefaultAddressFeng>() {
            @Override
            public void onResponse(Call<DefaultAddressFeng> call, Response<DefaultAddressFeng> response) {
                DefaultAddressFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    mInAddress.setVisibility(View.GONE);
                    mPhone.setVisibility(View.VISIBLE);
                    mName.setVisibility(View.VISIBLE);
                    morderZi.setVisibility(View.VISIBLE);
                    mDefaultAddress.setVisibility(View.VISIBLE);

                    DefaultAddressFeng.InfoBean bean = pcfeng.getInfo();
                    String site_city1 = bean.getSite_city1();//省
                    String site_city2 = bean.getSite_city2();//市
                    String site_city3 = bean.getSite_city3();//区
                    String site_detail = bean.getSite_detail();//详细信息
                    String site_name = bean.getSite_name();//名称
                    String site_tel = bean.getSite_tel();//电话

                    mName.setText(site_name);//选中收货人名称
                    mPhone.setText(site_tel);//选中收货人电话
                    mDefaultAddress.setText(site_city1 + " " + site_city2 + site_city3+site_detail);//选中收货人地址
                } else {
                    Toast.makeText(ConfirmOrderActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DefaultAddressFeng> call, Throwable t) {

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
    protected void onRestart() {
        super.onRestart();
        //获取选中地址id
        String addressId = preferences.getString("addressId", "");
        Log.e("yh", "mUserId--" + mUserId);
        if (addressId.equals("")) {
            sited_id = "";
            onaddress();//获取默认地址
        } else {
//            mAddress.setVisibility(View.VISIBLE);
            sited_id = addressId;//收货地址id
            onSelectedAddress(addressId);//获取选中地址
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleMessage(MessageEvent msg) {
        Bundle bundle = new Bundle();
        switch (msg.getMessage()) {
            case "finish":
                if (isVip) {
                    onYesDialogs();
                } else {
                    ConfirmOrderActivity.this.finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}

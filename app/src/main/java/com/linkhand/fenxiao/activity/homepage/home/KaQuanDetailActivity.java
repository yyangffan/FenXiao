package com.linkhand.fenxiao.activity.homepage.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.home.KaQuanDetailBean;
import com.linkhand.fenxiao.utils.MyImageLoader;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.views.MyWevClient;
import com.linkhand.fenxiao.views.PasswordInputView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

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
 @description: 卡券详情页面
 @author: 杨帆
 @time: 2018/7/4 10:31
 @变更历史:
 ********************************************************************/
public class KaQuanDetailActivity extends BaseActicity {

    @Bind(R.id.myvip_back)
    LinearLayout mMyvipBack;
    @Bind(R.id.banner)
    Banner mBanner;
    @Bind(R.id.vip_detail_title)
    TextView mVipDetailTitle;
    @Bind(R.id.vip_deatail_money)
    TextView mVipDeatailMoney;
    @Bind(R.id.vip_detail_position)
    TextView mVipDetailPosition;
    @Bind(R.id.vip_detail_wb)
    WebView mVipDetailWb;
    @Bind(R.id.vip_detail_use)
    TextView mVipSearchUse;
    List<Uri> list;
    private String mUc_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ka_quan_detail);
        ButterKnife.bind(this);
        initEver();
    }

    public void initEver() {
        Intent intent = this.getIntent();
        if (intent != null) {
            mUc_id = intent.getStringExtra("uc_id");
        }
        onMesage();
    }

    @OnClick({R.id.myvip_back, R.id.vip_detail_use})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myvip_back:
                this.finish();
                break;
            case R.id.vip_detail_use:
                showPassDialog();
                break;
        }
    }
    /*显示输入二级密码的弹窗*/
    public void showPassDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_pass, null);
        final PasswordInputView pass = (PasswordInputView) v.findViewById(R.id.again_paypswd_pet);
        TextView mtv_title= (TextView) v.findViewById(R.id.agin_title);
        mtv_title.setText("请向商家索取密码");
        alertDialog.setView(v);
        alertDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showKeyboard(pass);
            }
        },300);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    useKaquan(pass.getText().toString());
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
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }
    /*使用卡券*/
    public void useKaquan(String pass) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("uc_id", mUc_id);
        map.put("pwd", pass);
        Call<HttpResponse> call = service.getUseOpen(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpre = response.body();
                if (httpre.getCode() == 100) {
                    onMesage();
                }
                ToastUtil.showToast(KaQuanDetailActivity.this, httpre.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(KaQuanDetailActivity.this, "网络异常");
            }
        });

    }

    public void onMesage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("uc_id", mUc_id);
        Call<KaQuanDetailBean> call = service.getUseDetailGet(map);
        call.enqueue(new Callback<KaQuanDetailBean>() {
            @Override
            public void onResponse(Call<KaQuanDetailBean> call, Response<KaQuanDetailBean> response) {
                KaQuanDetailBean bean = response.body();
                if (bean.getCode() == 100) {
                    mVipDetailTitle.setText(bean.getInfo().getUse_name());
                    mVipDeatailMoney.setText("¥"+bean.getInfo().getUse_money());
                    mVipDetailPosition.setText(bean.getInfo().getCity_str() + "\n" + bean.getInfo().getUc_detail());
                    mVipSearchUse.setText(bean.getInfo().getNum_state());
                    mVipDetailWb.setWebViewClient(new MyWevClient());
                    mVipDetailWb.getSettings().setJavaScriptEnabled(true);
                    mVipDetailWb.loadDataWithBaseURL(null, bean.getInfo().getUse_content(), "text/html", "utf-8", null);
                    configBanner(bean.getInfo());
                } else {
                    ToastUtil.showToast(KaQuanDetailActivity.this, bean.getSuccess());
                }
            }

            @Override
            public void onFailure(Call<KaQuanDetailBean> call, Throwable t) {
                ToastUtil.showToast(KaQuanDetailActivity.this, "网络异常");
            }
        });

    }

    /*初始化轮播图（设置图片）*/
    public void configBanner(KaQuanDetailBean.InfoBean infoBean) {
        List<String> use_img = infoBean.getUse_img();
        list = new ArrayList<>();
        for (int i = 0; i < use_img.size(); i++) {
            Uri uri = Uri.parse(C.TU + use_img.get(i));
            list.add(uri);
        }
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setIndicatorGravity(Gravity.CENTER);
        mBanner.setImageLoader(new MyImageLoader());
        mBanner.setImages(list);
        mBanner.setBannerAnimation(Transformer.DepthPage);
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(2000);
        mBanner.start();
    }

}

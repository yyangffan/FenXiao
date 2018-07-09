package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.dialog.MyDialogApprove;
import com.linkhand.fenxiao.dialog.MyDialogUpgradeVip;
import com.linkhand.fenxiao.dialog.MyPayWap;
import com.linkhand.fenxiao.feng.home.RecommendedVipGoods;
import com.linkhand.fenxiao.info.InfoData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UpgradeVIPActivity extends BaseActicity implements View.OnClickListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    InfoData service;
    TextView mUpgradeVIP;//立即升级vip
    ImageView mReturn;//返回
    ImageView mTu;//图
    TextView mVipRmb;//价格
    String mVipMater = "xxx";//返母币
    String mVipSon = "xxx";//返子币
    MyPayWap dialogs;//支付dialog
    String mUserId;//个人id
    String mUserIsVip; //是否vip  0否  1是
    String mUserReal;//是否认证  0否  1是

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_vip);
        init();
        initRetrofit();
        onClicks();

        //-------------
        onVipMessage();
//        onApprove();//实名认证
        //-------------
    }

    public void init() {
        mUpgradeVIP = (TextView) findViewById(R.id.home_upgradevip_id);
        mReturn = (ImageView) findViewById(R.id.home_return_id);
        mTu = (ImageView) findViewById(R.id.vip_imageview_id);
        mVipRmb = (TextView) findViewById(R.id.vip_rmb_id);


        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
         mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);
        //是否vip  0否  1是
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
        Log.e("yh", "是否vip--" + mUserIsVip);
        //是否认证  0否  1是
        mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
        Log.e("yh", "是否vip--" + mUserIsVip);

    }

    public void onClicks() {
        mUpgradeVIP.setOnClickListener(this);//立即升级vip
        mReturn.setOnClickListener(this);//返回
    }

    public void onApprove() {//实名认证
        MyDialogApprove dialog = new MyDialogApprove(UpgradeVIPActivity.this);
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
    }

    //SPAN_EXCLUSIVE_EXCLUSIVE
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.home_upgradevip_id://立即升级vip
                //是否认证  0否  1是
                mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
                Log.e("yh", "是否vip--" + mUserIsVip);
                if(mUserIsVip.equals("1")){
                    Toast.makeText(this, "当前已是vip!", Toast.LENGTH_SHORT).show();
                }else{
                    if(mUserReal.equals("0")){//是否认证  0否  1是
                        onApprove();//实名认证
                    }else{
                        onDialogs();//支付dialog
                    }
                }
                break;
            case R.id.home_return_id://返回
                this.finish();
                break;
            case R.id.zhifubao_id://Dialog支付宝支付
                onYesDialogs();//支付成功dialog
                dialogs.dismiss();
                break;
            case R.id.weixin_id://Dialog微信支付
                onYesDialogs();//支付成功dialog
                dialogs.dismiss();
                break;
//            case R.id.yue_id://Dialog余额支付
//                onYesDialogs();//支付成功dialog
//                dialogs.dismiss();
//                break;
            case R.id.return_id2://Dialog取消
                dialogs.dismiss();
                break;
        }
    }

    public void onDialogs() {//支付dialog
        dialogs = new MyPayWap(this);//支付
        dialogs.setCanceledOnTouchOutside(true);//点击空白处是否消失
        dialogs.show();
        final LinearLayout mZhiFuBao = (LinearLayout) dialogs.findViewById(R.id.zhifubao_id);//支付宝支付
        final LinearLayout mWeiXin = (LinearLayout) dialogs.findViewById(R.id.weixin_id);//微信支付
//        final LinearLayout mYuE = (LinearLayout) dialogs.findViewById(R.id.yue_id);//余额支付
        final ImageView mReturns = (ImageView) dialogs.findViewById(R.id.return_id2);//取消

        mZhiFuBao.setOnClickListener(UpgradeVIPActivity.this);
        mWeiXin.setOnClickListener(UpgradeVIPActivity.this);
//        mYuE.setOnClickListener(UpgradeVIPActivity.this);
        mReturns.setOnClickListener(UpgradeVIPActivity.this);
    }

    public void onYesDialogs() {//支付成功dialog
        mUserIsVip="1";
        //是否vip  0否  1是
        editor.putString("userIsVip","1");//-------------------测试-------------------------
        editor.commit();
        MyDialogUpgradeVip dialog = new MyDialogUpgradeVip(this, mVipMater, mVipSon);//支付成功
        dialog.setCanceledOnTouchOutside(true);//点击空白处是否消失
        dialog.show();
    }

    public void onVipMessage() {//vip
        Map<String, Object> map = new HashMap<>();
        Call<RecommendedVipGoods> call = service.getVipRecommended(map);
        call.enqueue(new Callback<RecommendedVipGoods>() {
            @Override
            public void onResponse(Call<RecommendedVipGoods> call, Response<RecommendedVipGoods> response) {
                RecommendedVipGoods pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                if (code == 100) {


                    RecommendedVipGoods.InfoBean mBean = pcfeng.getInfo();
                    String vip_money = mBean.getVip_money();//价格
                    mVipMater = mBean.getVip_mater();//返母币
                    mVipSon = mBean.getVip_son();//返子币
                    String thumb = mBean.getVip_img();//图片

                    mVipRmb.setText(vip_money);
                    if (thumb.equals("") | thumb.equals("null")) {
                    } else {
                        thumb = C.TU + thumb;
//                        Log.e("yh", "thumb--" + thumb);
                        Glide.with(UpgradeVIPActivity.this)
                                .load(thumb)
                                .into(mTu);
                    }

                } else {
//                    Toast.makeText(UpgradeVIPActivity.this, "xx", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RecommendedVipGoods> call, Throwable t) {

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
}

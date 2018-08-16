package com.linkhand.fenxiao.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.login.LoginActivity;
import com.linkhand.fenxiao.activity.login.RetrievePswActivity;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.AllConfigFeng;
import com.linkhand.fenxiao.jpush.SetJPushAlias;
import com.linkhand.fenxiao.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/********************************************************************
 @version: 1.0.0
 @description: 设置界面
 @author: user
 @time: 2018/5/24 15:24
 @变更历史:
 ********************************************************************/

public class SetUpTheActivity extends BaseActicity implements View.OnClickListener {

    @Bind(R.id.setupthe_return_id3)
    LinearLayout mReturnIdTwo;//退出登录
    @Bind(R.id.setupthe_return_id2)
    LinearLayout mReturnIdOne;//返回
    @Bind(R.id.setupthe_phone)
    LinearLayout mPhone;//客服电话
    @Bind(R.id.setupthe_update_psw)
    LinearLayout mUpdatePsw;//修改支付密码
    @Bind(R.id.setup_online)
    TextView mSetupOnline;
    @Bind(R.id.set_banben)
    TextView mSetBanben;
    @Bind(R.id.setup_changeLogin_pwd)
    TextView mSetupChangeLoginPwd;


    Intent intent;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String mQq = "";
    private String mTel = "";
    private String versionName;
    private int appVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_the);
        ButterKnife.bind(this);
        initView();
        onClicks();
    }

    public void initView() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        getData();
        getVersion();
    }

    public void onClicks() {
        mReturnIdOne.setOnClickListener(this);//返回
        mReturnIdTwo.setOnClickListener(this);//退出登录
        mPhone.setOnClickListener(this);//客服电话
        mUpdatePsw.setOnClickListener(this);//修改支付密码
        mSetupOnline.setOnClickListener(this);//在线客服
        mSetupChangeLoginPwd.setOnClickListener(this);//修改登录密码
    }

    /**
     * 获取当前版本号
     */
    public void getVersion() {

        // 获取当前版本信息
        try {
            PackageInfo packageInfo = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            versionName = packageInfo.versionName;
            appVersionCode = packageInfo.versionCode;
            mSetBanben.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setupthe_return_id3://退出登录
                logout();
                break;
            case R.id.setupthe_return_id2://返回
                this.finish();
                break;
            case R.id.setupthe_phone://客服电话
                if (mTel.equals("")) {
                    ToastUtil.showToast(this, "未找到客服电话");
                } else {
                    call(mTel);
                }
                break;
            case R.id.setupthe_update_psw://修改支付密码
                intent = new Intent(this, SetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.setup_online:
                if (isQQClientAvailable(this)) {
                    if (mQq.equals("")) {
                        ToastUtil.showToast(this, "稍后重试");
                    } else {
                        String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + mQq;
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }
                } else {
                    Toast.makeText(this, "请安装QQ", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.setup_changeLogin_pwd://修改登录密码
                Intent intent = new Intent(SetUpTheActivity.this, RetrievePswActivity.class);
                intent.putExtra("isWhat", "change");
                startActivity(intent);
                break;

        }
    }

    public void logout() {
        new ShowRemindDialog().showRemind(this, "确定", "取消", "提示", "退出登录？", R.drawable.logout, new ShowRemindDialog.OnTvClickListener() {
            @Override
            public void OnSureClickListener() {
                editor.remove("user_id");
                editor.remove("userReal");
                editor.remove("pay_pwd");
                editor.remove("userIsVip");
                editor.remove("instructions");
                editor.commit();
                new SetJPushAlias("", SetUpTheActivity.this).cancleAlias();
                intent = new Intent(SetUpTheActivity.this, LoginActivity.class);//登录
                startActivity(intent);
                SetUpTheActivity.this.finish();
//                this.finish();
            }
        });
    }

    /*获取到客服电话以及客服QQ*/
    public void getData() {
        Call<AllConfigFeng> call = service.getAllConfig(new HashMap<String, Object>());
        call.enqueue(new Callback<AllConfigFeng>() {
            @Override
            public void onResponse(Call<AllConfigFeng> call, Response<AllConfigFeng> response) {
                AllConfigFeng allConfigFeng = response.body();
                int code = allConfigFeng.getCode();
                if (code == 100) {
                    mTel = allConfigFeng.getInfo().getKefu_tel();//客服电话
                    mQq = allConfigFeng.getInfo().getKefu_qq();//qq客服
                } else {
//                    Toast.makeText(SetUpTheActivity.this, allConfigFeng., Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllConfigFeng> call, Throwable t) {
                Toast.makeText(SetUpTheActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 判断是否安装了qq
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        //跳过拨号界面，直接拨打电话
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//        startActivity(intent);
    }

}

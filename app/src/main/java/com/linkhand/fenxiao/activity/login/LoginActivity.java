package com.linkhand.fenxiao.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.HomePageActivity;
import com.linkhand.fenxiao.dialog.LoadingPop;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.AllConfigFeng;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.login.LoginFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.jpush.SetJPushAlias;
import com.linkhand.fenxiao.utils.StatusBarUtils;
import com.linkhand.fenxiao.utils.ToastUtil;

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

/*
*登录
* */
public class LoginActivity extends BaseActicity implements View.OnClickListener {
    TextView mRegistration;//注册
    TextView mLogin;//登录
    TextView mForgetPsw;//忘记密码
    TextView mtv_diushi;//账户丢失
    Intent intent;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Bind(R.id.fenxiao_login_name)
    EditText mName;
    @Bind(R.id.login_denglu_psw)
    EditText mPsw;
    String mNameStr;//账号
    String mPswStr;//密码
    InfoData service;
    private String mQq;
    private LoadingPop mLoadingPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
        onClicks();
        initRetrofit();
        onGuangBiao();
        getData();
    }

    public void init() {
        mLoadingPop=new LoadingPop(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorwhites);//状态栏
        mRegistration = (TextView) findViewById(R.id.fenxiao_registration);
        mtv_diushi = (TextView) findViewById(R.id.fenxiao_diushi);
        mLogin = (TextView) findViewById(R.id.fenxiao_login);
        mForgetPsw = (TextView) findViewById(R.id.fenxiao_forgetpassword);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();


    }

    public void onClicks() {
        mRegistration.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mForgetPsw.setOnClickListener(this);
        mtv_diushi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenxiao_login://登录
                onLogin();//登录
                break;
            case R.id.fenxiao_registration://注册
                intent = new Intent(this, RegistrationActivity.class);
                intent.putExtra("isAccessLogin", true);
                startActivity(intent);
                break;
            case R.id.fenxiao_forgetpassword://忘记密码
                intent = new Intent(this, RetrievePswActivity.class);
                startActivity(intent);
                break;
            case R.id.fenxiao_diushi:
                new ShowRemindDialog().showRemind(this, "确定", "取消", "", "更换帐号请联系客服", R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
                    @Override
                    public void OnSureClickListener() {
                        if (isQQClientAvailable(LoginActivity.this)) {
                            if (mQq.equals("")) {
                                ToastUtil.showToast(LoginActivity.this, "稍后重试");
                            } else {
                                String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + mQq;
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "请安装QQ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
        }
    }

    public void onLogin() {//登录
        mNameStr = mName.getText().toString() + "";
        mPswStr = mPsw.getText().toString() + "";
        if (mNameStr.equals("") | mPswStr.equals("")) {
            Toast.makeText(LoginActivity.this, "请输入账号密码", Toast.LENGTH_SHORT).show();
        } else {
            onMessage();
        }
    }

    /*获取到客服QQ*/
    public void getData() {
        Call<AllConfigFeng> call = service.getAllConfig(new HashMap<String, Object>());
        call.enqueue(new Callback<AllConfigFeng>() {
            @Override
            public void onResponse(Call<AllConfigFeng> call, Response<AllConfigFeng> response) {
                AllConfigFeng allConfigFeng = response.body();
                int code = allConfigFeng.getCode();
                if (code == 100) {
                    mQq = allConfigFeng.getInfo().getKefu_qq();//qq客服
                }
            }

            @Override
            public void onFailure(Call<AllConfigFeng> call, Throwable t) {
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


    public void onMessage() {
        mLoadingPop.showLoadPop();
        Map<String, Object> map = new HashMap<>();
        map.put("user_pwd", mPswStr);//密码
        map.put("user_tel", mNameStr);//电话
        Call<LoginFeng> call = service.getLogin(map);
        call.enqueue(new Callback<LoginFeng>() {
            @Override
            public void onResponse(Call<LoginFeng> call, Response<LoginFeng> response) {
                mLoadingPop.hideLoadPop();
                LoginFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(LoginActivity.this, success, Toast.LENGTH_SHORT).show();
                    String user_id = pcfeng.getInfo();
                    String User_is_vip = pcfeng.getUser().getUser_is_vip();
                    String Is_real = pcfeng.getUser().getIs_real();
                    String phone = pcfeng.getUser().getUser_tel();
                    //存入user_id个人id
                    editor.putString("user_id", user_id);
                    editor.commit();
                    //是否vip  0否  1是
//                    editor.putString("userIsVip", "0");//-------------------测试-------------------------
                    editor.putString("userIsVip", User_is_vip);
                    editor.commit();
                    //是否认证  0否  1是
//                    editor.putString("userReal", "0");//-------------------测试-------------------------
                    editor.putString("userReal", Is_real);
                    /*存入手机号*/
                    editor.putString("phone", phone);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);//首页
                    startActivity(intent);
                    LoginActivity.this.finish();
//                    new SetJPushAlias(user_id, LoginActivity.this).setAlias();
                    new SetJPushAlias(pcfeng.getUser().getNew_token(),LoginActivity.this).setAlias();
                    loginOut(pcfeng.getUser().getOld_token());
                } else {
                    Toast.makeText(LoginActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginFeng> call, Throwable t) {
                mLoadingPop.hideLoadPop();
                ToastUtil.showToast(LoginActivity.this, "网络异常");
            }
        });
    }

    /*登录下线通知*/
    public void loginOut(String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        Call<HttpResponse> call = service.getLoginOut(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {

            }
        });


    }

    public void onGuangBiao() {
        mName.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
// 编辑框设置触摸监听
        mName.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    mName.setCursorVisible(true);// 再次点击显示光标
                }

                return false;
            }

        });

        mPsw.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
// 编辑框设置触摸监听
        mPsw.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    mPsw.setCursorVisible(true);// 再次点击显示光标
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
    public void onBackPressed() {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra("where", "0");
        startActivity(intent);
        this.finish();
    }
}

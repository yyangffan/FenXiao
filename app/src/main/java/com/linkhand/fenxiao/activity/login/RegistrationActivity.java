package com.linkhand.fenxiao.activity.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.HomePageActivity;
import com.linkhand.fenxiao.dialog.MyDialog;
import com.linkhand.fenxiao.feng.AllConfigFeng;
import com.linkhand.fenxiao.feng.login.Register;
import com.linkhand.fenxiao.feng.login.Registered;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.ToastUtil;

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

/*
*注册
* */
public class RegistrationActivity extends BaseActicity implements View.OnClickListener {


    TextView mLogin;//去登陆
    LinearLayout mReturn;//返回
    @Bind(R.id.phone_id)
    EditText mPhone;//手机号
    @Bind(R.id.psw_id1)
    EditText mPsw1;
    @Bind(R.id.psw_id2)
    EditText mPsw2;
    @Bind(R.id.invite_id)
    EditText mInvite;//邀请人推荐码
    @Bind(R.id.validation_et_id)
    EditText mValidationContent;//验证码
    @Bind(R.id.validation_tv_id)
    TextView mSend;//点击发送验证码
    @Bind(R.id.checkbox_id)
    CheckBox mCheckBox;//是否同意登录协议（选中状态）
    @Bind(R.id.agreement_id)
    TextView mAgreement;//协议
    private boolean mRunning;
    InfoData service;

    private String mPhoneStr;//手机号
    private String mPsw1Str;
    private String mPsw2Str;
    private String mVerificationCodeStr;//验证码
    private String mInviteCodeStr;//邀请人推荐码
    private boolean mIsAccessLogin;//登录时点击注册过来的  true是  否不是

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        initView();
        onClicks();
        initRetrofit();
    }

    public void initView() {
        Intent intent = this.getIntent();
        if (intent != null) {
            mIsAccessLogin = intent.getBooleanExtra("isAccessLogin", false);
        }
        mLogin = (TextView) findViewById(R.id.fenxiao_register_login_id);
        mReturn = (LinearLayout) findViewById(R.id.fenxiao_return_id);
    }

    public void onClicks() {
        mLogin.setOnClickListener(this);
        mReturn.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mAgreement.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenxiao_register_login_id://去登陆
                onToLogIn();//去登陆
//                    onRegistered();//注册

                break;
            case R.id.fenxiao_return_id://返回
                if (!mIsAccessLogin) {
                    Intent intent = new Intent(this, HomePageActivity.class);
                    intent.putExtra("where", "0");
                    startActivity(intent);
                }
                this.finish();
                break;
            case R.id.validation_tv_id://发送验证码
                String s = mPhone.getText().toString();
                if (s == null || s.equals("")) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (s.length() != 11) {
                    Toast.makeText(this, "请输入11位有效手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                getCode(s);
                break;
            case R.id.agreement_id://注册协议
                onAgreement();//获取协议信息
                break;

        }

    }

    /*获取验证码*/
    public void getCode(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);//电话
        map.put("type", "1");//"2"为忘记密码   1为注册
        Call<Register> call = service.getRegister(map);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Register pcfeng = response.body();
                if (pcfeng.getCode() == 100) {
                    if (mRunning) {
                    } else {
                        downTimer.start();
                    }
                }
                Toast.makeText(RegistrationActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "获取失败,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onToLogIn() {//去注册
        Log.e("yh", "boolean--" + mCheckBox.isChecked());
        if (mCheckBox.isChecked() == true) {
            mPhoneStr = mPhone.getText().toString() + "";
            mPsw1Str = mPsw1.getText().toString() + "";
            mPsw2Str = mPsw2.getText().toString() + "";
            mVerificationCodeStr = mValidationContent.getText().toString() + "";
            mInviteCodeStr = mInvite.getText().toString() + "";
            Log.e("yh", "mPsw1Str--" + mPsw1Str + "mPsw2Str--" + mPsw2Str);
            if (mPhoneStr.equals("") | mPsw1Str.equals("") | mPsw2Str.equals("") | mVerificationCodeStr.equals("") | mInviteCodeStr.equals("")) {
                Toast.makeText(RegistrationActivity.this, "请填全注册信息", Toast.LENGTH_SHORT).show();
            } else if (mPhoneStr.length() != 11) {
                ToastUtil.showToast(this, "请输入11位有效手机号");
            } else if (mPsw1Str.length() < 6) {
                ToastUtil.showToast(this, "请输入至少6位的密码");
            } else {
                if (mPsw1Str.equals(mPsw2Str)) {
                    onRegistered();//注册
                } else {
                    Toast.makeText(RegistrationActivity.this, "密码不一致,请重新输入!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(RegistrationActivity.this, "请同意注册协议", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRegistered() {//注册
        Map<String, Object> map = new HashMap<>();
        map.put("user_pwd", mPsw1Str);//密码
        map.put("user_tel", mPhoneStr);//电话
        map.put("code", mVerificationCodeStr);//验证码
//        map.put("inviter", "w5aa");//邀请码
        map.put("inviter", mInviteCodeStr);//邀请码
        Call<Registered> call = service.getRegistered(map);
        call.enqueue(new Callback<Registered>() {
            @Override
            public void onResponse(Call<Registered> call, Response<Registered> response) {
                Registered pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                if (code == 100) {
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegistrationActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistrationActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Registered> call, Throwable t) {

            }
        });
    }

    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTick(long l) {
            mRunning = true;
//            mSend.getResources().getColor(R.color.colorgraynessd, null);
            mSend.setTextColor(RegistrationActivity.this.getResources().getColor(R.color.colorgraynessd));
            mSend.setText((l / 1000) + "秒后重发");
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onFinish() {
            mRunning = false;
//            mSend.getResources().getColor(R.color.colorred, null);
            mSend.setTextColor(RegistrationActivity.this.getResources().getColor(R.color.colorred));
            mSend.setText("重新发送");
        }
    };

    public void onAgreement() {//获取协议信息
        Map<String, Object> map = new HashMap<>();
        Call<AllConfigFeng> call = service.getAllConfig(map);
        call.enqueue(new Callback<AllConfigFeng>() {
            @Override
            public void onResponse(Call<AllConfigFeng> call, Response<AllConfigFeng> response) {
                AllConfigFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                if (code == 100) {
                    AllConfigFeng.InfoBean bean = pcfeng.getInfo();
                    String notices = bean.getSign_not() + "";//协议信息
                    final MyDialog dialog = new MyDialog(RegistrationActivity.this);
                    dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
                    dialog.show();
                    TextView textone = (TextView) dialog.findViewById(R.id.dialog_confirm_id);
                    TextView textView = (TextView) dialog.findViewById(R.id.mydialog_title);
                    textView.setText("注册协议");
                    textone.setVisibility(View.GONE);
                    LinearLayout lLayout = (LinearLayout) dialog.findViewById(R.id.dialog_llayout_id);
                    lLayout.setVisibility(View.VISIBLE);
                    TextView notice = (TextView) dialog.findViewById(R.id.config_notice_id);//公告须知内容
                    notice.setText(notices);
                    TextView tv_cancel = (TextView) dialog.findViewById(R.id.dialog_login_id);
                    TextView tv_sure = (TextView) dialog.findViewById(R.id.dialog_login_id2);
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCheckBox.setChecked(false);
                            dialog.dismiss();
                        }
                    });
                    tv_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCheckBox.setChecked(true);
                            dialog.dismiss();
                        }
                    });

                } else {
//                    Toast.makeText(HomePageFragment.this.getActivity(), "xx", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllConfigFeng> call, Throwable t) {

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
        if (!mIsAccessLogin) {
            Intent intent = new Intent(this, HomePageActivity.class);
            intent.putExtra("where", "0");
            startActivity(intent);
        }
        this.finish();
    }
}

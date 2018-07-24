package com.linkhand.fenxiao.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.login.Register;
import com.linkhand.fenxiao.info.InfoData;

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
*忘记密码
* */
public class RetrievePswActivity extends BaseActicity implements View.OnClickListener {
    TextView mConfirm;//确认
    LinearLayout mReturn;//返回
    @Bind(R.id.phone_number_id)
    EditText mPhoneNumber;//手机号
    @Bind(R.id.paw_id)
    EditText mPaw;//密码
    @Bind(R.id.new_psw_id)
    EditText mNewPsw;//新密码
    @Bind(R.id.auth_code_id)
    EditText mAuthCode;//输入验证码
    @Bind(R.id.send_sum_id)
    TextView mSend;//发送验证码
    @Bind(R.id.fenxiao_title_id2)
    TextView mFenxiaoTitleId2;

    private boolean mRunning;
    InfoData service;
    private String mIsWhat = "";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_psw);
        ButterKnife.bind(this);
        init();
        initRetrofit();
        onClicks();
    }

    public void init() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mPhone = preferences.getString("phone", "");
        Intent intent = this.getIntent();
        if (intent != null) {
            mIsWhat = intent.getStringExtra("isWhat");
        }
        if (mIsWhat != null && mIsWhat.equals("change")) {
            mFenxiaoTitleId2.setText("修改登录密码");
            mPhoneNumber.setText(mPhone);
            mPhoneNumber.setEnabled(false);
        }

        mConfirm = (TextView) findViewById(R.id.fenxiao_psw_confirm_id);
        mReturn = (LinearLayout) findViewById(R.id.fenxiao_return_id2);
    }

    public void onClicks() {
        mConfirm.setOnClickListener(this);
        mReturn.setOnClickListener(this);
        mSend.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenxiao_psw_confirm_id://确认
                onConfirm();
                break;
            case R.id.fenxiao_return_id2://返回
                this.finish();
                break;
            case R.id.send_sum_id://发送验证码
                onSend();
                break;
        }
    }

    /*获取验证码*/
    public void getCode(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);//电话
        map.put("type", "2");//"2"为忘记密码   1为注册
        Call<Register> call = service.getRegister(map);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Register pcfeng = response.body();
                if(pcfeng.getCode()==100) {
                    if (mRunning) {
                    } else {
                        downTimer.start();
                    }
                }
                Toast.makeText(RetrievePswActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RetrievePswActivity.this, "获取失败,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onSend() {
        String phone = mPhoneNumber.getText() + "";
        if (phone == null || phone.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        } else if (phone.length() != 11) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        getCode(phone);
    }

    public void onConfirm() {
        String phone = mPhoneNumber.getText() + "";
        String pawOne = mPaw.getText() + "";
        String pawTwo = mNewPsw.getText() + "";
        String code = mAuthCode.getText() + "";
        if (phone.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        } else if (pawOne.equals("") | pawTwo.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (pawOne.toString().length() < 6) {
            Toast.makeText(this, "密码长度最少6位", Toast.LENGTH_SHORT).show();
            return;
        } else if (!pawOne.equals(pawTwo)) {
            Toast.makeText(this, "密码不一致,请重新输入!", Toast.LENGTH_SHORT).show();
            return;
        } else if (code == null || code.equals("")) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        onMessage(phone, pawOne, code);//修改密码
    }

    public void onMessage(String phone, String pawOne, String code) {
        Map<String, Object> map = new HashMap<>();//修改密码
        map.put("user_new_pwd", pawOne);//密码
        map.put("user_tel", phone);//电话
        map.put("code", code);//验证码
        Call<ReturnFeng> call = service.getUpdatePsw(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(RetrievePswActivity.this, success, Toast.LENGTH_SHORT).show();
                    if (mIsWhat != null && !mIsWhat.equals("change")) {
                        Intent intent = new Intent(RetrievePswActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        RetrievePswActivity.this.finish();
                    }

                } else {
                    Toast.makeText(RetrievePswActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Toast.makeText(RetrievePswActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTick(long l) {
            mRunning = true;
//            mSend.getResources().getColor(R.color.colorgraynessd, null);
            mSend.setTextColor(RetrievePswActivity.this.getResources().getColor(R.color.colorgraynessd));
            mSend.setText((l / 1000) + "秒后重发");
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onFinish() {
            mRunning = false;
//            mSend.getResources().getColor(R.color.colorred, null);
            mSend.setTextColor(RetrievePswActivity.this.getResources().getColor(R.color.colorred));
            mSend.setText("重新发送");
        }
    };


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

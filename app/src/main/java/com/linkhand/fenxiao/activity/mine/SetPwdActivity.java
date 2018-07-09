package com.linkhand.fenxiao.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.login.Register;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPwdActivity extends BaseActicity {

//    @Bind(R.id.fenxiao_return_id)
//    LinearLayout mFenxiaoReturnId;
//    @Bind(R.id.edittext_pwdyuan)
//    EditText mEdittextPwdyuan;
//    @Bind(R.id.edittext_pwdonce)
//    EditText mEdittextPwdonce;
//    @Bind(R.id.editText_pwdagin)
//    EditText mEditTextPwdagin;
//    @Bind(R.id.set_sure)
//    TextView mSetSure;


    @Bind(R.id.fenxiao_title_id2)
    TextView mFenxiaoTitleId2;
    @Bind(R.id.phone_number_id)
    EditText mPhoneNumberId;
    @Bind(R.id.paw_id)
    EditText mPawId;
    @Bind(R.id.new_psw_id)
    EditText mNewPswId;
    @Bind(R.id.auth_code_id)
    EditText mAuthCodeId;
    @Bind(R.id.send_sum_id)
    TextView mSend;
    @Bind(R.id.fenxiao_psw_confirm_id)
    TextView mFenxiaoPswConfirmId;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    private boolean mRunning;
    private String mPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_psw);//原布局为activity_set_pwd现不使用
        ButterKnife.bind(this);
        initView();

    }

    public void initView() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        mPhone = preferences.getString("phone", "");
        mFenxiaoTitleId2.setText("设置密码");
        mPhoneNumberId.setText(mPhone);
        mPhoneNumberId.setEnabled(false);
        mPawId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        mPawId.setInputType(InputType.TYPE_CLASS_NUMBER);
        mNewPswId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        mNewPswId.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @OnClick({R.id.fenxiao_return_id2, R.id.fenxiao_psw_confirm_id, R.id.send_sum_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fenxiao_return_id2:
                this.finish();
                break;
            case R.id.send_sum_id://发送验证码
                onSend();
                break;
            case R.id.fenxiao_psw_confirm_id:
                onConfirm();
                break;
        }
    }

    public void onConfirm() {
        String pawOne = mPawId.getText() + "";
        String pawTwo = mNewPswId.getText() + "";
        String code = mAuthCodeId.getText() + "";
        if (pawOne.equals("") | pawTwo.equals("")) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (pawOne.toString().length() != 6) {
            Toast.makeText(this, "请输入6位新密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (pawTwo == null || pawTwo.equals("")) {
            Toast.makeText(this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (!pawOne.equals(pawTwo)) {
            Toast.makeText(this, "密码不一致,请重新输入!", Toast.LENGTH_SHORT).show();
            return;
        } else if (code == null || code.equals("")) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        setNewPwd(code, pawOne);
    }

    public void onSend() {
        String phone = mPhoneNumberId.getText() + "";
        if (phone == null || phone.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        } else if (phone.length() != 11) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        getCode(phone);
        if (!phone.equals("")) {
            if (mRunning) {
            } else {
                downTimer.start();
            }
        } else {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
        }
    }

    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTick(long l) {
            mRunning = true;
//            mSend.getResources().getColor(R.color.colorgraynessd, null);
            mSend.setTextColor(SetPwdActivity.this.getResources().getColor(R.color.colorgraynessd));
            mSend.setText((l / 1000) + "秒后重发");
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onFinish() {
            mRunning = false;
//            mSend.getResources().getColor(R.color.colorred, null);
            mSend.setTextColor(SetPwdActivity.this.getResources().getColor(R.color.colorred));
            mSend.setText("发送验证码");
        }
    };

    /*获取验证码*/
    public void getCode(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);//电话
        map.put("type", "4");//"2"为忘记密码   1为注册  4设置修改支付密码
        Call<Register> call = service.getRegister(map);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Register pcfeng = response.body();
                Toast.makeText(SetPwdActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(SetPwdActivity.this, "获取失败,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setNewPwd(String code, String newpwd) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("code", code);//验证码
        map.put("user_pay_pwd", newpwd);//新密码

        Call<HttpResponse> call = service.getSavepayPwd(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse pcfeng = response.body();
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(SetPwdActivity.this, success, Toast.LENGTH_SHORT).show();
                    SetPwdActivity.this.finish();
//                    new ShowRemindDialog().showRemind(SetPwdActivity.this, "好的", "", "提示", success, R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
//                        @Override
//                        public void OnSureClickListener() {
//                            SetPwdActivity.this.finish();
//                        }
//                    });
                } else {
                    Toast.makeText(SetPwdActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Toast.makeText(SetPwdActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }


}

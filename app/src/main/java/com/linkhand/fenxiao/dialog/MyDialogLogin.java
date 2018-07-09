package com.linkhand.fenxiao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.login.LoginActivity;
import com.linkhand.fenxiao.activity.login.RegistrationActivity;

public class MyDialogLogin extends Dialog implements View.OnClickListener {
    TextView mDialogLogin;//登录
    TextView mRegistration;//注册
    ImageView mReturn;//关闭
    Context context;
    Intent intent;

    public MyDialogLogin(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_dialog_login);
        init();
        onClicks();


    }

    public void init() {
        mDialogLogin = (TextView) findViewById(R.id.dialog_login_id);
        mRegistration = (TextView) findViewById(R.id.dialog_login_id2);
        mReturn = (ImageView) findViewById(R.id.dialog_return_id);

    }

    public void onClicks() {
        mDialogLogin.setOnClickListener(this);
        mReturn.setOnClickListener(this);
        mRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_login_id://登录
                intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
            case R.id.dialog_login_id2://注册
                intent = new Intent(context, RegistrationActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
            case R.id.dialog_return_id://关闭
                dismiss();
                break;
        }
    }


}

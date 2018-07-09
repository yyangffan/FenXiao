package com.linkhand.fenxiao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.ApproveActivity;

public class MyDialogApprove extends Dialog implements View.OnClickListener {
    TextView mDialogLogin;//取消
    TextView mRegistration;//实名认证
    Context context;
    Intent intent;

    public MyDialogApprove(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_dialog_approve);
        init();
        onClicks();


    }

    public void init() {
        mDialogLogin = (TextView) findViewById(R.id.dialog_login_id);
        mRegistration = (TextView) findViewById(R.id.dialog_login_id2);

    }

    public void onClicks() {
        mDialogLogin.setOnClickListener(this);
        mRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_login_id://取消
                dismiss();
                break;
            case R.id.dialog_login_id2://实名认证
                intent = new Intent(context, ApproveActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
        }
    }


}

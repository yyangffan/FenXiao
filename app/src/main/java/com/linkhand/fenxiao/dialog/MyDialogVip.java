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
import com.linkhand.fenxiao.activity.homepage.home.AllVipGoodsActivity;

public class MyDialogVip extends Dialog implements View.OnClickListener {
    TextView mRegistration;//立即购买
    ImageView mReturn;//关闭
    Context context;
    Intent intent;

    public MyDialogVip(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_dialog_vip);
        init();
        onClicks();
    }

    public void init() {
        mRegistration = (TextView) findViewById(R.id.dialogvip_login_id2);
        mReturn = (ImageView) findViewById(R.id.dialogvip_return_id);

    }

    public void onClicks() {
        mReturn.setOnClickListener(this);
        mRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogvip_login_id2://立即购买
//                intent = new Intent(context, UpgradeVIPActivity.class);
                intent = new Intent(context, AllVipGoodsActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
            case R.id.dialogvip_return_id://关闭
                dismiss();
                break;
        }
    }
}

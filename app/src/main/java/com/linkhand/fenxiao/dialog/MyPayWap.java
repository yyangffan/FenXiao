package com.linkhand.fenxiao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.linkhand.fenxiao.R;

/**
 * Created by 11860_000 on 2018/1/13.
 */

public class MyPayWap extends Dialog {

    Context context;

    public MyPayWap(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_dialog_pay_wap);
        this.setCanceledOnTouchOutside(false);
    }
}

package com.linkhand.fenxiao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.linkhand.fenxiao.R;

public class MyDialogLashSum extends Dialog{
    public MyDialogLashSum(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_sum);

    }


}

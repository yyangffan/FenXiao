package com.linkhand.fenxiao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;

public class MyDialogApproveFace extends Dialog implements View.OnClickListener {

    TextView mConfirm;//确定
    Context context;
    Intent intent;
    public MyDialogApproveFace(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_rapprove_face);
        init();
        onClicks();
    }

    public void init() {
        mConfirm = (TextView) findViewById(R.id.dialog_face_id);

    }

    public void onClicks() {
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_face_id://确定
                dismiss();
                ((BaseActicity)context).finish();
                break;
        }
    }
}

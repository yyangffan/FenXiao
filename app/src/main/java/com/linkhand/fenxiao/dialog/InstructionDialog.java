package com.linkhand.fenxiao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.linkhand.fenxiao.R;

public class InstructionDialog extends Dialog implements View.OnClickListener {
    TextView mDialog;//确认

    public InstructionDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_instruction_dialog);
        init();
        onClicks();

    }

    public void init() {
        mDialog = (TextView) findViewById(R.id.dialog_sayconfirm_id);

    }

    public void onClicks() {
        mDialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_sayconfirm_id://确认
                dismiss();
                break;
        }
    }
}


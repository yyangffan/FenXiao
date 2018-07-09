package com.linkhand.fenxiao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.UpgradeVIPActivity;

public class DialogUpgradeVIP extends Dialog implements View.OnClickListener {

    TextView mDialogLogin;//立即升级
    TextView mContent;//内容
    TextView mReturn;//关闭
    Context context;
    Intent intent;

    public DialogUpgradeVIP(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_upgrade_vip);
        init();
        onClicks();
        onColors();//给text中的字加颜色

    }

    public void init() {
        mDialogLogin = (TextView) findViewById(R.id.dialog_viplogin_id);
        mReturn = (TextView) findViewById(R.id.dialog_return_id2);
        mContent = (TextView) findViewById(R.id.dialog_tv_id);


    }

    public void onClicks() {
        mDialogLogin.setOnClickListener(this);
        mReturn.setOnClickListener(this);
    }

    public void onColors() {
        String string01 = mContent.getText().toString();

        SpannableString spannableString01 = new SpannableString(string01);
        ForegroundColorSpan colorSpan01 = new ForegroundColorSpan(context.getResources().getColor(R.color.colorred));
        spannableString01.setSpan(colorSpan01, 3, spannableString01.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mContent.setText(spannableString01);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_viplogin_id://立即升级
                intent = new Intent(context, UpgradeVIPActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
            case R.id.dialog_return_id2://关闭
                dismiss();
                break;
        }
    }

}

package com.linkhand.fenxiao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.fenxiao.R;

public class MyDialogUpgradeVip extends Dialog implements View.OnClickListener {
    TextView mDialog;//确定
    ImageView mReturn;//返回
    TextView mTextOne, mTextTwo;
    Context context;
    String mVipMater = "";//返母币
    String mVipSon = "";//返子币
    private OnBtClickListener mOnBtClickListener;

    public MyDialogUpgradeVip(Context context, String mVipMater, String mVipSon) {
        super(context);
        this.context = context;
        this.mVipMater = mVipMater;
        this.mVipSon = mVipSon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_dialog_upgrade_vip);
        init();
        onClicks();
        onColors();//给text中的字加颜色
        mTextTwo.setText("赠送您" + mVipMater + "母币," + mVipSon + "子币");
    }

    public void init() {
        mDialog = (TextView) findViewById(R.id.dialogvip_determine_id);
        mReturn = (ImageView) findViewById(R.id.dialogvip_return_id2);
        mTextOne = (TextView) findViewById(R.id.dialog_textid1);
        mTextTwo = (TextView) findViewById(R.id.dialog_textid2);


    }

    public void onClicks() {
        mDialog.setOnClickListener(this);
        mReturn.setOnClickListener(this);
    }

    public void onColors() {
        String string01 = mTextOne.getText().toString();
        String string02 = mTextTwo.getText().toString();


        SpannableString spannableString01 = new SpannableString(string01);
        ForegroundColorSpan colorSpan01 = new ForegroundColorSpan(context.getResources().getColor(R.color.colorred));
        spannableString01.setSpan(colorSpan01, 8, spannableString01.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextOne.setText(spannableString01);


        SpannableString spannableString02 = new SpannableString(string02);
        ForegroundColorSpan colorSpan02 = new ForegroundColorSpan(context.getResources().getColor(R.color.colorred));
        spannableString02.setSpan(colorSpan02, 3, spannableString02.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextTwo.setText(spannableString02);


    }

    public void setOnBtClickListener(OnBtClickListener onBtClickListener) {
        mOnBtClickListener = onBtClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogvip_determine_id://确定
                if(mOnBtClickListener!=null){
                    mOnBtClickListener.OnBtCLickListener(R.id.dialogvip_determine_id);
                }else {
                    dismiss();
                }
                break;
            case R.id.dialogvip_return_id2://返回
                if(mOnBtClickListener!=null){
                    mOnBtClickListener.OnBtCLickListener(R.id.dialogvip_return_id2);
                }else {
                    dismiss();
                }
                break;
        }
    }

    public interface OnBtClickListener{
        void OnBtCLickListener(int id);
    }
}

package com.linkhand.fenxiao.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.linkhand.fenxiao.R;

/********************************************************************
 @version: 1.0.0
 @description: 各个说明的弹窗
 @author: 杨帆
 @time: 2018/8/10 17:46
 @变更历史:
 ********************************************************************/

public class EveryDialog {
    private TextView mtv_title;
    private TextView mtv_content;
    private TextView mtv_sure;
    private TextView mtv_cancle;
    private ScrollView mscroll;
    private OnTvCancelListener mCancelListener;


    public EveryDialog showRemind(Context context, String leftContent, String rightContent, String title, String content, final OnTvClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_every, null);
        mtv_title = (TextView) dialogV.findViewById(R.id.dialog_title);
        mtv_content = (TextView) dialogV.findViewById(R.id.dialog_content);
        mtv_sure = (TextView) dialogV.findViewById(R.id.dialog_never);
        mtv_cancle = (TextView) dialogV.findViewById(R.id.dialog_know);
        mscroll = (ScrollView) dialogV.findViewById(R.id.dialog_scroll);
        mtv_sure.setText(leftContent);
        mtv_cancle.setText(rightContent);
        mtv_title.setText(title);
        mtv_content.setText(content);

        ViewGroup.LayoutParams layoutParams = mscroll.getLayoutParams();
        if (content.length() > 320) {
            layoutParams.height=700;
            mscroll.setLayoutParams(layoutParams);
        }

        mtv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnSureClickListener();
                }
                dialog.dismiss();
            }
        });
        if (leftContent.equals("")) {
            mtv_cancle.setVisibility(View.GONE);
        }
        if (rightContent.equals("")) {
            mtv_sure.setVisibility(View.GONE);
        }
        mtv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelListener != null) {
                    mCancelListener.OnTvCancelListener();
                }
                dialog.dismiss();
            }
        });
        dialog.setView(dialogV);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        return this;
    }

    public void setCancelListener(OnTvCancelListener cancelListener) {
        mCancelListener = cancelListener;
    }

    public interface OnTvCancelListener {
        void OnTvCancelListener();
    }

    public interface OnTvClickListener {
        void OnSureClickListener();
    }
}

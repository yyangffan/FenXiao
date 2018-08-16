package com.linkhand.fenxiao.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.fenxiao.R;

/**
 * Created by user on 2018/5/5.
 */

public class ShowRemindDialog {
    TextView mDialog;//确认
    private TextView mtv_title;
    private TextView mtv_content;
    private TextView mtv_sure;
    private TextView mtv_cancle;
    private TextView mtv_note;
    private ImageView mimgv;
    private LinearLayout mLinearLayout;
    private LinearLayout mLayout_two;
    private OnTvCancelListener mCancelListener;


    public ShowRemindDialog showRemind(Context context, String rightContent, String leftContent, String title, String content, int imgv_id, final OnTvClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_remind, null);
        mDialog = (TextView) dialogV.findViewById(R.id.dialog_confirm_id);
        mtv_title = (TextView) dialogV.findViewById(R.id.mydialog_title);
        mtv_content = (TextView) dialogV.findViewById(R.id.config_notice_id);
        mtv_sure = (TextView) dialogV.findViewById(R.id.dialog_login_id);
        mtv_cancle = (TextView) dialogV.findViewById(R.id.dialog_login_id2);
        mLinearLayout = (LinearLayout) dialogV.findViewById(R.id.dialog_llayout_id);
        mLayout_two = (LinearLayout) dialogV.findViewById(R.id.dialog_ll_two);
        mimgv = (ImageView) dialogV.findViewById(R.id.dilaog_imgv);
        mtv_note = (TextView) dialogV.findViewById(R.id.dilaog_note);
        mDialog.setVisibility(View.GONE);
        mLinearLayout.setVisibility(View.VISIBLE);
        mLayout_two.setVisibility(View.VISIBLE);
        mtv_sure.setText(rightContent);
        mtv_cancle.setText(leftContent);
        mtv_title.setText(title);
        mtv_content.setText(content);
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
        mtv_note.setText(content);
        mimgv.setImageResource(imgv_id);
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

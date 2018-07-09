package com.linkhand.fenxiao.dialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.linkhand.fenxiao.R;

/**
 * Created by 杨帆 on 2018/7/6.
 */

public class LoadingPop {
    private Activity mContext;
    private PopupWindow mPopupWindow;

    public LoadingPop(Activity context) {
        mContext = context;
    }


    /*加载Loading 如下两个*/
    public void showLoadPop() {
        mPopupWindow = new PopupWindow(LayoutInflater.from(mContext).inflate(R.layout.layout_load_popup, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(mContext.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);

    }

    public void hideLoadPop() {
        if(mPopupWindow!=null){
            mPopupWindow.dismiss();
        }
    }
}

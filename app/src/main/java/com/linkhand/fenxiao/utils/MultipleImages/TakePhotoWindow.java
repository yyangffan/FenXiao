package com.linkhand.fenxiao.utils.MultipleImages;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.linkhand.fenxiao.R;


/**
 * Created by user on 2017/6/16.
 */

public class TakePhotoWindow extends PopupWindow {

    private final View mView;
    private TakePhotoListener takePhotoListener;
    private SelectPhotoListener selectPhotoListener;

    public TakePhotoWindow(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.layout_popupwindow, null);
        TextView btn_camera = (TextView) mView
                .findViewById(R.id.btn_camera);
        TextView btn_photo = (TextView) mView
                .findViewById(R.id.btn_photo);
        TextView btn_cancel = (TextView) mView
                .findViewById(R.id.btn_cancel);
        btn_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                takePhotoListener.takePhoto();
                dismiss();
            }
        });
        btn_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectPhotoListener.selectPhoto();
                dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(mView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAlphaAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x5e000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    public interface TakePhotoListener {
        void takePhoto();
    }

    public interface SelectPhotoListener {
        void selectPhoto();
    }

    public void setTakePhotoListener(TakePhotoListener takePhotoListener) {
        this.takePhotoListener = takePhotoListener;
    }
    public void setSelectPhotoListener(SelectPhotoListener selectPhotoListener){
        this.selectPhotoListener = selectPhotoListener;
    }
}

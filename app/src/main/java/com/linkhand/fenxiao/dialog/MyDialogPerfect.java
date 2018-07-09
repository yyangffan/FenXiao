package com.linkhand.fenxiao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.linkhand.fenxiao.R;

public class MyDialogPerfect extends Dialog{
    EditText minput;
    EditText minputIdentity;//身份
    Context context;
    int ints;//1 修改名称    2修改手机号  3修改身份证号码  4修改真实姓名

    public MyDialogPerfect(Context context, int ints) {
        super(context);
        this.context = context;
        this.ints = ints;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_dialog_perfect);
        initView();
        onGuangBiao();//光标隐藏
    }

    public void initView() {

        minput = (EditText) findViewById(R.id.dialog_input);
        minputIdentity = (EditText) findViewById(R.id.dialog_input_identity);

        if (ints == 1) {//1 修改名称    2修改手机号  3修改身份证号码  4修改真实姓名
            minput.setHint("请输入您的昵称");
            minput.setMaxLines(1);
            minput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)}); //最大输入长度
        } else if (ints == 2) {
            minput.setHint("请输入您的手机号码");
            minput.setInputType(InputType.TYPE_CLASS_NUMBER);
            minput.setMaxLines(1);
            minput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //最大输入长度
        } else if (ints == 3) {
            minputIdentity.setVisibility(View.VISIBLE);
            minput.setVisibility(View.GONE);
            minputIdentity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)}); //最大输入长度
        } else if (ints == 4) {
            minput.setHint("请输入您的真实姓名");
            minput.setMaxLines(1);
            minput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)}); //最大输入长度
        }


    }

    public void onGuangBiao() {
        minput.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
// 编辑框设置触摸监听
        minput.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    minput.setCursorVisible(true);// 再次点击显示光标
                }

                return false;
            }

        });
    }
}

package com.linkhand.fenxiao.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.dialog.InstructionDialog;

public class MyTeamActivity extends BaseActicity implements View.OnClickListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    int mIsSayOne = 0;//获取是否第一次进首页说明须知  1是提示完了
    LinearLayout mReturn;//返回
    Intent intent;
    LinearLayout mGoldMedalLayout;//金牌layout
    LinearLayout mSilverMedalLayout;//银牌layout
    LinearLayout mCopperMedalLayout;//铜牌layout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        init();
        onClicks();
        if (mIsSayOne == 0) {
            onAnnouncement();//说明须知
        }

    }


    public void init() {
        mReturn = (LinearLayout) findViewById(R.id.fenxiao_return_id6);//返回
        mGoldMedalLayout = (LinearLayout) findViewById(R.id.gold_medal_llayout_id);//金牌layout
        mSilverMedalLayout = (LinearLayout) findViewById(R.id.silver_medal_llayout_id);//银牌layout
        mCopperMedalLayout = (LinearLayout) findViewById(R.id.copper_medal_llayout_id);//铜牌layout

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);
        //获取是否第一次进首页instructions须知  1是提示完了
        mIsSayOne = preferences.getInt("SayInstructions", 0);
        Log.e("yh", "mIsSayOne--" + mIsSayOne);

    }

    public void onClicks() {
        mReturn.setOnClickListener(this);//返回
        mGoldMedalLayout.setOnClickListener(this);//金牌layout
        mSilverMedalLayout.setOnClickListener(this);//银牌layout
        mCopperMedalLayout.setOnClickListener(this);//铜牌layout
    }


    public void onAnnouncement() {//说明须知
        InstructionDialog dialog = new InstructionDialog(this);
        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
        //存入instructions须知  1是提示完了
        editor.putInt("SayInstructions", 1);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenxiao_return_id6://返回
                this.finish();
//                intent = new Intent(MyTeamActivity.this, MyOrderActivity.class);
//                startActivity(intent);
                break;

            case R.id.gold_medal_llayout_id://金牌layout
                intent = new Intent(MyTeamActivity.this, MedalTeamActivity.class);
                startActivity(intent);
                //存入点击的牌  1金牌layout    2银牌layout    3铜牌layout
                editor.putInt("medal", 1);
                editor.commit();
                break;

            case R.id.silver_medal_llayout_id://银牌layout
                intent = new Intent(MyTeamActivity.this, MedalTeamActivity.class);
                startActivity(intent);
                //存入点击的牌  1金牌layout    2银牌layout    3铜牌layout
                editor.putInt("medal", 2);
                editor.commit();
                break;
            case R.id.copper_medal_llayout_id://铜牌layout
                intent = new Intent(MyTeamActivity.this, MedalTeamActivity.class);
                startActivity(intent);
                //存入点击的牌  1金牌layout    2银牌layout    3铜牌layout
                editor.putInt("medal", 3);
                editor.commit();
                break;
        }
    }
}

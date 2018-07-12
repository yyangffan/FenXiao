package com.linkhand.fenxiao.activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.linkhand.fenxiao.AppManager;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.login.LoginActivity;
import com.linkhand.fenxiao.fragment.ClassificationFragment;
import com.linkhand.fenxiao.fragment.ExchangeFragment;
import com.linkhand.fenxiao.fragment.HomePageFragment;
import com.linkhand.fenxiao.fragment.MineFragment;
import com.linkhand.fenxiao.fragment.shopping.ShoppingFragment;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActicity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ShoppingFragment mShoppingFragment;
    private RadioGroup mrg;
    private RadioButton mrbone;
    private RadioButton mrbtwo;
    private RadioButton mrbthree;
    private RadioButton mrbfour;
    private RadioButton mrbfive;
    private FragmentManager mFragmentManager;
    private List<Fragment> mList;
    private List<RadioButton> mRadioButtons;
    private MineFragment mMineFragment;
    private HomePageFragment mHomePageFragment;
    private static final String TAG = "HomePageActivity";
    private ExchangeFragment mExchangeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mFragmentManager = this.getSupportFragmentManager();
        init();

    }

    public void init() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mList = new ArrayList<>();
        mRadioButtons = new ArrayList<>();
        mrbone = (RadioButton) findViewById(R.id.homepage_rbone);
        mrbtwo = (RadioButton) findViewById(R.id.homepage_rbtwo);
        mrbthree = (RadioButton) findViewById(R.id.homepage_rbthree);
        mrbfour = (RadioButton) findViewById(R.id.homepage_rbfour);
        mrbfive = (RadioButton) findViewById(R.id.homepage_rbfive);
        mrg = (RadioGroup) findViewById(R.id.homepage_rg);
        mShoppingFragment = new ShoppingFragment();
        mMineFragment = new MineFragment();
        mHomePageFragment = new HomePageFragment();
        mList.add(mHomePageFragment);//首页
        mList.add(new ClassificationFragment());//分类
        mExchangeFragment = new ExchangeFragment();
        mList.add(mExchangeFragment);//购物券兑换
        mList.add(mShoppingFragment);//购物车
        mList.add(mMineFragment);//我的
        mRadioButtons.add(mrbone);
        mRadioButtons.add(mrbtwo);
        mRadioButtons.add(mrbthree);
        mRadioButtons.add(mrbfour);
        mRadioButtons.add(mrbfive);
        Intent intent = this.getIntent();
        int what = 0;
        if (intent != null) {
            String isWhat = intent.getStringExtra("what");
            if (isWhat != null) {
                what = Integer.parseInt(isWhat);
            }
        }
        toChangeWghat(what);

        mrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.homepage_rbone) {
                    toChangeWghat(0);
                } else {
                    if (mUserId.equals("")) {//是否登录
                        onIsLogin();//去登陆
                        mrbone.setChecked(true);
                        return;
                    }
                    switch (checkedId) {
                        case R.id.homepage_rbtwo:
                            toChangeWghat(1);
                            break;
                        case R.id.homepage_rbthree:
                            toChangeWghat(2);
                            break;
                        case R.id.homepage_rbfour:
                            toChangeWghat(3);
                            break;
                        case R.id.homepage_rbfive:
                            toChangeWghat(4);
                            break;
                    }

                }
            }
        });
    }

    public void toChangeWghat(int what) {
        mFragmentManager.beginTransaction().replace(R.id.main_framelayoutmain_id, mList.get(what)).commitAllowingStateLoss();
        for (int i = 0; i < mRadioButtons.size(); i++) {
            RadioButton radioButton = mRadioButtons.get(i);
            if (i == what) {
                radioButton.setChecked(true);
                radioButton.setTextColor(this.getResources().getColor(R.color.colorred));
            } else {
                radioButton.setTextColor(this.getResources().getColor(R.color.gray_text));
            }
        }
    }

    public void onIsLogin() {//登录注册去
        Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
        this.startActivity(intent);
//        MyDialogLogin dialog = new MyDialogLogin(this);
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
//        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.remove("isClick").commit();//订单
        editor.remove("isDeckClick").commit();//奖牌
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mUserId = preferences.getString("user_id", "");
        if (mUserId.equals("")) {
            toChangeWghat(0);
        }
        Intent intent = this.getIntent();
        int what = Integer.parseInt(preferences.getString("what", "0"));
        if(what!=0) {
            toChangeWghat(what);
        }
        if (mHomePageFragment != null && mHomePageFragment.isVisible()) {
            mHomePageFragment.OnRefresh();
        }
        if (mShoppingFragment != null && mShoppingFragment.isVisible()) {
            mShoppingFragment.onMessage(0);
        }
        if (mMineFragment != null && mMineFragment.isVisible()) {
            mMineFragment.refresh();
        }
        if (mExchangeFragment != null) {
            mExchangeFragment.onRefresh();
        }
        editor.putString("what","0");
        editor.commit();

    }

    long starT = 0;
    long endT = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            starT = System.currentTimeMillis();
            if (starT - endT >= 2000) {
                //存入返回判断  1不提示
                String mUserId = preferences.getString("keyDown", "0");
                if (mUserId.equals("0")) {
                    Toast.makeText(this, "双击退出", Toast.LENGTH_SHORT).show();
                    endT = starT;
                } else {
                    editor.remove("keyDown").commit();  //存入返回判断  1不提示
                    return super.onKeyDown(keyCode, event);
                }
                return false;
            }
            AppManager.getAppManager().AppExit(this);
            System.exit(0);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (mMineFragment != null) {
                        mMineFragment.setHeadImgv(selectList.get(0).getCutPath());
                    }
                    Log.e(TAG, "onActivityResult: " + selectList.get(0).getCutPath());
                    break;
            }
        }


    }
}

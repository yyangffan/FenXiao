package com.linkhand.fenxiao.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.OpenGroupActivity;
import com.linkhand.fenxiao.activity.login.LoginActivity;
import com.linkhand.fenxiao.adapter.fenlei.MyAdapter;
import com.linkhand.fenxiao.feng.fenlei.Category;
import com.linkhand.fenxiao.feng.fenlei.LeftClassFeng;
import com.linkhand.fenxiao.fragment.fenlei.JDFragment;
import com.linkhand.fenxiao.info.InfoData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 分类
 * A simple {@link Fragment} subclass.
 */
public class ClassificationFragment extends BaseFragment implements View.OnClickListener {
    ListView mLeftListView;//左
    FrameLayout mRightListView;//右
    EditText mCheckClass;//搜索内容
    ImageView mSearchClass;//搜索

    private List<Category> itemList = new ArrayList<Category>();
    private MyAdapter adapter = null;
    //可见列表项的数量
    private int visibleCount = 0;
    //上次点击的位置
    private int lastPosition = 0;
    private int ce = 0;
    //实际列表是否超出屏幕
    private boolean isOut = true;
    private JDFragment fragment = null;

    InfoData service;
    String cate_id = "";//左侧类别id

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    String mUserIsVip;//是否vip  0否  1是


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_classification, container, false);
        initView(v);
        onClicks();
        initRetrofit();//初始化retrofit
        onGuangBiao();
        onMessage();//一级分类（左侧列表）
//        adapterUpdate();
        return v;
    }

    public void initView(View v) {
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();

        editor.remove("keyDown").commit();  //存入返回判断  1不提示
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        //是否vip  0否  1是
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
        mCheckClass = (EditText) v.findViewById(R.id.check_class_id);
        mSearchClass = (ImageView) v.findViewById(R.id.search_class_id);
        mLeftListView = (ListView) v.findViewById(R.id.fenlei_lv_id);
        mRightListView = (FrameLayout) v.findViewById(R.id.fenlei_flayout_id);
    }

    public void onClicks() {
        mSearchClass.setOnClickListener(ClassificationFragment.this);//搜索
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_class_id://搜索
                onSearch();//搜索
                break;
        }
    }

    public void onSearch() {//搜索
        //是否vip  0否  1是
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
        Log.e("yh", "是否vip--" + mUserIsVip);
        if (mUserId.equals("")) {//是否登录
            onLogin();//去登陆
        } else {
            String cntent = mCheckClass.getText() + "";
            Log.e("yh", "cntent--" + cntent);
            if (!cntent.equals("")) {
                Log.e("yh", "搜索内容--" + cntent);
                Intent intent = new Intent(ClassificationFragment.this.getActivity(), OpenGroupActivity.class);
                intent.putExtra("mClassify_id", "");//分类id
                intent.putExtra("mBrand_id", "");//品牌id
                intent.putExtra("mKeyword_id", cntent);//关键字
                startActivity(intent);
            } else {
                Toast.makeText(ClassificationFragment.this.getActivity(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void adapterUpdate(String cate_id) {

        if (adapter != null) {
            if (cate_id.equals("") | cate_id.equals("null")) {

            } else {
                Log.e("yh", "右");
                //模拟右侧标签页
                fragment = new JDFragment();
                Bundle bundle = new Bundle();
                bundle.putString("cate_id", cate_id);
                fragment.setArguments(bundle);
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fenlei_flayout_id, fragment, "c0").commitAllowingStateLoss();
                adapter.setSelected(0);
                adapter.notifyDataSetChanged();
            }
        }

    }


    public void onMessage() {//一级分类（左侧列表）
        Map<String, Object> map = new HashMap<>();
        Call<LeftClassFeng> call = service.getLeftClass(map);
        call.enqueue(new Callback<LeftClassFeng>() {
            @Override
            public void onResponse(Call<LeftClassFeng> call, Response<LeftClassFeng> response) {
                LeftClassFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    List<LeftClassFeng.InfoBean> beanList = pcfeng.getInfo();
                    cate_id = beanList.get(0).getCate_id();//左侧类别id(这个是默认显示的)
                    adapter = new MyAdapter(ClassificationFragment.this.getActivity(), beanList);
                    mLeftListView.setAdapter(adapter);
                    mLeftListView.setOnItemClickListener(new MyOnItemOnClick());
                    Log.e("yh", "右");
                    if (ClassificationFragment.this.isAdded()) {
                        adapterUpdate(cate_id);//默认显示
                    }
                } else {
                    Toast.makeText(ClassificationFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LeftClassFeng> call, Throwable t) {

            }
        });
    }


    public void onGuangBiao() {
        mCheckClass.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
        // 编辑框设置触摸监听
        mCheckClass.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    mCheckClass.setCursorVisible(true);// 再次点击显示光标
                }

                return false;
            }

        });
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                //把固定不变的url写到这里
                .baseUrl(C.HOSTIP)
                //支持返回字符串,先写字符串
                .addConverterFactory(ScalarsConverterFactory.create())
                //支持解析返回的json，后写json解析
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建一个接口的实现类
        service = retrofit.create(InfoData.class);
    }


    private class MyOnItemOnClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Log.e("yh", "position--" + position + "--id--" + id);
            //计算滑动
            if (visibleCount == 0) {
                visibleCount = mLeftListView.getChildCount();
                if (visibleCount == itemList.size())
                    isOut = false;
                else {
                    ce = visibleCount / 2;
                }
            }

            if (position <= (parent.getFirstVisiblePosition() + ce)) {   //上移
                mLeftListView.smoothScrollToPosition(position - ce);
            } else {   //下移
                if ((parent.getLastVisiblePosition() + ce + 1) <= parent.getCount()) {
                    mLeftListView.smoothScrollToPosition(position + ce);
                } else {
                    mLeftListView.smoothScrollToPosition(parent.getCount() - 1);
                }

            }

            lastPosition = position;
            adapter.setSelected(position);
            adapter.notifyDataSetChanged();

            //更新右侧标签页的标题
            fragment.updateTitle(id + "");
        }
    }

    public void onLogin() {//去登陆
        Intent intent = new Intent(ClassificationFragment.this.getActivity(), LoginActivity.class);//登录
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        //是否vip  0否  1是
        mUserIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
        Log.e("yh", "是否vip--" + mUserIsVip);
//        onMessage();//一级分类（左侧列表）
    }


}

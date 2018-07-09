package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.OpenGroupAdapter;
import com.linkhand.fenxiao.feng.home.GroupListFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.HomeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OpenGroupActivity extends BaseActicity implements View.OnClickListener, HomeInfo {

    @Bind(R.id.open_group_return_id)
    LinearLayout mReturn;//返回
    @Bind(R.id.search_class_id2)
    ImageView mSearch;//关键字搜索
    @Bind(R.id.check_class_id3)
    EditText mCheck;
    @Bind(R.id.open_group_lv_id)
    ListView mListView;
    @Bind(R.id.open_title)
    TextView mOpenTitle;

    InfoData service;
    OpenGroupAdapter mAdapter;
    String mClassify = "";//分类id
    String mBrand = "";//品牌id
    String mKeyword = "";//搜索关键字

    private String mIs_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_group);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
        onGuangBiao();//隐藏光标
        onMessage();
    }

    public void initView() {
        mReturn.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            mClassify = intent.getStringExtra("mClassify_id"); //分类id
            mBrand = intent.getStringExtra("mBrand_id"); //品牌id
            mKeyword = intent.getStringExtra("mKeyword_id"); //关键字
            Log.e("yh", "分类id---" + mClassify + "--品牌id--" + mBrand + "--搜索关键字--" + mKeyword);
            if (mClassify == null) {
                mClassify = "";
            }
            if (mBrand == null) {
                mBrand = "";
            }
            if (mKeyword == null) {
                mKeyword = "";
            }
            mCheck.setText(mKeyword);
            mIs_type = intent.getStringExtra("is_type");
        }
        if (mIs_type != null && mIs_type.equals("2")) {
            mOpenTitle.setText("金牌专区");
        } else {
            mOpenTitle.setText("开团专区");
        }

        mCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mKeyword = "";
                    onMessage();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_group_return_id://返回
                this.finish();
                break;
            case R.id.search_class_id2://关键字搜索
                mKeyword = mCheck.getText() + "";//搜索关键字(赋值)
                onMessage();
                break;
        }
    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        if (!mClassify.equals("")) {
            map.put("cate_id", mClassify);//分类id (可为空)
        }
        if (!mBrand.equals("")) {
            map.put("brand_id", mBrand);//品牌id (可为空)
        }
        if (!mKeyword.equals("")) {
            map.put("serach", mKeyword);//搜索关键字 (可为空)
        }
        if (mIs_type != null && mIs_type.equals("2")) {
            map.put("is_type", "2");
        } else {
            map.put("is_type", "1");
        }
        Call<GroupListFeng> call = service.getGroupList(map);
        call.enqueue(new Callback<GroupListFeng>() {
            @Override
            public void onResponse(Call<GroupListFeng> call, Response<GroupListFeng> response) {
                GroupListFeng pcfeng = response.body();
                int code = pcfeng.getCode();
                if (code == 100) {
                    List<GroupListFeng.InfoBean> beanList = pcfeng.getInfo();
                    mAdapter = new OpenGroupAdapter(OpenGroupActivity.this, beanList);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setOnItemClicks(OpenGroupActivity.this);
                } else {
                    mAdapter = new OpenGroupAdapter(OpenGroupActivity.this, new ArrayList<GroupListFeng.InfoBean>());
                    mListView.setAdapter(mAdapter);
                    Toast.makeText(OpenGroupActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GroupListFeng> call, Throwable t) {

            }
        });
    }

    public void onGuangBiao() {
        mCheck.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
        // 编辑框设置触摸监听
        mCheck.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    mCheck.setCursorVisible(true);// 再次点击显示光标
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

    @Override
    public void onItemClicks(RelativeLayout mRelativeLayout, final List<Map<String, Object>> list) {
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = (String) list.get(0).get("id");//(商品id)
                Intent intent = new Intent(OpenGroupActivity.this, DetailPageActivity.class);
                intent.putExtra("good_id", id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onMessage();
    }
}

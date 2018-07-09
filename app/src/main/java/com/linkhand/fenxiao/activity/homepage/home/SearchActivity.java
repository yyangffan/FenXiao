package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.IntentionAdapter;
import com.linkhand.fenxiao.adapter.home.OpenGroupAdapter;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.home.GroupListFeng;
import com.linkhand.fenxiao.feng.home.IntentionGoods;
import com.linkhand.fenxiao.info.callback.DetailsInfo;
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


public class SearchActivity extends BaseActicity implements View.OnClickListener, DetailsInfo, HomeInfo {

    @Bind(R.id.fenxiao_return_id)
    LinearLayout mReturn;
    @Bind(R.id.tabLayout_id)
    TabLayout mTabLayoutId;
    @Bind(R.id.search_lv)
    ListView mSearchLv;
    @Bind(R.id.search_tv_search)
    TextView mSearchTvSearch;
    @Bind(R.id.check_class_id)
    EditText mCheckClassId;

    private int type = 0;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id

    OpenGroupAdapter mAdapter;/*普通商品*/
    IntentionAdapter mAdapter_yixiang;/*意向商品*/
    private List<GroupListFeng.InfoBean> mBeanList;
    private List<IntentionGoods.InfoBean> mMBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
//        StatusBarUtils.setWindowStatusBarColor(this, R.color.colorwhites);//状态栏
    }

    public void initView() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        mReturn.setOnClickListener(this);
        mSearchTvSearch.setOnClickListener(this);
        mTabLayoutId.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        type = 0;
                        break;
                    case 1:
                        type = 1;
                        break;
                }
                gotoSearch();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mCheckClassId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    if (type == 0) {
                        mBeanList = new ArrayList<>();
                        mAdapter = new OpenGroupAdapter(SearchActivity.this, mBeanList);
                        mSearchLv.setAdapter(mAdapter);
                    } else if (type == 1) {
                        mMBean = new ArrayList<>();
                        mAdapter_yixiang = new IntentionAdapter(SearchActivity.this, mMBean);
                        mSearchLv.setAdapter(mAdapter_yixiang);
                    }
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
            case R.id.fenxiao_return_id:
                this.finish();
                break;
            case R.id.search_tv_search:
                gotoSearch();
                break;
        }
    }

    public void gotoSearch() {
        String search_content = mCheckClassId.getText().toString();
        if (search_content == null || search_content.equals("")) {
            Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
            return;
        }
        if (type == 0) {//普通商品
            onMessage(search_content);
        } else if (type == 1) {//意向商品
            onMessage_yx(search_content);
        }
    }

    /*普通商品*/
    public void onMessage(String keyword) {
        Map<String, Object> map = new HashMap<>();
        map.put("serach", keyword);
        Call<GroupListFeng> call = service.getGroupList(map);
        call.enqueue(new Callback<GroupListFeng>() {
            @Override
            public void onResponse(Call<GroupListFeng> call, Response<GroupListFeng> response) {
                GroupListFeng pcfeng = response.body();
                int code = pcfeng.getCode();
                if (code == 100) {
                    mBeanList = pcfeng.getInfo();
                    mAdapter = new OpenGroupAdapter(SearchActivity.this, mBeanList);
                    mSearchLv.setAdapter(mAdapter);
                    mAdapter.setOnItemClicks(SearchActivity.this);
                } else {
                    mBeanList = new ArrayList<>();
                    mAdapter = new OpenGroupAdapter(SearchActivity.this, mBeanList);
                    mSearchLv.setAdapter(mAdapter);
                    Toast.makeText(SearchActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GroupListFeng> call, Throwable t) {

            }
        });
    }

    /*意向商品*/
    public void onMessage_yx(String keyword) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("search", keyword);
        Call<IntentionGoods> call = service.getIntention(map);
        call.enqueue(new Callback<IntentionGoods>() {
            @Override
            public void onResponse(Call<IntentionGoods> call, Response<IntentionGoods> response) {
                IntentionGoods pcfeng = response.body();
                int code = pcfeng.getCode();
                if (code == 100) {
                    mMBean = pcfeng.getInfo();
                    mAdapter_yixiang = new IntentionAdapter(SearchActivity.this, mMBean);
                    mSearchLv.setAdapter(mAdapter_yixiang);
                    mAdapter_yixiang.setOnItemClicks(SearchActivity.this);
                } else {
                    mMBean = new ArrayList<>();
                    mAdapter_yixiang = new IntentionAdapter(SearchActivity.this, mMBean);
                    mSearchLv.setAdapter(mAdapter_yixiang);
//                    Toast.makeText(IntentionActivity.this, "xx", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IntentionGoods> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        gotoSearch();
    }

    public void onCancelOrder(String idea_id) {//取消订购（取消意向）
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("idea_id", idea_id);
        Call<ReturnFeng> call = service.getDeleteIntention(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(SearchActivity.this, success, Toast.LENGTH_SHORT).show();
                    gotoSearch();//0正常查   1更新数据
                } else {
                    Toast.makeText(SearchActivity.this, success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    public void onOrder(String idea_id) {//立即订购(关注意向)
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("idea_id", idea_id);
        Call<ReturnFeng> call = service.getAddIntention(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(SearchActivity.this, success, Toast.LENGTH_SHORT).show();
                    gotoSearch();//0正常查   1更新数据
                } else {
                    Toast.makeText(SearchActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemDetailsClicks(LinearLayout mLinearLayout, TextView mTextView, final List<Map<String, Object>> list) {
        mLinearLayout.setOnClickListener(new View.OnClickListener() {//进详情页
            @Override
            public void onClick(View v) {
                String idea_id = list.get(0).get("idea_id").toString();//商品id
                String is_have = list.get(0).get("is_have").toString();//是否订购(是否已关注 1是 0否)

                Intent intent = new Intent(SearchActivity.this, InDetailsActivity.class);
                intent.putExtra("idea_id", idea_id);
                startActivity(intent);


            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {//订购
            @Override
            public void onClick(View v) {
                String idea_id = list.get(0).get("idea_id").toString();//商品id
                String is_have = list.get(0).get("is_have").toString();//是否订购(是否已关注 1是 0否)
                if (is_have.equals("1")) {
                    onCancelOrder(idea_id);//取消订购
                } else if (is_have.equals("0")) {
                    onOrder(idea_id);//立即订购
                }
            }
        });
    }

    @Override
    public void onItemClicks(RelativeLayout mRelativeLayout, final List<Map<String, Object>> list) {
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {//详情页
            @Override
            public void onClick(View v) {
                String id = list.get(0).get("id").toString();//商品id
                Intent intent = new Intent(SearchActivity.this, DetailPageActivity.class);
                intent.putExtra("good_id", id);
                startActivity(intent);
            }
        });

    }
}

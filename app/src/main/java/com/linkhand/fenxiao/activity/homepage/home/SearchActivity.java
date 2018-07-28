package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.TuanGRcAdapter;
import com.linkhand.fenxiao.adapter.home.IntentionAdapter;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.home.GroupListFeng;
import com.linkhand.fenxiao.feng.home.IntentionGoods;
import com.linkhand.fenxiao.fragment.DividerGridItemDecoration;
import com.linkhand.fenxiao.info.callback.DetailsInfo;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.utils.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends BaseActicity implements View.OnClickListener, DetailsInfo {

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
    @Bind(R.id.search_recy)
    RecyclerView mSearchRecy;

    private int type = 0;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id

    IntentionAdapter mAdapter_yixiang;/*意向商品*/
    private List<IntentionGoods.InfoBean> mMBean;

    private List<GroupListFeng.InfoBean> mInfoBeanList;
    private TuanGRcAdapter mTuanGRcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initEver();
        initView();
    }

    public void initEver() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mSearchRecy.setLayoutManager(gridLayoutManager);
        mSearchRecy.addItemDecoration(new DividerGridItemDecoration(this, R.drawable.gray_juxing));
        mInfoBeanList = new ArrayList<>();
        mTuanGRcAdapter = new TuanGRcAdapter(this, mInfoBeanList);
        mSearchRecy.setAdapter(mTuanGRcAdapter);
        mTuanGRcAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                String id = mInfoBeanList.get(position).getGood_id();//(商品id)
                Intent intent = new Intent(SearchActivity.this, DetailPageActivity.class);
                intent.putExtra("good_id", id);
                startActivity(intent);
            }
        });
        mMBean = new ArrayList<>();
        mAdapter_yixiang = new IntentionAdapter(this, mMBean);
        mSearchLv.setAdapter(mAdapter_yixiang);
        mAdapter_yixiang.setOnItemClicks(SearchActivity.this);
        mCheckClassId.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    //先隐藏键盘
                    ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    gotoSearch();
                }
                return false;
            }
        });
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
                        mSearchRecy.setVisibility(View.GONE);
                        mSearchLv.setVisibility(View.GONE);
                    } else if (type == 1) {
                        mSearchRecy.setVisibility(View.GONE);
                        mSearchLv.setVisibility(View.GONE);
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
            mSearchRecy.setVisibility(View.VISIBLE);
            mSearchLv.setVisibility(View.GONE);
            onMessage(search_content);
        } else if (type == 1) {//意向商品
            mSearchRecy.setVisibility(View.GONE);
            mSearchLv.setVisibility(View.VISIBLE);
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
                mInfoBeanList.clear();
                if (code == 100) {
                    mInfoBeanList.addAll(pcfeng.getInfo());
                } else {
                    Toast.makeText(SearchActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
                }
                mTuanGRcAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GroupListFeng> call, Throwable t) {
                ToastUtil.showToast(SearchActivity.this,"网络异常");
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
                mMBean.clear();
                if (code == 100) {
                    mMBean.addAll(pcfeng.getInfo());
                } else {
                    Toast.makeText(SearchActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
                }
                mAdapter_yixiang.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<IntentionGoods> call, Throwable t) {
                ToastUtil.showToast(SearchActivity.this,"网络异常");
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
}

package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.MyRecyAdapter;
import com.linkhand.fenxiao.adapter.home.MienAdapter;
import com.linkhand.fenxiao.bean.MienBean;
import com.linkhand.fenxiao.dialog.EveryDialog;
import com.linkhand.fenxiao.feng.home.FenCaiFlBean;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.utils.ScrollSpeedLinearLayoutManger;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MienActivity extends BaseActicity {

    @Bind(R.id.open_group_return_id)
    LinearLayout mReturn;
    @Bind(R.id.open_group_lv_id)
    ListView mListView;
    @Bind(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;
    @Bind(R.id.mine_fc_recy)
    RecyclerView mMineFcRecy;
    @Bind(R.id.imgv_what)
    ImageView mImgvWhat;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    MienAdapter mAdapter;
    List<MienBean.InfoBean> mList;//测试数据

    private List<Map<String, Object>> mListMap;
    private MyRecyAdapter mRecyAdapter;
    private static final String TAG = "MienActivity";
    private int mScreenWidth;
    private int mWidth = 0;
    private int page = 0;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mien);
        ButterKnife.bind(this);
        initView();
        getCatCount();
//        onMessage();
        getAndroiodScreenProperty();
        if(preferences.getBoolean("fengcai",true)) {
            getMessage();
        }

    }

    public void initView() {
        mList = new ArrayList<>();
        mAdapter = new MienAdapter(MienActivity.this, mList);
        mListView.setAdapter(mAdapter);

        preferences = MienActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ar_id = mList.get(position).getAr_id();
                Intent intent = new Intent(MienActivity.this, MienDetailsAcyivity.class);//详情页
                intent.putExtra("ar_id", ar_id);
                startActivity(intent);
            }
        });
        /*上面的标题栏*/
        mListMap = new ArrayList<>();
        ScrollSpeedLinearLayoutManger manager = new ScrollSpeedLinearLayoutManger(this);
        manager.setOrientation(ScrollSpeedLinearLayoutManger.HORIZONTAL);
        mMineFcRecy.setLayoutManager(manager);

        mRecyAdapter = new MyRecyAdapter(this, mListMap);
        mMineFcRecy.setAdapter(mRecyAdapter);
        mRecyAdapter.setOnTitleListener(new MyRecyAdapter.OnTitlClickListener() {
            @Override
            public void OnTitleClickListener(int position, float x) {
                Log.e(TAG, "OnTitleClickListener:x= " + x + "  mWidth/2=" + mScreenWidth / 2);
                for (int i = 0; i < mListMap.size(); i++) {
                    if (i == position) {
                        mListMap.get(i).put("isSelect", true);
                    } else {
                        mListMap.get(i).put("isSelect", false);
                    }
                }
                mRecyAdapter.notifyDataSetChanged();
                if (x > (mWidth / 2)) {
                    if (position != (mListMap.size() - 1)) {
//                        if (position != (mListMap.size() - 2)) {
//                            mMineFcRecy.smoothScrollToPosition(position + 2);
//                        } else {
                        mMineFcRecy.smoothScrollToPosition(position + 1);
//                        }
                    }
                } else {
                    if (position != 0) {
//                        if (position != 1) {
//                            mMineFcRecy.smoothScrollToPosition(position - 2);
//                        } else {
                        mMineFcRecy.smoothScrollToPosition(position - 1);
//                        }
                    }
                }
                mId = (String) mListMap.get(position).get("id");
                mSmartRefresh.autoRefresh();
                page = 0;
                onMessage(mId);
            }
        });
//        mSmartRefresh.setEnableRefresh(false);
//        mSmartRefresh.setEnableLoadmore(false);
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                onMessage(mId);
            }
        });
        mSmartRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++page;
                onMessage(mId);
            }
        });

    }

    /*获取屏幕参数*/
    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 屏幕宽度（像素）
        mWidth = dm.widthPixels;
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // 屏幕宽度(dp)
        mScreenWidth = (int) (mWidth / density);
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
    }

    @OnClick({R.id.open_group_return_id,R.id.imgv_what})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_group_return_id:
                this.finish();
                break;
            case R.id.imgv_what:
                getMessage();
                break;
        }
    }

    /*获取说明内容*/
    public void getMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("get_type", "6");
        Call<HttpResponse> call = service.getDescGet(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    new EveryDialog().showRemind(MienActivity.this, "（不在提示）", "确定", "说明", httpResponse.getInfo(), new EveryDialog.OnTvClickListener() {
                        @Override
                        public void OnSureClickListener() {
                            editor.putBoolean("fengcai",false).commit();
                        }
                    });

                } else {
                    ToastUtil.showToast(MienActivity.this, httpResponse.getSuccess());
                }

            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(MienActivity.this, "网络异常");
            }
        });
    }


    public void getCatCount() {
        Map<String, Object> mapp = new HashMap<>();
        Call<FenCaiFlBean> call = service.getCateGet(mapp);
        call.enqueue(new Callback<FenCaiFlBean>() {
            @Override
            public void onResponse(Call<FenCaiFlBean> call, Response<FenCaiFlBean> response) {
                FenCaiFlBean fenCaiFlBean = response.body();
                mListMap.clear();
                if (fenCaiFlBean.getCode() == 100) {
                    for (int i = 0; i < fenCaiFlBean.getInfo().size(); i++) {
                        FenCaiFlBean.InfoBean infoBean = fenCaiFlBean.getInfo().get(i);
                        Map<String, Object> map = new HashMap<>();
                        map.put("title", infoBean.getArt_cat_name());
                        map.put("id", infoBean.getArt_cat_id());
                        if (i == 0) {
                            map.put("isSelect", true);
                        } else {
                            map.put("isSelect", false);
                        }
                        mListMap.add(map);
                    }
                    mRecyAdapter.notifyDataSetChanged();
                    mId = fenCaiFlBean.getInfo().get(0).getArt_cat_id();
                    mSmartRefresh.autoRefresh();
                }

            }

            @Override
            public void onFailure(Call<FenCaiFlBean> call, Throwable t) {
                ToastUtil.showToast(MienActivity.this, "网络异常");
            }
        });
    }

    public void onMessage(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("art_cat_id", id);
        map.put("pag", page);
        Call<MienBean> call = service.getMienMsg(map);
        call.enqueue(new Callback<MienBean>() {
            @Override
            public void onResponse(Call<MienBean> call, Response<MienBean> response) {
                mSmartRefresh.finishRefresh();
                mSmartRefresh.finishLoadmore();
                MienBean mienBean = response.body();
                if (mienBean.getCode() == 100) {
                    if (page == 0) {
                        mList.clear();
                        mList.addAll(mienBean.getInfo());
                    } else {
                        for (MienBean.InfoBean infoBean : mienBean.getInfo()) {
                            mList.add(infoBean);
                        }
                    }
                } else {
                    if (page == 0) {
                        mList.clear();
                    }
                    Toast.makeText(MienActivity.this, mienBean.getSuccess(), Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MienBean> call, Throwable t) {
                mSmartRefresh.finishRefresh();
                mSmartRefresh.finishLoadmore();
                Toast.makeText(MienActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mSmartRefresh.autoRefresh();
    }
}

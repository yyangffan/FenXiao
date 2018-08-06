package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.KaQuanRecyAdapter;
import com.linkhand.fenxiao.adapter.home.AreaAdapter;
import com.linkhand.fenxiao.adapter.home.CityAdapter;
import com.linkhand.fenxiao.adapter.home.ProvinceAdapter;
import com.linkhand.fenxiao.feng.home.CityFeng;
import com.linkhand.fenxiao.feng.home.ProvinceFeng;
import com.linkhand.fenxiao.feng.home.VipLvResponse;
import com.linkhand.fenxiao.info.callback.AreaInfo;
import com.linkhand.fenxiao.info.callback.CityInfo;
import com.linkhand.fenxiao.info.callback.ProvinceInfo;
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

public class VipSearchActivity extends BaseActicity implements ProvinceInfo, CityInfo, AreaInfo {

    @Bind(R.id.edt_search)
    EditText mEdtSearch;
    @Bind(R.id.tv_search)
    TextView mTvSearch;
    @Bind(R.id.my_vip_recy)
    RecyclerView mMyVipRecy;
    @Bind(R.id.my_vip_sheng)
    TextView mMyVipSheng;
    @Bind(R.id.my_vip_shi)
    TextView mMyVipShi;
    @Bind(R.id.my_vip_qu)
    TextView mMyVipQu;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private KaQuanRecyAdapter mKaQuanRecyAdapter;
    private List<VipLvResponse.UsedataBean> mUsedataBeenList;

    ProvinceAdapter mProvinceAdapter;//省adapter
    CityAdapter mCityAdapter;//市adapter
    AreaAdapter mAreaAdapter;//区adapter
    String provinceId = "";//省id
    String cityId = "";//市id
    String areaId = "";//区id
    private View mView;
    ListView mPopwListView;
    PopupWindow popupWindow;

    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_search);
        ButterKnife.bind(this);
        initEver();
    }

    public void initEver() {
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMyVipRecy.setLayoutManager(linearManager);

        mUsedataBeenList = new ArrayList<>();
        mKaQuanRecyAdapter = new KaQuanRecyAdapter(this, mUsedataBeenList);
        mMyVipRecy.setAdapter(mKaQuanRecyAdapter);
        mKaQuanRecyAdapter.setItemClickListener(new KaQuanRecyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                VipLvResponse.UsedataBean usedataBean = mUsedataBeenList.get(position);
                Intent intent = new Intent(VipSearchActivity.this, KaQuanDetailActivity.class);
                intent.putExtra("uc_id", usedataBean.getUc_id());
                startActivity(intent);
            }
        });
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                getData(mEdtSearch.getText().toString());
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++page;
                getData(mEdtSearch.getText().toString());

            }
        });
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mRefreshLayout.autoRefresh();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    //先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    mRefreshLayout.autoRefresh();
                }
                return false;
            }
        });

    }

    @OnClick({ R.id.tv_search, R.id.my_vip_sheng, R.id.my_vip_shi, R.id.my_vip_qu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                String search_str = mEdtSearch.getText().toString();
                if (search_str != null && search_str.length() == 0) {
                    ToastUtil.showToast(this, "请输入要搜索的内容");
                    return;
                }
                mRefreshLayout.autoRefresh();
                break;
            case R.id.my_vip_sheng:
                onProvincePopupWindow(view, 1);
                break;
            case R.id.my_vip_shi:
                if (provinceId.equals("")) {
                    ToastUtil.showToast(this, "请选择省");
                } else {
                    onProvincePopupWindow(view, 2);
                }
                break;
            case R.id.my_vip_qu:
                if (cityId.equals("")) {
                    ToastUtil.showToast(this, "请选择市");
                } else {
                    onProvincePopupWindow(view, 3);
                }
                break;
        }
    }

    /*获取卡券数据*/
    public void getData(String search_str) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("city1", provinceId);
        map.put("city2", cityId);
        map.put("city3", areaId);
        map.put("search", search_str);
        map.put("pag", page);
        Call<VipLvResponse> call = service.getVipLv(map);
        call.enqueue(new Callback<VipLvResponse>() {
            @Override
            public void onResponse(Call<VipLvResponse> call, Response<VipLvResponse> response) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                VipLvResponse bean = response.body();
                if (bean.getCode() == 100) {
                    if (page == 0) {
                        mUsedataBeenList.clear();
                        mUsedataBeenList.addAll(bean.getUsedata());
                    } else {
                        for (VipLvResponse.UsedataBean usedataBean : bean.getUsedata()) {
                            mUsedataBeenList.add(usedataBean);
                        }
                    }
                    mKaQuanRecyAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showToast(VipSearchActivity.this, bean.getSuccess());
                }
            }

            @Override
            public void onFailure(Call<VipLvResponse> call, Throwable t) {
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
                ToastUtil.showToast(VipSearchActivity.this, "网络异常");
            }
        });

    }

    public void onProvincePopupWindow(View v, int ints) {//省1 市2 区3
        mView = LayoutInflater.from(VipSearchActivity.this).inflate(R.layout.address_item_popw, null);
        mPopwListView = (ListView) mView.findViewById(R.id.popw_id);
        popupWindow = new PopupWindow(mView);
        int width = v.getWidth();
        int height = 600;
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(v);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        if (ints == 1) {
            onProvinceMessage();//省
        } else if (ints == 2) {
            onProvinceMessageCity(provinceId);//市
        } else if (ints == 3) {
            onProvinceMessageArea(cityId);//区
        }
    }

    /*获取省*/
    public void onProvinceMessage() {
        Map<String, Object> map = new HashMap<>();
        Call<ProvinceFeng> call = service.getProvince(map);
        call.enqueue(new Callback<ProvinceFeng>() {
            @Override
            public void onResponse(Call<ProvinceFeng> call, Response<ProvinceFeng> response) {
                ProvinceFeng feng = response.body();
                Log.e("yh", "feng--" + feng);
                int code = feng.getCode();
                if (code == 100) {
                    List<ProvinceFeng.InfoBean> oneBeanList = feng.getInfo();
                    mProvinceAdapter = new ProvinceAdapter(VipSearchActivity.this, oneBeanList);
                    mPopwListView.setAdapter(mProvinceAdapter);
                    mProvinceAdapter.setOnProvinceClicks(VipSearchActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ProvinceFeng> call, Throwable t) {

            }
        });
    }

    /*获取市*/
    public void onProvinceMessageCity(String provinceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("city_id", provinceId);
        Call<CityFeng> call = service.getCity(map);
        call.enqueue(new Callback<CityFeng>() {
            @Override
            public void onResponse(Call<CityFeng> call, Response<CityFeng> response) {
                CityFeng feng = response.body();
                Log.e("yh", "feng--" + feng);
                int code = feng.getCode();
                if (code == 100) {
                    List<CityFeng.InfoBean> oneBeanList = feng.getInfo();
                    mCityAdapter = new CityAdapter(VipSearchActivity.this, oneBeanList);
                    mPopwListView.setAdapter(mCityAdapter);
                    mCityAdapter.setOnCityClicks(VipSearchActivity.this);
                }


            }

            @Override
            public void onFailure(Call<CityFeng> call, Throwable t) {

            }
        });
    }

    /*获取区*/
    public void onProvinceMessageArea(String cityId) {
        Map<String, Object> map = new HashMap<>();
        map.put("city_id", cityId);
        Call<CityFeng> call = service.getCity(map);
        call.enqueue(new Callback<CityFeng>() {
            @Override
            public void onResponse(Call<CityFeng> call, Response<CityFeng> response) {
                CityFeng feng = response.body();
                Log.e("yh", "feng--" + feng);
                int code = feng.getCode();
                if (code == 100) {
                    List<CityFeng.InfoBean> oneBeanList = feng.getInfo();
                    mAreaAdapter = new AreaAdapter(VipSearchActivity.this, oneBeanList);
                    mPopwListView.setAdapter(mAreaAdapter);
                    mAreaAdapter.setOnAreaClicks(VipSearchActivity.this);
                }
            }

            @Override
            public void onFailure(Call<CityFeng> call, Throwable t) {

            }
        });
    }

    @Override
    public void onProvinceItemClicks(TextView mContent, final List<Map<String, Object>> list) {
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provinceId = list.get(0).get("city_id").toString();//省id
                String cityName = list.get(0).get("city_name").toString();//省名
                if (!cityName.equals(mMyVipSheng.getText().toString())) {
                    mMyVipSheng.setText(cityName);
                    Log.e("yh", "省--" + cityName + "--id--" + provinceId);
                    mMyVipShi.setText("市");
                    cityId = "";
                    mMyVipQu.setText("区");
                    areaId = "";
                }
                popupWindow.dismiss();
                mRefreshLayout.autoRefresh();
            }
        });
    }

    @Override
    public void onCityItemClicks(TextView mContent, final List<Map<String, Object>> list) {
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityId = list.get(0).get("city_id").toString();//市id
                String cityName = list.get(0).get("city_name").toString();//市名
                if (!cityName.equals(mMyVipShi.getText().toString())) {
                    mMyVipShi.setText(cityName);
                    Log.e("yh", "市--" + cityName + "--id--" + cityId);

                    mMyVipQu.setText("区");
                    areaId = "";
                }
                popupWindow.dismiss();
                mRefreshLayout.autoRefresh();

            }
        });
    }

    @Override
    public void onAreaItemClicks(TextView mContent, final List<Map<String, Object>> list) {
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaId = list.get(0).get("city_id").toString();//区id
                String cityName = list.get(0).get("city_name").toString();//区名
                if (!cityName.equals(mMyVipQu.getText().toString())) {
                    mMyVipQu.setText(cityName);
                    Log.e("yh", "区--" + cityName + "--id--" + areaId);
                }
                popupWindow.dismiss();
                mRefreshLayout.autoRefresh();

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData(mEdtSearch.getText().toString());
    }
}

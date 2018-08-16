package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.KaQuanRecyAdapter;
import com.linkhand.fenxiao.adapter.home.AreaAdapter;
import com.linkhand.fenxiao.adapter.home.CityAdapter;
import com.linkhand.fenxiao.adapter.home.ProvinceAdapter;
import com.linkhand.fenxiao.bean.MessageEvent;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

/********************************************************************
 @version: 1.0.0
 @description: Vip我的订单--显示不同状态(未支付可以进行跳转进行支付，其他状态只是展示)
 @author: user
 @time: 2018/5/19 9:33
 @变更历史:
 ********************************************************************/
public class MyVipActivity extends BaseActicity implements ProvinceInfo, CityInfo, AreaInfo {

    @Bind(R.id.myvip_back)
    LinearLayout mMyvipBack;
    @Bind(R.id.myvip_imgv)
    ImageView mMyvipImgv;
    @Bind(R.id.myvip_title)
    TextView mMyvipTitle;
    @Bind(R.id.myvip_money)
    TextView mMyvipMoney;
    @Bind(R.id.myvip_guige)
    TextView mMyvipGuige;
    @Bind(R.id.myvip_state)
    TextView mMyvipState;
    @Bind(R.id.myvip_cons)
    ConstraintLayout mMyvipCons;
    @Bind(R.id.my_vip_sheng)
    TextView mMyVipSheng;
    @Bind(R.id.my_vip_shi)
    TextView mMyVipShi;
    @Bind(R.id.my_vip_qu)
    TextView mMyVipQu;
    @Bind(R.id.my_vip_ll)
    LinearLayout mMyVipLl;
    @Bind(R.id.my_vip_recy)
    RecyclerView mMyVipRecy;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @Bind(R.id.myvip_search)
    TextView mMyvipSearch;
    @Bind(R.id.edt_search)
    EditText mEdtSearch;
    @Bind(R.id.tv_search)
    TextView mTvSearch;
    @Bind(R.id.title)
    TextView mTitle;

    private String danhao = "";
    private int mStatus;
    ProvinceAdapter mProvinceAdapter;//省adapter
    CityAdapter mCityAdapter;//市adapter
    AreaAdapter mAreaAdapter;//区adapter
    String provinceId = "";//省id
    String cityId = "";//市id
    String areaId = "";//区id
    private View mView;
    ListView mPopwListView;
    PopupWindow popupWindow;

    private KaQuanRecyAdapter mKaQuanRecyAdapter;
    private List<VipLvResponse.UsedataBean> mUsedataBeenList;
    private int page = 0;
    private boolean isSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vip);
        ButterKnife.bind(this);
        initEver();
        getData("");
        EventBus.getDefault().register(this);
    }

    /*各种初始化*/
    public void initEver() {
        mTitle.requestFocus();
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMyVipRecy.setLayoutManager(linearManager);

        mUsedataBeenList = new ArrayList<>();
        mKaQuanRecyAdapter = new KaQuanRecyAdapter(this, mUsedataBeenList);
        mMyVipRecy.setAdapter(mKaQuanRecyAdapter);

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
        mKaQuanRecyAdapter.setItemClickListener(new KaQuanRecyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                VipLvResponse.UsedataBean usedataBean = mUsedataBeenList.get(position);
                Intent intent = new Intent(MyVipActivity.this, KaQuanDetailActivity.class);
                intent.putExtra("uc_id", usedataBean.getUc_id());
                startActivity(intent);
            }
        });
    }

    /*获取vip商品数据*/
    public void getData(String search_str) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("city1", provinceId);
        map.put("city2", cityId);
        map.put("city3", areaId);
        map.put("pag", page);
        map.put("search", search_str);
        Call<VipLvResponse> call = service.getVipLv(map);
        call.enqueue(new Callback<VipLvResponse>() {
            @Override
            public void onResponse(Call<VipLvResponse> call, Response<VipLvResponse> response) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                VipLvResponse bean = response.body();
                if (bean.getCode() == 100) {
                    if (mMyvipCons != null) {
                        mMyvipCons.setVisibility(View.VISIBLE);
                    }
                    setMsg(bean);
                } else {
                    if (mMyvipCons != null) {
                        mMyvipCons.setVisibility(View.GONE);
                    }
                    ToastUtil.showToast(MyVipActivity.this, bean.getSuccess());
                }
            }

            @Override
            public void onFailure(Call<VipLvResponse> call, Throwable t) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
                ToastUtil.showToast(MyVipActivity.this, "网络异常");
            }
        });

    }

    /*设置数据*/
    public void setMsg(VipLvResponse bean) {
        if (bean.getInfo() != null && page == 0) {/*vip商品*/
            VipLvResponse.InfoBean info = bean.getInfo();
            danhao = info.getVip_order_id() + "";
            mStatus = info.getVip_order_status();// 1.待付款   2.待发货    3.待收货    4.完成（已升级vip）
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
            Glide.with(this).load(C.TU + info.getVimg_url()).apply(requestOptions).into(mMyvipImgv);
            mMyvipTitle.setText(info.getVip_good_name());
            mMyvipMoney.setText("¥" + info.getVip_order_money());
            mMyvipState.setText(info.getStatus_str());
            String guige = "";
            for (VipLvResponse.InfoBean.SpeciBean be : info.getSpeci()) {
                guige += be.getSpeci_name() + ":" + be.getVsp_value() + ";";
            }
            mMyvipGuige.setText(guige.substring(0, guige.length() - 1));
        }
        /*卡券列表*/
        if (bean.getUsedata() != null) {
            if (!provinceId.equals("")) {
                isSearch = true;
            } else {
                isSearch = false;
            }
            List<VipLvResponse.UsedataBean> usedata = bean.getUsedata();
            if (usedata.size() != 0) {
                mRefreshLayout.setVisibility(View.VISIBLE);
            } else {
                if (page == 0 && !isSearch) {
                    mRefreshLayout.setVisibility(View.GONE);
                    return;
                }
            }
            if (page == 0) {
                mUsedataBeenList.clear();
                mUsedataBeenList.addAll(usedata);
            } else {
                for (VipLvResponse.UsedataBean usedataBean : usedata) {
                    mUsedataBeenList.add(usedataBean);
                }
            }
            mKaQuanRecyAdapter.notifyDataSetChanged();
        }

    }


    @OnClick({R.id.myvip_back, R.id.myvip_cons, R.id.my_vip_sheng, R.id.my_vip_shi, R.id.my_vip_qu, R.id.myvip_search, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myvip_back:
                this.finish();
                break;
            case R.id.myvip_cons:
                if (!danhao.equals("") && mStatus == 1) {
                    Intent intent = new Intent(this, ConfirmOrderActivity.class);
                    intent.putExtra("danhao", danhao);
                    intent.putExtra("isVip", true);
                    startActivity(intent);
                }
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
            case R.id.myvip_search://搜索跳转
                startActivity(new Intent(this, VipSearchActivity.class));
                break;
            case R.id.tv_search:
                String search_str = mEdtSearch.getText().toString();
                if (search_str != null && search_str.length() == 0) {
                    ToastUtil.showToast(this, "请输入要搜索的内容");
                    return;
                }
                mRefreshLayout.autoRefresh();
                break;
        }
    }

    public void onProvincePopupWindow(View v, int ints) {//省1 市2 区3
        mView = LayoutInflater.from(MyVipActivity.this).inflate(R.layout.address_item_popw, null);
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
                    mProvinceAdapter = new ProvinceAdapter(MyVipActivity.this, oneBeanList);
                    mPopwListView.setAdapter(mProvinceAdapter);
                    mProvinceAdapter.setOnProvinceClicks(MyVipActivity.this);
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
                    mCityAdapter = new CityAdapter(MyVipActivity.this, oneBeanList);
                    mPopwListView.setAdapter(mCityAdapter);
                    mCityAdapter.setOnCityClicks(MyVipActivity.this);
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
                    mAreaAdapter = new AreaAdapter(MyVipActivity.this, oneBeanList);
                    mPopwListView.setAdapter(mAreaAdapter);
                    mAreaAdapter.setOnAreaClicks(MyVipActivity.this);
                }


            }

            @Override
            public void onFailure(Call<CityFeng> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleMessage(MessageEvent msg) {
        Bundle bundle = new Bundle();
        switch (msg.getMessage()) {
            case "finish":
                getData("");
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mMyVipSheng.setText("省");
        mMyVipShi.setText("市");
        mMyVipQu.setText("区");
        provinceId = "";
        cityId = "";
        areaId = "";
        mEdtSearch.setText("");
        page = 0;
        getData("");
    }
}

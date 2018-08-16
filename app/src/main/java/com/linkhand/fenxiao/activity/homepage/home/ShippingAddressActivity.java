package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.AreaAdapter;
import com.linkhand.fenxiao.adapter.home.CityAdapter;
import com.linkhand.fenxiao.adapter.home.ProvinceAdapter;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.home.CityFeng;
import com.linkhand.fenxiao.feng.home.ProvinceFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.AreaInfo;
import com.linkhand.fenxiao.info.callback.CityInfo;
import com.linkhand.fenxiao.info.callback.ProvinceInfo;
import com.linkhand.fenxiao.utils.ToastUtil;

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

public class ShippingAddressActivity extends BaseActicity implements View.OnClickListener, ProvinceInfo, CityInfo, AreaInfo {

    @Bind(R.id.shipping_address_return_id)
    LinearLayout mReturn;//返回
    @Bind(R.id.address_name_id)
    EditText mAddressName;//姓名
    @Bind(R.id.address_phone_id)
    EditText mAddressPhone;//电话
    @Bind(R.id.province_tvname_id)
    TextView mProvinceTvname;//省名
    @Bind(R.id.province_llayout_id)
    LinearLayout mProvinceLlayout;//省layout
    @Bind(R.id.city_tvname_id)
    TextView mCityTvname;//城市名
    @Bind(R.id.city_llayout_id)
    LinearLayout mCityLlayout;//市layout
    @Bind(R.id.area_tvname_id)
    TextView mAreaTvname;//区名
    @Bind(R.id.area_llayout_id)
    LinearLayout mAreaLlayout;//区layout
    @Bind(R.id.detailAddress_id)
    EditText mDetailAddress;//详细地址
    @Bind(R.id.shipping_insert_address_id)
    TextView mDetermine;//确定

    ProvinceAdapter mProvinceAdapter;//省adapter
    CityAdapter mCityAdapter;//市adapter
    AreaAdapter mAreaAdapter;//区adapter
    String provinceId = "";//省id
    String cityId = "";//市id
    String areaId = "";//区id
    @Bind(R.id.collection_return)
    LinearLayout mCollectionReturn;
    @Bind(R.id.shipping_address_title_id)
    TextView mShippingAddressTitleId;
    @Bind(R.id.activity_shipping_address)
    LinearLayout mActivityShippingAddress;

    private View mView;
    ListView mPopwListView;
    PopupWindow popupWindow;
    InfoData service;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
        onClicks();
        onGuangBiao();//隐藏光标
    }

    public void initView() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);
    }

    public void onClicks() {
        mReturn.setOnClickListener(ShippingAddressActivity.this);
        mDetermine.setOnClickListener(ShippingAddressActivity.this);
        mProvinceLlayout.setOnClickListener(ShippingAddressActivity.this);
        mCityLlayout.setOnClickListener(ShippingAddressActivity.this);
        mAreaLlayout.setOnClickListener(ShippingAddressActivity.this);
        mActivityShippingAddress.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shipping_address_return_id://返回
                this.finish();
//                intent = new Intent(this, MyOrderActivity.class);
//                startActivity(intent);
                break;
            case R.id.province_llayout_id://省layout
                onProvincePopupWindow(v, 1);
                break;
            case R.id.city_llayout_id://市layout
                if (provinceId.equals("")) {
                } else {
                    onProvincePopupWindow(v, 2);
                }
                break;
            case R.id.area_llayout_id://区layout
                if (cityId.equals("")) {
                } else {
                    onProvincePopupWindow(v, 3);
                }
                break;
            case R.id.shipping_insert_address_id://确定
                determine();
                break;
            case R.id.activity_shipping_address:
                this.finish();
                break;
        }
    }


    public void determine() {
        String name = mAddressName.getText() + "";
        String phone = mAddressPhone.getText() + "";
        String address = mDetailAddress.getText() + "";
        if (areaId.equals("") | name.equals("") | phone.equals("") | address.equals("")) {
            Toast.makeText(this, "请填全信息", Toast.LENGTH_SHORT).show();
        } else if (phone.length()!=11) {
            ToastUtil.showToast(this,"请输入11位有效手机号");
        } else {
            onMessage(name, phone, address);//添加收货地址
        }
    }

    public void onMessage(String name, String phone, String address) {//添加收货地址
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);//会员id
        map.put("site_name", name);//收货人
        map.put("site_tel", phone);//收货电话
//        map.put("site_city1", provinceId);//省id
//        map.put("site_city2", cityId);//市id
//        map.put("site_city3", areaId);//区id
        map.put("site_city1", mProvinceTvname.getText().toString());//省名
        map.put("site_city2", mCityTvname.getText().toString());//市
        map.put("site_city3", mAreaTvname.getText().toString());//区
        map.put("site_detail", address);//详细地址
        Call<ReturnFeng> call = service.getInsertAddress(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng feng = response.body();
                Log.e("yh", "feng--" + feng);
                int code = feng.getCode();
                String info = feng.getSuccess();
                if (code == 100) {
                    Toast.makeText(ShippingAddressActivity.this, info, Toast.LENGTH_SHORT).show();
                    ShippingAddressActivity.this.finish();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    public void onProvincePopupWindow(View v, int ints) {//省1 市2 区3
        mView = LayoutInflater.from(ShippingAddressActivity.this).inflate(R.layout.address_item_popw, null);
        mPopwListView = (ListView) mView.findViewById(R.id.popw_id);
        popupWindow = new PopupWindow(mView);
        int width = mProvinceLlayout.getWidth();
//        int width =mMajorsLayoutWidth.getWidth();
//        int width = ViewGroup.LayoutParams.MATCH_PARENT;
//        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = 600;
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);

        //设置点击外边区域消失
        popupWindow.setOutsideTouchable(true);
        //设置点击后消失
        popupWindow.setTouchable(true);
        //是否具有获取焦点的能力
        popupWindow.setFocusable(true);//必须写
//        popupWindow.showAsDropDown(mProvinceLlayout.getRootView());
        popupWindow.showAsDropDown(v);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
//        //设置是否允许PopupWindow的范围超过屏幕范围
//        popupWindow.setClippingEnabled(true);

        if (ints == 1) {
            onProvinceMessage();//省
        } else if (ints == 2) {
            onProvinceMessageCity(provinceId);//市
        } else if (ints == 3) {
            onProvinceMessageArea(cityId);//区
        }


    }


    public void onProvinceMessage() {//省
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
                    mProvinceAdapter = new ProvinceAdapter(ShippingAddressActivity.this, oneBeanList);
                    mPopwListView.setAdapter(mProvinceAdapter);
                    mProvinceAdapter.setOnProvinceClicks(ShippingAddressActivity.this);
                }


            }

            @Override
            public void onFailure(Call<ProvinceFeng> call, Throwable t) {

            }
        });
    }

    public void onProvinceMessageCity(String provinceId) {//市
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
                    mCityAdapter = new CityAdapter(ShippingAddressActivity.this, oneBeanList);
                    mPopwListView.setAdapter(mCityAdapter);
                    mCityAdapter.setOnCityClicks(ShippingAddressActivity.this);
                }


            }

            @Override
            public void onFailure(Call<CityFeng> call, Throwable t) {

            }
        });
    }

    public void onProvinceMessageArea(String cityId) {//区
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
                    mAreaAdapter = new AreaAdapter(ShippingAddressActivity.this, oneBeanList);
                    mPopwListView.setAdapter(mAreaAdapter);
                    mAreaAdapter.setOnAreaClicks(ShippingAddressActivity.this);
                }


            }

            @Override
            public void onFailure(Call<CityFeng> call, Throwable t) {

            }
        });
    }


    public void onGuangBiao() {//隐藏光标
        mAddressName.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
// 编辑框设置触摸监听
        mAddressName.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    mAddressName.setCursorVisible(true);// 再次点击显示光标
                }

                return false;
            }

        });

        mAddressPhone.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
// 编辑框设置触摸监听
        mAddressPhone.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    mAddressPhone.setCursorVisible(true);// 再次点击显示光标
                }

                return false;
            }

        });
        mDetailAddress.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
// 编辑框设置触摸监听
        mDetailAddress.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    mDetailAddress.setCursorVisible(true);// 再次点击显示光标
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
    public void onProvinceItemClicks(TextView mContent, final List<Map<String, Object>> list) {//省
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provinceId = list.get(0).get("city_id").toString();//省id
                String cityName = list.get(0).get("city_name").toString();//省名
                mProvinceTvname.setText(cityName);
                Log.e("yh", "省--" + cityName + "--id--" + provinceId);

                mCityTvname.setText("");
                cityId = "";
                mAreaTvname.setText("");
                areaId = "";
                popupWindow.dismiss();
            }
        });

    }

    @Override
    public void onCityItemClicks(TextView mContent, final List<Map<String, Object>> list) {//市
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityId = list.get(0).get("city_id").toString();//市id
                String cityName = list.get(0).get("city_name").toString();//市名
                mCityTvname.setText(cityName);
                Log.e("yh", "市--" + cityName + "--id--" + cityId);

                mAreaTvname.setText("");
                areaId = "";
                popupWindow.dismiss();

            }
        });
    }

    @Override
    public void onAreaItemClicks(TextView mContent, final List<Map<String, Object>> list) {//区
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaId = list.get(0).get("city_id").toString();//区id
                String cityName = list.get(0).get("city_name").toString();//区名
                mAreaTvname.setText(cityName);
                Log.e("yh", "区--" + cityName + "--id--" + areaId);
                popupWindow.dismiss();

            }
        });
    }
}

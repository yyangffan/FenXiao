package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.AddressAdapter;
import com.linkhand.fenxiao.dialog.MyDialogDelete;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.home.AllAddressFeng;
import com.linkhand.fenxiao.feng.home.SortModel;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.AddressInfo;
import com.linkhand.fenxiao.utils.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AddressActivity extends BaseActicity implements View.OnClickListener, AddressInfo {
    @Bind(R.id.address_return_id)
    LinearLayout mReturn;//返回
    @Bind(R.id.address_listview_id)
    MyListView mListView;
    @Bind(R.id.insert_address_id)
    TextView mInsertAddress;

    InfoData service;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id

    List<AllAddressFeng.InfoBean> beanList;
    List<SortModel> namesList;
    List<SortModel> mLists;

    private AddressAdapter mAdapter;
    List<Map<String, Object>> mList;
    String addressId="";//选中的地址id
//    String mJudgeAddress="0";//1  没有地址

    //适配器的数据源
    public List<String> mDatas;

    /**
     * 获取数据的方法
     */
    public void getData() {

        //一个临时变量，用于存放数据
        List<String> fileItemList = new ArrayList<String>();
        //生成随机数，控制循环次数
        int sum = new Random().nextInt(10);
        for (int i = 0; i < sum; i++) {
            fileItemList.add(i + " ");
        }

        //然后给数据源赋值
        mDatas = fileItemList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
        onClicks();
        onMessage(0);//0正常查   1更新数据
    }

    public void initView() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);

        //取得从上一个Activity当中传递过来的Intent对象
        Intent intent = getIntent();
        addressId = intent.getStringExtra("addressId");
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);
        mInsertAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_return_id://返回
                onReturn();
//                this.finish();
                break;
            case R.id.insert_address_id://新增收货地址
                Intent intent = new Intent(AddressActivity.this, ShippingAddressActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void onMessage(final int isPass) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<AllAddressFeng> call = service.getAllAddress(map);
        call.enqueue(new Callback<AllAddressFeng>() {
            @Override
            public void onResponse(Call<AllAddressFeng> call, Response<AllAddressFeng> response) {
                AllAddressFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    beanList = pcfeng.getInfo();


                    mLists = new ArrayList<SortModel>();
                    for (int i = 0; i < beanList.size(); i++) {
                        SortModel sortModels = new SortModel();
                        //1 选中  2不选中
                        if(!addressId.equals("")&&beanList.get(i).getSite_id().equals(addressId)){
                            sortModels.setSelects("1");
                        }else {
                            sortModels.setSelects("2");
                        }
                        sortModels.setSite_city1(beanList.get(i).getSite_city1());//省
                        sortModels.setSite_city2(beanList.get(i).getSite_city2());//市
                        sortModels.setSite_city3(beanList.get(i).getSite_city3());//区
                        sortModels.setSite_detail(beanList.get(i).getSite_detail());//详细地址
                        sortModels.setSite_id(beanList.get(i).getSite_id());//地址id
                        sortModels.setSite_is_del(beanList.get(i).getSite_is_del());//是否已删除  0是   1否
                        sortModels.setSite_is_first(beanList.get(i).getSite_is_first());//是否默认  0.否  1.是
                        sortModels.setSite_name(beanList.get(i).getSite_name());//收货人
                        sortModels.setSite_tel(beanList.get(i).getSite_tel());//收货人电话
                        mLists.add(sortModels);
                    }
                    namesList = getData(mLists);
                    if(isPass==0) {//0正常查   1更新数据
                        mAdapter = new AddressAdapter(AddressActivity.this, namesList);
                        mListView.setAdapter(mAdapter);
                        mAdapter.setOnItemClicks(AddressActivity.this);

                    }else if(isPass==1){
                        mAdapter.setData(namesList);
                        mAdapter.notifyDataSetChanged();
                    }

                } else {
                    mListView.setAdapter(null);
                    editor.remove("addressId").commit();
//                    Toast.makeText(AddressActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllAddressFeng> call, Throwable t) {

            }
        });

    }


    /**
     * 为ListView填充数据
     */
    private List<SortModel> getData(List<SortModel> mLists) {
        List<SortModel> list = new ArrayList<>();
        for (int i = 0; i < mLists.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setSelects(mLists.get(i).getSelects());//1 对勾  2选择
            sortModel.setSite_city1(mLists.get(i).getSite_city1());//省
            sortModel.setSite_city2(mLists.get(i).getSite_city2());//市
            sortModel.setSite_city3(mLists.get(i).getSite_city3());//区
            sortModel.setSite_detail(mLists.get(i).getSite_detail());//详细地址
            sortModel.setSite_id(mLists.get(i).getSite_id());//地址id
            sortModel.setSite_is_del(mLists.get(i).getSite_is_del());//是否已删除  0是   1否
            sortModel.setSite_is_first(mLists.get(i).getSite_is_first());//是否默认  0.否  1.是
            sortModel.setSite_name(mLists.get(i).getSite_name());//收货人
            sortModel.setSite_tel(mLists.get(i).getSite_tel());//收货人电话
            list.add(sortModel);
        }
        return list;
    }


    public void onDeleteDialog(final String site_id){//是否删除
        final MyDialogDelete dialog = new MyDialogDelete(AddressActivity.this);
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();

        TextView cancel= (TextView) dialog.findViewById(R.id.address_isdelete_id);//取消删除收货地址
        TextView confirm= (TextView) dialog.findViewById(R.id.address_isdelete_id2);//确定删除收货地址
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete(site_id,dialog);//删除收货地址
            }
        });
    }

    //删除收货地址
    public void onDelete(String site_id, final MyDialogDelete dialog) {
        Map<String, Object> map = new HashMap<>();
        map.put("site_id", site_id);//收货地址id
        Call<ReturnFeng> call = service.getDeleteAddress(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    dialog.dismiss();
                    Toast.makeText(AddressActivity.this, success, Toast.LENGTH_SHORT).show();
                    onMessage(1);//adapter更新数据//0正常查   1更新数据
                } else {
                    dialog.dismiss();
                    Toast.makeText(AddressActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    //设置默认收货地址
    public void onDefaultAddress(String site_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("site_id", site_id);//收货地址id
        map.put("user_id", mUserId);//个人id
        Call<ReturnFeng> call = service.getDefaultAddress(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(AddressActivity.this, success, Toast.LENGTH_SHORT).show();
                    onMessage(1);//adapter更新数据//0正常查   1更新数据
                } else {
                    Toast.makeText(AddressActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onMessage(0);//0正常查   1更新数据
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

    public void confim(){//确认
        if(addressId.equals("")){
            Toast.makeText(this, "请选择收货地址", Toast.LENGTH_SHORT).show();
        }else{
            int num=0;
            if(namesList!=null){//判断选中的地址是否删除
                for(int i=0;i<namesList.size();i++){
                    if(namesList.get(i).getSite_id().equals(addressId)){
                        num=1;
                    }
                }
            }
            if(num==1){
                //存入addressId 地址id
                editor.putString("addressId",addressId);
                editor.commit();
                this.finish();
            }else{
                Toast.makeText(this, "请选择收货地址", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onReturn(){//返回
        if(addressId.equals("")){
            editor.remove("addressId").commit();
            this.finish();
        }else{
            int num=0;
            if(namesList!=null){//判断选中的地址是否删除
                for(int i=0;i<namesList.size();i++){
                    if(namesList.get(i).getSite_id().equals(addressId)){
                        num=1;
                    }
                }
            }
            if(num==0){
                editor.remove("addressId").commit();
                this.finish();
            }else{
                this.finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (addressId.equals("")) {
                editor.remove("addressId").commit();
                return super.onKeyDown(keyCode, event);
            } else {
                int num = 0;
                if (namesList != null) {//判断选中的地址是否删除
                    for (int i = 0; i < namesList.size(); i++) {
                        if (namesList.get(i).getSite_id().equals(addressId)) {
                            num = 1;
                        }
                    }
                }
                if (num == 0) {
                    editor.remove("addressId").commit();
                    return super.onKeyDown(keyCode, event);
                } else {
                    return super.onKeyDown(keyCode, event);
                }
            }
        }
        return false;
    }


    //地址总layout   设置默认总layout   编辑   删除   设置默认（字）  //对勾图片  小圆图片
    @Override
    public void onItemClicks(RelativeLayout mRelativeLayout, LinearLayout mLinearLayout, TextView edit, TextView delete, final TextView content, final TextView checkTu, final ImageView roundTu, final List<Map<String, Object>> list) {
        //是否选中
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) list.get(0).get("position");
//                Log.e("yh", "position--" + position);
                for (int i = 0; i < namesList.size(); i++) {
                    namesList.get(i).setSelects("2");
                }
                namesList.get(position).setSelects("1");
                addressId= namesList.get(position).getSite_id();//选中的地址id
                Log.e("yh","position--" + position+ "--addressId选中的地址id--" + addressId);
                mAdapter.notifyDataSetChanged();
                confim();
            }
        });
        //默认
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String site_id = (String) list.get(0).get("site_id");//地址id
                onDefaultAddress(site_id);//设置默认收货地址

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {//编辑
            @Override
            public void onClick(View v) {
                String site_id = (String) list.get(0).get("site_id");//地址id
                //修改收货地址
                Intent intent = new Intent(AddressActivity.this, UpdateAddressActivity.class);
                //在Intent对象当中添加一个键值对
                intent.putExtra("site_id",site_id);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {//删除
            @Override
            public void onClick(View v) {//删除
                String site_id = (String) list.get(0).get("site_id");//地址id
                onDeleteDialog(site_id);//是否删除
            }
        });
    }



}

package com.linkhand.fenxiao.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.info.callback.ShoppingInfo;

import java.util.List;
import java.util.Map;

public class ShoppingActivity extends BaseActicity implements View.OnClickListener, ShoppingInfo {

//    @Bind(R.id.shopping_return_id)
//    LinearLayout mReturn;
//    @Bind(R.id.shopping_listview)
//    ListView mListView;
//    @Bind(R.id.shopping_rbtn_id)
//    ImageView mRbtn;
//    @Bind(R.id.shopping_rmb_id)
//    TextView mAllRmb;//总金额
//    @Bind(R.id.shopping_all_rbtn_id)
//    LinearLayout mAllRbtn;//全选
//    @Bind(shopping_settlement_id)
//    TextView mSettlement;//去结算
//    ShoppingAdapter mAdapter;
//    boolean mBoolean = false;
//    //测试
//    List<Map<String, Object>> beanList;
//
//    SharedPreferences preferences;
//    SharedPreferences.Editor editor;
//    String mUserId;//个人id
//    List<ShoppingCartListFeng.InfoBean> beanLists;
//    int mRmbs = 0;//总母币
//    int mRmbszi = 0;//总子币
//    String defaultIs;
//    InfoData service;
//
//    List<Map<String, Object>> list1;
//    Map<String, Object> map1;
//    String allGoodId = "";//商品id 多个用,隔开（购物车）
//    String allSpecId = "";//所选规格id 规格用,隔开；商品用|隔开 没有时为空，|不能少
//    String allNums = ""; //数量 多个用,隔开
//
//    String Mater_name="母币";//母币名称
//    String Son_name="子币";//子币名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
//        ButterKnife.bind(this);
//        initView();
//        initRetrofit();//初始化retrofit
//        onClicks();
//        onMessage(0);//购物车列表//0正常查   1更新数据,  list1(选中状态)，position  第几个

    }
//
//    public void initView() {
//        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
//        editor = preferences.edit();
//        //获取个人id
//        mUserId = preferences.getString("user_id", "");
//        Log.e("yh", "mUserId--" + mUserId);
//        //获取子母币名称
//         Mater_name = preferences.getString("Mater_name", "母币");//母币名称
//         Son_name = preferences.getString("Son_name", "子币");//子币名称
//    }
//
//    public void onClicks() {
//        mReturn.setOnClickListener(this);//返回
//        mAllRbtn.setOnClickListener(this);//全选
//        mSettlement.setOnClickListener(this);//去结算
//    }
//
    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.shopping_return_id://返回
//                this.finish();
//            case R.id.shopping_all_rbtn_id://全选
//                onAllChoose();
//                break;
//            case R.id.shopping_settlement_id://去结算
//                obtainMessage();//获取下单信息
//                if (beanLists != null) {
//                    Log.e("yh", "xx商品id--" + allGoodId + "--xx规格id--" + allSpecId + "--xx数量--" + allNums);
////                    intent.putExtra("nums", allNums);// 选中数量
////                    intent.putExtra("goods_id", allGoodId); //商品id
////                    intent.putExtra("specId", allSpecId); //选中的规格id
//                    if (allNums.equals("") | allGoodId.equals("") | allSpecId.equals("")) {
//                        Toast.makeText(this, "请选择商品", Toast.LENGTH_SHORT).show();
//                    } else {
//                        onSettlement();//去结算
//                    }
//                }
//                break;
//        }
    }
//
//
//    public void onMessage(final int isPass) {//购物车列表
//        Map<String, Object> map = new HashMap<>();
//        map.put("user_id", mUserId);
//        Call<ShoppingCartListFeng> call = service.getShoppingCartList(map);
//        call.enqueue(new Callback<ShoppingCartListFeng>() {
//            @Override
//            public void onResponse(Call<ShoppingCartListFeng> call, Response<ShoppingCartListFeng> response) {
//                ShoppingCartListFeng pcfeng = response.body();
//                Log.e("yh", "pcfeng--" + pcfeng);
//                int code = pcfeng.getCode();
//                if (code == 100) {
//                   int MinvalidNumber=0;//失效个
//                    beanLists = pcfeng.getInfo();
//                    if (isPass == 0) {//0正常查   1更新数据
//                        list1 = new ArrayList<Map<String, Object>>();
//                        for (int i = 0; i < beanLists.size(); i++) {
//                            int state=beanLists.get(i).getGood_state();//是否失效  1未失效  0已失效
//                            map1 = new HashMap<String, Object>();
//                            map1.put("good_state",state);
//                            if(state==1){
//                                map1.put("is_selected", defaultIs);//1选中   2未选中
//                            }else{
//                                map1.put("is_selected", 2);//1选中   2未选中
//                            }
//                            list1.add(map1);
//                        }
//                        mAdapter = new ShoppingAdapter(ShoppingActivity.this, beanLists, list1);
//                        mListView.setAdapter(mAdapter);
//                        mAdapter.setOnClicks(ShoppingActivity.this);
//                        onAllChoose();//全选
//                    } else if (isPass == 1) {
//
////                        list1Xin.remove(position);
////                        list1=list1Xin;
//                        mAdapter.setData(beanLists);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                    onrmb();
//                } else {
//                    mListView.setAdapter(null);
////                    Toast.makeText(HomePageFragment.this.getActivity(), "xx", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ShoppingCartListFeng> call, Throwable t) {
//
//            }
//        });
//
//    }
//
//    public void onrmb() {
//        if (beanLists != null) {
//            mRmbs = 0;
//            mRmbszi = 0;
//            for (int i = 0; i < beanLists.size(); i++) {
//                String is_selected = list1.get(i).get("is_selected") + "";
//                if (is_selected.equals("1")) {//1选中   2未选中
//                    String mu = beanLists.get(i).getCart_mater() + "";//母币
//                    String zi = beanLists.get(i).getCart_son() + "";//子币
//                    int ints = (int) Double.parseDouble(mu);
//                    int intszi = (int) Double.parseDouble(zi);
//                    mRmbs = mRmbs + ints;
//                    mRmbszi = mRmbszi + intszi;
//                }
//            }
//
//            mAllRmb.setText(mRmbs +Mater_name + mRmbszi + Son_name);
//        }
//    }
//
//    public void onAllChoose() {//全选
//        if (mBoolean) {
//            mBoolean = false;
//            mRbtn.setImageResource(R.drawable.ovaltwo);
//            if (list1 != null) {
//                for (int i = 0; i < list1.size(); i++) {
//                    list1.get(i).put("is_selected", "2");//1选中   2未选中
//                }
//                if (mAdapter != null) {
//                    mAdapter.notifyDataSetChanged();
//                }
//                mRmbs = 0;
//                mRmbszi = 0;
//            }
//        } else {
//            int ints = 0;
//            int intszi = 0;
//            mBoolean = true;
//            mRbtn.setImageResource(R.drawable.ovalone);
//            mRmbs = 0;
//            mRmbszi = 0;
//            if (beanLists != null & list1 != null) {
//                for (int i = 0; i < list1.size(); i++) {
//                   int state= beanLists.get(i).getGood_state();//是否失效  1未失效  0已失效
//                    if(state==1){
//                        list1.get(i).put("is_selected", "1");//1选中   2未选中
//                        String mu = beanLists.get(i).getCart_mater() + "";//母币
//                        String zi = beanLists.get(i).getCart_son() + "";//子币
//                        ints = (int) Double.parseDouble(mu);
//                        intszi = (int) Double.parseDouble(zi);
//                        mRmbs = mRmbs + ints;
//                        mRmbszi = mRmbszi + intszi;
//                    }
//                }
//            }
//            if (mAdapter != null) {
//                mAdapter.notifyDataSetChanged();
//            }
//        }
//
//        mAllRmb.setText(mRmbs + Mater_name + mRmbszi + Son_name);
//    }
//
//
//    public void onCheckeds() {//判断选中的是否是全部
//        String mySelected;
//        int sum = 0;//未失效数
//        int num = 0;//失效数
//        int good_state;//是否失效  1未失效  0已失效
//        for (int i = 0; i < list1.size(); i++) {
//            mySelected = list1.get(i).get("is_selected") + "";
//             good_state= (int) list1.get(i).get("good_state");
//            if (mySelected.equals("1")) {
//                sum++;
//            }
//            if(good_state==0){
//                num++;
//            }
//        }
//
//        if (sum == list1.size()-num) {
//            mRbtn.setImageResource(R.drawable.ovalone);
//            mBoolean = true;
//            if (sum == 0) {
//                mRmbs = 0;
//                mRmbszi = 0;
//                mAllRmb.setText(mRmbs +Mater_name + mRmbszi + Son_name);
//            }
//        } else {
//            mRbtn.setImageResource(R.drawable.ovaltwo);
//            mBoolean = false;
//        }
//
//    }
//
//    public void onRemove(String cart_id, final int position, final int isFailure) {//删除购物车商品 isFailure  1未失效  0已失效
//        Map<String, Object> map = new HashMap<>();
//        map.put("cart_id", cart_id);//购物车id
//        Call<ReturnFeng> call = service.getRemoveGoods(map);
//        call.enqueue(new Callback<ReturnFeng>() {
//            @Override
//            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
//                ReturnFeng pcfeng = response.body();
//                Log.e("yh", "pcfeng--" + pcfeng);
//                int code = pcfeng.getCode();
//                String success = pcfeng.getSuccess();
//                if (code == 100) {
//                    Toast.makeText(ShoppingActivity.this, success, Toast.LENGTH_SHORT).show();
////                    onMessage(1, list1, position);//购物车列表//0正常查   1更新数据,  list1(选中状态)，position  第几个
//                    onMessage(1);//购物车列表//0正常查   1更新数据,  list1(选中状态)，position  第几个
////                    if(isFailure==1){//1未失效  0已失效
//                        list1.remove(position);
//                        onCheckeds();//判断选中的是否是全部
////                    }
//
//                } else {
//                    Toast.makeText(ShoppingActivity.this, success, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ReturnFeng> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void obtainMessage() {//获取下单信息
//
//        allGoodId = "";//商品id 多个用,隔开（购物车）
//        allSpecId = "";//所选规格id 规格用,隔开；商品用|隔开 没有时为空，|不能少
//        allNums = ""; //数量 多个用,隔开
////        beanLists//数据
////        list1
//        if (beanLists != null) {
//            for (int i = 0; i < list1.size(); i++) {
//                String is_selected = "";//1选中   2未选中
//                is_selected = list1.get(i).get("is_selected").toString();
//                if (is_selected.equals("1")) {
//                    //----------------商品id-----------------
//                    String good_id = beanLists.get(i).getGood_id();//商品id
//                    if (allGoodId.equals("")) {
//                        allGoodId = good_id;
//                    } else {
//                        allGoodId = allGoodId + "," + good_id;
//                    }
//                    //----------------数量-----------------
//                    String cart_num = beanLists.get(i).getCart_num();//数量
//                    if (allNums.equals("")) {
//                        allNums = cart_num;
//                    } else {
//                        allNums = allNums + "," + cart_num;
//                    }
//                    //----------------规格id-----------------
//                    List<ShoppingCartListFeng.InfoBean.SpeciBean> onespeciBeenList = beanLists.get(i).getSpeci();//单个的全部规格
//                    String oneSpec = ""; //单个的规格id
//                    if (onespeciBeenList != null) {
//                        for (int j = 0; j < onespeciBeenList.size(); j++) {
//                            String speci_id = onespeciBeenList.get(j).getGsp_id();//规格id
//                            if (oneSpec.equals("")) {
//                                oneSpec = speci_id;
//                            } else {
//                                oneSpec = oneSpec + "," + speci_id;
//                            }
//                        }
//                    }
//                    if (allSpecId.equals("")) {
//                        allSpecId = oneSpec;
//                    } else {
//                        allSpecId = allSpecId + "|" + oneSpec;
//                    }
//
//
//                }
//
//            }
//        }
//    }
//
//    public void onSettlement() {//去结算
//        Intent intent = new Intent(this, ConfirmOrderActivity.class);
//        intent.putExtra("nums", allNums);// 选中数量
//        intent.putExtra("goods_id", allGoodId); //商品id
//        intent.putExtra("specId", allSpecId); //选中的规格id
//        startActivity(intent);
//    }
//
//
//    private void initRetrofit() {
//        Retrofit retrofit = new Retrofit.Builder()
//                //把固定不变的url写到这里
//                .baseUrl(C.HOSTIP)
//                //支持返回字符串,先写字符串
//                .addConverterFactory(ScalarsConverterFactory.create())
//                //支持解析返回的json，后写json解析
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        //创建一个接口的实现类
//        service = retrofit.create(InfoData.class);
//    }
//
//    public void onUpdateNum(String cart_id, int nums) {//改变数量(购物车id，数量)
//        Log.e("yh", "改变数量" + cart_id + "--" + nums);
////        Toast.makeText(this, "改变数量" + cart_id + "--" + nums, Toast.LENGTH_SHORT).show();
//        Map<String, Object> map = new HashMap<>();
//        map.put("cart_id", cart_id);//购物车id
//        map.put("num", nums);//数量
//        Call<ReturnFeng> call = service.getUpdateGoodNum(map);
//        call.enqueue(new Callback<ReturnFeng>() {
//            @Override
//            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
//                ReturnFeng pcfeng = response.body();
//                Log.e("yh", "pcfeng--" + pcfeng);
//                int code = pcfeng.getCode();
//                String success = pcfeng.getSuccess();
//                if (code == 100) {
////                    Toast.makeText(ShoppingActivity.this, success, Toast.LENGTH_SHORT).show();
//                    onMessage(1);//购物车列表//0正常查   1更新数据,  list1(选中状态)，position  第几个
//                } else {
////                    Toast.makeText(ShoppingActivity.this, success, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ReturnFeng> call, Throwable t) {
//
//            }
//        });
//    }
//
//
    @Override
    public void onItemClicks(LinearLayout mLinearLayout, final Button jian, Button jia, final TextView num, ImageView mSelected, ImageView mImageView, final List<Map<String, Object>> list) {
//        mSelected.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = (int) list.get(0).get("position");
//                int good_state = (int) list.get(0).get("good_state");//是否失效  1未失效  0已失效
//                Log.e("yh", "position--" + position + "--是否失效--" + good_state);
//                if (good_state == 1) {
//                    String is_selected = list1.get(position).get("is_selected") + "";
//                    if (is_selected.equals("1")) {
//                        list1.get(position).put("is_selected", "2");
//                        String mu = beanLists.get(position).getCart_mater() + "";//母币
//                        String zi = beanLists.get(position).getCart_son() + "";//子币
////                    int ints = Integer.parseInt(mu);
//                        int ints = (int) Double.parseDouble(mu);
//                        int intszi = (int) Double.parseDouble(zi);
//                        mRmbs = mRmbs - ints;
//                        mRmbszi = mRmbszi - intszi;
//                    } else if (is_selected.equals("2")) {
//                        list1.get(position).put("is_selected", "1");
//                        String mu = beanLists.get(position).getCart_mater() + "";//母币
//                        String zi = beanLists.get(position).getCart_son() + "";//子币
//                        int ints = (int) Double.parseDouble(mu);
//                        int intszi = (int) Double.parseDouble(zi);
//                        mRmbs = mRmbs + ints;
//                        mRmbszi = mRmbszi + intszi;
//                    }
//                    mAdapter.notifyDataSetChanged();
//                    onCheckeds();//判断选中的是否是全部
//                    mAllRmb.setText(mRmbs + Mater_name + mRmbszi + Son_name);
//                }
//
//            }
//        });
//
//        mLinearLayout.setOnClickListener(new View.OnClickListener() {//选中
//            @Override
//            public void onClick(View v) {
//
//                String good_id = (String) list.get(0).get("good_id");
//                int good_state = (int) list.get(0).get("good_state");//是否失效  1未失效  0已失效
//                Log.e("yh", "good_id--" + good_id + "--是否失效--" + good_state);
//                if (good_state == 1) {
//                    Intent intent = new Intent(ShoppingActivity.this, DetailPageActivity.class);
//                    intent.putExtra("good_id", good_id);
//                    startActivity(intent);
//                }
//            }
//        });
//        mImageView.setOnClickListener(new View.OnClickListener() {//删除
//            @Override
//            public void onClick(View v) {//删除
//                int position = (int) list.get(0).get("position");
//                String cart_id = (String) list.get(0).get("cart_id");//购物车id
////                Log.e("yh", "position--" + position);
////                beanList.remove(position);
////                mAdapter.notifyDataSetChanged();
//                int good_state = (int) list.get(0).get("good_state");//是否失效  1未失效  0已失效
//                Log.e("yh", "是否失效--" + good_state);
//                if (good_state == 1) {
//                    onRemove(cart_id, position,1);//删除购物车商品1未失效  0已失效
//                }else{
//                    onRemove(cart_id, position,0);//删除购物车商品1未失效  0已失效
//                }
//
//            }
//        });
//
//        jian.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int good_state = (int) list.get(0).get("good_state");//是否失效  1未失效  0已失效
//                Log.e("yh", "是否失效--" + good_state);
//                if (good_state == 1) {
//                    String cart_id = (String) list.get(0).get("cart_id");//购物车id
//                    int nums = Integer.parseInt(num.getText().toString());
//                    if (nums == 1) {
//                        num.setText(nums + "");
//                    } else {
//                        nums = nums - 1;
//                        num.setText(nums + "");
////                    Log.e("yh", "改变数量" + cart_id + "--" + nums);
//                        onUpdateNum(cart_id, nums);//改变数量(购物车id，数量)
//                    }
//                }
//            }
//        });
//        jia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int good_state = (int) list.get(0).get("good_state");//是否失效  1未失效  0已失效
//                Log.e("yh", "是否失效--" + good_state);
//                if (good_state == 1) {
//                    String cart_id = (String) list.get(0).get("cart_id");//购物车id
//                    int nums = Integer.parseInt(num.getText().toString());
//
//                    if (nums == 99) {
//                        num.setText(nums + "");
//                    } else {
//                        nums = nums + 1;
//                        num.setText(nums + "");
////                    Log.e("yh", "改变数量" + cart_id + "--" + nums);
//                        onUpdateNum(cart_id, nums);//改变数量(购物车id，数量)
//                    }
//                }
//            }
//        });
//
    }
}

package com.linkhand.fenxiao.fragment.shopping;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.ConfirmOrderActivity;
import com.linkhand.fenxiao.activity.login.LoginActivity;
import com.linkhand.fenxiao.adapter.mine.ShoppingNewAdapter;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.home.OrderInfoResponse;
import com.linkhand.fenxiao.feng.mine.ShoppingCartListNewFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.ShoppingInfo;
import com.linkhand.fenxiao.utils.MyListView;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.text.DecimalFormat;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingFragment extends BaseFragment implements View.OnClickListener, ShoppingInfo {

    @Bind(R.id.shopping_listview)
    MyListView mListView;
    @Bind(R.id.shopping_rbtn_id)
    ImageView mRbtn;
    @Bind(R.id.shopping_rmb_id)
    TextView mAllRmb;//总金额
    @Bind(R.id.shopping_all_rbtn_id)
    LinearLayout mAllRbtn;//全选
    @Bind(R.id.shopping_settlement_id)
    TextView mSettlement;//去结算
    @Bind(R.id.shopping_tv_cancelAll)
    TextView mShoppingTvCancelAll;
    @Bind(R.id.shopping_cancel_lv)
    MyListView mShoppingCancelLv;
    @Bind(R.id.shopping_cancel_ll)
    LinearLayout mShoppingCancelLl;
    @Bind(R.id.title_right_delete)
    TextView mTitleRightDelete;
    @Bind(R.id.shopping_shixiao_num)
    TextView mShoppingShixiaoNum;
    @Bind(R.id.shopping_title)
    TextView mShoppingTitle;
    @Bind(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;

    boolean mBoolean = false;
    List<Map<String, Object>> beanList;    //测试
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    //    List<ShoppingCartListFeng.InfoBean> beanLists;
    double mRmbs = 0;//总母币
    double mRmbszi = 0;//总子币
    String defaultIs;
    InfoData service;
    Map<String, Object> map1;
    String allCartId = "";//商品id 多个用,隔开（购物车）
    String allSpecId = "";//所选规格id 规格用,隔开；商品用|隔开 没有时为空，|不能少
    String allNums = ""; //数量 多个用,隔开
    String Mater_name = "母币";//母币名称
    String Son_name = "子币";//子币名称


    List<Map<String, Object>> list1;
    ShoppingNewAdapter mNewAdapter;//未失效商品
    List<ShoppingCartListNewFeng.InfoBean> mInfoBeen;//未失效商品集合

    List<Map<String, Object>> list_cancel;
    ShoppingNewAdapter mCancelAdapter;//失效商品
    private List<ShoppingCartListNewFeng.InfoBean> mNotCartBeen;//失效商品列表集合
    private String mCancel_ids;//失效商品id集合
    private DecimalFormat mDecimalFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        ButterKnife.bind(this, view);
        mDecimalFormat = new DecimalFormat("0.00");
        mInfoBeen = new ArrayList<>();
        mNotCartBeen = new ArrayList<>();
        list_cancel = new ArrayList<>();
        list1 = new ArrayList<>();
        initView();
        if (mUserId.equals("")) {
            onLogin();//去登陆
        } else {
            initRetrofit();//初始化retrofit
            onClicks();
            onMessage(0);//购物车列表//0正常查   1更新数据,  list1(选中状态)，position  第几个
        }
        return view;
    }


    public void initView() {
        mSmartRefresh.setEnableRefresh(false);
        mSmartRefresh.setEnableLoadmore(false);
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);
        Mater_name = preferences.getString("Mater_name", "母币");//母币名称
        Son_name = preferences.getString("Son_name", "子币");//子币名称
    }

    public void onClicks() {
        mAllRbtn.setOnClickListener(this);//全选
        mSettlement.setOnClickListener(this);//去结算
        mTitleRightDelete.setOnClickListener(this);//批量删除
        mShoppingTvCancelAll.setOnClickListener(this);//清空失效宝贝
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_all_rbtn_id://全选
                if (mInfoBeen != null && mInfoBeen.size() != 0 && list1 != null && list1.size() != 0) {
                    onAllChoose();
                } else {
                    ToastUtil.showToast(this.getActivity(), "没有商品可选择");
                }
                break;
            case R.id.shopping_settlement_id://去结算
                obtainMessage();//获取下单信息
                if (mInfoBeen != null) {
                    Log.e("yh", "xx商品id--" + allCartId + "--xx规格id--" + allSpecId + "--xx数量--" + allNums);
                    if (allNums.equals("") || allCartId.equals("") || allSpecId.equals("")) {
                        Toast.makeText(ShoppingFragment.this.getActivity(), "请选择商品", Toast.LENGTH_SHORT).show();
                    } else {
                        onSettlement();//去结算
                    }
                }
                break;
            case R.id.title_right_delete://批量删除
                rigthDelete();
                break;
            case R.id.shopping_tv_cancelAll://清空失效宝贝
                new ShowRemindDialog().showRemind(ShoppingFragment.this.getActivity(), "确定", "取消", "", "清空失效宝贝?", R.drawable.delete_img, new ShowRemindDialog.OnTvClickListener() {
                    @Override
                    public void OnSureClickListener() {
                        cancelAll();
                    }
                });
                break;
        }
    }

    /*批量删除*/
    public void rigthDelete() {
        obtainMessage();
        if (allCartId.equals("")) {
            ToastUtil.showToast(this.getActivity(), "请选择要删除的商品");
        } else {
            new ShowRemindDialog().showRemind(ShoppingFragment.this.getActivity(), "确定", "取消", "", "确定删除所选商品?", R.drawable.delete_img, new ShowRemindDialog.OnTvClickListener() {
                @Override
                public void OnSureClickListener() {
                    onRemovePiLiang(allCartId);
                }
            });
        }
    }

    /*清空失效宝贝*/
    public void cancelAll() {
        onRemovePiLiang(mCancel_ids);
    }

    public void onLogin() {//去登陆
        Intent intent = new Intent(ShoppingFragment.this.getActivity(), LoginActivity.class);//登录
        startActivity(intent);
    }

    public void onMessage(final int isPass) {//购物车列表
        if (service != null) {
            mBoolean = false;
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", mUserId);
            Call<ShoppingCartListNewFeng> call = service.getShoppingCartNewList(map);
            call.enqueue(new Callback<ShoppingCartListNewFeng>() {
                @Override
                public void onResponse(Call<ShoppingCartListNewFeng> call, Response<ShoppingCartListNewFeng> response) {
                    ShoppingCartListNewFeng pcfeng = response.body();
                    int code = pcfeng.getCode();
                    if (code == 100) {
                        /*初始化全选按钮*/
                        if (mRbtn != null) {
                            if(isPass!=1) {
                                mRbtn.setImageResource(R.drawable.ovaltwo);
                            }
                        }
                        mBoolean = false;
                        int MinvalidNumber = 0;//失效个
                        mInfoBeen = pcfeng.getInfo();
                        List<ShoppingCartListNewFeng.NotCartBean> not_cart = pcfeng.getNot_cart();
                        if (not_cart != null && not_cart.size() > 0 && mShoppingCancelLl != null) {
                            if (mShoppingCancelLl != null) {
                                mShoppingCancelLl.setVisibility(View.VISIBLE);
                            }
                            if (mShoppingShixiaoNum != null) {
                                mShoppingShixiaoNum.setText("失效宝贝(" + not_cart.size() + ")");
                            }
                        } else {
                            if (mShoppingCancelLl != null) {
                                mShoppingCancelLl.setVisibility(View.GONE);
                            }
                        }

                        if (mInfoBeen != null && mInfoBeen.size() > 0) {
                            if (mShoppingTitle != null) {
                                mShoppingTitle.setText("购物车(" + mInfoBeen.size() + ")");
                            }
                        } else {
                            if (mShoppingTitle != null) {
                                mShoppingTitle.setText("购物车");
                            }
                        }
                        if (isPass == 0) {
                             /*未失效商品*/
                            list1 = new ArrayList<Map<String, Object>>();
                            for (int i = 0; i < mInfoBeen.size(); i++) {
                                int state = mInfoBeen.get(i).getGood_state();//是否失效  1未失效  0已失效
                                map1 = new HashMap<String, Object>();
                                map1.put("good_state", state);
                                if (state == 1) {
                                    map1.put("is_selected", "2");//1选中   2未选中
                                } else {
                                    map1.put("is_selected", 2);//1选中   2未选中
                                }
                                list1.add(map1);
                            }
                            mNewAdapter = new ShoppingNewAdapter(ShoppingFragment.this.getActivity(), mInfoBeen, list1);
                            if (mListView != null) {
                                mListView.setAdapter(mNewAdapter);
                            }
                            mNewAdapter.setOnFragmentClicks(ShoppingFragment.this);
                            /*失效商品*/
                            list_cancel.clear();
                            mNotCartBeen.clear();
                            mCancel_ids = "";
                            for (int i = 0; i < not_cart.size(); i++) {
                                Map<String, Object> mapp = new HashMap<>();
                                mapp.put("is_selected", 2);
                                list_cancel.add(mapp);
                                mCancel_ids = mCancel_ids + "," + not_cart.get(i).getCart_id();
                            }
                            for (int i = 0; i < not_cart.size(); i++) {
                                ShoppingCartListNewFeng.NotCartBean mCart = not_cart.get(i);
                                ShoppingCartListNewFeng.InfoBean infobean = new ShoppingCartListNewFeng.InfoBean(mCart.getCart_id(), mCart.getGood_id(),
                                        mCart.getUser_id(), mCart.getSpeci_ids(), mCart.getCart_mater(), mCart.getCart_son(), mCart.getCart_num(), mCart.getCart_add_time(),
                                        mCart.getCart_state(), mCart.getGood_name(), mCart.getGood_is_top(), mCart.getImg_url(), 0, mCart.getSpeci());
                                mNotCartBeen.add(infobean);
                            }

                            mCancelAdapter = new ShoppingNewAdapter(ShoppingFragment.this.getActivity(), mNotCartBeen, list_cancel);
                            mCancelAdapter.setOnFragmentClicks(ShoppingFragment.this);
                            if (mShoppingCancelLv != null) {
                                mShoppingCancelLv.setAdapter(mCancelAdapter);
                            }
                        } else if (isPass == 1) {
                            mNewAdapter.setData(mInfoBeen);
                            mNewAdapter.notifyDataSetChanged();
                        }
                        if (mAllRmb != null) {
                            mAllRmb.setText("0" + Mater_name + "\n0" + Son_name);
                        }
                        onrmb();
                    } else {
                        mInfoBeen.clear();
                        list1.clear();
                        mNotCartBeen.clear();
                        list_cancel.clear();
                        if (mShoppingTitle != null) {
                            mShoppingTitle.setText("购物车");
                        }
                        if (mShoppingCancelLl != null) {
                            mShoppingCancelLl.setVisibility(View.GONE);
                        }
                        if (mShoppingCancelLl != null) {
                            mShoppingCancelLv.setAdapter(null);
                        }
                        mBoolean = false;
                        if (mRbtn != null && mAllRmb != null && mListView != null) {
                            mRbtn.setImageResource(R.drawable.ovaltwo);
                            mAllRmb.setText("0" + Mater_name + "\n0" + Son_name);
                            mListView.setAdapter(null);
                        }
                    }

                }

                @Override
                public void onFailure(Call<ShoppingCartListNewFeng> call, Throwable t) {
                    Toast.makeText(ShoppingFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            initRetrofit();
        }
    }

    public void onrmb() {
        if (mInfoBeen != null) {
            mRmbs = 0;
            mRmbszi = 0;
            for (int i = 0; i < mInfoBeen.size(); i++) {
                String is_selected = list1.get(i).get("is_selected") + "";
                if (is_selected.equals("1")) {//1选中   2未选中
                    String mu = mInfoBeen.get(i).getCart_mater() + "";//母币
                    String zi = mInfoBeen.get(i).getCart_son() + "";//子币
                    double ints = Double.parseDouble(mu);
                    double intszi = Double.parseDouble(zi);
                    mRmbs = mRmbs + ints;
                    mRmbszi = mRmbszi + intszi;
                }
            }
            if (mAllRmb != null) {
                mAllRmb.setText(mDecimalFormat.format(mRmbs) + Mater_name + "\n" + mDecimalFormat.format(mRmbszi) + Son_name);
            }
        }
    }

    public void onAllChoose() {//全选
        if (mBoolean) {
            mBoolean = false;
            if (mRbtn != null) {
                mRbtn.setImageResource(R.drawable.ovaltwo);
            }
            if (list1 != null) {
                for (int i = 0; i < list1.size(); i++) {
                    list1.get(i).put("is_selected", "2");//1选中   2未选中
                }
                if (mNewAdapter != null) {
                    mNewAdapter.notifyDataSetChanged();
                }
                mRmbs = 0;
                mRmbszi = 0;
            }
        } else {
            double ints = 0;
            double intszi = 0;
            mBoolean = true;
            if (mRbtn != null) {
                mRbtn.setImageResource(R.drawable.ovalone);
            }
            mRmbs = 0;
            mRmbszi = 0;
            if (mInfoBeen != null & list1 != null) {
                for (int i = 0; i < list1.size(); i++) {
                    int state = mInfoBeen.get(i).getGood_state();//是否失效  1未失效  0已失效
                    if (state == 1) {
                        list1.get(i).put("is_selected", "1");//1选中   2未选中
                        String mu = mInfoBeen.get(i).getCart_mater() + "";//母币
                        String zi = mInfoBeen.get(i).getCart_son() + "";//子币
                        ints = Double.parseDouble(mu);
                        intszi = Double.parseDouble(zi);
                        mRmbs = mRmbs + ints;
                        mRmbszi = mRmbszi + intszi;
                    }
                }
            }
            if (mNewAdapter != null) {
                mNewAdapter.notifyDataSetChanged();
            }
        }
        if (mAllRmb != null) {
            mAllRmb.setText(mDecimalFormat.format(mRmbs) + Mater_name + "\n" + mDecimalFormat.format(mRmbszi) + Son_name);
        }
    }


    public void onCheckeds() {//判断选中的是否是全部
        String mySelected;
        int sum = 0;//未失效数
        int num = 0;//失效数
        int good_state;//是否失效  1未失效  0已失效
        for (int i = 0; i < list1.size(); i++) {
            mySelected = list1.get(i).get("is_selected") + "";
            good_state = (int) list1.get(i).get("good_state");
            if (mySelected.equals("1")) {
                sum++;
            }
            if (good_state == 0) {
                num++;
            }
        }

        if (sum == list1.size() - num) {
            mRbtn.setImageResource(R.drawable.ovalone);
            mBoolean = true;
            if (sum == 0) {
                mRmbs = 0;
                mRmbszi = 0;
                mAllRmb.setText(mDecimalFormat.format(mRmbs) + Mater_name + "\n" + mDecimalFormat.format(mRmbszi) + Son_name);
            }
        } else {
            mRbtn.setImageResource(R.drawable.ovaltwo);
            mBoolean = false;
        }

    }

    /*批量删除商品  包括失效商品*/
    public void onRemovePiLiang(String cart_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("cart_id", cart_id);//购物车id
        Call<ReturnFeng> call = service.getRemoveGoods(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(ShoppingFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                    onMessage(0);//成功后调用
                    mBoolean = true;//取消掉选择并且将数据重置
                    onAllChoose();
                } else {
                    Toast.makeText(ShoppingFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Toast.makeText(ShoppingFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onRemove(String cart_id, final int position, final int isFailure) {//删除购物车商品 isFailure  1未失效  0已失效
        Map<String, Object> map = new HashMap<>();
        map.put("cart_id", cart_id);//购物车id
        Call<ReturnFeng> call = service.getRemoveGoods(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(ShoppingFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
//                    onMessage(1, list1, position);//购物车列表//0正常查   1更新数据,  list1(选中状态)，position  第几个
                    onMessage(0);//购物车列表//0正常查   1更新数据,  list1(选中状态)，position  第几个
                    if (isFailure == 1) {//1未失效  0已失效
                        list1.remove(position);
                    } else {
                        list_cancel.remove(position);
                    }
                    onCheckeds();//判断选中的是否是全部
//                    }

                } else {
                    Toast.makeText(ShoppingFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Toast.makeText(ShoppingFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtainMessage() {//获取下单信息

        allCartId = "";//商品id 多个用,隔开（购物车）
        allSpecId = "";//所选规格id 规格用,隔开；商品用|隔开 没有时为空，|不能少
        allNums = ""; //数量 多个用,隔开

        if (mInfoBeen != null) {
            for (int i = 0; i < list1.size(); i++) {
                String is_selected = "";//1选中   2未选中
                is_selected = list1.get(i).get("is_selected").toString();
                if (is_selected.equals("1")) {
                    String cart_id = mInfoBeen.get(i).getCart_id();//商品id
                    if (allCartId.equals("")) {
                        allCartId = cart_id;
                    } else {
                        allCartId = allCartId + "," + cart_id;
                    }
                    String cart_num = mInfoBeen.get(i).getCart_num();//数量
                    if (allNums.equals("")) {
                        allNums = cart_num;
                    } else {
                        allNums = allNums + "," + cart_num;
                    }
                    List<ShoppingCartListNewFeng.InfoBean.SpeciBean> onespeciBeenList = mInfoBeen.get(i).getSpeci();//单个的全部规格
                    String oneSpec = ""; //单个的规格id
                    if (onespeciBeenList != null) {
                        for (int j = 0; j < onespeciBeenList.size(); j++) {
                            String speci_id = onespeciBeenList.get(j).getGsp_id();//规格id
                            if (oneSpec.equals("")) {
                                oneSpec = speci_id;
                            } else {
                                oneSpec = oneSpec + "," + speci_id;
                            }
                        }
                    }
                    if (allSpecId.equals("")) {
                        allSpecId = oneSpec;
                    } else {
                        allSpecId = allSpecId + "|" + oneSpec;
                    }
                }

            }
        }
    }

    public void onSettlement() {//去结算
        xiadan(allCartId);

    }

    public void xiadan(String cart_ids) {
        Map<String, Object> map = new HashMap<>();
        map.put("cart_ids", cart_ids);
        Call<OrderInfoResponse> call = service.getCartInfo(map);
        call.enqueue(new Callback<OrderInfoResponse>() {
            @Override
            public void onResponse(Call<OrderInfoResponse> call, Response<OrderInfoResponse> response) {
                OrderInfoResponse pcfeng = response.body();
                int code = pcfeng.getCode();
                if (code == 100) {
                    Intent intent = new Intent(ShoppingFragment.this.getActivity(), ConfirmOrderActivity.class);
                    String danhao_str = "";
                    for (String str : pcfeng.getInfo()) {
                        danhao_str = danhao_str + "," + str;
                    }
                    intent.putExtra("danhao", danhao_str.substring(1, danhao_str.length()));
                    startActivity(intent);
                } else if (code == 309) {
                    new ShowRemindDialog().showRemind(ShoppingFragment.this.getActivity(), "确定", "", "", pcfeng.getSuccess(), R.drawable.prompt, null);
                } else {
                    Toast.makeText(ShoppingFragment.this.getActivity(), pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OrderInfoResponse> call, Throwable t) {
                Toast.makeText(ShoppingFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
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

    public void onUpdateNum(String cart_id, final int nums, final TextView num) {//改变数量(购物车id，数量)
        Log.e("yh", "改变数量" + cart_id + "--" + nums);
//        Toast.makeText(this, "改变数量" + cart_id + "--" + nums, Toast.LENGTH_SHORT).show();
        Map<String, Object> map = new HashMap<>();
        map.put("cart_id", cart_id);//购物车id
        map.put("num", nums);//数量
        Call<ReturnFeng> call = service.getUpdateGoodNum(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
//                    Toast.makeText(ShoppingActivity.this, success, Toast.LENGTH_SHORT).show();
                    onMessage(1);//购物车列表//0正常查   1更新数据,  list1(选中状态)，position  第几个
                    num.setText(nums + "");
                } else {
                    Toast.makeText(ShoppingFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Toast.makeText(ShoppingFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClicks(LinearLayout mLinearLayout, final Button jian, Button jia, final TextView num, View mSelected, ImageView mImageView, final List<Map<String, Object>> list, View ll) {
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) list.get(0).get("position");
                int good_state = (int) list.get(0).get("good_state");//是否失效  1未失效  0已失效
                Log.e("yh", "position--" + position + "--是否失效--" + good_state);
                if (good_state == 1) {
                    String is_selected = list1.get(position).get("is_selected") + "";
                    if (is_selected.equals("1")) {
                        list1.get(position).put("is_selected", "2");
//                        String mu = beanLists.get(position).getCart_mater() + "";//母币
//                        String zi = beanLists.get(position).getCart_son() + "";//子币
                        String mu = mInfoBeen.get(position).getCart_mater() + "";//母币
                        String zi = mInfoBeen.get(position).getCart_son() + "";//子币
//                    int ints = Integer.parseInt(mu);
                        double ints = Double.parseDouble(mu);
                        double intszi = Double.parseDouble(zi);
                        mRmbs = mRmbs - ints;
                        mRmbszi = mRmbszi - intszi;
                    } else if (is_selected.equals("2")) {
                        list1.get(position).put("is_selected", "1");
//                        String mu = beanLists.get(position).getCart_mater() + "";//母币
//                        String zi = beanLists.get(position).getCart_son() + "";//子币
                        String mu = mInfoBeen.get(position).getCart_mater() + "";//母币
                        String zi = mInfoBeen.get(position).getCart_son() + "";//子币
                        double ints = Double.parseDouble(mu);
                        double intszi = Double.parseDouble(zi);
                        mRmbs = mRmbs + ints;
                        mRmbszi = mRmbszi + intszi;
                    }
                    mNewAdapter.notifyDataSetChanged();
                    onCheckeds();//判断选中的是否是全部
                    mAllRmb.setText(mDecimalFormat.format(mRmbs) + Mater_name + "\n" + mDecimalFormat.format(mRmbszi) + Son_name);
                }

            }
        });

        mLinearLayout.setOnClickListener(new View.OnClickListener() {//选中
            @Override
            public void onClick(View v) {

                String good_id = (String) list.get(0).get("good_id");
                int good_state = (int) list.get(0).get("good_state");//是否失效  1未失效  0已失效
                Log.e("yh", "good_id--" + good_id + "--是否失效--" + good_state);
                if (good_state == 1) {
//                    Intent intent = new Intent(ShoppingFragment.this.getActivity(), DetailPageActivity.class);
//                    intent.putExtra("good_id", good_id);
//                    startActivity(intent);
                }
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {//删除
            @Override
            public void onClick(View v) {//删除
                new ShowRemindDialog().showRemind(ShoppingFragment.this.getActivity(), "确定", "取消", "提示", "确认删除该商品?", R.drawable.delete_img, new ShowRemindDialog.OnTvClickListener() {
                    @Override
                    public void OnSureClickListener() {
                        int position = (int) list.get(0).get("position");
                        String cart_id = (String) list.get(0).get("cart_id");//购物车id
                        int good_state = (int) list.get(0).get("good_state");//是否失效  1未失效  0已失效
                        Log.e("yh", "是否失效--" + good_state);
                        if (good_state == 1) {
                            onRemove(cart_id, position, 1);//删除购物车商品1未失效  0已失效
                        } else {
                            onRemove(cart_id, position, 0);//删除购物车商品1未失效  0已失效
                        }
                    }
                });


            }
        });

        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int good_state = (int) list.get(0).get("good_state");//是否失效  1未失效  0已失效
                Log.e("yh", "是否失效--" + good_state);
                if (good_state == 1) {
                    String cart_id = (String) list.get(0).get("cart_id");//购物车id
                    int nums = Integer.parseInt(num.getText().toString());
                    if (nums == 1) {
                        num.setText(nums + "");
                    } else {
                        nums = nums - 1;
//                        num.setText(nums + "");
//                    Log.e("yh", "改变数量" + cart_id + "--" + nums);
                        onUpdateNum(cart_id, nums, num);//改变数量(购物车id，数量)
                    }
                }
            }
        });
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int good_state = (int) list.get(0).get("good_state");//是否失效  1未失效  0已失效
                Log.e("yh", "是否失效--" + good_state);
                if (good_state == 1) {
                    String cart_id = (String) list.get(0).get("cart_id");//购物车id
                    int nums = Integer.parseInt(num.getText().toString());

                    if (nums == 99) {
                        num.setText(nums + "");
                    } else {
                        nums = nums + 1;
//                        num.setText(nums + "");
//                    Log.e("yh", "改变数量" + cart_id + "--" + nums);
                        onUpdateNum(cart_id, nums, num);//改变数量(购物车id，数量)
                    }
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

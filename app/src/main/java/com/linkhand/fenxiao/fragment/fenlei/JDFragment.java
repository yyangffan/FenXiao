package com.linkhand.fenxiao.fragment.fenlei;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.OpenGroupActivity;
import com.linkhand.fenxiao.activity.login.LoginActivity;
import com.linkhand.fenxiao.adapter.fenlei.GDAdapter;
import com.linkhand.fenxiao.adapter.fenlei.GDTwoAdapter;
import com.linkhand.fenxiao.dialog.MyDialogVip;
import com.linkhand.fenxiao.feng.fenlei.RightClassFeng;
import com.linkhand.fenxiao.info.InfoData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by djz on 2016/11/10.
 */

public class JDFragment extends BaseFragment {
    String TAG = "yh";
    private View rootView = null;
    private LinearLayout llayout_main = null;

    private LinearLayout.LayoutParams lp_gd = null;
    private LinearLayout.LayoutParams lp_tv = null;
    //    private ArrayList<Category> itemList = null;
    private GDAdapter adapter = null;
    private GDTwoAdapter adapterTwo = null;

    InfoData service;
    String cate_id;//左侧类别id
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.jd_frg_main, null);
        initView();//接收传值
        initRetrofit();
//        updateTitle();

//        //模拟数据
//        for (int i = 0; i < 2; i++) {
//            setData();
//        }
        onMessage();//二级分类（右侧列表）
        return rootView;
    }

    public void initView() {
        llayout_main = (LinearLayout) rootView.findViewById(R.id.llayout_jd_frg_main);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);
        //是否vip  0否  1是

        //获取Fragment传值
        Bundle bundle = getArguments();
        if (bundle != null) {
            cate_id = bundle.getString("cate_id");////左侧类别id
            Log.e("yh", "cate_id---" + cate_id);
        }
    }


//    protected void updateTitle() {
//        if (getArguments() != null) {
//            updateTitle(getArguments().getString("name"),getArguments().getString("cate_id"));
//        }
//    }

    public void updateTitle(String id) {
        Log.e("yh", "id--" + id);

        if (cate_id.equals(id)) {

        } else {
            cate_id = id + "";
            llayout_main.removeAllViews();
            onMessage();//二级分类（右侧列表）
        }

    }

    public void onMessage() {//二级分类（右侧列表）
        Map<String, Object> map = new HashMap<>();
        map.put("pid", cate_id);
        Call<RightClassFeng> call = service.getRightClass(map);
        call.enqueue(new Callback<RightClassFeng>() {
            @Override
            public void onResponse(Call<RightClassFeng> call, Response<RightClassFeng> response) {
                RightClassFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {

                    RightClassFeng.InfoBean beanList = pcfeng.getInfo();
                    if (JDFragment.this.isAdded()) {
                        setData(beanList);
                    }

                } else {
                    Toast.makeText(JDFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RightClassFeng> call, Throwable t) {

            }
        });
    }


    private void setData(RightClassFeng.InfoBean beanList) {
        final List<RightClassFeng.InfoBean.CateBean> cateBeanList = beanList.getCate();
        final List<RightClassFeng.InfoBean.BrandBean> brandBeanList = beanList.getBrand();
        List<String> titleList = beanList.getTitle();//标题文字  0：分类   1：品牌
//        if (itemList == null) {
//            itemList = new ArrayList<Category>();
//            for (int i = 1; i < 11; i++) {
//                itemList.add(new Category("选项 " + i, "" + i));
//            }
//        }

        //高度60dp+行距8dp = 68dp
        int heightUnit = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 68, getResources().getDisplayMetrics());
        int height;

        //计算Gridview总高度
        if (1 % 3 == 0) {
            height = (1 / 3 + 2) * heightUnit;
        } else {
            height = (1 / 3 + 1) * heightUnit;
        }

        if (lp_gd == null)
            lp_gd = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);

        if (lp_tv == null)
            lp_tv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                    , (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                    , 30, getResources().getDisplayMetrics()));
        //类别
        View v = LayoutInflater.from(JDFragment.this.getActivity()).inflate(R.layout.fen_title_item, null);
        TextView tv_title = (TextView) v.findViewById(R.id.fen_reight_title_id);
        tv_title.setText(titleList.get(0));
        llayout_main.addView(v);
//
        MyGridView gridView = new MyGridView(getActivity());
        gridView.setNumColumns(3);
        gridView.setVerticalSpacing(8);
//        gridView.setLayoutParams(lp_gd);
        if (cateBeanList.size() != 0) {
            adapter = new GDAdapter(getActivity(), cateBeanList);
            gridView.setAdapter(adapter);
            llayout_main.addView(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mUserId.equals("")) {//是否登录
                        onLogin();//去登陆
                    } else {
                        Log.e(TAG, "position--" + position + "--id--" + id + "--商品id--" + cateBeanList.get(position).getCate_id());
                        Intent intent = new Intent(JDFragment.this.getActivity(), OpenGroupActivity.class);
                        intent.putExtra("mClassify_id", cateBeanList.get(position).getCate_id() + "");//分类id
                        intent.putExtra("mBrand_id", "");//品牌id
                        intent.putExtra("mKeyword_id", "");//关键字
                        startActivity(intent);
                    }

                }
            });
        }


        //品牌
        View vTwo = LayoutInflater.from(JDFragment.this.getActivity()).inflate(R.layout.fen_title_item, null);
        TextView tv_titles = (TextView) vTwo.findViewById(R.id.fen_reight_title_id);
        tv_titles.setText(titleList.get(1));
        llayout_main.addView(vTwo);
        gridView = new MyGridView(getActivity());
        gridView.setNumColumns(3);
        gridView.setVerticalSpacing(8);
//        gridView.setLayoutParams(lp_gd);
        if (brandBeanList.size() != 0) {
            adapterTwo = new GDTwoAdapter(getActivity(), brandBeanList);
            gridView.setAdapter(adapterTwo);
            llayout_main.addView(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mUserId.equals("")) {//是否登录
                        onLogin();//去登陆
                    } else {
                        Log.e(TAG, "position--" + position + "--id--" + id + "--商品id--" + brandBeanList.get(position).getBrand_id());
                        Intent intent = new Intent(JDFragment.this.getActivity(), OpenGroupActivity.class);
                        intent.putExtra("mClassify_id", "");//分类id
                        intent.putExtra("mBrand_id", brandBeanList.get(position).getBrand_id() + "");//品牌id
                        intent.putExtra("mKeyword_id", "");//关键字
                        startActivity(intent);
                    }
                }
            });
        }
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

    public void onLogin() {//去登陆
        Intent intent = new Intent(JDFragment.this.getActivity(), LoginActivity.class);//登录
        startActivity(intent);
    }

    public void onPrompt() {//提示信息 购买vip
        MyDialogVip dialog = new MyDialogVip(getActivity());
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
//        Toast.makeText(JDFragment.this.getActivity(), "请先购买vip!", Toast.LENGTH_SHORT).show();
    }


}
package com.linkhand.fenxiao.fragment.mine;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.InDetailsActivity;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.mine.InCollectionFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.CollectionInfo;

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
public class IntentionCollectionFragment extends BaseFragment implements CollectionInfo {


    @Bind(R.id.collection_listview)
    ListView mListView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    InfoData service;
    InCollectionAdapter mAdapter;

    public IntentionCollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intention_collection, container, false);
        ButterKnife.bind(this, v);
        initView();
        initRetrofit();
        onMessage(0);//0正常查   1更新数据
        return v;
    }


    public void initView() {
        preferences = IntentionCollectionFragment.this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);
    }

    public void onMessage(final int isPass){
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("type", "2");//1:商品 2：意向
        Call<InCollectionFeng> call = service.getInCollectionOrder(map);
        call.enqueue(new Callback<InCollectionFeng>() {
            @Override
            public void onResponse(Call<InCollectionFeng> call, Response<InCollectionFeng> response) {
                InCollectionFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    List<InCollectionFeng.InfoBean> beanList = pcfeng.getInfo();
                    if (isPass == 0) {//0正常查   1更新数据
                        mAdapter = new InCollectionAdapter(IntentionCollectionFragment.this.getActivity(), beanList);
                        mListView.setAdapter(mAdapter);
                        mAdapter.setOnItemClicks(IntentionCollectionFragment.this);
                    } else if (isPass == 1) {
                        mAdapter.setData(beanList);
                        mAdapter.notifyDataSetChanged();
                    }


                } else {
                    mListView.setAdapter(null);
                    Toast.makeText(IntentionCollectionFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<InCollectionFeng> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
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

    public void CancelCollection(String good_id) {//取消收藏
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("good_id", good_id);
        map.put("type", 2);	//1:商品 2：意向
        Call<ReturnFeng> call = service.getCancelCollection(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(IntentionCollectionFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                    onMessage(1);//0正常查   1更新数据
                } else {
                    Toast.makeText(IntentionCollectionFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemDetailsClicks(RelativeLayout mRelativeLayout, TextView mTextView, final List<Map<String, Object>> list) {
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String good_id = (String) list.get(0).get("good_id");//意向id
                Intent intent = new Intent(IntentionCollectionFragment.this.getActivity(), InDetailsActivity.class);
                intent.putExtra("idea_id",good_id);
                startActivity(intent);
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {//取消收藏
            @Override
            public void onClick(View v) {
                String good_id = (String) list.get(0).get("good_id");//意向id
                CancelCollection(good_id);//取消收藏
            }
        });


    }
}

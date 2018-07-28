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
import com.linkhand.fenxiao.activity.homepage.home.DetailPageActivity;
import com.linkhand.fenxiao.adapter.mine.GroupCollectionAdapter;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.mine.CollectionFeng;
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
public class BulkCollectionFragment extends BaseFragment  implements  CollectionInfo {


    @Bind(R.id.collection_listview)
    ListView mListView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    InfoData service;
    GroupCollectionAdapter mAdapter;

    public BulkCollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bulk_collection, container, false);
        ButterKnife.bind(this, v);
        initView();
        initRetrofit();
        onMessage(0);//0正常查   1更新数据
        return v;
    }

    public void initView() {
        preferences = BulkCollectionFragment.this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);
    }

    public void onMessage(final int isPass){
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("type", "1");//1:团购 2：意向
        Call<CollectionFeng> call = service.getGroupCollectionOrder(map);
        call.enqueue(new Callback<CollectionFeng>() {
            @Override
            public void onResponse(Call<CollectionFeng> call, Response<CollectionFeng> response) {
                CollectionFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    List<CollectionFeng.InfoBean> beanList = pcfeng.getInfo();
                    if (isPass == 0) {//0正常查   1更新数据
                        mAdapter = new GroupCollectionAdapter(BulkCollectionFragment.this.getActivity(), beanList);
                        if(mListView!=null){
                            mListView.setAdapter(mAdapter);
                            mAdapter.setOnItemClicks(BulkCollectionFragment.this);
                        }
                    } else if (isPass == 1) {
                        mAdapter.setData(beanList);
                        mAdapter.notifyDataSetChanged();
                    }


                } else {
                    mListView.setAdapter(null);
                    Toast.makeText(BulkCollectionFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CollectionFeng> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
        map.put("type", 1);	//1:商品 2：意向
        Call<ReturnFeng> call = service.getCancelCollection(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(BulkCollectionFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                    onMessage(1);//0正常查   1更新数据
                } else {
                    Toast.makeText(BulkCollectionFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
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
                String good_id = (String) list.get(0).get("good_id");//商品id
                Intent intent = new Intent(BulkCollectionFragment.this.getActivity(), DetailPageActivity.class);
                intent.putExtra("good_id", good_id);
                startActivity(intent);
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {//取消收藏
            @Override
            public void onClick(View v) {
                final String good_id = (String) list.get(0).get("good_id");//商品id
                new ShowRemindDialog().showRemind(BulkCollectionFragment.this.getActivity(), "确定", "取消", "", "确认取消收藏?", R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
                    @Override
                    public void OnSureClickListener() {
                        CancelCollection(good_id);//取消收藏
                    }
                });
            }
        });


    }

}

package com.linkhand.fenxiao.activity.mine;


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
import android.widget.Toast;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.mine.ThreeDeckFragmentAdapter;
import com.linkhand.fenxiao.feng.mine.MyTeamFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.info.callback.MineMembersInfo;

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
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends BaseFragment implements MineMembersInfo {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ListView mListView;
    ThreeDeckFragmentAdapter mAdapter;
    InfoData service;
    String mUserId;//个人id

    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_three, container, false);
        init(v);
        initRetrofit();
        onMessage();
        return v;
    }

    public void init(View v) {
        mListView = (ListView) v.findViewById(R.id.threedeck_lv_id);
        preferences = ThreeFragment.this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<MyTeamFeng> call = service.getMyTeam(map);
        call.enqueue(new Callback<MyTeamFeng>() {
            @Override
            public void onResponse(Call<MyTeamFeng> call, Response<MyTeamFeng> response) {
                MyTeamFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                        List<MyTeamFeng.InfoBean._$2Bean>  threeList=  pcfeng.getInfo().get_$2();
                        mAdapter = new ThreeDeckFragmentAdapter(ThreeFragment.this.getActivity(), threeList);
                        mListView.setAdapter(mAdapter);
                        mAdapter.setOnItemClicks(ThreeFragment.this);

                } else {
                    Toast.makeText(ThreeFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MyTeamFeng> call, Throwable t) {

            }
        });

    }

    @Override
    public void onItemClicks(RelativeLayout mRelativeLayout, final List<Map<String, Object>> list) {
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id= (String) list.get(0).get("user_id");
                Intent intent = new Intent(ThreeFragment.this.getActivity(), MembersDetailActivity.class);//成员详情页
                intent.putExtra("user_id",user_id);
                startActivity(intent);
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
}

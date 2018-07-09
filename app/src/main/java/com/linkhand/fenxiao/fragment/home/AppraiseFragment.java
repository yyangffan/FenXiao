package com.linkhand.fenxiao.fragment.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.AppraiseFragmentAdapter;
import com.linkhand.fenxiao.feng.home.IdeaGoodsDetailsFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.MyListView;

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
public class AppraiseFragment extends BaseFragment {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    MyListView mListView;
    AppraiseFragmentAdapter mAdapter;
    String mUserId; //获取个人id
    String mMoodId;//意向商品id
    InfoData service;
    IdeaGoodsDetailsFeng.InfoBean bean;//商品数据
    @Bind(R.id.all_evaluation)
    Button mAllEvaluation;//查看全部评价

    public AppraiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appraise, container, false);
        ButterKnife.bind(this, v);
        initView(v);
        initRetrofit();
        onMessage();
        return v;
    }

    public void initView(View v) {
        mListView = (MyListView) v.findViewById(R.id.appraise_listview_id);
        preferences = AppraiseFragment.this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);

        //获取Fragment传值
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMoodId = bundle.getString("mMoodId");//意向商品id
            Log.e("yh", "意向商品id--" + mMoodId);
        }
    }

    public void onMessage() {
        Log.e("yh", "意向商品id---" + mMoodId + "--mUserId--" + mUserId);
        Map<String, Object> map = new HashMap<>();
        map.put("idea_id", mMoodId);//商品id
        map.put("user_id", mUserId);
        Call<IdeaGoodsDetailsFeng> call = service.getIdeaGoodsDetails(map);
        call.enqueue(new Callback<IdeaGoodsDetailsFeng>() {
            @Override
            public void onResponse(Call<IdeaGoodsDetailsFeng> call, Response<IdeaGoodsDetailsFeng> response) {
                IdeaGoodsDetailsFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                if (code == 100) {
                    bean = pcfeng.getInfo();
                    List<IdeaGoodsDetailsFeng.InfoBean.EvalBean> evalbean = bean.getEval();//全部评论
                    Log.e("yh", "mMoodId---" + mMoodId + "--mUserId--" + mUserId + "--size--" + evalbean.size());
//                    mAdapter = new AppraiseFragmentAdapter(AppraiseFragment.this.getActivity(), evalbean);
//                    mListView.setAdapter(mAdapter);
                } else {
//                    Toast.makeText(AppraiseFragment.this.getActivity(), "xx", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<IdeaGoodsDetailsFeng> call, Throwable t) {

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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

package com.linkhand.fenxiao.fragment.mine;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.mine.MembersDetailActivity;
import com.linkhand.fenxiao.adapter.mine.OneDeckFragmentAdapter;
import com.linkhand.fenxiao.info.callback.MineMembersInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlreadyRegistrationFragment extends BaseFragment implements MineMembersInfo {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ListView mListView;

    public AlreadyRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_already_registration, container, false);
        init(v);
        onMessage();
        return v;
    }


    public void init(View v) {
        mListView = (ListView) v.findViewById(R.id.AlreadyRegistration_lv_id);

        preferences = AlreadyRegistrationFragment.this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    OneDeckFragmentAdapter mAdapter;
    public void onMessage() {
        List<Map<String, Object>> mList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("tu", "http://pic.58pic.com/58pic/14/34/62/39S58PIC9jV_1024.jpg");
        map.put("name", "name");//被邀请的人
        map.put("referrer", "referrer");//推荐人
        map.put("is_pass", "1");//1充值了的  2没充值的已注册
        mList.add(map);
        map = new HashMap<>();
        map.put("tu", "http://pic.58pic.com/58pic/14/34/62/39S58PIC9jV_1024.jpg");
        map.put("name", "name");//被邀请的人
        map.put("referrer", "referrer");//推荐人
        map.put("is_pass", "2");//1充值了的  2没充值的已注册
        mList.add(map);
//        mAdapter = new OneDeckFragmentAdapter(AlreadyRegistrationFragment.this.getActivity(), mList);
//        mListView.setAdapter(mAdapter);
//        mAdapter.setOnItemClicksAlreadyRegistration(AlreadyRegistrationFragment.this);

    }

    @Override
    public void onItemClicks(RelativeLayout mRelativeLayout, List<Map<String, Object>> list) {
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlreadyRegistrationFragment.this.getActivity(), MembersDetailActivity.class);//成员详情页
                startActivity(intent);
            }
        });

    }
}

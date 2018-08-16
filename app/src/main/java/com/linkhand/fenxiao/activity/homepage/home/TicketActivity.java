package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.TuanGRcAdapter;
import com.linkhand.fenxiao.dialog.EveryDialog;
import com.linkhand.fenxiao.feng.home.GroupListFeng;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.fragment.DividerGridItemDecoration;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.utils.util.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/********************************************************************
 @version: 1.0.0
 @description: 返券专区--Adapter可以使用主页HomePageFragment的推荐商品中的HomePageFragmentAdapter
 @author: user
 @time: 2018/4/21 15:58
 @变更历史:
 ********************************************************************/
public class TicketActivity extends BaseActicity {

    @Bind(R.id.open_group_return_id)
    LinearLayout mReturn;
    @Bind(R.id.activity_ticket)
    LinearLayout mTicket;
    SharedPreferences preferences;
    String mUserId;//个人id
    @Bind(R.id.ticket_recy)
    RecyclerView mTicketRecy;
    @Bind(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;
    @Bind(R.id.imgv_what)
    ImageView mImgvWhat;

    private List<GroupListFeng.InfoBean> mInfoBeanList;
    private TuanGRcAdapter mTuanGRcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        ButterKnife.bind(this);
        ininEver();
        initView();
        onMessage();
        if(preferences.getBoolean("fanquan_show",true)) {
            getMessage();
        }
    }

    public void ininEver() {
        mSmartRefresh.setEnableRefresh(false);
        mSmartRefresh.setEnableLoadmore(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mTicketRecy.setLayoutManager(gridLayoutManager);
        mTicketRecy.addItemDecoration(new DividerGridItemDecoration(this, R.drawable.gray_juxing));
        mInfoBeanList = new ArrayList<>();
        mTuanGRcAdapter = new TuanGRcAdapter(this, mInfoBeanList);
        mTicketRecy.setAdapter(mTuanGRcAdapter);
        mTuanGRcAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                String id = mInfoBeanList.get(position).getGood_id();//(商品id)
                Intent intent = new Intent(TicketActivity.this, DetailPageActivity.class);
                intent.putExtra("good_id", id);
                startActivity(intent);
            }
        });
    }

    public void initView() {
        preferences = TicketActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
        mUserId = preferences.getString("user_id", "");
    }

    @OnClick({R.id.open_group_return_id,R.id.imgv_what})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_group_return_id:
                this.finish();
                break;
            case R.id.imgv_what:
                getMessage();
                break;
        }
    }

    /*获取说明内容*/
    public void getMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("get_type", "3");
        Call<HttpResponse> call = service.getDescGet(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    new EveryDialog().showRemind(TicketActivity.this, "（不在提示）", "确定", "说明", httpResponse.getInfo(), new EveryDialog.OnTvClickListener() {
                        @Override
                        public void OnSureClickListener() {
                            editor.putBoolean("fanquan_show",false).commit();
                        }
                    });

                } else {
                    ToastUtil.showToast(TicketActivity.this, httpResponse.getSuccess());
                }

            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(TicketActivity.this, "网络异常");
            }
        });
    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("is_fan", "1");
        Call<GroupListFeng> call = service.getGroupList(map);
        call.enqueue(new Callback<GroupListFeng>() {
            @Override
            public void onResponse(Call<GroupListFeng> call, Response<GroupListFeng> response) {
                GroupListFeng pcfeng = response.body();
                int code = pcfeng.getCode();
                mInfoBeanList.clear();
                if (code == 100) {
                    mInfoBeanList.addAll(pcfeng.getInfo());
                } else {
                    Toast.makeText(TicketActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
                }
                mTuanGRcAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GroupListFeng> call, Throwable t) {
                ToastUtil.showToast(TicketActivity.this, "网络异常");
            }
        });
    }
}

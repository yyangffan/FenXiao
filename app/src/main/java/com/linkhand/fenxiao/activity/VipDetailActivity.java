package com.linkhand.fenxiao.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.views.MyWevClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/********************************************************************
 @version: 1.0.0
 @description: vip说明界面
 @author: 杨帆
 @time: 2018/8/10 14:18
 @变更历史:
 ********************************************************************/

public class VipDetailActivity extends BaseActicity {

    @Bind(R.id.open_group_return_id)
    LinearLayout mOpenGroupReturnId;
    @Bind(R.id.vip_detail_wv)
    WebView mVipDetailWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_detail);
        ButterKnife.bind(this);
        initEver();
    }

    @OnClick({R.id.open_group_return_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_group_return_id:
                this.finish();
                break;
        }
    }

    public void initEver() {
        mVipDetailWv.getSettings().setJavaScriptEnabled(true);
        mVipDetailWv.setWebViewClient(new MyWevClient());
        getMessage();

    }

    /*获取内容*/
    public void getMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("get_type", "1");
        Call<HttpResponse> call = service.getDescGet(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    mVipDetailWv.loadDataWithBaseURL(null, httpResponse.getInfo(), "text/html", "utf-8", null);
                } else {
                    ToastUtil.showToast(VipDetailActivity.this, httpResponse.getSuccess());
                }

            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(VipDetailActivity.this, "网络异常");
            }
        });
    }

}

package com.linkhand.fenxiao.activity.news;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.home.MessageDetailResponse;
import com.linkhand.fenxiao.utils.DateUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailsActivity extends BaseActicity implements View.OnClickListener {

    @Bind(R.id.fenxiao_return_id4)
    LinearLayout mReturn;
    @Bind(R.id.news_time_id)
    TextView mNewsTimeId;
    @Bind(R.id.news_content_id)
    TextView mNewsContentId;
    private String mTrade_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        initView();
        getData();
    }

    public void initView() {
        mReturn.setOnClickListener(this);//返回
        Intent intent = this.getIntent();
        if (intent != null) {
            mTrade_id = intent.getStringExtra("trade_id");
        }


    }

    /*获取单条消息数据*/
    public void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("trade_id", mTrade_id);
        Call<MessageDetailResponse> call = service.getMessageDetailList(map);
        call.enqueue(new Callback<MessageDetailResponse>() {
            @Override
            public void onResponse(Call<MessageDetailResponse> call, Response<MessageDetailResponse> response) {
                MessageDetailResponse messageDetailResponse = response.body();
                if (messageDetailResponse.getCode() == 100) {
                    MessageDetailResponse.InfoBean info = messageDetailResponse.getInfo();
                    mNewsTimeId.setText(DateUtil.getStrTime(info.getTrade_time()));
                    mNewsContentId.setText(info.getTrade_text());
                    toTellRead();
                } else {
                    Toast.makeText(NewsDetailsActivity.this, messageDetailResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageDetailResponse> call, Throwable t) {
                Toast.makeText(NewsDetailsActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*告诉服务器该条已阅读*/
    public void toTellRead() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("trade_id", mTrade_id);
        map.put("all_look", "0");//所有修改：1      单条修改：0
        Call<HttpResponse> call = service.getMessageIsRead(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    Log.i("消息状态修改成功", httpResponse.getSuccess());
                } else {
                    Log.i("消息状态修改失败", httpResponse.getSuccess());
                }

            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenxiao_return_id4://返回
                this.finish();
        }
    }
}

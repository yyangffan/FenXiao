package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.bean.MienDainZanBean;
import com.linkhand.fenxiao.bean.MienDetailBean;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.utils.DateUtil;
import com.linkhand.fenxiao.views.MyWevClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MienDetailsAcyivity extends BaseActicity {

    @Bind(R.id.open_group_return_id)
    LinearLayout mOpenGroupReturnId;
    @Bind(R.id.llayout_one)
    LinearLayout mLlayoutOne;
    @Bind(R.id.details_id)
    TextView mDetailsId;
    @Bind(R.id.llayout_two)
    LinearLayout mLlayoutTwo;
    @Bind(R.id.activity_mien_details_acyivity)
    RelativeLayout mActivityMienDetailsAcyivity;
    @Bind(R.id.mien_detail_title)
    TextView mMienDetailTitle;
    @Bind(R.id.mien_detail_renNum)
    TextView mMienDetailRenNum;
    @Bind(R.id.mien_detail_wb)
    WebView mMienDetailWb;
    @Bind(R.id.llayout)
    LinearLayout mLlayout;
    @Bind(R.id.mien_details_imgv)
    ImageView mMienDetailsImgv;
    @Bind(R.id.mien_detail_botNum)
    TextView mMienDetailBotNum;
    @Bind(R.id.mien_imgv_what)
    ImageView mMienImgvWhat;

    private String mAr_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mien_details_acyivity);
        ButterKnife.bind(this);
        initWhat();
    }

    public void initWhat() {
        Intent intent = this.getIntent();
        if (intent != null) {
            mAr_id = intent.getStringExtra("ar_id");
        }
        onMessage();
    }

    @OnClick({R.id.open_group_return_id, R.id.llayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_group_return_id:
                this.finish();
                break;
            case R.id.llayout:
                Dianzan();
                break;
        }
    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("ar_id", mAr_id);
        map.put("user_id", mUserId);
        Call<MienDetailBean> call = service.getMienDetailMsg(map);
        call.enqueue(new Callback<MienDetailBean>() {
            @Override
            public void onResponse(Call<MienDetailBean> call, Response<MienDetailBean> response) {
                MienDetailBean mienDetailBean = response.body();
                if (mienDetailBean.getCode() == 100) {
                    setMsg(mienDetailBean.getInfo());
                } else {
                    Toast.makeText(MienDetailsAcyivity.this, mienDetailBean.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MienDetailBean> call, Throwable t) {
                Toast.makeText(MienDetailsAcyivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setMsg(MienDetailBean.InfoBean infoBean) {
        mMienDetailTitle.setText(infoBean.getAr_title());
        mDetailsId.setText(infoBean.getAr_add_time() == null || infoBean.getAr_add_time().equals("") ? "" : DateUtil.getStrTime(infoBean.getAr_add_time()));
        mMienDetailRenNum.setText(infoBean.getPraise());
        mMienDetailBotNum.setText(infoBean.getPraise());
        mMienDetailWb.getSettings().setJavaScriptEnabled(true);
        String content = infoBean.getAr_content();
        mMienDetailWb.setWebViewClient(new MyWevClient());
        mMienDetailWb.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
//        mMienDetailWb.loadDataWithBaseURL(null, infoBean.getAr_content(), "text/html", "utf-8", null);
        int is_praise = infoBean.getIs_praise();
        if (is_praise == 1) {
            mMienDetailsImgv.setImageResource(R.drawable.heart_red);
            mMienImgvWhat.setImageResource(R.drawable.heart_red);
        } else {
            mMienDetailsImgv.setImageResource(R.drawable.heart_gray);
            mMienImgvWhat.setImageResource(R.drawable.heart_gray);
        }
    }

    /*进行点赞*/
    public void Dianzan() {
        Map<String, Object> map = new HashMap<>();
        map.put("ar_id", mAr_id);
        map.put("user_id", mUserId);
        Call<MienDainZanBean> call = service.getMienDianZan(map);
        call.enqueue(new Callback<MienDainZanBean>() {
            @Override
            public void onResponse(Call<MienDainZanBean> call, Response<MienDainZanBean> response) {
                MienDainZanBean mienDainZanBean = response.body();
                if (mienDainZanBean.getCode() == 100) {
                    ShowRemindDialog showRemindDialog = new ShowRemindDialog();
                    showRemindDialog.showRemind(MienDetailsAcyivity.this, "好的", "", "提示", mienDainZanBean.getSuccess(), R.drawable.money, new ShowRemindDialog.OnTvClickListener() {
                        @Override
                        public void OnSureClickListener() {
                            onMessage();
                        }
                    });
                } else if (mienDainZanBean.getCode() == 400) {
                    Toast.makeText(MienDetailsAcyivity.this, mienDainZanBean.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MienDainZanBean> call, Throwable t) {
                Toast.makeText(MienDetailsAcyivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });


    }

}

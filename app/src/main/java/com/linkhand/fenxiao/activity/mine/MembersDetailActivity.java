package com.linkhand.fenxiao.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.mine.TeamMessageFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.DateUtil;
import com.linkhand.fenxiao.utils.RoundImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MembersDetailActivity extends BaseActicity implements View.OnClickListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout mTelephone;//电话(联系)
    TextView mTelephoneNumber;//电话号码
    LinearLayout mReturn;//返回
    String mId = ""; //获取点击的user_id
    @Bind(R.id.members_name_id)
    TextView mName;//用户名
    @Bind(R.id.members_login_time)
    TextView mLoginTime;//注册时间
    @Bind(R.id.members_tup_up)
    TextView mTupUp;//充值
    @Bind(R.id.members_top_name)
    TextView mTopName;//推荐人名
    @Bind(R.id.members_recommend_number)
    TextView mRecommendNumber;//推荐人数

    @Bind(R.id.roundImageView_id2)
    RoundImageView mImageView;//头像
    @Bind(R.id.members_tier_id)
    TextView mTier;//级别
    @Bind(R.id.members_names_id)
    TextView mNames;//名称

    InfoData service;
    @Bind(R.id.member_detail_jibierenshu)
    TextView mMemberDetailJibierenshu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_detail);
        ButterKnife.bind(this);
        init();
        initRetrofit();
        onClicks();
        onMessage();
    }

    public void init() {
        mTelephone = (LinearLayout) findViewById(R.id.telephone_llayout_id);//电话(联系)
        mReturn = (LinearLayout) findViewById(R.id.fenxiao_return_id8);//返回
        mTelephoneNumber = (TextView) findViewById(R.id.telephone_number_id);//电话号码


        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();

        //取得从上一个Activity当中传递过来的Intent对象
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            mId = intent.getStringExtra("user_id"); //获取点击的user_id
            Log.e("yh", "mId---" + mId);
        }
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);//返回
        mTelephone.setOnClickListener(this);//电话
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenxiao_return_id8://返回
                this.finish();
                break;
            case R.id.telephone_llayout_id://电话(联系)
                String number = mTelephoneNumber.getText() + "";
                if (number.equals("")) {

                } else {
                    call(number);
                }

                break;
        }
    }


    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mId);
        Call<TeamMessageFeng> call = service.getMyTeamMessage(map);
        call.enqueue(new Callback<TeamMessageFeng>() {
            @Override
            public void onResponse(Call<TeamMessageFeng> call, Response<TeamMessageFeng> response) {
                TeamMessageFeng pcfeng = response.body();
                Log.e("yh", "pcfeng--" + pcfeng);
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    TeamMessageFeng.InfoBean bean = pcfeng.getInfo();
                    String name = bean.getUser_name();//名称
                    String user_tel = bean.getUser_tel();//手机号
                    String thumb = bean.getUser_head_img();//头像
                    String top_name = bean.getTop_name();//上级姓名
                    String user_grade = bean.getUser_grade();//团队层级
                    String user_sign_time = bean.getSign_time();//注册时间
                    String mUser_referr1 = "";
                    String mUser_referr2 = "";
                    String mUser_referr3 = "";

                    if (bean.getRef_head_img() != null && bean.getRef_head_img().size() != 0) {
                        //推荐人1
                        mUser_referr1 = bean.getRef_head_img().get(0);
                        mUser_referr2 = bean.getRef_head_img().get(1);//推荐人2
                        mUser_referr3 = bean.getRef_head_img().get(2);//推荐人3
                    }

                    mName.setText(name);
                    mNames.setText(name);
                    mTelephoneNumber.setText(user_tel);//手机号
                    mLoginTime.setText(DateUtil.getStrTime(user_sign_time) + "注册");//注册时间
                    mTopName.setText(top_name);//推荐人名
                    mTier.setText(bean.getRebate_str());//级别
                    mRecommendNumber.setText(bean.getRef_num());//推荐人数
                    if (bean.getUser_is_vip().equals("1")) {
                        mTupUp.setText("已充值");//充值
                    } else {
                        mTupUp.setText("未充值");//充值
                    }
                    mMemberDetailJibierenshu.setText(bean.getTeam_num());
                    if (thumb.equals("") | thumb.equals("null")) {

                    } else {
                        thumb = C.TU + thumb;
                        RequestOptions requestOptions=new RequestOptions();
                        requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
                        Glide.with(MembersDetailActivity.this)
                                .load(thumb)
                                .apply(requestOptions)
                                .into(mImageView);//头像
                    }
                } else {
                    Toast.makeText(MembersDetailActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TeamMessageFeng> call, Throwable t) {

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


    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        //跳过拨号界面，直接拨打电话
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//        startActivity(intent);
    }

}

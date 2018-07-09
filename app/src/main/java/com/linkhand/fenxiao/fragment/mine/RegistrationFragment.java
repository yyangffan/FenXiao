package com.linkhand.fenxiao.fragment.mine;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.AllConfigFeng;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.ToastUtil;

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

/**
 * 提现
 */
public class RegistrationFragment extends BaseFragment implements View.OnClickListener {
    EditText mRecharge;
    TextView mDetermine;//确定
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Bind(R.id.note)
    TextView mNote;
    private String mUserId;
    InfoData service;
    private String mMoney;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        initRetrofit();
        init(v);
        onClicks();
        onGuangBiao();//光标不显
        ButterKnife.bind(this, v);
        return v;
    }

    public void init(View v) {
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        mRecharge = (EditText) v.findViewById(R.id.mine_registration_et_id);
        mDetermine = (TextView) v.findViewById(R.id.mine_registration_determine_id);//确定

        getNote();
    }

    public void onClicks() {
        mDetermine.setOnClickListener(this);//确定
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_registration_determine_id:
                mMoney = mRecharge.getText().toString();
                if (mMoney == null || mMoney.equals("")) {
                    ToastUtil.showToast(this.getActivity(), "请输入提现金额");
                    return;
                }
                toTiXian(mMoney);
                break;

        }

    }

    //提现
    public void toTiXian(String money) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("son_money", money);
        Call<HttpResponse> call = service.getTiXian(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                final HttpResponse httpResponse = response.body();
                int code = httpResponse.getCode();
                if (code == 100) {
                    ToastUtil.showToast(RegistrationFragment.this.getActivity(), httpResponse.getSuccess());
                    RegistrationFragment.this.getActivity().finish();
                } else if(code==301){
                    new ShowRemindDialog().showRemind(RegistrationFragment.this.getActivity(), "确定", "", "提示", httpResponse.getSuccess(), 0, new ShowRemindDialog.OnTvClickListener() {
                        @Override
                        public void OnSureClickListener() {
                            mRecharge.setText("");
                        }
                    });

                }else{
                    Toast.makeText(RegistrationFragment.this.getActivity(), httpResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Toast.makeText(RegistrationFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onGuangBiao() {
        mRecharge.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
        // 编辑框设置触摸监听
        mRecharge.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    mRecharge.setCursorVisible(true);// 再次点击显示光标
                }

                return false;
            }

        });

    }

    /*获取提现的说明*/
    public void getNote() {
        Call<AllConfigFeng> call = service.getAllConfig(new HashMap<String, Object>());
        call.enqueue(new Callback<AllConfigFeng>() {
            @Override
            public void onResponse(Call<AllConfigFeng> call, Response<AllConfigFeng> response) {
                AllConfigFeng allConfigFeng = response.body();
                int code = allConfigFeng.getCode();
                if (code == 100) {
                    String presen = allConfigFeng.getInfo().getPresen();//提现说明
                    mNote.setText(presen);

                }

            }

            @Override
            public void onFailure(Call<AllConfigFeng> call, Throwable t) {
                Toast.makeText(RegistrationFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
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

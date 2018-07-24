package com.linkhand.fenxiao.activity.homepage.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.dialog.MyDialogApproveFace;
import com.linkhand.fenxiao.dialog.MyDialogApproveWrong;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.login.Register;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApproveActivity extends BaseActicity implements View.OnClickListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Bind(R.id.fenxiao_return_id)
    LinearLayout mReturn;
    @Bind(R.id.approve_name)
    EditText mApproveName;//姓名
    @Bind(R.id.approve_number)
    EditText mApproveNumber;//身份证号码
    @Bind(R.id.approve_phone)
    EditText mApprovePhone;//手机号
    @Bind(R.id.approve_verify)
    EditText mApproveVerify;//验证码
    @Bind(R.id.approve_send)
    TextView mApproveSend;//发送验证码
    @Bind(R.id.approve_confirm)
    TextView mApproveConfirm;//确定
    @Bind(R.id.apporve_imgv_over)
    ImageView mApporveImgvOver;
    @Bind(R.id.apporve_imgv_bot)
    ImageView mApporveImgvBot;
    @Bind(R.id.fenxiao_title_id)
    TextView mFenxiaoTitleId;
    @Bind(R.id.approve_rel_over)
    RelativeLayout mApproveRelOver;
    @Bind(R.id.approve_rel_bot)
    RelativeLayout mApproveRelBot;
    @Bind(R.id.approve_tv_over)
    TextView mApproveTvOver;
    @Bind(R.id.approve_tv_bot)
    TextView mApproveTvBot;

    InfoData service;
    String mUserId;//个人id
    private boolean mRunning;
    private String mPhone = "";
    private int isWhat = 0;
    private String imgv_over = "";
    private String imgv_bot = "";
    private static final String TAG = "ApproveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
    }

    public void initView() {
        mFenxiaoTitleId.requestFocus();
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        mPhone = preferences.getString("phone", "");
        mApprovePhone.setText(mPhone);
        mApproveConfirm.setOnClickListener(this);
        mApproveSend.setOnClickListener(this);
        mReturn.setOnClickListener(this);
        mApporveImgvOver.setOnClickListener(this);
        mApporveImgvBot.setOnClickListener(this);
        mApproveRelOver.setOnClickListener(this);
        mApproveRelBot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.approve_send://发送验证码
                String s = mApprovePhone.getText().toString();
                if (s == null || s.equals("")) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (s.length() != 11) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                getCode(s);
                break;
            case R.id.approve_confirm://确定
                onConfirm();//确定
                break;
            case R.id.fenxiao_return_id:
                this.finish();
                break;
            case R.id.apporve_imgv_over://身份证正面
            case R.id.approve_rel_over:
                isWhat = 0;
                botDialog();
                break;
            case R.id.apporve_imgv_bot://身份证反面
            case R.id.approve_rel_bot:
                isWhat = 1;
                botDialog();
                break;
        }
    }

    public void onConfirm() {
        String name = mApproveName.getText().toString() + "";//姓名
        String number = mApproveNumber.getText().toString() + "";//身份证号码
        String code = mApproveVerify.getText().toString();//验证码
        String phone = mApprovePhone.getText().toString();//手机号

        if (name.equals("") || number.equals("") || phone.equals("") || imgv_over.equals("") || imgv_bot.equals("")) {
            Toast.makeText(this, "请填全信息", Toast.LENGTH_SHORT).show();
        } else {
            if (code == null || code.equals("")) {
                Toast.makeText(this, "请填写验证码", Toast.LENGTH_SHORT).show();
            } else {
//                onMessage(name, number, code, phone);//实名认证   (姓名,身份证号码)
                mApproveConfirm.setEnabled(false);
                mApproveConfirm.setText("提交中,请稍候...");
                mApproveConfirm.setBackground(ApproveActivity.this.getResources().getDrawable(R.drawable.btn_bg_rounded_six));
                mApproveConfirm.setTextColor(ApproveActivity.this.getResources().getColor(R.color.white));
                toUpMessage(name, number, code, phone);//
            }
        }
    }

    public void toUpMessage(String name, String number, String code, String phone) {
        File file_over = new File(imgv_over);
        File file_bot = new File(imgv_bot);
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("evaluate_user_id", mUserId)//会员id
//                .addFormDataPart("evaluate_good_id", "7")//	商品id
//                .addFormDataPart("evaluate_good_name", "阶梯价格商品")//商品名称
//                .addFormDataPart("evaluate_content", "xx")  //	评价内容
//                .addFormDataPart("eval_imgs", mYuPathQQ, RequestBody.create(MediaType.parse("image/*"), file))
                .addFormDataPart("user_id", mUserId)
                .addFormDataPart("real_name", name)//真实姓名
                .addFormDataPart("user_card", number)//身份证
                .addFormDataPart("user_tel", phone)//手机号
                .addFormDataPart("code", code)//验证码
                .addFormDataPart("user_before_card", imgv_over, RequestBody.create(MediaType.parse("image/*"), file_over))
                .addFormDataPart("user_behind_card", imgv_bot, RequestBody.create(MediaType.parse("image/*"), file_bot))
                .build();
        Log.e("身份认证上传参数", "?user_id=" + mUserId + "&real_name=" + name + "&user_card=" + number + "&user_tel=" + phone + "&code=" + code + "&user_before_card=" + imgv_over + "&user_behind_card=" + imgv_bot);
        Call<ReturnFeng> call = service.getNewApprove(requestBody);
//        Call<ReturnFeng> call = service.upLoadComments(requestBody);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(ApproveActivity.this, success, Toast.LENGTH_SHORT).show();
                    //是否认证  0否  1是
                    editor.putString("userReal", "1");
                    editor.commit();
                    onFace();//认证成功
                } else {
                    Toast.makeText(ApproveActivity.this, success, Toast.LENGTH_SHORT).show();
                    onWrong();//认证失败
                }
                mApproveConfirm.setEnabled(true);
                mApproveConfirm.setText("确定");
                mApproveConfirm.setBackground(ApproveActivity.this.getResources().getDrawable(R.drawable.btn_bg_rounded_two));
                mApproveConfirm.setTextColor(ApproveActivity.this.getResources().getColor(R.color.white));
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                mApproveConfirm.setEnabled(true);
                mApproveConfirm.setText("确定");
                mApproveConfirm.setBackground(ApproveActivity.this.getResources().getDrawable(R.drawable.btn_bg_rounded_two));
                mApproveConfirm.setTextColor(ApproveActivity.this.getResources().getColor(R.color.white));
                ToastUtil.showToast(ApproveActivity.this, "网络异常");
            }
        });

    }

    /*弹出底部图片选项*/
    private void botDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(ApproveActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .enableCrop(true)
                        .showCropFrame(true)
                        .showCropGrid(true)
                        .freeStyleCropEnabled(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                popupWindow.dismiss();

            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(ApproveActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)
                        .isCamera(false)
                        .selectionMode(PictureConfig.SINGLE)
                        .enableCrop(true)
                        .showCropFrame(true)
                        .showCropGrid(true)
                        .freeStyleCropEnabled(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    /*获取验证码*/
    public void getCode(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);//电话
        map.put("type", "3");//3实名认证  2为忘记密码   1为注册
        Call<Register> call = service.getRegister(map);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Register pcfeng = response.body();
                if(pcfeng.getCode()==100){
                    if (mRunning) {
                    } else {
                        downTimer.start();
                    }
                }
                Toast.makeText(ApproveActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(ApproveActivity.this, "获取失败,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onFace() {//认证成功
        MyDialogApproveFace dialog = new MyDialogApproveFace(ApproveActivity.this);
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
    }

    public void onWrong() { //认证失败
        MyDialogApproveWrong dialog = new MyDialogApproveWrong(ApproveActivity.this);
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
    }

    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTick(long l) {
            mRunning = true;
//            mApproveSend.getResources().getColor(R.color.colorgraynessd, null);
            mApproveSend.setTextColor(ApproveActivity.this.getResources().getColor(R.color.colorgraynessd));
            mApproveSend.setText((l / 1000) + "秒后重发");
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onFinish() {
            mRunning = false;
//            mApproveSend.getResources().getColor(R.color.colorred, null);
            mApproveSend.setTextColor(ApproveActivity.this.getResources().getColor(R.color.colorred));
            mApproveSend.setText("重新发送");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (isWhat == 0) {
                        RequestOptions requestOptions=new RequestOptions();
                        requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
                        Glide.with(ApproveActivity.this).load(selectList.get(0).getCutPath()).apply(requestOptions).into(mApporveImgvOver);
                        imgv_over = selectList.get(0).getCutPath();
                        mApproveTvOver.setVisibility(View.GONE);
                        mApporveImgvOver.setVisibility(View.VISIBLE);
                        Log.e(TAG, "onActivityResult: " + imgv_over);
                    } else {
                        RequestOptions requestOptions=new RequestOptions();
                        requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
                        Glide.with(ApproveActivity.this).load(selectList.get(0).getCutPath()).apply(requestOptions).into(mApporveImgvBot);
                        imgv_bot = selectList.get(0).getCutPath();
                        Log.e(TAG, "onActivityResult: " + imgv_bot);
                        mApproveTvBot.setVisibility(View.GONE);
                        mApporveImgvBot.setVisibility(View.VISIBLE);
                    }
                    break;
            }


        }
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

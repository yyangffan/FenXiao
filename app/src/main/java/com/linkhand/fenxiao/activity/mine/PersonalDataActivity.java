package com.linkhand.fenxiao.activity.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.BuildConfig;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.dialog.MyDialogPerfect;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.mine.PersonalMessageFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.ClipImageActivity;
import com.linkhand.fenxiao.utils.util.FileUtil;
import com.linkhand.fenxiao.utils.view.CircleImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.linkhand.fenxiao.R.id.mine_llayout_name_id;
import static com.linkhand.fenxiao.utils.util.FileUtil.getRealFilePathFromUri;


public class PersonalDataActivity extends BaseActicity implements View.OnClickListener {
    @Bind(R.id.mine_llayoutreturn_id)
    LinearLayout mReturn;
    @Bind(R.id.mine_civ_id)
    CircleImageView mCiv;
    @Bind(R.id.mine_llayout_tu_id)
    LinearLayout mLlayoutTu;
    @Bind(R.id.mine_tv_name_id)
    TextView mTvName;
    @Bind(mine_llayout_name_id)
    LinearLayout mLlayoutName;
    @Bind(R.id.mine_tv_phone_id)
    TextView mTvPhone;
    @Bind(R.id.mine_llayout_phone_id)
    LinearLayout mLlayoutPhone;
    @Bind(R.id.mine_tv_number_id)
    TextView mTvNumber;
    @Bind(R.id.mine_llayout_number_id)
    LinearLayout mLlayoutNumber;
    @Bind(R.id.mine_tv_wx_id)
    TextView mWxText;
    @Bind(R.id.mine_llayout_wx_id)
    LinearLayout mWxLlayout;
    @Bind(R.id.mine_tv_realname_id)
    TextView mRealname;
    @Bind(R.id.mine_llayout_realname_id)
    LinearLayout mRealLlayout;
    @Bind(R.id.mine_tv_over)
    ImageView mMineTvOver;
    @Bind(R.id.mine_imgv_over)
    ImageView mMineImgvOver;
    @Bind(R.id.ming_tv_bot)
    ImageView mMingTvBot;
    @Bind(R.id.mine_imgv_bot)
    ImageView mMineImgvBot;

    InfoData service;
    OkHttpClient mOkHttpClient;
    //上传图片
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("multipart/form-data");
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;

    //调用照相机返回图片文件
    private File tempFile;
    // 1: qq（圆形CircleImageView）, 2: weixin（矩形ImageView）
    private int type;
    String myLogo = "";//返回的logo
    String myLQYTu = "";//返回的企业图片
    String mYuPathQQ = "";
    String mFangPath = "";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
        onMessage();//获取用户信息
    }

    public void initView() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        Log.e("yh", "mUserId--" + mUserId);

        mLlayoutTu.setOnClickListener(this);//头像
        mLlayoutName.setOnClickListener(this);//名称（昵称）
        mLlayoutPhone.setOnClickListener(this);//手机号
        mLlayoutNumber.setOnClickListener(this);//身份证号码
        mReturn.setOnClickListener(this);//返回
        mWxLlayout.setOnClickListener(this);//微信绑定
        mRealLlayout.setOnClickListener(this);//真实姓名
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_llayoutreturn_id://返回
                this.finish();
                break;
            case R.id.mine_llayout_tu_id://头像
                type = 1;
                uploadHeadImage();
                requestPower();
                break;
            case R.id.mine_llayout_name_id://昵称
                onUpdateName(1,mTvName.getText().toString());//1 修改昵称    2修改手机号  3修改身份证号码
                break;
            case R.id.mine_llayout_realname_id://修改真实姓名
//                onUpdateName(4);//1 修改昵称    2修改手机号  3修改身份证号码  4修改真实姓名
                break;
            case R.id.mine_llayout_phone_id://手机号  不可修改
//                onUpdateName(2);//1 修改昵称    2修改手机号  3修改身份证号码
                break;
            case R.id.mine_llayout_number_id://身份证号码
//                onUpdateName(3);//1 修改昵称    2修改手机号  3修改身份证号码
                break;
            case R.id.mine_llayout_wx_id://微信绑定
//                Toast.makeText(this, "微信绑定", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void requestPower() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
            }
        }
    }


    public void onUpdateName(final int i,String user_name) {//1 修改名称    2修改手机号  3修改身份证号码  4修改真实姓名
        final MyDialogPerfect dialog = new MyDialogPerfect(PersonalDataActivity.this, i);
//        dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
        dialog.show();
        TextView mDialogLogin = (TextView) dialog.findViewById(R.id.dialogvip_login_id);//取消
        TextView mRegistration = (TextView) dialog.findViewById(R.id.dialogvip_login_id2);//确定
        final EditText minput = (EditText) dialog.findViewById(R.id.dialog_input);
        minput.setText(user_name);
        final EditText minputTwo = (EditText) dialog.findViewById(R.id.dialog_input_identity);//身份证号码
        mDialogLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "";
                if (i == 3) {
                    content = minputTwo.getText().toString() + "";//身份证号码
                } else {
                    content = minput.getText().toString() + "";
                }

                onUpdateMessage(i, content);//完善信息
                dialog.dismiss();
            }
        });
    }


//头像获取----------------------

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
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
                //权限判断
                if (ContextCompat.checkSelfPermission(PersonalDataActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(PersonalDataActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(PersonalDataActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(PersonalDataActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
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


    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }


    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            try {
            Uri contentUri = FileProvider.getUriForFile(PersonalDataActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            if (contentUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            }
//            }catch (Exception e){
//
//            }

        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                    Log.e("yh", "截图--" + Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    Log.e("yh", "tempFile--" + tempFile);
                    Log.e("yh", "uri--" + uri);
//                    Log.e("yh","bitMap--"+bitMap);
                    if (type == 1) {
                        mCiv.setImageBitmap(bitMap);
//                        Glide.with(MainActivity.this)
//                                .load(uri)
//                                .asBitmap()
////                                    .error(R.mipmap.ic_launcher)
//                                .into(headImage1);
                        saveBitmapFile(bitMap);//Bitmap对象保存为图片文件

                    } else {
//                        mQYTU.setImageBitmap(bitMap);
//                        Glide.with(MainActivity.this)
//                                .load(uri)
//                                .asBitmap()
////                                    .error(R.mipmap.ic_launcher)
//                                .into(headImage2);
//                        saveBitmapFilewx(bitMap); //    Bitmap对象保存为味图片文件
                    }
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......
                    if (mYuPathQQ.equals("")) {

                    } else {
                        Log.e("yh", "mYuPathQQ--" + mYuPathQQ);
                        onUpload();//修改头像
                    }
                }
                break;
        }
    }


    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }


    //    Bitmap对象保存为味图片文件
    public void saveBitmapFile(Bitmap bitmap) {//圆
        Log.e("yh保存", "bitmap--" + bitmap);
        mYuPathQQ = "/mnt/sdcard/qq.jpg";
        File file = new File(mYuPathQQ);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    Bitmap对象保存为味图片文件
    public void saveBitmapFilewx(Bitmap bitmap) {//矩形
        Log.e("yh保存", "bitmap--" + bitmap);
        mFangPath = "/mnt/sdcard/fang.jpg";
        File file = new File(mFangPath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onUpload() {//修改头像  retrofit2
        File file = new File(mYuPathQQ);
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("evaluate_user_id", mUserId)//会员id
//                .addFormDataPart("evaluate_good_id", "7")//	商品id
//                .addFormDataPart("evaluate_good_name", "阶梯价格商品")//商品名称
//                .addFormDataPart("evaluate_content", "xx")  //	评价内容
//                .addFormDataPart("eval_imgs", mYuPathQQ, RequestBody.create(MediaType.parse("image/*"), file))
                .addFormDataPart("user_id", mUserId)
                .addFormDataPart("head_img", mYuPathQQ, RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        Call<ReturnFeng> call = service.upLoad(requestBody);
//        Call<ReturnFeng> call = service.upLoadComments(requestBody);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                if (code == 100) {
                    Log.e("yh", "yes");
                } else {
                    Log.e("yh", "no");
                    Toast.makeText(PersonalDataActivity.this, pcfeng.getSuccess() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

            }
        });
    }


    public void onMessage() {//获取信息
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);//会员id
        Call<PersonalMessageFeng> call = service.getInformation(map);
        call.enqueue(new Callback<PersonalMessageFeng>() {
            @Override
            public void onResponse(Call<PersonalMessageFeng> call, Response<PersonalMessageFeng> response) {
                PersonalMessageFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    PersonalMessageFeng.InfoBean mBean = pcfeng.getInfo();
                    String user_name = mBean.getUser_name() + "";//用户名称
                    String real_name = mBean.getReal_name() + "";//真实姓名
                    String user_card = mBean.getUser_card() + "";//身份证
                    String user_tel = mBean.getUser_tel() + "";//手机号
                    String thumb = mBean.getUser_head_img() + "";//头像
                    String user_before_card = mBean.getUser_before_card();
                    String user_behind_card = mBean.getUser_behind_card();
                    if (mTvName != null) {
                        mTvName.setText(user_name);
                    }
                    if (mTvNumber != null) {
                        mTvNumber.setText(user_card);
                    }
                    if (mTvPhone != null) {
                        mTvPhone.setText(user_tel);
                    }
                    if (mRealname != null) {
                        mRealname.setText(real_name);
                    }
                    if(mWxText!=null){
                        mWxText.setText(mBean.getWx_name());
                    }
                    if (user_before_card != null && !user_before_card.equals("")) {
                        Glide.with(PersonalDataActivity.this).load(C.TU + user_before_card).into(mMineImgvOver);
                        mMineImgvOver.setVisibility(View.VISIBLE);
                        mMineTvOver.setVisibility(View.GONE);
                    }
                    if (user_behind_card != null && !user_behind_card.equals("")) {
                        Glide.with(PersonalDataActivity.this).load(C.TU + user_behind_card).into(mMineImgvBot);
                        mMineImgvBot.setVisibility(View.VISIBLE);
                        mMingTvBot.setVisibility(View.GONE);
                    }

                    if (thumb.equals("") | thumb.equals("null")) {

                    } else {
                        thumb = C.TU + thumb;
                        if (PersonalDataActivity.this != null) {
                            RequestOptions requestOptions=new RequestOptions();
                            requestOptions.placeholder(R.drawable.default_portrait).error(R.drawable.default_portrait);
                            Glide.with(PersonalDataActivity.this).load(thumb).apply(requestOptions).into(mCiv);
                        }
                    }

                } else {
//                    Toast.makeText(MineFragment.this.getActivity(), success, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PersonalMessageFeng> call, Throwable t) {

            }
        });
    }

    public void onUpdateMessage(int i, String content) {//完善信息
        //1 修改昵称    2修改手机号  3修改身份证号码  4修改真实姓名
        Map<String, Object> map = new HashMap<>();
        if (i == 1) {
            map.put("user_name", content);//昵称
        } else if (i == 2) {
            map.put("user_tel", content);//电话
        } else if (i == 3) {
            map.put("user_card", content);//身份证
        } else if (i == 4) {
            map.put("real_name", content);//真实姓名
        }
        map.put("user_id", mUserId);//会员id
        Call<ReturnFeng> call = service.getUpdateMessage(map);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e("yh", pcfeng + "--");
                int code = pcfeng.getCode();
                String success = pcfeng.getSuccess();
                if (code == 100) {
                    Toast.makeText(PersonalDataActivity.this, success, Toast.LENGTH_SHORT).show();
                    onMessage();//获取信息
                } else {
                    Toast.makeText(PersonalDataActivity.this, success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {

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

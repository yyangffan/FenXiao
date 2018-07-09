package com.linkhand.fenxiao.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.BuildConfig;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.mine.GvPhotoAdapter;
import com.linkhand.fenxiao.dialog.MyDialogReview;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.picture_select.FullyGridLayoutManager;
import com.linkhand.fenxiao.picture_select.GridImageAdapter;
import com.linkhand.fenxiao.utils.MultipleImages.NoScrollGridView;
import com.linkhand.fenxiao.utils.MultipleImages.TakePhotoWindow;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.utils.util.FileUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

import static com.linkhand.fenxiao.utils.util.FileUtil.getRealFilePathFromUri;

/********************************************************************
 @version: 1.0.0
 @变更历史: 评论界面
 ********************************************************************/
public class EvaluationActivity extends BaseActicity implements View.OnClickListener, View.OnTouchListener {

    @Bind(R.id.evaluation_return_id)
    ImageView mReturn;
    @Bind(R.id.evaluation_et_id)
    EditText mEvaluation;
    @Bind(R.id.activity_evaluation)
    RelativeLayout mActivityEvaluation;
    @Bind(R.id.evaluation_confirm_id)
    TextView mConfirm;//确定

    InfoData service;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    String mUserId;//个人id
    String mGoodId;//商品id
    String mTitle;//商品名称
    @Bind(R.id.evaluation_iv_id)
    NoScrollGridView mImg;
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.evaluation_title)
    TextView mEvaluationTitle;
    @Bind(R.id.evaluation_rb)
    RatingBar mEvaluationRb;

    //---------图---------
    private ArrayList<String> listPath;
    private String IMG_ADD_TAG = "a";
    GvPhotoAdapter adapter;
    private int IMG_COUNT = 6;
    private final int CAMERACODE = 0X111;
    private final int GALLERYCODE = 0X222;
    private String CAR_IMG = "CAR_IMG";
    private TakePhotoWindow takePhotoWindow;
    private String flag = CAR_IMG;

    private Uri cameraImgUri;
    private String cameraImgPath;

    //调用照相机返回图片文件
    private File tempFile;
    String path;

    private GridImageAdapter madapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 5;
    private boolean mIsPic = true;

    private static final int REQUESTCODE_PICK = 0;        // 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;        // 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2;    // 图片裁切标记
    public static final int ALBUM = 0x0007; //相册
    private static final String IMAGE_FILE_NAME = "IdleImage.jpg";// 头像文件名称
    private String mOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
        takePhotoWindow = new TakePhotoWindow(this);
        initImgData();
        initRecy();
        mEvaluationTitle.requestFocus();
    }

    public void initRecy() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(EvaluationActivity.this, 4, GridLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(manager);
        madapter = new GridImageAdapter(EvaluationActivity.this, onAddPicClickListener);
        madapter.setList(selectList);
        madapter.setSelectMax(maxSelectNum);
        mRecycler.setAdapter(madapter);
        madapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(EvaluationActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(EvaluationActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(EvaluationActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            toSelect();
        }

    };

    public void toSelect() {
        madapter.setSelectMax(mIsPic ? maxSelectNum : 1);
        PictureSelector.create(EvaluationActivity.this)
                .openGallery(mIsPic ? PictureMimeType.ofImage() : PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(mIsPic ? maxSelectNum : 1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(mIsPic ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(false)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    public void panduan(String content) {
        //发布
        if (selectList != null) {
            if (selectList.size() != 0) {
                for (LocalMedia media : selectList) {
                    listPath.add(media.getPath());
                }
                onMessage(content);
            } else {
                listPath = null;
                onMessage(content);
            }
        } else {
            listPath = null;
            onMessage(content);
        }
    }

    public void initView() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");
        Intent intent = getIntent();
        if (intent != null) {
            mGoodId = intent.getStringExtra("good_id");//商品id
            mTitle = intent.getStringExtra("title");//商品名称
            mOrderId = intent.getStringExtra("order_id");//订单id
        }
        mConfirm.setOnClickListener(this);
        mReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluation_confirm_id://确定
                String content = mEvaluation.getText() + "";
                if (content.equals("")) {
                    Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                } else {
                    panduan(content);
                }
                break;
            case R.id.evaluation_return_id:
                this.finish();
                break;
        }
    }

    /*调用评论接口*/
    public void onMessage(String content) {
        float rating = mEvaluationRb.getRating();
        if(rating==0.0){
            ToastUtil.showToast(this,"请选择星级");
            return;
        }
        mConfirm.setEnabled(false);
        mConfirm.setText("提交中,请稍候....");
        mConfirm.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.btn_bg_rounded_six));
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("evaluate_user_id", mUserId)//会员id
                .addFormDataPart("evaluate_good_id", mGoodId)//	商品id
                .addFormDataPart("evaluate_good_name", mTitle)//商品名称
                .addFormDataPart("evaluate_content", content) //	评价内容
                .addFormDataPart("order_id", mOrderId) //	订单id
                .addFormDataPart("star_num", rating+"");  //	星级评价
//                    .addFormDataPart("head_img", listPath.get(0), RequestBody.create(MediaType.parse("image/*"), file))
        if (listPath != null) {//全部图片
            if (!listPath.get(0).equals("a")) {
                for (int i = 0; i < listPath.size(); i++) {
                    File file = new File(listPath.get(i));
                    requestBody.addFormDataPart("eval_imgs" + (i + 1), listPath.get(i), RequestBody.create(MediaType.parse("image/*"), file));
                }
            }
        }
//        Log.e("yh", "?evaluate_user_id=" + mUserId + "&evaluate_good_id=" + mGoodId + "&evaluate_good_name=" + mTitle + "&evaluate_content="
//                + content + "order_id=" + mOrderId + "&eval_imgs0=" + listPath.get(0));
        Call<ReturnFeng> call = service.upLoadComments(requestBody.build());
        call.enqueue(new Callback<ReturnFeng>() {
                         @Override
                         public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                             mConfirm.setEnabled(true);
                             mConfirm.setText("确定");
                             mConfirm.setBackgroundDrawable(EvaluationActivity.this.getResources().getDrawable(R.drawable.btn_bg_rounded_two));
                             ReturnFeng pcfeng = response.body();
                             Log.e("yh", pcfeng + "--");
                             int code = pcfeng.getCode();
                             if (code == 100) {
                                 //成功后的评论框
                                 final MyDialogReview dialog = new MyDialogReview(EvaluationActivity.this);
                                 dialog.show();
                                 TextView tv = (TextView) dialog.findViewById(R.id.dialog_review_id);
                                 dialog.setCanceledOnTouchOutside(false);//点击空白处是否消失
                                 tv.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         EvaluationActivity.this.finish();
                                         dialog.dismiss();
                                     }
                                 });
                             } else {
                                 Toast.makeText(EvaluationActivity.this, pcfeng.getSuccess() + "", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<ReturnFeng> call, Throwable t) {
                             mConfirm.setEnabled(true);
                             mConfirm.setText("确定");
                             mConfirm.setBackgroundDrawable(EvaluationActivity.this.getResources().getDrawable(R.drawable.btn_bg_rounded_two));
                         }
                     }

        );
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if (view.getId() == R.id.evaluation_et_id && canVerticalScroll(mEvaluation)) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
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

    //-----------------图-------------

    //初始化 照片资源
    private void initImgData() {
        if (listPath == null) {
            listPath = new ArrayList<>();
            listPath.add(IMG_ADD_TAG);
        }
        adapter = new GvPhotoAdapter(listPath, this);
        mImg.setAdapter(adapter);
        mImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listPath.get(position).equals(IMG_ADD_TAG)) {
                    if (listPath.size() < IMG_COUNT) {
                        flag = CAR_IMG;
//                        View parents = LayoutInflater.from(EvaluationActivity.this).inflate(R.layout.activity_evaluation, null);
//                        takePhotoWindow.showAtLocation(parents, Gravity.BOTTOM, 0, 0);

                        takePhotoWindow.setSelectPhotoListener(selectPhotoListener);
                        takePhotoWindow.setTakePhotoListener(takePhotoListener);
                        takePhotoWindow.showAtLocation(mConfirm, Gravity.BOTTOM, 0, 0);
                    } else
                        Toast.makeText(EvaluationActivity.this, "最多只能选择5张照片！", Toast.LENGTH_SHORT).show();

                }
            }
        });
        adapter.setOnDeletListener(new GvPhotoAdapter.OnDeleteListener() {
            @Override
            public void onDelete(int position) {
                listPath.remove(position);
//                uriList.remove(position);
                adapter.notifyDataSetChanged();

            }
        });
        adapter.notifyDataSetChanged();
    }


    TakePhotoWindow.TakePhotoListener takePhotoListener = new TakePhotoWindow.TakePhotoListener() {
        @Override
        public void takePhoto() {
            takeMyPhoto();
        }
    };
    TakePhotoWindow.SelectPhotoListener selectPhotoListener = new TakePhotoWindow.SelectPhotoListener() {
        @Override
        public void selectPhoto() {
            selectMyPhoto();
        }
    };

    /**
     * 拍照
     */
    private void takeMyPhoto() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");
        cameraImgPath = getRealFilePathFromUri(getApplicationContext(), Uri.fromFile(tempFile));
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            try {
            Uri contentUri = FileProvider.getUriForFile(EvaluationActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            if (contentUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            }
//            }catch (Exception e){
//
//            }

        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERACODE);
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 从相册选择
     */
    private void selectMyPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERYCODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUESTCODE_PICK:// 直接从相册获取
                    try {
//                        startPhotoZoom(data.getData());
                        //  ImageUtils.setCircleDefImage(mHeaderTV,data.getData().getPath());
                    } catch (NullPointerException e) {
                        e.printStackTrace();// 用户点击取消操作
                    }
                    break;
                case REQUESTCODE_TAKE:// 调用相机拍照
                    File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
//                    startPhotoZoom(Uri.fromFile(temp));
                    //  ImageUtils.setCircleDefImage(mHeaderTV,Uri.fromFile(temp).getPath());
                    break;
                case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                    if (data != null) {
//                        setPicToView(data);
                    }

                    break;
//
                case ALBUM:
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    removeItem();
                    madapter.setList(selectList);
                    madapter.notifyDataSetChanged();
                    break;
            }

        }

    }

    private void removeItem() {
        if (listPath.size() != IMG_COUNT) {
            if (listPath.size() != 0) {
                listPath.remove(listPath.size() - 1);
            }
        }
    }

    private void refreshAdapter() {
        for (int i = 0; i < listPath.size(); i++) {
            Log.e("list", "遍历list------------》" + listPath.get(i) + "\n");
        }

        if (listPath == null) {
            listPath = new ArrayList<>();
        }
        if (adapter == null) {
            adapter = new GvPhotoAdapter(listPath, this);
        }
        adapter.notifyDataSetChanged();
    }


}

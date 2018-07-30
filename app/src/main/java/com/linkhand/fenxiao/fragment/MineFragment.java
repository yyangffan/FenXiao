package com.linkhand.fenxiao.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.BaseFragment;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.AllVipGoodsActivity;
import com.linkhand.fenxiao.activity.homepage.home.InviteActivity;
import com.linkhand.fenxiao.activity.homepage.home.MyVipActivity;
import com.linkhand.fenxiao.activity.login.LoginActivity;
import com.linkhand.fenxiao.activity.mine.AllCollectionActivity;
import com.linkhand.fenxiao.activity.mine.IsRechargeActivity;
import com.linkhand.fenxiao.activity.mine.MedalTeamActivity;
import com.linkhand.fenxiao.activity.mine.MyOrderActivity;
import com.linkhand.fenxiao.activity.mine.PersonalDataActivity;
import com.linkhand.fenxiao.activity.mine.SetUpTheActivity;
import com.linkhand.fenxiao.dialog.MyDialogApprove;
import com.linkhand.fenxiao.dialog.MyDialogVip;
import com.linkhand.fenxiao.feng.AllConfigFeng;
import com.linkhand.fenxiao.feng.ReturnFeng;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.mine.PersonalMessageFeng;
import com.linkhand.fenxiao.fragment.mineorder.IsWaitFragment;
import com.linkhand.fenxiao.fragment.mineorder.TuihActivity;
import com.linkhand.fenxiao.info.InfoData;
import com.linkhand.fenxiao.utils.RoundImageView;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.File;
import java.util.HashMap;
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

/**
 * 我的
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    LinearLayout mMyOrder;//我的订单
    LinearLayout mTeam;//我的团队
    LinearLayout mInvite;//邀请好友
    RelativeLayout mLayoutOne;//拼团中
    RelativeLayout mLayoutTwo;//待发货
    RelativeLayout mLayoutThree;//待收货
    RelativeLayout mLayoutFour;//待评价
    LinearLayout mIsRMB;//充值提现
    LinearLayout mCollection;//我的收藏
    Intent intent;
    Fragment fragment;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Bind(R.id.mine_setting_id)
    TextView mMineSetting;//设置
    String mUserId = "";//个人id
    InfoData service;
    @Bind(R.id.mine_img_id)
    RoundImageView mImg;
    @Bind(R.id.mine_name_id)
    TextView mName;
    @Bind(R.id.mine_zi_rmb_id)
    TextView mZiRmb;
    @Bind(R.id.mine_mu_rmb_id)
    TextView mMuRmb;
    @Bind(R.id.mine_perfectdata_layout_id)
    LinearLayout mPerfectData;//完善资料
    @Bind(R.id.perfect_vip_id)
    LinearLayout mPerfectVip;//完善vip
    String userIsVip = "0";//是否vip   0否  1是
    @Bind(R.id.team_level_ids)
    TextView mTeamLevel;//团队级别
    @Bind(R.id.zi_text_id2)
    TextView mZiText;
    @Bind(R.id.mu_text_id2)
    TextView mMuText;
    @Bind(R.id.mine_llayout_wait)
    RelativeLayout mMineLlayoutWait;
    @Bind(R.id.mine_dj_zb)
    TextView mMineDjZb;
    @Bind(R.id.mine_lj_zb)
    TextView mMineLjZb;
    @Bind(R.id.mine_dj_note)
    TextView mMineDjNote;
    @Bind(R.id.min_lj_note)
    TextView mMinLjNote;
    @Bind(R.id.mine_ll_one)
    LinearLayout mMineLlOne;
    @Bind(R.id.mine_ll_two)
    LinearLayout mMineLlTwo;
    @Bind(R.id.mine_ll_three)
    LinearLayout mMineLlThree;
    @Bind(R.id.mine_ll_four)
    LinearLayout mMineLlFour;
    @Bind(R.id.mine_tv_one)
    TextView mMineTvOne;
    @Bind(R.id.mine_tv_two)
    TextView mMineTvTwo;
    @Bind(R.id.mine_tv_three)
    TextView mMineTvThree;
    @Bind(R.id.mine_tv_four)
    TextView mMineTvFour;
    @Bind(R.id.mine_tv_five)
    TextView mMineTvFive;
    @Bind(R.id.team_qiandao)
    TextView mTeamQiandao;
    @Bind(R.id.mine_tv_six)
    TextView mMineTvSix;
    @Bind(R.id.mine_llayout_id5)
    RelativeLayout mMineLlayoutId5;
    @Bind(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;

    String Mater_name = "母币";//母币名称
    String Son_name = "子币";//子币名称
    private String imgv_url = "";
    public String mUserReal;
    private static final String TAG = "MineFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, v);
        initRetrofit();
        init(v);
        onClicks();
        onMessage(); //个人信息
        return v;
    }

    public void init(View v) {
        mSmartRefresh.setEnableRefresh(false);
        mSmartRefresh.setEnableLoadmore(false);
        mMyOrder = (LinearLayout) v.findViewById(R.id.mine_myorder_id);//我的订单
        mTeam = (LinearLayout) v.findViewById(R.id.mine_team_id);//我的团队
        mInvite = (LinearLayout) v.findViewById(R.id.mine_invite_llayout_id);//邀请好友
        mLayoutOne = (RelativeLayout) v.findViewById(R.id.mine_llayout_id1);//拼团中
        mLayoutTwo = (RelativeLayout) v.findViewById(R.id.mine_llayout_id2);//待发货
        mLayoutThree = (RelativeLayout) v.findViewById(R.id.mine_llayout_id3);//待收货
        mLayoutFour = (RelativeLayout) v.findViewById(R.id.mine_llayout_id4);//待评价
        mIsRMB = (LinearLayout) v.findViewById(R.id.mine_isrmb_id);//充值提现
        mCollection = (LinearLayout) v.findViewById(R.id.mine_collection_llayout_id);//我的收藏
//        mShopping = (LinearLayout) v.findViewById(R.id.mine_shopping_llayout_id);//购物车


        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove("keyDown").commit();  //存入返回判断  1不提示
        editor.remove("isTitles").commit();//判断订单标题是否隐藏  0不隐藏（单个订单）  1隐藏（全部订单）
        //获取个人id
        mUserId = preferences.getString("user_id", "");
        mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
        Log.e("yh", "mUserId--" + mUserId);
        //获取子母币名称
        Mater_name = preferences.getString("Mater_name", "母币");//母币名称
        Son_name = preferences.getString("Son_name", "子币");//子币名称
        mZiText.setText(Son_name);
        mMuText.setText(Mater_name);

        getNote();
    }

    public void onClicks() {
        mMyOrder.setOnClickListener(MineFragment.this);//我的订单
        mTeam.setOnClickListener(MineFragment.this);//我的团队
        mInvite.setOnClickListener(MineFragment.this);//邀请好友
        mLayoutOne.setOnClickListener(MineFragment.this);//拼团中
        mLayoutTwo.setOnClickListener(MineFragment.this);//待发货
        mLayoutThree.setOnClickListener(MineFragment.this);//待收货
        mLayoutFour.setOnClickListener(MineFragment.this);//待评价
        mIsRMB.setOnClickListener(MineFragment.this);//充值提现
        mMineSetting.setOnClickListener(MineFragment.this);//设置
        mCollection.setOnClickListener(MineFragment.this);//我的收藏
//        mShopping.setOnClickListener(MineFragment.this);//购物车
        mPerfectData.setOnClickListener(MineFragment.this);//完善资料
        mPerfectVip.setOnClickListener(MineFragment.this);//完善资料
        mMineLlayoutWait.setOnClickListener(this);
        mImg.setOnClickListener(this);
        mMineLlOne.setOnClickListener(this);
        mMineLlTwo.setOnClickListener(this);
        mMineLlThree.setOnClickListener(this);
        mTeamQiandao.setOnClickListener(this);
        mMineLlayoutId5.setOnClickListener(this);
//        mName.setOnClickListener(this);//登录
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        mUserReal = preferences.getString("userReal", "0");//是否认证  0否  1是
        switch (v.getId()) {
            case R.id.mine_myorder_id://我的订单
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        //判断订单标题是否隐藏  0不隐藏（单个订单）  1隐藏（全部订单）
                        editor.putInt("isTitles", 1);
                        editor.putInt("isClick", 0);
                        editor.commit();
                        intent = new Intent(MineFragment.this.getActivity(), MyOrderActivity.class);
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }
                break;
            case R.id.mine_name_id://登录
//                if (mUserId.equals("")) {
//                    Intent intent = new Intent(MineFragment.this.getActivity(), LoginActivity.class);
//                    startActivity(intent);
//                }
                break;

            case R.id.mine_team_id://我的团队
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        intent = new Intent(MineFragment.this.getActivity(), MedalTeamActivity.class);
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }
                break;
            case R.id.mine_invite_llayout_id://邀请好友
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        intent = new Intent(MineFragment.this.getActivity(), InviteActivity.class);
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }
                break;
            case R.id.mine_llayout_id1://拼团中
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        editor.putInt("isTitles", 1);
                        editor.putInt("isClick", 2);
                        editor.commit();
                        intent = new Intent(MineFragment.this.getActivity(), MyOrderActivity.class);
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }
                break;
            case R.id.mine_llayout_id2://待发货
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        editor.putInt("isTitles", 1);
                        editor.putInt("isClick", 3);
                        editor.commit();
                        intent = new Intent(MineFragment.this.getActivity(), MyOrderActivity.class);
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }

                break;
            case R.id.mine_llayout_id3://待收货
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        editor.putInt("isTitles", 1);
                        editor.putInt("isClick", 4);
                        editor.commit();
                        intent = new Intent(MineFragment.this.getActivity(), MyOrderActivity.class);
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }

                break;
            case R.id.mine_llayout_id4://待评价
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        editor.putInt("isTitles", 1);
                        editor.putInt("isClick", 5);
                        editor.commit();
                        intent = new Intent(MineFragment.this.getActivity(), MyOrderActivity.class);
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }

                break;
            case R.id.mine_isrmb_id://充值提现
                if (mUserId.equals("")) {
                    Intent intent = new Intent(MineFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        intent = new Intent(MineFragment.this.getActivity(), IsRechargeActivity.class);
                        intent.putExtra("zi", mZiRmb.getText().toString() + mZiText.getText().toString());
                        intent.putExtra("mother", mMuRmb.getText().toString() + mMuText.getText().toString());
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }
                break;
            case R.id.mine_setting_id://设置
                if (mUserId.equals("")) {
                    Intent intent = new Intent(MineFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(MineFragment.this.getActivity(), SetUpTheActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_collection_llayout_id://我的收藏
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        intent = new Intent(MineFragment.this.getActivity(), AllCollectionActivity.class);
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }
                break;
            case R.id.mine_perfectdata_layout_id://完善资料
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    intent = new Intent(MineFragment.this.getActivity(), PersonalDataActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.perfect_vip_id://完善vip
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("0")) {//是否vip  0否  1是
                        intent = new Intent(MineFragment.this.getActivity(), AllVipGoodsActivity.class);
                        startActivity(intent);
                    } else {
                        //如果是VIP的话就跳转到Vip订单列表
                        startActivity(new Intent(MineFragment.this.getActivity(), MyVipActivity.class));
                    }
                }
                break;
            case R.id.mine_llayout_wait://待付款
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        editor.putInt("isTitles", 1);
                        editor.putInt("isClick", 1);
                        editor.commit();
                        intent = new Intent(MineFragment.this.getActivity(), MyOrderActivity.class);
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }
                break;
            case R.id.mine_ll_one:
                bundle.putString("what", "2");//MineJlActivity标示跳转
                bundle.putString("son", "1");
                bundle.putString("title", mZiText.getText().toString() + "记录");
                intent = new Intent(MineFragment.this.getActivity(), MineJlActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.mine_ll_two:
                bundle.putString("what", "2");//MineJlActivity标示跳转
                bundle.putString("son", "2");
                bundle.putString("title", mMuText.getText().toString() + "记录");
                intent = new Intent(MineFragment.this.getActivity(), MineJlActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.mine_ll_three:
                bundle.putString("what", "2");//MineJlActivity标示跳转
                bundle.putString("son", "3");
                bundle.putString("title", mMineDjNote.getText().toString() + "记录");
                intent = new Intent(MineFragment.this.getActivity(), MineJlActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.mine_img_id:
                uploadHeadImage();
                break;
            case R.id.team_qiandao://签到
                toSignIn();
                break;
            case R.id.mine_llayout_id5://退货界面
                if (mUserId.equals("")) {
                    onLogin();//去登陆
                } else {
                    if (userIsVip.equals("1")) {//是否vip  0否  1是
                        if (mUserReal.equals("0")) {//是否认证  0否  1是
                            onApprove();
                            return;
                        }
                        intent = new Intent(MineFragment.this.getActivity(), TuihActivity.class);
                        startActivity(intent);
                    } else {
                        onPrompt();//提示信息
                    }
                }
                break;
        }
    }

    public void toSignIn() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<HttpResponse> call = service.getSignIn(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    mTeamQiandao.setText("已签到");
                    onMessage();
                }
                ToastUtil.showToast(MineFragment.this.getActivity(), httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(MineFragment.this.getActivity(), "网络异常");
            }
        });
    }

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(this.getActivity()).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this.getActivity()).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = this.getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        this.getActivity().getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                MineFragment.this.getActivity().getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(MineFragment.this.getActivity())
                        .openCamera(PictureMimeType.ofImage())
                        .enableCrop(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .circleDimmedLayer(true)
                        .freeStyleCropEnabled(false)
                        .withAspectRatio(1,1)
                        .imageFormat(PictureMimeType.PNG)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                popupWindow.dismiss();

            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(MineFragment.this.getActivity())
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)
                        .isCamera(false)
                        .selectionMode(PictureConfig.SINGLE)
                        .enableCrop(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .freeStyleCropEnabled(false)
                        .circleDimmedLayer(true)
                        .withAspectRatio(1,1)
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

    public void setHeadImgv(String imgv_url) {
        this.imgv_url = imgv_url;
        onUpload();
    }

    /*上传头像*/
    public void onUpload() {
        File file = new File(imgv_url);
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("user_id", mUserId)
                .addFormDataPart("head_img", imgv_url, RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        Call<ReturnFeng> call = service.upLoad(requestBody);
        call.enqueue(new Callback<ReturnFeng>() {
            @Override
            public void onResponse(Call<ReturnFeng> call, Response<ReturnFeng> response) {
                ReturnFeng pcfeng = response.body();
                Log.e(TAG, pcfeng + "--");
                int code = pcfeng.getCode();
                if (code == 100) {
                    RequestOptions requestOptions=new RequestOptions();
                    requestOptions.placeholder(R.drawable.default_portrait).error(R.drawable.default_portrait);
                    Glide.with(MineFragment.this.getActivity()).load(imgv_url).apply(requestOptions).into(mImg);
                    onMessage();
                    Log.e(TAG, "yes");
                } else {
                    Log.e(TAG, "no");
                    Toast.makeText(MineFragment.this.getActivity(), pcfeng.getSuccess() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnFeng> call, Throwable t) {
                Log.e("头像上传错误", t.toString());
            }
        });
    }


    /*获取上面的提示*/
    public void getNote() {
        Call<AllConfigFeng> call = service.getAllConfig(new HashMap<String, Object>());
        call.enqueue(new Callback<AllConfigFeng>() {
            @Override
            public void onResponse(Call<AllConfigFeng> call, Response<AllConfigFeng> response) {
                AllConfigFeng allConfigFeng = response.body();
                int code = allConfigFeng.getCode();
                if (code == 100) {
                    String frozen = allConfigFeng.getInfo().getFrozen();//冻结名称
                    String accumu = allConfigFeng.getInfo().getAccumu();//累计名称
                    if (mMineDjNote != null) {
                        mMineDjNote.setText(frozen);
                    }
                    if (mMinLjNote != null) {
                        mMinLjNote.setText(accumu);
                    }
                }
            }

            @Override
            public void onFailure(Call<AllConfigFeng> call, Throwable t) {
                Toast.makeText(MineFragment.this.getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onMessage() { //个人信息
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<PersonalMessageFeng> call = service.getInformation(map);
        call.enqueue(new Callback<PersonalMessageFeng>() {
            @Override
            public void onResponse(Call<PersonalMessageFeng> call, Response<PersonalMessageFeng> response) {
                PersonalMessageFeng pcfeng = response.body();
                int code = pcfeng.getCode();
                if (code == 100) {
                    PersonalMessageFeng.InfoBean mBean = pcfeng.getInfo();
                    String mater_money = mBean.getUser_mater_money();//母币
                    String son_money = mBean.getUser_son_money();//子币
                    String user_name = mBean.getUser_name();//名
                    String thumb = mBean.getUser_head_img();
                    String userReal = mBean.getIs_real() + "";//是否认证 0否  1是
                    if (mTeamLevel != null) {
                        mTeamLevel.setText(mBean.getRebate_str());
                    }
                    userIsVip = preferences.getString("userIsVip", "0");//是否vip  0否  1是
                    editor.putString("userReal", userReal);   //是否认证  0否  1是
                    editor.commit();
                    if (mName != null) {
                        mName.setText(user_name);
                    }
                    if (mMuRmb != null) {
                        mMuRmb.setText(mater_money);
                    }
                    if (mZiRmb != null) {
                        mZiRmb.setText(son_money);
                    }
                    if (mMineDjZb != null) {
                        mMineDjZb.setText(mBean.getUser_son_frost());
                    }
                    if (mMineLjZb != null) {
                        mMineLjZb.setText(mBean.getAll_money());
                    }
                    if (thumb != null && !thumb.equals("")) {
                        if (mImg != null) {
                            thumb = C.TU + thumb;
                            RequestOptions requestOptions=new RequestOptions();
                            requestOptions.placeholder(R.drawable.default_portrait).error(R.drawable.default_portrait);
                            Glide.with(MineFragment.this.getActivity()).load(thumb).apply(requestOptions).into(mImg);
                        }
                    }
                    String fk_cou = mBean.getFk_cou();//待付款数量
                    String pt_cou = mBean.getPt_cou();//拼团中数量
                    String fh_cou = mBean.getFh_cou();//待发货数量
                    String sh_cou = mBean.getSh_cou();//待收货数量
                    String pj_cou = mBean.getPj_cou();//待评价数量
                    if (fk_cou != null && !fk_cou.equals("0") && mMineTvOne != null) {
                        mMineTvOne.setText(fk_cou);
                        mMineTvOne.setVisibility(View.VISIBLE);
                    } else {
                        if (mMineTvOne != null) {
                            mMineTvOne.setVisibility(View.GONE);
                        }
                    }
                    if (pt_cou != null && !pt_cou.equals("0") && mMineTvTwo != null) {
                        mMineTvTwo.setText(pt_cou);
                        mMineTvTwo.setVisibility(View.VISIBLE);
                    } else {
                        if (mMineTvTwo != null) {
                            mMineTvTwo.setVisibility(View.GONE);
                        }
                    }
                    if (fh_cou != null && !fh_cou.equals("0") && mMineTvThree != null) {
                        mMineTvThree.setText(fh_cou);
                        mMineTvThree.setVisibility(View.VISIBLE);
                    } else {
                        if (mMineTvThree != null) {
                            mMineTvThree.setVisibility(View.GONE);
                        }
                    }
                    if (sh_cou != null && !sh_cou.equals("0") && mMineTvFour != null) {
                        mMineTvFour.setText(sh_cou);
                        mMineTvFour.setVisibility(View.VISIBLE);
                    } else {
                        if (mMineTvFour != null) {
                            mMineTvFour.setVisibility(View.GONE);
                        }
                    }
                    if (pj_cou != null && !pj_cou.equals("0") && mMineTvFive != null) {
                        mMineTvFive.setText(pj_cou);
                        mMineTvFive.setVisibility(View.VISIBLE);
                    } else {
                        if (mMineTvFive != null) {
                            mMineTvFive.setVisibility(View.GONE);
                        }
                    }
                    String sign_in = pcfeng.getInfo().getSign_in();
                    if (sign_in.equals("1")) {
                        if (mTeamQiandao != null) {
                            mTeamQiandao.setText("已签到");
                        }
                    }else {
                        if (mTeamQiandao != null) {
                            mTeamQiandao.setText("签到");
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

    public void onLogin() {//去登陆
        Intent intent = new Intent(MineFragment.this.getActivity(), LoginActivity.class);//登录
        startActivity(intent);
    }

    public void onPrompt() {//提示信息
        MyDialogVip dialog = new MyDialogVip(getActivity());
        dialog.show();
    }
    public void onApprove() {//实名认证
        MyDialogApprove dialog = new MyDialogApprove(this.getActivity());
        dialog.show();
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
    public void onStart() {
        super.onStart();
        onMessage(); //个人信息
    }

    public void refresh() {
        mUserId = preferences.getString("user_id", "");
        if (fragment != null && fragment.isVisible()) {
            if (fragment instanceof IsWaitFragment) {
                ((IsWaitFragment) fragment).onMessage(0);
            }
        }
        onMessage();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}

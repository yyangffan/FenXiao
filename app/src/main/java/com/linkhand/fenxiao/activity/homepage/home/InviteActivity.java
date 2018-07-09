package com.linkhand.fenxiao.activity.homepage.home;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.bean.InviteBean;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.linkhand.fenxiao.utils.youmeng.ShareUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteActivity extends BaseActicity implements View.OnClickListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout mReturn;//返回
    @Bind(R.id.fenxiao_invitation)
    TextView mInvitation;//邀请
    @Bind(R.id.invite_yaoqingcode)
    TextView mInviteYaoqingcode;
    @Bind(R.id.banner)
    Banner mBanner;
    @Bind(R.id.invite_copy)
    TextView mInviteCopy;


    private List<String> mList_pic;
    private String mUserId;
    private String pic_lujing = "";
    private List<Uri> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        init();
        onClicks();
        getHaiBaoImg();
    }

    public void init() {
        mReturn = (LinearLayout) findViewById(R.id.fenxiao_return_id5);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");

        mList = new ArrayList<>();
        mList_pic = new ArrayList<>();
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pic_lujing = mList_pic.get((Math.abs(position - 1)) % mList_pic.size());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void onClicks() {
        mReturn.setOnClickListener(this);//返回
        mInvitation.setOnClickListener(this);//邀请
        mInviteCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenxiao_return_id5://返回
                this.finish();
                break;
            case R.id.fenxiao_invitation://邀请
                onShare();//分享
                break;
            case R.id.invite_copy://复制邀请码
                ClipboardManager cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(mInviteYaoqingcode.getText().toString());
                ToastUtil.showToast(this,"已复制邀请码");
                break;
        }
    }

    public void getHaiBaoImg() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<InviteBean> call = service.getUserCode(map);
        call.enqueue(new Callback<InviteBean>() {
            @Override
            public void onResponse(Call<InviteBean> call, Response<InviteBean> response) {
                InviteBean pcfeng = response.body();
                String code = pcfeng.getCode();
                if (code.equals("100")) {
                    List<String> info = pcfeng.getInfo();
                    for (int i = 0; i < info.size(); i++) {
                        String one_pic_lujing = C.TU + info.get(i);
                        mList_pic.add(one_pic_lujing);
                        Uri uri = Uri.parse(one_pic_lujing);
                        mList.add(uri);
                    }
                    pic_lujing = C.TU + pcfeng.getInfo().get(0);
                    mInviteYaoqingcode.setText(pcfeng.getInviter());
                    onRoastingTu();
                } else {
                    Toast.makeText(InviteActivity.this, pcfeng.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InviteBean> call, Throwable t) {
                Toast.makeText(InviteActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onRoastingTu() {//轮播图
        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置
        mBanner.setIndicatorGravity(Gravity.BOTTOM);
        //设置图片加载器
        mBanner.setImageLoader(new MyBannerLoader());
        //设置图片集合
        mBanner.setImages(mList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合
//        mBanner.setBannerTitles(titleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(false);
        //设置轮播时间
        mBanner.setDelayTime(2000);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }


    public void onShare() {//分享
//        Defaultcontent.url
        //this  连接地址   标题  内容     网络图片路径     本地缩略图路径    不用面板要打开的地方
        ShareUtils.sharePic(InviteActivity.this, "友趣团购邀请您", pic_lujing, SHARE_MEDIA.QQ);
    }

    public class MyBannerLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if (context != null) {
                //Glide 加载图片简单用法
                Glide.with(context).load(path).into(imageView);
            }
            //Picasso 加载图片简单用法
//        Picasso.with(context).load((Uri)path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
//        Uri uri = Uri.parse((String) path);
//        imageView.setImageURI(uri);
        }
    }
}

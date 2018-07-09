package com.linkhand.fenxiao;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 11860_000 on 2018/3/12.
 */

public class MyApplication extends MultiDexApplication {
    /**打开的activity**/
    private List<Activity> activities = new ArrayList<Activity>();
    /**应用实例**/
    private static MyApplication instance;
    private IWXAPI mIWXAPI;

    //各个平台的配置---友盟分享(设置appid  等)
    {
        PlatformConfig.setWeixin("wx0e520cf074c1db30", "1cb3d8cfa488a6ed4bd4b4b5f2b77002");    //微信
        PlatformConfig.setQQZone("1106716681", "djKIqqQS5PHfsBpI"); //QQ
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //解析html(图文)初始化
        initImageLoader(this);
//        //友盟分享
        UMShareAPI.get(this);//初始化sdk
//        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        /*极光初始化*/
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        mIWXAPI= WXAPIFactory.createWXAPI(this, "wx0e520cf074c1db30");
//        mIWXAPI= WXAPIFactory.createWXAPI(this,"wx0e520cf074c1db30",true);
        mIWXAPI.registerApp("wx0e520cf074c1db30");
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "5aff862ca40fa3522300033e");
    }
    /*获取微信支付的Api实例*/
    public IWXAPI getApi() {
        return mIWXAPI;
    }

    private void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(20 * 1024 * 1024); // 20 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
    /**
     *  获得实例
     * @return
     */
    public static MyApplication getInstance(){
        return instance;
    }
    /**
     * 新建了一个activity
     * @param activity
     */
    public void addActivity(Activity activity){
        activities.add(activity);
    }
    /**
     *  结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if (activity!=null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }
    /**
     * 应用退出，结束所有的activity
     */
    public void exit(){
        for (Activity activity : activities) {
            if (activity!=null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}
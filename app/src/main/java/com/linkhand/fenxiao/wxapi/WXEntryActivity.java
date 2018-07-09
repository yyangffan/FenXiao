package com.linkhand.fenxiao.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.linkhand.fenxiao.MyApplication;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.utils.Log;
import com.umeng.weixin.callback.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		api = WXAPIFactory.createWXAPI(this,"wx56fe95ec4e181742");
//		api.handleIntent(this.getIntent(), this);
        MyApplication.getInstance().getApi().handleIntent(this.getIntent(),this);
        Log.e("支付页面", "发送广播");

        //发送广播
        Intent intent = new Intent();
        intent.setAction("finish");
        sendBroadcast(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MyApplication.getInstance().getApi().handleIntent(this.getIntent(),this);
//		api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e("支付返回", "onPayFinish, errCode = " + resp.errCode +resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int errCode = resp.errCode;
//            if(errCode==0){
//                ToastUtil.showToast(this,"支付成功");
//                EventBus.getDefault().post(new MessageEvent("finish"));
//            }else if(errCode==-1){
//                ToastUtil.showToast(this,"支付错误");
//            }else if(errCode==-2){
//                ToastUtil.showToast(this,"用户取消");
//                EventBus.getDefault().post(new MessageEvent("finish"));
//            }
        }

        //发送广播
        Intent intent = new Intent();
        intent.setAction("finish");
        sendBroadcast(intent);
    }


    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null) {
            Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
            startActivity(iLaunchMyself);
        }
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }



}
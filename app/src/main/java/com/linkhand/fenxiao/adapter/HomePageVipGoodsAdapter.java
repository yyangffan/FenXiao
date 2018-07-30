package com.linkhand.fenxiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.AllVipGoodsActivity;
import com.linkhand.fenxiao.feng.home.NewRecommendedVipGoods;
import com.linkhand.fenxiao.fragment.HomePageFragment;
import com.linkhand.fenxiao.info.callback.HomeVipInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/1/13.
 */

public class HomePageVipGoodsAdapter extends BaseAdapter {
    Context context;
    List<NewRecommendedVipGoods.InfoBean> mBean;
    HomeVipInfo mHomeVipInfo;

    public HomePageVipGoodsAdapter(Context context, List<NewRecommendedVipGoods.InfoBean> mBean) {
        this.context = context;
        this.mBean = mBean;
    }

    @Override
    public int getCount() {
        return mBean.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HomePageVipGoodsAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new HomePageVipGoodsAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.home_item_layout, null, false);
            holder.mTu = (ImageView) convertView.findViewById(R.id.home_item_tuid);
            holder.mClick = (TextView) convertView.findViewById(R.id.home_item_click);
            holder.mTitle = (TextView) convertView.findViewById(R.id.home_item_title);
            holder.mVipRMB = (TextView) convertView.findViewById(R.id.home_item_viprmb);
            holder.mMuRMB = (TextView) convertView.findViewById(R.id.home_item_murmb);
            holder.mZiRMB = (TextView) convertView.findViewById(R.id.home_item_zirmb);
            holder.mMuText = (TextView) convertView.findViewById(R.id.home_item_mutext);
            holder.mZiText = (TextView) convertView.findViewById(R.id.home_item_zitext);
            holder.mRLayout = (RelativeLayout) convertView.findViewById(R.id.home_item_rlayout_id);//全部

            convertView.setTag(holder);
        } else {
            holder = (HomePageVipGoodsAdapter.ViewHolder) convertView.getTag();
        }
        if (this.mBean != null) {
            String vip_id = mBean.get(position).getVip_id();//商品id
            String thumb = mBean.get(position).getVimg_url() + "";
            String title = mBean.get(position).getVip_name();
            String click = mBean.get(position).getVip_num() + "";
            String viprmb = mBean.get(position).getVip_money();
            String vip_is_top=mBean.get(position).getVip_is_top();//是否上线  1上线  0下线

            if(vip_is_top.equals("0")){
                holder.mRLayout.setVisibility(View.GONE);
            }

            if (true) {//vip
                holder.mVipRMB.setVisibility(View.VISIBLE);
                holder.mMuText.setVisibility(View.GONE);
                holder.mZiText.setVisibility(View.GONE);
                holder.mMuRMB.setVisibility(View.GONE);
                holder.mZiRMB.setVisibility(View.GONE);
                holder.mVipRMB.setText("¥" + viprmb);
            }
            holder.mClick.setText(click);
            holder.mTitle.setText(title);

            if (thumb.equals("") | thumb.equals("null")) {
            } else {
                thumb = C.TU + thumb;
                RequestOptions requestOptions=new RequestOptions();
                requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
                Glide.with(context).load(thumb).apply(requestOptions).into(holder.mTu);
            }
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("vip_id", vip_id);
            list.add(map);
            mHomeVipInfo.onVipItemClicks(holder.mRLayout, list);

        }
        return convertView;
    }

    public void setOnVipItemClicks(HomePageFragment mHomeVipInfo) {
        this.mHomeVipInfo = mHomeVipInfo;
    }

    public void setOnAllVipItemClicks(AllVipGoodsActivity mHomeVipInfo) {
        this.mHomeVipInfo = mHomeVipInfo;
    }

    private class ViewHolder {
        ImageView mTu;//图片
        TextView mTitle;//标题
        TextView mZiRMB;//子币
        TextView mZiText;//子币text
        TextView mMuRMB;//母币
        TextView mMuText;//母币text
        TextView mClick;//人数
        TextView mVipRMB;//vip钱
        RelativeLayout mRLayout;//总
    }
}

package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.OpenGroupActivity;
import com.linkhand.fenxiao.activity.homepage.home.SearchActivity;
import com.linkhand.fenxiao.activity.homepage.home.TicketActivity;
import com.linkhand.fenxiao.feng.home.GroupListFeng;
import com.linkhand.fenxiao.info.callback.HomeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/3/7.
 */

public class OpenGroupAdapter extends BaseAdapter {
    Context context;
    List<GroupListFeng.InfoBean> beanList;
    HomeInfo mHomeInfo;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public OpenGroupAdapter(Context context, List<GroupListFeng.InfoBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    public void setHomeInfo(TicketActivity homeInfo) {
        mHomeInfo = homeInfo;
    }

    @Override
    public int getCount() {
        return beanList.size();
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

        OpenGroupAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new OpenGroupAdapter.ViewHolder();
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
            holder.mRLayout = (RelativeLayout) convertView.findViewById(R.id.home_item_rlayout_id);

            convertView.setTag(holder);
        } else {
            holder = (OpenGroupAdapter.ViewHolder) convertView.getTag();
        }
        if (this.beanList != null) {
            GroupListFeng.InfoBean child = beanList.get(position);
            String id = child.getGood_id();//(商品id)
            String thumb = child.getImg_url() + "";//(商品图片)
            String title = child.getGood_name();//(商品名称)
            String click = child.getAll_num() + "";//(已下单数量)
            String murmb = child.getMoney_mater() + "";//(母币价格)
            String zirmb = child.getMoney_son() + "";//(子币价格)


            preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            editor = preferences.edit();
            //获取子母币名称
            String Mater_name = preferences.getString("Mater_name", "母币");//母币名称
            String Son_name = preferences.getString("Son_name", "子币");//子币名称
            holder.mZiText.setText(Son_name);
            holder.mMuText.setText(Mater_name);
            holder.mClick.setText(click);
            holder.mTitle.setText(title);

            holder.mMuText.setVisibility(View.VISIBLE);
            holder.mZiText.setVisibility(View.VISIBLE);
            holder.mMuRMB.setVisibility(View.VISIBLE);
            holder.mZiRMB.setVisibility(View.VISIBLE);
            holder.mVipRMB.setVisibility(View.GONE);
            holder.mMuRMB.setText(murmb);
            holder.mZiRMB.setText("¥" + zirmb);


            if (thumb.equals("") | thumb.equals("null")) {

            } else {
                thumb = C.TU + thumb;
//                Log.e("yh","thumb--"+thumb);
                Glide.with(context)
                        .load(thumb)
                        .into(holder.mTu);
            }

            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> maps = new HashMap<>();
            maps.put("id", id);
            list.add(maps);
            if (mHomeInfo != null) {
                mHomeInfo.onItemClicks(holder.mRLayout, list);
            }
        }
        return convertView;
    }

    public void setOnItemClicks(OpenGroupActivity mHomeInfo) {
        this.mHomeInfo = mHomeInfo;
    }

    public void setOnItemClicks(SearchActivity mHomeInfo) {
        this.mHomeInfo = mHomeInfo;
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
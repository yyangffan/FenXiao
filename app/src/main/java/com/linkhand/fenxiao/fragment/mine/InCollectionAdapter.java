package com.linkhand.fenxiao.fragment.mine;

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
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.mine.InCollectionFeng;
import com.linkhand.fenxiao.info.callback.CollectionInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/3/16.
 */

public class InCollectionAdapter  extends BaseAdapter {
    Context context;
    List<InCollectionFeng.InfoBean> beanList;
    CollectionInfo mCollectionInfo;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public InCollectionAdapter(Context context, List<InCollectionFeng.InfoBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    public void setData(List<InCollectionFeng.InfoBean> data) {
        this.beanList = data;
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

        InCollectionAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new InCollectionAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.collection_item_layout, null, false);
            holder.mTu = (ImageView) convertView.findViewById(R.id.collection_item_tuid);
            holder.mTitle = (TextView) convertView.findViewById(R.id.collection_item_title);
            holder.mMuRMB = (TextView) convertView.findViewById(R.id.collection_item_murmb);
            holder.mZiRMB = (TextView) convertView.findViewById(R.id.collection_item_zirmb);
            holder.mMuText = (TextView) convertView.findViewById(R.id.collection_item_mutext);
            holder.mZiText = (TextView) convertView.findViewById(R.id.collection_item_zitext);
            holder.mRLayout = (RelativeLayout) convertView.findViewById(R.id.collection_item_rlayout_id);
            holder.mCancel = (TextView) convertView.findViewById(R.id.collection_item_goumai);//取消收藏


            convertView.setTag(holder);
        } else {
            holder = (InCollectionAdapter.ViewHolder) convertView.getTag();
        }
        if (this.beanList != null) {
            InCollectionFeng.InfoBean childMap = beanList.get(position);
            String good_id = childMap.getIdea_id();//意向id
            String mater_money = childMap.getIdea_mater();//母币价格
            String son_money = childMap.getIdea_son();//子币价格
            String good_name = childMap.getIdea_name();//意向商品名称
            String thumb = childMap.getIdea_img_url();//商品图片
            String house_id=childMap.getIha_id();//收藏id

            preferences =context.getSharedPreferences("user", Context.MODE_PRIVATE);
            editor = preferences.edit();
            preferences =context.getSharedPreferences("user", Context.MODE_PRIVATE);
            editor = preferences.edit();
            //获取子母币名称
            String Mater_name = preferences.getString("Mater_name", "母币");//母币名称
            String Son_name = preferences.getString("Son_name", "子币");//子币名称
            holder.mZiText.setText(Son_name);
            holder.mMuText.setText(Mater_name);
            holder.mTitle.setText(good_name);
            holder.mMuRMB.setText( "¥" +Math.floor((Double.parseDouble(mater_money)))+"");
            holder.mZiRMB.setText("¥" +  Math.floor((Double.parseDouble(son_money))));
            if (thumb.equals("") | thumb.equals("null")) {

            } else {
                thumb = C.TU + thumb;
                RequestOptions requestOptions=new RequestOptions();
                requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
                Glide.with(context).load(thumb).apply(requestOptions).into(holder.mTu);
            }
            List<Map<String, Object>> mList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("good_id", good_id);//意向id
            mList.add(map);
            mCollectionInfo.onItemDetailsClicks(holder.mRLayout, holder.mCancel, mList);
        }

        return convertView;
    }

    public void setOnItemClicks(IntentionCollectionFragment mCollectionInfo) {
        this.mCollectionInfo = mCollectionInfo;
    }




    private class ViewHolder {
        ImageView mTu;//图片
        TextView mTitle;//标题
        TextView mZiRMB;//子币
        TextView mZiText;//子币text
        TextView mMuRMB;//母币
        TextView mMuText;//母币text
        TextView mCancel;//取消收藏
        RelativeLayout mRLayout;//总
    }
}
package com.linkhand.fenxiao.adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.mine.CollectionActivity;
import com.linkhand.fenxiao.feng.mine.CollectionFeng;
import com.linkhand.fenxiao.info.callback.CollectionInfo;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/7.
 */

public class CollectionAdapter extends BaseAdapter {
    Context context;
    List<CollectionFeng.InfoBean> beanList;
    CollectionInfo mCollectionInfo;

    public CollectionAdapter(Context context, List<CollectionFeng.InfoBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    public void setData(List<CollectionFeng.InfoBean> data) {
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

        CollectionAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new CollectionAdapter.ViewHolder();
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
            holder = (CollectionAdapter.ViewHolder) convertView.getTag();
        }
        if (this.beanList != null) {
//            map.put("tu","http://pic.58pic.com/58pic/14/34/62/39S58PIC9jV_1024.jpg");
//            map.put("tit","这是要收藏的这是要收藏的这是要收藏的这是要收藏的");
//            map.put("zi","100");
//            map.put("mu","10");
//            CollectionFeng.InfoBean childMap = beanList.get(position);
//            String good_id = childMap.getGood_id();//商品id
//            Object status = childMap.getStatus();//状态  除了开团的 都是失效
//            String good_is_top = childMap.getGood_is_top();//0.下架 1.开团 2.开团成功 3.取消开团
//            String mater_money = childMap.getGood_mater_money();//母币价格
//            String son_money = childMap.getGood_son_money();//子币价格
//            String good_name = childMap.getGood_name();//商品名称
//            String thumb = childMap.getImg_url();//商品图片
//            String house_id=childMap.getHouse_id();//收藏id
//
//           ;
//            holder.mTitle.setText(good_name);
//            holder.mMuRMB.setText( Math.floor((Double.parseDouble(mater_money)))+"");
//            holder.mZiRMB.setText("¥" +  Math.floor((Double.parseDouble(son_money))));
//            if (thumb.equals("") | thumb.equals("null")) {
//
//            } else {
//                thumb = C.TU + thumb;
////                Log.e("yh","thumb--"+thumb);
//                Glide.with(context)
//                        .load(thumb)
//                        .asBitmap()
//                        .into(holder.mTu);
//            }
//            List<Map<String, Object>> mList = new ArrayList<>();
//            Map<String, Object> map = new HashMap<>();
//            map.put("good_id", good_id);//商品id
//            mList.add(map);
//            mCollectionInfo.onItemDetailsClicks(holder.mRLayout, holder.mCancel, mList);
        }

        return convertView;
    }

    public void setOnItemClicks(CollectionActivity mCollectionInfo) {
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
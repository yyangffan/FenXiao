package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.IntentionActivity;
import com.linkhand.fenxiao.activity.homepage.home.SearchActivity;
import com.linkhand.fenxiao.feng.home.IntentionGoods;
import com.linkhand.fenxiao.info.callback.DetailsInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2017/12/21.
 */

public class IntentionAdapter extends BaseAdapter {
    Context context;
    List<IntentionGoods.InfoBean> mBean;
    DetailsInfo mDetailsInfo;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public IntentionAdapter(Context context, List<IntentionGoods.InfoBean> mBean) {
        this.context = context;
        this.mBean = mBean;
    }

    public void setData(List<IntentionGoods.InfoBean> mBean) {
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

        IntentionAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new IntentionAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.intention_item, null, false);
            holder.mTu = (ImageView) convertView.findViewById(R.id.intention_item_tu);//图片
            holder.mName = (TextView) convertView.findViewById(R.id.intention_item_name);
            holder.mRMB = (TextView) convertView.findViewById(R.id.intention_item_rmb);
            holder.mNumber = (TextView) convertView.findViewById(R.id.intention_item_ren);//最低人数
            holder.mTv = (TextView) convertView.findViewById(R.id.intention_item_istuan);//是否团购
            holder.mDetails = (LinearLayout) convertView.findViewById(R.id.llayout_details_id);
            holder.mOrder = (TextView) convertView.findViewById(R.id.order_id);

            convertView.setTag(holder);
        } else {
            holder = (IntentionAdapter.ViewHolder) convertView.getTag();
        }
        if (this.mBean != null) {
            preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            editor = preferences.edit();
            //获取子母币名称
            String Mater_name = preferences.getString("Mater_name", "母币");//母币名称
            String Son_name = preferences.getString("Son_name", "子币");//子币名称


            IntentionGoods.InfoBean mChild = mBean.get(position);
            String idea_id = mChild.getIdea_id();//商品id
            String thumb = mChild.getIdea_img() + "";//图片
            String name = mChild.getIdea_name();//商品名
            String idea_num = mChild.getIdea_num();//预售数量
            String idea_nownum = mChild.getIdea_nownum();//当前数量
            String idea_money = mChild.getIdea_money();//市场价格
            int is_have = mChild.getIs_have();//是否订购(是否已关注 1是 0否)
            int is_open = mChild.getIs_open();//(是否有开团 1是 0否)
            String idea_mater = mChild.getIdea_mater();//母币
            String idea_son = mChild.getIdea_son();//子币


            holder.mNumber.setText(idea_num);
            holder.mTv.setText( idea_nownum + "人想买");

            holder.mName.setText(name);
//            holder.mRMB.setText(idea_money);
            holder.mRMB.setText("¥"+idea_son+Son_name+" "+idea_mater+Mater_name);

            if (is_have == 1) {//是否订购(是否已关注 1是 0否)
                holder.mOrder.setText("取消订购");
                holder.mOrder.setBackgroundResource(R.drawable.btn_bg_rounded_four);
            } else if (is_have == 0) {
                holder.mOrder.setText("立即订购");
                holder.mOrder.setBackgroundResource(R.drawable.btn_bg_rounded_two);
            }


            if (thumb.equals("") | thumb.equals("null")) {

            } else {
                thumb = C.TU + thumb;
                RequestOptions requestOption=new RequestOptions();
                requestOption.placeholder(R.drawable.position_img).error(R.drawable.position_img);
                Glide.with(context).load(thumb).apply(requestOption).into(holder.mTu);
            }

            List<Map<String, Object>> lists = new ArrayList<>();
            Map<String, Object> maps = new HashMap<>();
            maps.put("idea_id", idea_id + "");//商品id
            maps.put("is_have", is_have + "");//是否订购(是否已关注 1是 0否)
            lists.add(maps);
            if(mDetailsInfo!=null) {
                mDetailsInfo.onItemDetailsClicks(holder.mDetails, holder.mOrder, lists);
            }

        }
        return convertView;
    }

    public void setOnItemClicks(IntentionActivity mDetailsInfo) {
        this.mDetailsInfo = mDetailsInfo;
    }

      public void setOnItemClicks(SearchActivity mDetailsInfo) {
        this.mDetailsInfo = mDetailsInfo;
    }



    private class ViewHolder {
        ImageView mTu;//图片
        TextView mName;
        TextView mRMB;
        TextView mNumber;//最低人数
        TextView mTv;//已团购人数
        LinearLayout mDetails;//点击进详情
        TextView mOrder;//订购键
    }
}

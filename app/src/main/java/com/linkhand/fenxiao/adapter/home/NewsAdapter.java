package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.MessageResponse;
import com.linkhand.fenxiao.utils.DateUtil;

import java.util.List;

/**
 * Created by 11860_000 on 2017/12/19.
 */

public class NewsAdapter extends BaseAdapter {
    Context context;
    List<MessageResponse.InfoBean> mList;

    public NewsAdapter(Context context, List<MessageResponse.InfoBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
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

        NewsAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new NewsAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.news_item, null, false);
            holder.mContent = (TextView) convertView.findViewById(R.id.news_content_id);
            holder.mTime = (TextView) convertView.findViewById(R.id.news_time_id);
            holder.mAllLayout = (LinearLayout) convertView.findViewById(R.id.all_llayout_id);//全部
            holder.mtv_red = (TextView) convertView.findViewById(R.id.item_red);

            convertView.setTag(holder);
        } else {
            holder = (NewsAdapter.ViewHolder) convertView.getTag();
        }
        if (this.mList != null) {
            MessageResponse.InfoBean infoBean = mList.get(position);

            String content = infoBean.getTrade_text();
            String time = DateUtil.getStrTime(infoBean.getTrade_time());
            holder.mContent.setText(content);
            holder.mTime.setText(time);
            int trade_look = infoBean.getTrade_look();
            if (trade_look == 0) {
                holder.mtv_red.setVisibility(View.VISIBLE);
            } else {
                holder.mtv_red.setVisibility(View.GONE);
            }
        }
        return convertView;
    }


    private class ViewHolder {
        TextView mContent;
        TextView mTime;
        TextView mtv_red;
        LinearLayout mAllLayout;
    }
}

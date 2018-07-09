package com.linkhand.fenxiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.MineGongGBean;
import com.linkhand.fenxiao.utils.DateUtil;

import java.util.List;

/**
 * Created by 杨帆 on 2018/6/28.
 */

public class GongGRecyAdapter extends RecyclerView.Adapter<GongGRecyAdapter.ViewHolder> {
    private Context mContext;
    private List<MineGongGBean.InfoBean> mLists;
    private LayoutInflater mInflater;

    public GongGRecyAdapter(Context context, List<MineGongGBean.InfoBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gongg, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        MineGongGBean.InfoBean bean = mLists.get(position);
        vh.mtv_content.setText(bean.getNo_text());
        vh.mtv_time.setText(DateUtil.getStrTime(bean.getNo_time()+""));

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mtv_content;
        TextView mtv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            mtv_content = (TextView) itemView.findViewById(R.id.item_gongg_content);
            mtv_time = (TextView) itemView.findViewById(R.id.item_gongg_time);
        }
    }

}

package com.linkhand.fenxiao.adapter;

/**
 * Created by 杨帆 on 2018/8/13.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.GoodsDetailsFeng;

import java.util.List;

public class GoumaiXzAdapter extends RecyclerView.Adapter<GoumaiXzAdapter.ViewHolder> {
    private Context mContext;
    private List<GoodsDetailsFeng.BuyRuleBean> mLists;
    private LayoutInflater mInflater;

    public GoumaiXzAdapter(Context context, List<GoodsDetailsFeng.BuyRuleBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_detail_xianzhi, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        GoodsDetailsFeng.BuyRuleBean bean = mLists.get(position);
        vh.mtv_name.setText(bean.getRebate_name());
        vh.mtv_type.setText(bean.getDay_type());
        vh.mtv_number.setText(bean.getBuy_num());

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mtv_name;
        private TextView mtv_type;
        private TextView mtv_number;


        public ViewHolder(View itemView) {
            super(itemView);
            mtv_name = (TextView) itemView.findViewById(R.id.item_xianzhi_name);
            mtv_type = (TextView) itemView.findViewById(R.id.item_xianzhi_type);
            mtv_number = (TextView) itemView.findViewById(R.id.item_xianzhi_number);


        }
    }
}


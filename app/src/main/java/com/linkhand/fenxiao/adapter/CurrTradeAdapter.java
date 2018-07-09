package com.linkhand.fenxiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.CurrTrade;
import com.linkhand.fenxiao.utils.DateUtil;

import java.util.List;

/**
 * Created by user on 2018/6/19.
 */

public class CurrTradeAdapter extends RecyclerView.Adapter<CurrTradeAdapter.ViewHolder> {
    private Context mContext;
    private List<CurrTrade.InfoBean> mLists;
    private LayoutInflater mInflater;

    public CurrTradeAdapter(Context context, List<CurrTrade.InfoBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recy_minejl, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        CurrTrade.InfoBean bean = mLists.get(position);
        vh.mtv_name.setText(bean.getType_str());
        vh.mtv_time.setText(DateUtil.getStrTime(bean.getTrade_time()));
        if(bean.getTrade_son_money().contains("-")){
            vh.mtv_top.setTextColor(mContext.getResources().getColor(R.color.black));
        }else {
            vh.mtv_top.setTextColor(mContext.getResources().getColor(R.color.colorred));
        }
        if(bean.getTrade_mater_money().contains("-")){
            vh.mtv_bot.setTextColor(mContext.getResources().getColor(R.color.black));
        }else {
            vh.mtv_bot.setTextColor(mContext.getResources().getColor(R.color.colorred));
        }
        if(bean.getTrade_mater_money().equals("没有")){
            vh.mtv_bot.setVisibility(View.GONE);
            vh.mtv_top.setText(bean.getTrade_son_money().contains("-") ? bean.getTrade_son_money()+bean.getTrade_type() : "+" + bean.getTrade_son_money()+bean.getTrade_type());
        }else {
            vh.mtv_top.setText(bean.getTrade_son_money().contains("-") ? bean.getTrade_son_money() : "+" + bean.getTrade_son_money());
            vh.mtv_bot.setVisibility(View.VISIBLE);
            vh.mtv_bot.setText(bean.getTrade_mater_money().contains("-") ? bean.getTrade_mater_money() : "+" + bean.getTrade_mater_money());
        }

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mtv_name;
        private TextView mtv_time;
        private TextView mtv_top;
        private TextView mtv_bot;


        public ViewHolder(View itemView) {
            super(itemView);
            mtv_name = (TextView) itemView.findViewById(R.id.item_mine_title);
            mtv_time = (TextView) itemView.findViewById(R.id.item_mine_time);
            mtv_top = (TextView) itemView.findViewById(R.id.item_mine_top);
            mtv_bot = (TextView) itemView.findViewById(R.id.item_mine_bot);
        }
    }
}




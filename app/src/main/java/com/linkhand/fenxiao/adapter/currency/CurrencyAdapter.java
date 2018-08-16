package com.linkhand.fenxiao.adapter.currency;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.zimu.AllCoinFeng;
import com.linkhand.fenxiao.fragment.ExchangeFragment;
import com.linkhand.fenxiao.info.callback.ProvinceInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2017/12/18.
 */

public class CurrencyAdapter extends BaseAdapter {
    Context context;
    List<AllCoinFeng.InfoBean> mList;
    ProvinceInfo mProvinceInfo;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String mUserId;

    public CurrencyAdapter(Context context, List<AllCoinFeng.InfoBean> mList) {
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


        CurrencyAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new CurrencyAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.currency_item, null, false);
            holder.mTu = (ImageView) convertView.findViewById(R.id.currency_iv_id);//图
            holder.mNumberSell = (TextView) convertView.findViewById(R.id.currency_sell_id);//
            holder.mNumberZi = (TextView) convertView.findViewById(R.id.currency_zi);//
            holder.mNumberMoney = (TextView) convertView.findViewById(R.id.currency_money);//卖的价钱
            holder.mCurrency = (TextView) convertView.findViewById(R.id.currency_tv_id);//兑换
            holder.mIsOneself = (TextView) convertView.findViewById(R.id.currency_ismy_id);//个人出售，官方出售
            holder.mSurplus = (TextView) convertView.findViewById(R.id.surplus_rmb_id);//剩余


            convertView.setTag(holder);
        } else {
            holder = (CurrencyAdapter.ViewHolder) convertView.getTag();
        }
        if (this.mList != null) {
            AllCoinFeng.InfoBean child = mList.get(position);
            String user_id = child.getUser_id();//发布者id  0为官方
            String curr_son_money = child.getCurr_son_money();//子币价格（单价）
            String curr_mater_num = child.getCurr_mater_num();//发布母币
            String curr_state_time = child.getCurr_state_time();//发布时间
            String user_name = child.getUser_name();//发布者名称
            String curr_state = child.getCurr_state();//状态  1未出售
            String curr_id = child.getCurr_id();//子母币id

            if (user_id.equals("0")) {//发布者id  0为官方
                holder.mIsOneself.setText("官方出售");
                holder.mSurplus.setVisibility(View.INVISIBLE);
            } else {
                holder.mIsOneself.setText("个人：" + user_name);
                holder.mSurplus.setVisibility(View.VISIBLE);
            }
            preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            editor = preferences.edit();
            //获取子母币名称
            String Mater_name = preferences.getString("Mater_name", "母币");//母币名称
            String Son_name = preferences.getString("Son_name", "子币");//子币名称
            mUserId = preferences.getString("user_id", "");
            holder.mNumberMoney.setText(curr_son_money);
            holder.mNumberZi.setText(Son_name);
            holder.mNumberSell.setText("/" + Mater_name + "单价");
            holder.mSurplus.setText("剩余" + curr_mater_num + Mater_name);
            if (user_id.equals(mUserId)) {
                holder.mCurrency.setText("撤销");
            } else {
                holder.mCurrency.setText("兑换");
            }

            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("curr_id", curr_id);//子母币id
            map.put("curr_son_money", curr_son_money);//子币价钱
            map.put("curr_mater_num", curr_mater_num);//剩余(发布)母币
            map.put("user_id", user_id);//发布者id  0为官方
            list.add(map);
            mProvinceInfo.onProvinceItemClicks(holder.mCurrency, list);
        }

        return convertView;
    }

    public void setOnItemClicks(ExchangeFragment mProvinceInfo) {
        this.mProvinceInfo = mProvinceInfo;
    }

    private class ViewHolder {
        ImageView mTu;
        TextView mNumberSell;//
        TextView mNumberZi;//
        TextView mNumberMoney;//卖的价钱
        TextView mCurrency;//兑换
        TextView mSurplus;//剩余xx母币
        TextView mIsOneself;//个人出售，官方出售


    }
}
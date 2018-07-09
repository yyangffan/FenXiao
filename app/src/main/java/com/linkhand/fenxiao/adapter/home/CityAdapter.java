package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.MyVipActivity;
import com.linkhand.fenxiao.activity.homepage.home.ShippingAddressActivity;
import com.linkhand.fenxiao.activity.homepage.home.UpdateAddressActivity;
import com.linkhand.fenxiao.activity.homepage.home.VipSearchActivity;
import com.linkhand.fenxiao.feng.home.CityFeng;
import com.linkhand.fenxiao.info.callback.CityInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/2/23.
 */

public class CityAdapter  extends BaseAdapter {
    Context context;
    List<CityFeng.InfoBean> mBeanList;
    CityInfo mCityInfo;
    private VipSearchActivity mOnCityClicks;

    public CityAdapter(Context context, List<CityFeng.InfoBean> mBeanList) {
        this.context = context;
        this.mBeanList = mBeanList;
    }

    @Override
    public int getCount() {
        return mBeanList.size();
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

        CityAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new CityAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.province_adapter_item, null, false);
            holder.mContent = (TextView) convertView.findViewById(R.id.according_name);

            convertView.setTag(holder);
        } else {
            holder = (CityAdapter.ViewHolder) convertView.getTag();
        }
        if (this.mBeanList != null) {
            CityFeng.InfoBean child = mBeanList.get(position);
            String content = child.getCity_name();
            String city_id=child.getCity_id();
            holder.mContent.setText(content);

            List<Map<String,Object>> mList=new ArrayList<>();
            Map<String,Object> map=new HashMap<>();
            map.put("city_id",city_id);
            map.put("city_name",content);
            mList.add(map);
            mCityInfo.onCityItemClicks(holder.mContent,mList);
        }
        return convertView;
    }

    public void setOnCityClicks(ShippingAddressActivity mCityInfo) {
        this.mCityInfo = mCityInfo;
    }

    public void setOnUpdateCityClicks(UpdateAddressActivity mCityInfo) {
        this.mCityInfo = mCityInfo;
    }

    public void setOnCityClicks(MyVipActivity mCityInfo) {
        this.mCityInfo = mCityInfo;
    }

    public void setOnCityClicks(VipSearchActivity onCityClicks) {
        mCityInfo = onCityClicks;
    }


    private class ViewHolder {
        TextView mContent;
    }
}

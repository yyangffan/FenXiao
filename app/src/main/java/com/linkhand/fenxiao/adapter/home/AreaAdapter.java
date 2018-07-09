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
import com.linkhand.fenxiao.info.callback.AreaInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/2/23.
 */

public class AreaAdapter extends BaseAdapter {
    Context context;
    List<CityFeng.InfoBean> mBeanList;
    AreaInfo mAreaInfo;
    private VipSearchActivity mOnAreaClicks;

    public AreaAdapter(Context context, List<CityFeng.InfoBean> mBeanList) {
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

        AreaAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new AreaAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.province_adapter_item, null, false);
            holder.mContent = (TextView) convertView.findViewById(R.id.according_name);

            convertView.setTag(holder);
        } else {
            holder = (AreaAdapter.ViewHolder) convertView.getTag();
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
            mAreaInfo.onAreaItemClicks(holder.mContent,mList);
        }
        return convertView;
    }

    public void setOnAreaClicks(ShippingAddressActivity mAreaInfo) {
        this.mAreaInfo = mAreaInfo;
    }

    public void setOnUpdateAreaClicks(UpdateAddressActivity mAreaInfo) {
        this.mAreaInfo = mAreaInfo;
    }

    public void setOnAreaClicks(MyVipActivity mAreaInfo) {
        this.mAreaInfo = mAreaInfo;
    }

    public void setOnAreaClicks(VipSearchActivity onAreaClicks) {
        mAreaInfo = onAreaClicks;
    }


    private class ViewHolder {
        TextView mContent;
    }
}

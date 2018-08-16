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
import com.linkhand.fenxiao.feng.home.ProvinceFeng;
import com.linkhand.fenxiao.info.callback.ProvinceInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/2/11.
 */

public class ProvinceAdapter extends BaseAdapter {
    Context context;
    List<ProvinceFeng.InfoBean> mBeanList;
    ProvinceInfo mProvinceInfo;
    private VipSearchActivity mOnProvinceClicks;

    public ProvinceAdapter(Context context, List<ProvinceFeng.InfoBean> mBeanList) {
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

        ProvinceAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new ProvinceAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.province_adapter_item, null, false);
            holder.mContent = (TextView) convertView.findViewById(R.id.according_name);

            convertView.setTag(holder);
        } else {
            holder = (ProvinceAdapter.ViewHolder) convertView.getTag();
        }
        if (this.mBeanList != null) {
            ProvinceFeng.InfoBean child = mBeanList.get(position);
            String city_id= child.getCity_id();
            String content = child.getCity_name();
            holder.mContent.setText(content);

            List<Map<String,Object>> mList=new ArrayList<>();
            Map<String,Object> map=new HashMap<>();
            map.put("city_id",city_id);
            map.put("city_name",content);
            mList.add(map);
            mProvinceInfo.onProvinceItemClicks(holder.mContent,mList);
        }
        return convertView;
    }

    public void setOnProvinceClicks(ShippingAddressActivity mProvinceInfo) {
        this.mProvinceInfo = mProvinceInfo;
    }

    public void setOnUpdateProvinceClicks(UpdateAddressActivity mProvinceInfo) {
        this.mProvinceInfo = mProvinceInfo;
    }

    public void setOnProvinceClicks(MyVipActivity mProvinceInfo) {
        this.mProvinceInfo = mProvinceInfo;
    }

    private class ViewHolder {
        TextView mContent;
    }
}
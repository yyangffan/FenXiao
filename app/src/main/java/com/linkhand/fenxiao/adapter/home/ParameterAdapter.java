package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.GoodsDetailsFeng;

import java.util.List;

/**
 * Created by 11860_000 on 2018/3/8.
 */

public class ParameterAdapter extends BaseAdapter {
    Context context;
    List<GoodsDetailsFeng.InfoBean.AttrBean> beanList;

    public ParameterAdapter(Context context, List<GoodsDetailsFeng.InfoBean.AttrBean> beanList) {
        this.context = context;
        this.beanList = beanList;
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

        ParameterAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new ParameterAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.parameter_layout, null, false);
            holder.mName = (TextView) convertView.findViewById(R.id.parameter_name_id);
            holder.mContent = (TextView) convertView.findViewById(R.id.parameter_content_id);

            convertView.setTag(holder);
        } else {
            holder = (ParameterAdapter.ViewHolder) convertView.getTag();
        }
        if (this.beanList != null) {
            GoodsDetailsFeng.InfoBean.AttrBean child = beanList.get(position);
            String attr_val = child.getAttr_val();//(参数名称)
            String attr_name = child.getAttr_name() + "";//(参数值)

            holder.mName.setText(attr_name);
            holder.mContent.setText(attr_val);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView mName;//参数名称
        TextView mContent;//参数值
    }
}
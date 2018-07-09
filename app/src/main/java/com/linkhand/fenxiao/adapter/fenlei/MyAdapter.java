package com.linkhand.fenxiao.adapter.fenlei;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.fenlei.LeftClassFeng;

import java.util.List;

/**
 * Created by djz on 2016/11/10.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
//    private List<Category> results;
    List<LeftClassFeng.InfoBean> results;
    private ViewHolder holder = null;
    private int selectedId;

    public MyAdapter(Context context,List<LeftClassFeng.InfoBean> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public int getCount() {

        return results.size();
    }

    @Override
    public Object getItem(int position) {

        return results.get(position);
    }


    @Override
    public long getItemId(int position) {

        return Long.parseLong(results.get(position).getCate_id());
    }

    public void setSelected(int position) {
        this.selectedId = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Category c = (Category) getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.jd_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_jdleft_item);
            holder.viewcolor = (View) convertView.findViewById(R.id.viewcolor_id);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (this.results != null) {
            LeftClassFeng.InfoBean child= results.get(position);

            if (position == selectedId) {
                holder.viewcolor.setVisibility(View.VISIBLE);
                convertView.setBackgroundResource(R.color.colorwhites);
                holder.tv.setTextColor(Color.RED);
            } else {
                holder.viewcolor.setVisibility(View.GONE);
                convertView.setBackgroundResource(R.color.colorgraynessb);
                holder.tv.setTextColor(Color.BLACK);
            }

            holder.tv.setText(child.getCate_name());
            convertView.setTag(holder);
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv;
        View viewcolor;
    }
}
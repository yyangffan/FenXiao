package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.fenlei.Category;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/26.
 */

public class GoodsDetailsAdapter  extends BaseAdapter {
    Context context;
    List<Category> results;
    ViewHolder holder = null;

    public GoodsDetailsAdapter(Context context, List<Category> results) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category c = (Category) getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.detailpage_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.detailpage_tv_id);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setTag(holder);

        holder.tv.setText(c.getName());

        return convertView;
    }

    class ViewHolder {
        TextView tv;
    }
}

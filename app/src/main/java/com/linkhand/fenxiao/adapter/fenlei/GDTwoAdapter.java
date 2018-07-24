package com.linkhand.fenxiao.adapter.fenlei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.fenlei.RightClassFeng;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/26.
 */

public class GDTwoAdapter extends BaseAdapter {
    Context context;
    ViewHolder holder = null;
    List<RightClassFeng.InfoBean.BrandBean> results;

    public GDTwoAdapter(Context context, List<RightClassFeng.InfoBean.BrandBean> results) {
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
//            Category c = (Category) getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.jd_right_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_jd_item);
            holder.imv = (ImageView) convertView.findViewById(R.id.imv_jd_item);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (this.results != null) {
            String cate_id = results.get(position).getBrand_id();//商品id
            String thrmb = results.get(position).getBrand_img();//商品图片

            convertView.setTag(holder);
            holder.tv.setText(results.get(position).getBrand_name());

            if (thrmb.equals("") | thrmb.equals("null")) {
            } else {
                thrmb = C.TU + thrmb;
                RequestOptions requestOptions=new RequestOptions();
                requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
                Glide.with(context)
                        .load(thrmb)
                        .apply(requestOptions)
                        .into(holder.imv);
            }

        }
        return convertView;
    }

    class ViewHolder {
        TextView tv;
        ImageView imv;
    }
}
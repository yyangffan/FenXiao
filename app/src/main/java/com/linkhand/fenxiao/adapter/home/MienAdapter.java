package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.bean.MienBean;
import com.linkhand.fenxiao.info.callback.MienInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 11860_000 on 2018/4/12.
 */

public class MienAdapter extends BaseAdapter {
    Context context;
    List<MienBean.InfoBean> myList;
    MienInfo mMienInfo;

    public MienAdapter(Context context, List<MienBean.InfoBean> myList) {
        this.context = context;
        this.myList = myList;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Category c = (Category) getItem(position);
        MienAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new MienAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.mien_adapter_item, null);
            holder.mTitle = (TextView) convertView.findViewById(R.id.mien_item_title);//标题
            holder.mLike = (ImageView) convertView.findViewById(R.id.mien_item_tu);//点赞
            holder.mImg = (ImageView) convertView.findViewById(R.id.mien_item_tuid);//图
            holder.mRen = (TextView) convertView.findViewById(R.id.mien_item_ren);//人
            holder.mRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.all_rlayout_id);
            convertView.setTag(holder);
        } else {
            holder = (MienAdapter.ViewHolder) convertView.getTag();
        }
        if (myList != null) {
            MienBean.InfoBean mChild = myList.get(position);
            String title = mChild.getAr_title();//标题
            String thumb = C.TU+mChild.getAr_img_url();//图
            String ren = mChild.getPraise();//点赞人数
            String time = mChild.getAr_add_time();//时间戳（秒）

            holder.mTitle.setText(title);//标题
            holder.mRen.setText(ren + "人");//人
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
            Glide.with(context).load(thumb).apply(requestOptions).into(holder.mImg);
            String is_have = mChild.getIs_have();
            if(is_have.equals("0")){
                holder.mLike.setImageResource(R.drawable.heart_gray);
            }else {
                holder.mLike.setImageResource((R.drawable.heart_red));
            }

        }


        return convertView;
    }



    //时间戳转字符串
    public static String getStrTime(String timeStamp) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        "yyyy-MM-dd HH:mm"
        long l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    class ViewHolder {
        TextView mTitle;//标题
        ImageView mLike;//点赞
        ImageView mImg;//图
        TextView mRen;//人
        RelativeLayout mRelativeLayout;

    }


}


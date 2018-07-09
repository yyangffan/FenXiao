package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.IdeaGoodsDetailsFeng;

import java.util.List;

/**
 * Created by 11860_000 on 2017/12/16.
 */

public class AppraiseFragmentAdapter extends BaseAdapter {
    Context context;
    List<IdeaGoodsDetailsFeng.InfoBean.EvalBean> mList;

    public AppraiseFragmentAdapter(Context context, List<IdeaGoodsDetailsFeng.InfoBean.EvalBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AppraiseFragmentAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new AppraiseFragmentAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.appraise_fragment_item, null, false);
            holder.mTu = (ImageView) convertView.findViewById(R.id.appraise_riv_id);
            holder.mName = (TextView) convertView.findViewById(R.id.appraise_name_id);
            holder.mContent = (TextView) convertView.findViewById(R.id.appraise_content_id);

            holder.mImg0 = (ImageView) convertView.findViewById(R.id.iv_one);
            holder.mImg1 = (ImageView) convertView.findViewById(R.id.iv_two);
            holder.mImg2 = (ImageView) convertView.findViewById(R.id.iv_three);
            holder.mImg3 = (ImageView) convertView.findViewById(R.id.iv_four);
            holder.mImg4 = (ImageView) convertView.findViewById(R.id.iv_five);
            holder.mll= (LinearLayout) convertView.findViewById(R.id.item_fragment_ll);
            convertView.setTag(holder);
        } else {
            holder = (AppraiseFragmentAdapter.ViewHolder) convertView.getTag();
        }
        if (this.mList != null) {

            IdeaGoodsDetailsFeng.InfoBean.EvalBean child = mList.get(position);
            String thumb = child.getUser_head_img() + "";
            String name = child.getUser_name() + "";
            String content = child.getEvaluate_content() + "";
            List<String> eimgBeanList = child.getEimg();//全部评论图片
            if(eimgBeanList.size()==0){
                holder.mll.setVisibility(View.INVISIBLE);
            }else {
                holder.mll.setVisibility(View.VISIBLE);
            }
            if(eimgBeanList.size()!=0){
                if(eimgBeanList.size()==1){
                    holder.mImg1.setVisibility(View.INVISIBLE);
                    holder.mImg2.setVisibility(View.INVISIBLE);
                    holder.mImg3.setVisibility(View.INVISIBLE);
                    holder.mImg4.setVisibility(View.INVISIBLE);
                }else if(eimgBeanList.size()==2){
                    holder.mImg2.setVisibility(View.INVISIBLE);
                    holder.mImg3.setVisibility(View.INVISIBLE);
                    holder.mImg4.setVisibility(View.INVISIBLE);
                }else if(eimgBeanList.size()==3){
                    holder.mImg3.setVisibility(View.INVISIBLE);
                    holder.mImg4.setVisibility(View.INVISIBLE);
                }else if(eimgBeanList.size()==4){
                    holder.mImg4.setVisibility(View.INVISIBLE);
                }
            }
            if (eimgBeanList != null) {
                for (int i = 0; i < eimgBeanList.size(); i++) {
                    String eimg_url = eimgBeanList.get(i);//评论图片
                    if (eimg_url.equals("") | eimg_url.equals("null")) {
                    } else {
                        eimg_url = C.TU + eimg_url;
                        if (i == 0) {
                            Glide.with(context).load(eimg_url).into(holder.mImg0);
                        } else if (i == 1) {
                            Glide.with(context).load(eimg_url).into(holder.mImg1);
                        } else if (i == 2) {
                            Glide.with(context).load(eimg_url).into(holder.mImg2);
                        } else if (i == 3) {
                            Glide.with(context).load(eimg_url).into(holder.mImg3);
                        } else if (i == 4) {
                            Glide.with(context).load(eimg_url).into(holder.mImg4);
                        }
                    }
                }
            }

//            String time = map.get("time").toString() + "";
//            String color = map.get("color").toString() + "";

            holder.mName.setText(name);
            holder.mContent.setText(content);
//            holder.mTime.setText(time);
//            holder.mTime.setText(color);

            if (thumb.equals("") | thumb.equals("null")) {

            } else {
                thumb = C.TU + thumb;
                RequestOptions options = new RequestOptions().centerCrop()
                        .placeholder(R.drawable.default_portrait)
                        .error(R.drawable.default_portrait)
                        .priority(Priority.HIGH);
                Glide.with(context).load(thumb).apply(options).into(holder.mTu);
//                Glide.with(context)
//                        .load(thumb)
//                        .into(holder.mTu);
            }


        }
        return convertView;
    }


    private class ViewHolder {
        ImageView mTu;//图片
        TextView mName;
        TextView mContent;
        ImageView mImg0, mImg1, mImg2, mImg3, mImg4;
        LinearLayout mll;

//        TextView mTime;
//        TextView mColor;
    }
}
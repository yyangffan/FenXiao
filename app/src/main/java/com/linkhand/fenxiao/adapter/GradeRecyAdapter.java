package com.linkhand.fenxiao.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.bean.PinglunBean;
import com.linkhand.fenxiao.utils.RoundImageView;

import java.util.List;

/**
 * Created by 杨帆 on 2018/6/25.
 */

public class GradeRecyAdapter extends RecyclerView.Adapter<GradeRecyAdapter.ViewHolder> {
    private Context mContext;
    private List<PinglunBean.InfoBean> mLists;
    private LayoutInflater mInflater;

    public GradeRecyAdapter(Context context, List<PinglunBean.InfoBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_upgrade_recy, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        PinglunBean.InfoBean bean = mLists.get(position);
        vh.mtv_title.setText(bean.getUser_name());
        vh.mtv_content.setText(bean.getEvaluate_content());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.default_portrait).error(R.drawable.default_portrait);
        Glide.with(mContext).load(C.TU + bean.getUser_head_img()).apply(requestOptions).into(vh.mimgv);
        if (bean.getEimg() != null && bean.getEimg().size() != 0) {
            vh.mrecy_item.setVisibility(View.VISIBLE);
            GradeItemIAdapter gradeItemIAdapter = new GradeItemIAdapter(mContext, bean.getEimg());
            GridLayoutManager manager = new GridLayoutManager(mContext, 3){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            vh.mrecy_item.setLayoutManager(manager);
            vh.mrecy_item.setAdapter(gradeItemIAdapter);
        } else {
            vh.mrecy_item.setVisibility(View.GONE);
        }
        vh.mrecy_item.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        if(bean.getEvaluate_star_num().equals("没有")){
            vh.mRatingBar.setVisibility(View.GONE);
        }else {
            vh.mRatingBar.setVisibility(View.VISIBLE);
            vh.mRatingBar.setRating(Float.parseFloat(bean.getEvaluate_star_num()));
        }

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundImageView mimgv;
        private TextView mtv_title;
        private TextView mtv_content;
        private RecyclerView mrecy_item;
        private RatingBar mRatingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            mimgv = (RoundImageView) itemView.findViewById(R.id.item_grade_imgv);
            mtv_title = (TextView) itemView.findViewById(R.id.item_grade_title);
            mtv_content = (TextView) itemView.findViewById(R.id.item_upgrade_content);
            mrecy_item = (RecyclerView) itemView.findViewById(R.id.item_upgrade_recy);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.evaluation_rb);


        }
    }

}

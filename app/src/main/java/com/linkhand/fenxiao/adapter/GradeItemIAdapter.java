package com.linkhand.fenxiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;

import java.util.List;

/**
 * Created by 杨帆 on 2018/6/25.
 */

public class GradeItemIAdapter extends RecyclerView.Adapter<GradeItemIAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mLists;
    private LayoutInflater mInflater;

    public GradeItemIAdapter(Context context, List<String> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_imgv, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        String bean = mLists.get(position);
        final RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
        Glide.with(mContext).load(C.TU + bean).apply(requestOptions).into(vh.mimgv);
//        vh.mimgv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                List<View> mlist = new ArrayList<>();
//                List<String> mdizhi = new ArrayList<>();
//                for (String str : mLists) {
//                    PhotoView photoView = new PhotoView(mContext);
//                    Glide.with(mContext).load(C.TU +str).apply(requestOptions).into(photoView);
//                    mlist.add(photoView);
//                    mdizhi.add(C.TU +str);
//                }
//                MyViewPagDialog myViewPagDialog = new MyViewPagDialog(mContext, mlist, mdizhi, position);
//                myViewPagDialog.show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mimgv;

        public ViewHolder(View itemView) {
            super(itemView);
            mimgv = (ImageView) itemView.findViewById(R.id.upgrade_item_item_imgv);
        }
    }

}

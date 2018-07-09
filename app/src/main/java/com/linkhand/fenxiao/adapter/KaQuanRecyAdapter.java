package com.linkhand.fenxiao.adapter;

/**
 * Created by 杨帆 on 2018/6/30.
 */

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.VipLvResponse;

import java.util.List;

public class KaQuanRecyAdapter extends RecyclerView.Adapter<KaQuanRecyAdapter.ViewHolder> {
    private Context mContext;
    private List<VipLvResponse.UsedataBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mItemClickListener;


    public KaQuanRecyAdapter(Context context, List<VipLvResponse.UsedataBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recy_kaquan, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        VipLvResponse.UsedataBean bean = mLists.get(position);
        Glide.with(mContext).load(C.TU + bean.getUse_img()).into(vh.mimgv);
        vh.mtv_title.setText(bean.getUse_name());
        vh.mtv_position.setText(bean.getCity_str());
        vh.mtv_money.setText("¥" + bean.getUse_money());
        String state = bean.getNum_state();
        vh.mtv_state.setText(state);
        vh.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.OnItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mimgv;
        private TextView mtv_title;
        private TextView mtv_position;
        private TextView mtv_money;
        private TextView mtv_state;
        private ConstraintLayout mConstraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mimgv = (ImageView) itemView.findViewById(R.id.item_kaquan_imgv);
            mtv_title = (TextView) itemView.findViewById(R.id.item_kaquan_title);
            mtv_position = (TextView) itemView.findViewById(R.id.item_kaquan_position);
            mtv_money = (TextView) itemView.findViewById(R.id.item_kaquan_money);
            mtv_state = (TextView) itemView.findViewById(R.id.item_kaquan_state);
            mConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.item_kaquan_cons);

        }
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);
    }

}


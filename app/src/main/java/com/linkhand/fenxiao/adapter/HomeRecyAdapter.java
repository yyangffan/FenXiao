package com.linkhand.fenxiao.adapter;

/**
 * Created by 杨帆 on 2018/7/26.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.RecommendedGoods;

import java.util.List;

public class HomeRecyAdapter extends RecyclerView.Adapter<HomeRecyAdapter.ViewHolder> {
    private Context mContext;
    private List<RecommendedGoods.InfoBean> beanList;
    private LayoutInflater mInflater;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private final String mMater_name;  //母币名称
    private final String mSon_name;  //子币名称
    private OnItemClickListener mOnItemClickListener;

    public HomeRecyAdapter(Context context, List<RecommendedGoods.InfoBean> stringList) {
        mContext = context;
        beanList = stringList;
        mInflater = LayoutInflater.from(mContext);
        preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mMater_name = preferences.getString("Mater_name", "母币");
        mSon_name = preferences.getString("Son_name", "子币");
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home_recy, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        RecommendedGoods.InfoBean child = beanList.get(position);
        String id = child.getGood_id();//(商品id)
        String thumb = child.getImg_url() + "";//(商品图片)
        String title = child.getGood_name();//(商品名称)
        String click = child.getAll_num() + "";//(已下单数量)
        String murmb = child.getMoney_mater() + "";//(母币价格)
        String zirmb = child.getMoney_son() + "";//(子币价格)

        holder.mZiText.setText(mSon_name);
        holder.mMuText.setText(mMater_name);

        holder.mClick.setText(click);
        holder.mTitle.setText(title);

        holder.mMuText.setVisibility(View.VISIBLE);
        holder.mZiText.setVisibility(View.VISIBLE);
        holder.mMuRMB.setVisibility(View.VISIBLE);
        holder.mZiRMB.setVisibility(View.VISIBLE);
        holder.mVipRMB.setVisibility(View.GONE);
        holder.mMuRMB.setText("¥" + murmb);
        holder.mZiRMB.setText("¥" + zirmb);
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
        Glide.with(mContext).load(C.TU + thumb).apply(requestOptions).into(holder.mTu);
        holder.mRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.OnItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList == null ? 0 : beanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mTu;//图片
        TextView mTitle;//标题
        TextView mZiRMB;//子币
        TextView mZiText;//子币text
        TextView mMuRMB;//母币
        TextView mMuText;//母币text
        TextView mClick;//人数
        TextView mVipRMB;//vip钱
        RelativeLayout mRLayout;//总

        public ViewHolder(View itemView) {
            super(itemView);
            mTu = (ImageView) itemView.findViewById(R.id.home_item_tuid);
            mClick = (TextView) itemView.findViewById(R.id.home_item_click);
            mTitle = (TextView) itemView.findViewById(R.id.home_item_title);
            mVipRMB = (TextView) itemView.findViewById(R.id.home_item_viprmb);
            mMuRMB = (TextView) itemView.findViewById(R.id.home_item_murmb);
            mZiRMB = (TextView) itemView.findViewById(R.id.home_item_zirmb);
            mMuText = (TextView) itemView.findViewById(R.id.home_item_mutext);
            mZiText = (TextView) itemView.findViewById(R.id.home_item_zitext);
            mRLayout = (RelativeLayout) itemView.findViewById(R.id.home_item_rlayout_id);

        }
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);
    }

}

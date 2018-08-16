package com.linkhand.fenxiao.adapter;

/**
 * Created by 杨帆 on 2018/7/25.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkhand.fenxiao.R;

import java.util.List;
import java.util.Map;

public class MyRecyAdapter extends RecyclerView.Adapter<MyRecyAdapter.ViewHolder> {
    private Context mContext;
    private List<Map<String, Object>> mLists;
    private LayoutInflater mInflater;
    private OnTitlClickListener mOnTitleListener;
    private float mX = 0;

    public MyRecyAdapter(Context context, List<Map<String, Object>> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnTitleListener(OnTitlClickListener onTitleListener) {
        mOnTitleListener = onTitleListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recy, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int position) {
        Map<String, Object> bean = mLists.get(position);
        String title = (String) bean.get("title");
        boolean isSelect = (boolean) bean.get("isSelect");
        if(isSelect){
            vh.mtv_line.setVisibility(View.VISIBLE);
            vh.mtv.setTextColor(mContext.getResources().getColor(R.color.colorred));
        }else {
            vh.mtv_line.setVisibility(View.GONE);
            vh.mtv.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        vh.mtv.setText(title);
        vh.mtv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mX = motionEvent.getRawX();
                return false;
            }
        });
        vh.mtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnTitleListener != null) {
                    mOnTitleListener.OnTitleClickListener(position, mX);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mtv;
        private TextView mtv_line;

        public ViewHolder(View itemView) {
            super(itemView);
            mtv = (TextView) itemView.findViewById(R.id.item_recy_tv);
            mtv_line = (TextView) itemView.findViewById(R.id.item_recy_line);

        }
    }

    public interface OnTitlClickListener {
        void OnTitleClickListener(int position, float x);
    }

}

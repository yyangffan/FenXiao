package com.linkhand.fenxiao.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.linkhand.fenxiao.feng.home.TuiHBean;

import java.util.List;

/**
 * Created by 杨帆 on 2018/6/27.
 */

public class TuiHAdapter extends BaseAdapter {
    private Context mContext;
    private List<TuiHBean.InfoBean> mInfoBeanList;
    private LayoutInflater mInflater;
    SharedPreferences preferences;
    private String mMater_name = "";//母币名称
    private String mSon_name = "";//子币名称

    public TuiHAdapter(Context context, List<TuiHBean.InfoBean> infoBeanList) {
        mContext = context;
        mInfoBeanList = infoBeanList;
        mInflater = LayoutInflater.from(mContext);
        preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        mMater_name = preferences.getString("Mater_name", "母币");
        mSon_name = preferences.getString("Son_name", "子币");
    }

    @Override
    public int getCount() {
        return mInfoBeanList == null ? 0 : mInfoBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mInfoBeanList == null ? null : mInfoBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mInfoBeanList == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = mInflater.inflate(R.layout.allorder_item, parent, false);
            vh.mimgv = (ImageView) convertView.findViewById(R.id.allorder_item_tuid);
            vh.mtv_title = (TextView) convertView.findViewById(R.id.allorder_item_title);
            vh.mtv_guige = (TextView) convertView.findViewById(R.id.allorder_item_guige);
            vh.mtv_num = (TextView) convertView.findViewById(R.id.allorder_item_number_id);
            vh.mtv_master_num = (TextView) convertView.findViewById(R.id.allorder_rmbtwo_id);
            vh.mtv_master_name = (TextView) convertView.findViewById(R.id.allorder_motherrmb_id);
            vh.mtv_son_num = (TextView) convertView.findViewById(R.id.allorder_rmb_id);
            vh.mtv_son_name = (TextView) convertView.findViewById(R.id.allorder_childrmb_id);
            vh.mtv_state = (TextView) convertView.findViewById(R.id.idorder_tv_id);
            vh.mtv_cancel = (TextView) convertView.findViewById(R.id.allorder_item_btn);
            vh.mtv_no = (TextView) convertView.findViewById(R.id.allorder_item_tv_id);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.mtv_no.setVisibility(View.GONE);
        TuiHBean.InfoBean tuiHBean = mInfoBeanList.get(position);
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
        Glide.with(mContext).load(C.TU + tuiHBean.getImg_url()).apply(requestOptions).into(vh.mimgv);
        vh.mtv_title.setText(tuiHBean.getOrder_good_name());
        String guige = "";
        for (TuiHBean.InfoBean.SpeciBean bean : tuiHBean.getSpeci()) {
            guige += ";" + bean.getSpeci_name() + ":" + bean.getGsp_value();
        }
        vh.mtv_guige.setText(guige.substring(1, guige.length()));
        vh.mtv_num.setText("X" + tuiHBean.getOrder_good_num());
        vh.mtv_master_num.setText(tuiHBean.getOrder_mater_money());
        vh.mtv_master_name.setText(mMater_name);
        vh.mtv_son_num.setText(tuiHBean.getOrder_son_money());
        vh.mtv_son_name.setText(mSon_name);
        vh.mtv_cancel.setVisibility(View.GONE);
        vh.mtv_state.setText(tuiHBean.getState_str());


        return convertView;
    }

    class ViewHolder {
        private ImageView mimgv;
        private TextView mtv_title;
        private TextView mtv_no;
        private TextView mtv_guige;
        private TextView mtv_num;
        private TextView mtv_master_num;
        private TextView mtv_master_name;
        private TextView mtv_son_num;
        private TextView mtv_son_name;
        private TextView mtv_state;
        private TextView mtv_cancel;
    }

}

package com.linkhand.fenxiao.adapter.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.DingDanResponse;
import com.linkhand.fenxiao.fragment.mineorder.All0rderFragment;
import com.linkhand.fenxiao.fragment.mineorder.IsAppraiseFragment;
import com.linkhand.fenxiao.fragment.mineorder.IsPayFragment;
import com.linkhand.fenxiao.fragment.mineorder.IsShippingFragment;
import com.linkhand.fenxiao.fragment.mineorder.IsTheGoodsFragment;
import com.linkhand.fenxiao.fragment.mineorder.IsWaitFragment;
import com.linkhand.fenxiao.info.callback.AllOrderInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2017/12/19.
 */

public class All0rderFragmentAdapter extends BaseAdapter {
    Context context;
    List<DingDanResponse.InfoBean> beanList;
    AllOrderInfo mAllOrderInfo;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String mSpeci_name;
    private String mSpeci_value;
    private OnTuiHListener mOnTuiHListener;

    public All0rderFragmentAdapter(Context context, List<DingDanResponse.InfoBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    public void setData(List<DingDanResponse.InfoBean> beanList) {
        this.beanList = beanList;
    }

    public void setOnTuiHListener(OnTuiHListener onTuiHListener) {
        mOnTuiHListener = onTuiHListener;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        All0rderFragmentAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new All0rderFragmentAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.allorder_item, null, false);
            holder.mTu = (ImageView) convertView.findViewById(R.id.allorder_item_tuid);//图片
            holder.mTitle = (TextView) convertView.findViewById(R.id.allorder_item_title);//标题
            holder.mSpecificationName = (TextView) convertView.findViewById(R.id.allorder_item_tv_id);//规格名
            holder.mSpecification = (TextView) convertView.findViewById(R.id.allorder_item_guige);//规格值
            holder.mCurrentRMB = (TextView) convertView.findViewById(R.id.allorder_rmb_id);//子币价格
            holder.mMotherRMB = (TextView) convertView.findViewById(R.id.allorder_rmbtwo_id);//母币价格
//            holder.mOriginalRMB = (TextView) convertView.findViewById(R.id.allorder_rmb_id2);//原来价格
            holder.mNumber = (TextView) convertView.findViewById(R.id.allorder_item_number_id);//数量
            holder.mButton = (TextView) convertView.findViewById(R.id.allorder_item_btn);
            holder.mIsOrder = (TextView) convertView.findViewById(R.id.idorder_tv_id);//拼团中。。。
            holder.mZiText = (TextView) convertView.findViewById(R.id.allorder_childrmb_id);//子币名称
            holder.mMuText = (TextView) convertView.findViewById(R.id.allorder_motherrmb_id);//母币名称
            holder.mAllOrderLLayout = (LinearLayout) convertView.findViewById(R.id.all_order_llayout);//全部的layout
            holder.mtv_tuihuo = (TextView) convertView.findViewById(R.id.allorder_item_tuihuo);//退货


            convertView.setTag(holder);
        } else {
            holder = (All0rderFragmentAdapter.ViewHolder) convertView.getTag();
        }
        if (this.beanList != null) {
            DingDanResponse.InfoBean child = beanList.get(position);
            String good_id = child.getGood_id();//商品id
            String title = child.getOrder_good_name();//商品名称
            List<DingDanResponse.InfoBean.SpeciBean> speci = child.getSpeci();//规格（全部）
            String guige = "";
            for (DingDanResponse.InfoBean.SpeciBean bean : child.getSpeci()) {
                guige += ";" + bean.getSpeci_name() + ":" + bean.getGsp_value();
            }
            if (speci.size() != 0) {
                //规格名
                mSpeci_name = speci.get(0).getSpeci_name();
                //规格值
                mSpeci_value = speci.get(0).getGsp_value();
            }
            String mater_money = child.getOrder_mater_money();//母币总价
            String son_money = child.getOrder_son_money();//子币总价
            String good_num = child.getOrder_good_num();//商品数量
            String good_state = child.getOrder_state();//订单状态  0.未付款 1.拼团中 2.待发货 3.待收货 4.待评价 5.团购取消
            String thumb = child.getImg_url();//商品图片
            String order_id = child.getOrder_id();//订单id

            preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            editor = preferences.edit();
            //获取子母币名称
            String Mater_name = preferences.getString("Mater_name", "母币");//母币名称
            String Son_name = preferences.getString("Son_name", "子币");//子币名称
            holder.mZiText.setText(Son_name);
            holder.mMuText.setText(Mater_name);

            holder.mTitle.setText(title);//标题
//            holder.mSpecificationName.setText(mSpeci_name+":");//规格名
//            holder.mSpecification.setText(mSpeci_value);//规格值
            holder.mSpecificationName.setVisibility(View.GONE);
            holder.mSpecification.setText(guige.substring(1, guige.length()));//规格值
            holder.mCurrentRMB.setText("¥" + son_money);//子币价格
            holder.mMotherRMB.setText("¥" + mater_money);//母币价格
//            holder.mOriginalRMB.setText("¥" + originalRMB);//原来价格
//            holder.mOriginalRMB.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线（删除线）
            holder.mNumber.setText("x" + good_num);//数量

            if (good_state.equals("1")) {//0.未付款 1.拼团中 2.待发货 3.待收货 4.待评价 5.团购取消
                holder.mIsOrder.setText("拼团中");
                holder.mButton.setText("取消");
                holder.mButton.setVisibility(View.VISIBLE);
                holder.mtv_tuihuo.setVisibility(View.GONE);
            } else if (good_state.equals("2")) {
                holder.mIsOrder.setText("买家已付款");
                holder.mButton.setVisibility(View.GONE);
                holder.mtv_tuihuo.setVisibility(View.GONE);
            } else if (good_state.equals("3")) {
                holder.mIsOrder.setText("卖家已发货");
                holder.mButton.setText("确认收货");
                holder.mButton.setVisibility(View.VISIBLE);
                holder.mtv_tuihuo.setVisibility(View.VISIBLE);
            } else if (good_state.equals("4")) {
                holder.mIsOrder.setText("交易成功");
                holder.mButton.setText("评价");
                holder.mButton.setVisibility(View.VISIBLE);
                holder.mtv_tuihuo.setVisibility(View.VISIBLE);
            } else if (good_state.equals("0")) {
                holder.mIsOrder.setText("待付款");
                holder.mButton.setText("取消");
                holder.mButton.setVisibility(View.VISIBLE);
                holder.mtv_tuihuo.setVisibility(View.GONE);
            }
            if (thumb.equals("") | thumb.equals("null")) {

            } else {
                thumb = C.TU + thumb;
                Glide.with(context)
                        .load(thumb)
                        .into(holder.mTu);
            }
            holder.mtv_tuihuo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnTuiHListener != null) {
                        mOnTuiHListener.OnTuiHListener(position);
                    }
                }
            });

            List<Map<String, Object>> mList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("good_state", good_state);//订单状态  0.未付款 1.拼团中 2.待发货 3.待收货 4.待评价 5.团购取消
            map.put("good_id", good_id);//商品id
            map.put("order_id", order_id);//订单id
            map.put("title", title);//商品名称
            mList.add(map);
            mAllOrderInfo.onItemClicks(holder.mAllOrderLLayout, holder.mButton, mList);
        }
        return convertView;
    }

    public void setOnItemClicks(All0rderFragment mAllOrderInfo) {
        this.mAllOrderInfo = mAllOrderInfo;
    }

    public void setOnDeliveryItemClicks(IsShippingFragment mAllOrderInfo) {
        this.mAllOrderInfo = mAllOrderInfo;
    }

    public void setOnGroupItemClicks(IsPayFragment mAllOrderInfo) {
        this.mAllOrderInfo = mAllOrderInfo;
    }

    public void setOnGoodsItemClicks(IsTheGoodsFragment mAllOrderInfo) {
        this.mAllOrderInfo = mAllOrderInfo;
    }

    public void setOnEvaluationItemClicks(IsAppraiseFragment mAllOrderInfo) {
        this.mAllOrderInfo = mAllOrderInfo;
    }

    /*退货按钮的监听*/
    public interface OnTuiHListener {
        void OnTuiHListener(int position);
    }

    public void setOnIsWait(IsWaitFragment mAllOrderInfo) {
        this.mAllOrderInfo = mAllOrderInfo;
    }


//    public void setOnItemClicks(HomePageFragment mHomeInfo) {
//        this.mHomeInfo = mHomeInfo;
//    }

    private class ViewHolder {
        ImageView mTu;//图片
        TextView mTitle;//标题
        TextView mSpecificationName;//规格名
        TextView mSpecification;//规格值
        TextView mCurrentRMB;//子币价格
        TextView mMotherRMB;//母币价格
        //        TextView mOriginalRMB;//原来价格
        TextView mNumber;//数量
        TextView mIsOrder;//拼团中。。。
        TextView mButton;//按钮
        LinearLayout mAllOrderLLayout;//全部的layout
        TextView mZiText;//子币名称
        TextView mMuText;//母币名称
        TextView mtv_tuihuo;//退货按钮
    }
}

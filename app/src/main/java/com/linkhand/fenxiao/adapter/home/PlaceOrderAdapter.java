package com.linkhand.fenxiao.adapter.home;

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
import com.linkhand.fenxiao.feng.home.OrderInterfaceNewFeng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/3/15.
 */

public class PlaceOrderAdapter extends BaseAdapter {
    Context context;
    List<OrderInterfaceNewFeng.InfoBean> beanList;
    String speciContent = "";//显示规格
    //    AllConfirmOrderInfo mInfo;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public PlaceOrderAdapter(Context context, List<OrderInterfaceNewFeng.InfoBean> beanList) {
        this.context = context;
        this.beanList = beanList;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        PlaceOrderAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new PlaceOrderAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.place_order_item, null, false);
            holder.mMoney = (TextView) convertView.findViewById(R.id.order_merchandisermb_id);//单个金额
            holder.mGoodsName = (TextView) convertView.findViewById(R.id.order_name_id2);//商品名
            holder.mGoodsZi = (TextView) convertView.findViewById(R.id.order_merchandisermb_zi);
            holder.mGoodsImg = (ImageView) convertView.findViewById(R.id.order_merchandise_iv);//商品图
            holder.mGoodsnums = (TextView) convertView.findViewById(R.id.number_id);//商品数量
            holder.mSpec = (TextView) convertView.findViewById(R.id.order_item_model_id);//规格
            holder.mtv_money = (TextView) convertView.findViewById(R.id.order_item_model_what);
            holder.mtv_son_money= (TextView) convertView.findViewById(R.id.order_son_what);
            convertView.setTag(holder);
        } else {
            holder = (PlaceOrderAdapter.ViewHolder) convertView.getTag();
        }
        if (this.beanList != null) {
            speciContent = "";
            OrderInterfaceNewFeng.InfoBean child = beanList.get(position);
            String good_name = child.getOrder_good_name();//商品名
            String thumb = child.getGood_img();//商品图片
            String order_num = child.getOrder_good_num();//订单数量
            String onemater = child.getOrder_mater_money();//单价母币
            String oneson = child.getOrder_son_money();//单价子币
//            int allmater = child.get;//总价母币
//            int allson = child.getAllson();//总价子币
            String good_id = child.getGood_id();//商品id
            String order_speci_vals = child.getOrder_speci_vals();

//            List<OrderInterfaceNewFeng.InfoBean.SpeciBean> speciBeenList = child.getSpeci();
//            Log.e("yh", speciBeenList.size() + "------------------------" + beanList.size());
//            if (speciBeenList != null) {
//                String name = "";
//                String value = "";
//                for (int i = 0; i < speciBeenList.size(); i++) {
//                    name = speciBeenList.get(i).getSpeci_name();
//                    value = speciBeenList.get(i).getGsp_value();
//                    speciContent = speciContent + name + ":" + value + "  ";
//                    Log.e("yh", speciContent + "------------------------");
//                }
//            }
            preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            editor = preferences.edit();
            for (OrderInterfaceNewFeng.InfoBean.SpeciBean bean : child.getSpeci()) {
                speciContent = speciContent + ";" + bean.getSpeci_name() +":"+ bean.getGsp_value();
            }
            //获取子母币名称
            String Mater_name = preferences.getString("Mater_name", "母币");//母币名称
            String Son_name = preferences.getString("Son_name", "子币");//子币名称
            if (order_speci_vals!=null&&order_speci_vals.equals("不知道")) {
                holder.mGoodsZi.setText(child.getOrder_son_money() + "元");
                holder.mMoney.setVisibility(View.INVISIBLE);
                holder.mtv_money.setVisibility(View.INVISIBLE);
                holder.mtv_son_money.setVisibility(View.INVISIBLE);
            } else {
                holder.mMoney.setText(onemater + Mater_name);//单个金额
                holder.mGoodsZi.setText(oneson + Son_name);
                holder.mMoney.setVisibility(View.VISIBLE);
                holder.mtv_money.setVisibility(View.VISIBLE);
                holder.mtv_son_money.setVisibility(View.VISIBLE);

            }
            holder.mGoodsName.setText(good_name);//商品名
            holder.mGoodsnums.setText("x" + order_num);//商品数量
            holder.mSpec.setText(speciContent.substring(1, speciContent.length()));//规格
            if (thumb.equals("") | thumb.equals("null")) {
            } else {
                thumb = C.TU + thumb;
                RequestOptions requestOptions=new RequestOptions();
                requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
                Glide.with(context).load(thumb).apply(requestOptions).into(holder.mGoodsImg);
            }
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("position", position);
            map.put("good_id", good_id);
            list.add(map);
//            mInfo.onItemClicks(holder.mPlusSign, holder.mSubtract, holder.mGoodsnums, list);//加号，减号，选中数，数据集合

        }
        return convertView;
    }

//    public void setOnItemClicks(ConfirmOrderActivity mInfo) {
//        this.mInfo = mInfo;
//    }

    private class ViewHolder {
        TextView mMoney;//单个金额
        TextView mGoodsName;//商品名
        TextView mSpec;//规格
        ImageView mGoodsImg;//商品图
        TextView mGoodsnums;//商品数量
        TextView mGoodsZi;
        TextView mtv_money;
        TextView mtv_son_money;
//        Button mSubtract;//减号
//        Button mPlusSign;//加号

    }
}
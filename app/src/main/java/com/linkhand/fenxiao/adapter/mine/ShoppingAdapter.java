package com.linkhand.fenxiao.adapter.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.mine.ShoppingCartListFeng;
import com.linkhand.fenxiao.fragment.shopping.ShoppingFragment;
import com.linkhand.fenxiao.info.callback.ShoppingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/2/8.
 */

public class ShoppingAdapter extends BaseAdapter {
    Context context;
    List<ShoppingCartListFeng.InfoBean> beanList;
    ShoppingInfo mShoppingInfo;
    List<Map<String, Object>> list1;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public ShoppingAdapter(Context context, List<ShoppingCartListFeng.InfoBean> beanList, List<Map<String, Object>> list1) {
        this.context = context;
        this.beanList = beanList;
        this.list1 = list1;
    }

    public void setData(List<ShoppingCartListFeng.InfoBean> beanList) {
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

        ShoppingAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new ShoppingAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.shopping_item_layout, parent, false);
            holder.mTu = (ImageView) convertView.findViewById(R.id.shopping_item_tuid);
            holder.mTitle = (TextView) convertView.findViewById(R.id.shopping_item_title);
            holder.mMuRMB = (TextView) convertView.findViewById(R.id.shopping_item_murmb);
            holder.mZiRMB = (TextView) convertView.findViewById(R.id.shopping_item_zirmb);
            holder.mMuText = (TextView) convertView.findViewById(R.id.shopping_item_mutext);
            holder.mZiText = (TextView) convertView.findViewById(R.id.shopping_item_zitext);
            holder.mRLayout = (LinearLayout) convertView.findViewById(R.id.shopping_item_rlayout_id);
            holder.mDelete = (ImageView) convertView.findViewById(R.id.shopping_item_delete);
            holder.mSelected = (ImageView) convertView.findViewById(R.id.shopping_selected_id);
            holder.mType = (TextView) convertView.findViewById(R.id.shopping_item_type);
            holder.mNumber = (TextView) convertView.findViewById(R.id.shopping_item_number);
            holder.mSubtract = (Button) convertView.findViewById(R.id.detail_addition_id);//减
            holder.mAdd = (Button) convertView.findViewById(R.id.detail_subtraction_id);//加
            holder.mChangeNumber = (TextView) convertView.findViewById(R.id.detail_number_id);//数量
            holder.mfailure = (ImageView) convertView.findViewById(R.id.failure_image_id);//失效
            holder.mll_all = (LinearLayout) convertView.findViewById(R.id.shopping_ll_all);//失效
            convertView.setTag(holder);
        } else {
            holder = (ShoppingAdapter.ViewHolder) convertView.getTag();
        }
        if (this.beanList != null) {
            preferences =context.getSharedPreferences("user", Context.MODE_PRIVATE);
            editor = preferences.edit();
            //获取子母币名称
            String Mater_name = preferences.getString("Mater_name", "母币");//母币名称
            String Son_name = preferences.getString("Son_name", "子币");//子币名称
            holder.mZiText.setText(Son_name);
            holder.mMuText.setText(Mater_name);

            ShoppingCartListFeng.InfoBean childMap = beanList.get(position);
            String cart_id = childMap.getCart_id();//购物车id
            String thumb = childMap.getImg_url();//(商品图片)
            String title = childMap.getGood_name();//(商品名称)
            String zirmb = childMap.getCart_son();//(子币价格)
            String murmb = childMap.getCart_mater();//(母币价格)
            String cart_num = childMap.getCart_num();//数量
            String good_id = childMap.getGood_id();//商品id
            int good_state = childMap.getGood_state();//是否失效  1未失效  0已失效
            List<ShoppingCartListFeng.InfoBean.SpeciBean> speciBeen = childMap.getSpeci();//规格

            if (speciBeen != null & speciBeen.size() != 0) {
                holder.mType.setText("");
                for (int i = 0; i < speciBeen.size(); i++) {
                    String name = speciBeen.get(i).getSpeci_name();
                    String value = speciBeen.get(i).getGsp_value();
                    String str = holder.mType.getText() + "";
                    if (str.equals("")) {
                        holder.mType.setText(name + ":" + value);
                        Log.e("yh1", holder.mType.getText().toString());
                    } else {
                        holder.mType.setText(str + "  " + name + ":" + value);
                        Log.e("yh2", holder.mType.getText().toString());
                    }
                }
            }
            Log.e("yh3----", holder.mType.getText().toString());
            holder.mNumber.setText("x" + cart_num);
            holder.mChangeNumber.setText(cart_num);

            String isSelected = "2";
            if (good_state == 0) {//是否失效  1未失效  0已失效
                holder.mfailure.setVisibility(View.VISIBLE);
                isSelected="2";
            }else{
                holder.mfailure.setVisibility(View.GONE);
                isSelected = (String) list1.get(position).get("is_selected");//(是否选中)//1选中   2未选中

            }
            if(isSelected!=null){
                if (isSelected.equals("1")) {
//                holder.mSelected.setChecked(true);
                    holder.mSelected.setImageResource(R.drawable.ovalone);
                } else if (isSelected.equals("2")) {
//                holder.mSelected.setChecked(false);
                    holder.mSelected.setImageResource(R.drawable.ovaltwo);
                }
            }

            holder.mTitle.setText(title);
            holder.mMuRMB.setText("¥" + murmb);
            holder.mZiRMB.setText("¥" + zirmb);


            if (thumb.equals("") | thumb.equals("null")) {

            } else {
                thumb = C.TU + thumb;
                RequestOptions requestOptions=new RequestOptions();
                requestOptions.placeholder(R.drawable.position_img).error(R.drawable.position_img);
                Glide.with(context)
                        .load(thumb)
                        .apply(requestOptions)
                        .into(holder.mTu);
            }
            List<Map<String, Object>> mList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("position", position);
            map.put("cart_id", cart_id);//购物车id
            map.put("good_id", good_id);//商品id
            map.put("good_state", good_state);//是否失效  1未失效  0已失效

            mList.add(map);
            mShoppingInfo.onItemClicks(holder.mRLayout, holder.mSubtract, holder.mAdd, holder.mChangeNumber, holder.mSelected, holder.mDelete, mList,holder.mll_all);

        }
        return convertView;
    }

//    public void setOnClicks(ShoppingActivity mShoppingInfo) {//我的购物车
//        this.mShoppingInfo = mShoppingInfo;
//    }

    public void setOnFragmentClicks(ShoppingFragment mShoppingInfo) {
        this.mShoppingInfo = mShoppingInfo;
    }

    private class ViewHolder {
        ImageView mTu;//图片
        TextView mTitle;//标题
        TextView mZiRMB;//子币
        TextView mZiText;//子币text
        TextView mMuRMB;//母币
        TextView mMuText;//母币text
        LinearLayout mRLayout;//总
        ImageView mDelete;//删除
        ImageView mSelected;//是否选中
        TextView mType;//类型
        TextView mNumber;//数量

        Button mSubtract;//减
        Button mAdd;//加
        TextView mChangeNumber;//数量
        ImageView mfailure;//失效
        LinearLayout mll_all;

    }
}
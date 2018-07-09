package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.AddressActivity;
import com.linkhand.fenxiao.feng.home.SortModel;
import com.linkhand.fenxiao.info.callback.AddressInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2017/12/18.
 */

public class AddressAdapter extends BaseAdapter {
    Context context;
    List<SortModel> mList;
    AddressInfo mAddressInfo;


    public AddressAdapter(Context context, List<SortModel> mList) {
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


        AddressAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new AddressAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.address_item, null, false);
            holder.mName = (TextView) convertView.findViewById(R.id.order_username_item_id);//名
            holder.mAddress = (TextView) convertView.findViewById(R.id.address_item_id);//地址
            holder.mNumber = (TextView) convertView.findViewById(R.id.address_item_number_id);//号码
            holder.mCheckTu = (TextView) convertView.findViewById(R.id.address_item_tu);//对勾图片
            holder.mRoundTu = (ImageView) convertView.findViewById(R.id.address_xiao_tu);//小圆图片
            holder.mContent = (TextView) convertView.findViewById(R.id.adress_content_id);//设置默认（字）
            holder.mTextOne = (TextView) convertView.findViewById(R.id.address_tv_id);//编辑
            holder.mTextTwo = (TextView) convertView.findViewById(R.id.address_tv_id2);//删除
            holder.mLLayout = (LinearLayout) convertView.findViewById(R.id.address_item_llayout);//设置默认总layout
            holder.mRLayout = (RelativeLayout) convertView.findViewById(R.id.order_address_item_id);//地址总layout
            convertView.setTag(holder);
        } else {
            holder = (AddressAdapter.ViewHolder) convertView.getTag();
        }
        if (this.mList != null) {

            SortModel mSortModel = mList.get(position);
            String site_city1 = mSortModel.getSite_city1();//省
            String site_city2 = mSortModel.getSite_city2();//市
            String site_city3 = mSortModel.getSite_city3();//区
            String site_detail = mSortModel.getSite_detail();//详细地址
            String site_name = mSortModel.getSite_name();//收货人
            String site_tel = mSortModel.getSite_tel();//收货人电话
            String site_id = mSortModel.getSite_id();//地址id
            String site_is_del = mSortModel.getSite_is_del();//是否已删除  0是   1否
            String site_is_first = mSortModel.getSite_is_first();//是否默认  0.否  1.是
            String selects = mSortModel.getSelects();//1 对勾  2选择


            if (site_is_del.equals("0")) {//是否已删除  0是   1否

            } else if(site_is_del.equals("1")){
                holder.mName.setText(site_name);
                holder.mNumber.setText(site_tel);
                holder.mAddress.setText(site_city1+" "+site_city2+site_city3+site_detail);
                if (selects.equals("1")) {//1 对勾  2选择
                    holder.mCheckTu.setText("");
                    holder.mCheckTu.setBackgroundResource(R.drawable.choose_yes);

                } else if (selects.equals("2")) {//1 对勾  2选择
                    holder.mCheckTu.setText("选择");
                    holder.mCheckTu.setBackgroundColor(Color.WHITE);
                }
                if (site_is_first.equals("1")) {//1 默认 0不默认(R.color.colorred);
                    holder.mRoundTu.setImageResource(R.drawable.marquee_yes);
                    holder.mContent.setTextColor(Color.RED);
                } else if (site_is_first.equals("0")) {//1 默认 0不默认
                    holder.mRoundTu.setImageResource(R.drawable.marquee);
//                holder.mContent.getResources().getColor(R.color.colorgraynessd);
                    holder.mContent.setTextColor(Color.GRAY);
                }


                List<Map<String, Object>> mLists = new ArrayList<>();
                Map<String, Object> maps = new HashMap<>();
                maps.put("content", holder.mContent);//设置默认（字）
                maps.put("position", position);
                maps.put("site_id", site_id);
                mLists.add(maps);

//地址总layout   设置默认总layout   编辑   删除   设置默认（字）  //对勾图片  小圆图片
                mAddressInfo.onItemClicks(holder.mRLayout, holder.mLLayout, holder.mTextOne, holder.mTextTwo, holder.mContent, holder.mCheckTu, holder.mRoundTu, mLists);

            }

        }
        return convertView;
    }

    public void setOnItemClicks(AddressActivity mAddressInfo) {
        this.mAddressInfo = mAddressInfo;
    }

    public void setData(List<SortModel> mList){
        this.mList = mList;
    }

    private class ViewHolder {
        TextView mCheckTu;//对勾图片
        ImageView mRoundTu;//小圆图片
        TextView mContent;//设置默认（字）
        TextView mName;//名
        TextView mAddress;//地址
        TextView mNumber;//号码
        TextView mTextOne;//编辑
        TextView mTextTwo;//删除
        LinearLayout mLLayout;//设置默认
        RelativeLayout mRLayout;//地址总layout
    }
}

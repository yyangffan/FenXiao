package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.DetailPageActivity;
import com.linkhand.fenxiao.feng.home.GoodsDetailsFeng;
import com.linkhand.fenxiao.feng.home.MyGoodsFeng;
import com.linkhand.fenxiao.info.callback.LeftLVIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2017/12/21.
 */

public class DetailPageAdapter extends BaseAdapter {
    Context context;
    GoodsDetailsFeng.InfoBean.SpeciBean results;
    LeftLVIn mLeftLVIn;
    int place;
    List<MyGoodsFeng> myList;

    public DetailPageAdapter(Context context, GoodsDetailsFeng.InfoBean.SpeciBean results, LeftLVIn mLeftLVIn, int place,List<MyGoodsFeng> myList) {
        this.context = context;
        this.results = results;
        this.mLeftLVIn = mLeftLVIn;
        this.place = place;
        this.myList = myList;
    }

    @Override
    public int getCount() {
        return results.getSpeci_vals().size();
    }

    @Override
    public Object getItem(int position) {
        return results.getSpeci_vals().get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Category c = (Category) getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.detailpage_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.detailpage_tv_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (results != null) {

            List<GoodsDetailsFeng.InfoBean.SpeciBean.SpeciValsBean> SpeciValsBeanList = results.getSpeci_vals();
            String gsp_id = SpeciValsBeanList.get(position).getGsp_id();//规格id
            String gsp_value = SpeciValsBeanList.get(position).getGsp_value();//规格名
            String img = SpeciValsBeanList.get(position).getGsp_img()+"";//图片
//            Log.e("yh", "gsp_id--" + gsp_id + "--gsp_value--" + gsp_value);
            String mygsp_id =myList.get(place).getMyid();
            if(gsp_id.equals(mygsp_id)){
                holder.tv.setBackgroundResource(R.drawable.btn_bg_rounded_greytwo);
                holder.tv.setTextColor(context.getResources().getColor(R.color.white));
            }else{
                holder.tv.setBackgroundResource(R.drawable.btn_bg_rounded_grey);
                holder.tv.setTextColor(context.getResources().getColor(R.color.black));
            }
            holder.tv.setText(gsp_value);



            List<Map<String, Object>> mList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("gsp_id", gsp_id + "");//规格id
            map.put("gsp_value", gsp_value + "");//规格名
            map.put("place", place);//类型位置
            map.put("position", position);//规格位置
            map.put("img", img);//规格位置
            mList.add(map);
            mLeftLVIn.onGVItemClick(holder.tv, mList);

        }


        return convertView;
    }

    public void setOnItems(DetailPageActivity mLeftLVIn) {
        this.mLeftLVIn = mLeftLVIn;
    }

    class ViewHolder {
        TextView tv;
    }


}

package com.linkhand.fenxiao.guige;

/**
 * Created by 杨帆 on 2018/7/26.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.feng.home.MyGoodsFeng;
import com.linkhand.fenxiao.feng.home.VipGoodsDetailsFeng;
import com.linkhand.fenxiao.info.callback.LeftLVIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VipNewAdapter extends RecyclerView.Adapter<VipNewAdapter.ViewHolder>{
    Context context;
    VipGoodsDetailsFeng.InfoBean.SpeciBean results;
    LeftLVIn mLeftLVIn;
    int place;
    List<MyGoodsFeng> myList;
    private LayoutInflater mInflater;

    public VipNewAdapter(Context context, VipGoodsDetailsFeng.InfoBean.SpeciBean results, LeftLVIn mLeftLVIn, int place, List<MyGoodsFeng> myList) {
        this.context = context;
        this.results = results;
        this.mLeftLVIn = mLeftLVIn;
        this.place = place;
        this.myList = myList;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.flow_item,parent,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (results != null) {
            List<VipGoodsDetailsFeng.InfoBean.SpeciBean.SpeciValsBean> SpeciValsBeanList = results.getSpeci_vals();
            String gsp_id = SpeciValsBeanList.get(position).getVsp_id();//规格id
            String gsp_value = SpeciValsBeanList.get(position).getVsp_value();//规格名
            String img = SpeciValsBeanList.get(position).getVsp_img()+"";//图片
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
    }

    @Override
    public int getItemCount() {
        return results.getSpeci_vals().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.flow_text);
        }
    }
}


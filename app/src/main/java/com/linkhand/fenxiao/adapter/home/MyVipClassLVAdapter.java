package com.linkhand.fenxiao.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.NestedRecyclerView;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.homepage.home.VIPDetailPageActivity;
import com.linkhand.fenxiao.feng.home.MyGoodsFeng;
import com.linkhand.fenxiao.feng.home.VipGoodsDetailsFeng;
import com.linkhand.fenxiao.guige.VipNewAdapter;
import com.linkhand.fenxiao.info.callback.LeftLVIn;
import com.linkhand.fenxiao.utils.NoScrollGridView;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/27.
 */

public class MyVipClassLVAdapter extends BaseAdapter {
    private Context context;
    private List<VipGoodsDetailsFeng.InfoBean.SpeciBean> BeanList;
    private ViewHolder holder = null;
    LeftLVIn mLeftLVIn;
    List<MyGoodsFeng> myList;

    public MyVipClassLVAdapter(Context context, List<VipGoodsDetailsFeng.InfoBean.SpeciBean> BeanList, List<MyGoodsFeng> myList) {
        this.context = context;
        this.BeanList = BeanList;
        this.myList = myList;
    }

    @Override
    public int getCount() {
        return BeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return BeanList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Categoryright c = (Categoryright) getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.class_lv_item, null);

            holder.mTextView = (TextView) convertView.findViewById(R.id.right_title_id);//小标题
            holder.mGridView = (NoScrollGridView) convertView.findViewById(R.id.right_gv_id);
            holder.mNestedRecyclerView = (NestedRecyclerView) convertView.findViewById(R.id.des);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.BeanList != null) {
            if (holder.mGridView != null) {
                VipGoodsDetailsFeng.InfoBean.SpeciBean speciBean = BeanList.get(position);
                String cat_name = speciBean.getSpeci_name();//类型
                holder.mTextView.setText(cat_name);
                VipDetailPageAdapter gridViewAdapter = new VipDetailPageAdapter(context, speciBean, mLeftLVIn,position,myList);
                holder.mGridView.setAdapter(gridViewAdapter);

                FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
                holder.mNestedRecyclerView.setLayoutManager(flowLayoutManager);
                holder.mNestedRecyclerView.setAdapter(new VipNewAdapter(context, speciBean, mLeftLVIn, position, myList));
            }
        }


        return convertView;
    }

    public void setOnItems(VIPDetailPageActivity mLeftLVIn) {
        this.mLeftLVIn = mLeftLVIn;
    }

//    public void setOnItemss(InDetailsActivity mLeftLVIn) {
//        this.mLeftLVIn = mLeftLVIn;
//    }


    class ViewHolder {
        TextView mTextView;//小标题
        NoScrollGridView mGridView;
        NestedRecyclerView mNestedRecyclerView;
    }

}

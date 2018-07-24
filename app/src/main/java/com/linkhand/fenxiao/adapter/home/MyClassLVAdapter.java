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
import com.linkhand.fenxiao.utils.NoScrollGridView;

import java.util.List;

/**
 * Created by 11860_000 on 2018/2/27.
 */

public class MyClassLVAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsDetailsFeng.InfoBean.SpeciBean> BeanList;
    LeftLVIn mLeftLVIn;
    List<MyGoodsFeng> myList;

    public MyClassLVAdapter(Context context, List<GoodsDetailsFeng.InfoBean.SpeciBean> BeanList,List<MyGoodsFeng> myList) {
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
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.BeanList != null) {
            if (holder.mGridView != null) {
                GoodsDetailsFeng.InfoBean.SpeciBean speciBean = BeanList.get(position);
                String cat_name = speciBean.getSpeci_name();//类型
                holder.mTextView.setText(cat_name);
                DetailPageAdapter gridViewAdapter = new DetailPageAdapter(context, speciBean, mLeftLVIn,position,myList);
                holder.mGridView.setAdapter(gridViewAdapter);
            }
        }

        convertView.setTag(holder);
        return convertView;
    }

    public void setOnItems(DetailPageActivity mLeftLVIn) {
        this.mLeftLVIn = mLeftLVIn;
    }

//    public void setOnItemss(InDetailsActivity mLeftLVIn) {
//        this.mLeftLVIn = mLeftLVIn;
//    }


    class ViewHolder {
        TextView mTextView;//小标题
        NoScrollGridView mGridView;
    }

}

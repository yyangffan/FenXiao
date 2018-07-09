package com.linkhand.fenxiao.adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.fenxiao.C;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.activity.mine.ThreeFragment;
import com.linkhand.fenxiao.feng.mine.MyTeamFeng;
import com.linkhand.fenxiao.info.callback.MineMembersInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2017/12/19.
 */

public class ThreeDeckFragmentAdapter extends BaseAdapter {
    Context context;
    MineMembersInfo mMineMembersInfo;
    List<MyTeamFeng.InfoBean._$2Bean> beanListThree;

    public ThreeDeckFragmentAdapter(Context context, List<MyTeamFeng.InfoBean._$2Bean> beanListThree) {

        this.context = context;
        this.beanListThree = beanListThree;

    }

    @Override
    public int getCount() {
        return beanListThree.size();
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

        ThreeDeckFragmentAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new ThreeDeckFragmentAdapter.ViewHolder();
            convertView = LayoutInflater.from
                    (this.context).inflate(R.layout.onedeck_item, null, false);
            holder.mTu = (ImageView) convertView.findViewById(R.id.onedeck_roundImageView_id);//图片
            holder.mName = (TextView) convertView.findViewById(R.id.onedeck_name_id);//被邀请的人
            holder.mReferrer = (TextView) convertView.findViewById(R.id.onedeck_referrer_id);//推荐人
            holder.mRechargeLayout = (LinearLayout) convertView.findViewById(R.id.mine_recharge_llayout_id);//已充值显示的
            holder.mRegistrationTv = (TextView) convertView.findViewById(R.id.mine_registration_tv_id);//已注册未充值显示的
            holder.mRLayout = (RelativeLayout) convertView.findViewById(R.id.deck_all_rlayout_id);//总布局
            holder.mIvOne= (ImageView) convertView.findViewById(R.id.deck_iv_one);
            holder.mIvTwo= (ImageView) convertView.findViewById(R.id.deck_iv_two);
            holder.mIvThree= (ImageView) convertView.findViewById(R.id.deck_iv_three);
            convertView.setTag(holder);
        } else {
            holder = (ThreeDeckFragmentAdapter.ViewHolder) convertView.getTag();
        }
        if (this.beanListThree != null) {
            MyTeamFeng.InfoBean._$2Bean childOne = beanListThree.get(position);
            String user_id = childOne.getUser_id();//会员id
            String thumb = childOne.getUser_head_img();//头像
            String user_name = childOne.getUser_name();//用户名
            String top_name = childOne.getTop_user_name();//上级账号名
            String user_referr1 = childOne.getReferr1_head() + "";
            String user_referr2 = childOne.getReferr2_head() + "";
            String user_referr3 = childOne.getReferr3_head() + "";

            holder.mName.setText(user_name);
            holder.mReferrer.setText(top_name);
            holder.mRechargeLayout.setVisibility(View.VISIBLE);//已充值显示的
            holder.mRegistrationTv.setVisibility(View.GONE);//已注册未充值显示的

            if (thumb.equals("") | thumb.equals("null")) {
            } else {
                thumb = C.TU + thumb;
                Glide.with(context)
                        .load(thumb)
                        .into(holder.mTu);
            }

            if (user_referr1.equals("")) {
                holder.mIvOne.setImageResource(R.drawable.wu);
            } else {
                Glide.with(context).load(C.TU + user_referr1).into(holder.mIvOne);
            }
            if (user_referr2.equals("")) {
                holder.mIvTwo.setImageResource(R.drawable.wu);
            } else {
                Glide.with(context).load(C.TU + user_referr2).into(holder.mIvTwo);
            }
            if (user_referr3.equals("")) {
                holder.mIvThree.setImageResource(R.drawable.wu);
            } else {
                Glide.with(context).load(C.TU + user_referr3).into(holder.mIvThree);
            }

            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> maps = new HashMap<>();
            maps.put("user_id", user_id);//会员id
            list.add(maps);

            mMineMembersInfo.onItemClicks(holder.mRLayout, list);
        }
        return convertView;
    }

    public void setOnItemClicks(ThreeFragment mMineMembersInfo) {
        this.mMineMembersInfo = mMineMembersInfo;
    }


    private class ViewHolder {
        ImageView mTu;//图片
        TextView mName;//被邀请的人
        TextView mReferrer;//推荐人

        LinearLayout mRechargeLayout;//已充值显示的
        TextView mRegistrationTv;//已注册未充值显示的
        RelativeLayout mRLayout;//总布局
        ImageView mIvOne;
        ImageView mIvTwo;
        ImageView mIvThree;
    }
}
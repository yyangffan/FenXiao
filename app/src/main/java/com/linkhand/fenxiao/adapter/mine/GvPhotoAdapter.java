package com.linkhand.fenxiao.adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.utils.MultipleImages.ImageTool;

import java.util.List;

/**
 * Created by user on 2017/6/19.
 */

public class GvPhotoAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private LayoutInflater inflater;
    private String IMG_ADD_TAG = "a";
    private OnDeleteListener listener;

    public GvPhotoAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_add_photo, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.main_gridView_item_photo);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.main_gridView_item_cb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String s = list.get(position);
        if (!s.equals(IMG_ADD_TAG) && !s.equals("path")) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.imageView.setImageBitmap(ImageTool.createImageThumbnail(s));
        } else {
            holder.checkBox.setVisibility(View.GONE);
            holder.imageView.setImageResource(R.drawable.addtu);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelete(position);
//                list.remove(position);
//                uriList.remove(position);
//                notifyDataSetChanged();
////                refreshAdapter();
            }
        });
        return convertView;
    }

    public interface OnDeleteListener{
        void onDelete(int position);
    }
    public void setOnDeletListener(OnDeleteListener listener){
        this.listener = listener;
    }

    private class ViewHolder {
        ImageView imageView;
        CheckBox checkBox;
    }
}

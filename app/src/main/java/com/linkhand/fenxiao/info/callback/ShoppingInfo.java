package com.linkhand.fenxiao.info.callback;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/2/8.
 */

public interface ShoppingInfo {
    void onItemClicks(LinearLayout mLinearLayout, Button jian, Button jia, TextView num, View mSelected, ImageView mImageView, List<Map<String, Object>> list,View ll);
}

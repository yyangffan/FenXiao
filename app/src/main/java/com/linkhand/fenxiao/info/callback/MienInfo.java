package com.linkhand.fenxiao.info.callback;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/4/12.
 */

public interface MienInfo {
    void onItemClicks(RelativeLayout mRelativeLayout, ImageView mLike, List<Map<String, Object>> list);
}

package com.linkhand.fenxiao.info.callback;

import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/2/27.
 */

public interface CollectionInfo {
    void onItemDetailsClicks(RelativeLayout mRelativeLayout, TextView mTextView, List<Map<String,Object>> list);
}

package com.linkhand.fenxiao.info.callback;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/1/13.
 */

public interface DetailsInfo {
    void onItemDetailsClicks(LinearLayout mLinearLayout, TextView mTextView, List<Map<String,Object>> list);
}

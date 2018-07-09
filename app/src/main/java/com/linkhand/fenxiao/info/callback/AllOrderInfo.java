package com.linkhand.fenxiao.info.callback;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/2/26.
 */

public interface AllOrderInfo {
    void onItemClicks(LinearLayout mLinearLayout, TextView mTextView, List<Map<String, Object>> list);
}

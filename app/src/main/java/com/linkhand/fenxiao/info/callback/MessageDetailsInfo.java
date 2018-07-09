package com.linkhand.fenxiao.info.callback;

import android.widget.LinearLayout;

import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/3/12.
 */

public interface MessageDetailsInfo {
    void onItemClicks(LinearLayout mLinearLayout, List<Map<String, Object>> list);
}

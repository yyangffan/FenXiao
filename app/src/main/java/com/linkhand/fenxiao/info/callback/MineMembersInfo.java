package com.linkhand.fenxiao.info.callback;

import android.widget.RelativeLayout;

import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2017/12/20.
 */

public interface MineMembersInfo {
    void onItemClicks(RelativeLayout mRelativeLayout, List<Map<String, Object>> list);
}

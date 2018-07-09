package com.linkhand.fenxiao.info.callback;

import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/3/15.
 */

public interface AllConfirmOrderInfo {
    void onItemClicks(Button mAdd, Button mSubtract, TextView mNumber, List<Map<String, Object>> list);
}

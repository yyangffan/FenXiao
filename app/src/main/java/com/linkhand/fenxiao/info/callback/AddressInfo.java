package com.linkhand.fenxiao.info.callback;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2017/12/16.
 */

public interface AddressInfo {
    void onItemClicks(RelativeLayout mRelativeLayout, LinearLayout mLinearLayout, TextView edit, TextView delete, TextView content, TextView checkTu,ImageView roundTu, List<Map<String, Object>> list);
}

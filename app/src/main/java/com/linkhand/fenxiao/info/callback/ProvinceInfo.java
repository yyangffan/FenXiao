package com.linkhand.fenxiao.info.callback;

import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by 11860_000 on 2018/2/8.
 */

public interface ProvinceInfo {
    void onProvinceItemClicks(TextView mContent, List<Map<String, Object>> list);
}

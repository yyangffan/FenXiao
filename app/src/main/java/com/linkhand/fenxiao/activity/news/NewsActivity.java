package com.linkhand.fenxiao.activity.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.NewsAdapter;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.home.MessageResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends BaseActicity implements View.OnClickListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mUserId;//个人id
    LinearLayout mReturn;//返回
    TextView mtvDeleteAll;//清空消息
    SlideAndDragListView mListView;
    NewsAdapter mAdapter;
    private List<MessageResponse.InfoBean> mList;
    SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
        onClicks();
        onMessage();
    }

    public void init() {
        mReturn = (LinearLayout) findViewById(R.id.fenxiao_return_id4);
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smartRefresh);
        mtvDeleteAll = (TextView) findViewById(R.id.news_delete_all);
        mListView = (SlideAndDragListView) findViewById(R.id.news_listview_id);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mUserId = preferences.getString("user_id", "");

        Menu menu = new Menu(false, 0);//第1个参数表示滑动 item 是否能滑的过头 true 表示过头，false 表示不过头
        menu.addItem(new MenuItem.Builder().setWidth(200)
                .setBackground(new ColorDrawable(Color.RED))
                .setText("删除")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize(20)
                .build());
        mListView.setMenu(menu);
        mListView.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
            @Override
            public int onMenuItemClick(View v, final int itemPosition, int buttonPosition, int direction) {
                switch (direction) {
                    case MenuItem.DIRECTION_RIGHT:
                        deleteWhat(mList.get(itemPosition).getTrade_id() + "","0");
                        break;
                    default:
                        return Menu.ITEM_NOTHING;
                }
                return Menu.ITEM_NOTHING;
            }
        });
        mSmartRefreshLayout.setEnableLoadmore(false);
        mSmartRefreshLayout.setEnableRefresh(false);

    }

    public void onClicks() {
        mReturn.setOnClickListener(this);//返回
        mtvDeleteAll.setOnClickListener(this);//返回
        mList = new ArrayList<>();
        mAdapter = new NewsAdapter(NewsActivity.this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                intent.putExtra("trade_id", mList.get(position).getTrade_id() + "");
                startActivity(intent);
            }
        });
//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                new ShowRemindDialog().showRemind(NewsActivity.this, "确定", "取消", "", "删除消息?", 0, new ShowRemindDialog.OnTvClickListener() {
//                    @Override
//                    public void OnSureClickListener() {
//                        deleteOneMsg(mList.get(position).getTrade_id() + "");
//                    }
//                });
//                return true;
//            }
//        });

    }

    /**
     *
     * @param trade_id 删除单条时传否则不传
     * @param what  0为删除单条 1为删除所有
     */
    public void deleteWhat(String trade_id,String what) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("trade_id", trade_id);
        map.put("all_del", what);

        Call<HttpResponse> call = service.getTradeDel(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    onMessage();
                } else {
                    Toast.makeText(NewsActivity.this, httpResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Toast.makeText(NewsActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<MessageResponse> call = service.getMessageList(map);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                MessageResponse messageResponse = response.body();
                mList.clear();
                if (messageResponse.getCode() == 100) {
                    mList.addAll(messageResponse.getInfo());
                } else {
                    Toast.makeText(NewsActivity.this, messageResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(NewsActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fenxiao_return_id4://返回
                this.finish();
            case R.id.news_delete_all://清空消息
                new ShowRemindDialog().showRemind(this, "确定", "取消", "", "清空消息？", R.drawable.delete_img, new ShowRemindDialog.OnTvClickListener() {
                    @Override
                    public void OnSureClickListener() {
                        deleteWhat("","1");
                    }
                });
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onMessage();
    }
}

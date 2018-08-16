package com.linkhand.fenxiao.activity.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.home.NewsAdapter;
import com.linkhand.fenxiao.dialog.ShowRemindDialog;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.home.MessageResponse;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
    ImageView mtvDeleteAll;//清空消息
    SlideAndDragListView mListView;
    NewsAdapter mAdapter;
    private List<MessageResponse.InfoBean> mList;
    SmartRefreshLayout mSmartRefreshLayout;
    private int page = 0;

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
        mtvDeleteAll = (ImageView) findViewById(R.id.news_delete_all);
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
                        deleteWhat(mList.get(itemPosition).getTrade_id() + "", "0");
                        break;
                    default:
                        return Menu.ITEM_NOTHING;
                }
                return Menu.ITEM_NOTHING;
            }
        });
//        mSmartRefreshLayout.setEnableLoadmore(false);
//        mSmartRefreshLayout.setEnableRefresh(false);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                onMessage();

            }
        });
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++page;
                onMessage();
            }
        });

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
    }

    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("pag", page);
        Call<MessageResponse> call = service.getMessageList(map);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadmore();
                MessageResponse messageResponse = response.body();
                if (messageResponse.getCode() == 100) {
                    if (page == 0) {
                        mList.clear();
                        mList.addAll(messageResponse.getInfo());
                    } else {
                        for (MessageResponse.InfoBean infoBean : messageResponse.getInfo()) {
                            mList.add(infoBean);
                        }
                    }
                } else {
                    if (page == 0) {
                        mList.clear();
                    }
                    Toast.makeText(NewsActivity.this, messageResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadmore();
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
                onMenuClick();
                break;
        }
    }

    /*右上方的多选按钮*/
    public void onMenuClick() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.message_content, null);
        final PopupWindow popW = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        TextView tv_read = (TextView) contentView.findViewById(R.id.menu_allRead);
        TextView tv_delete = (TextView) contentView.findViewById(R.id.menu_allDelete);
        if (popW.isShowing()) {
            popW.dismiss();
        } else {
            popW.showAsDropDown(mtvDeleteAll, Gravity.BOTTOM, 0, mtvDeleteAll.getLayoutParams().width);
        }
        tv_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShowRemindDialog().showRemind(NewsActivity.this, "确定", "取消", "", "全部已读？", R.drawable.prompt, new ShowRemindDialog.OnTvClickListener() {
                    @Override
                    public void OnSureClickListener() {
                        toTellRead();
                    }
                });
                popW.dismiss();
            }
        });
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShowRemindDialog().showRemind(NewsActivity.this, "确定", "取消", "", "清空消息？", R.drawable.delete_img, new ShowRemindDialog.OnTvClickListener() {
                    @Override
                    public void OnSureClickListener() {
                        deleteWhat("", "1");
                    }
                });
                popW.dismiss();
            }
        });
    }

    /**
     * @param trade_id 删除单条时传否则不传
     * @param what     0为删除单条 1为删除所有
     */
    public void deleteWhat(String trade_id, final String what) {
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
                    if (what.equals("1")) {
                        page = 0;
                    }
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

    /*标记所有已读*/
    public void toTellRead() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        map.put("trade_id", "");
        map.put("all_look", "1");//所有修改：1      单条修改：0
        Call<HttpResponse> call = service.getMessageIsRead(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    page=0;
                    onMessage();
                    Log.i("消息状态修改成功", httpResponse.getSuccess());
                } else {
                    Log.i("消息状态修改失败", httpResponse.getSuccess());
                }
                ToastUtil.showToast(NewsActivity.this, httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {

            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        page = 0;
        onMessage();
    }
}

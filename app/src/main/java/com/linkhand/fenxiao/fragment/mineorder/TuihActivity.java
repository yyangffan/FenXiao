package com.linkhand.fenxiao.fragment.mineorder;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.fenxiao.BaseActicity;
import com.linkhand.fenxiao.R;
import com.linkhand.fenxiao.adapter.TuiHAdapter;
import com.linkhand.fenxiao.feng.home.HttpResponse;
import com.linkhand.fenxiao.feng.home.TuiHBean;
import com.linkhand.fenxiao.utils.ToastUtil;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuihActivity extends BaseActicity {

    @Bind(R.id.mine_return_id)
    ImageView mMineReturnId;
    @Bind(R.id.fenxiao_title_id)
    TextView mFenxiaoTitleId;
    @Bind(R.id.tuihuo_lv)
    SlideAndDragListView mTuihuoLv;
    private List<TuiHBean.InfoBean> mInfoBeanList;
    private TuiHAdapter mTuiHAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuih);
        ButterKnife.bind(this);
        initEver();
        onMessage();
    }

    @OnClick({R.id.mine_return_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_return_id:
                this.finish();
                break;
        }
    }

    public void initEver() {
        mInfoBeanList = new ArrayList<>();
        mTuiHAdapter = new TuiHAdapter(this, mInfoBeanList);
        Menu menu = new Menu(false, 0);//第1个参数表示滑动 item 是否能滑的过头 true 表示过头，false 表示不过头
        menu.addItem(new MenuItem.Builder().setWidth(200)
                .setBackground(new ColorDrawable(Color.RED))
                .setText("删除")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize(20)
                .build());
        mTuihuoLv.setMenu(menu);
        mTuihuoLv.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
            @Override
            public int onMenuItemClick(View v, final int itemPosition, int buttonPosition, int direction) {
                switch (direction) {
                    case MenuItem.DIRECTION_RIGHT:
                        TuiHBean.InfoBean infoBean = mInfoBeanList.get(itemPosition);
                        if (infoBean.getOrder_quit() == 1) {
                            userDelete(infoBean.getOrder_id() + "");
                        } else {
                            ToastUtil.showToast(TuihActivity.this,"只可删除退货完成的订单");
                        }
                        break;
                    default:
                        return Menu.ITEM_NOTHING;
                }
                return Menu.ITEM_NOTHING;
            }
        });
        mTuihuoLv.setAdapter(mTuiHAdapter);
    }

    /*删除订单*/
    public void userDelete(String or_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", or_id);
        Call<HttpResponse> call = service.getDelOrder(map);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                HttpResponse httpResponse = response.body();
                if (httpResponse.getCode() == 100) {
                    onMessage();
                }
                ToastUtil.showToast(TuihActivity.this, httpResponse.getSuccess());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                ToastUtil.showToast(TuihActivity.this, "网络异常");
            }
        });
    }


    /*获取退货列表数据*/
    public void onMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", mUserId);
        Call<TuiHBean> call = service.getQuitList(map);
        call.enqueue(new Callback<TuiHBean>() {
            @Override
            public void onResponse(Call<TuiHBean> call, Response<TuiHBean> response) {
                TuiHBean tuiHBean = response.body();
                mInfoBeanList.clear();
                if (tuiHBean.getCode() == 100) {
                    mInfoBeanList.addAll(tuiHBean.getInfo());
                } else {
                    ToastUtil.showToast(TuihActivity.this, tuiHBean.getSuccess());
                }
                mTuiHAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TuiHBean> call, Throwable t) {
                ToastUtil.showToast(TuihActivity.this, "网络异常");
            }
        });

    }
}

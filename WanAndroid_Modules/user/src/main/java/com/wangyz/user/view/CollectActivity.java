package com.wangyz.user.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wangyz.common.base.BaseActivity;
import com.wangyz.common.bean.db.Collect;
import com.wangyz.common.bean.event.Event;
import com.wangyz.common.custom.SpaceItemDecoration;
import com.wangyz.user.R;
import com.wangyz.user.adapter.CollectArticleAdapter;
import com.wangyz.user.contract.Contract;
import com.wangyz.user.presenter.CollectActivityPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/28 14:19
 * @description CollectActivity
 */
public class CollectActivity extends BaseActivity<Contract.CollectActivityView, CollectActivityPresenter> implements Contract.CollectActivityView, View.OnClickListener {

    ImageView mBack;

    SmartRefreshLayout mSmartRefreshLayout;

    RecyclerView mRecyclerView;

    WaveLoadingView mWaveLoadingView;

    private Context mContext;

    private CollectArticleAdapter mAdapter;

    private int mPage;

    private List<Collect> mList = new ArrayList<>();

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.user_activity_collect;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();

        mBack = findViewById(R.id.back);
        mSmartRefreshLayout = findViewById(R.id.activity_collect_article_refresh);
        mRecyclerView = findViewById(R.id.activity_collect_article_list);
        mWaveLoadingView = findViewById(R.id.loading);

        mBack.setOnClickListener(this);

        EventBus.getDefault().register(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(mContext.getResources().getDimensionPixelSize(R.dimen.main_list_item_margin)));

        mAdapter = new CollectArticleAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.load(mPage);

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refresh(0);
        });

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            mPresenter.load(mPage);
        });
    }

    @Override
    protected CollectActivityPresenter createPresenter() {
        return new CollectActivityPresenter();
    }

    @Override
    public void onLoading() {
        LogUtils.i();
    }

    @Override
    public void onLoadSuccess() {
        LogUtils.i();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadFailed() {
        LogUtils.e();
        ToastUtils.showShort(mContext.getString(R.string.common_load_failed));
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onLoad(List<Collect> list) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (list != null) {
            List<Collect> tempList = new ArrayList<>();
            list.stream().forEach(m -> {
                long count = mList.stream().filter(n -> n.articleId == m.articleId).count();
                if (count <= 0) {
                    tempList.add(m);
                }
            });
            mList.addAll(tempList);
            mAdapter.setList(mList);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onRefresh(List<Collect> list) {
        if (list != null) {
            List<Collect> tempList = new ArrayList<>();
            list.stream().forEach(m -> {
                long count = mList.stream().filter(n -> n.articleId == m.articleId).count();
                if (count <= 0) {
                    tempList.add(m);
                }
            });
            mList.addAll(0, tempList);
            mAdapter.setList(mList);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onUnCollect(com.wangyz.common.bean.model.Collect result, int articleId) {
        if (result != null) {
            if (result.getErrorCode() == 0) {
                List<Collect> tempList = mList.stream().filter(a -> a.articleId != articleId).collect(Collectors.toList());
                mList.clear();
                mList.addAll(tempList);
                mAdapter.setList(mList);
            } else {
                ToastUtils.showShort(mContext.getString(R.string.common_uncollect_failed));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_COLLECT) {
            if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mPresenter.unCollect(articleId);
            }
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

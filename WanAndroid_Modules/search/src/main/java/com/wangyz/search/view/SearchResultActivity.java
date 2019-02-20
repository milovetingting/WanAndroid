package com.wangyz.search.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wangyz.common.ConstantValue;
import com.wangyz.common.base.BaseActivity;
import com.wangyz.common.bean.db.Article;
import com.wangyz.common.bean.event.Event;
import com.wangyz.common.bean.model.Collect;
import com.wangyz.common.bean.model.SearchResult;
import com.wangyz.common.custom.SpaceItemDecoration;
import com.wangyz.search.R;
import com.wangyz.search.adapter.SearchResultAdapter;
import com.wangyz.search.contract.Contract;
import com.wangyz.search.presenter.SearchResultActivityPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/28 17:28
 * @description SearchResultActivity
 */
public class SearchResultActivity extends BaseActivity<Contract.SearchResultActivityView, SearchResultActivityPresenter> implements Contract.SearchResultActivityView, View.OnClickListener {

    ImageView mBack;

    SmartRefreshLayout mSmartRefreshLayout;

    RecyclerView mRecyclerView;

    TextView mTitle;

    WaveLoadingView mWaveLoadingView;

    private Context mContext;

    private SearchResultAdapter mAdapter;

    private int mPage;

    private String mKey;

    private List<Article> mList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.search_activity_search_result;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mContext = getApplicationContext();

        mBack = findViewById(R.id.back);
        mSmartRefreshLayout = findViewById(R.id.activity_search_result_refresh);
        mRecyclerView = findViewById(R.id.activity_search_result_list);
        mTitle = findViewById(R.id.title);
        mWaveLoadingView = findViewById(R.id.loading);

        mBack.setOnClickListener(this);

        mKey = getIntent().getStringExtra(ConstantValue.KEY_KEYOWRD);
        if (TextUtils.isEmpty(mKey)) {
            return;
        }

        mTitle.setText(mKey);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(mContext.getResources().getDimensionPixelSize(R.dimen.main_list_item_margin)));

        mAdapter = new SearchResultAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.search(mKey, mPage);

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            mPresenter.search(mKey, mPage);
        });
    }

    @Override
    protected SearchResultActivityPresenter createPresenter() {
        return new SearchResultActivityPresenter();
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
        LogUtils.i();
        ToastUtils.showShort(mContext.getString(R.string.common_load_failed));
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onSearch(SearchResult result) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (result != null && result.getErrorCode() == 0) {
            List<Article> tempList = new ArrayList<>();
            result.getData().getDatas().forEach(m -> {
                long count = mList.stream().filter(n -> n.articleId == m.getId()).count();
                if (count <= 0) {
                    Article model = new Article();
                    model.articleId = m.getId();
                    model.title = m.getTitle();
                    model.author = m.getAuthor();
                    model.category = m.getSuperChapterName() + "/" + m.getChapterName();
                    model.time = m.getPublishTime();
                    model.link = m.getLink();
                    model.collect = m.isCollect();
                    tempList.add(model);
                }
            });
            mList.addAll(tempList);
            mAdapter.setList(mList);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onCollect(Collect result, int articleId) {
        if (result != null) {
            if (result.getErrorCode() == 0) {
                mList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                mAdapter.setList(mList);
            } else {
                ToastUtils.showShort(mContext.getString(R.string.common_collect_failed));
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onUnCollect(Collect result, int articleId) {
        if (result != null) {
            if (result.getErrorCode() == 0) {
                mList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                mAdapter.setList(mList);
            } else {
                ToastUtils.showShort(mContext.getString(R.string.common_uncollect_failed));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_SEARCH_RESULT) {
            if (event.type == Event.TYPE_COLLECT) {
                int articleId = Integer.valueOf(event.data);
                mPresenter.collect(articleId);
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mPresenter.unCollect(articleId);
            } else if (event.type == Event.TYPE_LOGIN) {
                mList.clear();
                mPresenter.search(mKey, 0);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mList.clear();
                mPresenter.search(mKey, 0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        }
    }
}

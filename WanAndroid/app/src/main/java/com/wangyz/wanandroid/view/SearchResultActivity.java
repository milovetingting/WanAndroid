package com.wangyz.wanandroid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.adapter.SearchResultAdapter;
import com.wangyz.wanandroid.base.BaseActivity;
import com.wangyz.wanandroid.bean.db.Article;
import com.wangyz.wanandroid.bean.event.Event;
import com.wangyz.wanandroid.bean.model.Collect;
import com.wangyz.wanandroid.bean.model.SearchResult;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.custom.SpaceItemDecoration;
import com.wangyz.wanandroid.presenter.SearchResultActivityPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/28 17:28
 * @description SearchResultActivity
 */
public class SearchResultActivity extends BaseActivity<Contract.SearchResultActivityView, SearchResultActivityPresenter> implements Contract.SearchResultActivityView {

    @BindView(R.id.activity_search_result_refresh)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.activity_search_result_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.loading)
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
        return R.layout.activity_search_result;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mContext = getApplicationContext();

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
        ToastUtils.showShort(mContext.getString(R.string.load_failed));
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
                ToastUtils.showShort(mContext.getString(R.string.collect_failed));
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
                ToastUtils.showShort(mContext.getString(R.string.uncollect_failed));
            }
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
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
}

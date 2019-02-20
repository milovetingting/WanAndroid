package com.wangyz.wanandroid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.adapter.TreeArticleAdapter;
import com.wangyz.wanandroid.base.BaseFragment;
import com.wangyz.wanandroid.bean.db.Article;
import com.wangyz.wanandroid.bean.event.Event;
import com.wangyz.wanandroid.bean.model.Collect;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.custom.SpaceItemDecoration;
import com.wangyz.wanandroid.presenter.TreeArticleFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/23 15:32
 * @description TreeArticleFragment
 */
public class TreeArticleFragment extends BaseFragment<Contract.TreeArticleFragmentView, TreeArticleFragmentPresenter> implements Contract.TreeArticleFragmentView {

    @BindView(R.id.fragment_tree_article_refresh)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.fragment_tree_article_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading)
    WaveLoadingView mWaveLoadingView;

    private Context mContext;

    private TreeArticleAdapter mAdapter;

    private int mTreeType;

    private int mPage;

    private List<Article> mList = new ArrayList<>();

    private boolean mAddItemDecoration;

    public void setTreeType(int treeType) {
        mTreeType = treeType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_tree_article;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mContext = getContext().getApplicationContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        if (!mAddItemDecoration) {
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(mContext.getResources().getDimensionPixelSize(R.dimen.main_list_item_margin)));
            mAddItemDecoration = true;
        }

        mAdapter = new TreeArticleAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.load(mTreeType, mPage);

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refresh(mTreeType, 0);
        });

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            mPresenter.load(mTreeType, mPage);
        });

    }

    @Override
    protected TreeArticleFragmentPresenter createPresenter() {
        return new TreeArticleFragmentPresenter();
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
        ToastUtils.showShort(mContext.getString(R.string.load_failed));
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onLoad(List<Article> list) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        List<Article> tempList = new ArrayList<>();
        if (list != null) {
            list.stream().forEach(a -> {
                if (mList.stream().filter(m -> m.id == a.id).count() <= 0) {
                    tempList.add(a);
                } else {
                    mList.stream().forEach(m -> {
                        if (m.id == a.id) {
                            m.title = a.title;
                            m.des = a.des;
                            m.authorId = a.authorId;
                            m.author = a.author;
                            m.category = a.category;
                            m.time = a.time;
                            m.link = a.link;
                            m.collect = a.collect;
                        }
                    });
                }
            });
            mList.addAll(tempList);
            mAdapter.setList(mList);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onRefresh(List<Article> list) {
        List<Article> tempList = new ArrayList<>();
        if (list != null) {
            list.stream().forEach(a -> {
                if (mList.stream().filter(m -> m.id == a.id).count() <= 0) {
                    tempList.add(a);
                } else {
                    mList.stream().forEach(m -> {
                        if (m.id == a.id) {
                            m.title = a.title;
                            m.des = a.des;
                            m.authorId = a.authorId;
                            m.author = a.author;
                            m.category = a.category;
                            m.time = a.time;
                            m.link = a.link;
                            m.collect = a.collect;
                        }
                    });
                }
            });
            mList.addAll(0, tempList);
            mAdapter.setList(mList);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_TREE) {
            if (event.type == Event.TYPE_COLLECT) {
                int articleId = Integer.valueOf(event.data);
                mPresenter.collect(articleId);
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mPresenter.unCollect(articleId);
            } else if (event.type == Event.TYPE_LOGIN) {
                mList.clear();
                mPresenter.refresh(mTreeType, 0);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mList.clear();
                mPresenter.refresh(mTreeType, 0);
            }
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
}

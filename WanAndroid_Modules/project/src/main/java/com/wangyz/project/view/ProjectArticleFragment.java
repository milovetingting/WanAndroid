package com.wangyz.project.view;

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
import com.wangyz.common.base.BaseFragment;
import com.wangyz.common.bean.event.Event;
import com.wangyz.common.custom.SpaceItemDecoration;
import com.wangyz.project.R;
import com.wangyz.project.adapter.ProjectArticleAdapter;
import com.wangyz.common.bean.db.Article;
import com.wangyz.common.bean.model.Collect;
import com.wangyz.project.contract.Contract;
import com.wangyz.project.presenter.ProjectArticleFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/22 14:59
 * @description ProjectArticleFragment
 */
public class ProjectArticleFragment extends BaseFragment<Contract.ProjectArticleFragmentView, ProjectArticleFragmentPresenter> implements Contract.ProjectArticleFragmentView {

    SmartRefreshLayout mSmartRefreshLayout;

    RecyclerView mRecyclerView;

    WaveLoadingView mWaveLoadingView;

    private Context mContext;

    private ProjectArticleAdapter mAdapter;

    private int mCategoryId;

    private int mPage;

    private List<Article> mList = new ArrayList<>();

    private boolean mAddItemDecoration;

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
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
        return R.layout.project_fragment_project_article;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getContext().getApplicationContext();

        mSmartRefreshLayout = rootView.findViewById(R.id.fragment_project_article_refresh);
        mRecyclerView = rootView.findViewById(R.id.fragment_project_article_list);
        mWaveLoadingView = rootView.findViewById(R.id.loading);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        if (!mAddItemDecoration) {
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(mContext.getResources().getDimensionPixelSize(R.dimen.main_list_item_margin)));
            mAddItemDecoration = true;
        }

        mAdapter = new ProjectArticleAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.load(mCategoryId, mPage);

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refresh(mCategoryId, 0);
        });

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            mPresenter.load(mCategoryId, mPage);
        });
    }

    @Override
    protected ProjectArticleFragmentPresenter createPresenter() {
        return new ProjectArticleFragmentPresenter();
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
        if (event.target == Event.TARGET_PROJECT) {
            if (event.type == Event.TYPE_COLLECT) {
                String[] data = event.data.split(";");
                if (data.length > 1 && mCategoryId == Integer.valueOf(data[1])) {
                    int articleId = Integer.valueOf(data[0]);
                    mPresenter.collect(articleId);
                }
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                String[] data = event.data.split(";");
                if (data.length > 1 && mCategoryId == Integer.valueOf(data[1])) {
                    int articleId = Integer.valueOf(data[0]);
                    mPresenter.unCollect(articleId);
                }
            } else if (event.type == Event.TYPE_LOGIN) {
                mList.clear();
                mPresenter.refresh(mCategoryId, 0);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mList.clear();
                mPresenter.refresh(mCategoryId, 0);
            }
        }
    }
}

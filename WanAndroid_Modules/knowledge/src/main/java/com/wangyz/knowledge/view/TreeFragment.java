package com.wangyz.knowledge.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wangyz.common.ConstantValue;
import com.wangyz.common.base.BaseFragment;
import com.wangyz.common.custom.SpaceItemDecoration;
import com.wangyz.knowledge.R;
import com.wangyz.knowledge.adapter.TreeAdapter;
import com.wangyz.common.bean.model.TreeInfo;
import com.wangyz.knowledge.contract.Contract;
import com.wangyz.knowledge.presenter.TreeFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/23 9:44
 * @description TreeFragment
 */
@Route(path = ConstantValue.ROUTE_TREE_ROOT)
public class TreeFragment extends BaseFragment<Contract.TreeFragmentView, TreeFragmentPresenter> implements Contract.TreeFragmentView {

    SmartRefreshLayout mSmartRefreshLayout;

    RecyclerView mRecyclerView;

    WaveLoadingView mWaveLoadingView;

    private Context mContext;

    private TreeAdapter mAdapter;

    private List<TreeInfo> mList = new ArrayList<>();

    private boolean mAddItemDecoration;

    @Override
    protected int getContentViewId() {
        return R.layout.knowledge_fragment_tree;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mContext = getContext().getApplicationContext();

        mSmartRefreshLayout = rootView.findViewById(R.id.fragment_tree_refresh);
        mRecyclerView = rootView.findViewById(R.id.fragment_tree_list);
        mWaveLoadingView = rootView.findViewById(R.id.loading);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        if (!mAddItemDecoration) {
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(mContext.getResources().getDimensionPixelSize(R.dimen.main_list_item_margin)));
            mAddItemDecoration = true;
        }

        mAdapter = new TreeAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.load();

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.load();
        });

    }

    @Override
    protected TreeFragmentPresenter createPresenter() {
        return new TreeFragmentPresenter();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onLoad(List<TreeInfo> list) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (list != null) {
            mList.clear();
            mList.addAll(list);
            mAdapter.setList(mList);
        }
    }

    @Override
    public void onRefresh(List<TreeInfo> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(0, list);
            mAdapter.setList(mList);
        }
    }

    @Override
    public void onLoading() {
        LogUtils.i();
    }

    @Override
    public void onLoadSuccess() {
        LogUtils.i();
        mSmartRefreshLayout.finishRefresh();
    }

    @Override
    public void onLoadFailed() {
        LogUtils.e();
        ToastUtils.showShort(mContext.getString(R.string.common_load_failed));
        mSmartRefreshLayout.finishRefresh();
    }
}

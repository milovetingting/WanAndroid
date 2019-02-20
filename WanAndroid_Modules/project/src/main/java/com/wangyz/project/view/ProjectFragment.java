package com.wangyz.project.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.wangyz.common.ConstantValue;
import com.wangyz.common.base.BaseFragment;
import com.wangyz.project.R;
import com.wangyz.project.adapter.ProjectAdapter;
import com.wangyz.common.bean.db.ProjectCategory;
import com.wangyz.project.contract.Contract;
import com.wangyz.project.presenter.ProjectFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/22 14:46
 * @description ProjectFragment
 */
@Route(path = ConstantValue.ROUTE_PROJECT)
public class ProjectFragment extends BaseFragment<Contract.ProjectFragmentView, ProjectFragmentPresenter> implements Contract.ProjectFragmentView {

    TabLayout mTabLayout;

    ViewPager mViewPager;

    WaveLoadingView mWaveLoadingView;

    private FragmentManager mFragmentManager;

    private List<ProjectArticleFragment> mList = new ArrayList<>();

    private List<ProjectCategory> mCategories = new ArrayList<>();

    private ProjectAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.project_fragment_project;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mTabLayout = rootView.findViewById(R.id.fragment_project_tab);
        mViewPager = rootView.findViewById(R.id.fragment_project_viewpager);
        mWaveLoadingView = rootView.findViewById(R.id.loading);

        mFragmentManager = getActivity().getSupportFragmentManager();

        mAdapter = new ProjectAdapter(mFragmentManager, mList, mCategories);
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mPresenter.refreshProject();
    }

    @Override
    protected ProjectFragmentPresenter createPresenter() {
        return new ProjectFragmentPresenter();
    }

    @Override
    public void onLoading() {
        LogUtils.i();
    }

    @Override
    public void onLoadSuccess() {
        LogUtils.i();
    }

    @Override
    public void onLoadFailed() {
        LogUtils.e();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onLoadProject(List<ProjectCategory> list) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (list != null) {
            mList.clear();
            list.stream().forEach(a -> {
                ProjectArticleFragment fragment = new ProjectArticleFragment();
                fragment.setCategoryId(a.categoryId);
                mList.add(fragment);
            });
            mAdapter.setList(mList, list);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onRefreshProject(List<ProjectCategory> list) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (list == null || list.size() <= 0) {
            mPresenter.loadProject();
        } else {
            mList.clear();
            list.stream().forEach(a -> {
                ProjectArticleFragment fragment = new ProjectArticleFragment();
                fragment.setCategoryId(a.categoryId);
                mList.add(fragment);
            });
            mAdapter.setList(mList, list);
        }
    }
}

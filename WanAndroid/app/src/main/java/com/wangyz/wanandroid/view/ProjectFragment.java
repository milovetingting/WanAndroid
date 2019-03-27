package com.wangyz.wanandroid.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.adapter.ProjectAdapter;
import com.wangyz.wanandroid.base.BaseFragment;
import com.wangyz.wanandroid.bean.db.ProjectCategory;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.presenter.ProjectFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/22 14:46
 * @description ProjectFragment
 */
public class ProjectFragment extends BaseFragment<Contract.ProjectFragmentView, ProjectFragmentPresenter> implements Contract.ProjectFragmentView {

    @BindView(R.id.fragment_project_tab)
    TabLayout mTabLayout;

    @BindView(R.id.fragment_project_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.loading)
    WaveLoadingView mWaveLoadingView;

    private FragmentManager mFragmentManager;

    private List<ProjectArticleFragment> mList = new ArrayList<>();

    private List<ProjectCategory> mCategories = new ArrayList<>();

    private ProjectAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
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
        mWaveLoadingView.setVisibility(View.GONE);
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

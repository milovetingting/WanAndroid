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
import com.wangyz.wanandroid.adapter.WxAdapter;
import com.wangyz.wanandroid.base.BaseFragment;
import com.wangyz.wanandroid.bean.db.Author;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.presenter.WxFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/21 17:22
 * @description WxFragment
 */
public class WxFragment extends BaseFragment<Contract.WxFragmentView, WxFragmentPresenter> implements Contract.WxFragmentView {

    @BindView(R.id.fragment_wx_tab)
    TabLayout mTabLayout;

    @BindView(R.id.fragment_wx_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.loading)
    WaveLoadingView mWaveLoadingView;

    private FragmentManager mFragmentManager;

    private List<WxArticleFragment> mList = new ArrayList<>();

    private List<Author> mAuthors = new ArrayList<>();

    private WxAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_wx;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mFragmentManager = getActivity().getSupportFragmentManager();

        mAdapter = new WxAdapter(mFragmentManager, mList, mAuthors);
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

        mPresenter.refreshWx();
    }

    @Override
    protected WxFragmentPresenter createPresenter() {
        return new WxFragmentPresenter();
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
    public void onLoadWx(List<Author> list) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (list != null) {
            mList.clear();
            list.stream().forEach(a -> {
                WxArticleFragment fragment = new WxArticleFragment();
                fragment.setAuthorId(a.authorId);
                mList.add(fragment);
            });
            mAdapter.setList(mList, list);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onRefreshWx(List<Author> list) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (list == null || list.size() <= 0) {
            mPresenter.loadWx();
        } else {
            mList.clear();
            list.stream().forEach(a -> {
                WxArticleFragment fragment = new WxArticleFragment();
                fragment.setAuthorId(a.authorId);
                mList.add(fragment);
            });
            mAdapter.setList(mList, list);
        }
    }
}

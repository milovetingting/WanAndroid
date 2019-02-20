package com.wangyz.wx.view;

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
import com.wangyz.wx.R;
import com.wangyz.wx.adapter.WxAdapter;
import com.wangyz.common.bean.db.Author;
import com.wangyz.wx.contract.Contract;
import com.wangyz.wx.presenter.WxFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/21 17:22
 * @description WxFragment
 */
@Route(path = ConstantValue.ROUTE_WX)
public class WxFragment extends BaseFragment<Contract.WxFragmentView, WxFragmentPresenter> implements Contract.WxFragmentView {

    TabLayout mTabLayout;

    ViewPager mViewPager;

    WaveLoadingView mWaveLoadingView;

    private FragmentManager mFragmentManager;

    private List<WxArticleFragment> mList = new ArrayList<>();

    private List<Author> mAuthors = new ArrayList<>();

    private WxAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.wx_fragment_wx;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mTabLayout = rootView.findViewById(R.id.fragment_wx_tab);
        mViewPager = rootView.findViewById(R.id.fragment_wx_viewpager);
        mWaveLoadingView = rootView.findViewById(R.id.loading);

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

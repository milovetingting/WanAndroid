package com.wangyz.wx.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.wangyz.common.bean.db.Author;
import com.wangyz.wx.view.WxArticleFragment;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyz
 * @time 2019/1/22 9:40
 * @description WxAdapter
 */
public class WxAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;

    private List<WxArticleFragment> mList = new ArrayList<>();

    private List<Author> mAuthors = new ArrayList<>();

    public void setList(List<WxArticleFragment> list, List<Author> authors) {
        mList.clear();
        mList.addAll(list);
        mAuthors.clear();
        mAuthors.addAll(authors);
        notifyDataSetChanged();
    }

    public WxAdapter(FragmentManager fm, List<WxArticleFragment> list, List<Author> authors) {
        super(fm);
        mFragmentManager = fm;
        mList.addAll(list);
        mAuthors.addAll(authors);
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mAuthors.get(position).author;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        removeFragment(container, position);
        return super.instantiateItem(container, position);
    }


    private void removeFragment(ViewGroup container, int index) {
        String tag = getFragmentTag(container.getId(), index);
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
    }

    private String getFragmentTag(int viewId, int index) {
        String tag = "";
        try {
            Class<FragmentPagerAdapter> cls = FragmentPagerAdapter.class;
            Class<?>[] parameterTypes = {int.class, long.class};
            Method method = cls.getDeclaredMethod("makeFragmentName", parameterTypes);
            method.setAccessible(true);
            tag = (String) method.invoke(this, viewId, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }
}

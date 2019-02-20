package com.wangyz.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.wangyz.wanandroid.bean.db.ProjectCategory;
import com.wangyz.wanandroid.view.ProjectArticleFragment;

import org.apache.commons.lang3.StringEscapeUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyz
 * @time 2019/1/22 9:40
 * @description ProjectAdapter
 */
public class ProjectAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;

    private List<ProjectArticleFragment> mList = new ArrayList<>();

    private List<ProjectCategory> mCategories = new ArrayList<>();

    public void setList(List<ProjectArticleFragment> list, List<ProjectCategory> categories) {
        mList.clear();
        mList.addAll(list);
        mCategories.clear();
        mCategories.addAll(categories);
        notifyDataSetChanged();
    }

    public ProjectAdapter(FragmentManager fm, List<ProjectArticleFragment> list, List<ProjectCategory> categories) {
        super(fm);
        mFragmentManager = fm;
        mList.addAll(list);
        mCategories.addAll(categories);
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
        return StringEscapeUtils.unescapeHtml4(mCategories.get(position).name);
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

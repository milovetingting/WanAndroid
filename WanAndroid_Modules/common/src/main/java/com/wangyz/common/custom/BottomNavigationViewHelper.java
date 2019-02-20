package com.wangyz.common.custom;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

/**
 * @author wangyz
 * @time 2019/1/21 14:56
 * @description BottomNavigationViewHelper
 */
public class BottomNavigationViewHelper {

    /**
     * 禁用超过3个item时的切换动画
     *
     * @param view
     */
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        int childCount = view.getChildCount();
        if (childCount > 0) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            if (menuView != null) {
                menuView.setLabelVisibilityMode(1);
                menuView.updateMenuView();
            }
        }
    }

}

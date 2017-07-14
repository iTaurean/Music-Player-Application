package com.android.lvxin.musicplayer.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @ClassName: ActivityUtils
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/6 10:32
 */
public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, tag);
        transaction.commit();
    }
}

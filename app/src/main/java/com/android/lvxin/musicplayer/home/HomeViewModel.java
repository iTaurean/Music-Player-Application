package com.android.lvxin.musicplayer.home;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * @ClassName: HomeViewModel
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/5 14:19
 */
public class HomeViewModel extends BaseObservable {

    private Context mContext;

    private HomeNavigator mNavigator;

    public HomeViewModel(Context context) {
        mContext = context;
    }

    void setNavigator(HomeNavigator navigator) {
        mNavigator = navigator;
    }

    void onActivityDestroyed() {
        mNavigator = null;
    }

    public void start() {

    }

    public void openLocalMusicPage() {
        if (null != mNavigator) {
            mNavigator.openClickItem();
        }
    }
}

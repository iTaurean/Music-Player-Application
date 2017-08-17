package com.android.lvxin.musicplayer.player;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: HiiPagerAdapter
 * @Description: TODO
 * @Author: lvxin
 * @Date: 07/04/2017 15:33
 */
public class HiiPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    /**
     * Instantiates a new My pager adapter.
     *
     * @param fm the fm
     */
    public HiiPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 设置adapter的内容
     *
     * @param data
     */
    public void update(List<Fragment> data) {
        if (null != data) {
            fragments.clear();
            fragments.addAll(data);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

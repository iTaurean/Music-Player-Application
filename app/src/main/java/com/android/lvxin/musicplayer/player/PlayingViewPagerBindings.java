package com.android.lvxin.musicplayer.player;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.android.lvxin.musicplayer.util.ActivityUtils;
import com.android.lvxin.musicplayer.util.ViewModelHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PlayingViewPagerBindings
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/8/16 14:32
 */
public class PlayingViewPagerBindings {

    private static final String MUSIC_PLAYING_INFO_TAG = "music_playing_info_tag";

    private static final String MUSIC_PLAYING_LIST_TAG = "music_playing_list_tag";

    @BindingAdapter("app:viewPagerContents")
    public static void setupViewPage(final ViewPager vp, final MusicPlayingFragment fragment) {
        Context context = fragment.getContext().getApplicationContext();
        FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();

        MusicPlayingInfoFragment playingInfoFragment = MusicPlayingInfoFragment.newInstance();
        playingInfoFragment.setViewModel(findOrCreatePlayingInfoViewModel(context, fragmentManager));

        MusicPlayingListFragment musicPlayingListFragment = MusicPlayingListFragment.newInstance();
        musicPlayingListFragment.setViewModel(findOrCreatePlayListViewModel(context, fragmentManager));

        List<Fragment> fragments = new ArrayList<>(2);
        fragments.add(playingInfoFragment);
        fragments.add(musicPlayingListFragment);

        HiiPagerAdapter pagerAdapter = new HiiPagerAdapter(fragmentManager);
        pagerAdapter.update(fragments);
        vp.setAdapter(pagerAdapter);
        vp.setOffscreenPageLimit(0);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // TODO: 2017/8/16
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private static MusicPlayingInfoViewModel findOrCreatePlayingInfoViewModel(Context context, FragmentManager fm) {
        ViewModelHolder<MusicPlayingInfoViewModel> retainedViewModel = (ViewModelHolder<MusicPlayingInfoViewModel>) fm.findFragmentByTag(MUSIC_PLAYING_INFO_TAG);
        if (null == retainedViewModel || null == retainedViewModel.getViewModel()) {
            MusicPlayingInfoViewModel viewModel = new MusicPlayingInfoViewModel(context);
            retainedViewModel = ViewModelHolder.createContainer(viewModel);
            ActivityUtils.addFragmentToActivity(fm, retainedViewModel, MUSIC_PLAYING_INFO_TAG);
        }

        return retainedViewModel.getViewModel();
    }

    private static MusicPlayingListViewModel findOrCreatePlayListViewModel(Context context, FragmentManager fm) {
        ViewModelHolder<MusicPlayingListViewModel> retainedViewModel = (ViewModelHolder<MusicPlayingListViewModel>) fm.findFragmentByTag(MUSIC_PLAYING_LIST_TAG);
        if (null == retainedViewModel || null == retainedViewModel.getViewModel()) {
            MusicPlayingListViewModel viewModel = new MusicPlayingListViewModel(context);
            retainedViewModel = ViewModelHolder.createContainer(viewModel);
            ActivityUtils.addFragmentToActivity(fm, retainedViewModel, MUSIC_PLAYING_LIST_TAG);
        }

        return retainedViewModel.getViewModel();
    }
}

package com.android.lvxin.musicplayer.player;

import android.content.Context;

import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.service.IMusicAidlInterface;
import com.android.lvxin.musicplayer.service.MusicPlayer;

import java.util.List;
import java.util.Observable;

/**
 * @ClassName: MusicPlayingViewModel
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/18 16:29
 */
public class MusicPlayingViewModel extends Observable {

    private Context context;

    private MusicPlayingNavigation mNavigation;

    private IMusicAidlInterface mService;

    public MusicPlayingViewModel(Context context) {
        this.context = context.getApplicationContext();
    }

    public void setNavigation(MusicPlayingNavigation navigation) {
        mNavigation = navigation;
    }

    public void onViewDestroy() {
        mNavigation = null;
    }

    public void start(List<MusicModel> playList) {
        MusicPlayer.setPlayList(playList);
    }

    public void onNextMusic() {
        if (null != mNavigation) {
            mNavigation.onNextMusic();
        }
    }

    public void onPrevMusic() {
        if (null != mNavigation) {
            mNavigation.onPrevMusic();
        }
    }


}

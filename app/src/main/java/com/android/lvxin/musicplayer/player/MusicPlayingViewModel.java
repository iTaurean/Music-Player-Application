package com.android.lvxin.musicplayer.player;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.lvxin.musicplayer.BR;
import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.service.IMusicAidlInterface;
import com.android.lvxin.musicplayer.service.MusicPlayer;

import java.util.List;

/**
 * @ClassName: MusicPlayingViewModel
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/18 16:29
 */
public class MusicPlayingViewModel extends BaseObservable {

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


    @Bindable
    public boolean isPlaying() {
        return MusicPlayer.isPlaying();
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

    /**
     * 播放暂停
     */
    public void onPlayPauseMusic() {
        if (MusicPlayer.isPlaying()) {
            MusicPlayer.pause();
        } else {
            MusicPlayer.resume();
        }
        notifyPropertyChanged(BR.playing);
    }


}

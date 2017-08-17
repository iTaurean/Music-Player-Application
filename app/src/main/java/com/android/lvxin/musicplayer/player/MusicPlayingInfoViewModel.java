package com.android.lvxin.musicplayer.player;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.event.UpdateMusicInfoEvent;
import com.android.lvxin.musicplayer.service.MusicPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @ClassName: MusicPlayingInfoViewModel
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/8/16 14:14
 */
public class MusicPlayingInfoViewModel extends BaseObservable {
    private static final String TAG = "MusicPlayingInfoViewMod";

    public final ObservableField<MusicModel> music = new ObservableField<>();

    private Context context;

    public MusicPlayingInfoViewModel(Context context) {
        this.context = context.getApplicationContext();

    }

    public void start() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        updateMusicInfo();
    }

    public void onViewDestroy() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateMusicInfo(UpdateMusicInfoEvent event) {
        Log.d(TAG, "updateMusicInfo: ");
        updateMusicInfo();
    }

    private void updateMusicInfo() {
        MusicModel playingMusic = MusicPlayer.getPlayingMusic();
        if (null != playingMusic) {
            music.set(playingMusic);
        }
    }
}

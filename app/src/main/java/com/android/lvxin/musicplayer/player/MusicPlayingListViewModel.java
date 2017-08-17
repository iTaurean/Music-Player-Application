package com.android.lvxin.musicplayer.player;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * @ClassName: MusicPlayingListViewModel
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/8/16 14:02
 */
public class MusicPlayingListViewModel extends BaseObservable {
    private Context context;

    public MusicPlayingListViewModel(Context context) {
        this.context = context.getApplicationContext();
    }

    public void start() {

    }
}

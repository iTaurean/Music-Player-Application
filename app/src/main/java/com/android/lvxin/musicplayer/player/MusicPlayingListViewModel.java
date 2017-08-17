package com.android.lvxin.musicplayer.player;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.android.lvxin.musicplayer.BR;
import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.service.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MusicPlayingListViewModel
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/8/16 14:02
 */
public class MusicPlayingListViewModel extends BaseObservable {

    public final ObservableList<MusicModel> items = new ObservableArrayList<>();

    private Context context;

    public MusicPlayingListViewModel(Context context) {
        this.context = context.getApplicationContext();
    }

    public void start() {
        List<MusicModel> playList = MusicPlayer.getOriginalPlayList();
        if (null == playList) {
            playList = new ArrayList<>(0);
        }
        items.clear();
        items.addAll(playList);
        notifyPropertyChanged(BR.empty);
    }

    @Bindable
    public boolean isEmpty() {
        return items.isEmpty();
    }
}

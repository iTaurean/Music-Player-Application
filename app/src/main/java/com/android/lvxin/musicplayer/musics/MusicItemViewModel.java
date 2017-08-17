package com.android.lvxin.musicplayer.musics;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.data.source.MusicsRepository;

import java.lang.ref.WeakReference;

/**
 * @ClassName: MusicItemViewModel
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/12 15:59
 */
public class MusicItemViewModel extends BaseObservable {
    public final ObservableField<String> musicName = new ObservableField<>();

    public final ObservableField<String> artist = new ObservableField<>();

    private final ObservableField<MusicModel> mMusicObservable = new ObservableField<>();
    private final Context mContext;
    private MusicsRepository mMusicsRepository;
    private boolean mIsDataLoading;

    private WeakReference<MusicItemNavigation> mNavigation;

    public MusicItemViewModel(Context context) {
        this(context, null);
    }

    public MusicItemViewModel(Context context, MusicsRepository musicsRepository) {
        mContext = context.getApplicationContext();
        mMusicsRepository = musicsRepository;

        mMusicObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                MusicModel music = mMusicObservable.get();
                if (null != music) {
                    musicName.set(music.musicName);
                    artist.set(music.artist);
                }
            }
        });
    }

    public void setNavigation(MusicItemNavigation navigation) {
        mNavigation = new WeakReference<>(navigation);
    }


    public void setMusic(MusicModel music) {
        mMusicObservable.set(music);
    }

    public void musicClicked() {
        if (null != mNavigation && null != mNavigation.get()) {
            mNavigation.get().openMusicPlayPage();
        }
    }
}

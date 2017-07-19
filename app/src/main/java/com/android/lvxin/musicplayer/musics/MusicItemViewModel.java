package com.android.lvxin.musicplayer.musics;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.data.source.MusicsDataSource;
import com.android.lvxin.musicplayer.data.source.MusicsRepository;
import com.android.lvxin.musicplayer.util.ThreadManager;

import java.lang.ref.WeakReference;

/**
 * @ClassName: MusicItemViewModel
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/12 15:59
 */
public class MusicItemViewModel extends BaseObservable implements MusicsDataSource.GetMusicCallback {
    public final ObservableField<String> musicName = new ObservableField<>();

    public final ObservableField<String> artist = new ObservableField<>();

    private final ObservableField<MusicModel> mMusicObservable = new ObservableField<>();

    private final MusicsRepository mMusicsRepository;

    private final Context mContext;

    private boolean mIsDataLoading;

    private WeakReference<MusicItemNavigation> mNavigation;

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

    public void start(final long musicId) {
        if (-1 != musicId) {
            mIsDataLoading = true;
            ThreadManager.getManager().post(ThreadManager.THREAD_DATA, new Runnable() {
                @Override
                public void run() {
                    mMusicsRepository.getMusic(musicId, MusicItemViewModel.this);
                }
            });
        }
    }

    public void setMusic(MusicModel music) {
        mMusicObservable.set(music);
    }

    protected long getMusicId() {
        return mMusicObservable.get().musicId;
    }

    @Override
    public void onMusicLoaded(MusicModel music) {
        mMusicObservable.set(music);
        mIsDataLoading = false;
        notifyChange();
    }

    @Override
    public void onDataNotAvailable() {
        mMusicObservable.set(null);
        mIsDataLoading = false;
    }

    public void musicClicked() {
        if (null != mNavigation && null != mNavigation.get()) {
            mNavigation.get().openMusicPlayPage();
        }
    }
}

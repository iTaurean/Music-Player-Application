package com.android.lvxin.musicplayer.musics;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.android.lvxin.musicplayer.BR;
import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.data.source.MusicsDataSource;
import com.android.lvxin.musicplayer.data.source.MusicsRepository;
import com.android.lvxin.musicplayer.util.ThreadManager;

import java.util.List;

/**
 * @ClassName: MusicsViewModel
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/6 15:24
 */
public class MusicsViewModel extends BaseObservable {

    public final ObservableList<MusicModel> items = new ObservableArrayList<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    public final ObservableField<String> noMusicsLabel = new ObservableField<>();

    private final ObservableBoolean mIsDataLoadingError = new ObservableBoolean(false);

    private MusicItemNavigation mNavigation;

    private Context context;

    private MusicsRepository mMusicsRepository;

    public MusicsViewModel(Context context, MusicsRepository repository) {
        this.context = context.getApplicationContext();
        this.mMusicsRepository = repository;
    }

    void setNavigation(MusicItemNavigation navigation) {
        mNavigation = navigation;
    }

    void onActivityDestroyed() {
        mNavigation = null;
    }

    @Bindable
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void start() {
        loadMusics();
    }

    private void loadMusics() {
        ThreadManager.getManager().post(ThreadManager.THREAD_DATA, new Runnable() {
            @Override
            public void run() {
                mMusicsRepository.getMusics(new MusicsDataSource.LoadMusicsCallback() {
                    @Override
                    public void onTasksLoaded(List<MusicModel> musics) {

                        mIsDataLoadingError.set(false);
                        items.clear();
                        items.addAll(musics);
                        notifyPropertyChanged(BR.empty);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mIsDataLoadingError.set(true);
                    }
                });
            }
        });
    }
}

package com.android.lvxin.musicplayer.data.source;

import com.android.lvxin.musicplayer.data.MusicModel;

import java.util.List;

/**
 * @ClassName: MusicsDataSource
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/11 15:56
 */
public interface MusicsDataSource {

    void getMusics(LoadMusicsCallback callback);

    void getMusic(long musicId, GetMusicCallback callback);

    interface LoadMusicsCallback {

        void onTasksLoaded(List<MusicModel> musics);

        void onDataNotAvailable();
    }

    interface GetMusicCallback {

        void onMusicLoaded(MusicModel music);

        void onDataNotAvailable();
    }
}

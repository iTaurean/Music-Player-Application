package com.android.lvxin.musicplayer.data.source;

import android.content.Context;

import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.data.source.local.MusicsLocalDataSource;
import com.android.lvxin.musicplayer.data.source.remote.MusicsRemoteDataSource;

import java.util.List;

/**
 * @ClassName: MusicsRepository
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/11 15:55
 */
public class MusicsRepository implements MusicsDataSource {

    private static MusicsRepository INSTANCE = null;

    private MusicsLocalDataSource mLocalDataSource;
    private MusicsRemoteDataSource mRemoteDataSource;

    private MusicsRepository(Context context) {
        mLocalDataSource = MusicsLocalDataSource.getInstance(context);
    }

    public static MusicsRepository getInstance(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new MusicsRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public void getMusics(final LoadMusicsCallback callback) {

        // TODO: 2017/7/11 暂时只加载本地音乐
        mLocalDataSource.getMusics(new LoadMusicsCallback() {
            @Override
            public void onTasksLoaded(List<MusicModel> musics) {
                callback.onTasksLoaded(musics);
            }

            @Override
            public void onDataNotAvailable() {
                // TODO: 2017/7/11  get musics from remote
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void getMusic(long musicId, final GetMusicCallback callback) {

        mLocalDataSource.getMusic(musicId, new GetMusicCallback() {
            @Override
            public void onMusicLoaded(MusicModel music) {
                callback.onMusicLoaded(music);
            }

            @Override
            public void onDataNotAvailable() {
                // TODO: 2017/7/11  get music from remote
                callback.onDataNotAvailable();
            }
        });
    }
}

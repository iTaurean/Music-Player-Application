package com.android.lvxin.musicplayer.data.source.local;

import android.content.Context;

import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.data.source.MusicsDataSource;
import com.android.lvxin.musicplayer.util.IConstants;

import java.util.List;

/**
 * @ClassName: MusicsLocalDataSource
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/11 16:04
 */
public class MusicsLocalDataSource implements MusicsDataSource {

    private static MusicsLocalDataSource INSTANCE;
    private Context mContext;

    private MusicsLocalDataSource(Context context) {
        this.mContext = context;
    }

    public static MusicsLocalDataSource getInstance(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new MusicsLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getMusics(LoadMusicsCallback callback) {
        List<MusicModel> musics = QueryLocalMusicHelper.queryMusics(mContext, IConstants.START_FROM_LOCAL);
        if (musics.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onTasksLoaded(musics);
        }
    }

    @Override
    public void getMusic(long musicId, GetMusicCallback callback) {
        MusicModel musicModel = QueryLocalMusicHelper.getMusicModel(mContext, musicId);
        if (null == musicModel) {
            callback.onDataNotAvailable();
        } else {
            callback.onMusicLoaded(musicModel);
        }
    }


}

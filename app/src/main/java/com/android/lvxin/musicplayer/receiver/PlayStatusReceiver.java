package com.android.lvxin.musicplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.android.lvxin.musicplayer.IConstants;
import com.android.lvxin.musicplayer.service.MusicPlayCallback;

/**
 * @ClassName: PlayStatusReceiver
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/18 15:22
 */
public final class PlayStatusReceiver extends BroadcastReceiver {
    private static final String TAG = "PlayStatusReceiver";

    private static PlayStatusReceiver INSTANCE;

    private MusicPlayCallback mCallback;

    private PlayStatusReceiver() {

    }

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(IConstants.BroadcastActions.ACTION_UPDATE_PLAY_MUSIC);
        filter.addAction(IConstants.BroadcastActions.ACTION_MUSIC_PREPARED);
        filter.addAction(IConstants.BroadcastActions.ACTION_MUSIC_COMPLETION);
        filter.addAction(IConstants.BroadcastActions.ACTION_MUSIC_ERROR);
        filter.addAction(IConstants.BroadcastActions.ACTION_UPDATE_MUSIC_PLAY_STATUS);
        filter.addAction(IConstants.BroadcastActions.ACTION_UPDATE_PROGRESS);

        return filter;
    }

    public static PlayStatusReceiver getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new PlayStatusReceiver();
        }

        return INSTANCE;
    }

    public void setPlayStatusCallback(MusicPlayCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (null != mCallback) {
            if (IConstants.BroadcastActions.ACTION_UPDATE_PLAY_MUSIC.equals(action)) {
                mCallback.onUpdateMusicInfo();
            } else if (IConstants.BroadcastActions.ACTION_MUSIC_PREPARED.equals(action)) {
                Log.d(TAG, "onReceive: >>>>");
                mCallback.onPrepared();
            } else if (IConstants.BroadcastActions.ACTION_MUSIC_COMPLETION.equals(action)) {
                mCallback.onCompletion();
            } else if (IConstants.BroadcastActions.ACTION_MUSIC_ERROR.equals(action)) {
                mCallback.onError();
            } else if (IConstants.BroadcastActions.ACTION_UPDATE_MUSIC_PLAY_STATUS.equals(action)) {
                boolean isPlaying = intent.getBooleanExtra(IConstants.BroadcastActions.EXTRA_MUSIC_PLAYING_STATUS, false);
                mCallback.onUpdatePlayStatus(isPlaying);
            } else if (IConstants.BroadcastActions.ACTION_UPDATE_PROGRESS.equals(action)) {
                mCallback.onUpdateProgress();
            }
        }
    }
}

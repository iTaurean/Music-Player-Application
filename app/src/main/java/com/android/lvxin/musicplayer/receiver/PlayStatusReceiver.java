package com.android.lvxin.musicplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.lvxin.musicplayer.IConstants;
import com.android.lvxin.musicplayer.service.MusicPlayCallback;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: PlayStatusReceiver
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/18 15:22
 */
@Deprecated
public final class PlayStatusReceiver extends BroadcastReceiver {
    public static final Set<MusicPlayCallback> CALLBACKS = new HashSet<>();
    private static final String TAG = "PlayStatusReceiver";
    private static PlayStatusReceiver INSTANCE;

    private MusicPlayCallback mCallback;

    public PlayStatusReceiver() {

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

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (IConstants.BroadcastActions.ACTION_UPDATE_PLAY_MUSIC.equals(action)) {
            Iterator<MusicPlayCallback> it = CALLBACKS.iterator();
            while (it.hasNext()) {
                MusicPlayCallback callback = it.next();
                callback.onUpdateMusicInfo();
            }
        } else if (IConstants.BroadcastActions.ACTION_MUSIC_PREPARED.equals(action)) {
            Iterator<MusicPlayCallback> it = CALLBACKS.iterator();
            while (it.hasNext()) {
                MusicPlayCallback callback = it.next();
                callback.onPrepared();
            }
        } else if (IConstants.BroadcastActions.ACTION_MUSIC_COMPLETION.equals(action)) {
            Iterator<MusicPlayCallback> it = CALLBACKS.iterator();
            while (it.hasNext()) {
                MusicPlayCallback callback = it.next();
                callback.onCompletion();
            }
        } else if (IConstants.BroadcastActions.ACTION_MUSIC_ERROR.equals(action)) {
            Iterator<MusicPlayCallback> it = CALLBACKS.iterator();
            while (it.hasNext()) {
                MusicPlayCallback callback = it.next();
                callback.onError();
            }
        } else if (IConstants.BroadcastActions.ACTION_UPDATE_MUSIC_PLAY_STATUS.equals(action)) {
            boolean isPlaying = intent.getBooleanExtra(IConstants.BroadcastActions.EXTRA_MUSIC_PLAYING_STATUS, false);
            Iterator<MusicPlayCallback> it = CALLBACKS.iterator();
            while (it.hasNext()) {
                MusicPlayCallback callback = it.next();
                callback.onUpdatePlayStatus(isPlaying);
            }
        } else if (IConstants.BroadcastActions.ACTION_UPDATE_PROGRESS.equals(action)) {
            Iterator<MusicPlayCallback> it = CALLBACKS.iterator();
            while (it.hasNext()) {
                MusicPlayCallback callback = it.next();
                callback.onUpdateProgress();
            }
        }
    }
}

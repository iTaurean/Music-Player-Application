package com.android.lvxin.musicplayer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class MusicService extends Service {

    public static final String ACTION_MUSIC_PLAY_PAUSE = "com.cmcc.komectwalking.music.ACTION_MUSIC_PLAY_PAUSE";
    public static final String ACTION_MUSIC_PLAY_NEXT = "com.cmcc.komectwalking.music.ACTION_MUSIC_PLAY_NEXT";
    public static final String ACTION_MUSIC_PLAY_PREVIOUS = "com.cmcc.komectwalking.music.ACTION_MUSIC_PLAY_PREVIOUS";
    private static final String TAG = "MusicService";

    private final IBinder mBinder = new MusicServiceStub(this);
    private MusicPlayControl mMusicPlayControl;

    public MusicService() {
    }

    /**
     * start service with action
     *
     * @param context
     * @param action
     */
    public static void startCommand(Context context, String action) {
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(action);
        context.startService(intent);
    }

    public MusicPlayControl getMusicPlayControl() {
        if (null == mMusicPlayControl) {
            mMusicPlayControl = MusicPlayControl.getInstance(this);
        }
        return mMusicPlayControl;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

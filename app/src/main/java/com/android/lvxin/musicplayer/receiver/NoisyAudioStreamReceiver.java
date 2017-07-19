package com.android.lvxin.musicplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.lvxin.musicplayer.service.MusicService;

/**
 * 来电/耳机拔出时暂停播放
 */
public class NoisyAudioStreamReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MusicService.startCommand(context, MusicService.ACTION_MUSIC_PLAY_PAUSE);
    }
}
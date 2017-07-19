package com.android.lvxin.musicplayer.service;

/**
 * @ClassName: MusicPlayCallback
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/18 08:39
 */
public interface MusicPlayCallback {

    /**
     * 更新播放音乐的信息
     */
    void onUpdateMusicInfo();

    void onPrepared();

    void onCompletion();

    void onError();

    /**
     * 播放进度条更新
     */
    void onUpdateProgress();

    /**
     * 播放状态更新
     *
     * @param isPlaying
     */
    void onUpdatePlayStatus(boolean isPlaying);
}

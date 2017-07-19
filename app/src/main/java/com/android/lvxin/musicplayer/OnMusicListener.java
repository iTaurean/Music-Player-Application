package com.android.lvxin.musicplayer;

/**
 * @ClassName: OnMusicListener
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/14 14:35
 */
public interface OnMusicListener {

    /**
     * 准备就绪
     */
    void onMusicPrepared();

    /**
     * error
     */
    void onMusicError();

    /**
     * 完成播放
     */
    void onMusicCompletion();

    /**
     * 播放进度
     */
    void onMusicProgress();


}

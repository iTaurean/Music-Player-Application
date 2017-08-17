package com.android.lvxin.musicplayer.event;

/**
 * @ClassName: UpdateMusicPlayStatusEvent
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/8/17 09:45
 */
public class UpdateMusicPlayStatusEvent extends BaseEventBus {
    public final boolean isPlaying;

    public UpdateMusicPlayStatusEvent(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}

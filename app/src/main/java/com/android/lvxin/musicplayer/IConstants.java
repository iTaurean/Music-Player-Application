package com.android.lvxin.musicplayer;

/**
 * @ClassName: IConstants
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/17 17:18
 */
public interface IConstants {
    interface RepeatMode {
        int REPEAT_ALL = 23;
        int RANDOM = 22;
        int REPEAT_ONE = 21;
    }

    interface BroadcastActions {
        String ACTION_UPDATE_PROGRESS = "action_update_music_play_progress"; // 更新播放进度
        String ACTION_UPDATE_PLAY_MUSIC = "action_update_play_music"; // 更新播放歌曲信息
        String ACTION_UPDATE_MUSIC_PLAY_STATUS = "action_update_music_play_status"; // 更新音乐播放状态
        String ACTION_MUSIC_PREPARED = "action_music_prepared"; // 音乐准备就绪
        String ACTION_MUSIC_COMPLETION = "action_music_completion"; // 音乐完成播放
        String ACTION_MUSIC_ERROR = "action_music_error"; // 音乐出错

        String EXTRA_MUSIC_PLAYING_STATUS = "extra_music_playing_status"; // 音乐播放状态
    }
}

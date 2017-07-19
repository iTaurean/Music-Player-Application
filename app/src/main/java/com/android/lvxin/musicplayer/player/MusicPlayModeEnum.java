package com.android.lvxin.musicplayer.player;

import com.android.lvxin.musicplayer.R;

/**
 * @ClassName: MusicPlayModeEnum
 * @Description: 定义音乐播放的循环模式的枚举类
 * @Author: lvxin
 * @Date: 10/04/2017 09:42
 */
public enum MusicPlayModeEnum {
    REPEAT_ALL(R.string.music_play_repeat_all, R.mipmap.ic_play_mode_repeat_all, 23), // 顺序
    RANDOM(R.string.music_play_random, R.mipmap.ic_play_mode_random, 22), // 随机
    REPEAT_ONE(R.string.music_play_repeat_one, R.mipmap.ic_play_mode_repeat_one, 21); // 单曲循环

    private static MusicPlayModeEnum[] values = values();
    private int textResId;
    private int iconResId;
    private int code;

    MusicPlayModeEnum(int textResId, int iconResId, int code) {
        this.textResId = textResId;
        this.iconResId = iconResId;
        this.code = code;
    }

    /**
     * @param code
     * @return
     */
    public static MusicPlayModeEnum valueOf(int code) {
        if (RANDOM.getCode() == code) {
            return RANDOM;
        } else if (REPEAT_ONE.getCode() == code) {
            return REPEAT_ONE;
        } else {
            return REPEAT_ALL;
        }
    }

    /**
     * 获取下一个播放模式
     *
     * @return
     */
    public MusicPlayModeEnum next() {
        return values[(this.ordinal() + 1) % values.length];
    }

    /**
     * 获取循环模式的中文解释的资源id
     *
     * @return
     */
    public int getTextResId() {
        return textResId;
    }

    /**
     * 获取循环模式的图标资源id
     *
     * @return
     */
    public int getIconResId() {
        return iconResId;
    }

    /**
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

}

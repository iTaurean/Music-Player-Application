package com.android.lvxin.musicplayer.player;

import com.android.lvxin.musicplayer.data.MusicModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: MusicPlayCache
 * @Description: TODO
 * @Author: lvxin
 * @Date: 09/05/2017 14:41
 */
public class MusicPlayCache {
    private int id;
    private int playMode; // 播放模式
    private int pausePosition; // 暂停的位置
    private List<MusicModel> playList = new ArrayList<>(); // 播放列表

    private long playingMusicId;
    private boolean isPlay = true; // 是否播放：true: 播放状态， false: 暂停状态

    private int duration; // 音乐总时长

    public MusicPlayCache() {
        pausePosition = 0;
        playingMusicId = -1;
        isPlay = true;
        playList.clear();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getPlayingMusicId() {
        return playingMusicId;
    }

    public void setPlayingMusicId(long playingMusicId) {
        this.playingMusicId = playingMusicId;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayMode() {
        if (0 == playMode) {
            playMode = MusicPlayModeEnum.REPEAT_ALL.getCode();
        }
        return playMode;
    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }

    public int getPausePosition() {
        return pausePosition;
    }

    public void setPausePosition(int pausePosition) {
        this.pausePosition = pausePosition;
    }


    public List<MusicModel> getPlayList() {
        return playList;
    }

    public void setPlayList(List<MusicModel> playList) {
        this.playList.clear();
//        removeDuplicateWithOrder(playList);
        this.playList.addAll(playList);
    }

    public void reset() {

    }

    /**
     * 更新播放列表
     *
     * @param playList
     * @return
     */
    public synchronized void replacePlayList(List<MusicModel> playList) {

        if (null != playList && !playList.isEmpty()) {
            setPlayingMusicId(playList.get(0).musicId);
            this.setPlayList(playList);
        }
    }


    /**
     * 更新播放列表
     *
     * @param playList
     * @return
     */
    public synchronized void updatePlayList(List<MusicModel> playList) {

        if (null != playList && !playList.isEmpty()) {
            this.setPlayList(playList);
        }
    }


    /**
     * 添加歌曲到播放列表
     *
     * @param addMusic
     */
    public synchronized void addMusicToPlayList(MusicModel addMusic) {
        if (null != addMusic) {
            playList.add(0, addMusic);
        }

    }

    /**
     * 删除ArrayList中重复元素，保持顺序
     *
     * @param list
     */
    public void removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element)) {
                newList.add(element);
            }
        }
        list.clear();
        list.addAll(newList);
        System.out.println(" remove duplicate " + list);
    }

    /**
     * 获取正在播放的歌曲信息
     *
     * @return
     */
    public MusicModel getPlayingMusic() {
        MusicModel playingMusic = new MusicModel();
        playingMusic.musicId = playingMusicId;
        if (playList.contains(playingMusic)) {
            int position = playList.indexOf(playingMusic);
            return playList.get(position);
        }
        return null;
    }

}

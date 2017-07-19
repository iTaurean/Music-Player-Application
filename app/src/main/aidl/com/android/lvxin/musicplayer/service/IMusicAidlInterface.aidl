// IMusicAidlInterface.aidl
package com.android.lvxin.musicplayer.service;

// Declare any non-default types here with import statements
import com.android.lvxin.musicplayer.data.MusicModel;

interface IMusicAidlInterface {

    void setPlayList(in List<MusicModel> playList);

    List<MusicModel> getPlayList();

    List<MusicModel> getOriginalPlayList();

    void addMusicToList(in MusicModel addMusic);

    void addOrChangeMusicToNext(in MusicModel music);

    void playGivenMusic(in MusicModel music);

    void removeMusic(in MusicModel music);

    MusicModel getPlayingMusic();

    void setPlayStatus(boolean isPlay);

    void clearPlayList();

    void startNew();

    void start();

    void resume();

    void pause();

    void stop();

    void next();

    void prev();

    void seekTo(int position);

    boolean isPlaying();

    void setVolume(float volume);

    void reset();

    void release();

    long getDuration();

    long getPosition();

    int getRepeatMode();

    void setRepeatMode(int repeatMode);
}

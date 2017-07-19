package com.android.lvxin.musicplayer.service;

import android.os.RemoteException;

import com.android.lvxin.musicplayer.data.MusicModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @ClassName: MusicServiceStub
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/14 16:06
 */
public final class MusicServiceStub extends IMusicAidlInterface.Stub {

    private final WeakReference<MusicService> mService;
    private final MusicPlayControl mPlayControl;

    public MusicServiceStub(final MusicService service) {
        mService = new WeakReference<>(service);
        mPlayControl = mService.get().getMusicPlayControl();
    }

    @Override
    public List<MusicModel> getPlayList() throws RemoteException {
        return mPlayControl.getPlayList();
    }

    @Override
    public void setPlayList(List<MusicModel> playList) throws RemoteException {
        mPlayControl.setPlayList(playList);
    }

    @Override
    public List<MusicModel> getOriginalPlayList() throws RemoteException {
        return mPlayControl.getOriginalPlayList();
    }

    @Override
    public void addMusicToList(MusicModel addMusic) throws RemoteException {
        mPlayControl.addMusicToList(addMusic);
    }

    @Override
    public void addOrChangeMusicToNext(MusicModel music) throws RemoteException {
        mPlayControl.addOrChangeMusicToNext(music);
    }

    @Override
    public void playGivenMusic(MusicModel music) throws RemoteException {
        mPlayControl.playGivenMusic(music);
    }

    @Override
    public void removeMusic(MusicModel music) throws RemoteException {
        mPlayControl.removeMusic(music);
    }

    @Override
    public MusicModel getPlayingMusic() throws RemoteException {
        return mPlayControl.getPlayingMusic();
    }

    @Override
    public void clearPlayList() throws RemoteException {
        mPlayControl.clearPlayList();
    }

    @Override
    public void setPlayStatus(boolean isPlay) throws RemoteException {

    }

    @Override
    public void startNew() throws RemoteException {
        mPlayControl.startNew();
    }

    @Override
    public void start() throws RemoteException {
        mPlayControl.start();
    }

    @Override
    public void resume() throws RemoteException {
        mPlayControl.resume();
    }

    @Override
    public void pause() throws RemoteException {
        mPlayControl.pause();
    }

    @Override
    public void stop() throws RemoteException {
        mPlayControl.stop();
    }

    @Override
    public void next() throws RemoteException {
        mPlayControl.next();
    }

    @Override
    public void prev() throws RemoteException {
        mPlayControl.prev();
    }

    @Override
    public void seekTo(int position) throws RemoteException {
        mPlayControl.seekTo(position);
    }

    @Override
    public boolean isPlaying() throws RemoteException {
        return mPlayControl.isPlaying();
    }

    @Override
    public void setVolume(float volume) throws RemoteException {
        mPlayControl.setVolume(volume);
    }

    @Override
    public void reset() throws RemoteException {
        mPlayControl.reset();
    }

    @Override
    public void release() throws RemoteException {
        mPlayControl.release();
    }

    @Override
    public long getDuration() throws RemoteException {
        return mPlayControl.getDuration();
    }

    @Override
    public long getPosition() throws RemoteException {
        return mPlayControl.getPosition();
    }

    @Override
    public int getRepeatMode() throws RemoteException {
        return mPlayControl.getRepeatMode();
    }

    @Override
    public void setRepeatMode(int repeatMode) throws RemoteException {
        mPlayControl.getRepeatMode();
    }
}

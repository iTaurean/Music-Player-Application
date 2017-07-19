package com.android.lvxin.musicplayer.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.lvxin.musicplayer.data.MusicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MusicPlayer
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/14 16:41
 */
public class MusicPlayer {

    public static IMusicAidlInterface mService;

    public static void bindService(Context context, ServiceConnection serviceConnection) {
        MusicPlayer.ServiceBinder binder = new MusicPlayer.ServiceBinder(context.getApplicationContext(), serviceConnection);
        Intent intent = new Intent(context.getApplicationContext(), MusicService.class);
        context.getApplicationContext().bindService(intent, binder, Context.BIND_AUTO_CREATE);
    }

    public static List<MusicModel> getPlayList() {

        try {
            return mService.getPlayList();
        } catch (RemoteException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void setPlayList(List<MusicModel> playList) {
        try {
            mService.setPlayList(playList);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static List<MusicModel> getOriginalPlayList() {
        try {
            return mService.getOriginalPlayList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addMusicToList(MusicModel addMusic) {
        try {
            mService.addMusicToList(addMusic);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void addOrChangeMusicToNext(MusicModel music) {
        try {
            mService.addOrChangeMusicToNext(music);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void playGivenMusic(MusicModel music) {
        try {
            mService.playGivenMusic(music);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void removeMusic(MusicModel music) {
        try {
            mService.removeMusic(music);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static MusicModel getPlayingMusic() {
        try {
            return mService.getPlayingMusic();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void clearPlayList() {
        try {
            mService.clearPlayList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void startNew() {
        try {
            mService.startNew();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void start() {
        try {
            mService.start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void resume() {
        try {
            mService.resume();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void pause() {
        try {
            mService.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        try {
            mService.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void next() {
        try {
            mService.next();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void prev() {
        try {
            mService.prev();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void seekTo(int position) {
        try {
            mService.seekTo(position);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPlaying() {
        try {
            return mService.isPlaying();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setVolume(int volume) {
        try {
            mService.setVolume(volume);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void reset() {
        try {
            mService.reset();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void release() {
        try {
            mService.release();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static long getDuration() {
        try {
            return mService.getDuration();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getPosition() {
        try {
            return mService.getPosition();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getRepeatMode() {
        try {
            return mService.getRepeatMode();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setRepeatMode(int repeatMode) {
        try {
            mService.setRepeatMode(repeatMode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static class ServiceBinder implements ServiceConnection {

        private final Context mContext;
        private final ServiceConnection mServiceConnection;

        public ServiceBinder(final Context context, final ServiceConnection serviceConnection) {
            mContext = context;
            mServiceConnection = serviceConnection;
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = IMusicAidlInterface.Stub.asInterface(iBinder);
            if (null != mServiceConnection) {
                mServiceConnection.onServiceConnected(componentName, iBinder);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            if (null != mServiceConnection) {
                mServiceConnection.onServiceDisconnected(componentName);
            }
            mService = null;
        }
    }
}

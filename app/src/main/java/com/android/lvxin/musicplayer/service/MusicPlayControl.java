package com.android.lvxin.musicplayer.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.android.lvxin.musicplayer.IConstants;
import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.event.MusicCompletedEvent;
import com.android.lvxin.musicplayer.event.MusicErrorEvent;
import com.android.lvxin.musicplayer.event.MusicPreparedEvent;
import com.android.lvxin.musicplayer.event.UpdateMusicInfoEvent;
import com.android.lvxin.musicplayer.event.UpdateMusicPlayStatusEvent;
import com.android.lvxin.musicplayer.event.UpdateMusicProgressEvent;
import com.android.lvxin.musicplayer.player.HiiMediaPlayer;
import com.android.lvxin.musicplayer.player.MusicPlayCache;
import com.android.lvxin.musicplayer.player.MusicPlayModeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: MusicPlayControl
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/14 16:13
 */
public class MusicPlayControl implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, AudioManager.OnAudioFocusChangeListener {

    private static final String TAG = "MusicPlayControl";

    private static final long PROGRESS_UPDATE_TIME = 100L;

    private static MusicPlayControl INSTANCE;

    private HiiMediaPlayer mMediaPlayer;

    private MusicPlayCache mCache;

    private Context mContext;

    private List<MusicModel> mPlayList = new ArrayList<>();

    private int currentPlayIndex = 0;

    private boolean isTrackPrepared = false;

//    private MusicPlayCallback mCallback;

    private Handler mProgressHandler = new Handler();
    private Runnable mProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer.isPlaying()) {
                mCache.setPausePosition(mMediaPlayer.getCurrentPosition());
                mCache.setDuration(mMediaPlayer.getDuration());
                new UpdateMusicProgressEvent().post();
            }
            mProgressHandler.postDelayed(this, PROGRESS_UPDATE_TIME);
        }
    };

//    private IntentFilter mNoisyFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
//    private NoisyAudioStreamReceiver mNoisyReceiver = new NoisyAudioStreamReceiver();
//    private AudioManager mAudioManager;

    private MusicPlayControl(Context context) {
        mContext = context;
//        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        init();
    }

    public static MusicPlayControl getInstance(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new MusicPlayControl(context);
        }

        return INSTANCE;
    }

    private void init() {
        mMediaPlayer = new HiiMediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mCache = new MusicPlayCache();
    }

    /**
     * 获取实际播放的顺序的播放列表
     * 获取的列表顺序跟循环模式有关
     *
     * @return
     */
    public List<MusicModel> getPlayList() {
        if (null != mPlayList) {
            return mPlayList;
        }
        return new ArrayList<>();
    }

    void setPlayList(List<MusicModel> playList) {
        if (null != playList && !playList.isEmpty()) {
            mCache.reset();
            mCache.replacePlayList(playList);
            updateOrderByRepeatMode();
            notifyChange();
        }
    }

    private void updateOrderByRepeatMode() {
        switch (MusicPlayModeEnum.valueOf(mCache.getPlayMode())) {
            case RANDOM:
                mPlayList.clear();
                mPlayList.addAll(randomPlayList(mCache.getPlayList()));
                break;
            default:
                mPlayList.clear();
                mPlayList.addAll(mCache.getPlayList());
                break;
        }
    }

    /**
     * 打乱播放列表
     */
    private List<MusicModel> randomPlayList(List<MusicModel> musicModelList) {
        if (null != musicModelList && !musicModelList.isEmpty()) {
            List<MusicModel> values = new ArrayList<>();
            values.addAll(musicModelList);
            Collections.shuffle(values);
            return values;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 获取原始播放列表，跟播放模式无关
     *
     * @return
     */
    public List<MusicModel> getOriginalPlayList() {
        if (null != mCache && null != mCache.getPlayList()) {
            return mCache.getPlayList();
        }
        return new ArrayList<>();
    }

    public void addMusicToList(MusicModel addMusic) {
        if (null != addMusic) {
            updateOrderByRepeatMode();
            mPlayList.add(0, addMusic);
            mCache.addMusicToPlayList(addMusic);

            notifyChange();
        }
    }

    public void addOrChangeMusicToNext(final MusicModel music) {
        if (null == music) {
            return;
        }
        if (mPlayList.isEmpty()) {
            mPlayList.add(music);
            notifyChange();
            return;
        }
        if (!mPlayList.contains(music)) {
            mPlayList.add(1 + currentPlayIndex, music);
        } else {
            final int moveIndex = mPlayList.indexOf(music);
            final MusicModel moveMusic = mPlayList.get(moveIndex);
            boolean ignoreMove = (mPlayList.size() - 1 == currentPlayIndex && 0 == moveIndex)
                    || (moveIndex == currentPlayIndex + 1);
            if (!ignoreMove) {
                if (mPlayList.size() - 1 == currentPlayIndex) {
                    mPlayList.remove(moveIndex);
                    mPlayList.add(0, moveMusic);
                } else {
                    mPlayList.remove(moveIndex);
                    if (moveIndex < currentPlayIndex) {
                        currentPlayIndex -= 1;
                    }
                    mPlayList.add(1 + currentPlayIndex, moveMusic);
                }
            }
        }
    }

    /**
     * 播放指定的歌曲
     *
     * @param music
     */
    public void playGivenMusic(final MusicModel music) {
        if (null == mPlayList) {
            return;
        }
        // TODO: 2017/8/15 check 有缺陷
        for (int i = 0; i < mPlayList.size(); i++) {
            if (music.equals(mPlayList.get(i))) {
                mCache.setPausePosition(0);
                currentPlayIndex = i;
                if (null != mMediaPlayer) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                    }
                    mMediaPlayer.reset();
                    start();
                }
            }
        }
    }

    /**
     * 删除歌单中的歌曲
     * <p>
     * 情况一. 删除的是当前播放的歌曲
     * 1）有下一首，播放下一首，2）没有歌曲，停止播放
     * 情况二. 删除非当前播放的歌曲
     * 删除歌曲，不影响当前播放
     *
     * @param music
     */
    public void removeMusic(final MusicModel music) {
        if (null == mPlayList || mPlayList.isEmpty()) {
            return;
        }

        MusicModel currentPlayMusic = mPlayList.get(currentPlayIndex);
        if (music.equals(currentPlayMusic)) {
            if (mPlayList.size() > 1) {
                next();
            } else {
                stop();
            }
        }

        if (null != mCache.getPlayList()) {
            removeFromList(mCache.getPlayList(), music);
        }

        if (null != mPlayList) {
            final int removeIndex = mPlayList.indexOf(music);
            if (removeIndex <= currentPlayIndex) {
                currentPlayIndex -= 1;
            }
            removeFromList(mPlayList, music);
        }
    }

    private void removeFromList(List<MusicModel> objList, final MusicModel removeObj) {
        if (null == objList) {
            return;
        }
        for (MusicModel obj : objList) {
            if (removeObj.equals(obj)) {
                objList.remove(obj);
            }
        }
    }

    private void notifyChange() {
        currentPlayIndex = 0;
        if (null != mMediaPlayer) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
        }
        start();
    }

    private void notifyChange(final MusicModel startPlayMusic) {
        if (null == startPlayMusic) {
            notifyChange();
            return;
        }

        // 不管是否跟当前播放的音乐是否同一首，都要重新确认当前播放的index。因为歌单里的歌有增加/删除操作
        for (int i = 0; i < mPlayList.size(); i++) {
            MusicModel obj = mPlayList.get(i);
            if (startPlayMusic.equals(obj)) {
                currentPlayIndex = i;
            }
        }

        if (startPlayMusic.getMusicId() == mCache.getPlayingMusicId()) {
            new UpdateMusicInfoEvent().post();
        } else {
            if (null != mMediaPlayer) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer.reset();
                start();
            }
        }
    }

    public MusicModel getPlayingMusic() {
        if (null == mPlayList || mPlayList.isEmpty()) {
            return null;
        } else {
            if (-1 != currentPlayIndex) {
                return mPlayList.get(currentPlayIndex % mPlayList.size());
            }
            return null;
        }
    }

    public void clearPlayList() {
        if (null != mPlayList) {
            mPlayList.clear();
        }
    }

    public void setPlayStatus(boolean isPlay) {
        mCache.setPlay(isPlay);
    }

    public void start() {
        if (null == mMediaPlayer) {
            return;
        }
        isTrackPrepared = false;
        if (-1 != currentPlayIndex && null != mPlayList && !mPlayList.isEmpty()) {
            MusicModel playingMusic = mPlayList.get(currentPlayIndex);

            if (null != playingMusic) {
                mCache.setPlayingMusicId(playingMusic.getMusicId());
                Log.d(TAG, "start: playing music id=" + mCache.getPlayingMusic() + ", play index=" + currentPlayIndex);
                new UpdateMusicInfoEvent().post();

                mMediaPlayer.setupContentData(mContext, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + playingMusic.getMusicId());
            }
        }
//        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
//        mContext.registerReceiver(mNoisyReceiver, mNoisyFilter);
    }

    public void resume() {
        if (null == mMediaPlayer) {
            return;
        }

        final MusicModel currentPlayingMusic = getPlayingMusic();
        final MusicModel toPlayMusic = mCache.getPlayingMusic();
        if (null == currentPlayingMusic || null == toPlayMusic) {
            return;
        }

        if (isTrackPrepared && currentPlayingMusic.equals(toPlayMusic)) {
            if (0 == mCache.getPausePosition()) {
                if (!mMediaPlayer.isPlaying()) {
                    if (mCache.isPlay()) {
                        mMediaPlayer.start();
                    } else {
                        mMediaPlayer.seekToPause(0);
                    }
                    new UpdateMusicPlayStatusEvent(true).post();
                }
            } else {
                if (!mMediaPlayer.isPlaying()) {
                    if (mCache.isPlay()) {
                        mMediaPlayer.seekToStart(mCache.getPausePosition());
                    } else {
                        mMediaPlayer.seekToPause(mCache.getPausePosition());
                    }
                    new UpdateMusicPlayStatusEvent(true).post();
                }
            }
            mProgressHandler.post(mProgressRunnable);
//            mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
//            mContext.registerReceiver(mNoisyReceiver, mNoisyFilter);
        } else {
            startNew();
        }
    }

    public void startNew() {
        if (null != mMediaPlayer) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            start();
        }
    }

    public void pause() {
        if (null == mMediaPlayer) {
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mCache.setPausePosition(mMediaPlayer.getCurrentPosition());
            mMediaPlayer.pause();
            mProgressHandler.removeCallbacks(mProgressRunnable);

            new UpdateMusicPlayStatusEvent(false).post();
        }
//        mAudioManager.abandonAudioFocus(this);
//        try {
//            if (null != mNoisyReceiver) {
//                mContext.unregisterReceiver(mNoisyReceiver);
//            }
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
    }

    public void next() {
        Log.d(TAG, "next: 下一首");
        setPlayStatus(true);
        mCache.setPausePosition(0);
        getNextPlayIndex();
        if (null == mMediaPlayer) {
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
        start();
    }

    public void prev() {
        Log.d(TAG, "previous: 上一首");
        setPlayStatus(true);
        mCache.setPausePosition(0);
        getPrevPlayIndex();
        if (null == mMediaPlayer) {
            return;
        }
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        start();
    }

    public void stop() {
        if (null == mMediaPlayer) {
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    public void seekTo(int position) {
        mCache.setPausePosition(position);
        mMediaPlayer.seekTo(position);
    }

    public boolean isPlaying() {
        if (null != mMediaPlayer) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    public void setVolume(float volume) {
        if (null != mMediaPlayer) {
            mMediaPlayer.setVolume(volume, volume);
        }
    }

    void reset() {
        if (null != mMediaPlayer) {
            mMediaPlayer.reset();
        }
    }

    void release() {
        isTrackPrepared = false;
        if (null != mMediaPlayer) {
            mMediaPlayer.release();
        }
    }

    long getDuration() {
        if (null != mMediaPlayer && isTrackPrepared) {
            return mMediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    long getPosition() {
        if (null != mMediaPlayer && isTrackPrepared) {
            return mMediaPlayer.getCurrentPosition();
        } else {
            return -1;
        }
    }

    public int getRepeatMode() {
        return mCache.getPlayMode();
    }

    public void setRepeatMode(int repeatMode) {
        mCache.setPlayMode(repeatMode);
        setupPlayMode(repeatMode);
    }

    private void setupPlayMode(final int playMode) {
        if (playMode != IConstants.RepeatMode.REPEAT_ONE) {
            MusicModel currentPlayMusic = mPlayList.get(currentPlayIndex);
            updateOrderByRepeatMode();
            findIndexInNewModeList(currentPlayMusic, mPlayList);
        }
    }

    /**
     * 获取当前播放的index 在 新的播放模式中的index
     *
     * @param playingMusic 当前播放的音乐
     * @param newModeList  新的播放模式下的播放列表
     */
    private void findIndexInNewModeList(MusicModel playingMusic, List<MusicModel> newModeList) {

        for (int i = 0; i < newModeList.size(); i++) {
            MusicModel obj = newModeList.get(i);
            if (obj.getMusicId() == playingMusic.getMusicId()) {
                currentPlayIndex = i;
            }
        }
    }

    /**
     * 获取上一个播放的index
     *
     * @return
     */
    protected int getPrevPlayIndex() {
        if (null != mPlayList && !mPlayList.isEmpty()) {
            currentPlayIndex = (currentPlayIndex > 0) ? currentPlayIndex - 1 : mPlayList.size() - 1;
            return currentPlayIndex;
        }
        return -1;
    }

    /**
     * 获取下一个播放的index
     *
     * @return
     */
    protected int getNextPlayIndex() {
        if (null != mPlayList && !mPlayList.isEmpty()) {
            currentPlayIndex = (currentPlayIndex + 1) % mPlayList.size();
            return currentPlayIndex;
        }
        return -1;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onCompletion: ");
        new MusicCompletedEvent().post();
        mCache.setPausePosition(0);
        if (IConstants.RepeatMode.REPEAT_ONE == mCache.getPlayMode()) {
            mMediaPlayer.start();
            mProgressHandler.post(mProgressRunnable);
            new MusicPreparedEvent().post();
        } else {
            next();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        Log.i(TAG, "onError: what = " + what + ", extra=" + extra);
        new MusicErrorEvent().post();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        isTrackPrepared = true;
        if (null != mCache) {
            if (mCache.isPlay()) {
                mMediaPlayer.seekToStart(mCache.getPausePosition());
                mProgressHandler.post(mProgressRunnable);
            } else {
                mMediaPlayer.pauseAndSeekTo(mCache.getPausePosition());
            }
        } else {
            mMediaPlayer.start();
            setPlayStatus(true);
        }
        new MusicPreparedEvent().post();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                pause();
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                resume();
                break;
            default:
                break;
        }
    }
}

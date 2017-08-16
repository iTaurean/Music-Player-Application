package com.android.lvxin.musicplayer.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.android.lvxin.musicplayer.BR;
import com.android.lvxin.musicplayer.BaseToolBarActivity;
import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.service.MusicPlayer;
import com.android.lvxin.musicplayer.util.ActivityUtils;
import com.android.lvxin.musicplayer.util.ViewModelHolder;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayingActivity extends BaseToolBarActivity implements MusicPlayingNavigation {

    private static final String TAG = "MusicPlayingActivity";

    private static final String MUSIC_PLAYING_TAG = "music_playing_tag";

    private static final String EXTRA_PLAYING_BUNDLE = "extra_playing_bundle";

    private static final String EXTRA_PLAY_LIST = "extra_play_list";

    private MusicPlayingViewModel mViewModel;

    private List<MusicModel> mPlayList;

    public static void start(Context context, List<MusicModel> playList) {
        Intent intent = new Intent(context, MusicPlayingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_PLAY_LIST, (ArrayList<? extends Parcelable>) playList);
        intent.putExtra(EXTRA_PLAYING_BUNDLE, bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing);
        addDefaultCustomView();

        mPlayList = getIntent().getBundleExtra(EXTRA_PLAYING_BUNDLE).getParcelableArrayList(EXTRA_PLAY_LIST);
        MusicPlayingFragment musicsFragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();
        mViewModel.setNavigation(this);
        musicsFragment.setViewModel(mViewModel);
    }


    private MusicPlayingFragment findOrCreateViewFragment() {
        MusicPlayingFragment fragment = (MusicPlayingFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == fragment) {
            fragment = MusicPlayingFragment.newInstance(mPlayList);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
        return fragment;
    }

    private MusicPlayingViewModel findOrCreateViewModel() {
        ViewModelHolder<MusicPlayingViewModel> retainedViewModel = (ViewModelHolder<MusicPlayingViewModel>)
                getSupportFragmentManager().findFragmentByTag(MUSIC_PLAYING_TAG);
        if (null == retainedViewModel || null == retainedViewModel.getViewModel()) {
            MusicPlayingViewModel viewModel = new MusicPlayingViewModel(getApplicationContext());
            retainedViewModel = ViewModelHolder.createContainer(viewModel);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), retainedViewModel, MUSIC_PLAYING_TAG);
        }
        return retainedViewModel.getViewModel();

    }

    @Override
    public void onNextMusic() {
        MusicPlayer.next();
    }

    @Override
    public void onPrevMusic() {
        MusicPlayer.prev();
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        Log.d(TAG, "onPrepared: ");
        mViewModel.notifyPropertyChanged(BR.playing);
    }
}

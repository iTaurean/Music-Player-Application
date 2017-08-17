package com.android.lvxin.musicplayer.player;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.databinding.MusicPlayingInfoFragmentBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MusicPlayingInfoFragment extends Fragment {

    private static final String TAG = "MusicPlayingInfoFragmen";

    private MusicPlayingInfoFragmentBinding mViewBinding;

    private MusicPlayingInfoViewModel mViewModel;

    public MusicPlayingInfoFragment() {
        // Required empty public constructor
    }

    public static MusicPlayingInfoFragment newInstance() {
        MusicPlayingInfoFragment fragment = new MusicPlayingInfoFragment();
        return fragment;
    }

    public void setViewModel(MusicPlayingInfoViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.music_playing_info_fragment, container, false);
        mViewBinding = MusicPlayingInfoFragmentBinding.bind(root);
        mViewBinding.setViewModel(mViewModel);
        return mViewBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }

    @Override
    public void onDestroy() {
        mViewModel.onViewDestroy();
        super.onDestroy();

    }
}

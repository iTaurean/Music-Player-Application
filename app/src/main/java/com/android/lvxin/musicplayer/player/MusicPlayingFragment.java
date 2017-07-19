package com.android.lvxin.musicplayer.player;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.databinding.MusicPlayingFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayingFragment extends Fragment {

    private static final String ARG_PLAY_LIST = "arg_play_list";

    private MusicPlayingFragmentBinding mViewBinding;

    private MusicPlayingViewModel mViewModel;

    private List<MusicModel> mPlayList;


    public MusicPlayingFragment() {
        // Required empty public constructor
    }

    public static MusicPlayingFragment newInstance(List<MusicModel> playList) {
        MusicPlayingFragment fragment = new MusicPlayingFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PLAY_LIST, (ArrayList<? extends Parcelable>) playList);
        fragment.setArguments(args);
        return fragment;
    }

    public void setViewModel(MusicPlayingViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mPlayList = getArguments().getParcelableArrayList(ARG_PLAY_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.music_playing_fragment, container, false);
        mViewBinding = MusicPlayingFragmentBinding.bind(root);
        mViewBinding.setView(this);
        mViewBinding.setViewModel(mViewModel);
        return mViewBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start(mPlayList);
    }

    @Override
    public void onDestroy() {
        mViewModel.onViewDestroy();
        super.onDestroy();
    }
}

package com.android.lvxin.musicplayer.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.databinding.MusicPlayingListFragmentBinding;
import com.android.lvxin.musicplayer.event.UpdateMusicInfoEvent;
import com.android.lvxin.musicplayer.musics.MusicItemViewModel;
import com.android.lvxin.musicplayer.service.MusicPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MusicPlayingListFragment extends Fragment {

    private MusicPlayingListFragmentBinding mViewBinding;

    private MusicPlayingListViewModel mViewModel;

    private PlayListAdapter mAdapter;

    public MusicPlayingListFragment() {
        // Required empty public constructor
    }

    public static MusicPlayingListFragment newInstance() {
        MusicPlayingListFragment fragment = new MusicPlayingListFragment();
        return fragment;
    }

    public void setViewModel(MusicPlayingListViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.music_playing_list_fragment, container, false);
        mViewBinding = MusicPlayingListFragmentBinding.bind(root);
        mViewBinding.setView(this);
        mViewBinding.setViewModel(mViewModel);

        return mViewBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = mViewBinding.playList;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        MusicItemViewModel itemViewModel = new MusicItemViewModel(getContext());
        mAdapter = new PlayListAdapter(itemViewModel, (MusicPlayingActivity) getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * 播放歌曲变化时 更新ui
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateMusicInfo(UpdateMusicInfoEvent event) {
        if (null != mAdapter) {
            mAdapter.updateData(MusicPlayer.getOriginalPlayList());
        }
    }

    @Override
    public void onDestroy() {
        mAdapter.onDestroy();
        super.onDestroy();
    }
}

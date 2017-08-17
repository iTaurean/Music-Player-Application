package com.android.lvxin.musicplayer.player;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lvxin.musicplayer.R;

public class MusicPlayingListFragment extends Fragment {

    public MusicPlayingListFragment() {
        // Required empty public constructor
    }

    public static MusicPlayingListFragment newInstance() {
        MusicPlayingListFragment fragment = new MusicPlayingListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.music_playing_list_fragment, container, false);
    }

}

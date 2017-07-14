package com.android.lvxin.musicplayer.musics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.data.source.MusicsRepository;
import com.android.lvxin.musicplayer.databinding.LocalMusicsFragmentBinding;

public class MusicsFragment extends Fragment {

    private LocalMusicsFragmentBinding mFragmentBinding;
    private MusicsViewModel mViewModel;

    private MusicsAdapter mAdapter;

    public MusicsFragment() {
        // Required empty public constructor
    }

    public static MusicsFragment newInstance() {
        MusicsFragment fragment = new MusicsFragment();
        return fragment;
    }

    public void setViewModel(MusicsViewModel viewModel) {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.local_musics_fragment, container, false);
        mFragmentBinding = LocalMusicsFragmentBinding.bind(root);
        mFragmentBinding.setView(this);
        mFragmentBinding.setViewModel(mViewModel);
        return mFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = mFragmentBinding.musicsList;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        MusicItemViewModel itemViewModel = new MusicItemViewModel(getContext(), MusicsRepository.getInstance(getContext()));
        mAdapter = new MusicsAdapter(itemViewModel, (MusicsActivity) getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        mAdapter.onDestroy();
        super.onDestroy();
    }
}

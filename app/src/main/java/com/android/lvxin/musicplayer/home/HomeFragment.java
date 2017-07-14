package com.android.lvxin.musicplayer.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lvxin.musicplayer.BR;
import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding mViewDataBinding;
    private HomeViewModel mViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public void setViewModel(HomeViewModel viewModel) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        if (null == mViewDataBinding) {
            mViewDataBinding = HomeFragmentBinding.bind(root);
        }
        mViewDataBinding.setView(this);
        mViewDataBinding.setViewModel(mViewModel);

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}

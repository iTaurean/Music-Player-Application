package com.android.lvxin.musicplayer.musics;

import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.SingleRecyclerAdapter;
import com.android.lvxin.musicplayer.data.MusicModel;

/**
 * @ClassName: MusicsAdapter
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/13 08:56
 */
public class MusicsAdapter extends SingleRecyclerAdapter<MusicModel, MusicItemViewModel> {

    private MusicItemNavigation mNavigation;

    public MusicsAdapter(MusicItemViewModel viewModel, MusicItemNavigation navigation) {
        super(R.layout.music_item, viewModel);
        mNavigation = navigation;
        viewModel.setNavigation(mNavigation);

    }

    @Override
    protected void setItemValue(MusicModel obj) {
        mViewModel.setMusic(obj);
    }

    public void onDestroy() {
        mNavigation = null;
    }
}

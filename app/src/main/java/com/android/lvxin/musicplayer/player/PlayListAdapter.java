package com.android.lvxin.musicplayer.player;

import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.SingleRecyclerAdapter;
import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.musics.MusicItemNavigation;
import com.android.lvxin.musicplayer.musics.MusicItemViewModel;

/**
 * @ClassName: PlayListAdapter
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/8/17 14:23
 */
public class PlayListAdapter extends SingleRecyclerAdapter<MusicModel, MusicItemViewModel> {

    private MusicItemNavigation mNavigation;

    public PlayListAdapter(MusicItemViewModel viewModel, MusicItemNavigation navigation) {
        super(R.layout.play_list_item, viewModel);
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

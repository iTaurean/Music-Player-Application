package com.android.lvxin.musicplayer.musics;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.android.lvxin.musicplayer.data.MusicModel;

import java.util.List;

/**
 * @ClassName: MusicListBindings
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/11 15:25
 */
public class MusicListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, List<MusicModel> items) {
        MusicsAdapter adapter = (MusicsAdapter) recyclerView.getAdapter();
        if (null != adapter) {
            adapter.updateData(items);
        }
    }
}

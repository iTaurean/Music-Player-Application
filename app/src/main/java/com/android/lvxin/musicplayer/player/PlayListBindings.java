package com.android.lvxin.musicplayer.player;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.lvxin.musicplayer.R;
import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.service.MusicPlayer;

import java.util.List;

/**
 * @ClassName: PlayListBindings
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/8/17 14:48
 */
public class PlayListBindings {
    @SuppressWarnings("unchecked")
    @BindingAdapter("app:play_list_items")
    public static void setItems(RecyclerView recyclerView, List<MusicModel> items) {
        PlayListAdapter adapter = (PlayListAdapter) recyclerView.getAdapter();
        if (null != adapter) {
            adapter.updateData(items);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:music_name_color")
    public static void setMusicNameColor(TextView textView, final MusicModel obj) {
        int colorResId;
        if (MusicPlayer.getPlayingMusic().equals(obj)) {
            colorResId = R.color.color_869df8;
        } else {
            colorResId = R.color.color_111111;
        }
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), colorResId));
    }
}

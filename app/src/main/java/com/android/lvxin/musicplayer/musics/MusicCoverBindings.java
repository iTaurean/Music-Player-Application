package com.android.lvxin.musicplayer.musics;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.android.lvxin.musicplayer.data.source.local.QueryLocalMusicHelper;
import com.bumptech.glide.Glide;

/**
 * @ClassName: MusicCoverBindings
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/8/4 10:52
 */
public class MusicCoverBindings {

    @BindingAdapter("app:src")
    public static void setImageSrc(ImageView iv, int albumId) {
        Glide.with(iv.getContext()).load(QueryLocalMusicHelper.getAlbumArtUri(albumId)).into(iv);
    }
}

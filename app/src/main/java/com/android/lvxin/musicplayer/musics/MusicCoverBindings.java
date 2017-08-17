package com.android.lvxin.musicplayer.musics;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @ClassName: MusicCoverBindings
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/8/4 10:52
 */
public class MusicCoverBindings {
    static String[] images = new String[]{
            "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383248_3693.jpg",


    };

    @BindingAdapter("app:src")
    public static void setImageSrc(ImageView iv, int albumId) {
        int index = albumId % 10;
        if (images.length > index) {
//        Glide.with(iv.getContext()).load(QueryLocalMusicHelper.getAlbumArtUri(albumId)).into(iv);
            Glide.with(iv.getContext()).load(images[index]).into(iv);
        }
    }
}

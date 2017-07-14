package com.android.lvxin.musicplayer.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;

import com.android.lvxin.musicplayer.data.MusicModel;
import com.android.lvxin.musicplayer.util.IConstants;
import com.android.lvxin.musicplayer.util.PreferencesUtils;
import com.github.promeg.pinyinhelper.Pinyin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: QueryLocalMusicHelper
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/12 09:08
 */
public class QueryLocalMusicHelper {
    public static final int FILTER_SIZE = 1 * 1024 * 1024;// 1MB
    public static final int FILTER_DURATION = 1 * 60 * 1000;// 1分钟
    private static String[] proj_music = new String[]{
            Media._ID, Media.TITLE,
            Media.DATA, Media.ALBUM_ID,
            Media.ALBUM, Media.ARTIST,
            Media.ARTIST_ID, Media.DURATION, Media.SIZE};

    public static List<MusicModel> queryMusics(Context context, int from) {
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver cr = context.getContentResolver();
        StringBuffer select = new StringBuffer("1=1 and title != ''");
        // 查询语句：检索出时长大于1分钟，文件大于1MB的媒体文件
        select.append(" and " + MediaStore.Audio.Media.SIZE + " > " + FILTER_SIZE);
        select.append(" and " + Media.DURATION + " > " + FILTER_DURATION);

        String selectionStatement = "is_music=1 and title != ''";
        final String songSortOrder = PreferencesUtils.getInstance(context).getSongSortOrder();

        switch (from) {
            case IConstants.START_FROM_LOCAL:
                return getMusicListCursor(cr.query(uri, proj_music, select.toString(), null, songSortOrder));
            default:
                return new ArrayList<>();
        }

    }

    public static List<MusicModel> getMusicListCursor(Cursor cursor) {
        List<MusicModel> musicList = new ArrayList<>();
        if (null == cursor) {
            return musicList;
        }

        while (cursor.moveToNext()) {
            MusicModel music = new MusicModel();
            music.musicId = cursor.getLong(cursor.getColumnIndex(Media._ID));
            music.albumId = cursor.getInt(cursor.getColumnIndex(Media.ALBUM_ID));
            music.albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            music.albumData = getAlbumArtUri(music.albumId) + "";
            music.duration = cursor.getInt(cursor.getColumnIndex(Media.DURATION));
            music.musicName = cursor.getString(cursor.getColumnIndex(Media.TITLE));
            music.artist = cursor.getString(cursor.getColumnIndex(Media.ARTIST));
            music.artistId = cursor.getLong(cursor.getColumnIndex(Media.ARTIST_ID));
            String filePath = cursor.getString(cursor.getColumnIndex(Media.DATA));
            music.data = filePath;
            music.folder = filePath.substring(0, filePath.lastIndexOf(File.separator));
            music.size = cursor.getInt(cursor.getColumnIndex(Media.SIZE));
            music.isLocal = true;
            music.sort = Pinyin.toPinyin(music.musicName.charAt(0)).substring(0, 1).toUpperCase();
            musicList.add(music);
        }

        return musicList;

    }

    public static MusicModel getMusicModel(Context context, long musicId) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(Media.EXTERNAL_CONTENT_URI, proj_music, "_id = " + String.valueOf(musicId), null, null);
        if (cursor == null) {
            return null;
        }

        MusicModel music = new MusicModel();
        while (cursor.moveToNext()) {
            music.musicId = cursor.getInt(cursor
                    .getColumnIndex(Media._ID));
            music.albumId = cursor.getInt(cursor
                    .getColumnIndex(Media.ALBUM_ID));
            music.albumName = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            music.albumData = getAlbumArtUri(music.albumId) + "";
            music.duration = cursor.getInt(cursor
                    .getColumnIndex(Media.DURATION));
            music.musicName = cursor.getString(cursor
                    .getColumnIndex(Media.TITLE));
            music.size = cursor.getInt(cursor.getColumnIndex(Media.SIZE));
            music.artist = cursor.getString(cursor
                    .getColumnIndex(Media.ARTIST));
            music.artistId = cursor.getLong(cursor.getColumnIndex(Media.ARTIST_ID));
            String filePath = cursor.getString(cursor
                    .getColumnIndex(Media.DATA));
            music.data = filePath;
            String folderPath = filePath.substring(0,
                    filePath.lastIndexOf(File.separator));
            music.folder = folderPath;
            music.sort = Pinyin.toPinyin(music.musicName.charAt(0)).substring(0, 1).toUpperCase();
        }
        cursor.close();
        return music;
    }

    public static Uri getAlbumArtUri(long albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }
}

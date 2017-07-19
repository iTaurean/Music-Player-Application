package com.android.lvxin.musicplayer.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: MusicModel
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/6 15:35
 */
public class MusicModel implements Parcelable {
    public static final Parcelable.Creator<MusicModel> CREATOR = new Parcelable.Creator<MusicModel>() {
        @Override
        public MusicModel createFromParcel(Parcel source) {
            return new MusicModel(source);
        }

        @Override
        public MusicModel[] newArray(int size) {
            return new MusicModel[size];
        }
    };
    private static final String KEY_MUSIC_ID = "song_id";
    private static final String KEY_ALBUM_ID = "album_id";
    private static final String KEY_ALBUM_NAME = "album_name";
    private static final String KEY_ALBUM_DATA = "album_data";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_MUSIC_NAME = "music_name";
    private static final String KEY_ARTIST = "artist";
    private static final String KEY_ARTIST_ID = "artist_id";
    private static final String KEY_DATA = "data";
    private static final String KEY_FOLDER = "folder";
    private static final String KEY_SIZE = "size";
    private static final String KEY_FAVORITE = "favorite";
    private static final String KEY_LRC = "lrc";
    private static final String KEY_ISLOCAL = "is_local";
    private static final String KEY_SORT = "sort";
    public long musicId = -1;
    public String musicName;
    public String artist;
    public long artistId;
    public int albumId;
    public String albumName;
    public String albumData;
    public int duration;
    public String data;
    public String folder;
    public String lrc;
    public boolean isLocal;
    public String sort;
    public int size;
    /**
     * 0表示没有收藏 1表示收藏
     */
    public int favorite = 0;

    public MusicModel() {
    }

    protected MusicModel(Parcel in) {
        Bundle bundle = in.readBundle();
        this.musicId = bundle.getLong(KEY_MUSIC_ID);
        this.albumId = bundle.getInt(KEY_ALBUM_ID);
        this.albumName = bundle.getString(KEY_ALBUM_NAME);
        this.duration = bundle.getInt(KEY_DURATION);
        this.musicName = bundle.getString(KEY_MUSIC_NAME);
        this.artist = bundle.getString(KEY_ARTIST);
        this.artistId = bundle.getLong(KEY_ARTIST_ID);
        this.data = bundle.getString(KEY_DATA);
        this.folder = bundle.getString(KEY_FOLDER);
        this.albumData = bundle.getString(KEY_ALBUM_DATA);
        this.size = bundle.getInt(KEY_SIZE);
        this.lrc = bundle.getString(KEY_LRC);
        this.isLocal = bundle.getBoolean(KEY_ISLOCAL);
        this.sort = bundle.getString(KEY_SORT);
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumData() {
        return albumData;
    }

    public void setAlbumData(String albumData) {
        this.albumData = albumData;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicModel obj = (MusicModel) o;
        return musicId == obj.musicId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putLong(KEY_MUSIC_ID, this.musicId);
        bundle.putInt(KEY_ALBUM_ID, albumId);
        bundle.putString(KEY_ALBUM_NAME, albumName);
        bundle.putString(KEY_ALBUM_DATA, albumData);
        bundle.putInt(KEY_DURATION, duration);
        bundle.putString(KEY_MUSIC_NAME, musicName);
        bundle.putString(KEY_ARTIST, artist);
        bundle.putLong(KEY_ARTIST_ID, artistId);
        bundle.putString(KEY_DATA, data);
        bundle.putString(KEY_FOLDER, folder);
        bundle.putInt(KEY_SIZE, size);
        bundle.putString(KEY_LRC, lrc);
        bundle.putBoolean(KEY_ISLOCAL, isLocal);
        bundle.putString(KEY_SORT, sort);
        dest.writeBundle(bundle);
    }
}

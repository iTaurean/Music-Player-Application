package com.android.lvxin.musicplayer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

/**
 * @ClassName: PreferencesUtils
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/12 09:15
 */
public final class PreferencesUtils {

    public static final String SONG_SORT_ORDER = "song_sort_order";

    private static PreferencesUtils INSTANCE;

    private static SharedPreferences mPreferences;

    private PreferencesUtils(final Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesUtils getInstance(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new PreferencesUtils(context);
        }

        return INSTANCE;
    }


    public final String getSongSortOrder() {
        return mPreferences.getString(SONG_SORT_ORDER, SortOrder.SongSortOrder.SONG_A_Z);
    }

    public void setSongSortOrder(final String value) {
        setSortOrder(SONG_SORT_ORDER, value);
    }

    private void setSortOrder(final String key, final String value) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                final SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString(key, value);
                editor.apply();

                return null;
            }
        }.execute();
    }


}

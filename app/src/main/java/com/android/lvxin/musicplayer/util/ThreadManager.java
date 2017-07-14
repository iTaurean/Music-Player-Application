package com.android.lvxin.musicplayer.util;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.security.InvalidParameterException;

/**
 * @ClassName: ThreadManager
 * @Description: 对于任务量小，操作不那么频繁的，我们只需要放在一个后台线程中即能满足要求，想法只需要分门别类就可以了，
 * 对于操作数据库的，都使用data线程，对于计算相关的，都使用background线程，这样整个项目只需要维护几个固定后台线程。
 * @Author: lvxin
 * @Date: 2017/7/4 14:41
 */

/**
 * example:
 * ThreadManager.getManager().post(ThreadManager.THREAD_DATA, new Runnable() {
 *
 * @Override public void run() {
 * // TODO: do something
 * }
 * });
 */
public class ThreadManager {
    public static final int THREAD_UI = 0; // 主线程
    public static final int THREAD_BACKGROUND = 1; // background 线程， 用于一般的操作
    public static final int THREAD_DATA = 2; // data线程， 用于数据库操作
    private static final int THREAD_SIZE = 3;
    private static final String[] THREAD_NAME_LIST = {
            "thread_ui", "thread_background", "thread_data"
    };
    private static final Handler[] HANDLER_LIST = new Handler[THREAD_SIZE];

    private ThreadManager() {
        HANDLER_LIST[THREAD_UI] = new Handler();
    }

    public static ThreadManager getManager() {
        return ThreadManagerHolder.threadManager;
    }

    /**
     * 派发任务
     *
     * @param index 线程类型
     */
    public void post(int index, Runnable r) {
        postDelayed(index, r, 0);
    }

    /**
     * 派发任务
     *
     * @param index       线程类型
     * @param r
     * @param delayMillis
     */
    public void postDelayed(int index, Runnable r, long delayMillis) {
        Handler handler = getHandler(index);
        handler.postDelayed(r, delayMillis);
    }

    /**
     * 删除任务
     *
     * @param index
     * @param r
     */
    public void removeCallback(int index, Runnable r) {
        Handler handler = getHandler(index);
        handler.removeCallbacks(r);
    }

    /**
     * @param index
     * @return
     */
    public Handler getHandler(int index) {

        if (0 > index || THREAD_SIZE <= index) {
            throw new InvalidParameterException();
        }
        if (null == HANDLER_LIST[index]) {
            synchronized (HANDLER_LIST) {
                if (null == HANDLER_LIST[index]) {
                    HandlerThread thread = new HandlerThread(THREAD_NAME_LIST[index]);
                    if (THREAD_UI != index) {
                        // 优先级低于主线程
                        thread.setPriority(Thread.MIN_PRIORITY);
                    }
                    thread.start();
                    Handler handler = new Handler(thread.getLooper());
                    HANDLER_LIST[index] = handler;
                }
            }
        }
        return HANDLER_LIST[index];
    }

    /**
     * 判断是否运行在当前线程
     *
     * @param index
     * @return
     */
    public boolean runningOnCurrent(int index) {
        return Looper.myLooper() == getHandler(index).getLooper();
    }


    private static class ThreadManagerHolder {
        private static ThreadManager threadManager = new ThreadManager();
    }

}

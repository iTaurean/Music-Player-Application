package com.android.lvxin.musicplayer.util;

import android.app.Activity;
import android.util.Log;

import java.util.Iterator;
import java.util.Stack;

/**
 * @ClassName: ActivityStack
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017-07-17 09:32:25
 */
public final class ActivityStack {
    private static final String TAG = "ActivityStack";
    private static final ActivityStack INSTANCE = new ActivityStack();
    private final Stack<Activity> stack;

    private ActivityStack() {
        stack = new Stack<Activity>();
    }

    public static ActivityStack getIns() {
        return INSTANCE;
    }

    public int getSize() {
        return stack.size();
    }

    /**
     * @param act
     * @param singleTask
     */
    public void push(Activity act, boolean singleTask) {
        if (act == null) {
            return;
        }
        // if singleTask mode and contain the activity,
        // remove all activities above the activity and the activity
        if (singleTask && contain(act.getClass())) {
            popupAbove(act.getClass());
            stack.pop().finish();
        }
        // then push the activity
        stack.push(act);
    }

    /**
     * @param activity
     */
    public void push(Activity activity) {
        push(activity, false);
    }

    /**
     *
     */
    public void popup() {
        if (stack.isEmpty()) {
            return;
        }

        Activity activity = stack.pop();

        if (null != activity) {
            activity.finish();
        }
    }

    /**
     * @param activity
     */
    public void popup(Activity activity) {
        if (null != activity) {
            stack.removeElement(activity);
        }
    }

    /**
     * @param activityClass
     */
    public void popup(Class<? extends Activity> activityClass) {
        if (null != activityClass) {
            Activity ba;

            for (Iterator<Activity> it = stack.iterator(); it.hasNext(); ) {
                ba = it.next();

                if (null != ba && ba.getClass() == activityClass) {
                    it.remove();
                    ba.finish();
                }
            }
        }
    }

    /**
     * @param curAc
     */
    public void popupWithoutFinish(Activity curAc) {
        if (null != curAc) {
            stack.removeElement(curAc);
        }
    }

    /**
     * @param activity
     */
    public void popupAbove(Activity activity) {
        int size = stack.size();
        int loc = stack.indexOf(activity);

        if (loc == -1) {
            return;
        }

        Activity temp = null;

        for (int i = size - 1; i > loc; i--) {
            temp = stack.remove(i);
            temp.finish();
        }
    }

    /**
     * @param activity
     */
    public void popupAllExcept(Activity activity) {
        final Activity inAc = activity;
        int size = stack.size();
        Activity temp;

        try {
            for (int i = 0; i < size; i++) {
                temp = stack.pop();
                if ((null != temp) && (temp != inAc)) {
                    temp.finish();
                }
            }
        } catch (Exception e) {
        }

    }

    public Activity getCurActivity() {
        if (!stack.isEmpty()) {

            Activity currentActivity = stack.lastElement();
            if (null == currentActivity) {
                popup();
                currentActivity = getCurActivity();
            }
            return currentActivity;
        }
        return null;
    }

    public Activity getActivity(int position) {
        if ((null != stack) && (position < stack.size())) {
            return stack.elementAt(position);
        }
        return null;
    }


    /**
     * @param activityClass
     * @return
     */
    public boolean contain(Class<? extends Activity> activityClass) {
        Activity a;
        for (int i = 0; i < stack.size(); i++) {
            a = getActivity(i);
            if (a != null && a.getClass() == activityClass) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param activityClass
     */
    public void popupAbove(Class<?> activityClass) {
        if (activityClass == null) {
            return;
        }

        Activity a;
        int pos = -1;
        for (int i = 0; i < stack.size(); i++) {
            a = getActivity(i);
            if (a != null && a.getClass() == activityClass) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            return;
        }

        int stackSize = stack.size();
        for (int i = 0; i < stackSize - pos - 1; i++) {
            stack.pop().finish();
        }
    }

    /**
     * 退栈所有页面，finish所有页面
     */
    public void finishAllActivity() {
        if (!stack.isEmpty()) {
            for (Iterator<Activity> it = stack.iterator(); it.hasNext(); ) {
                Activity ba = it.next();
                if (null != ba) {
                    Log.e(TAG, "finishAllActivity: finish " + ba.getClass().getSimpleName());
                    it.remove();
                    ba.finish();
                }
            }
        }
    }
}


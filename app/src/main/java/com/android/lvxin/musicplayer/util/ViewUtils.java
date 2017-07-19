package com.android.lvxin.musicplayer.util;

import android.os.Build;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * The type View utils.
 *
 * @ClassName: ViewUtils
 * @Description: TODO
 * @Author: lvxin
 * @Date: 06 /12/2016 09:33
 */
public class ViewUtils {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static long lastClickTime;

    /**
     * 判断连续点击方法
     *
     * @return boolean
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 添加Fragment到Activity
     *
     * @param fragmentManager the fragment manager
     * @param fragment        the fragment
     * @param fragmentId      the fragment id
     */
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment,
                                             int fragmentId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragmentId, fragment);
        transaction.commit();
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean flymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean miuisetstatusbarlightmode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * Is miui boolean.
     *
     * @return the boolean
     */
    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * Is flyme boolean.
     *
     * @return the boolean
     */
    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * The type Build properties.
     */
    public static class BuildProperties {

        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                properties.load(fis);
                fis.close();
            } catch (IOException e) {
                if (null != fis) {
                    try {
                        fis.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } finally {
                if (null != fis) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * New instance build properties.
         *
         * @return the build properties
         * @throws IOException the io exception
         */
        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }

        /**
         * Contains key boolean.
         *
         * @param key the key
         * @return the boolean
         */
        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        /**
         * Contains value boolean.
         *
         * @param value the value
         * @return the boolean
         */
        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        /**
         * Entry set set.
         *
         * @return the set
         */
        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        /**
         * Gets property.
         *
         * @param name the name
         * @return the property
         */
        public String getProperty(final String name) {
            return properties.getProperty(name);
        }

        /**
         * Gets property.
         *
         * @param name         the name
         * @param defaultValue the default value
         * @return the property
         */
        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        /**
         * Is empty boolean.
         *
         * @return the boolean
         */
        public boolean isEmpty() {
            return properties.isEmpty();
        }

        /**
         * Keys enumeration.
         *
         * @return the enumeration
         */
        public Enumeration<Object> keys() {
            return properties.keys();
        }

        /**
         * Key set set.
         *
         * @return the set
         */
        public Set<Object> keySet() {
            return properties.keySet();
        }

        /**
         * Size int.
         *
         * @return the int
         */
        public int size() {
            return properties.size();
        }

        /**
         * Values collection.
         *
         * @return the collection
         */
        public Collection<Object> values() {
            return properties.values();
        }
    }

}

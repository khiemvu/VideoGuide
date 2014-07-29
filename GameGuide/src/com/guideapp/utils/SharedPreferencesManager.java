package com.guideapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.guideapp.VideoGuideApplication;

/**
 * User: Khiemvx
 * Date: 7/28/14
 */
public class SharedPreferencesManager
{
    public static final String FILE_NAME_SHARE = "RCIcache";
    private SharedPreferences sharedPreferences = null;
    private static String tag = "[SharedPreferencesManager]";
    private SharedPreferences.Editor editor;

    /**
     * @param context context
     */
    public SharedPreferencesManager(Context context)
    {
        if (context == null)
        {
            context = VideoGuideApplication.context;
        }
        sharedPreferences = context.getApplicationContext().getSharedPreferences(FILE_NAME_SHARE, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void clearCache()
    {
        sharedPreferences.edit().clear().commit();
        Log.d(tag, "Clear Cache logout");
    }

    /**
     * @param key ket
     * @return boolean
     */
    public boolean getPreference(String key)
    {
        try
        {
            return sharedPreferences.getBoolean(key, false);
        }
        catch (Exception e)
        {

            try
            {
                editor.putBoolean(key, true);
                editor.commit();
            }
            catch (Exception e2)
            {
                Log.d("Exception", e.getMessage());
            }
            return true;
        }
    }

    /**
     * @param key   key
     * @param value value
     */
    public void setValue(String key, Object value)
    {
        if (value instanceof String)
        {
            editor.putString(key, String.valueOf(value));
            editor.commit();
        }
        if (value instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) value);
            editor.commit();
        }

        if (value instanceof Integer)
        {
            editor.putInt(key, (Integer) value);
            editor.commit();
        }

    }

    /**
     * @param key key
     * @return String
     */
    public String getString(String key)
    {
        return sharedPreferences.getString(key, "");
    }

    /**
     * @param key key
     * @return boolean
     */
    public boolean getBoolean(String key)
    {
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * @param key key
     * @return int
     */
    public int getInt(String key)
    {
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * @param key   key
     * @param value value
     */
    public void setTabShow(String key, int value)
    {
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * @param key key
     * @return int
     */
    public int getTabShow(String key)
    {
        return sharedPreferences.getInt(key, 0);
    }
}


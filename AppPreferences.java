
package com.project;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 *
 * @author MINGWEI ZHONG
 * 
 * My implementation is base on:
 * http://developer.android.com/reference/android/content/SharedPreferences.html
 * 
 */

public class AppPreferences 
{
	//Set the global variable KEY_PREFS_CLIP_BUFFER 
    public static final String KEY_PREFS_CLIP_BUFFER = "clipboardbuffer";
    
    //Set the global variable EY_PREFS_CLIP_BUFFER_SIZE
    public static final String KEY_PREFS_CLIP_BUFFER_SIZE = "clipboardbuffersize";
    
    //5
    private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName(); 
    
    private final SharedPreferences _sharedPrefs;
    
    private final Editor _prefsEditor;

    public AppPreferences(Context context)
    {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public String getClip(int index)
    {
        return _sharedPrefs.getString(KEY_PREFS_CLIP_BUFFER + String.valueOf(index), null);
    }

    public void saveClip(int index, String text)
    {
        _prefsEditor.putString(KEY_PREFS_CLIP_BUFFER + String.valueOf(index), text);
        _prefsEditor.commit();
    }

    public int getClipSize()
    {
        return _sharedPrefs.getInt(KEY_PREFS_CLIP_BUFFER_SIZE, 0);
    }

    public void saveClipSize(int count)
    {
        _prefsEditor.putInt(KEY_PREFS_CLIP_BUFFER_SIZE, count);
        _prefsEditor.commit();
    }
}

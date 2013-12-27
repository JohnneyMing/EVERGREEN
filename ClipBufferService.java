/**
 * Author: MINGWEI ZHONG, JENNEY,DREW
 * All code is original
 */

package com.project;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.os.Build;
import android.os.IBinder;

public class ClipBufferService extends Service 
{

    private ClipboardManager clipboard;

    private AppPreferences _appPrefs;

    ArrayList<ClipData> clipBuffer
            = new ArrayList<ClipData>();
    
    public static boolean reloadBuffer = false;

    /**
     * Constructor which must be implemented by all Service subclasses. Used
     * only by startService() method.
     *
     * @param name
     */
    public ClipBufferService() 
    {
        super();
    }

    /**
     * Specifies what this ClipBufferService should do when bound to an
     * Activity; in this case, nothing.
     */
    @Override
    public IBinder onBind(Intent arg0)
    {
        // Do Nothing
        return null;
    }

    /**
     * Called when ClipBufferService is first instantiated. Should instantiate
     * one-time-only variables.
     *
     * @param intent
     */
    @SuppressLint("NewApi") @Override
    public int onStartCommand(Intent intent, int flag, int startId) 
    {
        super.onStartCommand(intent, 0, 0);

        clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        clipboard.addPrimaryClipChangedListener(new ClipChangedListener());

        return START_STICKY;
    }

    /**
     * Subclass Listener implemented when the primary clip in the
     * ClipboardManager is changed. Should be added to ClipboardManager object.
     *
     * @author 
     *
     */
    @SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public class ClipChangedListener implements OnPrimaryClipChangedListener {

        /**
         * Called when the primary clip in the ClipboardManager changes. Copies
         * primary clip and adds it to the ClipBuffer.
         */

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onPrimaryClipChanged() {
            if (ClipBufferService.reloadBuffer) {
                ClipBufferService.reloadBuffer = false;
                return;
            }
            _appPrefs = new AppPreferences(getApplicationContext());
            
            int bufsize = _appPrefs.getClipSize();
            clipBuffer.clear();
            for (int i = 0; i < bufsize; i++) {
                String clipvalue = _appPrefs.getClip(i);
                if (clipvalue != null) {
                    ClipData clip = ClipData.newPlainText(clipvalue, clipvalue);
                    clipBuffer.add(clip);
                }
            }

            ClipData clip = clipboard.getPrimaryClip();

            if (clip != null) {
                if (clipBuffer.size() < bufsize) 
                {
                    clipBuffer.add(clip);
                } else {
                    clipBuffer.remove(0);
                    clipBuffer.add(clip);
                }
            }
            int count = clipBuffer.size();
            ClipData _clip;
            for (int i = 0; i < count; i++)
            {
                _clip = clipBuffer.get(i);
                _appPrefs.saveClip(i, _clip.getItemAt(0).getText().toString());
            }
        }
    }

    /**
     * for adding chosen string to Clipboard. Creates ClipData object with
     * String clip and sets it to Primary Clip.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addToClipboard(String clip) {
        String[] mimeType = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        CharSequence label = "T:";
        CharSequence charClip = clip;

        ClipData chosenClip = new ClipData(label, mimeType, new ClipData.Item(charClip));

        clipboard.setPrimaryClip(chosenClip);
    }
}

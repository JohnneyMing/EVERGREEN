

package com.project;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
/**
 *
 * @author Mingwei zhong
 */
public class SettingActivity extends Activity {
    
    EditText _buffer_size;
    
    private AppPreferences _appPrefs;
    
    public static SettingActivity mainActivity = null;

    /**
     * Called when the activity is first created.
     * @param icicle
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Intent intent = getIntent();
        
        _appPrefs = new AppPreferences(getApplicationContext());
        
        setContentView(R.layout.setting);
        
        _buffer_size = (EditText) findViewById(R.id.buffer_size);
        
        int bufsize = _appPrefs.getClipSize();
        
        _buffer_size.setText(String.valueOf(bufsize));
        
        Intent myIntent = new Intent(SettingActivity.this, ClipBufferService.class);
        startService(myIntent);

        createNotification();
        mainActivity = this;
    }
    
    private void createNotification() {
        new ClippingTask(getApplicationContext()).execute(0);
    }
    
    public void saveSetting(View view) {
        String value = _buffer_size.getText().toString();
        int newBuffsize = Integer.parseInt(value);
        _appPrefs.saveClipSize(newBuffsize);
    }
    
    public void clearClipping(View view) {
        int bufsize = _appPrefs.getClipSize();
        for (int i = 0; i < bufsize; i++) {
            _appPrefs.saveClip(i, null);
        }
    }
    
}

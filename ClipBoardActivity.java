//Author : Mingwei zhong

package com.project;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;

public class ClipBoardActivity extends Activity 
{

    ClipboardManager mClipboard;

    Spinner mSpinner;
    
    int _mSpinnerSelected = -1;

    private AppPreferences _appPrefs;

    boolean bufferReload = false;

    ArrayList<String> spinnerArray = new ArrayList<String>();

    ArrayList<ClipData> clipBuffer = new ArrayList<ClipData>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        _appPrefs = new AppPreferences(getApplicationContext());

        mClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        setContentView(R.layout.main);

        mSpinner = (Spinner) findViewById(R.id.clip_type);

        int bufsize = _appPrefs.getClipSize();
        for (int i = 0; i < bufsize; i++)
        {
            String clipvalue = _appPrefs.getClip(i);
            
            if (clipvalue != null) 
            {
                ClipData clip = ClipData.newPlainText(clipvalue, clipvalue);
                
                clipBuffer.add(clip);
                
                spinnerArray.add(clipvalue);
            }
        }

        if (spinnerArray.isEmpty()) {
            ClipData clip = mClipboard.getPrimaryClip();
            if (clip != null) {
                clipBuffer.add(clip);
                int count = clipBuffer.size();
                ClipData _clip;
                for (int i = 0; i < count; i++) {
                    _clip = clipBuffer.get(i);
                    _appPrefs.saveClip(i, _clip.getItemAt(0).getText().toString());
                }
            }
        }
        
        ArrayAdapter<String> adapter = 
        		new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
            {
                ClipBufferService.reloadBuffer = true;
                mClipboard.setPrimaryClip(clipBuffer.get(arg2));
                if (ClipBoardActivity.this._mSpinnerSelected == -1) 
                {
                    ClipBoardActivity.this._mSpinnerSelected = arg2;
                } 
                else
                {
                    ClipBoardActivity.this._mSpinnerSelected = arg2;
                    ClipBoardActivity.this.finish();
                    SettingActivity.mainActivity.moveTaskToBack(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                ClipBoardActivity.this.finish();
                SettingActivity.mainActivity.moveTaskToBack(true);
            }
        });

        _mSpinnerSelected = -1;

        mSpinner.performClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

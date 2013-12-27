
package com.project;

import android.content.Context;
import android.os.AsyncTask;

/**
 *
 * @author MINGWEI ZHONG
 */


/**My implementation is base on here:
 * http://developer.android.com/reference/android/os/AsyncTask.html
 
*/
public class ClippingTask extends AsyncTask 
    {
    private NotificationHelper mNotificationHelper;
    public ClippingTask(Context context){
        mNotificationHelper = new NotificationHelper(context);
    }
 
    protected void onPreExecute()
    {
        //Create the notification in the status bar
        mNotificationHelper.createNotification();
    }
 
    @Override
    protected Void doInBackground(Object... params)
    {
        //This is where we would do the actual download stuff
        //for now I'm just going to loop for 10 seconds
        // publishing progress every second
        publishProgress("");
        return null;
    }
    protected void onProgressUpdate(Integer... progress)
    {
        //This method runs on the UI thread, it receives progress updates
        //from the background thread and publishes them to the status bar
        mNotificationHelper.progressUpdate("");
    }
    protected void onPostExecute(Void result)
    {
        //The task is complete, tell the status bar about it
        mNotificationHelper.completed();
    }
}



package com.project;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 *
 * @author Mingwei zhong
 */
public class NotificationHelper {
    private Context mContext;
    private int NOTIFICATION_ID = 1;
    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private PendingIntent mContentIntent;
    private CharSequence mContentTitle;
    public NotificationHelper(Context context)
    {
        mContext = context;
    }
 
    /**
     * Put the notification into the status bar
     */
    public void createNotification() {
        //get the notification manager
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
 
        //create the content which is shown in the notification pull down
        mContentTitle = mContext.getString(R.string.content_title); //Full title of the notification in the pull down
        CharSequence contentText = ""; //Text of the notification in the pull down
 
        //you have to set a PendingIntent on a notification to tell the system what you want it to do when the notification is selected
        //I don't want to use this here so I'm just creating a blank one
        Intent notificationIntent = new Intent(mContext, ClipBoardActivity.class);

        mContentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
 
        //add the additional content and intent to the notification
        
        mNotification = new Notification.Builder(mContext)
         .setContentTitle(mContentTitle)
         .setContentText(contentText)
         .setContentIntent(mContentIntent)
         .setSmallIcon(android.R.drawable.btn_star)
         .build();
 
        //make this notification appear in the 'Ongoing events' section
        mNotification.flags = Notification.FLAG_SHOW_LIGHTS;
 
        //show the notification
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }
 
    /**
     * Receives progress updates from the background task and updates the status bar notification appropriately
     * @param clipvalue
     */
    public void progressUpdate(String clipvalue) {
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }
 
    /**
     * called when the background task is complete, this removes the notification from the status bar.
     * 
     */
    public void completed() {
        //remove the notification from the status bar
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}

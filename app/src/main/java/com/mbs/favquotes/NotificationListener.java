package com.mbs.favquotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.*;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationListener extends NotificationListenerService {

    static final String TAG = "NotificationService";

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.d(TAG,"Service has been started!");
    }
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(TAG,"Notification Posted!");
        String pack = sbn.getPackageName();
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        String id = sbn.getTag();

        Log.i("Package", pack);
        Log.i("Title",title);
        Log.i("Text",text);
        if (id != null){
            Log.i("Key", id);
        }
        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("package", pack);
        msgrcv.putExtra("key", id);
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);

    }

    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG,"Notification Removed");

    }
}
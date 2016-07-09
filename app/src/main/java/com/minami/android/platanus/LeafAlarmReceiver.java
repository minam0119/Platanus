package com.minami.android.platanus;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.activeandroid.query.Select;
import com.minami.android.platanus.Model.Leaf;

/**
 * Created by MINAMI on 16/06/25.
 */
public class LeafAlarmReceiver extends BroadcastReceiver {
    public static final String KEY_LEAF_ID = "key_leaf_id";
    public static final String KEY_LEAF_CONDITION = "key_leaf_condition";

    @Override
    public void onReceive(Context context, Intent intent) {
        Leaf leaf = new Select().from(Leaf.class).where("id = ?", intent.getLongExtra(KEY_LEAF_ID, -1)).executeSingle();
        int condition = intent.getIntExtra(KEY_LEAF_CONDITION, 0);
        Log.d(LeafAlarmReceiver.class.getSimpleName(), "Condition:" + condition);
        if (leaf != null && !leaf.isDone()) {
            Notification notification = leaf.getNotification(context, condition);
            if (notification == null) return;
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(leaf.getNotificationId(condition), notification);
        }
    }
}

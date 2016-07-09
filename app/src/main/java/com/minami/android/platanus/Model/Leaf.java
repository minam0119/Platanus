package com.minami.android.platanus.Model;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.minami.android.platanus.Activity.MainActivity;
import com.minami.android.platanus.LeafAlarmReceiver;
import com.minami.android.platanus.R;

import java.util.Date;

/**
 * Created by MINAMI on 16/02/26.
 */
@Table(name = "Leafs")
public class Leaf extends Model {
    //public static final int CO
    @Column(name = "Name")
    public String name;
    @Column(name = "Tag")
    public String tag;
    @Column(name = "Type")  // type 0=flower 1=leaf
    public int type;
    @Column(name = "Condition") // condition 0=good 1=danger 2=bad
    public int condition = -1;
    @Column(name = "Time")
    public int time;
    @Column(name = "CreatedAt")
    public long createdAt;
    @Column(name = "DoneAt")
    public long doneAt;

    public long getTime() {
        return time * 60 * 1000;
    }

    public boolean isDone() {
        return doneAt != 0;
    }

    public long getLeaveTime() {
        if (isDone()) {
            return doneAt - createdAt;
        } else {
            return new Date().getTime() - createdAt;
        }
    }

    public int updateCondition() {
        // 経過時間
        long time = getTime();
        long leaveTime = getLeaveTime();
        long yellowTime = (int) (time * 0.7);
        if (leaveTime >= time) {
            // 枯れる
            condition = 2;
        } else if (leaveTime >= yellowTime) {
            // 黄色
            condition = 1;
        } else {
            // 普通のやつ
            condition = 0;
        }
        return condition;
    }

    public int getCondition() {
        updateCondition();
        return condition;
    }

    public int getConditionDrawable() {
        updateCondition();
        switch (condition) {
            case 0:
                return R.drawable.leaf_g;
            case 1:
                return R.drawable.leaf_y;
            case 2:
                return R.drawable.leaf_b;
        }
        return R.drawable.leaf_g;
    }

    public void setAlert(Context context, int condition) {
        long time;
        switch (condition) {
            case 0:
                return;
            case 1:
                time = createdAt + (int) (getTime() * 0.7);
                break;
            case 2:
                time = createdAt + getTime();
                break;
            default:
                return;
        }

        Intent intent = new Intent(context, LeafAlarmReceiver.class);
        // レシーバーで判断するため 適当でOK
        intent.putExtra(LeafAlarmReceiver.KEY_LEAF_ID, getId());
        intent.putExtra(LeafAlarmReceiver.KEY_LEAF_CONDITION, condition);
        PendingIntent sender = PendingIntent.getBroadcast(context, getNotificationId(condition), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, sender);
    }

    public Notification getNotification(Context context, int condition) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String text;
        switch (condition) {
            case 0:
                return null;
            case 1:
                text = name + "の葉が黄色くなりました";
                break;
            case 2:
                text = name + "の葉が枯れました";
                break;
            default:
                return null;
        }
        builder.setTicker(text);
        builder.setContentTitle("プラタナス");
        builder.setContentText(text);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        return builder.build();
    }

    public int getNotificationId(int condition) {
        return getId().intValue() + 10000 * condition;
    }

}

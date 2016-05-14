package com.lifeistech.android.platanus.Model;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.NotificationCompat;
import android.widget.ImageView;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.lifeistech.android.platanus.Activity.MainActivity;
import com.lifeistech.android.platanus.MyApplication;
import com.lifeistech.android.platanus.R;

import java.util.Date;

/**
 * Created by MINAMI on 16/02/26.
 */
@Table(name = "Leafs")
public class Leaf extends Model {
    //public static final int CO

    private Activity mainActivity;

    @Column(name = "Name")
    public String name;
    @Column(name = "Tag")
    public String tag;
    @Column(name = "Type")  // type 0=flower 1=leaf
    public int type;
    @Column(name = "Condition") // condition 0=good 1=danger 2=bad
    public int condition;
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
            getNotification();
        } else {
            // 普通のやつ
            condition = 0;
        }
        return condition;
    }

    public int getCondition () {
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

    public void getNotification () {
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (MyApplication.getInstance().getApplicationContext());
        Intent intent = new Intent(MyApplication.getInstance().getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mainActivity,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setTicker(name + "の葉が黄色くなりました");
        builder.setContentTitle("プラタナス");
        builder.setContentText(name + "の葉が黄色くなりました");
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
    }

}

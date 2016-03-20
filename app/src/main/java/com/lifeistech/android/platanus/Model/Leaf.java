package com.lifeistech.android.platanus.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.lifeistech.android.platanus.R;

import java.util.Date;
import java.util.Timer;

/**
 * Created by MINAMI on 16/02/26.
 */
@Table(name = "Leafs")
public class Leaf extends Model {
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

    public long getTime() {
        return time * 60 * 1000;
    }

    public int updateCondition() {
        // 経過時間
        long leaveTime = new Date().getTime() - createdAt;
        long time = getTime();
        long yellowTime = (int) (time / 0.7);
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

    public int getConditionDrawable() {
        switch (condition) {
            case 0:
                return R.drawable.leaf_b;
            case 1:
                return R.drawable.leaf_y;
            case 2:
                return R.drawable.leaf_g;
        }
        return R.drawable.leaf_g;
    }

}

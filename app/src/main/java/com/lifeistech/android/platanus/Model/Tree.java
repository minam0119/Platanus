package com.lifeistech.android.platanus.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by MINAMI on 16/06/25.
 */
@Table(name = "Trees")
public class Tree extends Model {
    @Column(name = "level")
    public int level = 1;
    @Column(name = "count")
    public int count = 1;
}

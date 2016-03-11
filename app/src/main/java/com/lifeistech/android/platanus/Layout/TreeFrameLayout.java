package com.lifeistech.android.platanus.Layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 */
public class TreeFrameLayout extends View {
    private Point[] mTree1Points = {
            new Point(118, 54),
            new Point(91, 120)
    };
    private Point[] mTree2Points = {};
    private Point[] mTree3Points = {};
    private Point[] mTree4Points = {};


    public TreeFrameLayout(Context context) {
        this(context, null);
    }

    public TreeFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {




    }
}

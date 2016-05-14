package com.lifeistech.android.platanus.Layout;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.activeandroid.query.Select;
import com.lifeistech.android.platanus.Model.Leaf;
import com.lifeistech.android.platanus.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class TreeFrameLayout extends FrameLayout implements View.OnClickListener {
    private static final int LEVEL1 = 2;
    private static final int LEVEL2 = 5;
    private static final int LEVEL3 = 10;
    private static final int LEVEL4 = 15;

    private final Point TreeImageSize = new Point(451, 474);
    private final Point LeafImageSize = new Point(34, 34);
    private final Point[] Tree1Points = {
            new Point(168, 351),
            new Point(251, 355)
    };
    private final Point[] Tree2Points = {
            new Point(227, 194),
            new Point(166, 265),
            new Point(172, 316),
            new Point(267, 265),
            new Point(336, 346)

    };
    private final Point[] Tree3Points = {
            new Point(241, 119),
            new Point(202, 154),
            new Point(159, 256),
            new Point(158, 300),
            new Point(126, 300),
            new Point(103, 307),
            new Point(311, 178),
            new Point(331, 269),
            new Point(394, 319),
            new Point(321, 341)

    };
    private final Point[] Tree4Points = {
            new Point(86, 267),
            new Point(93, 185),
            new Point(192, 191),
            new Point(157, 107),
            new Point(180, 101),
            new Point(239, 89),
            new Point(261, 78),
            new Point(289, 107),
            new Point(321, 109),
            new Point(379, 162),
            new Point(390, 203),
            new Point(377, 239),
            new Point(302, 282),
            new Point(425, 235),
            new Point(162, 215)
    };
    private final int[] TreeImages = {
            R.drawable.tree1,
            R.drawable.tree2,
            R.drawable.tree3,
            R.drawable.tree4
    };

    private int level;
    private String selectTag;
    private ImageView treeImageView;
    private Timer timer;
    private Handler handler = new Handler();
    private List<ImageView> leafImages = new ArrayList<>();
    private LeafFrameLayoutListener leafFrameLayoutListener;
    public int treeCount = 1;

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
        // Level
        level = getLevel();
        updateLevels();
        addAllLeafs();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateLeafConditions();
                    }
                });
            }
        }, 500, 1000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void addLeaf(Leaf leaf, boolean isLater) {
        // 後から追加する時
        if (isLater) {
            int updateLevel = getLevel();
            if (updateLevel != level) {
                if (updateLevel == 1) {
                    treeCount ++;
                    leafFrameLayoutListener.onTreeAdded(treeCount, updateLevel);
                }
                level = updateLevel;
                updateLevels();
                addAllLeafs();
                return;
            }
        }
        int size = leafImages.size();
        final Point point;
        switch (level) {
            case 1:
                point = Tree1Points[size % Tree1Points.length];
                break;
            case 2:
                point = Tree2Points[size % Tree2Points.length];
                break;
            case 3:
                point = Tree3Points[size % Tree3Points.length];
                break;
            case 4:
            default:
                point = Tree4Points[size % Tree4Points.length];
                break;
        }
        final ImageView imageView = new ImageView(getContext());
        leaf.updateCondition();
        imageView.setImageResource(leaf.getConditionDrawable());
        imageView.setTag(leaf);
        leafImages.add(imageView);
        // タグが選択されていないか、タグが一緒の時
        if (TextUtils.isEmpty(selectTag) || selectTag.equals(leaf.tag)) {
            imageView.setAlpha(1.0f);
        } else {
            imageView.setAlpha(0.4f);
        }
        addView(imageView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.post(new Runnable() {
            @Override
            public void run() {
                final int x = (getWidth() / TreeImageSize.x) * point.x - imageView.getWidth();
                final int y = (getHeight() / TreeImageSize.y) * point.y - imageView.getHeight();
                ViewCompat.setTranslationX(imageView, x);
                ViewCompat.setTranslationY(imageView, y);
            }
        });
        imageView.setOnClickListener(this);
    }

    // 木のレベルをあげる処理
    protected void updateLevels() {
        removeAllViews();
        // TreeImageView
        treeImageView = new ImageView(getContext());
        treeImageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        treeImageView.setImageResource(TreeImages[level - 1]);
        addView(treeImageView);
        leafImages.clear();
    }

    private void addAllLeafs() {
        final List<Leaf> leafs = new Select().from(Leaf.class).execute();
        for (Leaf leaf : leafs) {
            addLeaf(leaf, false);
        }
    }

    public int getLevel() {
        final int count = new Select().from(Leaf.class).count() % LEVEL4;
        if (count > LEVEL4) {
            return 1;
        } else if (count > LEVEL3) {
            return 4;
        } else if (count > LEVEL2) {
            return 3;
        } else if (count > LEVEL1) {
            return 2;
        }
        return 1;
    }

    public void selectTag(String tag) {
        selectTag = tag;
        for (ImageView imageView : leafImages) {
            Leaf leaf = (Leaf) imageView.getTag();
            if (tag.equals(leaf.tag)) {
                imageView.setAlpha(1.0f);
            } else {
                imageView.setAlpha(0.4f);
            }
        }
    }

    public void clearSelectTag() {
        for (ImageView imageView : leafImages) {
            imageView.setAlpha(1.0f);
        }
    }

    public void updateLeafConditions() {
        for (ImageView imageView : leafImages) {
            Leaf leaf = (Leaf) imageView.getTag();
            imageView.setImageResource(leaf.getConditionDrawable());
        }
    }

    public void setOnLeafClickListener(LeafFrameLayoutListener leafFrameLayoutListener) {
        this.leafFrameLayoutListener = leafFrameLayoutListener;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof ImageView) {
            Leaf leaf = (Leaf) view.getTag();
            if (leafFrameLayoutListener != null) {
                leafFrameLayoutListener.onLeafClick(leaf);
            }
        }
    }

    //更新したい情報を入れる
    public interface LeafFrameLayoutListener {
        void onLeafClick(Leaf leaf);

        void onTreeAdded(int treeCount, int level);

        void onChangeCondition(int condition);
    }

    public int getTreeCount() {
        return treeCount;
    }
}

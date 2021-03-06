package com.minami.android.platanus.Layout;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.minami.android.platanus.Model.Leaf;
import com.minami.android.platanus.Model.Tree;
import com.minami.android.platanus.R;

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

    private String selectTag;
    private ImageView treeImageView;
    private Timer timer;
    private Handler handler = new Handler();
    private List<ImageView> leafImages = new ArrayList<>();
    private LeafFrameLayoutListener leafFrameLayoutListener;
    Tree tree;

    public TreeFrameLayout(Context context) {
        this(context, null);
    }

    public TreeFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        Log.d("initしたら", String.valueOf(tree.level));
    }


    private void init() {
        //Classの継承、インスタンス化
        List<Tree> trees = new Select().from(Tree.class).limit(1).execute();
        if (trees.isEmpty()) {
            tree = new Tree();
        } else {
            tree = trees.get(0);
        }
        // Level
        // tree.level = getLevel();
        updateLevels();
        addAllLeafs();
        Log.d("initの中", String.valueOf(tree.level));
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
            if (updateLevel != tree.level) {
                // 起動した後にレベルが上がる
                boolean isFinished = true;
                for (ImageView imageView : leafImages) {
                    Leaf l = (Leaf) imageView.getTag();
                    if (!l.isDone() && l.getCondition() != 2) {
                        Log.d(TreeFrameLayout.class.getSimpleName(), "condition:" + leaf.condition);
                        isFinished = false;
                        break;
                    }
                }
                if (!isFinished) {
                    List<Leaf> lastLeaf = new Select().from(Leaf.class).orderBy("CreatedAt DESC").limit(1).execute();
                    if (lastLeaf != null && !lastLeaf.isEmpty()) {
                        lastLeaf.get(0).delete();
                    }
                    Toast.makeText(getContext(), "タスクが全て終了していましせん", Toast.LENGTH_LONG).show();
                    return;
                }

                tree.level = updateLevel;
                leafFrameLayoutListener.onTreeAdded(tree);
                // レベルを1にして, 木を新しく追加する
                if (updateLevel == 1) {
                    removeAllLeaf();
                    tree.count++;
                }
                updateLevels();
                addAllLeafs();
                tree.save();
                return;
            }
        }
        int size = leafImages.size();
        final Point point;
        switch (tree.level) {
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
        imageView.setImageResource(leaf.getConditionDrawable());
        imageView.setTag(leaf);
        leafImages.add(imageView);
        // タグが選択されていないか、タグが一緒の時
        if (TextUtils.isEmpty(selectTag) || selectTag.equals(leaf.tag)) {
            imageView.setAlpha(1.0f);
        } else {
            imageView.setAlpha(0.4f);
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);
        addView(imageView, new LayoutParams(getResources().getDimensionPixelSize(R.dimen.leaf_width), ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.post(new Runnable() {
            @Override
            public void run() {
                final int x = (int) (((float) getWidth() / TreeImageSize.x) * point.x) - imageView.getWidth();
                final int y = (int) (((float) getHeight() / TreeImageSize.y) * point.y) - imageView.getHeight();
                Log.d(TreeFrameLayout.class.getSimpleName(), "layout size width:" + getWidth() + ", height:" + getHeight());
                Log.d(TreeFrameLayout.class.getSimpleName(), "translate image x:" + x + ", y:" + y);
                ViewCompat.setTranslationX(imageView, x);
                ViewCompat.setTranslationY(imageView, y);
                if (x > getWidth() / 2) {
                    // 右側に葉っぱがある場合
                    imageView.setRotation(30);
                } else {
                    // 左側に葉っぱがある場合
                    imageView.setRotation(-30);
                }

            }
        });
        imageView.setOnClickListener(this);
    }

    // 木のレベルをあげる処理
    protected void updateLevels() {
        removeAllViews();
        // TreeImageView
        treeImageView = new ImageView(getContext());
        treeImageView.setLayoutParams(new LayoutParams(getResources().getDimensionPixelSize(R.dimen.tree_width),
                ViewGroup.LayoutParams.WRAP_CONTENT));
        treeImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        treeImageView.setAdjustViewBounds(true);
        treeImageView.setImageResource(TreeImages[tree.level - 1]);
        addView(treeImageView);
        leafImages.clear();
    }

    private void addAllLeafs() {
        final List<Leaf> leafs = new Select().from(Leaf.class).execute();
        for (Leaf leaf : leafs) {
            addLeaf(leaf, false);
        }
    }

    public void removeAllLeaf() {
        final List<Leaf> leafs = new Select().from(Leaf.class).orderBy("CreatedAt DESC").execute();
        for (int i = 1; i < leafs.size(); i++) {
            leafs.get(i).delete();
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
            final int lastCondition = leaf.condition;
            final int condition = leaf.updateCondition();
            if (lastCondition != -1 && lastCondition != condition) {

            }
            imageView.setImageResource(leaf.getConditionDrawable());
        }
    }

    public void setOnLeafClickListener(LeafFrameLayoutListener leafFrameLayoutListener) {
        this.leafFrameLayoutListener = leafFrameLayoutListener;
        leafFrameLayoutListener.onChangeCondition(tree);
    }

    public void updateLeaf(Leaf leaf) {
        for (ImageView leafImage : leafImages) {
            Leaf l = (Leaf) leafImage.getTag();
            if (l.getId().equals(leaf.getId())) {
                leafImage.setTag(leaf);
                return;
            }
        }
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

        void onTreeAdded(Tree tree);

        void onChangeCondition(Tree tree);
    }

    public int getTreeCount() {
        return tree.count;
    }
}

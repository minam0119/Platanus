package com.lifeistech.android.platanus.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.lifeistech.android.platanus.Activity.LeafLogActivity;
import com.lifeistech.android.platanus.Layout.TreeFrameLayout;
import com.lifeistech.android.platanus.Model.Leaf;
import com.lifeistech.android.platanus.Model.Tags;
import com.lifeistech.android.platanus.Model.Tree;
import com.lifeistech.android.platanus.R;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements
        LeafDialogFragment.LeafDialogListener,
        AddDialogFragment.AddLeafDialogListener,
        TreeFrameLayout.LeafFrameLayoutListener {

    ImageView btadd, bttag, btlog;
    TreeFrameLayout treeLayout;
    TextView levelTextView, countTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btadd = (ImageView) view.findViewById(R.id.btadd);
        bttag = (ImageView) view.findViewById(R.id.bttag);
        btlog = (ImageView) view.findViewById(R.id.btlog);
        treeLayout = (TreeFrameLayout) view.findViewById(R.id.layoutTree);
        levelTextView = (TextView) view.findViewById(R.id.levelTextView);
        countTextView = (TextView) view.findViewById(R.id.countTextView);
        countTextView = (TextView) view.findViewById(R.id.countTextView);

        treeLayout.setOnLeafClickListener(this);

        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ダイアログ表示
                showAddDialog();
            }
        });

        bttag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder tagListDialog = new AlertDialog.Builder(getActivity());
                tagListDialog.setTitle("タグ");
                tagListDialog.setItems(Tags.Items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //タグごとに分ける 該当しないものの透明度を60%
                        if (i == 0) {
                            treeLayout.clearSelectTag();
                        } else {
                            Log.d("onItemSelected:", String.valueOf(Tags.Items[i]));
                            treeLayout.selectTag(Tags.Items[i]);
                        }
                    }
                });
                tagListDialog.create().show();
            }
        });
        btlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LeafLogActivity.class);
                startActivity(intent);
            }
        });
    }

    //追加ダイアログを表示する関数
    private void showAddDialog() {
        AddDialogFragment addDialogFragment = new AddDialogFragment();
        addDialogFragment.show(getChildFragmentManager(), "dialog");
    }

    private void showLeafDialog(Leaf leaf) {
        LeafDialogFragment leafDialogFragment = LeafDialogFragment.createInstance(leaf);
        leafDialogFragment.show(getChildFragmentManager(), "leafDialog");
    }

    @Override
    public void onClickDone(Leaf leaf) {
        treeLayout.updateLeaf(leaf);
        treeLayout.updateLeafConditions();
    }

    @Override
    public void onClickGiveUp(Leaf leaf) {
        // Leafをセットする
        treeLayout.updateLeaf(leaf);
        treeLayout.updateLeafConditions();
    }

    @Override
    public void onAdded(Leaf leaf) {
        treeLayout.addLeaf(leaf, true);
    }

    @Override
    public void onLeafClick(Leaf leaf) {
        showLeafDialog(leaf);
    }

    @Override
    public void onTreeAdded(Tree tree) {
        levelTextView.setText(String.valueOf(tree.level));
        countTextView.setText(String.valueOf(tree.count));
        Log.d("level",String.valueOf(tree.level));
    }

    @Override
    public void onChangeCondition(Tree tree) {
        levelTextView.setText(String.valueOf(tree.level));
        countTextView.setText(String.valueOf(tree.count));
        Log.d("level",String.valueOf(tree.level));
    }
}

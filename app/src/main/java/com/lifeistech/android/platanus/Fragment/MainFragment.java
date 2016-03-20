package com.lifeistech.android.platanus.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.lifeistech.android.platanus.Activity.LogActivity;
import com.lifeistech.android.platanus.Layout.TreeFrameLayout;
import com.lifeistech.android.platanus.Model.Leaf;
import com.lifeistech.android.platanus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements AddDialogFragment.AddLeafDialogListener, TreeFrameLayout.LeafClickListener {

    ImageView btadd, bttag, btlog;
    public int leafCount, treeCount;
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
        leafCount = 0;
        treeCount = 1;

        levelTextView.setText(String.valueOf(treeLayout.getLevel()));
        countTextView.setText(String.valueOf(treeCount));

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
                final String[] tagItem = new String[]{
                        "全て",
                        "数学",
                        "国語",
                        "英語",
                        "社会",
                        "理科",
                        "課題",
                        "習い事",
                        "開発",
                        "運動",
                        "その他"
                };
                final AlertDialog.Builder tagList = new AlertDialog.Builder(getActivity());
                tagList.setTitle("タグ");
                tagList.setItems(tagItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //タグごとに分ける 該当しないものの透明度を60%
                        tagList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i == 0) {

                                } else if (i == 1) {

                                } else if (i == 2) {

                                } else if (i == 3) {

                                }
                                if (i == 4) {

                                }
                                if (i == 5) {

                                }
                                if (i == 6) {

                                }
                                if (i == 7) {

                                }
                                if (i == 8) {

                                }
                                if (i == 9) {

                                }
                                if (i == 10) {

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }
                });
                tagList.create().show();
            }
        });

        btlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LogActivity.class);
                startActivity(intent);
            }
        });
    }

    //追加ダイアログを表示する関数
    private void showAddDialog() {
        AddDialogFragment addDialogFragment = new AddDialogFragment();
        addDialogFragment.show(getChildFragmentManager(), "dialog");
    }

    private void showLeafDialog() {
        LeafDialogFragment leafDialogFragment = new LeafDialogFragment();
        leafDialogFragment.show(getChildFragmentManager(), "leafDialog");
    }

    @Override
    public void onAdded(Leaf leaf) {
        treeLayout.addLeaf(leaf);
    }

    @Override
    public void onLeafClick(Leaf leaf) {
        LeafDialogFragment leafDialogFragment = LeafDialogFragment.createInstance(leaf);
        leafDialogFragment.show(getChildFragmentManager(), "leafDialog");
    }
}

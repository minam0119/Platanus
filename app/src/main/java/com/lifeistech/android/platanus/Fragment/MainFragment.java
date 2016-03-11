package com.lifeistech.android.platanus.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lifeistech.android.platanus.Activity.LogActivity;
import com.lifeistech.android.platanus.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {
    ImageView btadd,bttag,btlog;
    public int level,leafCount,treeCount;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        btadd = (ImageView)view.findViewById(R.id.btadd);
        bttag = (ImageView)view.findViewById(R.id.bttag);
        btlog = (ImageView)view.findViewById(R.id.btlog);

        level = 1;
        leafCount = 0;
        treeCount = 0;

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
                AlertDialog.Builder tagList = new AlertDialog.Builder(getActivity());
                tagList.setTitle("タグ");
                tagList.setItems(tagItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //タグごとに分ける 該当しないものの透明度を60%
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

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLeafDialog();
            }
        });
    }

    //追加ダイアログを表示する関数
    private void showAddDialog(){
        AddDialogFragment addDialogFragment = new AddDialogFragment();
        addDialogFragment.show(getChildFragmentManager(), "dialog");
    }

    private void showLeafDialog(){
        LeafDialogFragment leafDialogFragment = new LeafDialogFragment();
        leafDialogFragment.show(getChildFragmentManager(), "leafDialog");
    }
}

package com.lifeistech.android.platanus.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.lifeistech.android.platanus.Model.Leaf;
import com.lifeistech.android.platanus.R;

import java.util.Timer;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddDialogFragment extends DialogFragment {
    EditText nameText;
    Spinner tagSpinner, timeSpinner;
    //EditTextの中身と、ダイアログの選択肢をLeafClassの変数と関連付け、MainFragmentに値を受け渡す

    public AddDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_dialog, null);

        nameText = (EditText) view.findViewById(R.id.nameText);
        tagSpinner = (Spinner) view.findViewById(R.id.tagSpinner);
        timeSpinner = (Spinner) view.findViewById(R.id.timeSpinner);

        final ArrayAdapter<String> tagSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        tagSpinnerAdapter.add("なし");
        tagSpinnerAdapter.add("数学");
        tagSpinnerAdapter.add("国語");
        tagSpinnerAdapter.add("英語");
        tagSpinnerAdapter.add("社会");
        tagSpinnerAdapter.add("理科");
        tagSpinnerAdapter.add("課題");
        tagSpinnerAdapter.add("習い事");
        tagSpinnerAdapter.add("開発");
        tagSpinnerAdapter.add("運動");
        tagSpinnerAdapter.add("その他");
        tagSpinner.setAdapter(tagSpinnerAdapter);

        final ArrayAdapter<String> timeSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        timeSpinnerAdapter.add("30");
        timeSpinnerAdapter.add("45");
        timeSpinnerAdapter.add("60");
        timeSpinnerAdapter.add("120");
        timeSpinnerAdapter.add("180");
        timeSpinner.setAdapter(timeSpinnerAdapter);

        view.findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 薄緑のボタンを押した時
                dismiss();
            }
        });
        view.findViewById(R.id.buttonPositive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 濃緑のボタンを押した時
                // TODO 保存処理
                Leaf leaf = new Leaf();
                leaf.name = nameText.getText().toString();
                leaf.tag = (String) tagSpinner.getSelectedItem();
                int time = Integer.valueOf((String) timeSpinner.getSelectedItem());
                leaf.time = time;
                if (time >= 120) {
                    leaf.type = 0;
                } else {
                    leaf.type = 1;
                }
                leaf.condition = 0;

                Log.d("name", leaf.name);
                Log.d("tag", leaf.tag);
                // 保存する処理
                leaf.save();
                Fragment fragment = getParentFragment();
                if (fragment instanceof AddLeafDialogListener) {
                    ((AddLeafDialogListener) fragment).onAdded(leaf);
                }
                dismiss();
            }
        });

        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("");
        return builder.create();*/

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setCancelable(true);
        final Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    public interface AddLeafDialogListener {
        public void onAdded(Leaf leaf);
    }
}

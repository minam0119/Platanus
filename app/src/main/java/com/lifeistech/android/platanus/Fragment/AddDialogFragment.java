package com.lifeistech.android.platanus.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lifeistech.android.platanus.Model.Leaf;
import com.lifeistech.android.platanus.Model.Tags;
import com.lifeistech.android.platanus.R;

import java.util.Date;
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
        tagSpinnerAdapter.addAll(Tags.Items);
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
                Log.d(AddDialogFragment.class.getSimpleName(), "buttonPositive");
                // 濃緑のボタンを押した時
                // TODO 保存処理
                String name = nameText.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getContext(),"名前を入力してください",Toast.LENGTH_LONG).show();
                    return;
                }
                Leaf leaf = new Leaf();
                leaf.name = name;
                leaf.tag = (String) tagSpinner.getSelectedItem();
                int time = Integer.valueOf((String) timeSpinner.getSelectedItem());
                leaf.time = time;
                //leaf.time = 1;
                if (time >= 120) {
                    leaf.type = 0;
                } else {
                    leaf.type = 1;
                }
                leaf.condition = 0;

                leaf.createdAt = new Date().getTime();
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
        void onAdded(Leaf leaf);
    }
}

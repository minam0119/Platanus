package com.lifeistech.android.platanus.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lifeistech.android.platanus.Model.Leaf;
import com.lifeistech.android.platanus.R;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class LeafDialogFragment extends DialogFragment {
    TextView nameText, tagText, timeText;
    private Handler handler = new Handler();
    private Leaf leaf;

    public LeafDialogFragment() {
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leaf_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        leaf = new Leaf();

        nameText = (TextView) view.findViewById(R.id.nameText);
        tagText = (TextView) view.findViewById(R.id.tagText);
        timeText = (TextView) view.findViewById(R.id.timeText);

        nameText.setText(leaf.name);
        tagText.setText(leaf.tag);
        timeText.setText(String.valueOf(leaf.time));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        leaf.updateCondition();
                        // コンディションによって処理変えて

                    }
                });
            }
        }, 500);

        view.findViewById(R.id.buttonNegative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view.findViewById(R.id.buttonPositive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("このToDoを中断しますか?" + "\n" + "※葉は枯葉となります。");
                builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //タイマーを0にする
                        //葉を枯れ葉にする
                        dismiss();
                    }
                });
                builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
            }
        });
    }
}

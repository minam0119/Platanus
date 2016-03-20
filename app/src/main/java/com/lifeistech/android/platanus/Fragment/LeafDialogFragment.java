package com.lifeistech.android.platanus.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.lifeistech.android.platanus.Model.Leaf;
import com.lifeistech.android.platanus.R;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class LeafDialogFragment extends DialogFragment {
    private static final String KEY_ID = "key_id";

    private Leaf leaf;
    ImageView leafImage;
    TextView nameText, tagText, timeText;
    private Handler handler = new Handler();

    public static LeafDialogFragment createInstance(Leaf leaf) {
        LeafDialogFragment fragment = new LeafDialogFragment();
        Bundle args = new Bundle();
        args.putLong(KEY_ID, leaf.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_leaf_dialog, null);
        leafImage = (ImageView) view.findViewById(R.id.imageLeaf);
        nameText = (TextView) view.findViewById(R.id.nameText);
        tagText = (TextView) view.findViewById(R.id.tagText);
        timeText = (TextView) view.findViewById(R.id.timeText);

        Bundle args = getArguments();
        if (args != null) {
            leaf = new Select().from(Leaf.class).where("id = ?", args.getLong(KEY_ID)).executeSingle();
            if (leaf != null) {
                nameText.setText(leaf.name);
                tagText.setText(leaf.tag);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // コンディションによって処理変えて
                                long leaveTime = new Date().getTime() - leaf.createdAt;

                                long time = leaf.getTime();
                                int minute = (int) (time - leaveTime) / (60 * 1000);
                                int second = (int) (time - leaveTime) % (60 * 1000) / 1000;
                                timeText.setText(minute + "分" + second + "秒");
                                leafImage.setImageResource(leaf.getConditionDrawable());
                            }
                        });
                    }
                }, 500, 1000);
            }
        }

        view.findViewById(R.id.buttonNegative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view.findViewById(R.id.buttonPositive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("このToDoを中断しますか?" + "\n" + "※葉は枯葉となります。");
                builder.show();
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
                });*/
            }
        });

        view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setCancelable(true);
        final Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        return dialog;
    }

}

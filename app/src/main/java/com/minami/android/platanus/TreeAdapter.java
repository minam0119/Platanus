package com.minami.android.platanus;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minami.android.platanus.Model.Leaf;

import junit.framework.TestCase;

/**
 * Created by MINAMI on 16/02/28.
 */
public class TreeAdapter extends ArrayAdapter<Leaf> {
    int level;

    public TreeAdapter(Context context) {
        super(context, R.layout.item_tree);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tree, parent, false);
        }
        Leaf item = getItem(position);

        TextView greenTextView = (TextView) convertView.findViewById(R.id.greenTextView);
        TextView yellowTextView = (TextView) convertView.findViewById(R.id.yellowTextView);
        TextView brownTextView = (TextView) convertView.findViewById(R.id.brownTextView);
        TextView fullTextView = (TextView) convertView.findViewById(R.id.fullTextView);
        TextView dangerTextView = (TextView) convertView.findViewById(R.id.dangerTextView);
        TextView diedTextView = (TextView) convertView.findViewById(R.id.diedTextView);
        ImageView treeImageView = (ImageView)convertView.findViewById(R.id.treeImageView);
        TextView levelTextView = (TextView) convertView.findViewById(R.id.levelTextView);
        //それぞれの葉、花の数をTextViewに反映する
        levelTextView.setText(level);


        return convertView;
    }
}

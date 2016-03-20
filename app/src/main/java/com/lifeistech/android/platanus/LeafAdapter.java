package com.lifeistech.android.platanus;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lifeistech.android.platanus.Model.Leaf;

import junit.framework.TestCase;

/**
 * Created by MINAMI on 16/02/28.
 */
public class LeafAdapter extends ArrayAdapter<Leaf> {

    public LeafAdapter(Context context) {
        super(context, R.layout.item_leaf);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_leaf, parent, false);
        }
        Leaf item = getItem(position);

        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView tagTextView = (TextView) convertView.findViewById(R.id.tagTextView);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);



        nameTextView.setText(item.name);
        tagTextView.setText(item.tag);
        timeTextView.setText(String.valueOf(item.time));



        return convertView;
    }
}

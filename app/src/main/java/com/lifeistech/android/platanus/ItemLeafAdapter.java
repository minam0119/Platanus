package com.lifeistech.android.platanus;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by MINAMI on 16/02/28.
 */
public class ItemLeafAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
        view = layoutInflater.inflate(R.layout.item_leaf,parent,false);
    }
}

package com.lifeistech.android.platanus.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lifeistech.android.platanus.ItemLeafAdapter;
import com.lifeistech.android.platanus.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LeafLogFragment extends Fragment {

    public LeafLogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leaf_log, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView)view.findViewById(R.id.listView);
        ItemLeafAdapter adapter = new ItemLeafAdapter(getActivity());

    }
}

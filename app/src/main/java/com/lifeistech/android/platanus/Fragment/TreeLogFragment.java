package com.lifeistech.android.platanus.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.lifeistech.android.platanus.LeafAdapter;
import com.lifeistech.android.platanus.Model.Leaf;
import com.lifeistech.android.platanus.R;
import com.lifeistech.android.platanus.TreeAdapter;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TreeLogFragment extends Fragment {

    public TreeLogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tree_log, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        TreeAdapter adapter = new TreeAdapter(getActivity());
        listView.setAdapter(adapter);

        List<Leaf> leafList = new Select().from(Leaf.class).execute();
        adapter.addAll(leafList);
    }
}

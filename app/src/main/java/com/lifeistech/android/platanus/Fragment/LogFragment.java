package com.lifeistech.android.platanus.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lifeistech.android.platanus.R;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class LogFragment extends Fragment {
    ListView listView;

    public LogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView)view.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        adapter.add("葉の記録");
        adapter.add("木の記録");
    }
}

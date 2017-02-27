package fragmentPackage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import alexis.boulet.channelmessaging.Channel;
import alexis.boulet.channelmessaging.R;

/**
 * Created by bouleta on 27/02/2017.
 */
public class ChannelListFragment extends Fragment {
    private ListView lvchanfrags;
    public String[] listItems = {"item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar", };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_list_fragment,container);
        lvchanfrags = (ListView)v.findViewById(R.id.lvchanfrags);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvchanfrags.setAdapter(new ArrayAdapter(getActivity(), R.layout.channel_list_fragment, listItems));
        lvchanfrags.setOnItemClickListener((MainActivity)getActivity());
    }
}

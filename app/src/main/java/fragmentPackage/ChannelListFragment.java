package fragmentPackage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import alexis.boulet.channelmessaging.Channel;
import alexis.boulet.channelmessaging.ChannelListActivity;
import alexis.boulet.channelmessaging.Channels;
import alexis.boulet.channelmessaging.Downloader;
import alexis.boulet.channelmessaging.MyshatedArrayAdapter;
import alexis.boulet.channelmessaging.OnDownloadCompleteListener;
import alexis.boulet.channelmessaging.R;

/**
 * Created by bouleta on 27/02/2017.
 */
public class ChannelListFragment extends Fragment implements OnDownloadCompleteListener {
    private ListView lvchanfrags;
    public List<Channel> chanels = new ArrayList<Channel>();
    private static final String PREFS_NAME = "MyPrefsFile";
    private TextView txt;
    private ListView lvChans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_list_fragment,container);
        lvchanfrags = (ListView)v.findViewById(R.id.lvchanfrags);
        //lecture des shared prefs
        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("accessToken", "rien");
        //récupération des channels
        Downloader d = new Downloader("http://www.raphaelbischof.fr/messaging/?function=getchannels",token);
        d.addOnDownloadCompleteListener(this);
        d.execute();
        txt = (TextView) v.findViewById(R.id.tv1);
        lvChans = (ListView) v.findViewById(R.id.lvchanfrags);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvchanfrags.setAdapter(new ArrayAdapter(getActivity(), R.layout.channel_list_fragment, chanels));
        lvchanfrags.setOnItemClickListener((ChannelListActivity)getActivity());
    }

    @Override
    public void onDownloadComplete(String content, int requestCode) {
        Gson gson = new Gson();
        Channels chans = gson.fromJson(content,Channels.class);
        for(Channel chan : chans.getChannels())
        {
            chanels.add(chan);
        }
        lvChans.setAdapter(new MyshatedArrayAdapter(this.getActivity().getApplicationContext(), chanels));
    }
}

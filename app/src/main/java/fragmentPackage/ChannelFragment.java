package fragmentPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import alexis.boulet.channelmessaging.R;

/**
 * Created by bouleta on 27/02/2017.
 */
public class ChannelFragment extends Fragment {
    //private TextView tvExample;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_fragment,container);
        //tvExample = (TextView)v.findViewById(R.id.tvMessage);
        return v;
    }
    public void fillTextView(String listItem) {

    }
}

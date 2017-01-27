package alexis.boulet.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bouleta on 27/01/2017.
 */
public class MyshatedArrayAdapter extends ArrayAdapter<Channel> {
    private final Context context;
    private final List<Channel> values;


    public MyshatedArrayAdapter(Context context, List<Channel> values) {
        super(context, R.layout.layout_simple_chan, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_simple_chan, parent, false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvUser = (TextView) rowView.findViewById(R.id.tvUsers);
        Channel chan = values.get(position);
            tvName.setText(chan.getName());
            tvUser.setText("nombre d'utilisateurs connectés : " + chan.getConnectedusers());
        return rowView;
    }
}

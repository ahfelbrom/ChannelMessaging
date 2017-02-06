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
public class MySecondArrayAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private final List<Message> values;


    public MySecondArrayAdapter(Context context, List<Message> values) {
        super(context, R.layout.layout_lane, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_chan_mess, parent, false);
        TextView tvUserId = (TextView) rowView.findViewById(R.id.tvUserId);
        TextView tvMessage = (TextView) rowView.findViewById(R.id.tvMessage);
        Message mess = values.get(position);
        tvUserId.setText(""+mess.getUsername());
        tvMessage.setText("" + mess.getMessage());
        return rowView;
    }
}

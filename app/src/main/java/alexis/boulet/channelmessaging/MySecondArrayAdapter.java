package alexis.boulet.channelmessaging;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
        tvUserId.setText("" + mess.getUsername());
        tvMessage.setText("" + mess.getMessage());
        /*File picture = new File(Environment.getExternalStorageDirectory() + "/photos_profile/" + "profile_photo.txt");
        if (!picture.exists()) {
            File directoryProfile = new File(Environment.getExternalStorageDirectory() + "/photos_profile/");
            directoryProfile.mkdirs();
            File profilepicture = new File(directoryProfile, "profile_photo.txt");
            FileOutputStream fileStream = null;
            try {
                fileStream = new FileOutputStream(profilepicture);
                PrintWriter pw = new PrintWriter(fileStream);
                pw.println(mess.getImageUrl()); // l'url a donner pour l'image
                pw.flush();
                pw.close();
                fileStream.close();
            } catch (FileNotFoundException e) {
                //TODO HANDLER
            } catch (IOException e) {
                //TODO HANDLER
            }
        }
        else {
            BitmapFactory.decodeFile(picture.getPath());
        }*/
            //if exists :
            //BitmapFactory.decodeFile()
            //else :
            // launch downloadFromUrl pui BitmapFactory.decodeFile()
        return rowView;
    }
}

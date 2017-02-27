package fragmentPackage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import alexis.boulet.channelmessaging.Downloader;
import alexis.boulet.channelmessaging.Message;
import alexis.boulet.channelmessaging.Messages;
import alexis.boulet.channelmessaging.MySecondArrayAdapter;
import alexis.boulet.channelmessaging.OnDownloadCompleteListener;
import alexis.boulet.channelmessaging.R;

/**
 * Created by bouleta on 27/02/2017.
 */
public class MessageFragment extends Fragment implements OnDownloadCompleteListener, View.OnClickListener {
    private ListView lvMessage;
    private EditText etMessage;
    private Button btnEnvoi;
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_fragment,container);
        lvMessage = (ListView) v.findViewById(R.id.lvMessageReceve);
        etMessage = (EditText) v.findViewById(R.id.etTextEnvoi);
        btnEnvoi = (Button) v.findViewById(R.id.btnEnvoi);
        btnEnvoi.setOnClickListener(this);
        fillTextView();
        return v;
    }
    public void fillTextView() {
        final OnDownloadCompleteListener thiss = this;
        final int chanID = this.getActivity().getIntent().getIntExtra("chanID", 0);
        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        final String token = settings.getString("accessToken", "rien");
        Downloader d = new Downloader(" http://www.raphaelbischof.fr/messaging/?function=getmessages",token, chanID);
        d.addOnDownloadCompleteListener(this);
        d.execute();
        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                Downloader d = new Downloader(" http://www.raphaelbischof.fr/messaging/?function=getmessages",token, chanID);
                d.addOnDownloadCompleteListener(thiss);
                d.execute();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);
    }

    @Override
    public void onDownloadComplete(String content, int requestCode) {
        //Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
        ArrayList<Message> messageList = new ArrayList<>();
        Gson gson = new Gson();
        Messages messs = gson.fromJson(content, Messages.class);
        if(requestCode == 2)
            etMessage.setText("");
        else
        {
            for (Message message : messs.getMessagesList()) {
                messageList.add(message);
            }
            lvMessage.setAdapter(new MySecondArrayAdapter(this.getActivity().getApplicationContext(), messageList));
        }
    }

    @Override
    public void onClick(View v) {
        String message = etMessage.getText().toString();
        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("accessToken", "rien");
        int chanID = this.getActivity().getIntent().getIntExtra("chanID", 0);
        Downloader d = new Downloader("http://www.raphaelbischof.fr/messaging/?function=sendmessage",token, chanID, message);
        d.addOnDownloadCompleteListener(this);
        d.execute();
    }
}

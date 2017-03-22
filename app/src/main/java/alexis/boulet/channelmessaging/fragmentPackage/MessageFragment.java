package alexis.boulet.channelmessaging.fragmentPackage;

import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    private Button btnSon;

    public int getChanId() {
        return chanId;
    }

    private int chanId = -1;
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_fragment,container);
        lvMessage = (ListView) v.findViewById(R.id.lvMessageReceve);
        etMessage = (EditText) v.findViewById(R.id.etTextEnvoi);
        btnEnvoi = (Button) v.findViewById(R.id.btnEnvoi);
        btnSon = (Button) v.findViewById(R.id.btnSon);
        btnSon.setOnClickListener(this);
        btnEnvoi.setOnClickListener(this);
        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        final String token = settings.getString("accessToken", "rien");
        final OnDownloadCompleteListener thiss = this;
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                if(isInLayout()) {
                    if(chanId != -1) {
                        Downloader d = new Downloader(" http://www.raphaelbischof.fr/messaging/?function=getmessages",token, chanId);
                        d.addOnDownloadCompleteListener(thiss);
                        d.execute();
                    }
                    handler.postDelayed(this, 1000);
                }

            }
        };
        handler.postDelayed(r, 1000);
        return v;
    }
    public void fillTextView(int chanId) {
        if(chanId != -1)
        {
            this.chanId = chanId;
        }

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
            if(isInLayout()) {
                if(messs.getMessagesList() != null) {
                    for (Message message : messs.getMessagesList()) {
                        messageList.add(message);
                    }
                    if(!messageList.isEmpty()) {
                        lvMessage.setAdapter(new MySecondArrayAdapter(this.getActivity().getApplicationContext(), messageList));
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnEnvoi) {
            String message = etMessage.getText().toString();
            SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
            String token = settings.getString("accessToken", "rien");
            Downloader d = new Downloader("http://www.raphaelbischof.fr/messaging/?function=sendmessage",token, chanId, message);
            d.addOnDownloadCompleteListener(this);
            d.execute();
        }
        else if(v == btnSon){
            //Toast.makeText(this.getContext(), "ça mache", Toast.LENGTH_SHORT).show();
            confirmFireMissiles();
        }

    }

    public void confirmFireMissiles() {
        MyDialogFragment newFragment = new MyDialogFragment();
        newFragment.setChanID(chanId);
        newFragment.show(getActivity().getSupportFragmentManager(), "missiles");
    }


}

package alexis.boulet.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by bouleta on 23/01/2017.
 */
public class ChannelListActivity extends Activity implements OnDownloadCompleteListener {

    private TextView txt;
    private ListView lvChans;
    private ArrayList<String> chanels = new ArrayList<String>();
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channelist);
        //lecture des shared prefs
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("accessToken", "rien");
        //récupération des channels
        Downloader d = new Downloader("http://www.raphaelbischof.fr/messaging/?function=getchannels",token);
        d.addOnDownloadCompleteListener(this);
        d.execute();
        txt = (TextView) findViewById(R.id.tv1);
        lvChans = (ListView) findViewById(R.id.lvchans);
        ArrayList<Map<String, String>> list = buildData();
        String[] from = { " name", " connectedusers" }; 
        int[] to = { R.id.textView, R.id.textView2 };
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.layout_simple_chan, from, to);
        lvChans.setAdapter(adapter);
    }

    private ArrayList<Map<String,String>> buildData() {
        return null;
    }


    private void activiteTerminee(boolean resultat, boolean etatHyperactif){
        if (resultat){
            Intent fermetureMonActivite = new Intent();
            if (etatHyperactif){
                fermetureMonActivite.putExtra("etat","hyperactif");
            }else{
                fermetureMonActivite.putExtra("etat","calme");
            }
            setResult(RESULT_OK,fermetureMonActivite);
        }else{
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public void onDownloadComplete(String content) {
        Gson gson = new Gson();
        Channels chans = gson.fromJson(content, Channels.class);
        for(Channel chan:chans.getChannels()) {
            chanels.add(chan.getName());
        }
        txt.setText(chans.toString());
    }
}

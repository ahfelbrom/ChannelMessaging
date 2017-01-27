package alexis.boulet.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bouleta on 23/01/2017.
 */
public class ChannelListActivity extends Activity implements OnDownloadCompleteListener, AdapterView.OnItemClickListener {

    private TextView txt;
    private ListView lvChans;
    private List<Channel> chanels = new ArrayList<Channel>();
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
        //ArrayAdapter ça marche mais une seule donnée affichée ...
        //lvChans.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_simple_chan,R.id.textView, chanels));
        //expérimental (recréer un arrayadapter) a faire quand t'auras le temps ou l'energie parce que ça te soule
        lvChans.setAdapter(new MyshatedArrayAdapter(this.getApplicationContext(), chanels));
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
        Channel chan = gson.fromJson(content, Channel.class);
            chanels.add(chan);
            txt.append(chan.toString() + "\n");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast toast = Toast.makeText(this.getApplicationContext(), "t tro for", Toast.LENGTH_LONG);
        toast.show();
    }
}

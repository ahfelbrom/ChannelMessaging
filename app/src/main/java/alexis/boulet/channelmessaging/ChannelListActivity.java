package alexis.boulet.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
        lvChans.setOnItemClickListener(this);
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
    public void onDownloadComplete(String content, int requestCode) {
        Gson gson = new Gson();
        Channels chans = gson.fromJson(content,Channels.class);
        for(Channel chan : chans.getChannels())
        {
            chanels.add(chan);
        }
        lvChans.setAdapter(new MyshatedArrayAdapter(this.getApplicationContext(), chanels));
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getApplicationContext(), ChannelActivity.class);
        //Toast.makeText(getApplicationContext(), chanels.get(position).toString(),Toast.LENGTH_SHORT).show();
        Channel chan = chanels.get(position);
        //Toast.makeText(getApplicationContext(), chan.getChannelID()+"" ,Toast.LENGTH_SHORT).show();
        myIntent.putExtra("chanID", chan.getChannelID());
        startActivityForResult(myIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int
            resultCode, Intent data) {
        if (requestCode == 1)
        {
            if (resultCode==RESULT_OK)
            {
                String s = data.getStringExtra("etat");
            }
        }
    }
}

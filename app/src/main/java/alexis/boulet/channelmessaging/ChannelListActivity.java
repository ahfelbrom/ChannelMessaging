package alexis.boulet.channelmessaging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alexis.boulet.channelmessaging.fragmentPackage.MessageFragment;
import alexis.boulet.channelmessaging.fragmentPackage.ChannelListFragment;

/**
 * Created by bouleta on 23/01/2017.
 */
public class ChannelListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TextView txt;
    private ListView lvChans;
    private List<Channel> chanels = new ArrayList<Channel>();
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channelist);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChannelListFragment fragA = (ChannelListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentA_ID);
        MessageFragment fragB = (MessageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentB_ID);
        Channel chan = fragA.chanels.get(position);
        if(fragB == null|| !fragB.isInLayout()){
            Intent i = new Intent(getApplicationContext(),ChannelActivity.class);
            i.putExtra("chanID", chan.getChannelID());
            startActivity(i);
        } else {
            fragB.fillTextView(chan.getChannelID());
        }
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

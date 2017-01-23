package alexis.boulet.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by bouleta on 23/01/2017.
 */
public class ChannelListActivity extends Activity{

    private TextView txt;
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channelist);
        //lecture des shared prefs
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("accessToken", "rien");
        //

        txt = (TextView) findViewById(R.id.tv1);
        txt.setText(token);
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
}

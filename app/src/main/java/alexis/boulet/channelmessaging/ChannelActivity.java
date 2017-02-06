package alexis.boulet.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ChannelActivity extends Activity implements OnDownloadCompleteListener, View.OnClickListener {

    private ListView lvMessage;
    private EditText etMessage;
    private Button btnEnvoi;
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        final OnDownloadCompleteListener thiss = this;
        final int chanID = getIntent().getIntExtra("chanID", 0);
        lvMessage = (ListView) findViewById(R.id.lvMessageReceve);
        etMessage = (EditText) findViewById(R.id.etTextEnvoi);
        btnEnvoi = (Button) findViewById(R.id.btnEnvoi);
        btnEnvoi.setOnClickListener(this);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        final String token = settings.getString("accessToken", "rien");
        Downloader d = new Downloader(" http://www.raphaelbischof.fr/messaging/?function=getmessages",token, chanID);
        d.addOnDownloadCompleteListener(this);
        d.execute();
        final Handler  handler = new Handler();

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

    private void
    activiteTerminee(boolean resultat, boolean etatHyperactif) {
        if (resultat) {
            Intent fermetureMonActivite = new Intent();
            if (etatHyperactif) {
                fermetureMonActivite.putExtra("etat", "hyperactif");
            } else {
                fermetureMonActivite.putExtra("etat", "calme");
            }
            setResult(RESULT_OK, fermetureMonActivite);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();

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
            lvMessage.setAdapter(new MySecondArrayAdapter(getApplicationContext(), messageList));
        }
    }

    @Override
    public void onClick(View v) {
        String message = etMessage.getText().toString();
        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        // url = http://www.raphaelbischof.fr/messaging/?function=sendmessage
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("accessToken", "rien");
        int chanID = getIntent().getIntExtra("chanID", 0);
        Downloader d = new Downloader("http://www.raphaelbischof.fr/messaging/?function=sendmessage",token, chanID, message);
        d.addOnDownloadCompleteListener(this);
        d.execute();
    }
}

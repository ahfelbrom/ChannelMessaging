package alexis.boulet.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ChannelActivity extends AppCompatActivity implements OnDownloadCompleteListener, View.OnClickListener {

    private ListView lvMessage;
    private EditText etMessage;
    private Button btnEnvoi;
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

    }

    private void activiteTerminee(boolean resultat, boolean etatHyperactif) {
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
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("accessToken", "rien");
        int chanID = getIntent().getIntExtra("chanID", 0);
        Downloader d = new Downloader("http://www.raphaelbischof.fr/messaging/?function=sendmessage",token, chanID, message);
        d.addOnDownloadCompleteListener(this);
        d.execute();
    }
}

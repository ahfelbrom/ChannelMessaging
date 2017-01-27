package alexis.boulet.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class loginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {
    private EditText etIdentifiant;
    private EditText etMdp;
    private Button btnValider;
    private static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etIdentifiant = (EditText) findViewById(R.id.etIdentifiant);
        etMdp = (EditText) findViewById(R.id.etMdp);
        btnValider = (Button) findViewById(R.id.btnValider);
        btnValider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Downloader d = new Downloader(etIdentifiant.getText().toString(),etMdp.getText().toString(),"http://www.raphaelbischof.fr/messaging/?function=connect");
        d.addOnDownloadCompleteListener(this);
        d.execute();
    }

    @Override
    public void onDownloadComplete(String content) {
        //désérialisation de json
        Gson gson = new Gson();
        Response response = gson.fromJson(content, Response.class);
        if(response.getAccessToken() != null) {
            //stockage dans les SharedPrefs
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("accessToken", response.getAccessToken());
            // Commit the edits!
            editor.commit();
            /*Toast toast = Toast.makeText(this.getApplicationContext(), response.toString(), Toast.LENGTH_LONG);
            toast.show();*/
            Intent myIntent = new Intent(this.getApplicationContext(),ChannelListActivity.class);
            startActivityForResult(myIntent,0);
        } else {
            Toast toast = Toast.makeText(this.getApplicationContext(), "informations de connexions erronées", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                String acces = data.getStringExtra("etat");
            }
        }
    }
}

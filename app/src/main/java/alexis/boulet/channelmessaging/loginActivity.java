package alexis.boulet.channelmessaging;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class loginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {
    private TextView tvidentifiant;
    private TextView tvmdp;
    private EditText etIdentifiant;
    private EditText etMdp;
    private Button btnValider;
    private String result;
    private static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvidentifiant = (TextView) findViewById(R.id.tvidentifiant);
        tvmdp = (TextView) findViewById(R.id.tvmdp);
        etIdentifiant = (EditText) findViewById(R.id.etIdentifiant);
        etMdp = (EditText) findViewById(R.id.etMdp);
        btnValider = (Button) findViewById(R.id.btnValider);
        btnValider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Downloader d = new Downloader();
        d.addOnDownloadCompleteListener(this);
        d.execute();

    }

    @Override
    public void onDownloadComplete(String content) {
        //désérialisation de json
        Gson gson = new Gson();
        Response response = gson.fromJson(result, Response.class);

        //stockage dans les ShardePrefs
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("accessToken", response.getAccessToken());

        // Commit the edits!
        editor.commit();
    }
}

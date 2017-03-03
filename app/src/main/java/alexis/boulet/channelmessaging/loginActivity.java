package alexis.boulet.channelmessaging;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

public class loginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {
    private EditText etIdentifiant;
    private EditText etMdp;
    private Button btnValider;
    protected ImageView mIvLogo;
    private Handler mHandlerTada;
    private int mShortDelay;
    private TextView tvTrans;
    private static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etIdentifiant = (EditText) findViewById(R.id.etIdentifiant);
        etMdp = (EditText) findViewById(R.id.etMdp);
        btnValider = (Button) findViewById(R.id.btnValider);
        mIvLogo = (ImageView) findViewById(R.id.IvLogo);
        tvTrans = (TextView) findViewById(R.id.tvTrans);
        mHandlerTada = new Handler(); // android.os.handler
        mShortDelay = 4000; //milliseconds

        mHandlerTada.postDelayed(new Runnable(){
            public void run(){
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(mIvLogo);
                mHandlerTada.postDelayed(this, mShortDelay);
            }
        }, mShortDelay);
        btnValider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Animation animSlideLeft = AnimationUtils.loadAnimation(this, R.anim.slide_left);
        tvTrans.startAnimation(animSlideLeft);
        Downloader d = new Downloader(etIdentifiant.getText().toString(),etMdp.getText().toString(),"http://www.raphaelbischof.fr/messaging/?function=connect");
        d.addOnDownloadCompleteListener(this);
        d.execute();
    }

    @Override
    public void onDownloadComplete(String content, int requestCode) {
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
            Intent loginIntent = new Intent(this, ChannelListActivity.class);
            startActivity(loginIntent, ActivityOptions.makeSceneTransitionAnimation(this, mIvLogo, "logo").toBundle());
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

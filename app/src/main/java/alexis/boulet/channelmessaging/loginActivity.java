package alexis.boulet.channelmessaging;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ScrollingView;
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
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Random;

public class loginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {
    private EditText etIdentifiant;
    private EditText etMdp;
    private Button btnValider;
    protected ImageView mIvLogo;
    private Handler mHandlerTada;
    private int mShortDelay;
    private TextView tvTrans;
    private AVLoadingIndicatorView chargement;
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
        tvTrans.setText(explainStringArray[new Random().nextInt(explainStringArray.length)]);
        chargement = (AVLoadingIndicatorView) findViewById(R.id.avi);
        mHandlerTada = new Handler(); // android.os.handler
        mShortDelay = 4000; //milliseconds
        mHandlerTada.postDelayed(new Runnable(){
            public void run(){
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(mIvLogo);
                YoYo.with(Techniques.SlideOutRight).duration(600).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tvTrans.setText(explainStringArray[new Random().nextInt(explainStringArray.length)]);
                        YoYo.with(Techniques.SlideInLeft)
                                .duration(600)
                                .playOn(tvTrans);
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {}
                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                }).playOn(tvTrans);
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
        YoYo.with(Techniques.FadeOut)
                .duration(700)
                .playOn(btnValider);
        chargement.show();
    }

    public void retry() {
        chargement.hide();
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(btnValider);
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
            Snackbar errorSnack = Snackbar.make(findViewById(R.id.llBackground),R.string.error_co,Snackbar.LENGTH_LONG);
            errorSnack.setAction(R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    retry();
                }
            });
            errorSnack.show();
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

    @Override
    protected void onResume() {
        super.onResume();
        Animation animSlideRight = AnimationUtils.loadAnimation(this,R.anim.slide_right);
        tvTrans.setText(explainStringArray[new Random().nextInt(explainStringArray.length)]);
        tvTrans.startAnimation(animSlideRight);
    }

    private static final String[] explainStringArray = {
            "Connecte toi pour chatter avec tes amis",
            "Winter is comming",
            "John snow est mort",
            "John snow est vivant",
            "Bazinga",
            "Pourquoi la vie ?!"
};
}

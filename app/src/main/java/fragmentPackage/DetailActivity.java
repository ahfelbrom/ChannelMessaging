package fragmentPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import alexis.boulet.channelmessaging.R;

/**
 * Created by bouleta on 27/02/2017.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        String texteAAfficher = getIntent().getStringExtra("monTextAAfficher");
        ChannelFragment fragB = (ChannelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentB_ID);
        fragB.fillTextView(texteAAfficher);
    }
}

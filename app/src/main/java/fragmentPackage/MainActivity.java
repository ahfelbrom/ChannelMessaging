package fragmentPackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import alexis.boulet.channelmessaging.R;

/**
 * Created by bouleta on 27/02/2017.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChannelListFragment fragA = (ChannelListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentA_ID);
        ChannelFragment fragB = (ChannelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentB_ID);
        if(fragB == null|| !fragB.isInLayout()){
            Intent i = new Intent(getApplicationContext(),DetailActivity.class);
            i.putExtra("monTextAAfficher",fragA.listItems[position]);
            startActivity(i);
        } else {
            fragB.fillTextView(fragA.listItems[position]);
        }
    }
}

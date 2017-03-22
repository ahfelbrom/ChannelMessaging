package alexis.boulet.channelmessaging.fragmentPackage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import alexis.boulet.channelmessaging.R;
import alexis.boulet.channelmessaging.UploadFileToServer;

/**
 * Created by bouleta on 15/03/2017.
 */
public class MyDialogFragment extends DialogFragment {

    private int chanID;
    private String accesstoken;
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("accessToken", "rien");
        accesstoken = token;
        final MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).mkdirs();
        String filepath = getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC)+"/message.3gp";
        recorder.setOutputFile(filepath);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            recorder.start();
        } catch (Exception e) {
            Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        String chan = chanID+"";
        List<String> values = new ArrayList<String>();
        values.add(chan);
        values.add(accesstoken);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.msg_dial)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recorder.stop();
                        recorder.release();
                        //UploadFileToServer ufts = new UploadFileToServer(getContext(),filepath,values,this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recorder.stop();
                        recorder.reset();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}

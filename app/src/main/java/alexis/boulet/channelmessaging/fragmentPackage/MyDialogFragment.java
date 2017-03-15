package alexis.boulet.channelmessaging.fragmentPackage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.IOException;

import alexis.boulet.channelmessaging.R;

/**
 * Created by bouleta on 15/03/2017.
 */
public class MyDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).mkdirs();
        String filepath = getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC)+"/message/message.3gp";
        recorder.setOutputFile(filepath);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        recorder.start();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.msg_dial)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recorder.stop();
                        recorder.release();
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

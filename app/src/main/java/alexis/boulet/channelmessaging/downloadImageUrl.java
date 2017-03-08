package alexis.boulet.channelmessaging;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by bouleta on 06/02/2017.
 */
public class DownloadImageUrl extends AsyncTask<Void, Void, Void> implements OnDownloadCompleteListener {

    private String imageUrl;
    private String filePath; //filepath
    private ArrayList<OnDownloadCompleteListener> listeners = new ArrayList<>();

    public void addOnDownloadCompleteListener(OnDownloadCompleteListener listener) {
        this.listeners.add(listener);
    }

    public DownloadImageUrl(String imageUrl, String filePath) {
        this.imageUrl = imageUrl;
        this.filePath = filePath;
    }

    public String getFilePath() { return filePath; }

    public String getImageUrl() {
        return imageUrl;
    }

    public void downloadFromUrl(String fileURL, String fileName) {
        try {
            URL url = new URL( fileURL);
            File file = new File(fileName);
            file.createNewFile();
            /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();
            /* Define InputStreams to read from the URLConnection.*/
            InputStream is = ucon.getInputStream();
            /* Read bytes to the Buffer until there is nothing more to read(-1) and
                write on the fly in the file.*/
            FileOutputStream fos = new FileOutputStream(file);
            final int BUFFER_SIZE = 23 * 1024;
            BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);
            byte[] baf = new byte[BUFFER_SIZE];
            int actual = 0;
            while (actual != -1) {
                fos.write(baf, 0, actual);
                actual = bis.read(baf, 0, BUFFER_SIZE);
            }
            fos.close();
        } catch (IOException e) {
            //TODO HANDLER
        }
    }


    @Override
    protected Void doInBackground(Void... voids) {
        downloadFromUrl(this.getImageUrl(), this.getFilePath());
        return null;
    }

    @Override
    public void onDownloadComplete(String content, int requestCode) {

    }
}

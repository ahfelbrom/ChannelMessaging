package alexis.boulet.channelmessaging;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by bouleta on 06/02/2017.
 */
public class downloadImageUrl extends AsyncTask<Void, Void, Void> implements OnDownloadCompleteListener {

    private String imageUrl;
    private String filename;

    public downloadImageUrl(String imageUrl, String filename) {
        this.imageUrl = imageUrl;
        this.filename = filename;
    }

    public String getFilename() {

        return filename;
    }

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
        downloadFromUrl(this.getImageUrl(), this.getFilename());
        return null ;
    }

    @Override
    public void onDownloadComplete(String content, int requestCode) {

    }
}

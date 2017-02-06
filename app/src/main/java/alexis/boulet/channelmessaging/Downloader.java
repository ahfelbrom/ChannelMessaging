package alexis.boulet.channelmessaging;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by bouleta on 20/01/2017.
 */
public class Downloader extends AsyncTask<Void, Void, String> implements OnDownloadCompleteListener {
    private String txtIdentifiant;
    private String txtMdp;
    private String url;
    private String accesstoken;
    private int chanelId;
    private String message;
    private ArrayList<OnDownloadCompleteListener> listeners = new ArrayList<>();
    private int requestCode;

    public void addOnDownloadCompleteListener(OnDownloadCompleteListener listener) {
        this.listeners.add(listener);
    }

    //création des constructeurs
    public Downloader(String txtIdentifiant, String txtMdp, String geturl, String accestoken) {
        this.txtIdentifiant = txtIdentifiant;
        this.txtMdp = txtMdp;
        this.url = geturl;
        this.accesstoken = accestoken;
        this.chanelId = 0;
        this.message = null;
    }
    public Downloader(String geturl, String accestoken) {
        this.txtIdentifiant = null;
        this.txtMdp = null;
        this.url = geturl;
        this.accesstoken = accestoken;
        this.chanelId = 0;
        this.message = null;
    }

    public Downloader(String txtIdentifiant, String txtMdp, String geturl) {
        this.txtIdentifiant = txtIdentifiant;
        this.txtMdp = txtMdp;
        this.url = geturl;
        this.accesstoken = null;
        this.chanelId = 0;
        this.message = null;
    }

    public Downloader(String geturl, String accessToken, int chanId)
    {
        this.chanelId = chanId;
        this.url = geturl;
        this.txtIdentifiant = null;
        this.txtMdp = null;
        this.accesstoken = accessToken;
        this.message = null;
    }

    public Downloader(String geturl, String accesstoken, int chanId, String message)
    {
        this.chanelId = chanId;
        this.url = geturl;
        this.txtIdentifiant = null;
        this.txtMdp = null;
        this.accesstoken = accesstoken;
        this.message = message;
    }

    //traitement effectuée par la tâche
    @Override
    protected String doInBackground(Void... params) {
        String requestURL = this.url;
        //postDataParams =
        HashMap<String, String> postparam = new HashMap<>();
        if(txtIdentifiant != null && txtMdp != null)
        {
            postparam.put("username", txtIdentifiant);
            postparam.put("password", txtMdp);
        } else if(accesstoken != null) {
            postparam.put("accesstoken", accesstoken);
            if(chanelId != 0)
            {
                postparam.put("channelid", chanelId+"");
                this.requestCode = 1;
            }
            if (message != null)
            {
                postparam.put("message", message);
                this.requestCode = 2;
            }
        }
        String result = performPostCall(requestURL, postparam);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        for(OnDownloadCompleteListener listener:listeners)
        {
            listener.onDownloadComplete(result, this.requestCode);
        }
    }

    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()){
            if
                (first) first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    @Override
    public void onDownloadComplete(String content, int requestCode) {

    }


}

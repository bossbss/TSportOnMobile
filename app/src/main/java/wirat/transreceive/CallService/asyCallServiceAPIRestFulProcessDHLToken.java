package wirat.transreceive.CallService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import wirat.transreceive.SettingActivity;

import static cz.msebera.android.httpclient.protocol.HTTP.USER_AGENT;


public class asyCallServiceAPIRestFulProcessDHLToken extends AsyncTask<Void, Void, JSONObject> {
    private AsyncTaskCompleteListener<JSONObject> callback;

    Context context;
    private ProgressDialog progress;

    public asyCallServiceAPIRestFulProcessDHLToken(Context Context, AsyncTaskCompleteListener<JSONObject> cb) {
        this.context = Context;
        this.callback = cb;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        try {
            // Simulate network access.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            return null;
        }

        JSONObject myjson = new JSONObject();
        try {
            System.out.println(".....REST..เรียกเซอวิส....");
            String Token = "";
            try {
                String url = SettingActivity.URLTOKEN;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("User-Agent", USER_AGENT);
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());
                JSONObject Tok = new JSONObject(response.toString());
                myjson = Tok;

            } catch (Exception ex)
            {
                return myjson;
            }

        } catch (Exception ex) {

        }
        return myjson;
    }

    @Override
    protected void onPostExecute(JSONObject strjson) {
        super.onPostExecute(strjson);
        progress.dismiss();
        callback.onTaskComplete(strjson);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progress = ProgressDialog.show(this.context, "", "Loading..");
    }

    @Override
    protected void onCancelled() {
        progress.dismiss();
    }

}

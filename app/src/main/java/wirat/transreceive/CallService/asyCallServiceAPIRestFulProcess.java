package wirat.transreceive.CallService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

public class asyCallServiceAPIRestFulProcess extends AsyncTask<Void, Void, JSONObject> {
    private AsyncTaskCompleteListener<JSONObject> callback;
    String nameMethod;
    HashMap<String, String> ParamiterValues;

    Context context;
    private ProgressDialog progress;
    private String LinkService;

    public asyCallServiceAPIRestFulProcess(String LinkService, Context Context, AsyncTaskCompleteListener<JSONObject> cb, String nameMethod, HashMap<String, String> ParamiterValues) {
        this.nameMethod = nameMethod;
        this.ParamiterValues = ParamiterValues;
        this.context = Context;
        this.LinkService = LinkService;
        this.callback = cb;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        try {
            // Simulate network access.
            Thread.sleep(200);
        } catch (InterruptedException e) {
            return null;
        }

        JSONObject myjson = new JSONObject();
        try {
            System.out.println(".....REST..เรียกเซอวิส....");

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(LinkService+"/"+nameMethod+"/");
            httppost.addHeader("BOSS", "Bossbss7@gmail.com");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            for(Map.Entry<String, String> entry : ParamiterValues.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                nameValuePairs.add(new BasicNameValuePair(key, value));
            }

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);

            InputStream in = response.getEntity().getContent();
            StringBuilder stringbuilder = new StringBuilder();
            BufferedReader bfrd = new BufferedReader(new InputStreamReader(in),1024);
            String line;
            while((line = bfrd.readLine()) != null)
                stringbuilder.append(line);

            System.out.println("RES :::" + stringbuilder.toString());

            httpclient.getConnectionManager().shutdown();
            myjson = new JSONObject(stringbuilder.toString());

        } catch (ClientProtocolException e) {
            return new JSONObject();
        } catch (IOException e) {
            return new JSONObject();
        } catch (Exception e) {
            return new JSONObject();
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

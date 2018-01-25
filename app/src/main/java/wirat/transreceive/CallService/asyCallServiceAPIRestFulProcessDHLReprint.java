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


public class asyCallServiceAPIRestFulProcessDHLReprint extends AsyncTask<Void, Void, JSONObject> {
    private AsyncTaskCompleteListener<JSONObject> callback;
    HashMap<String, String> ParamiterValuse;

    Context context;
    private ProgressDialog progress;

    public asyCallServiceAPIRestFulProcessDHLReprint(Context Context, AsyncTaskCompleteListener<JSONObject> cb, HashMap<String, String> ParamiterValues) {
        this.ParamiterValuse = ParamiterValues;
        this.context = Context;
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
                JSONObject IdTok = Tok.getJSONObject("accessTokenResponse");
                Token = IdTok.getString("token");
            } catch (Exception ex)
            {
                return myjson;
            }
            if(Token.equals(""))
                return myjson;

            String pattern = "yyyy-MM-dd'T'HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String currentDateandTime = sdf.format(new Date())+"+07:00";

            String uri = SettingActivity.URLREPRINT;
            String body = "{\n" +
                    "\t\"labelReprintRequest\": {\n" +
                    "\t\t\"hdr\": {\n" +
                    "\t\t\t\"messageType\": \"LABELREPRINT\",\n" +
                    "\t\t\t\"messageDateTime\": \""+currentDateandTime+"\",\n" +
                    "\t\t\t\"accessToken\": \""+Token+"\",\n" +
                    "\t\t\t\"messageVersion\": \"1.1\",\n" +
                    "\t\t\t\"messageLanguage\": \"th_TH\"\n" +
                    "\t\t},\n" +
                    "\t\t\"bd\": {\n" +
                    "\t\t\t\"pickupAccountId\": \""+SettingActivity.pickupAccountId+"\",\n" +
                    "\t\t\t\"soldToAccountId\": \""+SettingActivity.soldToAccountId+"\",\n" +
                    "\t\t\t\"shipmentItems\": [\n" +
                    "\t\t\t\t{\n" +
                    "\t\t\t\t\t\"shipmentID\": \""+ParamiterValuse.get("tracking_code")+"\"\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t]\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";

            String query = uri;
            String json = body;

            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            //String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
            //JSONObject jsonObject = new JSONObject(result);
            StringBuilder stringbuilder = new StringBuilder();
            BufferedReader bfrd = new BufferedReader(new InputStreamReader(in),1024);
            String line;
            while((line = bfrd.readLine()) != null)
                stringbuilder.append(line);

            System.out.println("RES :::" + stringbuilder.toString());


            in.close();
            conn.disconnect();
            myjson = new JSONObject(stringbuilder.toString());
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

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
import java.util.Scanner;

import wirat.transreceive.SettingActivity;

import static cz.msebera.android.httpclient.protocol.HTTP.USER_AGENT;


public class asyCallServiceAPIRestFulProcessDHL extends AsyncTask<Void, Void, JSONObject> {
    private AsyncTaskCompleteListener<JSONObject> callback;
    HashMap<String, String> ParamiterValuse;

    Context context;
    private ProgressDialog progress;

    public asyCallServiceAPIRestFulProcessDHL(Context Context, AsyncTaskCompleteListener<JSONObject> cb,HashMap<String, String> ParamiterValues) {
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

            SimpleDateFormat Inv = new SimpleDateFormat("yyMMddHHmmss");
            String InvoiceId = "THAYF"+SettingActivity.DHLID+ Inv.format(new Date());

            String uri = SettingActivity.URLLABEL;
            String body = "{\n" +
                    "\t\"labelRequest\": {\n" +
                    "\t\t\"hdr\": {\n" +
                    "\t\t\t\"messageType\": \"LABEL\",\n" +
                    "\t\t\t\"messageDateTime\": \""+currentDateandTime+"\",\n" +
                    "\t\t\t\"accessToken\": \""+Token+"\",\n" +
                    "\t\t\t\"messageVersion\": \"1.4\",\n" +
                    "\t\t\t\"messageLanguage\": \"th_TH\"\n" +
                    "\t\t},\n" +
                    "\t\t\"bd\": {\n" +
                    "\t\t\t\"customerAccountId\": null,\n" +
                    "\t\t\t\"pickupAccountId\": \""+SettingActivity.pickupAccountId+"\",\n" +
                    "\t\t\t\"soldToAccountId\": \""+SettingActivity.soldToAccountId+"\",\n" +
                    "\t\t\t\"pickupDateTime\": \""+currentDateandTime+"\",\n" +
                    "\t\t\t\"inlineLabelReturn\": \""+SettingActivity.inlineLabelReturn+"\",\n" +
                    "\t\t\t\"pickupAddress\": "+SettingActivity.pickupAddress+",\n" +
                    "\t\t\t\"shipperAddress\": {\n" +
                    "\t\t\t\t\"name\": \""+ParamiterValuse.get("data[0][from][name]")+"\",\n" +
                    "\t\t\t\t\"address1\": \""+ParamiterValuse.get("data[0][from][address]")+"\",\n" +
                    "\t\t\t\t\"city\": \""+ParamiterValuse.get("data[0][from][province]")+"\",\n" +
                    "\t\t\t\t\"state\": \""+ParamiterValuse.get("data[0][from][state]")+"\",\n" +
                    "\t\t\t\t\"district\": \""+ ParamiterValuse.get("data[0][from][district]") +"\",\n" +
                    "\t\t\t\t\"country\": \"TH\",\n" +
                    "\t\t\t\t\"postCode\": \""+ParamiterValuse.get("data[0][from][postcode]")+"\",\n" +
                    "\t\t\t\t\"phone\": \""+ParamiterValuse.get("data[0][from][tel]")+"\"\n" +
                    "\t\t\t},\n" +
                    "\t\t\t\"shipmentItems\": [{\n" +
                    "\t\t\t\t\t\"consigneeAddress\": {\n" +
                    "\t\t\t\t\t    \"companyName\": \"Import From Mobile\",\n" +
                    "\t\t\t\t\t\t\"name\": \""+ ParamiterValuse.get("data[0][to][name]") +"\",\n" +
                    "\t\t\t\t\t\t\"address1\": \""+ ParamiterValuse.get("data[0][to][address]") +"\",\n" +
                    "\t\t\t\t\t\t\"city\": \""+ ParamiterValuse.get("data[0][to][province]") +"\",\n" +
                    "\t\t\t\t\t\t\"state\": \""+ ParamiterValuse.get("data[0][to][state]") +"\",\n" +
                    "\t\t\t\t\t\t\"district\": \""+ ParamiterValuse.get("data[0][to][district]") +"\",\n" +
                    "\t\t\t\t\t\t\"country\": \"TH\",\n" +
                    "\t\t\t\t\t\t\"postCode\": \""+ ParamiterValuse.get("data[0][to][postcode]") +"\",\n" +
                    "\t\t\t\t\t\t\"phone\": \""+ ParamiterValuse.get("data[0][to][tel]") +"\"\n" +
                    "\t\t\t\t\t},\n" +
                    //"\t\t\t\t\t\"returnAddress\": "+SettingActivity.pickupAddress+",\n" +
                    "\t\t\t\t\t\"shipmentID\": \""+InvoiceId+"\",\n" +
                    "\t\t\t\t\t\"deliveryConfirmationNo\": null,\n" +
                    "\t\t\t\t\t\"packageDesc\": \"Boss license\",\n" +
                    "\t\t\t\t\t\"totalWeight\": "+ParamiterValuse.get("data[0][parcel][weight]").replaceAll("\\.0*$", "")+",\n" +
                    "\t\t\t\t\t\"totalWeightUOM\": \"G\",\n" +
                    "\t\t\t\t\t\"dimensionUOM\": \"CM\",\n" +
                    "\t\t\t\t\t\"height\": "+ParamiterValuse.get("data[0][parcel][height]")+",\n" +
                    "\t\t\t\t\t\"length\": "+ParamiterValuse.get("data[0][parcel][length]")+",\n" +
                    "\t\t\t\t\t\"width\": "+ParamiterValuse.get("data[0][parcel][width]")+",\n" +
                    "\t\t\t\t\t\"customerReference1\": null,\n" +
                    "\t\t\t\t\t\"customerReference2\": null,\n" +
                    "\t\t\t\t\t\"productCode\": \"PDO\",\n" +
                    "\t\t\t\t\t\"contentIndicator\": null,\n" +
                    "\t\t\t\t\t\"codValue\": null,\n" +
                    "\t\t\t\t\t\"insuranceValue\": null,\n" +
                    "\t\t\t\t\t\"freightCharge\": 0.00,\n" +
                    "\t\t\t\t\t\"totalValue\": 0,\n" +
                    "\t\t\t\t\t\"currency\": \"THB\",\n" +
                    "\t\t\t\t\t\"remarks\": \"Wirat license\",\n" +
                    "\t\t\t\t\t\"deliveryOption\": \"C\",\n" +
                    "\t\t\t\t\t\"isMult\": \"FALSE\",\n" +
                    "\t\t\t\t\t\"shipmentPieces\": null\n" +
                    "\t\t\t}],\n" +
                    "\t\t\t\"label\": "+SettingActivity.label+"\n" +
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

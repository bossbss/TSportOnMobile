package wirat.transreceive.Tracking;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import wirat.transreceive.AlertDialogManager;
import wirat.transreceive.CallService.AsyncTaskCompleteListener;
import wirat.transreceive.CallService.asyCallServiceAPIRestFulProcess;
import wirat.transreceive.CallService.asyCallServiceAPIRestFulProcessDHLtracking;
import wirat.transreceive.DataClass.COURIER;
import wirat.transreceive.DataClass.COURIERALL;
import wirat.transreceive.R;
import wirat.transreceive.SettingActivity;

public class TrakingActivity extends AppCompatActivity {

    EditText SPId;
    Button BtnShearch;
    ListView ListTrack;
    TextView courier_code_Name,courier_tracking_code,datetime_shipping,origin_postcode_destination_postcode;

    static String Tempcourier_code = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SPId = (EditText) findViewById(R.id.SPId);
        BtnShearch = (Button) findViewById(R.id.BtnShearch);
        ListTrack = (ListView) findViewById(R.id.ListTrack);

        courier_code_Name = (TextView) findViewById(R.id.courier_code_Name);
        courier_tracking_code = (TextView) findViewById(R.id.courier_tracking_code);
        datetime_shipping = (TextView) findViewById(R.id.datetime_shipping);
        origin_postcode_destination_postcode = (TextView) findViewById(R.id.origin_postcode_destination_postcode);

        BtnShearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (SPId.getText().toString().length() > 0) {
                        if(SPId.getText().toString().startsWith("SP") || SPId.getText().toString().startsWith("RU") || SPId.getText().toString().startsWith("EY")) {
                            HashMap<String, String> ParamiterValuse = new HashMap<String, String>();
                            ParamiterValuse.put("tracking_code", SPId.getText().toString());

                            asyCallServiceAPIRestFulProcess UpVisit = new asyCallServiceAPIRestFulProcess(SettingActivity.SERVER, TrakingActivity.this, new AsyncTaskCompleteListener<JSONObject>() {
                                @Override
                                public void onTaskComplete(JSONObject result) {
                                    if (result == null) {
                                        new AlertDialogManager().showAlertDialog(TrakingActivity.this, "ผิดพลาด ", "ไม่พบการส่งคืนค่ากลับ", true);
                                        return;
                                    }
                                    try {
                                        String Status = result.getString("status");
                                        if (Boolean.parseBoolean(Status)) {

                                            COURIERALL Cour = new COURIERALL();
                                            COURIER Use = Cour.Courier_Retrun(result.getString("courier_code"));
                                            Tempcourier_code = Use.courier_code;
                                            courier_code_Name.setText(Use.courier_Name);
                                            courier_tracking_code.setText(result.getString("courier_tracking_code"));
                                            datetime_shipping.setText(result.getString("datetime_shipping"));
                                            origin_postcode_destination_postcode.setText(result.getString("origin_postcode") + "/" + result.getString("destination_postcode"));

                                            JSONObject state = result.getJSONObject("state");
                                            Iterator x = state.keys();
                                            JSONArray the_json_array = new JSONArray();
                                            while (x.hasNext()) {
                                                String key = (String) x.next();
                                                the_json_array.put(state.get(key));
                                            }
                                            if (the_json_array.length() > 0) {
                                                CustomAdapterListTracking itemsAdapter = new CustomAdapterListTracking(TrakingActivity.this, the_json_array);
                                                ListTrack.setAdapter(itemsAdapter);
                                            }
                                        } else {
                                            new AlertDialogManager().showAlertDialog(TrakingActivity.this, result.getString("code"), result.getString("message"), true);
                                        }
                                    } catch (JSONException e) {
                                        new AlertDialogManager().showAlertDialog(TrakingActivity.this, "ผิดพลาด ", e.getMessage(), true);
                                    }
                                }
                            }, "tracking", ParamiterValuse);
                            UpVisit.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }else{
                            HashMap<String, String> ParamiterValuse = new HashMap<String, String>();
                            ParamiterValuse.put("tracking_code", SPId.getText().toString());
                            asyCallServiceAPIRestFulProcessDHLtracking UpVisit = new asyCallServiceAPIRestFulProcessDHLtracking(TrakingActivity.this, new AsyncTaskCompleteListener<JSONObject>() {
                                @Override
                                public void onTaskComplete(JSONObject result) {
                                    if (result == null) {
                                        new AlertDialogManager().showAlertDialog(TrakingActivity.this, "ผิดพลาด ", "ไม่พบการส่งคืนค่ากลับ", true);
                                        return;
                                    }
                                    try {
                                        JSONObject labelResponse = result.getJSONObject("trackItemResponse");
                                        JSONObject bd = labelResponse.getJSONObject("bd");
                                        JSONObject responseStatus = bd.getJSONObject("responseStatus");

                                        if (!responseStatus.getString("code").equals("200")) {
                                            new AlertDialogManager().showAlertDialog(TrakingActivity.this, "ผิดพลาด ", ((JSONObject) responseStatus.getJSONArray("messageDetails").get(0)).getString("messageDetail"), true);
                                            return;
                                        }
                                        JSONObject shipmentItems = bd.getJSONArray("shipmentItems").getJSONObject(0);
                                        COURIERALL Cour = new COURIERALL();
                                        COURIER Use = Cour.Courier_Retrun("DHL");
                                        Tempcourier_code = Use.courier_code;
                                        courier_code_Name.setText(Use.courier_Name);
                                        courier_tracking_code.setText(shipmentItems.getString("shipmentID"));
                                        datetime_shipping.setText(shipmentItems.getString("trackingID"));
                                        origin_postcode_destination_postcode.setText("0000/0000");

                                        JSONArray the_json_array = new JSONArray();
                                        JSONArray events = shipmentItems.getJSONArray("events");
                                        for (int i = 0;i<events.length();i++) {
                                            JSONObject item = events.getJSONObject(i);
                                            JSONObject Nitem = new JSONObject();
                                            Nitem.put("datetime",item.getString("dateTime"));
                                            Nitem.put("location",item.getJSONObject("address").getString("city") +" : "+item.getJSONObject("address").getString("state"));
                                            Nitem.put("description",item.getString("description"));
                                            the_json_array.put(Nitem);
                                        }
                                        if (the_json_array.length() > 0) {
                                            CustomAdapterListTracking itemsAdapter = new CustomAdapterListTracking(TrakingActivity.this, the_json_array);
                                            ListTrack.setAdapter(itemsAdapter);
                                        }

                                    } catch (JSONException e) {
                                        new AlertDialogManager().showAlertDialog(TrakingActivity.this, "ผิดพลาด ", e.getMessage(), true);
                                    }
                                }
                            }, ParamiterValuse);
                            UpVisit.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                    }
                    else
                    {
                        CustomAdapterListTracking itemsAdapter = new CustomAdapterListTracking(TrakingActivity.this,null);
                        ListTrack.setAdapter(itemsAdapter);
                    }
                } catch (Exception ex) {
                    new AlertDialogManager().showAlertDialog(TrakingActivity.this,"Error",ex.getMessage(),true);
                    CustomAdapterListTracking itemsAdapter = new CustomAdapterListTracking(TrakingActivity.this,null);
                    ListTrack.setAdapter(itemsAdapter);
                }
            }
        });

    }

}

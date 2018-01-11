package wirat.transreceive.Order;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import wirat.transreceive.AlertDialogManager;
import wirat.transreceive.CallService.AsyncTaskCompleteListener;
import wirat.transreceive.CallService.asyCallServiceAPIRestFulProcess;
import wirat.transreceive.DataBaseHelper.DBClass;
import wirat.transreceive.DataClass.COURIER;
import wirat.transreceive.DataClass.COURIERALL;
import wirat.transreceive.DataClass.bookingResponseObject;
import wirat.transreceive.R;
import wirat.transreceive.SettingActivity;

public class PriceListActivity extends AppCompatActivity {

    ListView ListPriceList;
    public static JSONArray ArryPrice = null;
    public static HashMap<String, String> UseConfirmParamiterValuse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListPriceList = (ListView) findViewById(R.id.ListPrice);
        if (ArryPrice != null) {
            CustomAdapterListPrice itemsAdapter = new CustomAdapterListPrice(PriceListActivity.this, ArryPrice);
            ListPriceList.setAdapter(itemsAdapter);
        } else {
            CustomAdapterListPrice itemsAdapter = new CustomAdapterListPrice(PriceListActivity.this, null);
            ListPriceList.setAdapter(itemsAdapter);
        }

        ListPriceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Object o = ListPriceList.getItemAtPosition(i);
                    final JSONObject Item = new JSONObject(o.toString());
                    COURIERALL Cour = new COURIERALL();
                    final COURIER Use = Cour.Courier_Retrun(Item.getString("courier_code"));
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PriceListActivity.this);
                    builder.setTitle("ยืนยันต้องการเลือกบริการ");
                    builder.setIcon(R.drawable.applyicon);
                    builder.setMessage(Use.courier_Name + "\n\nราคา " + String.valueOf(PriceListActivity.UpDatePriceList(Float.valueOf(PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][weight]")),Item.getString("courier_code"))) + " บาท");
                    builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("courier_code", Use.courier_code);
                                returnIntent.putExtra("courier_code_name", Use.courier_Name);
                                returnIntent.putExtra("price", Item.getString("price"));
                                returnIntent.putExtra("estimate_time", Item.getString("estimate_time"));
                                BookingPacket(returnIntent);

                            } catch (JSONException e) {
                                new AlertDialogManager().showAlertDialog(getApplicationContext(), "Error", e.getMessage(), true);
                            }
                        }
                    });
                    builder.create().show();

                } catch (JSONException e) {
                    new AlertDialogManager().showAlertDialog(PriceListActivity.this, "Error", e.getMessage(), true);
                }
            }
        });
    }

    private void BookingPacket(Intent data) {
        if (data != null) {
            try {
                HashMap<String, String> ParamiterValuse = new HashMap<String, String>();
                ParamiterValuse = UseConfirmParamiterValuse;
                ParamiterValuse.put("remark",SettingActivity.BTTYPE);
                ParamiterValuse.put("email", SettingActivity.EMAIL);
                ParamiterValuse.put("url[success]", "");
                ParamiterValuse.put("url[fail]", "");
                ParamiterValuse.put("data[0][courier_code]", data.getStringExtra("courier_code"));

                asyCallServiceAPIRestFulProcess UpVisit = new asyCallServiceAPIRestFulProcess(SettingActivity.SERVER, PriceListActivity.this, new AsyncTaskCompleteListener<JSONObject>() {
                    @Override
                    public void onTaskComplete(JSONObject result) {
                        if (result == null) {
                            new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด ", "ไม่พบการส่งคืนค่ากลับ", true);
                            return;
                        }
                        try {
                            String Status = result.getString("status");
                            final JSONObject resultF = result;
                            if (Boolean.parseBoolean(Status)) {
                                /*From Service*/
                                JSONObject state = result.getJSONObject("data");
                                Iterator x = state.keys();
                                JSONArray the_json_array = new JSONArray();
                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    the_json_array.put(state.get(key));
                                }
                                if (the_json_array.length() > 0) {
                                    final JSONObject ItemOne = the_json_array.getJSONObject(0);
                                    if (Boolean.parseBoolean(ItemOne.getString("status"))) {
                                        //Confirm
                                        try {
                                            HashMap<String, String> ParamiterValuse = new HashMap<String, String>();
                                            ParamiterValuse.put("api_key", SettingActivity.APIKEY);
                                            ParamiterValuse.put("purchase_id", result.getString("purchase_id"));

                                            asyCallServiceAPIRestFulProcess UpVisit = new asyCallServiceAPIRestFulProcess(SettingActivity.SERVER, PriceListActivity.this, new AsyncTaskCompleteListener<JSONObject>() {
                                                @Override
                                                public void onTaskComplete(JSONObject result) {
                                                    if (result == null) {
                                                        new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด ", "ไม่พบการส่งคืนค่ากลับ", true);
                                                        return;
                                                    }
                                                    try {
                                                        String Status = result.getString("status");
                                                        if (Boolean.parseBoolean(Status)) {
                                                            COURIERALL Cour = new COURIERALL();
                                                            COURIER Use = Cour.Courier_Retrun(ItemOne.getString("courier_code"));

                                                            InsertBookingLine(UseConfirmParamiterValuse ,resultF,ItemOne);

                                                            Intent returnIntent = new Intent();
                                                            returnIntent.putExtra("purchase_id", resultF.getString("purchase_id"));
                                                            returnIntent.putExtra("courier_code", Use.courier_code);
                                                            returnIntent.putExtra("courier_code_name", Use.courier_Name);
                                                            returnIntent.putExtra("price",  String.valueOf(PriceListActivity.UpDatePriceList(Float.valueOf(PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][weight]")),ItemOne.getString("courier_code"))));
                                                            returnIntent.putExtra("tracking_code", ItemOne.getString("tracking_code"));
                                                            returnIntent.putExtra("courier_tracking_code", ItemOne.getString("courier_tracking_code"));
                                                            setResult(100, returnIntent);
                                                            finish();
                                                        } else
                                                            new AlertDialogManager().showAlertDialog(PriceListActivity.this, result.getString("code"), result.getString("message"), true);

                                                    } catch (JSONException e) {
                                                        new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด ", e.getMessage(), true);
                                                    }
                                                }
                                            }, "confirm", ParamiterValuse);
                                            UpVisit.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                        } catch (Exception ex) {
                                            new AlertDialogManager().showAlertDialog(PriceListActivity.this, "Error", ex.getMessage(), true);
                                        }
                                    }else
                                        new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด", "บริการที่เลือกปฏิเสธบริการ", true);
                                }else
                                    new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด", "ไม่พบจำนวนคำสั่งซื้อ", true);

                            } else
                                new AlertDialogManager().showAlertDialog(PriceListActivity.this, result.getString("code"), result.getString("message"), true);

                        } catch (JSONException e) {
                            new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด ", e.getMessage(), true);
                        }
                    }
                }, "booking", ParamiterValuse);
                UpVisit.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            } catch (Exception ex) {
                new AlertDialogManager().showAlertDialog(PriceListActivity.this, "Error", ex.getMessage(), true);
            }
        }
    }

    public void InsertBookingLine(HashMap<String, String> ParamiterValuse ,JSONObject ItemAll,JSONObject ItemOne) {
        try {
            DBClass query = new DBClass(PriceListActivity.this);
            SQLiteDatabase db = query.getWritableDatabase();
            db.execSQL("INSERT INTO " + DBClass.TABLE_BOOKING + " ("
                    + DBClass.TABLE_BOOKING_Status + "  , "
                    + DBClass.TABLE_BOOKING_Tracking_code + "  , "
                    + DBClass.TABLE_BOOKING_Courier_code + "  , "
                    + DBClass.TABLE_BOOKING_Price + "  , "

                    + DBClass.TABLE_BOOKING_From_Name + "  , "
                    + DBClass.TABLE_BOOKING_From_Address + "  , "
                    + DBClass.TABLE_BOOKING_From_District + "  , "
                    + DBClass.TABLE_BOOKING_From_State + "  , "
                    + DBClass.TABLE_BOOKING_From_Province + "  , "
                    + DBClass.TABLE_BOOKING_From_Postcode + "  , "
                    + DBClass.TABLE_BOOKING_From_Tel + "  , "

                    + DBClass.TABLE_BOOKING_From_Email + "  , "
                    + DBClass.TABLE_BOOKING_From_Lat + "  , "
                    + DBClass.TABLE_BOOKING_From_Lng + "  , "

                    + DBClass.TABLE_BOOKING_To_Name + "  , "
                    + DBClass.TABLE_BOOKING_To_Address + "  , "
                    + DBClass.TABLE_BOOKING_To_District + "  , "
                    + DBClass.TABLE_BOOKING_To_State + "  , "
                    + DBClass.TABLE_BOOKING_To_Province + "  , "
                    + DBClass.TABLE_BOOKING_To_Postcode + "  , "
                    + DBClass.TABLE_BOOKING_To_Tel + "  , "

                    + DBClass.TABLE_BOOKING_To_Email + "  , "
                    + DBClass.TABLE_BOOKING_To_Lat + "  , "
                    + DBClass.TABLE_BOOKING_To_Lng + "  , "

                    + DBClass.TABLE_BOOKING_Parcel_Name + "  , "
                    + DBClass.TABLE_BOOKING_Parcel_Weight + "  , "
                    + DBClass.TABLE_BOOKING_Parcel_Width + "  , "
                    + DBClass.TABLE_BOOKING_Parcel_Length + "  , "
                    + DBClass.TABLE_BOOKING_Parcel_Height + "  , "

                    + DBClass.TABLE_BOOKING_Courier_tracking_code + "  , "

                    + DBClass.TABLE_BOOKING_Purchase_Id + "  , "
                    + DBClass.TABLE_BOOKING_Total_Price
                    + " )  "

                    + " VALUES ("
                    + "'"+ItemOne.getString("status")+"', "
                    + "'"+ItemOne.getString("tracking_code")+"', "
                    + "'"+ItemOne.getString("courier_code")+"', "
                    + "'"+ItemOne.getString("price")+"', "

                    + " '"+ParamiterValuse.get("data[0][from][name]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][from][address]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][from][district]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][from][state]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][from][province]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][from][postcode]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][from][tel]")+ "', "

                    + " '"+ParamiterValuse.get("email")+ "', "
                    + " '0', "
                    + " '0', "

                    + " '"+ParamiterValuse.get("data[0][to][name]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][to][address]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][to][district]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][to][state]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][to][province]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][to][postcode]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][to][tel]")+ "', "

                    + " '', "
                    + " '0', "
                    + " '0', "

                    + " '"+ParamiterValuse.get("data[0][parcel][name]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][parcel][weight]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][parcel][width]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][parcel][length]")+ "', "
                    + " '"+ParamiterValuse.get("data[0][parcel][height]")+ "', "

                    + "'"+ItemOne.getString("courier_tracking_code")+"', "

                    + "'"+ItemAll.getString("purchase_id")+"', "
                    + "'"+String.valueOf(PriceListActivity.UpDatePriceList(Float.valueOf(ParamiterValuse.get("data[0][parcel][weight]")),ItemOne.getString("courier_code")))+"' "
                    + ");");

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());

            bookingResponseObject objectList = DBClass.getResultsBycourier_code(PriceListActivity.this,ItemOne.getString("courier_tracking_code"));
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            String key = mRootRef.child("USERCHILDID/"+SettingActivity.BTTYPE+"/USERCHILDLINE/").push().getKey();

            HashMap<String, Object> postValues = new HashMap<>();

            postValues.put(objectList.getTSPCHILDIDName(), objectList.getTSPCHILDID()); //Where TSPCHILDID
            postValues.put("DATECREATE",formattedDate); //Where DateCreate
            postValues.put("TSPCHILDID_DATECREATE",objectList.getTSPCHILDID()+"_"+formattedDate); //Where DateCreate

            postValues.put(DBClass.TABLE_BOOKING_Status,objectList.getStatus().toString());
            postValues.put(DBClass.TABLE_BOOKING_Tracking_code,objectList.getTracking_code());
            postValues.put(DBClass.TABLE_BOOKING_Courier_code ,objectList.getCourier_code());
            postValues.put(DBClass.TABLE_BOOKING_Price,objectList.getPrice());

            postValues.put(DBClass.TABLE_BOOKING_From_Name,objectList.getFrom().getName());
            postValues.put(DBClass.TABLE_BOOKING_From_Address ,objectList.getFrom().getAddress());
            postValues.put(DBClass.TABLE_BOOKING_From_District ,objectList.getFrom().getDistrict());
            postValues.put(DBClass.TABLE_BOOKING_From_State ,objectList.getFrom().getState());
            postValues.put(DBClass.TABLE_BOOKING_From_Province ,objectList.getFrom().getProvince());
            postValues.put(DBClass.TABLE_BOOKING_From_Postcode ,objectList.getFrom().getPostcode());
            postValues.put(DBClass.TABLE_BOOKING_From_Tel ,objectList.getFrom().getTel());

            postValues.put(DBClass.TABLE_BOOKING_To_Name ,objectList.getTo().getName());
            postValues.put(DBClass.TABLE_BOOKING_To_Address ,objectList.getTo().getAddress());
            postValues.put(DBClass.TABLE_BOOKING_To_District ,objectList.getTo().getDistrict());
            postValues.put(DBClass.TABLE_BOOKING_To_State ,objectList.getTo().getState());
            postValues.put(DBClass.TABLE_BOOKING_To_Province ,objectList.getTo().getProvince());
            postValues.put(DBClass.TABLE_BOOKING_To_Postcode ,objectList.getTo().getPostcode());
            postValues.put(DBClass.TABLE_BOOKING_To_Tel ,objectList.getTo().getTel());

            postValues.put(DBClass.TABLE_BOOKING_Parcel_Name ,objectList.getParcel().getName());
            postValues.put(DBClass.TABLE_BOOKING_Parcel_Weight ,objectList.getParcel().getWeight());
            postValues.put(DBClass.TABLE_BOOKING_Parcel_Width ,objectList.getParcel().getWidth());
            postValues.put(DBClass.TABLE_BOOKING_Parcel_Length ,objectList.getParcel().getLength());
            postValues.put(DBClass.TABLE_BOOKING_Parcel_Height ,objectList.getParcel().getHeight());

            postValues.put(DBClass.TABLE_BOOKING_Courier_tracking_code,objectList.getCourier_tracking_code());
            postValues.put(DBClass.TABLE_BOOKING_Total_Price ,objectList.getTotal_price());

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("USERCHILDID/"+SettingActivity.BTTYPE+"/USERCHILDLINE/" + key, postValues);
            //childUpdates.put("/user-messages/Jirawatee/" + key, postValues);

            mRootRef.updateChildren(childUpdates);

        }catch (Exception ex)
        {
            new AlertDialogManager().showAlertDialog(PriceListActivity.this,"ผิดพลาด","การบันทึกรายการ ออนไลน์สำเร็จแล้ว \n ข้อผิดพลาดนี้เกิดขึ้นในการบันทึกภายในอุปกรณ์",true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    static public int UpDatePriceList(float weight,String courier_code){
        if(courier_code.equals("TP2")) {
            if (weight <= 20.0)
                return 16;
            else if (weight > 20.0 && weight <= 100.0)
                return 18;
            else if (weight > 100.0 && weight <= 250.0)
                return 22;
            else if (weight > 250.0 && weight <= 500.0)
                return 28;
            else if (weight > 500.0 && weight <= 1000.0)
                return 38;
            else if (weight > 1000.0 && weight <= 2000.0)
                return 58;
            else
                return 0;
        }else if(courier_code.equals("THP")) {
            if (weight <= 20.0)
                return 32;
            else if (weight > 20.0 && weight <= 100.0)
                return 37;
            else if (weight > 100.0 && weight <= 250.0)
                return 42;
            else if (weight > 250.0 && weight <= 500.0)
                return 52;
            else if (weight > 500.0 && weight <= 1000.0)
                return 67;
            else if (weight > 1000.0 && weight <= 1500.0)
                return 82;
            else if (weight > 1500.0 && weight <= 2000.0)
                return 97;
            else if (weight > 2000.0 && weight <= 2500.0)
                return 122;
            else if (weight > 2500.0 && weight <= 3000.0)
                return 137;
            else if (weight > 3000.0 && weight <= 3500.0)
                return 157;
            else if (weight > 3500.0 && weight <= 4000.0)
                return 177;
            else if (weight > 4000.0 && weight <= 4500.0)
                return 197;
            else if (weight > 4500.0 && weight <= 5000.0)
                return 217;
            else if (weight > 5000.0 && weight <= 5500.0)
                return 242;
            else if (weight > 5500.0 && weight <= 6000.0)
                return 267;
            else if (weight > 6000.0 && weight <= 6500.0)
                return 292;
            else if (weight > 6500.0 && weight <= 7000.0)
                return 317;
            else if (weight > 7000.0 && weight <= 7500.0)
                return 342;
            else if (weight > 7500.0 && weight <= 8000.0)
                return 367;
            else if (weight > 8000.0 && weight <= 8500.0)
                return 397;
            else if (weight > 8500.0 && weight <= 9000.0)
                return 427;
            else if (weight > 9000.0 && weight <= 9500.0)
                return 457;
            else if (weight > 9500.0 && weight <= 10000.0)
                return 487;
            else if (weight > 10000.0 && weight <= 11000.0)
                return 202;
            else if (weight > 11000.0 && weight <= 12000.0)
                return 517;
            else if (weight > 12000.0 && weight <= 13000.0)
                return 532;
            else if (weight > 13000.0 && weight <= 14000.0)
                return 547;
            else if (weight > 14000.0 && weight <= 15000.0)
                return 562;
            else if (weight > 15000.0 && weight <= 16000.0)
                return 577;
            else if (weight > 16000.0 && weight <= 17000.0)
                return 592;
            else if (weight > 17000.0 && weight <= 18000.0)
                return 607;
            else if (weight > 18000.0 && weight <= 19000.0)
                return 622;
            else if (weight > 19000.0 && weight <= 20000.0)
                return 637;
            else
                return 0;
        }else
            return 0;
    }
}

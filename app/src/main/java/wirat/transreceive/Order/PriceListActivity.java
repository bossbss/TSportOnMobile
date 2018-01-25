package wirat.transreceive.Order;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import wirat.transreceive.AlertDialogManager;
import wirat.transreceive.CallService.AsyncTaskCompleteListener;
import wirat.transreceive.CallService.asyCallServiceAPIRestFulProcess;
import wirat.transreceive.CallService.asyCallServiceAPIRestFulProcessDHL;
import wirat.transreceive.DataBaseHelper.DBClass;
import wirat.transreceive.DataClass.COURIER;
import wirat.transreceive.DataClass.COURIERALL;
import wirat.transreceive.DataClass.bookingResponseObject;
import wirat.transreceive.R;
import wirat.transreceive.SettingActivity;

public class PriceListActivity extends AppCompatActivity {

    ListView ListPriceList;
    public static JSONArray ArryPrice = null;
    public static HashMap<String, String> UseConfirmParamiterValuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListPriceList = (ListView) findViewById(R.id.ListPrice);
        if (ArryPrice != null) {
            /**DHL***/
            try {
                if(SettingActivity.DHLENABLE.equals("1")) {
                    JSONObject DHLitem = new JSONObject();
                    DHLitem.put("courier_code", "DHL");
                    DHLitem.put("price", 0);
                    DHLitem.put("estimate_time", "");
                    DHLitem.put("available", true);
                    DHLitem.put("remark", "");
                    DHLitem.put("err_code", "");
                    DHLitem.put("minimum", 1);

                    ArryPrice.put(DHLitem);
                }

                if(SettingActivity.SCGEXENABLE.equals("1")) {
                    JSONObject SCGEXitem = new JSONObject();
                    SCGEXitem.put("courier_code", "SCGEX");
                    SCGEXitem.put("price", 0);
                    SCGEXitem.put("estimate_time", "");
                    SCGEXitem.put("available", true);
                    SCGEXitem.put("remark", "");
                    SCGEXitem.put("err_code", "");
                    SCGEXitem.put("minimum", 1);

                    ArryPrice.put(SCGEXitem);
                }
            } catch (Exception ex) {
                new AlertDialogManager().showAlertDialog(PriceListActivity.this, "Error", ex.getMessage(), true);
            }
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
                    builder.setMessage(Use.courier_Name + "\n\nราคา " + String.valueOf(PriceListActivity.UpDatePriceList(Item.getString("courier_code"))) + " บาท");
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
                ParamiterValuse.put("remark", SettingActivity.BTTYPE);
                ParamiterValuse.put("email", SettingActivity.EMAIL);
                ParamiterValuse.put("url[success]", "");
                ParamiterValuse.put("url[fail]", "");
                ParamiterValuse.put("data[0][courier_code]", data.getStringExtra("courier_code"));

                if (data.getStringExtra("courier_code").equals("TP2") || data.getStringExtra("courier_code").equals("THP")) {
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

                                                                InsertBookingLine(UseConfirmParamiterValuse, resultF, ItemOne);

                                                                Intent returnIntent = new Intent();
                                                                returnIntent.putExtra("purchase_id", resultF.getString("purchase_id"));
                                                                returnIntent.putExtra("courier_code", Use.courier_code);
                                                                returnIntent.putExtra("courier_code_name", Use.courier_Name);
                                                                returnIntent.putExtra("price", String.valueOf(PriceListActivity.UpDatePriceList(ItemOne.getString("courier_code"))));
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
                                        } else
                                            new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด", "บริการที่เลือกปฏิเสธบริการ", true);
                                    } else
                                        new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด", "ไม่พบจำนวนคำสั่งซื้อ", true);

                                } else
                                    new AlertDialogManager().showAlertDialog(PriceListActivity.this, result.getString("code"), result.getString("message"), true);

                            } catch (JSONException e) {
                                new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด ", e.getMessage(), true);
                            }
                        }
                    }, "booking", ParamiterValuse);
                    UpVisit.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else if (data.getStringExtra("courier_code").equals("DHL")) {
                    asyCallServiceAPIRestFulProcessDHL UpVisit = new asyCallServiceAPIRestFulProcessDHL(PriceListActivity.this, new AsyncTaskCompleteListener<JSONObject>() {
                        @Override
                        public void onTaskComplete(JSONObject result) {
                            if (result == null || result.toString().equals("{}")) {
                                new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด ", "ไม่พบการส่งคืนค่ากลับ", true);
                                return;
                            }
                            try {
                                JSONObject labelResponse = result.getJSONObject("labelResponse");
                                JSONObject bd = labelResponse.getJSONObject("bd");
                                JSONObject labels = bd.getJSONArray("labels").getJSONObject(0);
                                JSONObject responseStatus = bd.getJSONObject("responseStatus");

                                if (!labels.getJSONObject("responseStatus").getString("code").equals("200")) {
                                    new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด ", ((JSONObject) labels.getJSONObject("responseStatus").getJSONArray("messageDetails").get(0)).getString("messageDetail"), true);
                                    return;
                                }
                                if (!responseStatus.getString("code").equals("200")) {
                                    new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด ", ((JSONObject) responseStatus.getJSONArray("messageDetails").get(0)).getString("messageDetail"), true);
                                    return;
                                }

                                COURIERALL Cour = new COURIERALL();
                                COURIER Use = Cour.Courier_Retrun("DHL");

                                JSONObject ItemAll = new JSONObject();
                                ItemAll.put("purchase_id", labels.getString("shipmentID"));

                                JSONObject ItemOne = new JSONObject();
                                ItemOne.put("status", "true");
                                ItemOne.put("tracking_code", labels.getString("deliveryConfirmationNo"));
                                ItemOne.put("courier_code", "DHL");
                                ItemOne.put("price", "0");
                                ItemOne.put("courier_tracking_code", labels.getString("shipmentID"));

                                InsertBookingLine(UseConfirmParamiterValuse, ItemAll, ItemOne);
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("purchase_id", ItemAll.getString("purchase_id"));
                                returnIntent.putExtra("courier_code", Use.courier_code);
                                returnIntent.putExtra("courier_code_name", Use.courier_Name);
                                returnIntent.putExtra("price", String.valueOf(PriceListActivity.UpDatePriceList(ItemOne.getString("courier_code"))));
                                returnIntent.putExtra("tracking_code", ItemOne.getString("tracking_code"));
                                returnIntent.putExtra("courier_tracking_code", ItemOne.getString("courier_tracking_code"));
                                setResult(100, returnIntent);
                                finish();

                            } catch (JSONException e) {
                                new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด ", e.getMessage(), true);
                            }
                        }
                    }, ParamiterValuse);
                    UpVisit.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else if (data.getStringExtra("courier_code").equals("SCGEX")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("ระบุเลขที่ติดตามพัสดุ");
                    final EditText input = new EditText(this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String m_Text = input.getText().toString();
                            try {
                                if(m_Text.length() > 0) {
                                    COURIERALL Cour = new COURIERALL();
                                    COURIER Use = Cour.Courier_Retrun("SCGEX");

                                    JSONObject ItemAll = new JSONObject();
                                    ItemAll.put("purchase_id", m_Text);

                                    JSONObject ItemOne = new JSONObject();
                                    ItemOne.put("status", "true");
                                    ItemOne.put("tracking_code", m_Text);
                                    ItemOne.put("courier_code", "SCGEX");
                                    ItemOne.put("price", "0");
                                    ItemOne.put("courier_tracking_code", m_Text);

                                    InsertBookingLine(UseConfirmParamiterValuse, ItemAll, ItemOne);
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("purchase_id", ItemAll.getString("purchase_id"));
                                    returnIntent.putExtra("courier_code", Use.courier_code);
                                    returnIntent.putExtra("courier_code_name", Use.courier_Name);
                                    returnIntent.putExtra("price", String.valueOf(PriceListActivity.UpDatePriceList(ItemOne.getString("courier_code"))));
                                    returnIntent.putExtra("tracking_code", ItemOne.getString("tracking_code"));
                                    returnIntent.putExtra("courier_tracking_code", ItemOne.getString("courier_tracking_code"));
                                    setResult(100, returnIntent);
                                    finish();
                                }
                            } catch (JSONException e) {
                                new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด ", e.getMessage(), true);
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                } else {
                    new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด", "บริการที่เลือกไม่อยู่ภายใต้ บริการที่กำหนด", true);
                }

            } catch (Exception ex) {
                new AlertDialogManager().showAlertDialog(PriceListActivity.this, "Error", ex.getMessage(), true);
            }
        }
    }

    public void InsertBookingLine(HashMap<String, String> ParamiterValuse, JSONObject ItemAll, JSONObject ItemOne) {
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
                    + "'" + ItemOne.getString("status") + "', "
                    + "'" + ItemOne.getString("tracking_code") + "', "
                    + "'" + ItemOne.getString("courier_code") + "', "
                    + "'" + ItemOne.getString("price") + "', "

                    + " '" + ParamiterValuse.get("data[0][from][name]") + "', "
                    + " '" + ParamiterValuse.get("data[0][from][address]") + "', "
                    + " '" + ParamiterValuse.get("data[0][from][district]") + "', "
                    + " '" + ParamiterValuse.get("data[0][from][state]") + "', "
                    + " '" + ParamiterValuse.get("data[0][from][province]") + "', "
                    + " '" + ParamiterValuse.get("data[0][from][postcode]") + "', "
                    + " '" + ParamiterValuse.get("data[0][from][tel]") + "', "

                    + " '" + ParamiterValuse.get("email") + "', "
                    + " '0', "
                    + " '0', "

                    + " '" + ParamiterValuse.get("data[0][to][name]") + "', "
                    + " '" + ParamiterValuse.get("data[0][to][address]") + "', "
                    + " '" + ParamiterValuse.get("data[0][to][district]") + "', "
                    + " '" + ParamiterValuse.get("data[0][to][state]") + "', "
                    + " '" + ParamiterValuse.get("data[0][to][province]") + "', "
                    + " '" + ParamiterValuse.get("data[0][to][postcode]") + "', "
                    + " '" + ParamiterValuse.get("data[0][to][tel]") + "', "

                    + " '', "
                    + " '0', "
                    + " '0', "

                    + " '" + ParamiterValuse.get("data[0][parcel][name]") + "', "
                    + " '" + ParamiterValuse.get("data[0][parcel][weight]") + "', "
                    + " '" + ParamiterValuse.get("data[0][parcel][width]") + "', "
                    + " '" + ParamiterValuse.get("data[0][parcel][length]") + "', "
                    + " '" + ParamiterValuse.get("data[0][parcel][height]") + "', "

                    + "'" + ItemOne.getString("courier_tracking_code") + "', "

                    + "'" + ItemAll.getString("purchase_id") + "', "
                    + "'" + String.valueOf(PriceListActivity.UpDatePriceList(ItemOne.getString("courier_code"))) + "' "
                    + ");");

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());

            bookingResponseObject objectList = DBClass.getResultsBycourier_code(PriceListActivity.this, ItemOne.getString("courier_tracking_code"));
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            String key = mRootRef.child("USERCHILDID/" + SettingActivity.BTTYPE + "/USERCHILDLINE/").push().getKey();

            HashMap<String, Object> postValues = new HashMap<>();

            postValues.put(objectList.getTSPCHILDIDName(), objectList.getTSPCHILDID()); //Where TSPCHILDID
            postValues.put("DATECREATE", formattedDate); //Where DateCreate
            postValues.put("TSPCHILDID_DATECREATE", objectList.getTSPCHILDID() + "_" + formattedDate); //Where DateCreate

            postValues.put(DBClass.TABLE_BOOKING_Status, objectList.getStatus().toString());
            postValues.put(DBClass.TABLE_BOOKING_Tracking_code, objectList.getTracking_code());
            postValues.put(DBClass.TABLE_BOOKING_Courier_code, objectList.getCourier_code());
            postValues.put(DBClass.TABLE_BOOKING_Price, objectList.getPrice());

            postValues.put(DBClass.TABLE_BOOKING_From_Name, objectList.getFrom().getName());
            postValues.put(DBClass.TABLE_BOOKING_From_Address, objectList.getFrom().getAddress());
            postValues.put(DBClass.TABLE_BOOKING_From_District, objectList.getFrom().getDistrict());
            postValues.put(DBClass.TABLE_BOOKING_From_State, objectList.getFrom().getState());
            postValues.put(DBClass.TABLE_BOOKING_From_Province, objectList.getFrom().getProvince());
            postValues.put(DBClass.TABLE_BOOKING_From_Postcode, objectList.getFrom().getPostcode());
            postValues.put(DBClass.TABLE_BOOKING_From_Tel, objectList.getFrom().getTel());

            postValues.put(DBClass.TABLE_BOOKING_To_Name, objectList.getTo().getName());
            postValues.put(DBClass.TABLE_BOOKING_To_Address, objectList.getTo().getAddress());
            postValues.put(DBClass.TABLE_BOOKING_To_District, objectList.getTo().getDistrict());
            postValues.put(DBClass.TABLE_BOOKING_To_State, objectList.getTo().getState());
            postValues.put(DBClass.TABLE_BOOKING_To_Province, objectList.getTo().getProvince());
            postValues.put(DBClass.TABLE_BOOKING_To_Postcode, objectList.getTo().getPostcode());
            postValues.put(DBClass.TABLE_BOOKING_To_Tel, objectList.getTo().getTel());

            postValues.put(DBClass.TABLE_BOOKING_Parcel_Name, objectList.getParcel().getName());
            postValues.put(DBClass.TABLE_BOOKING_Parcel_Weight, objectList.getParcel().getWeight());
            postValues.put(DBClass.TABLE_BOOKING_Parcel_Width, objectList.getParcel().getWidth());
            postValues.put(DBClass.TABLE_BOOKING_Parcel_Length, objectList.getParcel().getLength());
            postValues.put(DBClass.TABLE_BOOKING_Parcel_Height, objectList.getParcel().getHeight());

            postValues.put(DBClass.TABLE_BOOKING_Courier_tracking_code, objectList.getCourier_tracking_code());
            postValues.put(DBClass.TABLE_BOOKING_Total_Price, objectList.getTotal_price());

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("USERCHILDID/" + SettingActivity.BTTYPE + "/USERCHILDLINE/" + key, postValues);
            //childUpdates.put("/user-messages/Jirawatee/" + key, postValues);

            mRootRef.updateChildren(childUpdates);

        } catch (Exception ex) {
            new AlertDialogManager().showAlertDialog(PriceListActivity.this, "ผิดพลาด", "การบันทึกรายการ ออนไลน์สำเร็จแล้ว \n ข้อผิดพลาดนี้เกิดขึ้นในการบันทึกภายในอุปกรณ์", true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    static public String scgDetail = "";

    static public int UpDatePriceList(String courier_code) {
        float weight = Float.valueOf(PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][weight]") == "" ? "0" : PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][weight]"));
        float width = Float.valueOf(PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][width]") == "" ? "0" : PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][width]"));
        float length = Float.valueOf(PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][length]") == "" ? "0" : PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][length]"));
        float height = Float.valueOf(PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][height]") == "" ? "0" : PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][height]"));

        String poscodefrom = PriceListActivity.UseConfirmParamiterValuse.get("data[0][from][postcode]");
        String poscodeto = PriceListActivity.UseConfirmParamiterValuse.get("data[0][to][postcode]");

        float WLH = width + length + height;
        float xWLH = width * length * height / 5;

        if (courier_code.equals("TP2")) {
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
        } else if (courier_code.equals("THP")) {
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
        } else if (courier_code.equals("DHL")) {
            int fhl1 = 0;
            if (WLH <= 0) fhl1 = 0;
            else if (WLH <= 40.0) fhl1 = 45;
            else if (WLH <= 60.0) fhl1 = 80;
            else if (WLH <= 75.0) fhl1 = 100;
            else if (WLH <= 90.0) fhl1 = 115;
            else if (WLH <= 105.0) fhl1 = 155;
            else if (WLH <= 120.0) fhl1 = 205;
            else if (WLH <= 150.0) fhl1 = 330;
            else if (WLH <= 200.0) fhl1 = 420;
            else fhl1 = 0;

            int fhl12 = 0;
            if (weight <= 0) fhl12 = 0;
            else if (weight <= 250) fhl12 = 64;
            else if (weight <= 500) fhl12 = 80;
            else if (weight <= 1000) fhl12 = 95;
            else if (weight <= 2000) fhl12 = 105;
            else if (weight <= 3000) fhl12 = 115;
            else if (weight <= 4000) fhl12 = 120;
            else if (weight <= 5000) fhl12 = 125;
            else if (weight <= 6000) fhl12 = 135;
            else if (weight <= 7000) fhl12 = 140;
            else if (weight <= 8000) fhl12 = 145;
            else if (weight <= 9000) fhl12 = 150;
            else if (weight <= 10000) fhl12 = 165;
            else if (weight <= 11000) fhl12 = 170;
            else if (weight <= 12000) fhl12 = 180;
            else if (weight <= 13000) fhl12 = 190;
            else if (weight <= 14000) fhl12 = 200;
            else if (weight <= 15000) fhl12 = 210;
            else if (weight <= 20000) fhl12 = 340;
            else if (weight <= 25000) fhl12 = 430;
            else if (weight <= 30000) fhl12 = 690;
            else fhl12 = 0;

            int fhl11 = 0;
            if (xWLH <= 0) fhl11 = 0;
            else if (xWLH <= 250) fhl11 = 64;
            else if (xWLH <= 500) fhl11 = 80;
            else if (xWLH <= 1000) fhl11 = 95;
            else if (xWLH <= 2000) fhl11 = 105;
            else if (xWLH <= 3000) fhl11 = 115;
            else if (xWLH <= 4000) fhl11 = 120;
            else if (xWLH <= 5000) fhl11 = 125;
            else if (xWLH <= 6000) fhl11 = 135;
            else if (xWLH <= 7000) fhl11 = 140;
            else if (xWLH <= 8000) fhl11 = 145;
            else if (xWLH <= 9000) fhl11 = 150;
            else if (xWLH <= 10000) fhl11 = 165;
            else if (xWLH <= 11000) fhl11 = 170;
            else if (xWLH <= 12000) fhl11 = 180;
            else if (xWLH <= 13000) fhl11 = 190;
            else if (xWLH <= 14000) fhl11 = 200;
            else if (xWLH <= 15000) fhl11 = 210;
            else if (xWLH <= 20000) fhl11 = 340;
            else if (xWLH <= 25000) fhl11 = 430;
            else if (xWLH <= 30000) fhl11 = 690;
            else fhl11 = 0;

            if (xWLH > 30000 || weight > 30000 || WLH > 200)
                return 0;

            int tmp = 0;
            if (fhl12 > fhl11)
                tmp = fhl12;
            else
                tmp = fhl11;

            if (fhl1 > tmp)
                return fhl1;
            else
                return tmp;
        } else if (courier_code.equals("SCGEX")) {
            float Siz = 0;
            if (WLH > 0 && WLH <= 40.0)
                Siz = 40;
            else if (WLH > 40.0 && WLH <= 60.0)
                Siz = 60;
            else if (WLH > 60.0 && WLH <= 80.0)
                Siz = 80;
            else if (WLH > 80.0 && WLH <= 100.0)
                Siz = 100;
            else if (WLH > 100.0 && WLH <= 120.0)
                Siz = 120;
            else if (WLH > 120.0 && WLH <= 140.0)
                Siz = 140;
            else if (WLH > 140.0 && WLH <= 160.0)
                Siz = 160;
            else
                Siz = 0;

            if (poscodefrom.equals("") || poscodeto.equals("") || !poscodefrom.substring(0, 1).equals(poscodeto.substring(0, 1))) { //ต่าภาคหรือไม่ระบุ ปณ
                scgDetail = "(ราคาต่างภาค)";
                if (Siz == 40)
                    return 50;
                else if (Siz == 60)
                    return 85;
                else if (Siz == 80)
                    return 105;
                else if (Siz == 100)
                    return 145;
                else if (Siz == 120)
                    return 210;
                else if (Siz == 140)
                    return 270;
                else if (Siz == 160)
                    return 300;
                else
                    return 0;
            } else  //ภายในภาค
            {
                scgDetail = "(ราคาในภาค)";
                if (Siz == 40)
                    return 40;
                else if (Siz == 60)
                    return 70;
                else if (Siz == 80)
                    return 90;
                else if (Siz == 100)
                    return 130;
                else if (Siz == 120)
                    return 180;
                else if (Siz == 140)
                    return 240;
                else if (Siz == 160)
                    return 270;
                else
                    return 0;
            }
        } else
            return 0;
    }
}

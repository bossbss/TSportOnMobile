package wirat.transreceive;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import wirat.transreceive.CallService.AsyncTaskCompleteListener;
import wirat.transreceive.CallService.asyCallServiceAPIRestFulProcessDHLToken;
import wirat.transreceive.DataBaseHelper.DBClass;
import wirat.transreceive.DataClass.bookingResponseObject;
import wirat.transreceive.Order.OrderActivity;
import wirat.transreceive.Order.PriceListActivity;
import wirat.transreceive.Tracking.TrakingActivity;
import wirat.transreceive.TrackingFail.TrackingFailActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String[] PERMISSIONS = {
                Manifest.permission.BLUETOOTH,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE
        };
        hasPermissions(this, PERMISSIONS);

        DBClass mHelper = new DBClass(MainActivity.this);
        SQLiteDatabase mDb = mHelper.getWritableDatabase();
        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DBClass.TABLE_SETTING, null);

        mCursor.moveToPosition(0);
        SettingActivity.BTADDRESS = mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_SETTING_BTADDRESS));
        SettingActivity.BTTYPE = mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_SETTING_BTTYPE));

        Button BtnOrder = (Button) findViewById(R.id.BtnOrder);
        Button BtnTracking = (Button) findViewById(R.id.BtnTracking);
        Button BtnSetting = (Button) findViewById(R.id.BtnSetting);
        Button BtnTrackingFail = (Button) findViewById(R.id.BtnTrackingFail);

        mProgress = ProgressDialog.show(MainActivity.this,"", "loading");

        //start a new thread to process job
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!CheckConnecttion());
                    CheckConnecttion();
            }
        }).start();



        BtnOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(CheckConnecttion()) {
                    Intent Order = new Intent(MainActivity.this, OrderActivity.class);
                    startActivity(Order);
                }
            }
        });

        BtnTracking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(CheckConnecttion()) {
                    Intent Order = new Intent(MainActivity.this, TrakingActivity.class);
                    startActivity(Order);
                }
            }
        });

        BtnTrackingFail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(CheckConnecttion()) {
                    Intent Order = new Intent(MainActivity.this, TrackingFailActivity.class);
                    startActivity(Order);
                }
            }
        });

        BtnSetting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent Order = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(Order);
            }
        });
    }

    private boolean CheckConnecttion(){

        SettingDefault();

        if (SettingActivity.BTTYPE == null || SettingActivity.BTTYPE.equals("")) {
            new AlertDialogManager().showAlertDialog(MainActivity.this, "Error", "ระบุหมายเลข TSport ID ที่ การตั้งค่า", true);
            return false;
        }

        if(SettingActivity.APIKEY == null || SettingActivity.APIKEY.equals(""))
        {
            new AlertDialogManager().showAlertDialog(MainActivity.this, "Error", "TSport ID ไม่ถูกต้อง \nตรวจสอบการเชื่อมต่ออินเตอร์เน็ต", true);
            return false;
        }

        if (SettingActivity.TSPORTENABLE == null || !SettingActivity.TSPORTENABLE.equals("1")) {
            new AlertDialogManager().showAlertDialog(MainActivity.this, "Error", "ระงับการใช้งาน ติดต่อผู้พัฒนา", true);
            return false;
        }

        if (SettingActivity.TOKENDATE == null || SettingActivity.TOKENTMP == null) {
            new AlertDialogManager().showAlertDialog(MainActivity.this, "Error", "ไม่พบบริการ DHL", true);
            return false;
        }

        return true;
    }

    private void SettingDefault() {

        FirebaseCheckVersion();

        SimpleDateFormat Inv = new SimpleDateFormat("yyMMdd");
        final SimpleDateFormat Invf = Inv;
        if(SettingActivity.TOKENDATE.equals("") || SettingActivity.TOKENDATE.equals("") || !SettingActivity.TOKENDATE.equals(Inv.format(new Date())))
        {
            final asyCallServiceAPIRestFulProcessDHLToken UpVisit = new asyCallServiceAPIRestFulProcessDHLToken(MainActivity.this, new AsyncTaskCompleteListener<JSONObject>() {
                @Override
                public void onTaskComplete(JSONObject result) {
                    if (result == null) {
                        new AlertDialogManager().showAlertDialog(MainActivity.this, "ผิดพลาด ", "ไม่พบการส่งคืนค่ากลับ", true);
                        return;
                    }
                    try {
                        JSONObject IdTok = result.getJSONObject("accessTokenResponse");
                        String Token = IdTok.getString("token");
                        if(Token.equals("")) {
                            SettingActivity.TOKENDATE = Invf.format(new Date());
                            SettingActivity.TOKENTMP = Token;
                            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                            mRootRef.child("USERCHILDID/" + SettingActivity.BTTYPE + "/TSPCHDETAIL/DHL/TOKENDATE/").setValue(SettingActivity.TOKENDATE);
                            mRootRef.child("USERCHILDID/" + SettingActivity.BTTYPE + "/TSPCHDETAIL/DHL/TOKENTMP/").setValue(SettingActivity.TOKENTMP);
                        }else{
                            new AlertDialogManager().showAlertDialog(MainActivity.this, "ผิดพลาด ", "DHL ไม่สามารถใช้งานได้", true);
                        }
                    } catch (Exception e) {
                        new AlertDialogManager().showAlertDialog(MainActivity.this, "ผิดพลาด ", e.getMessage(), true);
                    }
                }
            });
            UpVisit.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public void hasPermissions(Context context, String... permissionsAll) {
        int PERMISSION_ALL = 0;
        for (String permission : permissionsAll) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(context,
                    permission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        permissionsAll,
                        PERMISSION_ALL);
                break;
            }
        }
    }

    private void FirebaseCheckVersion() {
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("USERCHILDID/" + SettingActivity.BTTYPE + "/TSPCHDETAIL").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    SettingActivity.APIKEY = dataSnapshot.child("APIKEY").getValue(String.class);
                    SettingActivity.SERVER = dataSnapshot.child("SERVER").getValue(String.class);
                    SettingActivity.EMAIL = dataSnapshot.child("EMAIL").getValue(String.class);
                    SettingActivity.TSPORTENABLE = dataSnapshot.child("TSPORTENABLE").getValue(String.class);

                    SettingActivity.SCGEXENABLE = dataSnapshot.child("SCGEXENABLE").getValue(String.class);
                    SettingActivity.DHLENABLE = dataSnapshot.child("DHLENABLE").getValue(String.class);

                    DataSnapshot DHLSnp = dataSnapshot.child("DHL");

                    SettingActivity.DHLID = DHLSnp.child("DHLID").getValue().toString();
                    SettingActivity.URLLABEL = DHLSnp.child("URLLABEL").getValue().toString();
                    SettingActivity.URLTOKEN = DHLSnp.child("URLTOKEN").getValue().toString();
                    SettingActivity.URLTRACKING = DHLSnp.child("URLTRACKING").getValue().toString();
                    SettingActivity.URLREPRINT = DHLSnp.child("URLREPRINT").getValue().toString();
                    SettingActivity.inlineLabelReturn = DHLSnp.child("inlineLabelReturn").getValue().toString();
                    SettingActivity.pickupAccountId = DHLSnp.child("pickupAccountId").getValue().toString();
                    SettingActivity.soldToAccountId = DHLSnp.child("soldToAccountId").getValue().toString();

                    SettingActivity.TOKENDATE = DHLSnp.child("TOKENDATE").getValue().toString();
                    SettingActivity.TOKENTMP = DHLSnp.child("TOKENTMP").getValue().toString();

                    DataSnapshot DHLPkAd = DHLSnp.child("pickupAddress");
                    Map<String, String> LebelAd = (Map<String, String>) DHLPkAd.getValue();
                    JSONObject jsonAd = new JSONObject(LebelAd);
                    SettingActivity.pickupAddress = jsonAd.toString();

                    DataSnapshot DHLLabel = DHLSnp.child("label");
                    Map<String, String> Lebel = (Map<String, String>) DHLLabel.getValue();
                    JSONObject json = new JSONObject(Lebel);
                    SettingActivity.label = json.toString();


                    Log.d("TEST Firebase", "onDataChange");

                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TEST Firebase", databaseError.getMessage());
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("TEST Firebase", "onCancelled");
            }
        });

        Log.d("TEST Firebase", "เพิ่มแล้ว");

    }
}

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

import java.io.File;
import java.io.IOException;
import java.util.Map;

import wirat.transreceive.DataBaseHelper.DBClass;
import wirat.transreceive.Order.OrderActivity;
import wirat.transreceive.Tracking.TrakingActivity;
import wirat.transreceive.TrackingFail.TrackingFailActivity;

public class MainActivity extends AppCompatActivity {

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
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE
        };
        hasPermissions(this, PERMISSIONS);

        Button BtnOrder = (Button) findViewById(R.id.BtnOrder);
        Button BtnTracking = (Button) findViewById(R.id.BtnTracking);
        Button BtnSetting = (Button) findViewById(R.id.BtnSetting);
        Button BtnTrackingFail = (Button) findViewById(R.id.BtnTrackingFail);

        SettingDefault();

        BtnOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SettingDefault();

                if (SettingActivity.BTTYPE.equals("")) {
                    new AlertDialogManager().showAlertDialog(MainActivity.this, "Error", "ระบุหมายเลข TSport Id ที่ การตั้งค่า", true);
                    return;
                }

                if(SettingActivity.APIKEY.equals(""))
                {
                    FirebaseCheckVersion();
                    return;
                }

                if (!SettingActivity.TSPORTENABLE.equals("1")) {
                    new AlertDialogManager().showAlertDialog(MainActivity.this, "Error", "ระงับการใช้งาน ติดต่อผู้พัฒนา", true);
                    return;
                } else {
                    Intent Order = new Intent(MainActivity.this, OrderActivity.class);
                    startActivity(Order);
                }
            }
        });

        BtnTracking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SettingDefault();

                Intent Order = new Intent(MainActivity.this, TrakingActivity.class);
                startActivity(Order);
            }
        });

        BtnTrackingFail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SettingDefault();

                Intent Order = new Intent(MainActivity.this, TrackingFailActivity.class);
                startActivity(Order);
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

    private void SettingDefault() {

        FirebaseCheckVersion();

        DBClass mHelper = new DBClass(MainActivity.this);
        SQLiteDatabase mDb = mHelper.getWritableDatabase();
        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DBClass.TABLE_SETTING, null);

        mCursor.moveToPosition(0);
        SettingActivity.BTADDRESS = mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_SETTING_BTADDRESS));
        SettingActivity.BTTYPE = mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_SETTING_BTTYPE));
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
                    Map<String, String> value = (Map<String, String>) dataSnapshot.getValue();
                    SettingActivity.APIKEY = value.get("APIKEY");
                    SettingActivity.SERVER = value.get("SERVER");
                    SettingActivity.EMAIL = value.get("EMAIL");
                    SettingActivity.TSPORTENABLE = value.get("TSPORTENABLE");

                    if (!value.get("TSPORTVERSIONNAME").equals(BuildConfig.VERSION_NAME)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("อัพเดทโปรแกรม")
                                .setMessage("Version " + value.get("TSPORTVERSIONNAME"))
                                .setIcon(R.drawable.applyicon)
                                .setPositiveButton("อัพเดททันที", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            downloadInLocalFile();
                                        } catch (Exception ex) {
                                            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                                        }
                                    }
                                });

                        builder.create().show();
                    }
                    Toast.makeText(MainActivity.this, value.get("TSPORTVERSIONNAME"), Toast.LENGTH_LONG).show();
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

    ProgressDialog Helper;

    private void downloadInLocalFile() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("TSport/TSport.apk");

        Log.d("TEST Firebase", imageRef.getPath());

        File dir = new File(Environment.getExternalStorageDirectory().toString());
        final File file = new File(dir, "TSport.apk");
        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final FileDownloadTask fileDownloadTask = imageRef.getFile(file);
        Helper = new ProgressDialog(MainActivity.this);
        Helper.setTitle("Download");
        Helper.setMessage("Updating App Version, Please Wait!");
        Helper.setIndeterminate(false);
        Helper.setMax(100);
        Helper.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Show ProgressBar
        Helper.setCancelable(false);
        Helper.show();

        fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Helper.dismiss();
                Intent install = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(file);
                install.setDataAndType(uri, "application/vnd.android.package-archive");
                startActivity(install);

                MainActivity.this.finish();
                finish();
                Log.d("TEST Firebase", "onSuccess..");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Helper.dismiss();
                Log.d("TEST Firebase", "Fail..");
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                int progress = (int) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                Helper.setProgress(progress);
                Log.d("TEST Firebase", "Wait..");
            }
        });


    }
}

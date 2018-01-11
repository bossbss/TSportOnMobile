package wirat.transreceive.TrackingFail;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import wirat.transreceive.AlertDialogManager;
import wirat.transreceive.CallService.AsyncTaskCompleteListener;
import wirat.transreceive.CallService.asyCallServiceAPIRestFulProcess;
import wirat.transreceive.DataClass.COURIER;
import wirat.transreceive.DataClass.COURIERALL;
import wirat.transreceive.R;
import wirat.transreceive.SettingActivity;
import wirat.transreceive.Tracking.CustomAdapterListTracking;
import wirat.transreceive.Tracking.TrakingActivity;

public class TrackingFailActivity extends AppCompatActivity {

    ListView ListTrack;
    Button BtnShearch;
    Calendar CalendarStart;
    EditText Datestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_fail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CalendarStart = Calendar.getInstance();
        Datestart = (EditText) findViewById(R.id.Datestart);
        final DatePickerDialog.OnDateSetListener dateST = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                CalendarStart.set(Calendar.YEAR, year);
                CalendarStart.set(Calendar.MONTH, monthOfYear);
                CalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Datestart.setText(sdf.format(CalendarStart.getTime()));

            }

        };

        Datestart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TrackingFailActivity.this, dateST, CalendarStart
                        .get(Calendar.YEAR), CalendarStart.get(Calendar.MONTH),
                        CalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ListTrack = (ListView) findViewById(R.id.ListTrack);
        BtnShearch = (Button) findViewById(R.id.BtnShearch);
        BtnShearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (Datestart.getText().toString().length() > 5) {

                        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference mRootRefLine = mRootRef.child("USERCHILDID/"+SettingActivity.BTTYPE+"/USERCHILDLINE/");
                        mRootRefLine.orderByChild("DATECREATE").equalTo(Datestart.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    ArrayList<HashMap<String, String>> ListValuse = new ArrayList<HashMap<String, String>>();
                                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                                        HashMap<String, String> Item = new HashMap<String, String>();

                                        Item.put("courier_code",messageSnapshot.child("courier_code").getValue().toString());
                                        Item.put("parcel_weight","น้ำหนัก "+messageSnapshot.child("parcel_weight").getValue().toString() + " กรัม");

                                        Item.put("courier_tracking_code",(String) messageSnapshot.child("courier_tracking_code").getValue());
                                        Item.put("total_price","ค่าบริการ " + messageSnapshot.child("total_price").getValue().toString() + " บาท");

                                        Item.put("from","ผู้ส่ง " + messageSnapshot.child("from_name").getValue().toString() + " ปณ." + messageSnapshot.child("from_postcode").getValue().toString());
                                        Item.put("to","ผู้รับ " + messageSnapshot.child("to_name").getValue().toString() + " ปณ." + messageSnapshot.child("to_postcode").getValue().toString());

                                        ListValuse.add(Item);
                                        Log.d("TEST Firebase", "onDataChange");
                                    }

                                    CustomAdapterListTrackingFail itemsAdapter = new CustomAdapterListTrackingFail(TrackingFailActivity.this, ListValuse);
                                    ListTrack.setAdapter(itemsAdapter);
                                }catch (Exception ex)
                                {
                                    Toast.makeText(TrackingFailActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.d("TEST Firebase","onCancelled");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("TEST Firebase", databaseError.getMessage());
                                Toast.makeText(TrackingFailActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                Log.d("TEST Firebase","onCancelled");
                            }
                        });

                    } else {
                        CustomAdapterListTrackingFail itemsAdapter = new CustomAdapterListTrackingFail(TrackingFailActivity.this, null);
                        ListTrack.setAdapter(itemsAdapter);
                    }
                } catch (Exception ex) {
                    new AlertDialogManager().showAlertDialog(TrackingFailActivity.this, "Error", ex.getMessage(), true);
                    CustomAdapterListTrackingFail itemsAdapter = new CustomAdapterListTrackingFail(TrackingFailActivity.this, null);
                    ListTrack.setAdapter(itemsAdapter);
                }
            }
        });
    }
}

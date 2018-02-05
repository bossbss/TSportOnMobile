package wirat.transreceive;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zebra.android.printer.internal.ZebraPrinterLegacyDelegator;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.internal.ZebraPrinterCpcl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import wirat.transreceive.DataBaseHelper.DBClass;
import wirat.transreceive.DataBaseHelper.ExportDB;

public class SettingActivity extends AppCompatActivity {

    public static String APIKEY = "";
    public static String SERVER = "";
    public static String EMAIL = "";
    public static String BTADDRESS = "";
    public static String BTTYPE = "";
    public static String TSPORTENABLE = "";

    public static String SCGEXENABLE = "";
    public static String DHLENABLE = "";

    public static String DHLID = "";
    public static String URLLABEL = "";
    public static String URLTOKEN = "";
    public static String URLTRACKING = "";
    public static String URLREPRINT = "";
    public static String inlineLabelReturn = "";
    public static String label = "";
    public static String pickupAccountId = "";
    public static String pickupAddress = "";
    public static String soldToAccountId = "";

    public static String TOKENDATE = "";
    public static String TOKENTMP = "";


    EditText BtNamae,BtAddress,TSportId;
    private BluetoothDevice BTDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            DBClass mHelper = new DBClass(SettingActivity.this);
            final SQLiteDatabase mDb = mHelper.getWritableDatabase();
            Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DBClass.TABLE_SETTING, null);

            mCursor.moveToPosition(0);

            TSportId = (EditText) findViewById(R.id.TSportId);
            TSportId.setText(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_SETTING_BTTYPE)));

            BtNamae = (EditText) findViewById(R.id.BtNamae);
            BtNamae.setText(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_SETTING_BTNAME)));

            BtAddress = (EditText) findViewById(R.id.BtAddress);
            BtAddress.setText(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_SETTING_BTADDRESS)));

            Button BtnSearchBT = (Button) findViewById(R.id.BtnSearchBT);
            BtnSearchBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent BTIntent = new Intent(SettingActivity.this, BTDeviceActivity.class);
                    startActivityForResult(BTIntent, /*BTDeviceActivity.REQUEST_CONNECT_BT*/100);
                }
            });

            BtnSearchBT.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent Order = new Intent(SettingActivity.this, BlueToothPrinterApp.class);
                    startActivity(Order);
                    return true;
                }
            });

            Button BtnTestPrint = (Button) findViewById(R.id.BtnTestPrint);
            BtnTestPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String textprint =
                            "! U1 SETBOLD 2\r\n"
                                    +"! U1 SETLP crth14b.cpf 0 46\r\n"
                                    +" ทดสอบเครื่องพิมพ์\r\n"
                                    +"! U1 SETLP crth10.cpf 0 24\r\n"
                                    +" ABCDEFGHIJKLMNOP \r\n"
                                    +"! U1 SETLP crth10.cpf 0 24\r\n"
                                    +" From : 1234567890 :(0000000) \r\n"
                                    +"! U1 SETLP crth10.cpf 0 24\r\n"
                                    +" To   : 0987654321 :(9999999) \r\n"
                                    +"! U1 SETLP crth10.cpf 0 24\r\n"
                                    +" Price   : 10000 บาท \r\n"
                                    +"\r\n";
                    SettingActivity.BTADDRESS = BtAddress.getText().toString();
                    pairPrinter(textprint,SettingActivity.this);


                    Bitmap bitmapToPrint = BitmapFactory.decodeResource(getResources(), R.drawable.shppicon);
                    pairPrinterImage(bitmapToPrint,SettingActivity.this);
                }
            });

            Button BtnSave = (Button) findViewById(R.id.BtnSave);
            BtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDb.execSQL("UPDATE " + DBClass.TABLE_SETTING + " SET "
                            + "   " + DBClass.TABLE_SETTING_BTNAME + "='" + BtNamae.getText().toString()
                            + "', " + DBClass.TABLE_SETTING_BTADDRESS + "='" + BtAddress.getText().toString()
                            + "', " + DBClass.TABLE_SETTING_BTTYPE + "='" + TSportId.getText().toString()
                            + "';");
                    Toast.makeText(SettingActivity.this, "บันทึกเรียบร้อย", Toast.LENGTH_LONG).show();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_CANCELED, returnIntent);
                    finish();
                }
            });

            BtnSave.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                    builder.setTitle("TSport Export")
                            .setMessage("For Admin")
                            .setIcon(R.drawable.applyicon)
                            .setPositiveButton("Import ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ExportDB db = new ExportDB();
                                    db.ImportDatabaseFile(SettingActivity.this,"TransReceive");
                                    new AlertDialogManager().showAlertDialog(SettingActivity.this,"Import","Finishes", true);

                                }
                            })
                            .setNegativeButton("Export ", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ExportDB db = new ExportDB();
                                            db.exportDatabaseFile(SettingActivity.this, "TransReceive");
                                            new AlertDialogManager().showAlertDialog(SettingActivity.this, "Export", "Finishes", true);
                                        }
                                    });
                    builder.create().show();
                    return true;
                }
            });
        }catch (Exception ex)
        {
            new AlertDialogManager().showAlertDialog(SettingActivity.this,"Error",ex.getMessage(), true);
        }

    }

    private static BluetoothDevice PrinterDevice(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        BluetoothDevice OneDevice = null;
        List<String> s = new ArrayList<String>();
        for(BluetoothDevice bt : pairedDevices)
            OneDevice = bt;

        return OneDevice;
    }

    public static void pairPrinter(final String textPrint, Context Con)  {
        /*
        BluetoothDevice Device = PrinterDevice();
        if(Device == null)
        {
            new AlertDialogManager().showAlertDialog(Con,"ไม่พบอุปกรณ์","ตรวจสอบการเชื่อมต่อ",true);
            return;
        }
        */

        final UUID SerialPortServiceClass_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                //.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
        final BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
        final String PrinterBsid = SettingActivity.BTADDRESS; //Device.getAddress();//"AC:3F:A4:4D:F7:AD";

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream sOut;
                BluetoothSocket socket;
                BA.cancelDiscovery();

                try {
                    BluetoothDevice BD = BA.getRemoteDevice(PrinterBsid);
                    socket = BD.createInsecureRfcommSocketToServiceRecord(SerialPortServiceClass_UUID);


                    if (!socket.isConnected()) {
                        socket.connect();
                        Thread.sleep(1000); // <-- WAIT FOR SOCKET
                    }
                    sOut = socket.getOutputStream();
                    String cpclData = textPrint;
                    sOut.write(cpclData.getBytes("TIS-620"));
                    sOut.close();

                    socket.close();
                    BA.cancelDiscovery();

                } catch (Exception e) {
                    Log.e("", "IOException");
                    //e.printStackTrace();
                    return;
                }
            }
        });

        t.start();
    }

    public static void pairPrinterImage(final Bitmap bitmapToPrint, Context Con)  {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BluetoothConnection myConn = new BluetoothConnection(SettingActivity.BTADDRESS);
                    ZebraPrinter myPrinter = new ZebraPrinterCpcl(myConn);
                    myConn.open();
                    new ZebraPrinterLegacyDelegator(myPrinter).getGraphicsUtil().printImage(bitmapToPrint, 0, 0, -1, -1,       false);
                    // to reduce extra space
                    myConn.write("! UTILITIES\r\nIN-MILLIMETERS\r\nSETFF 10 2\r\nPRINT\r\n".getBytes());
                    myConn.close();

                } catch (Exception e) {
                    Log.e("", "IOException");
                    //e.printStackTrace();
                    return;
                }
            }
        });

        t.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            BTDevice = BTDeviceActivity.getCurrentDevice();
            if (BTDevice != null) {
                BtNamae.setText(BTDevice.getName());
                BtAddress.setText(BTDevice.getAddress());
            }
        } catch (Exception e) {
            Log.d("onActivityResult",e.getMessage());
        }
    }
}

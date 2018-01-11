package wirat.transreceive;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BTDeviceActivity extends AppCompatActivity {

    private ArrayList<BluetoothDevice> mDevices = new ArrayList<BluetoothDevice>();
    static private ArrayList<String> mDeviceList = new ArrayList<String>();
    static private BluetoothAdapter mBluetoothAdapter = null;

    public static BluetoothDevice BTDevice = null;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btdevice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ListView) findViewById(R.id.ListBt);
        /*
        * Ennable Bt
        * */
        checkLocationPermission();
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivity(enableBtIntent);

        proceedDiscovery();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id){
                BTDevice = mDevices.get(pos);
                Toast.makeText(BTDeviceActivity.this,BTDevice.getAddress(),Toast.LENGTH_SHORT);
                finish();
            }
        });
    }

    protected void proceedDiscovery() {
        try {
            mDeviceList = new ArrayList<String>();
            mDevices.clear();
            mDevices = null;
            mDevices = new ArrayList<BluetoothDevice>();

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
            registerReceiver(mReceiver, filter);

            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            mBluetoothAdapter.startDiscovery();
        }catch (Exception ex)
        {
            Toast.makeText(BTDeviceActivity.this,ex.getMessage(),Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, final Intent intent) {
            final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the recently discovered device in a list
                mDevices.add(device);
                mDeviceList.add(device.getName() + "\n" + device.getAddress());
                listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mDeviceList));
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, Menu.FIRST, Menu.NONE, "Refresh Scanning");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case Menu.FIRST:
                proceedDiscovery();
                break;
        }
        return true;
    }

    protected void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
        }
    }

    public static BluetoothDevice getCurrentDevice() {
        return BTDevice;
    }
}

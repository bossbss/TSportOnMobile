package wirat.transreceive.DataBaseHelper;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ExportDB {
    public boolean exportDatabaseFile(Context context, String dbName) {
        try {
            File dbFile = context.getDatabasePath(dbName);
            File exportFile = new File(Environment.getExternalStorageDirectory() + "/" + dbName);
            FileInputStream fileInputStream = new FileInputStream(dbFile);
            FileOutputStream fileOutputStream = new FileOutputStream(exportFile);
            FileChannel fileInputChannel = fileInputStream.getChannel();
            FileChannel fileOutputChannel = fileOutputStream.getChannel();
            fileInputChannel.transferTo(0, fileInputChannel.size(), fileOutputChannel);
            fileInputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean ImportDatabaseFile(Context context, String dbName) {
        try {
            File dbFile = context.getDatabasePath(dbName);
            File exportFile = new File(Environment.getExternalStorageDirectory() + "/" + dbName);
            FileInputStream fileInputStream = new FileInputStream(exportFile);
            FileOutputStream fileOutputStream = new FileOutputStream(dbFile);
            FileChannel fileInputChannel = fileInputStream.getChannel();
            FileChannel fileOutputChannel = fileOutputStream.getChannel();
            fileInputChannel.transferTo(0, fileInputChannel.size(), fileOutputChannel);
            fileInputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}


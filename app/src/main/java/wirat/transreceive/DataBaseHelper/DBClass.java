package wirat.transreceive.DataBaseHelper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import wirat.transreceive.DataClass.ADDRESS;
import wirat.transreceive.DataClass.PARCEL;
import wirat.transreceive.DataClass.bookingResponseObject;
import wirat.transreceive.Order.PriceListActivity;
import wirat.transreceive.SettingActivity;

public class DBClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "TransReceive";

    public static final String TABLE_SETTING = "TABLE_SETTING";
    public static final String TABLE_SETTING_APIKEY = "APIKEY";
    public static final String TABLE_SETTING_SERVER = "SERVER";
    public static final String TABLE_SETTING_EMAIL = "EMAIL";
    public static final String TABLE_SETTING_BTNAME = "BTNAME";
    public static final String TABLE_SETTING_BTADDRESS = "BTADDRESS";
    public static final String TABLE_SETTING_BTTYPE = "BTTYPE";

    public static final String TABLE_BOOKING = "TABLE_BOOKING";
    public static final String TABLE_BOOKING_Status  =  "status";
    public static final String TABLE_BOOKING_Tracking_code = "tracking_code";
    public static final String TABLE_BOOKING_Courier_code= "courier_code";
    public static final String TABLE_BOOKING_Price= "price";
    public static final String TABLE_BOOKING_From_District= "from_district";
    public static final String TABLE_BOOKING_From_State= "from_state";
    public static final String TABLE_BOOKING_From_Province= "from_province";
    public static final String TABLE_BOOKING_From_Postcode= "from_postcode";
    public static final String TABLE_BOOKING_From_Address= "from_address";
    public static final String TABLE_BOOKING_From_Name= "from_name";
    public static final String TABLE_BOOKING_From_Email= "from_email";
    public static final String TABLE_BOOKING_From_Tel= "from_tel";
    public static final String TABLE_BOOKING_From_Lat= "from_lat";
    public static final String TABLE_BOOKING_From_Lng= "from_lng";

    public static final String TABLE_BOOKING_To_District= "to_district";
    public static final String TABLE_BOOKING_To_State= "to_state";
    public static final String TABLE_BOOKING_To_Province= "to_province";
    public static final String TABLE_BOOKING_To_Postcode= "to_postcode";
    public static final String TABLE_BOOKING_To_Address= "to_address";
    public static final String TABLE_BOOKING_To_Name= "to_name";
    public static final String TABLE_BOOKING_To_Email= "to_email";
    public static final String TABLE_BOOKING_To_Tel= "to_tel";
    public static final String TABLE_BOOKING_To_Lat= "to_lat";
    public static final String TABLE_BOOKING_To_Lng= "to_lng";

    public static final String TABLE_BOOKING_Courier_tracking_code = "courier_tracking_code";

    public static final String TABLE_BOOKING_Parcel_Name = "parcel_name";
    public static final String TABLE_BOOKING_Parcel_Weight = "parcel_weight";
    public static final String TABLE_BOOKING_Parcel_Width  = "parcel_width";
    public static final String TABLE_BOOKING_Parcel_Length = "parcel_length";
    public static final String TABLE_BOOKING_Parcel_Height = "parcel_height";

    public static final String TABLE_BOOKING_Purchase_Id = "purchase_id";
    public static final String TABLE_BOOKING_Total_Price = "total_price";

    public static final String TABLE_district = "district";
    public static final String TABLE_district_Column_DISTRICT_ID = "DISTRICT_ID";
    public static final String TABLE_district_Column_DISTRICT_CODE = "DISTRICT_CODE";
    public static final String TABLE_district_Column_DISTRICT_NAME = "DISTRICT_NAME";
    public static final String TABLE_district_Column_AMPHUR_ID = "AMPHUR_ID";
    public static final String TABLE_district_Column_PROVINCE_ID = "PROVINCE_ID";
    public static final String TABLE_district_Column_GEO_ID = "GEO_ID";

    public static final String TABLE_amphur = "amphur";
    public static final String TABLE_amphur_Column_AMPHUR_ID = "AMPHUR_ID";
    public static final String TABLE_amphur_Column_AMPHUR_CODE = "AMPHUR_CODE";
    public static final String TABLE_amphur_Column_AMPHUR_NAME = "AMPHUR_NAME";
    public static final String TABLE_amphur_Column_POSTCODE = "POSTCODE";
    public static final String TABLE_amphur_Column_GEO_ID = "GEO_ID";
    public static final String TABLE_amphur_Column_PROVINCE_ID = "PROVINCE_ID";

    public static final String TABLE_province = "province";
    public static final String TABLE_province_Column_PROVINCE_ID = "PROVINCE_ID";
    public static final String TABLE_province_Column_PROVINCE_CODE = "PROVINCE_CODE";
    public static final String TABLE_province_Column_PROVINCE_NAME = "PROVINCE_NAME";
    public static final String TABLE_province_Column_GEO_ID = "GEO_ID";


    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        * Table Setting
        * */
        db.execSQL("CREATE TABLE " + TABLE_SETTING + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_SETTING_APIKEY + " TEXT, "
                + TABLE_SETTING_SERVER + " TEXT, "
                + TABLE_SETTING_EMAIL + " TEXT ,"
                + TABLE_SETTING_BTNAME + " TEXT NOT NULL DEFAULT '', "
                + TABLE_SETTING_BTADDRESS + " TEXT NOT NULL DEFAULT '', "
                + TABLE_SETTING_BTTYPE + " TEXT NOT NULL DEFAULT '' "
                +");");

        db.execSQL("INSERT INTO " + TABLE_SETTING + " ("
                + TABLE_SETTING_APIKEY + " , "
                + TABLE_SETTING_SERVER + " , "
                + TABLE_SETTING_EMAIL + " ) " +
                "VALUES ('', '', '');");
        /*
        * Table Booking
        * */
        db.execSQL("CREATE TABLE " + TABLE_BOOKING + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TABLE_BOOKING_Status  + " TEXT , "
                +TABLE_BOOKING_Tracking_code + " TEXT , "
                +TABLE_BOOKING_Courier_code+ " TEXT , "
                +TABLE_BOOKING_Price+ " TEXT , "
                +TABLE_BOOKING_From_District+ " TEXT , "
                +TABLE_BOOKING_From_State+ " TEXT , "
                +TABLE_BOOKING_From_Province+ " TEXT , "
                +TABLE_BOOKING_From_Postcode+ " TEXT , "
                +TABLE_BOOKING_From_Address+ " TEXT , "
                +TABLE_BOOKING_From_Name+ " TEXT , "
                +TABLE_BOOKING_From_Email+ " TEXT , "
                +TABLE_BOOKING_From_Tel+ " TEXT , "
                +TABLE_BOOKING_From_Lat+ " TEXT , "
                +TABLE_BOOKING_From_Lng+ " TEXT , "

                +TABLE_BOOKING_To_District+ " TEXT , "
                +TABLE_BOOKING_To_State+ " TEXT , "
                +TABLE_BOOKING_To_Province+ " TEXT , "
                +TABLE_BOOKING_To_Postcode+ " TEXT , "
                +TABLE_BOOKING_To_Address+ " TEXT , "
                +TABLE_BOOKING_To_Name+ " TEXT , "
                +TABLE_BOOKING_To_Email+ " TEXT , "
                +TABLE_BOOKING_To_Tel+ " TEXT , "
                +TABLE_BOOKING_To_Lat+ " TEXT , "
                +TABLE_BOOKING_To_Lng+ " TEXT , "

                +TABLE_BOOKING_Courier_tracking_code + " TEXT,  "

                + TABLE_BOOKING_Parcel_Name+ " TEXT NOT NULL DEFAULT '', "
                + TABLE_BOOKING_Parcel_Weight+ " TEXT NOT NULL DEFAULT '0', "
                + TABLE_BOOKING_Parcel_Width + " TEXT NOT NULL DEFAULT '0', "
                + TABLE_BOOKING_Parcel_Length + " TEXT NOT NULL DEFAULT '0', "
                + TABLE_BOOKING_Parcel_Height+ " TEXT NOT NULL DEFAULT '0', "

                + TABLE_BOOKING_Purchase_Id + " TEXT NOT NULL DEFAULT '', "
                + TABLE_BOOKING_Total_Price + " TEXT NOT NULL DEFAULT '0' "

                + " );");

        InsertTestBookingKine(db);
        /*
        * district
        * */
        db.execSQL("CREATE TABLE " + TABLE_district + " (" + TABLE_district_Column_DISTRICT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_district_Column_DISTRICT_CODE + " TEXT, "
                + TABLE_district_Column_DISTRICT_NAME + " TEXT, "
                + TABLE_district_Column_AMPHUR_ID + " INTEGER, "
                + TABLE_district_Column_PROVINCE_ID + " INTEGER, "
                + TABLE_district_Column_GEO_ID + " INTEGER );");
        /*
        * province
        * */
        db.execSQL("CREATE TABLE " + TABLE_province + " (" + TABLE_province_Column_PROVINCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_province_Column_PROVINCE_CODE + " TEXT, "
                + TABLE_province_Column_PROVINCE_NAME + " TEXT, "
                + TABLE_province_Column_GEO_ID + " INTEGER );");
        /*
        * amphur
        * */
        db.execSQL("CREATE TABLE " + TABLE_amphur + " (" + TABLE_amphur_Column_AMPHUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_amphur_Column_AMPHUR_CODE + " TEXT, "
                + TABLE_amphur_Column_AMPHUR_NAME + " TEXT, "
                + TABLE_amphur_Column_POSTCODE + " TEXT, "
                + TABLE_amphur_Column_GEO_ID + " INTEGER, "
                + TABLE_amphur_Column_PROVINCE_ID + " INTEGER );");

        new ImportProvince1(db);
        new ImportProvince2(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_district);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_amphur);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_province);

        onCreate(db);
    }

    public void InsertTestBookingKine(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_BOOKING + " ("
                +TABLE_BOOKING_Status  + "  , "
                +TABLE_BOOKING_Tracking_code + "  , "
                +TABLE_BOOKING_Courier_code+ "  , "
                +TABLE_BOOKING_Price+ "  , "
                +TABLE_BOOKING_From_District+ "  , "
                +TABLE_BOOKING_From_State+ "  , "
                +TABLE_BOOKING_From_Province+ "  , "
                +TABLE_BOOKING_From_Postcode+ "  , "
                +TABLE_BOOKING_From_Address+ "  , "
                +TABLE_BOOKING_From_Name+ "  , "
                +TABLE_BOOKING_From_Email+ "  , "
                +TABLE_BOOKING_From_Tel+ "  , "
                +TABLE_BOOKING_From_Lat+ "  , "
                +TABLE_BOOKING_From_Lng+ "  , "

                +TABLE_BOOKING_To_District+ "  , "
                +TABLE_BOOKING_To_State+ "  , "
                +TABLE_BOOKING_To_Province+ "  , "
                +TABLE_BOOKING_To_Postcode+ "  , "
                +TABLE_BOOKING_To_Address+ "  , "
                +TABLE_BOOKING_To_Name+ "  , "
                +TABLE_BOOKING_To_Email+ "  , "
                +TABLE_BOOKING_To_Tel+ "  , "
                +TABLE_BOOKING_To_Lat+ "  , "
                +TABLE_BOOKING_To_Lng+ "  , "

                +TABLE_BOOKING_Courier_tracking_code
                + " )  "

                + " VALUES ("
                + "'true', "
                + "'SP010099266', "
                + "'THP' ,"
                + "'35', "
                + "'บางรัก', "
                + "'บางรัก' ,"
                + "'กรุงเทพมหานคร', "
                + "'10200', "
                + "'123/456 Testor Tower', "
                + "'ชื่อผู้ส่ง อยู่ตรงนี', "
                + "'bossbss7@gmail.com' ,"
                + "'0850035533', "
                + "'', "
                + "'' ,"
                + "'บางรัก', "
                + "'บางรัก' ,"
                + "'กรุงเทพมหานคร', "
                + "'10600', "
                + "'456/789 Testor Tower', "
                + "'ชื่อผู้รับ อยู่ตรงนี', "
                + "'bossbss7@gmail.com' ,"
                + "'0850035533', "
                + "'', "
                + "'' ,"
                + "'EY337000996TH' "
                + ");");
    }

    public List<String> getAllNameSend(SQLiteDatabase db) {
        Cursor mCursorS = db.rawQuery("SELECT DISTINCT "+ TABLE_BOOKING_From_Name
                + " FROM " + DBClass.TABLE_BOOKING
                + " ORDER BY _ID DESC"
                + " LIMIT 50;", null);

        List<String> array = new ArrayList<String>();
        while(mCursorS.moveToNext()){
            String uname = mCursorS.getString(0); //from_name
            array.add(uname);
        }
        return array;
    }

    public List<String> getAllNameReceive(SQLiteDatabase db) {
        Cursor mCursorR = db.rawQuery("SELECT DISTINCT "+ TABLE_BOOKING_To_Name
                + " FROM " + DBClass.TABLE_BOOKING
                + " ORDER BY _ID DESC"
                + " LIMIT 50;", null);

        List<String> array = new ArrayList<String>();
        while(mCursorR.moveToNext()){
            String uname = mCursorR.getString(0); //to_name
            array.add(uname);
        }

        return array;
    }

    public List<String> getAllAddressSend(SQLiteDatabase db) {
        Cursor mCursorS = db.rawQuery("SELECT DISTINCT "+ TABLE_BOOKING_From_Address
                + " FROM " + DBClass.TABLE_BOOKING
                + " ORDER BY _ID DESC"
                + " LIMIT 50;", null);

        List<String> array = new ArrayList<String>();
        while(mCursorS.moveToNext()){
            String uname = mCursorS.getString(0); //from_name
            array.add(uname);
        }

        return array;
    }

    public List<String> getAllAddressReceive(SQLiteDatabase db) {
        Cursor mCursorR = db.rawQuery("SELECT DISTINCT "+ TABLE_BOOKING_To_Address
                + " FROM " + DBClass.TABLE_BOOKING
                + " ORDER BY _ID DESC"
                + " LIMIT 50;", null);

        List<String> array = new ArrayList<String>();
        while(mCursorR.moveToNext()){
            String uname = mCursorR.getString(0); //to_name
            array.add(uname);
        }

        return array;
    }

    public List<String> getAlldistrict(SQLiteDatabase db) {
        Cursor mCursor = db.rawQuery("SELECT DISTINCT "+ TABLE_district_Column_DISTRICT_NAME
                + " FROM " + DBClass.TABLE_district , null);

        List<String> array = new ArrayList<String>();
        while(mCursor.moveToNext()){
            String uname = mCursor.getString(0); //from_name
            array.add(uname);
        }

        return array;

    }

    public List<String> getAllamphur(SQLiteDatabase db) {
        Cursor mCursor = db.rawQuery("SELECT DISTINCT "+ TABLE_amphur_Column_AMPHUR_NAME
                + " FROM " + DBClass.TABLE_amphur , null);

        List<String> array = new ArrayList<String>();
        while(mCursor.moveToNext()){
            String uname = mCursor.getString(0); //from_name
            array.add(uname);
        }

        return array;

    }

    public List<String> getAllprovince(SQLiteDatabase db) {
        Cursor mCursor = db.rawQuery("SELECT DISTINCT "+ TABLE_province_Column_PROVINCE_NAME
                + " FROM " + DBClass.TABLE_province , null);

        List<String> array = new ArrayList<String>();
        while(mCursor.moveToNext()){
            String uname = mCursor.getString(0); //from_name
            array.add(uname);
        }

        return array;

    }

    public List<String> getAllpostcode(SQLiteDatabase db) {
        Cursor mCursor = db.rawQuery("SELECT DISTINCT "+ TABLE_amphur_Column_POSTCODE
                + " FROM " + DBClass.TABLE_amphur , null);

        List<String> array = new ArrayList<String>();
        while(mCursor.moveToNext()){
            String uname = mCursor.getString(0); //from_name
            array.add(uname);
        }

        return array;
    }

    public List<String> getAlltelSend(SQLiteDatabase db) {
        Cursor mCursorS = db.rawQuery("SELECT DISTINCT "+ TABLE_BOOKING_From_Tel
                + " FROM " + DBClass.TABLE_BOOKING
                + " ORDER BY _ID DESC"
                + " LIMIT 50;", null);

        List<String> array = new ArrayList<String>();
        while(mCursorS.moveToNext()){
            String uname = mCursorS.getString(0); //from_name
            array.add(uname);
        }
        return array;
    }

    public List<String> getAlltelReceive(SQLiteDatabase db) {
        Cursor mCursorR = db.rawQuery("SELECT DISTINCT "+ TABLE_BOOKING_To_Tel
                + " FROM " + DBClass.TABLE_BOOKING
                + " ORDER BY _ID DESC"
                + " LIMIT 50;", null);

        List<String> array = new ArrayList<String>();
        while(mCursorR.moveToNext()){
            String uname = mCursorR.getString(0); //to_name
            array.add(uname);
        }

        return array;

    }

    public ADDRESS getADDRESSByNameSend(SQLiteDatabase db,String Name) {
        Cursor mCursorS = db.rawQuery("SELECT * "
                + " FROM " + DBClass.TABLE_BOOKING
                + " WHERE "+ TABLE_BOOKING_From_Name+ " LIKE '" +Name+"%' "
                + " ORDER BY _ID DESC"
                + " LIMIT 1;", null);

        ADDRESS Item = new ADDRESS();
        while(mCursorS.moveToNext()){
            Item.setName(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_From_Name))); //from_name
            Item.setDistrict(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_From_District))); //from_district
            Item.setState(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_From_State))); //from_state
            Item.setProvince(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_From_Province))); //from_province
            Item.setPostcode(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_From_Postcode))); //
            Item.setAddress(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_From_Address))); //
            Item.setEmail(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_From_Email))); //
            Item.setTel(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_From_Tel))); //
        }
        return Item;
    }

    public ADDRESS getADDRESSByNameReceive(SQLiteDatabase db,String Name) {
        Cursor mCursorR = db.rawQuery("SELECT * "
                + " FROM " + DBClass.TABLE_BOOKING
                + " WHERE "+ TABLE_BOOKING_To_Name+ " LIKE '" +Name+"%' "
                + " ORDER BY _ID DESC"
                + " LIMIT 1;", null);

        ADDRESS Item = new ADDRESS();
        while(mCursorR.moveToNext()){
            Item.setName(mCursorR.getString(mCursorR.getColumnIndex(TABLE_BOOKING_To_Name))); //to_name
            Item.setDistrict(mCursorR.getString(mCursorR.getColumnIndex(TABLE_BOOKING_To_District))); //
            Item.setState(mCursorR.getString(mCursorR.getColumnIndex(TABLE_BOOKING_To_State))); //
            Item.setProvince(mCursorR.getString(mCursorR.getColumnIndex(TABLE_BOOKING_To_Province))); //
            Item.setPostcode(mCursorR.getString(mCursorR.getColumnIndex(TABLE_BOOKING_To_Postcode))); //
            Item.setAddress(mCursorR.getString(mCursorR.getColumnIndex(TABLE_BOOKING_To_Address))); //
            Item.setEmail(mCursorR.getString(mCursorR.getColumnIndex(TABLE_BOOKING_To_Email))); //
            Item.setTel(mCursorR.getString(mCursorR.getColumnIndex(TABLE_BOOKING_To_Tel))); //
        }

        return Item;
    }

    public ADDRESS getADDRESSByPostcodeSendOrReceive(SQLiteDatabase db, String PosCOde) {
        Cursor mCursorS = db.rawQuery("SELECT  " + DBClass.TABLE_province+"."+TABLE_province_Column_PROVINCE_NAME
                + ", " + DBClass.TABLE_amphur+"."+TABLE_amphur_Column_AMPHUR_NAME
                + ", " + DBClass.TABLE_amphur+"."+TABLE_amphur_Column_POSTCODE
                + " FROM " + DBClass.TABLE_amphur
                + " INNER JOIN "+DBClass.TABLE_province
                + " ON " + DBClass.TABLE_amphur+"."+DBClass.TABLE_amphur_Column_PROVINCE_ID + " = " + DBClass.TABLE_province+"."+DBClass.TABLE_province_Column_PROVINCE_ID
                + " WHERE "+ TABLE_amphur_Column_POSTCODE + " LIKE '" +PosCOde.substring(0,4)+"%' "
                + " LIMIT 1;", null);
        ADDRESS Item = new ADDRESS();
        while(mCursorS.moveToNext()){
            Item.setProvince(mCursorS.getString(0)); //ROVINCE_NAME
            Item.setState(mCursorS.getString(1)); //AMPHUR_NAME
            Item.setPostcode(mCursorS.getString(2)); //POSTCODE
        }

        return Item;
    }

    public List<String> getAllarrayParcelSend(SQLiteDatabase db) {
        Cursor mCursorS = db.rawQuery("SELECT DISTINCT "+ TABLE_BOOKING_Parcel_Name
                + " FROM " + DBClass.TABLE_BOOKING
                + " ORDER BY _ID DESC"
                + " LIMIT 20;", null);

        List<String> array = new ArrayList<String>();
        while(mCursorS.moveToNext()){
            String uname = mCursorS.getString(0); //from_name
            array.add(uname);
        }

        return array;
    }

    public PARCEL getPARCELByNameSend(SQLiteDatabase db, String parcel_name) {
        Cursor mCursorS = db.rawQuery("SELECT * "
                + " FROM " + DBClass.TABLE_BOOKING
                + " WHERE "+ TABLE_BOOKING_Parcel_Name+ " LIKE '%" +parcel_name+"%' "
                + " ORDER BY _ID DESC"
                + " LIMIT 1;", null);

        PARCEL Item = new PARCEL();
        while(mCursorS.moveToNext()){
            Item.setName(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_Parcel_Name)));
            Item.setWeight(Float.parseFloat(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_Parcel_Weight))));
            Item.setWidth(Float.parseFloat(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_Parcel_Width))));
            Item.setLength(Float.parseFloat(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_Parcel_Length))));
            Item.setHeight(Float.parseFloat(mCursorS.getString(mCursorS.getColumnIndex(TABLE_BOOKING_Parcel_Height))));
        }
        return Item;
    }

    public static ArrayList<bookingResponseObject> getResults(Context Con) {
        DBClass query = new DBClass(Con);
        Cursor mCursor = query.getWritableDatabase().rawQuery("SELECT * FROM " + DBClass.TABLE_BOOKING + " ORDER BY _ID DESC LIMIT 30;", null);

        ArrayList<bookingResponseObject> resultList = new ArrayList<bookingResponseObject>();
        while (mCursor.moveToNext())
        {
            try
            {
                bookingResponseObject ob = new bookingResponseObject();
                ob.setStatus(Boolean.parseBoolean(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Status))));
                ob.setTracking_code(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Tracking_code)));
                ob.setCourier_code(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Courier_code )));
                ob.setPrice(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Price))));

                ob.getFrom().setName(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_Name)));
                ob.getFrom().setAddress(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_Address )));
                ob.getFrom().setDistrict(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_District )));
                ob.getFrom().setState(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_State )));
                ob.getFrom().setProvince(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_Province )));
                ob.getFrom().setPostcode(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_Postcode )));
                ob.getFrom().setTel(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_Tel )));

                ob.getTo().setName(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_Name )));
                ob.getTo().setAddress(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_Address )));
                ob.getTo().setDistrict(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_District )));
                ob.getTo().setState(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_State )));
                ob.getTo().setProvince(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_Province )));
                ob.getTo().setPostcode(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_Postcode )));
                ob.getTo().setTel(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_Tel )));

                ob.getParcel().setName(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Parcel_Name )));
                ob.getParcel().setWeight(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Parcel_Weight ))));
                ob.getParcel().setWidth(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Parcel_Width ))));
                ob.getParcel().setLength(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Parcel_Length ))));
                ob.getParcel().setHeight(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Parcel_Height ))));

                ob.setCourier_tracking_code(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Courier_tracking_code )));
                ob.setTotal_price(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Total_Price )));
                resultList.add(ob);
            }
            catch (Exception e) {
                Toast.makeText(Con,e.getMessage(),Toast.LENGTH_LONG);
            }
        }
        return resultList;
    }

    public static bookingResponseObject getResultsBycourier_code(Context Con, String courier_tracking_code) {
        DBClass query = new DBClass(Con);
        Cursor mCursor = query.getWritableDatabase().rawQuery("SELECT * FROM " + DBClass.TABLE_BOOKING + " WHERE "+DBClass.TABLE_BOOKING_Courier_tracking_code+" = '"+courier_tracking_code+"' ORDER BY _ID DESC LIMIT 1;", null);

        bookingResponseObject ob = new bookingResponseObject();
        if (mCursor.moveToFirst())
        {
            try
            {
                ob.setTSPCHILDID(SettingActivity.BTTYPE);
                ob.setStatus(Boolean.parseBoolean(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Status))));
                ob.setTracking_code(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Tracking_code)));
                ob.setCourier_code(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Courier_code )));
                ob.setPrice(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Price))));

                ob.getFrom().setName(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_Name)));
                ob.getFrom().setAddress(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_Address )));
                ob.getFrom().setDistrict(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_District )));
                ob.getFrom().setState(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_State )));
                ob.getFrom().setProvince(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_Province )));
                ob.getFrom().setPostcode(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_Postcode )));
                ob.getFrom().setTel(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_From_Tel )));

                ob.getTo().setName(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_Name )));
                ob.getTo().setAddress(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_Address )));
                ob.getTo().setDistrict(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_District )));
                ob.getTo().setState(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_State )));
                ob.getTo().setProvince(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_Province )));
                ob.getTo().setPostcode(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_Postcode )));
                ob.getTo().setTel(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_To_Tel )));

                ob.getParcel().setName(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Parcel_Name )));
                ob.getParcel().setWeight(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Parcel_Weight ))));
                ob.getParcel().setWidth(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Parcel_Width ))));
                ob.getParcel().setLength(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Parcel_Length ))));
                ob.getParcel().setHeight(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Parcel_Height ))));

                ob.setCourier_tracking_code(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Courier_tracking_code )));
                ob.setTotal_price(mCursor.getString(mCursor.getColumnIndex(DBClass.TABLE_BOOKING_Total_Price )));

            }
            catch (Exception e) {
                Toast.makeText(Con,e.getMessage(),Toast.LENGTH_LONG);
            }
        }
        return ob;
    }
}


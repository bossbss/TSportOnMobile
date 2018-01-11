package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class bookingupdate {

    String api_key;
    String tracking_code;

    public String getApi_key() {
        return api_key;
    }
    public String getApi_keyName() {
        return "api_key";
    }
    public String getApi_keyDept() {
        return "Api key เพื่อตอบว่าเป็น Marketplace เจ้าไหน";
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getTracking_code() {
        return tracking_code;
    }
    public String getTracking_codeName() {
        return "tracking_code";
    }
    public String getTracking_codeDept() {
        return "รหัสใช้ในการติดตามพัสดุ";
    }

    public void setTracking_code(String tracking_code) {
        this.tracking_code = tracking_code;
    }

    public bookingupdateParcel[] getData() {
        return data;
    }
    public String getDataName() {
        return "data";
    }
    public String getDataDept() {
        return "ส่งมาเป็น UPDATE PARCEL OBJECT";
    }

    public void setData(bookingupdateParcel[] data) {
        this.data = data;
    }

    bookingupdateParcel[] data;

}

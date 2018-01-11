package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/23/2017.
 */

public class trackingResponseStatusObject {

    String datetime;
    String location;
    String description;
    String latlong;

    public String getDatetime() {
        return datetime;
    }
    public String getDatetimeName() {
        return "datetime";
    }
    public String getDatetimeDept() {
        return "วันที่และเวลาของสถานะอัพเดท";
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLocation() {
        return location;
    }
    public String getLocationName() {
        return "location";
    }
    public String getLocationDept() {
        return "บอกว่า state นี้ของอยู่ที่ไหน";
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }
    public String getDescriptionName() {
        return "description";
    }
    public String getDescriptionDept() {
        return "คำอธิบายของ state นี้ว่าทำอะไรเป็นอย่างไร";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatlong() {
        return latlong;
    }
    public String getLatlongName() {
        return "latlong";
    }
    public String getLatlongDept() {
        return "Lat, Long ตัวอย่าง ‘12304.1123,12314.00013’ <ว่าง>";
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getSignature() {
        return signature;
    }
    public String getSignatureName() {
        return "signature";
    }
    public String getSignatureDept() {
        return "เป็น URL รูปภาพจะมีได้ก็ต่อเมื่อสำเร็จ";
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    String signature;

}

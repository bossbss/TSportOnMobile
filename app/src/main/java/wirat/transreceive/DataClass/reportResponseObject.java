package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/23/2017.
 */

public class reportResponseObject {

    String tracking_code;
    String courier_tracking_code;

    public String getDatetime_shipping() {
        return datetime_shipping;
    }
    public String getDatetime_shippingName() {
        return "datetime_shipping";
    }
    public String getDatetime_shippingDept() {
        return "วันที่ขนส่ง เป็น Y-m-d H:i:s";
    }

    public void setDatetime_shipping(String datetime_shipping) {
        this.datetime_shipping = datetime_shipping;
    }

    String datetime_shipping;
    float weight;
    float price;
    String courier_code;

    public String gettracking_code() {
        return tracking_code;
    }
    public String gettracking_codeName() {
        return "tracking_code";
    }
    public String gettracking_codeDept() {
        return "รหัส Tracking ID เพื่อเช็คว่าสถานะเป็นอย่างไรอยู่";
    }

    public void settracking_code(String tracking_code) {
        this.tracking_code = tracking_code;
    }

    public String getCourier_tracking_code() {
        return courier_tracking_code;
    }
    public String getCourier_tracking_codeName() {
        return "courier_tracking_code";
    }
    public String getCourier_tracking_codeDept() {
        return "รหัส Tracking ของขนส่งนั้นๆเช่น EMS";
    }

    public void setCourier_tracking_code(String courier_tracking_code) {
        this.courier_tracking_code = courier_tracking_code;
    }

    public float getWeight() {
        return weight;
    }
    public String getWeightName() {
        return "weight";
    }
    public String getWeightDept() {
        return "น้ำหนักของพัสดุ g/No 1";
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getPrice() {
        return price;
    }
    public String getPriceName() {
        return "price";
    }
    public String getPriceDept() {
        return "ราคาค่าบริการ";
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCourier_code() {
        return courier_code;
    }
    public String getCourier_codeName() {
        return "courier_code";
    }
    public String getCourier_codeDept() {
        return "รหัสขนส่งที่ลูกค้าเลือกใช้";
    }

    public void setCourier_code(String courier_code) {
        this.courier_code = courier_code;
    }
}

package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/23/2017.
 */

public class automatictracking {
    String tracking_code;
    String order_status;
    float[] data;

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

    public String getOrder_status() {
        return order_status;
    }
    public String getOrder_statusName() {
        return "order_status";
    }
    public String getOrder_statusDept() {
        return "wait : รอ confirm รายการ\n" +
                "booking : อยู่ระหว่างการนำสินค้าส่งขนส่ง\n" +
                "shipping : อยู่ระหว่างการจัดส่ง ( ของไป Drop ที่ปณ )\n" +
                "complete : รายการสำเร็จ\n" +
                "cancel : รายการถูกยกเลิกด้วยอะไรก็ตาม\n";
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public float[] getData() {
        return data;
    }
    public String getDataName() {
        return "data";
    }
    public String getDataDept() {
        return "จะมีส่งให้กรณีสถานะเป็น shipping เท่านั้น <ว่าง>";
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public float getData_weight() {
        return data_weight;
    }
    public String getData_weightName() {
        return "data_weight";
    }
    public String getData_weightDept() {
        return "ยิงค่าน้ำหนักจริงไปให้ กรณีเป็น shipping";
    }

    public void setData_weight(float data_weight) {
        this.data_weight = data_weight;
    }

    public float getData_price() {
        return data_price;
    }
    public String getData_priceName() {
        return "data_price";
    }
    public String getData_priceDept() {
        return "ยิงราคากลับไปให้ กรณีเป็น shipping";
    }

    public void setData_price(float data_price) {
        this.data_price = data_price;
    }

    public String getData_datetime() {
        return data_datetime;
    }
    public String getData_datetimeName() {
        return "data_datetime";
    }
    public String getData_datetimeDept() {
        return "วันเวลาที่ของถึงขนส่ง กรณีเป็น shipping";
    }

    public void setData_datetime(String data_datetime) {
        this.data_datetime = data_datetime;
    }

    float data_weight;
    float data_price;
    String data_datetime;
}

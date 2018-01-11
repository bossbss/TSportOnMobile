package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class bookingResponseObject {

    float price;
    ADDRESS from = new ADDRESS();
    ADDRESS to = new ADDRESS();
    PARCEL parcel = new PARCEL();
    String courier_code;
    Boolean status;
    String tracking_code;

    public String getTSPCHILDID() {
        return TSPCHILDID;
    }

    public String getTSPCHILDIDName() {
        return "TSPCHILDID";
    }

    public String getTSPCHILDIDDept() {
        return "Set TS id";
    }

    public void setTSPCHILDID(String TSPCHILDID) {
        this.TSPCHILDID = TSPCHILDID;
    }

    String TSPCHILDID = "";

    public String getTotal_price() {
        return total_price;
    }

    public String getTotal_priceName() {
        return "total_price";
    }

    public String getTotal_priceDept() {
        return "ยอดรวมในบิล";
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    String total_price;

    public float getPrice() {
        return price;
    }
    public String getPriceName() {
        return "price";
    }
    public String getPriceDept() {
        return "ราคาของ Parcel นี้";
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ADDRESS getFrom() {
        return from;
    }
    public String getFromName() {
        return "from";
    }
    public String getFromDept() {
        return "ที่อยู่ต้นทาง เป็น Address Object";
    }

    public void setFrom(ADDRESS from) {
        this.from = from;
    }

    public ADDRESS getTo() {
        return to;
    }
    public String getToName() {
        return "to";
    }
    public String getToDept() {
        return "ที่อยู่ปลาtยทาง เป็น Address Object";
    }

    public void setTo(ADDRESS to) {
        this.to = to;
    }

    public PARCEL getParcel() {
        return parcel;
    }
    public String getParcelName() {
        return "parcel";
    }
    public String getParcelDept() {
        return "ขนาดของพัสดุ เป็น Parcel Object";
    }

    public void setParcel(PARCEL parcel) {
        this.parcel = parcel;
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

    public Boolean getStatus() {
        return status;
    }
    public String getStatusName() {
        return "status";
    }
    public String getStatusDept() {
        return "True : รายการ booking ได้ไม่มีปัญหา False : รายการไม่สำเร็จ";
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    String courier_tracking_code;

    Boolean MarkSelect = false;

    public void setMarkSelect(Boolean status) {
        this.MarkSelect = status;
    }

    public Boolean getMarkSelect() {
        return MarkSelect;
    }
    public String getMarkSelectName() {
        return "MarkSelect";
    }
    public String getMarkSelectDept() {
        return "MarkSelect";
    }
}

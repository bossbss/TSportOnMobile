package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class bookingResponse {

    Boolean status;
    Integer code;
    Integer purchase_id;
    float total_price;

    public Boolean getStatus() {
        return status;
    }
    public String getStatusName() {
        return "status";
    }
    public String getStatusDept() {
        return "True : สำเร็จ False : ไม่สำเร็จ";
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }
    public String getCodeName() {
        return "code";
    }
    public String getCodeDept() {
        return "400 : ไม่สำเร็จเพราะไม่ถูกต้อง จะมีเมื่อ error เท่านั้น <ว่าง>";
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getPurchase_id() {
        return purchase_id;
    }
    public String getPurchase_idName() {
        return "purchase_id";
    }
    public String getPurchase_idDept() {
        return "เลขที่ใบสั่งซื้อจาก Shippop";
    }

    public void setPurchase_id(Integer purchase_id) {
        this.purchase_id = purchase_id;
    }

    public float getTotal_price() {
        return total_price;
    }
    public String getTotal_priceName() {
        return "total_price";
    }
    public String getTotal_priceDept() {
        return "ยอดราคารวมของ Purchase นี้";
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public bookingResponseObject[] getData() {
        return data;
    }
    public String getDataName() {
        return "data";
    }
    public String getDataDept() {
        return "จำนวนของที่จะส่งเข้ามาเช็คราคาส่งมาได้หลายชิ้นเป็น Array";
    }

    public void setData(bookingResponseObject[] data) {
        this.data = data;
    }

    bookingResponseObject[] data;




}

package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/23/2017.
 */

public class label {

    String api_key;
    Integer purchase_id;
    String tracking_code;
    String size;

    public String getSize() {
        return size;
    }
    public String getSizeName() {
        return "size";
    }
    public String getSizeDept() {
        return "A4 : ขนาดจะเป็น A4\n" +
                "A5 : ขนาดจะเป็น A5\n" +
                "receipt : ขนาดจะเป็นใบเสร็จ ( Default )\n" +
                "letter : ซองจดหมาย\n  <ว่าง>";
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLogo() {
        return logo;
    }
    public String getLogoName() {
        return "logo";
    }
    public String getLogoDept() {
        return "ส่งมาเป็น Url หากต้องการเปลี่ยน logo Default เป็นของร้านค้านั้นๆ <ว่าง>";
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    String logo;

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

    public String gettracking_code() {
        return tracking_code;
    }
    public String gettracking_codeName() {
        return "tracking_code";
    }
    public String gettracking_codeDept() {
        return "รหัส Tracking ID เพื่อเช็คว่าสถานะเป็นอย่างไรอยู่  <ว่าง>";
    }

    public void settracking_code(String tracking_code) {
        this.tracking_code = tracking_code;
    }
}

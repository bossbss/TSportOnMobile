package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/23/2017.
 */

public class tracking_purchase {
    String api_key;
    Integer purchase_id;
    String email;

    public String getApi_key() {
        return api_key;
    }
    public String getApi_keyName() {
        return "api_key";
    }
    public String getApi_keyDept() {
        return "Api key เพื่อตอบว่าเป็น Marketplace เจ้าไหน";
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

    public String getEmail() {
        return email;
    }
    public String getEmailName() {
        return "email";
    }
    public String getEmailDept() {
        return "Email ของพ่อค้าแม่ค้า เพื่อใช้ยืนยันว่าเป็นคนสร้าง order";
    }
}

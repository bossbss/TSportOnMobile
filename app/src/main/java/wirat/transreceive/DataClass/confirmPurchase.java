package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/23/2017.
 */

public class confirmPurchase {
    String api_key;
    Integer purchase_id;

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
}

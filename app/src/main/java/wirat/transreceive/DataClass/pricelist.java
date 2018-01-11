package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class pricelist {
    String api_key;

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

    public Packets[] getData() {
        return data;
    }
    public String getDataName() {
        return "data";
    }
    public String getDataDept() {
        return "จำนวนของที่จะส่งเข้ามาเช็คราคาส่งมาได้หลายชิ้นเป็น Array Packets";
    }

    public void setData(Packets[] data) {
        this.data = data;
    }

    Packets[] data;
}

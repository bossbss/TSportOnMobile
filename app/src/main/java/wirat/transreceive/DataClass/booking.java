package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class booking {
    String api_key;
    String email;
    String promo_code;
    String token;
    String url_success;
    String url_fail;

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

    public String getEmail() {
        return email;
    }
    public String getEmailName() {
        return "email";
    }
    public String getEmailDept() {
        return "Email ของพ่อค้าแม่ค้า";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPromo_code() {
        return promo_code;
    }
    public String getPromo_codeName() {
        return "promo_code";
    }
    public String getPromo_codeDept() {
        return "รหัสส่วนโปรโมชั่น ถ้ามี ( ยังไม่เปิดให้ใช้งาน ) <ว่าง>";
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public String getToken() {
        return token;
    }
    public String getTokenName() {
        return "token";
    }
    public String getTokenDept() {
        return "สำหรับยืนยันตัวตนแทนการใช้ email สำหรับลูกค้าที่เป็น SHIPPOP B2C เท่านั้น <ว่าง>";
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl_success() {
        return url_success;
    }
    public String getUrl_successName() {
        return "url_success";
    }
    public String getUrl_successDept() {
        return "กรณีใช้ payment form ถ้าสำเร็จให้ไปหน้าไหน";
    }

    public void setUrl_success(String url_success) {
        this.url_success = url_success;
    }

    public String getUrl_fail() {
        return url_fail;
    }
    public String getUrl_failName() {
        return "url_fail";
    }
    public String getUrl_failDept() {
        return "กรณีใช้ payment form ถ้าไม่สำเร็จให้ไปหน้าไหน";
    }

    public void setUrl_fail(String url_fail) {
        this.url_fail = url_fail;
    }

    public bookingPackets[] getData() {
        return data;
    }
    public String getDataName() {
        return "data";
    }
    public String getDataDept() {
        return "จำนวนของที่จะส่งเข้ามาเช็คราคาส่งมาได้หลายชิ้นเป็น Arry";
    }

    public void setData(bookingPackets[] data) {
        this.data = data;
    }

    bookingPackets[] data;
}

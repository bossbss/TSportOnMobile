package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class ADDRESS {

    public String getProvince() {
        return province;
    }
    public String getProvinceName() {
        return "province";
    }
    public String getProvinceDept() {
        return "จังหวัด";
    }

    public void setProvince(String province) {
        this.province = province;
    }

    String province;

    public String getState() {
        return state;
    }

    public String getStateName() {
        return "state";
    }

    public String getStateDept() {
        return "เขต/อำเภอ";
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }
    public String getDistrictNane() {
        return "district";
    }
    public String getDistrictDept() {
        return "แขวง/ตำบล";
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostcode() {
        return postcode;
    }
    public String getPostcodeName() {
        return "postcode";
    }
    public String getPostcodeDept() {
        return "รหัสไปรษณีย์";
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }
    public String getAddressName() {
        return "address";
    }
    public String getAddressDept() {
        return "ที่อยู่";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public String getNameName() {
        return "name";
    }
    public String getNameDept() {
        return "ชื่อ";
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTel() {
        return tel;
    }
    public String getTelName() {
        return "tel";
    }
    public String getTelDept() {
        return "เบอร์โทร";
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }
    public String getEmailName() {
        return "email";
    }
    public String getEmailDept() {
        return "อีเมล์ <ว่าง>";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLat() {
        return lat;
    }
    public String getLatName() {
        return "lat";
    }
    public String getLatDept() {
        return "สำหรับขนส่ง Skootar <ว่าง>";
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }
    public String getLngName() {
        return "lng";
    }
    public String getLngDept() {
        return "สำหรับขนส่ง Skootar <ว่าง>";
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    String state = "";
    String district= "";
    String postcode= "";
    String address= "";
    String name= "";
    String tel= "";
    String email= "";
    String lat= "";
    String lng= "";
}

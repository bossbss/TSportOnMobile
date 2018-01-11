package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class POSTOFFICE {
    Integer id;
    String name;
    String postcode;

    public Integer getId() {
        return id;
    }
    public String getIdName() {
        return "id";
    }
    public String getIdDept() {
        return "รหัสสถานที่ ปณ รับฝาก";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getNameName() {
        return "name";
    }
    public String getNameDept() {
        return "ชื่อสถานที่ ปณ รับฝาก";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }
    public String getPostcodeName() {
        return "postcode";
    }
    public String getPostcodeDept() {
        return "รหัสไปรษณีย์ที่ทำการนั้นๆ";
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLatlong() {
        return latlong;
    }
    public String getLatlongName() {
        return "latlong";
    }
    public String getLatlongDept() {
        return "lat, long ที่ทำการไปรษณีย์ เพื่อเอาไปทำ Map";
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    String latlong;
}

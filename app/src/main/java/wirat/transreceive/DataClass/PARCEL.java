package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class PARCEL {

    public int getParcel_size() {
        return parcel_size;
    }
    public String getParcel_sizeName() {
        return "parcel_size";
    }
    public String getParcel_sizeDept() {
        return "รหัสขนาดของกล่อง <ว่าง>";
    }

    public void setParcel_size(int parcel_size) {
        this.parcel_size = parcel_size;
    }

    public String getName() {
        return name;
    }
    public String getNameName() {
        return "name";
    }
    public String getNameDept() {
        return "ชื่อของ Package";
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }
    public String getWeightName() {
        return "weight";
    }
    public String getWeightDept() {
        return "น้ำหนักของพัสดุ g/No 1";
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWidth() {
        return width;
    }
    public String getWidthName() {
        return "width";
    }
    public String getWidthDept() {
        return "ความมกว้าง ของกล่องพัสดุ";
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getLength() {
        return length;
    }
    public String getLengthName() {
        return "length";
    }public String getLengthDept() {
        return "ความมยาว ของกล่องพัสดุ";
    }


    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }
    public String getHeightName() {
        return "height";
    }
    public String getHeightDept() {
        return "ความมสูง ของกล่องพัสดุ";
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getDefault() {
        return Default;
    }
    public String getDefaultName() {
        return "Default";
    }
    public String getDefaultDept() {
        return "กล่องนี้เป็นขนาด Default 0 ไม่ 1";
    }

    public void setDefault(int aDefault) {
        Default = aDefault;
    }

    int parcel_size = 0;
    String name = "";
    float weight = 0;
    float width = 0;
    float length = 0;
    float height = 0;
    int Default = 0;
}

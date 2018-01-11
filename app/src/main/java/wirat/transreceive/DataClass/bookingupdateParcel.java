package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class bookingupdateParcel {
    float weight;
    float width;
    float length;

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

    float height;
}

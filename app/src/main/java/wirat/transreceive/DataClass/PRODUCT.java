package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class PRODUCT {

    String product_code;
    String name;
    String detail;
    float price;

    public String getProduct_code() {
        return product_code;
    }

    public String getProduct_codeName() {
        return "product_code";
    }

    public String getProduct_codeDept() {
        return "รหัสของสินค้าที่ขาย";
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getName() {
        return name;
    }

    public String getNameName() {
        return "name";
    }

    public String getNameDept() {
        return "ชื่อของสินค้า";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public String getDetailName() {
        return "detail";
    }

    public String getDetailDept() {
        return "คำอธิบาย <ว่าง>";
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public float getPrice() {
        return price;
    }

    public String getPriceName() {
        return "price";
    }

    public String getPriceDept() {
        return "ราคา <ว่าง>";
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getAmountName() {
        return "amount";
    }

    public String getAmountDept() {
        return "จำนวน <ว่าง>";
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public float getWeight() {
        return weight;
    }

    public String getWeightName() {
        return "weight";
    }

    public String getWeightDept() {
        return "น้ำหนักของพัสดุ";
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    Integer amount;
    float weight;


}

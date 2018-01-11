package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class pricelistResponseCourierObject {

    Integer price;
    String estimate_time;
    Boolean available;

    public Integer getPrice() {
        return price;
    }
    public String getPriceName() {
        return "price";
    }
    public String getPriceDept() {
        return "ราคาค่าขนส่ง";
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getEstimate_time() {
        return estimate_time;
    }
    public String getEstimate_timeName() {
        return "estimate_time";
    }
    public String getEstimate_timeDept() {
        return "เงื่อนไขระยะเวลาการส่งของ ไม่เกิน กี่วัน / ชั่วโมง";
    }

    public void setEstimate_time(String estimate_time) {
        this.estimate_time = estimate_time;
    }

    public Boolean getAvailable() {
        return available;
    }
    public String getAvailableName() {
        return "available";
    }
    public String getAvailableDept() {
        return "True : ขนส่งสามารถจัดส่งได้ False : ขนส่งไม่สามารถจัดส่งได้";
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getRemark() {
        return remark;
    }
    public String getRemarkName() {
        return "remark";
    }
    public String getRemarkDept() {
        return "เหตุผลที่ทำไมจัดส่งไม่ได้ / หรือเงื่อนไขในการจัดส่ง <ว่าง>";
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    String remark;

}

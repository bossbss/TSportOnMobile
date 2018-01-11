package wirat.transreceive.DataClass;

import java.util.Date;
import java.sql.Time;

/**
 * Created by BOSS on 11/22/2017.
 */

public class bookingPackets {

    ADDRESS from;
    ADDRESS to;
    PARCEL parcel;

    public ADDRESS getFrom() {
        return from;
    }
    public String getFromName() {
        return "from";
    }
    public String getFromDept() {
        return "ที่อยู่ต้นทาง";
    }

    public void setFrom(ADDRESS from) {
        this.from = from;
    }

    public ADDRESS getTo() {
        return to;
    }
    public String getToName() {
        return "to";
    }
    public String getToDept() {
        return "ที่อยู่ปลาtยทาง";
    }

    public void setTo(ADDRESS to) {
        this.to = to;
    }

    public PARCEL getParcel() {
        return parcel;
    }
    public String getParcelName() {
        return "parcel";
    }
    public String getParcelDept() {
        return "ขนาดของพัสดุ";
    }

    public void setParcel(PARCEL parcel) {
        this.parcel = parcel;
    }

    public PRODUCT[] getProduct() {
        return product;
    }
    public String getProductName() {
        return "product";
    }
    public String getProductDept() {
        return "ข้อมูลสินค้า <ว่าง>";
    }

    public void setProduct(PRODUCT[] product) {
        this.product = product;
    }

    public String getCourier_code() {
        return courier_code;
    }
    public String getCourier_codeName() {
        return "courier_code";
    }
    public String getCourier_codeDept() {
        return "รหัสขนส่งที่ลูกค้าเลือกใช้";
    }

    public void setCourier_code(String courier_code) {
        this.courier_code = courier_code;
    }

    public String getRemark() {
        return remark;
    }
    public String getRemarkName() {
        return "remark";
    }
    public String getRemarkDept() {
        return "ระบุหมายเหตุเพิ่มเติมสำหรับการสร้างรายการ <ว่าง>";
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Time getStarttime() {
        return starttime;
    }
    public String getStarttimeName() {
        return "starttime";
    }
    public String getStarttimeDept() {
        return "กำหนดเวลาในการเข้ารับสินค้า (สำหรับเลือกขนส่ง Skootar เท่านั้น) <ว่าง>";
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    public Time getFinishtime() {
        return finishtime;
    }
    public String getFinishtimeName() {
        return "finishtime";
    }
    public String getFinishtimeDept() {
        return "กำหนดเวลาที่สินค้าจะถึงผู้รับ (สำหรับเลือกขนส่ง Skootar เท่านั้น) <ว่าง>";
    }

    public void setFinishtime(Time finishtime) {
        this.finishtime = finishtime;
    }

    PRODUCT[] product;
    String  courier_code;
    String remark;
    Time starttime;
    Time finishtime;

    public Date getJobdate() {
        return jobdate;
    }
    public String getJobdateName() {
        return "jobdate";
    }
    public String getJobdateDept() {
        return "กำหนดวันในการเข้ารับสินค้า (สำหรับเลือกขนส่ง skootar เท่านั้น) <ว่าง>";
    }

    public void setJobdate(Date jobdate) {
        this.jobdate = jobdate;
    }

    Date jobdate;



}

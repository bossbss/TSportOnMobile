package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class Packets {
    ADDRESS from;
    ADDRESS to;

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
        return "ที่อยู่ปลายทาง";
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

    PARCEL parcel;

    String courier_code;

    public String getCourier_code() {
        return courier_code;
    }
    public String getCourier_codeName() {
        return "courier_code";
    }
    public String getCourier_codeDept() {
        return "yes รหัสขนส่งที่ลูกค้าเลือกใช้";
    }

    public void setCourier_code(String courier_code) {
        this.courier_code = courier_code;
    }

    public Integer getShowall() {
        return showall;
    }
    public String getShowallName() {
        return "showall";
    }
    public String getShowallDept() {
        return "yes 0 (default) : แสดงเฉพาะขนส่งที่ใช้งานได้ 1";
    }

    public void setShowall(Integer showall) {
        this.showall = showall;
    }

    Integer showall;
}

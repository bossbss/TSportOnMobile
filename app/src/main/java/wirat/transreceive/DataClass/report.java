package wirat.transreceive.DataClass;

import java.util.Date;

/**
 * Created by BOSS on 11/23/2017.
 */

public class report {

    String api_key;

    public Date getStart_date() {
        return start_date;
    }
    public String getStart_dateName() {
        return "start_date";
    }
    public String getStart_dateDept() {
        return "วันที่เริ่มต้น เป็น Y-m-d : 2016-03-01";
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    Date start_date;

    public Date getEnd_date() {
        return end_date;
    }
    public String getEnd_dateName() {
        return "end_date";
    }
    public String getEnd_dateDept() {
        return "วันที่สิ้นสุด เป็น Y-m-d : 2016-04-30";
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    Date end_date;

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
}

package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/23/2017.
 */

public class tracking {
    String tracking_code;
    public String getTracking_code() {
        return tracking_code;
    }
    public String getTracking_codeName() {
        return "tracking_code";
    }
    public String getTracking_codeDept() {
        return "รหัสใช้ในการติดตามพัสดุ";
    }

    public void setTracking_code(String tracking_code) {
        this.tracking_code = tracking_code;
    }
}

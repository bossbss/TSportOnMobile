package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/23/2017.
 */

public class confirmPurchaseResponse {
    Boolean status;
    Integer code;

    public Boolean getStatus() {
        return status;
    }
    public String getStatusName() {
        return "status";
    }
    public String getStatusDept() {
        return "True : สำเร็จ False : ไม่สำเร็จ";
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }
    public String getCodeName() {
        return "code";
    }
    public String getCodeDept() {
        return "400 : ไม่สำเร็จเพราะไม่ถูกต้อง จะมีเมื่อ error เท่านั้น <ว่าง>";
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

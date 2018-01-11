package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/22/2017.
 */

public class trackingResponse {

    Boolean status;
    Integer code;
    String courier_code;

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

    public String getOrder_cancel_detail() {
        return order_cancel_detail;
    }
    public String getOrder_cancel_detailName() {
        return "order_cancel_detail";
    }
    public String getOrder_cancel_detailDept() {
        return "เหตุผลที่โดนยกเลิก จะมีเมื่อ order_status = cancel <ว่าง>";
    }

    public void setOrder_cancel_detail(String order_cancel_detail) {
        this.order_cancel_detail = order_cancel_detail;
    }

    String order_cancel_detail;

    public String getOrder_status() {
        return order_status;
    }
    public String getOrder_statusName() {
        return "order_status";
    }
    public String getOrder_statusDept() {
        return "wait : รอ confirm รายการ\n" +
                "booking : อยู่ระหว่างการนำสินค้าส่งขนส่ง\n" +
                "shipping : อยู่ระหว่างการจัดส่ง\n" +
                "complete : รายการสำเร็จ\n" +
                "cancel : รายการถูกยกเลิกด้วยอะไรก็ตาม\n";
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    String order_status;

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

    public trackingResponseStatusObject[] getState() {
        return state;
    }
    public String getStateName() {
        return "state";
    }
    public String getStateDept() {
        return "ตอบกลับเป็น STATE OBJEC";
    }

    public void setState(trackingResponseStatusObject[] state) {
        this.state = state;
    }

    trackingResponseStatusObject[] state;

}

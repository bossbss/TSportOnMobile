package wirat.transreceive.DataClass;

/**
 * Created by BOSS on 11/23/2017.
 */

public class COURIERALL{
    public COURIER Courier_Retrun(String TypeC){
        COURIER THP = new COURIER();
        switch (TypeC){
            case "THP":
                THP.courier_code = "THP";
                THP.courier_Name = "ไปรษณีย์ไทย EMS";
                THP.courier_condition = "-ต้องนำไปส่งเองเท่านั้น ภายใน (47 ปณ)\n" +
                        "-นำส่งได้ตั้งแต่หลัง 1 ชั่วโมงไปหลังสร้างรายการ\n" +
                        "-ระยะเวลาจัดส่ง 1-3 วัน\n" +
                        "-น้ำหนักสูงสุง 20 กิโลกรัม\n";
                THP.courier_Enable = true;
                break;
            case "TP2":
                THP.courier_code = "TP2";
                THP.courier_Name = "ไปรษณีย์ ลงทะเบียน";
                THP.courier_condition = "-ต้องนำไปส่งเองเท่านั้น ภายใน (47 ปณ)\n" +
                                        "-นำส่งได้หลังสร้างรายการ 1 ชั่วโมง\n" +
                                        "-ระยะเวลาจัดส่ง 1-6 วัน\n" +
                                        "-น้ำหนักสูงสุง 2 กิโลกรัม\n";
                THP.courier_Enable = true;
                break;
            case "APF":
                THP.courier_code = "APF";
                THP.courier_Name = "Alphafast";
                THP.courier_condition = "-ไปรับสินค้าที่หน้าบ้านลูกค้าเท่านั้น\n" +
                                        "-ราคาขั้นต่ำ 70 บาท\n" +
                                        "-กรุงเทพและปริมณทลเท่านั้น\n" +
                                        "-ระยะเวลาจัดส่ง 1 วัน\n" +
                                        "-น้ำหนักสูงสุง 5 กิโลกรัม\n";
                THP.courier_Enable = true;
                break;
            case "KRY":
                THP.courier_code = "KRY";
                THP.courier_Name = "Kerry Express";
                THP.courier_condition = "-ไม่มี";
                THP.courier_Enable = false;
                break;
            case "SCG":
                THP.courier_code = "SCG";
                THP.courier_Name = "SCG Yamato Express";
                THP.courier_condition = "-กรุงเทพและปริมณฑล\n" +
                                        "-ภาคตะวันออก\n" +
                                        "-ระยะเวลาจัดส่ง 1-2 วัน\n" +
                                        "-ขนาดกล่อง (กว้าง+ยาว+สูง) ไม่เกิน 160 เซนติเมตร\n";
                THP.courier_Enable = true;
                break;
            case "SCGF":
                THP.courier_code = "SCGF";
                THP.courier_Name = "SCG Yamato Express Chilled";
                THP.courier_condition = "-กรุงเทพและปริมณฑล\n" +
                                        "-ทำรายการล่วงหน้า 1วัน \n" +
                                        "-ระยะเวลาจัดส่ง 1-2 วัน\n" +
                                        "-ขนาดกล่อง (กว้าง+ยาว+สูง) ไม่เกิน 120 เซนติเมตร\n" +
                                        "-ควบคุมอุณหภูมิ 0 - 8องศาเซลเซียส\n";
                THP.courier_Enable = true;
                break;
            case "SCGC":
                THP.courier_code = "SCGC";
                THP.courier_Name = "SCG Yamato Express Frozen";
                THP.courier_condition = "-กรุงเทพและปริมณฑล\n" +
                                        "-ทำรายการล่วงหน้า 1วัน \n" +
                                        "-ระยะเวลาจัดส่ง 1-2 วัน\n" +
                                        "-ขนาดกล่อง (กว้าง+ยาว+สูง) ไม่เกิน 120 เซนติเมตร\n" +
                                        "-ควบคุมอุณหภูมิต่ำกว่า -15 องศาเซลเซียส\n";
                THP.courier_Enable = true;
                break;
            case "NJV":
                THP.courier_code = "NJV";
                THP.courier_Name = "Ninjavan";
                THP.courier_condition = "-กรุงเทพและปริมณฑล\n" +
                                        "-ระยะเวลาจัดส่ง 1-4 วันทำการ\n" +
                                        "-ขนาดกล่อง (กว้าง+ยาว+สูง) ไม่เกิน 120 เซนติเมตร\n" +
                                        "-ยืนยันรายการก่อน 15.00 เข้ารับสินค้าวันเดียวกัน และหลัง 15.00 เข้ารับสินค้าวันถัดไป\n";
                THP.courier_Enable = true;
                break;
            case "GRE":
                THP.courier_code = "GRE";
                THP.courier_Name = "Grab Express";
                THP.courier_condition = "-ไม่มี";
                THP.courier_Enable = false;
                break;
            case "GRB":
                THP.courier_code = "GRB";
                THP.courier_Name = "Grab Bike";
                THP.courier_condition = "-ไม่มี";
                THP.courier_Enable = false;
                break;
            case "DHL":
                THP.courier_code = "DHL";
                THP.courier_Name = "DHL";
                THP.courier_condition = "-ไม่มี";
                THP.courier_Enable = false;
                break;
            case "LLM":
                THP.courier_code = "LLM";
                THP.courier_Name = "Lalamove";
                THP.courier_condition = "-ไม่มี";
                THP.courier_Enable = false;
                break;
            case "NKS":
                THP.courier_code = "NKS";
                THP.courier_Name = "Niko Logistic";
                THP.courier_condition = "-ไม่มี";
                THP.courier_Enable = true;
                break;
            default:
                THP.courier_code = "";
                THP.courier_Name = "ไม่ตรงกับบริการใด";
                THP.courier_condition = "-ไม่มี";
                THP.courier_Enable = false;
        }
        return THP;
    }

}

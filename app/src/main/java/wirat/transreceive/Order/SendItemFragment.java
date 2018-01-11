package wirat.transreceive.Order;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.Code128;
import com.onbarcode.barcode.android.Code39;
import com.onbarcode.barcode.android.EAN13;
import com.onbarcode.barcode.android.IBarcode;
import com.zebra.sdk.graphics.internal.CompressedBitmapOutputStreamCpcl;
import com.zebra.sdk.graphics.internal.DitheredImageProvider;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import wirat.transreceive.AlertDialogManager;
import wirat.transreceive.DataBaseHelper.DBClass;
import wirat.transreceive.DataClass.bookingResponseObject;
import wirat.transreceive.R;
import wirat.transreceive.SettingActivity;

public class SendItemFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public ListView ListBooking;
    public Button BtnprintฺBill, BtnprintฺStiker;

    CustomAdapterListBooking Adf;
    ArrayList<bookingResponseObject> objectList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_send_item, container, false);
        ListBooking = (ListView) V.findViewById(R.id.ListBooking);
        BtnprintฺBill = (Button) V.findViewById(R.id.BtnprintฺBill);
        BtnprintฺStiker = (Button) V.findViewById(R.id.BtnprintฺStiker);

        ListBooking.setLongClickable(true);
        ListBooking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> view, View arg1,
                                    int pos, long id) {

                // save index and top position
                int index = ListBooking.getFirstVisiblePosition();
                View v = ListBooking.getChildAt(0);
                int top = (v == null) ? 0 : (v.getTop() - ListBooking.getPaddingTop());

                objectList.get(pos).setMarkSelect(!objectList.get(pos).getMarkSelect());
                Adf = new CustomAdapterListBooking(getActivity(), objectList);
                ListBooking.setAdapter(Adf);

                ListBooking.setSelectionFromTop(index, top);
            }
        });

        BtnprintฺBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    /***Set Text Image***/
                    ArrayList<String> textImg = new ArrayList<String>();
                    int index = -1;
                    int Totalprice = 0;

                    index++;
                    textImg.add(index, "*************************************************");
                    index++;
                    DateFormat df = new DateFormat();
                    textImg.add(index, "วันที่พิมพ์ " + df.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date()));
                    index++;
                    textImg.add(index, "*************************************************");
                    for (int i = 0; i < objectList.size(); i++) {
                        bookingResponseObject item = objectList.get(i);
                        if (item.getMarkSelect()) {
                            objectList.get(i).setMarkSelect(false);

                            index++;
                            textImg.add(index, "เลขที่ติดตามสินค้า : " + objectList.get(i).getCourier_tracking_code().trim());
                            index++;
                            textImg.add(index, objectList.get(i).getTracking_code().trim());
                            index++;
                            textImg.add(index, "ผู้ส่ง : " + objectList.get(i).getFrom().getName().trim() + " :(ปณ. " + objectList.get(i).getFrom().getPostcode() + ")");
                            index++;
                            textImg.add(index, "ผู้รับ : " + objectList.get(i).getTo().getName().trim() + " :(ปณ. " + objectList.get(i).getTo().getPostcode() + ")");
                            index++;
                            textImg.add(index, "ราคา : " + String.valueOf(objectList.get(i).getTotal_price()) + " บาท ");

                            Totalprice = Totalprice + Integer.parseInt(objectList.get(i).getTotal_price());

                            index++;
                            textImg.add(index, "-----------------------------------------------");

                            Log.d("ItemPrint : ", item.getCourier_code());
                        }
                    }

                    index++;
                    textImg.add(index, "ยอดรวม....." + String.valueOf(Totalprice) + "......บาท");
                    index++;
                    textImg.add(index, "*************************************************");

                    /***Create Image***/
                    View v = new CanvasBill(getActivity(), textImg);
                    Bitmap bitmap = Bitmap.createBitmap(520/*width*/, 40 * textImg.size() /*height*/, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    v.draw(canvas);

                    /***Show Image***/
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    dialogBuilder.setIcon(R.drawable.applyicon);
                    dialogBuilder.setTitle("ใบเสร็จรับเงิน");
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alertimage, null);
                    dialogBuilder.setView(dialogView);

                    ImageView ImageV = (ImageView) dialogView.findViewById(R.id.dialog_imageview);
                    ImageV.setImageBitmap(bitmap);
                    final Bitmap bitmapf = bitmap;

                    dialogBuilder.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            File file = saveBitMap(getActivity(), bitmapf);
                            if (file != null) {
                                Toast.makeText(getActivity(), "Drawing saved to the gallery!", Toast.LENGTH_LONG);
                            } else {
                                Toast.makeText(getActivity(), "Oops! Image could not be saved.", Toast.LENGTH_LONG);
                            }
                            dialog.cancel();
                        }
                    });

                    dialogBuilder.setNeutralButton("พิมพ์", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //SettingActivity.pairPrinter(textprintf, getActivity());
                            SettingActivity.pairPrinterImage(bitmapf, getActivity());
                            dialog.cancel();
                        }
                    });

                    dialogBuilder.setNegativeButton("ส่งต่อ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("image/*");
                            String bitmapPath = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmapf, "title", null);
                            Uri bitmapUri = Uri.parse(bitmapPath);
                            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                            startActivity(Intent.createChooser(intent, "Shared App"));
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    /***Clear List***/
                    Adf = new CustomAdapterListBooking(getActivity(), objectList);
                    ListBooking.setAdapter(Adf);

                } catch (Exception ex) {
                    new AlertDialogManager().showAlertDialog(getActivity(), "Error", ex.getMessage(), true);
                }
            }
        });

        BtnprintฺStiker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    /***Set Text Image***/
                    ArrayList<String> textImg = new ArrayList<String>();
                    String Courier_tracking_code = "";
                    String tracking_code = "";
                    int index = -1;
                    index++;
                    textImg.add(index, "------------------------------------------");

                    for (int i = 0; i < objectList.size(); i++) {
                        bookingResponseObject item = objectList.get(i);
                        if (item.getMarkSelect()) {
                            objectList.get(i).setMarkSelect(false);
                            Courier_tracking_code = objectList.get(i).getCourier_tracking_code().trim();
                            tracking_code = objectList.get(i).getTracking_code();

                            index++;
                            textImg.add(index, "ผู้รับ:"+objectList.get(i).getTo().getName());
                            index++;
                            textImg.add(index, objectList.get(i).getTo().getAddress() + " ");
                            index++;
                            textImg.add(index, "ต."+objectList.get(i).getTo().getDistrict() + " "
                                                    + "อ."+objectList.get(i).getTo().getState() + " ");
                            index++;
                            textImg.add(index, "จ."+objectList.get(i).getTo().getProvince()+" "
                                                          + objectList.get(i).getTo().getPostcode());
                            index++;
                            textImg.add(index,"โทร. " + objectList.get(i).getTo().getTel());

                            Log.d("ItemPrint : ", item.getCourier_code());
                            break;
                        }
                    }

                    index++;
                    textImg.add(index, "------------------------------------------");

                    /***Create Image***/
                    View v = new CanvasSticker(getActivity(), textImg,Courier_tracking_code,tracking_code);
                    Bitmap bitmap = Bitmap.createBitmap(800/*width*/, 450 /*height*/, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    v.draw(canvas);

                    /***Show Image***/
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    dialogBuilder.setIcon(R.drawable.applyicon);
                    dialogBuilder.setTitle("ใบปะหน้าพัสดุ");
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alertimage, null);
                    dialogBuilder.setView(dialogView);

                    ImageView ImageV = (ImageView) dialogView.findViewById(R.id.dialog_imageview);
                    ImageV.setImageBitmap(bitmap);
                    final Bitmap bitmapf = bitmap;

                    dialogBuilder.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            File file = saveBitMap(getActivity(), bitmapf);
                            if (file != null) {
                                Toast.makeText(getActivity(), "Drawing saved to the gallery!", Toast.LENGTH_LONG);
                            } else {
                                Toast.makeText(getActivity(), "Oops! Image could not be saved.", Toast.LENGTH_LONG);
                            }
                            dialog.cancel();
                        }
                    });

                    dialogBuilder.setNeutralButton("พิมพ์", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //SettingActivity.pairPrinter(textprintf, getActivity());
                            Matrix matrix = new Matrix();
                            matrix.postRotate(90);
                            SettingActivity.pairPrinterImage(Bitmap.createBitmap(bitmapf, 0, 0, bitmapf.getWidth(), bitmapf.getHeight(), matrix, true), getActivity());
                            dialog.cancel();
                        }
                    });

                    dialogBuilder.setNegativeButton("ส่งต่อ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("image/*");
                            String bitmapPath = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmapf, "title", null);
                            Uri bitmapUri = Uri.parse(bitmapPath);
                            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                            startActivity(Intent.createChooser(intent, "Shared App"));
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    /***Clear List***/
                    Adf = new CustomAdapterListBooking(getActivity(), objectList);
                    ListBooking.setAdapter(Adf);

                } catch (Exception ex) {
                    new AlertDialogManager().showAlertDialog(getActivity(), "Error", ex.getMessage(), true);
                }
            }
        });

        return V;
    }

    @Override
    public void onResume() {
        super.onResume();
        objectList = DBClass.getResults(getActivity());
        Adf = new CustomAdapterListBooking(getActivity(), objectList);
        ListBooking.setAdapter(Adf);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onContentFragmentInteraction(String string);
    }

    public class CanvasBill extends View {
        ArrayList<String> text = new ArrayList<String>();

        public CanvasBill(Context context, ArrayList<String> Text) {
            super(context);
            this.text = Text;
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            Paint pText = new Paint();
            pText.setColor(Color.BLACK);
            pText.setTextSize(20);
            for (int i = 0; i < text.size(); i++) {
                canvas.drawText(text.get(i), 20, (40 * (1 + i)), pText);
            }
        }
    }

    public class CanvasSticker extends View {
        ArrayList<String> text = new ArrayList<String>();
        String Courier_tracking_code = "";
        String tracking_code = "";
        public CanvasSticker(Context context, ArrayList<String> Text,String Courier_tracking_code,String tracking_code) {
            super(context);
            this.text = Text;
            this.Courier_tracking_code = Courier_tracking_code;
            this.tracking_code = tracking_code;
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);

            int Xwidth = 0;
            int Yheight = 10;

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(3);
            canvas.drawRect(Xwidth+10, Yheight, Xwidth+373, 153, paint);
            paint.setStrokeWidth(0);
            paint.setColor(Color.WHITE);
            canvas.drawRect(Xwidth+13, Yheight+3, Xwidth+370, 150, paint );

            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            canvas.drawText("บริการจัดส่งสินค้า (e-Commerce)", Xwidth+45, Yheight+30, paint);
            canvas.drawText("ใบอนุญาตสำหรับลูกค้าธุรกิจ", Xwidth+65, Yheight+60, paint);
            canvas.drawText("เลขที่ บธ.2 / 2559", Xwidth+105, Yheight+90, paint);
            canvas.drawText("ชำระค่าฝากส่งตามที่ ปณท กำหนด", Xwidth+50, Yheight+120, paint);

            Bitmap bitmapEms = BitmapFactory.decodeResource(getResources(), R.drawable.emslogo);
            paint.setColor(Color.WHITE);
            canvas.drawBitmap(bitmapEms, null, new RectF(Xwidth+10, 110, Xwidth+370, 270), paint);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.thailandpostlogo);
            paint.setColor(Color.WHITE);
            canvas.drawBitmap(bitmap, null, new RectF(Xwidth+10, 215, Xwidth+370, 370), paint);

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(3);
            canvas.drawRect(Xwidth+10, 365, Xwidth+373, 443, paint);
            paint.setStrokeWidth(0);
            paint.setColor(Color.WHITE);
            canvas.drawRect(Xwidth+13, 368, Xwidth+370, 440, paint );

            paint.setColor(Color.BLACK);
            paint.setTextSize(30);
            canvas.drawText("ไม่เก็บเงินค่าสินค้า", Xwidth+70, 415, paint);

            int Vheight = 30;
            int VWidth = 370;

            try {
                Code128 barcode = new Code128();
                barcode = CreatrBarcode128(this.Courier_tracking_code);
                RectF bounds = new RectF(VWidth+20, Vheight, 0, 0);
                barcode.drawBarcode(canvas, bounds);
            } catch (Exception ex) {
                Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG);
            }

            paint.setTextSize(10);
            canvas.drawText("SHIPPOP", VWidth+280, Vheight+110, paint);

            paint.setTextSize(25);
            for (int i = 0; i < text.size(); i++) {
                canvas.drawText(text.get(i), VWidth+40, (40 * (1 + i)) + Vheight + 100, paint);
            }

        }
    }

    private File saveBitMap(Context context, Bitmap bitmap) {
        String filename = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "There was an issue saving the image.", Toast.LENGTH_LONG);
        }
        scanGallery(context, pictureFile.getAbsolutePath());
        return pictureFile;
    }

    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }

    private Code128 CreatrBarcode128(String Barcode) {
        Code128 barcode = new Code128();

        /*
           Code 128 Valid data char set:
                all 128 ASCII characters (Char from 0 to 127)
        */
        barcode.setData(Barcode);

        //  Set the processTilde property to true, if you want use the tilde character "~"
        //  to specify special characters in the input data. Default is false.
        //  1) All 128 ISO/IEC 646 characters, i.e. characters 0 to 127 inclusive, in accordance with ISO/IEC 646.
        //       NOTE This version consists of the G0 set of ISO/IEC 646 and the C0 set of ISO/IEC 6429 with values 28 - 31
        //       modified to FS, GS, RS and US respectively.
        //  2) Characters with byte values 128 to 255 may also be encoded.
        //  3) 4 non-data function characters.
        //  4) 4 code set selection characters.
        //  5) 3 Start characters.
        //  6) 1 Stop character.
        barcode.setProcessTilde(false);

        // Unit of Measure, pixel, cm, or inch
        barcode.setUom(IBarcode.UOM_PIXEL);
        // barcode bar module width (X) in pixel
        barcode.setX(2f);
        // barcode bar module height (Y) in pixel
        barcode.setY(100f);

        // barcode image margins
        barcode.setLeftMargin(0f);
        barcode.setRightMargin(0f);
        barcode.setTopMargin(0f);
        barcode.setBottomMargin(0f);

        // barcode image resolution in dpi
        barcode.setResolution(72);

        // disply barcode encoding data below the barcode
        barcode.setShowText(true);
        // barcode encoding data font style
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 20));
        // space between barcode and barcode encoding data
        barcode.setTextMargin(6);
        barcode.setTextColor(AndroidColor.black);

        // barcode bar color and background color in Android device
        barcode.setForeColor(AndroidColor.black);
        barcode.setBackColor(AndroidColor.white);

	    /*
	    specify your barcode drawing area
	    */
        //RectF bounds = new RectF(30, 30, 0, 0);
        //barcode.drawBarcode(canvas, bounds);
        return barcode;
    }

}



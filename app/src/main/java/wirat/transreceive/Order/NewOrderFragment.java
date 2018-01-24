package wirat.transreceive.Order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import wirat.transreceive.AlertDialogManager;
import wirat.transreceive.CallService.AsyncTaskCompleteListener;
import wirat.transreceive.CallService.asyCallServiceAPIRestFulProcess;
import wirat.transreceive.DataBaseHelper.DBClass;
import wirat.transreceive.DataClass.ADDRESS;
import wirat.transreceive.DataClass.PARCEL;
import wirat.transreceive.R;
import wirat.transreceive.SettingActivity;

public class NewOrderFragment extends Fragment implements AsyncTaskCompleteListener<JSONObject> {

    private OnFragmentInteractionListener mListener;

    EditText parcel_weight, parcel_width, parcel_length, parcel_height;
    AutoCompleteTextView
            parcel_name, from_name, from_address, from_district, from_state, from_province, from_postcode, from_tel, to_name, to_address, to_district, to_state, to_province, to_postcode, to_tel;

    TextView courier_code, courier_code_Name, tracking_code, courier_tracking_code, price;
    Button Btnpricelist, BtnClarePurchase, BtnClareFrom, BtnClareTo;
    ImageView BtnFromSearchName, BtnToSearchName, BtnFromSearchPos, BtnToSearchPos, BtnToSearchParcel, BtnCheckprice;

    public NewOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_new_order, container, false);
        parcel_weight = (EditText) V.findViewById(R.id.parcel_weight);
        parcel_width = (EditText) V.findViewById(R.id.parcel_width);
        parcel_length = (EditText) V.findViewById(R.id.parcel_length);
        parcel_height = (EditText) V.findViewById(R.id.parcel_height);

        parcel_name = (AutoCompleteTextView) V.findViewById(R.id.parcel_name);
        from_name = (AutoCompleteTextView) V.findViewById(R.id.from_name);
        from_address = (AutoCompleteTextView) V.findViewById(R.id.from_address);
        from_district = (AutoCompleteTextView) V.findViewById(R.id.from_district);
        from_state = (AutoCompleteTextView) V.findViewById(R.id.from_state);
        from_province = (AutoCompleteTextView) V.findViewById(R.id.from_province);
        from_postcode = (AutoCompleteTextView) V.findViewById(R.id.from_postcode);
        from_tel = (AutoCompleteTextView) V.findViewById(R.id.from_tel);

        to_name = (AutoCompleteTextView) V.findViewById(R.id.to_name);
        to_address = (AutoCompleteTextView) V.findViewById(R.id.to_address);
        to_district = (AutoCompleteTextView) V.findViewById(R.id.to_district);
        to_state = (AutoCompleteTextView) V.findViewById(R.id.to_state);
        to_province = (AutoCompleteTextView) V.findViewById(R.id.to_province);
        to_postcode = (AutoCompleteTextView) V.findViewById(R.id.to_postcode);
        to_tel = (AutoCompleteTextView) V.findViewById(R.id.to_tel);

        courier_code = (TextView) V.findViewById(R.id.courier_code);
        courier_code_Name = (TextView) V.findViewById(R.id.courier_code_Name);
        tracking_code = (TextView) V.findViewById(R.id.tracking_code);
        courier_tracking_code = (TextView) V.findViewById(R.id.courier_tracking_code);
        price = (TextView) V.findViewById(R.id.price);

        Btnpricelist = (Button) V.findViewById(R.id.Btnpricelist);
        BtnClarePurchase = (Button) V.findViewById(R.id.BtnClarePurchase);
        BtnClareFrom = (Button) V.findViewById(R.id.BtnClareFrom);
        BtnClareTo = (Button) V.findViewById(R.id.BtnClareTo);

        BtnCheckprice = (ImageView) V.findViewById(R.id.BtnCheckPrice);
        BtnToSearchParcel = (ImageView) V.findViewById(R.id.BtnToSearchParcel);
        BtnFromSearchName = (ImageView) V.findViewById(R.id.BtnFromSearchName);
        BtnToSearchName = (ImageView) V.findViewById(R.id.BtnToSearchName);
        BtnFromSearchPos = (ImageView) V.findViewById(R.id.BtnFromSearchPos);
        BtnToSearchPos = (ImageView) V.findViewById(R.id.BtnToSearchPos);

        final DBClass query = new DBClass(getActivity());
        RefreshEditTextComplsase(query);
        /*
        * Get All district
        * */
        List<String> arraydistrict = new ArrayList<String>();
        arraydistrict = query.getAlldistrict(query.getWritableDatabase());
        ArrayAdapter<String> adapterdistrict = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arraydistrict);
        from_district.setAdapter(adapterdistrict);
        to_district.setAdapter(adapterdistrict);
        /*
        * Get All state
        * */
        List<String> arraystate = new ArrayList<String>();
        arraystate = query.getAllamphur(query.getWritableDatabase());
        ArrayAdapter<String> adapterstate = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arraystate);
        from_state.setAdapter(adapterstate);
        to_state.setAdapter(adapterstate);
        /*
        * Get All province
        * */
        List<String> arrayprovince = new ArrayList<String>();
        arrayprovince = query.getAllprovince(query.getWritableDatabase());
        ArrayAdapter<String> adapterprovince = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arrayprovince);
        from_province.setAdapter(adapterprovince);
        to_province.setAdapter(adapterprovince);
        /*
        * Get All postcode
        * */
        List<String> arraypostcode = new ArrayList<String>();
        arraypostcode = query.getAllpostcode(query.getWritableDatabase());
        ArrayAdapter<String> adapterpostcode = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arraypostcode);
        from_postcode.setAdapter(adapterpostcode);
        to_postcode.setAdapter(adapterpostcode);
        /*
        * Click Image Sharch
        * */
        BtnToSearchParcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PARCEL ITEM = new PARCEL();
                    ITEM = query.getPARCELByNameSend(query.getWritableDatabase(), parcel_name.getText().toString().trim());
                    if (!ITEM.getName().equals("")) {
                        parcel_name.setText(ITEM.getName());
                        parcel_weight.setText(String.valueOf(ITEM.getWeight()));
                        parcel_width.setText(String.valueOf(ITEM.getWidth()));
                        parcel_length.setText(String.valueOf(ITEM.getLength()));
                        parcel_height.setText(String.valueOf(ITEM.getHeight()));

                    } else
                        Toast.makeText(getActivity(),
                                "ไม่พบชื่อที่ค้นหา",
                                Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getActivity(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        BtnCheckprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PriceListActivity.UseConfirmParamiterValuse = new HashMap<String, String>();
                    PriceListActivity.UseConfirmParamiterValuse.put("data[0][parcel][weight]", parcel_weight.getText().toString());
                    PriceListActivity.UseConfirmParamiterValuse.put("data[0][parcel][width]", parcel_width.getText().toString());
                    PriceListActivity.UseConfirmParamiterValuse.put("data[0][parcel][length]", parcel_length.getText().toString());
                    PriceListActivity.UseConfirmParamiterValuse.put("data[0][parcel][height]", parcel_height.getText().toString());

                    ArrayList<HashMap<String, String>> ItemArr = new ArrayList<HashMap<String, String>>();
                    if(PriceListActivity.UpDatePriceList("TP2") != 0) {
                        HashMap<String, String> item1 = new HashMap<String, String>();
                        item1.put("courier_code", "TP2");
                        ItemArr.add(item1);
                    }
                    if(PriceListActivity.UpDatePriceList("THP") != 0) {
                        HashMap<String, String> item2 = new HashMap<String, String>();
                        item2.put("courier_code", "THP");
                        ItemArr.add(item2);
                    }
                    if(PriceListActivity.UpDatePriceList("DHL") != 0) {
                        HashMap<String, String> item3 = new HashMap<String, String>();
                        item3.put("courier_code", "DHL");
                        ItemArr.add(item3);
                    }

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    dialogBuilder.setTitle("เช็คราคา");
                    dialogBuilder.setIcon(R.drawable.applyicon);
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_price_check, null);
                    dialogBuilder.setView(dialogView);

                    ListView listprice = (ListView) dialogView.findViewById(R.id.listprice);
                    CustomAdapterCheckPrice itemsAdapter = new CustomAdapterCheckPrice(getActivity(), ItemArr);
                    listprice.setAdapter(itemsAdapter);

                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                } catch (Exception ex) {
                    Toast.makeText(getActivity(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        BtnFromSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ADDRESS ITEM = new ADDRESS();
                    ITEM = query.getADDRESSByNameSend(query.getWritableDatabase(), from_name.getText().toString().trim());
                    if (!ITEM.getName().equals("")) {
                        from_name.setText(ITEM.getName());
                        from_district.setText(ITEM.getDistrict());
                        from_address.setText(ITEM.getAddress());
                        from_postcode.setText(ITEM.getPostcode());
                        from_state.setText(ITEM.getState());
                        from_province.setText(ITEM.getProvince());
                        from_tel.setText(ITEM.getTel());
                    } else
                        Toast.makeText(getActivity(),
                                "ไม่พบชื่อที่ค้นหา",
                                Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getActivity(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        BtnToSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ADDRESS ITEM = new ADDRESS();
                    ITEM = query.getADDRESSByNameReceive(query.getWritableDatabase(), to_name.getText().toString().trim());
                    if (!ITEM.getName().equals("")) {
                        to_name.setText(ITEM.getName());
                        to_district.setText(ITEM.getDistrict());
                        to_address.setText(ITEM.getAddress());
                        to_postcode.setText(ITEM.getPostcode());
                        to_state.setText(ITEM.getState());
                        to_province.setText(ITEM.getProvince());
                        to_tel.setText(ITEM.getTel());
                    } else
                        Toast.makeText(getActivity(),
                                "ไม่พบชื่อที่ค้นหา",
                                Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getActivity(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        BtnFromSearchPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ADDRESS ITEM = new ADDRESS();
                    ITEM = query.getADDRESSByPostcodeSendOrReceive(query.getWritableDatabase(), from_postcode.getText().toString().trim());
                    if (!ITEM.getPostcode().equals("")) {
                        from_postcode.setText(ITEM.getPostcode());
                        from_state.setText(ITEM.getState());
                        from_province.setText(ITEM.getProvince());
                    } else
                        Toast.makeText(getActivity(),
                                "ไม่พบเลขที่ไปรษณี",
                                Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getActivity(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        BtnToSearchPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ADDRESS ITEM = new ADDRESS();
                    ITEM = query.getADDRESSByPostcodeSendOrReceive(query.getWritableDatabase(), to_postcode.getText().toString().trim());
                    if (!ITEM.getPostcode().equals("")) {
                        to_postcode.setText(ITEM.getPostcode());
                        to_state.setText(ITEM.getState());
                        to_province.setText(ITEM.getProvince());
                    } else
                        Toast.makeText(getActivity(),
                                "ไม่พบเลขที่ไปรษณี",
                                Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getActivity(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        /*
        * Button
        * */
        Btnpricelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!courier_code.getText().toString().equals("")) {
                        new AlertDialogManager().showAlertDialog(getActivity(), "ข้อมูลสั่งซื้อค้างอยู่", "ลบข้อมูลการสั่งซื่อ", true);
                        return;
                    }
                    if (parcel_name.getText().toString().equals("")
                            || parcel_weight.getText().toString().equals("")
                            || parcel_width.getText().toString().equals("")
                            || parcel_length.getText().toString().equals("")
                            || parcel_height.getText().toString().equals("")
                            || parcel_name.getText().toString().equals("")

                            || from_name.getText().toString().equals("")
                            || from_address.getText().toString().equals("")
                            || from_district.getText().toString().equals("")
                            || from_state.getText().toString().equals("")
                            || from_province.getText().toString().equals("")
                            || from_postcode.getText().toString().equals("")
                            || from_tel.getText().toString().equals("")

                            || to_name.getText().toString().equals("")
                            || to_address.getText().toString().equals("")
                            || to_district.getText().toString().equals("")
                            || to_state.getText().toString().equals("")
                            || to_province.getText().toString().equals("")
                            || to_postcode.getText().toString().equals("")
                            || to_tel.getText().toString().equals("")
                            ) {
                        new AlertDialogManager().showAlertDialog(getActivity(), "ตรวจสอบ", "คุณยังคีย์ข้อมูลไม่ครบถ่วน", true);
                    } else {
                        CkeckPrice();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getActivity(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        BtnClarePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    courier_code.setText("");
                    courier_code_Name.setText("");
                    courier_tracking_code.setText("");
                    tracking_code.setText("");
                    price.setText("0.0");
                } catch (Exception ex) {
                    Toast.makeText(getActivity(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        BtnClareFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    from_name.setText("");
                    from_address.setText("");
                    from_district.setText("");
                    from_state.setText("");
                    from_province.setText("");
                    from_postcode.setText("");
                    from_tel.setText("");
                } catch (Exception ex) {
                    Toast.makeText(getActivity(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        BtnClareTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    to_name.setText("");
                    to_address.setText("");
                    to_district.setText("");
                    to_state.setText("");
                    to_province.setText("");
                    to_postcode.setText("");
                    to_tel.setText("");
                } catch (Exception ex) {
                    Toast.makeText(getActivity(),
                            ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return V;
    }

    private void RefreshEditTextComplsase(DBClass query) {
        /*
        * GetAll Name
        * */
        List<String> arrayNameS = new ArrayList<String>();
        arrayNameS = query.getAllNameSend(query.getWritableDatabase());
        ArrayAdapter<String> adapterNameS = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arrayNameS);
        from_name.setAdapter(adapterNameS);

        List<String> arrayNameR = new ArrayList<String>();
        arrayNameR = query.getAllNameReceive(query.getWritableDatabase());
        ArrayAdapter<String> adapterNameR = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arrayNameR);
        to_name.setAdapter(adapterNameR);
        /*
        * Get All Address
        * */
        List<String> arrayAddressS = new ArrayList<String>();
        arrayAddressS = query.getAllAddressSend(query.getWritableDatabase());
        ArrayAdapter<String> adapterAddressS = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arrayAddressS);
        from_address.setAdapter(adapterAddressS);

        List<String> arrayAddressR = new ArrayList<String>();
        arrayAddressR = query.getAllAddressReceive(query.getWritableDatabase());
        ArrayAdapter<String> adapterAddressR = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arrayAddressR);
        to_address.setAdapter(adapterAddressR);

        /*
        * Get Top 100 Parcel
        * */
        List<String> arrayParcel = new ArrayList<String>();
        arrayParcel = query.getAllarrayParcelSend(query.getWritableDatabase());
        ArrayAdapter<String> adapterParcel = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arrayParcel);
        parcel_name.setAdapter(adapterParcel);
        /*
        * Get All tel
        * */
        List<String> arraytelS = new ArrayList<String>();
        arraytelS = query.getAlltelSend(query.getWritableDatabase());
        ArrayAdapter<String> adaptertelS = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arraytelS);
        from_tel.setAdapter(adaptertelS);

        List<String> arraytelR = new ArrayList<String>();
        arraytelR = query.getAlltelReceive(query.getWritableDatabase());
        ArrayAdapter<String> adaptertelR = new ArrayAdapter<String>(getActivity(), R.layout.list_item, arraytelR);
        from_tel.setAdapter(adaptertelR);
        to_tel.setAdapter(adaptertelR);
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

    @Override
    public void onTaskComplete(JSONObject result) {
        if (result == null) {
            new AlertDialogManager().showAlertDialog(getActivity(), "ผิดพลาด ", "ไม่พบการส่งคืนค่ากลับ", true);
            return;
        }
        try {
            String Status = result.getString("status");
            if (Boolean.parseBoolean(Status)) {
                JSONArray json_array_Service = new JSONArray();
                try {
                    JSONObject state = result.getJSONObject("data");
                    Iterator x = state.keys();
                    JSONArray the_json_array = new JSONArray();
                    while (x.hasNext()) {
                        String key = (String) x.next();
                        the_json_array.put(state.get(key));
                    }

                    JSONObject OneArray = the_json_array.optJSONObject(0);
                    Iterator y = OneArray.keys();

                    while (y.hasNext()) {
                        String key = (String) y.next();
                        json_array_Service.put(OneArray.get(key));
                    }
                }catch (Exception ex)
                {
                    new AlertDialogManager().showAlertDialog(getActivity(),"ตรวจสอบ","ระบุน้ำหนักไม่ถูกต้อง",true);
                    Log.e("Error",ex.getMessage());
                    return;
                }

                if (json_array_Service.length() > 0) {
                    PriceListActivity.ArryPrice = json_array_Service;
                    PriceListActivity.UseConfirmParamiterValuse = new HashMap<String, String>();
                    PriceListActivity.UseConfirmParamiterValuse = UseConfirmParamiterValuse;
                    Intent PriceList = new Intent(getActivity(), PriceListActivity.class);
                    startActivityForResult(PriceList, 100);
                }

            } else {
                new AlertDialogManager().showAlertDialog(getActivity(), result.getString("code"), result.getString("message"), true);
            }
        } catch (JSONException e) {
            new AlertDialogManager().showAlertDialog(getActivity(), "ผิดพลาด ", e.getMessage(), true);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onNavFragmentInteraction(String string);
    }

    HashMap<String, String> UseConfirmParamiterValuse = new HashMap<String, String>();

    public void CkeckPrice() {
        try {
            HashMap<String, String> ParamiterValuse = new HashMap<String, String>();
            ParamiterValuse.put("api_key", SettingActivity.APIKEY);
            ParamiterValuse.put("data[0][from][name]", from_name.getText().toString().trim());
            ParamiterValuse.put("data[0][from][address]", from_address.getText().toString().trim());
            ParamiterValuse.put("data[0][from][district]", from_district.getText().toString().trim());
            ParamiterValuse.put("data[0][from][state]", from_state.getText().toString().trim());
            ParamiterValuse.put("data[0][from][province]", from_province.getText().toString().trim());
            ParamiterValuse.put("data[0][from][postcode]", from_postcode.getText().toString().trim());
            ParamiterValuse.put("data[0][from][tel]", from_tel.getText().toString().trim());
            ParamiterValuse.put("data[0][to][name]", to_name.getText().toString().trim());
            ParamiterValuse.put("data[0][to][address]", to_address.getText().toString().trim());
            ParamiterValuse.put("data[0][to][district]", to_district.getText().toString().trim());
            ParamiterValuse.put("data[0][to][state]", to_state.getText().toString().trim());
            ParamiterValuse.put("data[0][to][province]", to_province.getText().toString().trim());
            ParamiterValuse.put("data[0][to][postcode]", to_postcode.getText().toString().trim());
            ParamiterValuse.put("data[0][to][tel]", to_tel.getText().toString().trim());
            ParamiterValuse.put("data[0][parcel][name]", parcel_name.getText().toString().trim());
            ParamiterValuse.put("data[0][parcel][weight]", parcel_weight.getText().toString().trim());
            ParamiterValuse.put("data[0][parcel][width]", parcel_width.getText().toString().trim());
            ParamiterValuse.put("data[0][parcel][length]", parcel_length.getText().toString().trim());
            ParamiterValuse.put("data[0][parcel][height]", parcel_height.getText().toString().trim());

            asyCallServiceAPIRestFulProcess UpVisit = new asyCallServiceAPIRestFulProcess(SettingActivity.SERVER, getActivity(), NewOrderFragment.this, "pricelist", ParamiterValuse);
            UpVisit.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            UseConfirmParamiterValuse = new HashMap<String, String>();
            UseConfirmParamiterValuse = ParamiterValuse;

        } catch (Exception ex) {
            new AlertDialogManager().showAlertDialog(getActivity(), "Error", ex.getMessage(), true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED)
            if (requestCode == 100) {
                courier_code.setText(data.getStringExtra("courier_code"));
                courier_code_Name.setText(data.getStringExtra("courier_code_name"));
                price.setText(data.getStringExtra("price"));
                tracking_code.setText(data.getStringExtra("tracking_code"));
                courier_tracking_code.setText(data.getStringExtra("courier_tracking_code"));

                new AlertDialogManager().showAlertDialog(getActivity(), "สำเร็จ", "บันทึกเรียบร้อย", true);

                DBClass query = new DBClass(getActivity());
                RefreshEditTextComplsase(query);
            }
    }
}

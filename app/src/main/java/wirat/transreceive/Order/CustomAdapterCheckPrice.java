package wirat.transreceive.Order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import wirat.transreceive.DataClass.COURIER;
import wirat.transreceive.DataClass.COURIERALL;
import wirat.transreceive.R;

public class CustomAdapterCheckPrice extends BaseAdapter {

    private Context mContext;
    private ArrayList<HashMap<String, String>> jsonArray;
    private LayoutInflater mInflater;

    public CustomAdapterCheckPrice(Context context, ArrayList<HashMap<String, String>> data) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        jsonArray = data;
    }

    @Override
    public int getCount() {
        if (null == jsonArray)
            return 0;
        else
            return jsonArray.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        if (null == jsonArray) return null;
        else
            return jsonArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        try {

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.checkprice_item, parent, false);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                holder.courier_code_name = (TextView) convertView.findViewById(R.id.courier_code_name);
                holder.Img = (ImageView) convertView.findViewById(R.id.Img);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, String> json_data = getItem(position);
            COURIERALL Cour = new COURIERALL();
            if(json_data.get("courier_code").equals("SCGEX")) {
                COURIER Use = Cour.Courier_Retrun(json_data.get("courier_code"));
                holder.price.setText(String.valueOf(PriceListActivity.UpDatePriceList(json_data.get("courier_code"))));
                holder.courier_code_name.setText(PriceListActivity.scgDetail+Use.courier_Name);
            }else
            {
                COURIER Use = Cour.Courier_Retrun(json_data.get("courier_code"));
                holder.price.setText(String.valueOf(PriceListActivity.UpDatePriceList(json_data.get("courier_code"))));
                holder.courier_code_name.setText(Use.courier_Name);
            }

            if (json_data.get("courier_code").equals("THP"))
                holder.Img.setImageResource(R.drawable.emslogo);
            else if (json_data.get("courier_code").equals("TP2"))
                holder.Img.setImageResource(R.drawable.thailandpostlogo);
            else if (json_data.get("courier_code").equals("DHL"))
                holder.Img.setImageResource(R.drawable.dhl);
            else if (json_data.get("courier_code").equals("SCGEX"))
                holder.Img.setImageResource(R.drawable.scgex);
            else
                holder.Img.setImageResource(R.drawable.producticon);

            convertView.setTag(holder);

        } catch (Exception ex) {
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return convertView;
    }

    public class ViewHolder {
        TextView courier_code_name, price;
        ImageView Img;
    }
}
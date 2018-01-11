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

import wirat.transreceive.DataClass.COURIER;
import wirat.transreceive.DataClass.COURIERALL;
import wirat.transreceive.R;

public class CustomAdapterListPrice extends BaseAdapter {

    private Context mContext;
    private JSONArray jsonArray;
    private LayoutInflater mInflater;

    public CustomAdapterListPrice(Context context, JSONArray data) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        jsonArray = data;
    }

    @Override
    public int getCount() {
        if (null == jsonArray)
            return 0;
        else
            return jsonArray.length();
    }

    @Override
    public JSONObject getItem(int position) {
        if (null == jsonArray) return null;
        else
            return jsonArray.optJSONObject(position);
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
                convertView = mInflater.inflate(R.layout.lineprice_item, parent, false);
                holder.courier_code = (TextView) convertView.findViewById(R.id.courier_code);
                holder.courier_code_name = (TextView) convertView.findViewById(R.id.courier_code_name);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                holder.estimate_time = (TextView) convertView.findViewById(R.id.estimate_time);
                holder.Img = (ImageView) convertView.findViewById(R.id.Img);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            JSONObject json_data = getItem(position);
            if ((json_data != null) && Boolean.parseBoolean(json_data.getString("available")))
            {
                holder.courier_code.setText(json_data.getString("courier_code"));

                COURIERALL Cour = new COURIERALL();
                COURIER Use = Cour.Courier_Retrun(json_data.getString("courier_code"));

                holder.courier_code_name.setText(Use.courier_Name);
                //holder.price.setText(json_data.getString("price"));
                holder.price.setText(String.valueOf(PriceListActivity.UpDatePriceList(Float.valueOf(PriceListActivity.UseConfirmParamiterValuse.get("data[0][parcel][weight]")),holder.courier_code.getText().toString())));
                holder.estimate_time.setText(json_data.getString("estimate_time"));

                if(json_data.getString("courier_code").equals("THP"))
                    holder.Img.setImageResource(R.drawable.emslogo);
                else if(json_data.getString("courier_code").equals("TP2"))
                    holder.Img.setImageResource(R.drawable.thailandpostlogo);
                else
                    holder.Img.setImageResource(R.drawable.producticon);
            }

            convertView.setTag(holder);

        } catch (Exception ex) {
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return convertView;
    }

    public class ViewHolder {
        TextView courier_code, courier_code_name, price, estimate_time;
        ImageView Img;
    }
}
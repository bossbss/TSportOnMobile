package wirat.transreceive.TrackingFail;

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

public class CustomAdapterListTrackingFail extends BaseAdapter {

    private Context mContext;
    private ArrayList<HashMap<String, String>> ListValuse;
    private LayoutInflater mInflater;

    public CustomAdapterListTrackingFail(Context context, ArrayList<HashMap<String, String>> ListVal) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        ListValuse = ListVal;
    }

    @Override
    public int getCount() {
        if (null == ListValuse)
            return 0;
        else
            return ListValuse.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        if (null == ListValuse) return null;
        else
            return ListValuse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            ViewHolder holder;
            HashMap<String, String> json_data;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.linelistviewtrackingfail, parent, false);

                holder = new ViewHolder();
                holder.courier_code_Name = (TextView) convertView.findViewById(R.id.courier_code_Name);
                holder.parcel_weight = (TextView) convertView.findViewById(R.id.parcel_weight);
                holder.courier_tracking_code = (TextView) convertView.findViewById(R.id.courier_tracking_code);
                holder.total_price = (TextView) convertView.findViewById(R.id.total_price);
                holder.from = (TextView) convertView.findViewById(R.id.from);
                holder.to = (TextView) convertView.findViewById(R.id.to);
                holder.Img = (ImageView) convertView.findViewById(R.id.Img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            json_data = getItem(position);

            COURIERALL Cour = new COURIERALL();
            COURIER Use = Cour.Courier_Retrun(json_data.get("courier_code"));

            holder.courier_code_Name.setText(Use.courier_Name);
            holder.parcel_weight.setText(json_data.get("parcel_weight"));
            holder.courier_tracking_code.setText(json_data.get("courier_tracking_code"));
            holder.total_price.setText(json_data.get("total_price"));
            holder.from.setText(json_data.get("from"));
            holder.to.setText(json_data.get("to"));

            if(Use.courier_code.equals("THP"))
                holder.Img.setImageResource(R.drawable.emslogo);
            else if(Use.courier_code.equals("TP2"))
                holder.Img.setImageResource(R.drawable.thailandpostlogo);
            else if(Use.courier_code.equals("DHL"))
                holder.Img.setImageResource(R.drawable.dhl);
            else
                holder.Img.setImageResource(R.drawable.producticon);

        } catch (Exception ex) {
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return convertView;
    }

    public class ViewHolder {
        TextView courier_code_Name, total_price, courier_tracking_code, parcel_weight, from, to;
        ImageView Img;
    }
}
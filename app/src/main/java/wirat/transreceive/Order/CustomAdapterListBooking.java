package wirat.transreceive.Order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wirat.transreceive.DataClass.bookingResponseObject;
import wirat.transreceive.R;

public class CustomAdapterListBooking extends BaseAdapter {

    private Context mContext;
    private List<bookingResponseObject> jsonArray;

    public CustomAdapterListBooking(Context context, ArrayList<bookingResponseObject> data) {
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
    public bookingResponseObject getItem(int position) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            ViewHolder holder;
            bookingResponseObject json_data;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.linelistviewbooking_item, parent, false);

                holder = new ViewHolder();
                holder.tracking_code = (TextView) convertView.findViewById(R.id.tracking_code);
                holder.courier_tracking_code = (TextView) convertView.findViewById(R.id.courier_tracking_code);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                holder.from_name = (TextView) convertView.findViewById(R.id.from_name);
                holder.to_name = (TextView) convertView.findViewById(R.id.to_name);
                holder.Img = (ImageView) convertView.findViewById(R.id.Img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            json_data = getItem(position);

            holder.tracking_code.setText(json_data.getTracking_code());
            holder.courier_tracking_code.setText(json_data.getCourier_tracking_code());
            //holder.price.setText(String.valueOf(json_data.getPrice()));
            holder.price.setText(json_data.getTotal_price());
            holder.from_name.setText(json_data.getFrom().getName());
            holder.to_name.setText(json_data.getTo().getName());

            if(json_data.getMarkSelect())
                holder.Img.setImageResource(R.drawable.selecticon);
            else {
                if(json_data.getCourier_code().equals("THP"))
                    holder.Img.setImageResource(R.drawable.emslogo);
                else if(json_data.getCourier_code().equals("TP2"))
                    holder.Img.setImageResource(R.drawable.thailandpostlogo);
                else
                    holder.Img.setImageResource(R.drawable.producticon);
            }
        } catch (Exception ex) {
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return convertView;
    }

    public class ViewHolder {
        TextView tracking_code, courier_tracking_code, price, from_name, to_name;
        ImageView Img;
    }
}
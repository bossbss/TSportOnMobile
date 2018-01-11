package wirat.transreceive.Tracking;

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

import wirat.transreceive.R;

public class CustomAdapterListTracking extends BaseAdapter {

    private Context mContext;
    private JSONArray jsonArray;
    private LayoutInflater mInflater;

    public CustomAdapterListTracking(Context context, JSONArray data) {
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
        JSONObject json_data;
        try {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.linelistviewtracking_item, parent, false);
                holder.datetime = (TextView) convertView.findViewById(R.id.datetime);
                holder.location = (TextView) convertView.findViewById(R.id.location);
                holder.description = (TextView) convertView.findViewById(R.id.description);
                holder.Img = (ImageView) convertView.findViewById(R.id.Img);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            json_data = getItem(position);
            if (null != json_data) {
                holder.datetime.setText(json_data.getString("datetime"));
                holder.location.setText(json_data.getString("location"));
                holder.description.setText(json_data.getString("description"));

                if(TrakingActivity.Tempcourier_code.equals("THP"))
                    holder.Img.setImageResource(R.drawable.emslogo);
                else if(TrakingActivity.Tempcourier_code.equals("TP2"))
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
        TextView datetime, location, description;
        ImageView Img;
    }
}
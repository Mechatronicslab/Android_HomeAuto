package Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.hans.agrigo.MenuTesting.Support.Model;
import com.example.hans.agrigo.R;

import java.util.List;

public class AdapterData extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Model> item;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    public AdapterData(Activity activity, List<Model> item, RequestQueue mRequestQueue, ProgressDialog pDialog) {
        this.activity = activity;
        this.item = item;
        this.mRequestQueue = mRequestQueue;
        this.pDialog = pDialog;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService( Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_data, null);


        TextView longitude = (TextView) convertView.findViewById(R.id.txtLongitude);
        TextView latitude = (TextView) convertView.findViewById(R.id.txtLatitude);

        longitude.setText(item.get(position).getLongitude());
        latitude.setText(item.get(position).getLatitude());

        return convertView;
}
}

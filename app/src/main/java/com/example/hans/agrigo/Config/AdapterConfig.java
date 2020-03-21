package com.example.hans.agrigo.Config;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hans.agrigo.Fragment.SearchFragment;
import com.example.hans.agrigo.LihatZona.LihatZona;
import com.example.hans.agrigo.Menu.Tampil_history;
import com.example.hans.agrigo.R;
import com.example.hans.agrigo.SetZona.SetZona;

import java.util.List;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class AdapterConfig extends RecyclerView.Adapter<AdapterConfig.MyViewHolder> {
    Context context;
    List<Item_Config> menu;
    //    String watering,Watering;
    public AdapterConfig(Context context, List<Item_Config > data_menu) {
        this.context = context;
        this.menu= data_menu;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_device, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // Set widget
        holder.nama.setText(menu.get(position).getNama());
        String a = holder.nama.getText().toString();
////        holder.nama.getText().toString();
//        if (a.equals("watering") || a.equals("Watering")) {
//            holder.history.setVisibility(View.GONE);
//        } else if (a.equals("sensor") || a.equals("Sensor")) {
//            holder.add.setVisibility(View.GONE);
//            holder.zona.setVisibility(View.GONE);
//        }

//        holder.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent varIntent = new Intent(context, SetZona.class);
//                varIntent.putExtra("mac", menu.get(position).getMac());
//                context.startActivity(varIntent);
//            }
//        });
////        holder.history.setVisibility(View.VISIBLE);
//        holder.history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////
//                Intent varIntent = new Intent(context, Tampil_history.class);
//                varIntent.putExtra("mac", menu.get(position).getMac());
//                context.startActivity(varIntent);
//            }
//        });
//        holder.zona.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////
//                Intent varIntent = new Intent(context, LihatZona.class);
//                varIntent.putExtra("mac", menu.get(position).getMac());
//                context.startActivity(varIntent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return menu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, hem;
        Button add, history,zona;
        public MyViewHolder(View itemView) {
            super(itemView);
//            nama = (TextView) itemView.findViewById(R.id.Nama);
//            add = (Button) itemView.findViewById( R.id.AddZona );
//            zona=(Button)itemView.findViewById(R.id.lihatZona);
//            history = (Button) itemView.findViewById( R.id.history );
//            history.setVisibility(View.VISIBLE);

        }
    }
}
package com.example.hans.agrigo.LihatZona.Support;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hans.agrigo.SiramZona.SiramZona;
import com.example.hans.agrigo.MenuJadwal.AturJadwalSiram;
import com.example.hans.agrigo.R;

import java.util.List;

public class AdapterZona extends RecyclerView.Adapter<AdapterZona.MyViewHolder> {
    Context context;
    List<Item_Zona> menu;
//    String Mac;

    public AdapterZona(Context context, List<Item_Zona> data_menu) {
        this.context = context;
        this.menu= data_menu;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_zona, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        // Set widget
//        Mac = menu.get(position).getZona_name();
//        holder.txtLihatZona.setText(menu.get(position).getZona_name());
//        holder.txtNomor.setText(menu.get(position).getZona_number());
//        final String urlGambar = InitRetrofit.BASE_URL+"../Images/" + menu.get(position).getFoto();
//        Picasso.with(context).load(urlGambar).into(holder.gambarmenu);
        holder.btnjadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Intent varIntent = new Intent(context, AturJadwalSiram.class);
//                varIntent.putExtra("mac", menu.get(position).getMac());
//                varIntent.putExtra("NAMA", menu.get(position).getNama());
//                varIntent.putExtra("HARGA", menu.get(position).getHarga());
//                varIntent.putExtra("DESKRIPSI", menu.get(position).getDeskripsi());
//                varIntent.putExtra("GAMBAR_MENU", urlGambar);
//                varIntent.putExtra("GAMBAR", menu.get(position).getFoto());
                context.startActivity(varIntent);
            }
        });

        holder.btnsiram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Intent varIntent = new Intent(context, SiramZona.class);
//                varIntent.putExtra("mac", menu.get(position).getMac());
//                varIntent.putExtra("NAMA", menu.get(position).getNama());
//                varIntent.putExtra("HARGA", menu.get(position).getHarga());
//                varIntent.putExtra("DESKRIPSI", menu.get(position).getDeskripsi());
//                varIntent.putExtra("GAMBAR_MENU", urlGambar);
//                varIntent.putExtra("GAMBAR", menu.get(position).getFoto());
                context.startActivity(varIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomor,txtLihatZona;
        Button btnjadwal, btnsiram;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtNomor = (TextView) itemView.findViewById(R.id.txtNomor);
            txtLihatZona = (TextView) itemView.findViewById(R.id.txtLihatZona);
            btnjadwal = (Button)itemView.findViewById(R.id.aturJadwal);
            btnsiram = (Button)itemView.findViewById(R.id.siramManual);
        }
    }
}
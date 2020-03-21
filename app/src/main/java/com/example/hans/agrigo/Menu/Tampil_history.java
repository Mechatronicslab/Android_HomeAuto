package com.example.hans.agrigo.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hans.agrigo.Network.InitRetrofit;
import com.example.hans.agrigo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tampil_history extends AppCompatActivity {
    TextView Levelair,soil,Flow;
    FrameLayout Satu,Dua,Tiga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_history);
        Levelair=(TextView)findViewById(R.id.level_air);
        Flow=(TextView)findViewById(R.id.flow);
        soil=(TextView)findViewById(R.id.soil);
        Satu=(FrameLayout) findViewById(R.id.satu);
        Dua=(FrameLayout) findViewById(R.id.dua);
        Tiga =(FrameLayout) findViewById(R.id.tiga);
        String mac = getIntent().getStringExtra( "mac" );
        histrory_level();
        history_flow();
        Satu.setVisibility(View.GONE);
        Dua.setVisibility(View.GONE);
        Tiga.setVisibility(View.GONE);
//        if (Levelair.equals("")) {
//            Satu.setVisibility(View.VISIBLE);
//        }else{
//            Satu.setVisibility(View.GONE);
//        }
    }
    private void histrory_level() {
        String mac = getIntent().getStringExtra( "mac" );
        Toast.makeText(this, ""+mac, Toast.LENGTH_SHORT).show();
        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().level(mac);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
//                    Satu.setVisibility(View.VISIBLE);
                    try {
                        JSONArray jsonarray = new JSONArray(response.body().string());
                        Log.i("pesan", jsonarray.toString());
                        Satu.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String  mac = jsonobject.getString("mac");
                            String Type = jsonobject.getString("level_air");
                            Levelair.setText(Type);
//                            if (Levelair.equals("")){
//                                Satu.setVisibility(View.VISIBLE);
//                            }
                            Log.d("isi:",mac+Type);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Tampil_history.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    Toast.makeText(Tampil_history.this, "Server Tidak Merespon/Periksa koneksi Internet anda", Toast.LENGTH_SHORT).show();
                    Log.v("debug", "onFailure: ERROR > " + t.toString());
//                        Toast.makeText(AddDevice.this, ""+t.toString(), Toast.LENGTH_SHORT).show();
//                        loading.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void history_soil() {
        String mac = getIntent().getStringExtra( "mac" );
        Toast.makeText(this, ""+mac, Toast.LENGTH_SHORT).show();
        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().soil(mac);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
//                    Satu.setVisibility(View.VISIBLE);
                    try {
                        JSONArray jsonarray = new JSONArray(response.body().string());
                        Log.i("pesan", jsonarray.toString());
                        Dua.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String  mac = jsonobject.getString("mac");
                            String kondisi_tanah = jsonobject.getString("kondisi_tanah");
                            String value_sensor = jsonobject.getString("tegangan_tanah");
                            soil.setText(kondisi_tanah);
//                            if (Levelair.equals("")){
//                                Satu.setVisibility(View.VISIBLE);
//                            }
                            Log.d("isi:",mac+kondisi_tanah);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Tampil_history.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    Toast.makeText(Tampil_history.this, "Server Tidak Merespon/Periksa koneksi Internet anda", Toast.LENGTH_SHORT).show();
                    Log.v("debug", "onFailure: ERROR > " + t.toString());
//                        Toast.makeText(AddDevice.this, ""+t.toString(), Toast.LENGTH_SHORT).show();
//                        loading.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void history_flow() {
        String mac ="A0:20:A6: 4:76:59";
//        getIntent().getStringExtra( "mac" );
        Toast.makeText(this, ""+mac, Toast.LENGTH_SHORT).show();
        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().flow(mac);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
//                    Satu.setVisibility(View.VISIBLE);
                    try {
                        JSONArray jsonarray = new JSONArray(response.body().string());
                        Log.i("pesan", jsonarray.toString());
                        Tiga.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String  mac = jsonobject.getString("mac");
                            String total = jsonobject.getString("total");
                            Flow.setText(total);
//                            if (Levelair.equals("")){
//                                Satu.setVisibility(View.VISIBLE);
//                            }
                            Log.d("isi:",mac+total);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Tampil_history.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    Toast.makeText(Tampil_history.this, "Server Tidak Merespon/Periksa koneksi Internet anda", Toast.LENGTH_SHORT).show();
                    Log.v("debug", "onFailure: ERROR > " + t.toString());
//                        Toast.makeText(AddDevice.this, ""+t.toString(), Toast.LENGTH_SHORT).show();
//                        loading.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}

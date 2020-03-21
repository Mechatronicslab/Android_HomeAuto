package com.example.hans.agrigo.LihatZona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hans.agrigo.Config.AdapterDevice;
import com.example.hans.agrigo.Config.Item_Device;
import com.example.hans.agrigo.Config.Response_Device;
import com.example.hans.agrigo.LihatZona.Support.AdapterZona;
import com.example.hans.agrigo.LihatZona.Support.Item_Zona;
import com.example.hans.agrigo.LihatZona.Support.ResponZona;
import com.example.hans.agrigo.Menu.MenuUtama;
import com.example.hans.agrigo.MenuJadwal.AturJadwalSiram;
import com.example.hans.agrigo.MenuLogin.Login;
import com.example.hans.agrigo.Network.InitRetrofit;
import com.example.hans.agrigo.Network.NetworkService;
import com.example.hans.agrigo.R;
import com.example.hans.agrigo.SetZona.SetZona;
import com.example.hans.agrigo.SiramZona.Helper.RabbitMq;
import com.example.hans.agrigo.SiramZona.SiramZona;
import com.example.hans.agrigo.Storage.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatZona extends AppCompatActivity {
    private ListView recyclerView;
    SharedPrefManager sharedPrefManager;
    TextView txtTampil;
    private ListView listview;
    ProgressDialog loading;
    String Status1,Status2,Status3,Status4,Status5,Status6,Status7,Status8;
    TextView Zonanumber,Zonanama,Zonanumber2,Zonanama2,Zonanumber3,Zonanama3,Zonanumber4,Zonanama4,ff,
            Zonanama5,Zonanama6,Zonanama7,Zonanama8,Zonanumber5,Zonanumber6,Zonanumber7,Zonanumber8;
    Button btnJadwal1, btnJadwal2, btnJadwal3, btnJadwal4, btnSiram1,
            btnSiram2, btnSiram3, btnSiram4,btnSiram5,btnSiram6,btnSiram7,btnSiram8,btnJadwal5,btnJadwal6,btnJadwal7,btnJadwal8;
    TextView status1,status2,status3,status4,status5,status6,status7,status8;

    LinearLayout satu, dua, tiga, empat, lima,enam,tujuh,delapan;
    String Mac_Address,a,Nama,Nomor;

    RabbitMq rmq = new RabbitMq();
    ConnectionFactory factory = new ConnectionFactory();

    String user = "homeauto";
    String pass = "homeauto12345!";
    String host = "167.205.7.226";
    String vhost = "/homeauto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lihat_zona );
        sharedPrefManager = new SharedPrefManager(LihatZona.this);
        satu = (LinearLayout) findViewById(R.id.satu);
        satu.setVisibility(View.GONE);
        dua = (LinearLayout) findViewById(R.id.dua);
        dua.setVisibility(View.GONE);
        tiga = (LinearLayout) findViewById(R.id.tiga);
        tiga.setVisibility(View.GONE);
        empat = (LinearLayout) findViewById(R.id.empat);
        empat.setVisibility(View.GONE);
        lima = (LinearLayout) findViewById(R.id.lima);
        lima.setVisibility(View.GONE);
        enam = (LinearLayout) findViewById(R.id.enam);
        enam.setVisibility(View.GONE);
        tujuh = (LinearLayout) findViewById(R.id.tujuh);
        tujuh.setVisibility(View.GONE);
        delapan = (LinearLayout) findViewById(R.id.delapan);
        delapan.setVisibility(View.GONE);
        final SwipeRefreshLayout dorefresh = (SwipeRefreshLayout)findViewById(R.id.Refres);
        dorefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        /*event ketika widget dijalankan*/
        dorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refreshItem();
            }

            void refreshItem() {
                tampil();
                tampilMac();
                tampil_status();
                onItemLoad();
            }

            void onItemLoad() {
                dorefresh.setRefreshing(true);
            }

        });


        Zonanumber  = (TextView)findViewById(R.id.zonanumber);
        Zonanama    = (TextView)findViewById(R.id.zonaname);
        status1    = (TextView)findViewById(R.id.txtkondisi1);
        Zonanumber.setVisibility(View.VISIBLE);
        Zonanama.setVisibility(View.VISIBLE);

        Zonanumber2 = (TextView)findViewById(R.id.zonanumber2);
        Zonanama2   = (TextView)findViewById(R.id.zonaname2);
        status2    = (TextView)findViewById(R.id.txtkondisi2);
        Zonanumber2.setVisibility(View.VISIBLE);
        Zonanama2.setVisibility(View.VISIBLE);

        Zonanumber3 = (TextView)findViewById(R.id.zonanumber3);
        Zonanama3   = (TextView)findViewById(R.id.zonaname3);
        status3    = (TextView)findViewById(R.id.txtkondisi3);
        Zonanumber3.setVisibility(View.VISIBLE);
        Zonanama3.setVisibility(View.VISIBLE);

        Zonanumber4 = (TextView)findViewById(R.id.zonanumber4);
        Zonanama4   = (TextView)findViewById(R.id.zonaname4);
        status4    = (TextView)findViewById(R.id.txtkondisi4);
        Zonanumber4.setVisibility(View.VISIBLE);
        Zonanama4.setVisibility(View.VISIBLE);



        Zonanumber5 = (TextView)findViewById(R.id.zonanumber5);
        Zonanama5   = (TextView)findViewById(R.id.zonaname5);
        status5    = (TextView)findViewById(R.id.txtkondisi5);
        Zonanumber5.setVisibility(View.VISIBLE);
        Zonanama5.setVisibility(View.VISIBLE);

        Zonanumber6 = (TextView)findViewById(R.id.zonanumber6);
        Zonanama6   = (TextView)findViewById(R.id.zonaname6);
        status6    = (TextView)findViewById(R.id.txtkondisi6);
        Zonanumber6.setVisibility(View.VISIBLE);
        Zonanama6.setVisibility(View.VISIBLE);

        Zonanumber7 = (TextView)findViewById(R.id.zonanumber7);
        Zonanama7   = (TextView)findViewById(R.id.zonaname7);
        status7    = (TextView)findViewById(R.id.txtkondisi7);
        Zonanumber7.setVisibility(View.VISIBLE);
        Zonanama7.setVisibility(View.VISIBLE);

        Zonanumber8 = (TextView)findViewById(R.id.zonanumber8);
        Zonanama8   = (TextView)findViewById(R.id.zonaname8);
        status8    = (TextView)findViewById(R.id.txtkondisi8);
        Zonanumber8.setVisibility(View.VISIBLE);
        Zonanama8.setVisibility(View.VISIBLE);

        txtTampil   = (TextView) findViewById( R.id.tampilan );
        btnJadwal1 = (Button) findViewById(R.id.jadwal1) ;
        btnJadwal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama.getText().toString();
                String nomor = Zonanumber.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
                varIntent.putExtra("MAC", a);
                varIntent.putExtra("namaZona", nama);
                varIntent.putExtra("nomorZona", nomor);
                varIntent.putExtra("Status1", Status1);
                varIntent.putExtra("Status2", Status2);
                varIntent.putExtra("Status3", Status3);
                varIntent.putExtra("Status4", Status4);
                varIntent.putExtra("Status5", Status5);
                varIntent.putExtra("Status6", Status6);
                varIntent.putExtra("Status7", Status7);
                varIntent.putExtra("Status8", Status8);
                startActivity(varIntent);
                finish();
            }
        });
        btnJadwal2 = (Button) findViewById(R.id.jadwal2) ;
        btnJadwal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama2.getText().toString();
                String nomor = Zonanumber2.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
                varIntent.putExtra("MAC", a);
                varIntent.putExtra("namaZona", nama);
                varIntent.putExtra("nomorZona", nomor);
                varIntent.putExtra("Status1", Status1);
                varIntent.putExtra("Status2", Status2);
                varIntent.putExtra("Status3", Status3);
                varIntent.putExtra("Status4", Status4);
                varIntent.putExtra("Status5", Status5);
                varIntent.putExtra("Status6", Status6);
                varIntent.putExtra("Status7", Status7);
                varIntent.putExtra("Status8", Status8);
                startActivity(varIntent);
                finish();
            }
        });
        btnJadwal3 = (Button) findViewById(R.id.jadwal3) ;
        btnJadwal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama3.getText().toString();
                String nomor = Zonanumber3.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
                varIntent.putExtra("MAC", a);
                varIntent.putExtra("namaZona", nama);
                varIntent.putExtra("nomorZona", nomor);
                varIntent.putExtra("Status1", Status1);
                varIntent.putExtra("Status2", Status2);
                varIntent.putExtra("Status3", Status3);
                varIntent.putExtra("Status4", Status4);
                varIntent.putExtra("Status5", Status5);
                varIntent.putExtra("Status6", Status6);
                varIntent.putExtra("Status7", Status7);
                varIntent.putExtra("Status8", Status8);
                startActivity(varIntent);
                finish();
            }
        });
        btnJadwal4 = (Button) findViewById(R.id.jadwal4);
        btnJadwal4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama4.getText().toString();
                String nomor = Zonanumber4.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
                varIntent.putExtra("MAC", a);
                varIntent.putExtra("namaZona", nama);
                varIntent.putExtra("nomorZona", nomor);
                varIntent.putExtra("Status1", Status1);
                varIntent.putExtra("Status2", Status2);
                varIntent.putExtra("Status3", Status3);
                varIntent.putExtra("Status4", Status4);
                varIntent.putExtra("Status5", Status5);
                varIntent.putExtra("Status6", Status6);
                varIntent.putExtra("Status7", Status7);
                varIntent.putExtra("Status8", Status8);
                startActivity(varIntent);
                finish();
            }
        });
        btnJadwal5 = (Button) findViewById(R.id.jadwal5);
        btnJadwal5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama5.getText().toString();
                String nomor = Zonanumber5.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
                varIntent.putExtra("MAC", a);
                varIntent.putExtra("namaZona", nama);
                varIntent.putExtra("nomorZona", nomor);
                varIntent.putExtra("Status1", Status1);
                varIntent.putExtra("Status2", Status2);
                varIntent.putExtra("Status3", Status3);
                varIntent.putExtra("Status4", Status4);
                varIntent.putExtra("Status5", Status5);
                varIntent.putExtra("Status6", Status6);
                varIntent.putExtra("Status7", Status7);
                varIntent.putExtra("Status8", Status8);
                startActivity(varIntent);
                finish();
            }
        });
        btnJadwal6 = (Button) findViewById(R.id.jadwal5);
        btnJadwal6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama5.getText().toString();
                String nomor = Zonanumber5.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
                varIntent.putExtra("MAC", a);
                varIntent.putExtra("namaZona", nama);
                varIntent.putExtra("nomorZona", nomor);
                varIntent.putExtra("Status1", Status1);
                varIntent.putExtra("Status2", Status2);
                varIntent.putExtra("Status3", Status3);
                varIntent.putExtra("Status4", Status4);
                varIntent.putExtra("Status5", Status5);
                varIntent.putExtra("Status6", Status6);
                varIntent.putExtra("Status7", Status7);
                varIntent.putExtra("Status8", Status8);
                startActivity(varIntent);
                finish();
            }
        });
        btnJadwal7 = (Button) findViewById(R.id.jadwal7);
        btnJadwal7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama6.getText().toString();
                String nomor = Zonanumber6.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
                varIntent.putExtra("MAC", a);
                varIntent.putExtra("namaZona", nama);
                varIntent.putExtra("nomorZona", nomor);
                varIntent.putExtra("Status1", Status1);
                varIntent.putExtra("Status2", Status2);
                varIntent.putExtra("Status3", Status3);
                varIntent.putExtra("Status4", Status4);
                varIntent.putExtra("Status5", Status5);
                varIntent.putExtra("Status6", Status6);
                varIntent.putExtra("Status7", Status7);
                varIntent.putExtra("Status8", Status8);
                startActivity(varIntent);
                finish();
            }
        });
        btnJadwal8 = (Button) findViewById(R.id.jadwal8);
        btnJadwal8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama7.getText().toString();
                String nomor = Zonanumber7.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
                varIntent.putExtra("MAC", a);
                varIntent.putExtra("namaZona", nama);
                varIntent.putExtra("nomorZona", nomor);
                varIntent.putExtra("Status1", Status1);
                varIntent.putExtra("Status2", Status2);
                varIntent.putExtra("Status3", Status3);
                varIntent.putExtra("Status4", Status4);
                varIntent.putExtra("Status5", Status5);
                varIntent.putExtra("Status6", Status6);
                varIntent.putExtra("Status7", Status7);
                varIntent.putExtra("Status8", Status8);
                startActivity(varIntent);
                finish();
            }
        });

        btnSiram1 = (Button) findViewById(R.id.siram1) ;
        btnSiram1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                loading = ProgressDialog.show(LihatZona.this,"Loading.....",null,true,true);
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama.getText().toString();
                String nomor = Zonanumber.getText().toString();
                String Pertama="10000000#0";
                String on="1"+Status2+Status3+Status4+Status5+Status6+Status7+Status8+"#0";
                String off="0"+Status2+Status3+Status4+Status5+Status6+Status7+Status8+"#0";
                if (Status1.equals("1")) {
                    setupConnectionFactory();
                    try {
                        publish(off);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else  {
                    setupConnectionFactory();
                    try {
                        publish(on);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                }else if (Status1.equals("")) {
//                    setupConnectionFactory();
//                    try {
//                        publish(Pertama);
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (KeyManagementException e) {
//                        e.printStackTrace();
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (TimeoutException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
////


            }
        });
        btnSiram2 = (Button) findViewById(R.id.siram2) ;
        btnSiram2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                kirim2();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama2.getText().toString();
                String nomor = Zonanumber2.getText().toString();
//                Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
//                varIntent.putExtra("mAC",a);
//                varIntent.putExtra("NamaZona", nama);
//                varIntent.putExtra("NomorZona", nomor);
//                startActivity(varIntent);
//                finish();
                String Pertama="01000000#0";
                String on=Status1+"1"+Status3+Status4+Status5+Status6+Status7+Status8+"#0";
                String off=Status1+"0"+Status3+Status4+Status5+Status6+Status7+Status8+"#0";
                if (Status2.equals("1")) {
                    setupConnectionFactory();
                    try {
                        publish(off);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if (Status2.equals("0")){
                    setupConnectionFactory();
                    try {
                        publish(on);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                }else {
//                    setupConnectionFactory();
//                    try {
//                        publish(Pertama);
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (KeyManagementException e) {
//                        e.printStackTrace();
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (TimeoutException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

            }
        });
        btnSiram3 = (Button) findViewById(R.id.siram3) ;
        btnSiram3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                kirim2();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama3.getText().toString();
                String nomor = Zonanumber3.getText().toString();
//                Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
                String Pertama="00100000#0";
                String on=Status1+Status2+"1"+Status4+Status5+Status6+Status7+Status8+"#0";
                String off=Status1+Status2+"0"+Status4+Status5+Status6+Status7+Status8+"#0";
                if (Status3.equals("1")) {
                    setupConnectionFactory();
                    try {
                        publish(off);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if (Status3.equals("0")){
                    setupConnectionFactory();
                    try {
                        publish(on);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                }else {
//                    setupConnectionFactory();
//                    try {
//                        publish(Pertama);
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (KeyManagementException e) {
//                        e.printStackTrace();
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (TimeoutException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

            }
        });
        btnSiram4 = (Button) findViewById(R.id.siram4);
        btnSiram4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                kirim2();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama3.getText().toString();
                String nomor = Zonanumber3.getText().toString();
//                Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
                String Pertama="00010000#0";
                String on=Status1+Status2+Status3+"1"+Status5+Status6+Status7+Status8+"#0";
                String off=Status1+Status2+Status3+"0"+Status5+Status6+Status7+Status8+"#0";
                if (Status4.equals("1")) {
                    setupConnectionFactory();
                    try {
                        publish(off);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if (Status4.equals("0"))  {
                    setupConnectionFactory();
                    try {
                        publish(on);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                }else {
//                    setupConnectionFactory();
//                    try {
//                        publish(Pertama);
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (KeyManagementException e) {
//                        e.printStackTrace();
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (TimeoutException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

            }
        });
        btnSiram5 = (Button) findViewById(R.id.siram5);
        btnSiram5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                kirim2();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama5.getText().toString();
                String nomor = Zonanumber5.getText().toString();
//                Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
                String Pertama="00001000#0";
                String on=Status1+Status2+Status3+Status4+"1"+Status6+Status7+Status8+"#0";
                String off=Status1+Status2+Status3+Status4+"0"+Status6+Status7+Status8+"#0";
                if (Status5.equals("1")) {
                    setupConnectionFactory();
                    try {
                        publish(off);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if (Status5.equals("0")){
                    setupConnectionFactory();
                    try {
                        publish(on);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                } else {
//                    setupConnectionFactory();
//                    try {
//                        publish(Pertama);
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (KeyManagementException e) {
//                        e.printStackTrace();
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (TimeoutException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

            }
        });

        btnSiram6 = (Button) findViewById(R.id.siram6);
        btnSiram6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                kirim2();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama6.getText().toString();
                String nomor = Zonanumber6.getText().toString();
//                Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
                String Pertama="00000100#0";
                String on=Status1+Status2+Status3+Status4+Status5+"1"+Status7+Status8+"#0";
                String off=Status1+Status2+Status3+Status4+Status5+"0"+Status7+Status8+"#0";
                if (Status6.equals("1")) {
                    setupConnectionFactory();
                    try {
                        publish(off);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if (Status6.equals("0")){
                    setupConnectionFactory();
                    try {
                        publish(on);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                }else {
//                    setupConnectionFactory();
//                    try {
//                        publish(Pertama);
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (KeyManagementException e) {
//                        e.printStackTrace();
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (TimeoutException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

            }
        });
        btnSiram7 = (Button) findViewById(R.id.siram7);
        btnSiram7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                kirim2();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama7.getText().toString();
                String nomor = Zonanumber7.getText().toString();
//                Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
                String Pertama="00000010#0";
                String on=Status1+Status2+Status3+Status4+Status5+Status6+"1"+Status8+"#0";
                String off=Status1+Status2+Status3+Status4+Status5+Status6+"0"+Status8+"#0";
                if (Status7.equals("1")) {
                    setupConnectionFactory();
                    try {
                        publish(off);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else  if (Status7.equals("0")) {
                    setupConnectionFactory();
                    try {
                        publish(on);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                }else {
//                    setupConnectionFactory();
//                    try {
//                        publish(Pertama);
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (KeyManagementException e) {
//                        e.printStackTrace();
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (TimeoutException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

            }
        });
        btnSiram8 = (Button) findViewById(R.id.siram8);
        btnSiram8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                kirim2();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama8.getText().toString();
                String nomor = Zonanumber8.getText().toString();
//                Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
                String Pertama="00000001#0";
                String on=Status1+Status2+Status3+Status4+Status5+Status6+Status7+"1"+"#0";
                String off=Status1+Status2+Status3+Status4+Status5+Status6+Status7+"0"+"#0";
                if (Status8.equals("1")) {
                    setupConnectionFactory();
                    try {
                        publish(off);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else   if (Status8.equals("0")){
                    setupConnectionFactory();
                    try {
                        publish(on);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//
//                } else{
//                    setupConnectionFactory();
//                    try {
//                        publish(Pertama);
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (KeyManagementException e) {
//                        e.printStackTrace();
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (TimeoutException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

            }
        });


//        String Mac = getIntent().getStringExtra( "mac" );
//        Toast.makeText(this, ""+Mac_Address, Toast.LENGTH_SHORT).show();
        tampil();
        tampilMac();
        tampil_status();
//        Toast.makeText(LihatZona.this, "ini status"+Status1, Toast.LENGTH_SHORT).show();

    }

    public void kirim(){
        Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
        startActivity(varIntent);
        finish();
    }
    public void kirim2(){
        Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
        startActivity(varIntent);
        finish();
    }

    public void tampilMac() {
        String Mac = getIntent().getStringExtra( "mac" );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    private void back() {
        Intent back = new Intent( LihatZona.this, MenuUtama.class );
        startActivity( back );
        finish();
    }

    public void tampil(){
        String Mac = getIntent().getStringExtra( "mac");
//        String nama = Zonanama.getText().toString();
//        Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
//        varIntent.putExtra("mac", Mac);
//        varIntent.putExtra("namaZona", nama);
//        startActivity(varIntent);
//        finish();

        NetworkService api = InitRetrofit.getInstance().getApi();
        Call<ResponseBody> menuCall = api.TampilZona(Mac);
        menuCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    try {
                        JSONObject obj = new JSONObject(response.body().string());
                        Log.i("pesan", obj.toString());
                        String device = obj.getString("device");
                        Log.i("pesan",  obj.getString("device"));
                        JSONArray array=new JSONArray(device);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonobject = array.getJSONObject(i);
                            String mac = jsonobject.getString("mac");
                            String zona = jsonobject.getString("zona");
                            Mac_Address=jsonobject.getString("mac");
                            txtTampil.setText(Mac_Address);
                             a = txtTampil.getText().toString();
                            Log.d("mac",Mac_Address);
                            Log.d("isi:",mac);
                            Log.d("zona:",zona);
                            JSONArray ray=new JSONArray(zona);
                            Log.d("response api", response.body().toString());
                            ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
                            for (int j = 0; j < ray.length(); j++) {
                                try {
                                    JSONObject obb = ray.getJSONObject(0);
                                    String zona_number = obb.getString("zona_number");
                                    String zona_name = obb.getString("nama");
                                    Zonanama.setText(zona_name);
                                    Zonanumber.setText(zona_number);
//                                Nomor=obb.getString("zona_number");
                                    if (Zonanama.equals("") || Zonanumber.equals("")) {
                                        satu.setVisibility(View.GONE);
                                    } else {
                                        satu.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    JSONObject obb1 = ray.getJSONObject(1);
                                    String zona_number1 = obb1.getString("zona_number");
                                    String zona_name1 = obb1.getString("nama");
                                    Zonanama2.setText(zona_name1);
                                    Zonanumber2.setText(zona_number1);
                                    if (Zonanama2.equals("") || Zonanumber2.equals("")) {
                                        dua.setVisibility(View.GONE);
                                    } else {
                                        dua.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {


                                    JSONObject obb2 = ray.getJSONObject(2);
                                    String zona_number2 = obb2.getString("zona_number");
                                    String zona_name2 = obb2.getString("nama");
                                    Zonanama3.setText(zona_name2);
                                    Zonanumber3.setText(zona_number2);
//                                Log.d("zona",zona_number);
//                                Log.d("zona:",zona_name);
//                                Log.d("zona:",zona_number1);
//                                Log.d("zona:",zona_name1);
                                    if (Zonanama3.equals("") || Zonanumber3.equals("")) {
                                        tiga.setVisibility(View.GONE);
                                    } else {
                                        tiga.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    JSONObject obb3 = ray.getJSONObject(3);
                                    String zona_number3 = obb3.getString("zona_number");
                                    String zona_name3 = obb3.getString("nama");
                                    Zonanama4.setText(zona_name3);
                                    Zonanumber4.setText(zona_number3);
//                                    Log.d("zona", zona_number);
//                                    Log.d("zona:", zona_name);
//                                    Log.d("zona:", zona_number1);
//                                    Log.d("zona:", zona_name1);
                                    if (Zonanama4.equals("") || Zonanumber4.equals("")) {
                                        empat.setVisibility(View.GONE);
                                    } else {
                                        empat.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {


                                    JSONObject obb4 = ray.getJSONObject(4);
                                    String zona_number5 = obb4.getString("zona_number");
                                    String zona_name5 = obb4.getString("nama");
                                    Zonanama5.setText(zona_name5);
                                    Zonanumber5.setText(zona_number5);
//                                Log.d("zona",zona_number);
//                                Log.d("zona:",zona_name);
//                                Log.d("zona:",zona_number1);
//                                Log.d("zona:",zona_name1);
                                    if (Zonanama5.equals("") || Zonanumber5.equals("")) {
                                        lima.setVisibility(View.GONE);
                                    } else {
                                        lima.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {


                                    JSONObject obb5 = ray.getJSONObject(5);
                                    String zona_number6 = obb5.getString("zona_number");
                                    String zona_name6 = obb5.getString("nama");
                                    Zonanama6.setText(zona_name6);
                                    Zonanumber6.setText(zona_number6);
//                                    Log.d("zona", zona_number);
//                                    Log.d("zona:", zona_name);
//                                    Log.d("zona:", zona_number1);
//                                    Log.d("zona:", zona_name1);
                                    if (Zonanama6.equals("") || Zonanumber6.equals("")) {
                                        enam.setVisibility(View.GONE);
                                    } else {
                                        enam.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {


                                    JSONObject obb6 = ray.getJSONObject(6);
                                    String zona_number7 = obb6.getString("zona_number");
                                    String zona_name7 = obb6.getString("nama");
                                    Zonanama7.setText(zona_name7);
                                    Zonanumber7.setText(zona_number7);
                                    Log.d("zona", zona_number7);
                                    Log.d("zona:", zona_name7);
                                    Log.d("zona:", zona_number7);
                                    Log.d("zona:", zona_name7);
                                    if (Zonanama7.equals("") || Zonanumber7.equals("")) {
                                        tujuh.setVisibility(View.GONE);
                                    } else {
                                        tujuh.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    JSONObject obb7 = ray.getJSONObject(7);
                                    String zona_number8 = obb7.getString("zona_number");
                                    String zona_name8 = obb7.getString("nama");
                                    Zonanama8.setText(zona_name8);
                                    Zonanumber8.setText(zona_number8);
                                    Log.d("zona", zona_number8);
                                    Log.d("zona:", zona_name8);
                                    Log.d("zona:", zona_number8);
                                    Log.d("zona:", zona_name8);
                                    if (Zonanama8.equals("") || Zonanumber8.equals("")) {
                                        delapan.setVisibility(View.GONE);
                                    } else {
                                        delapan.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }
    public  void tampil_status(){
        String Mac =getIntent().getStringExtra( "mac");
        NetworkService api = InitRetrofit.getInstance().getApi();
        Call<ResponseBody> menuCall = api.TampilStatus(Mac);
        menuCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    try {
                        JSONObject obj = new JSONObject(response.body().string());
                        Log.i("pesan", obj.toString());
                        String device = obj.getString("histori");
                        Log.v("isi",device);
                        JSONArray array=new JSONArray(device);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonobject = array.getJSONObject(i);
                            String value = jsonobject.getString("value");
                            Log.v("value",value);
                            char[] ch = new char[value.length()];
                            for (int j = 0; j < value.length(); j++) {
                                ch[j] = value.charAt(j);
                                try {
                                    Log.v("value", String.valueOf(value.charAt(0)));
                                    Status1 = String.valueOf(value.charAt(0));
                                    if (Status1.equals("1")) {
                                        btnSiram1.setText("OFF");
                                    } else if (Status1.equals("0")) {
                                        btnSiram1.setText("ON");
                                    }
//                                    else if (Status1.equals("")) {
//                                        btnSiram1.setText("ON/OFF");
//                                    }

                                    Log.v("value", String.valueOf(value.charAt(1)));
                                    Status2 = String.valueOf(value.charAt(1));
//                                status2.setText(Status2);
                                    if (Status2.equals("1")) {
                                        btnSiram2.setText("OFF");
                                    } else if (Status2.equals("0")) {
                                        btnSiram2.setText("ON");
                                    }
//                                    else {
//                                        btnSiram2.setVisibility(View.GONE);
//                                    }
                                    Log.v("value", String.valueOf(value.charAt(2)));
                                    Status3 = String.valueOf(value.charAt(2));
                                    if (Status3.equals("1")) {
                                        btnSiram3.setText("OFF");
                                    } else if(Status3.equals("0")) {
                                        btnSiram3.setText("ON");
                                    }
//                                    else {
//                                        btnSiram3.setVisibility(View.GONE);
//                                    }
                                    Log.v("value", String.valueOf(value.charAt(3)));
                                    Status4 = String.valueOf(value.charAt(3));
                                    Log.v("pesan4", Status4);
                                    if (Status4.equals("1")) {
                                        btnSiram4.setText("OF");
                                    } else if (Status4.equals("0")){
                                        btnSiram4.setText("ON");
                                    }
//                                    else if (Status4.equals("")) {
////                                        btnSiram4.setVisibility(View.GONE);
//                                    }
                                    Log.v("value", String.valueOf(value.charAt(4)));
                                    Status5 = String.valueOf(value.charAt(4));
                                    if (Status5.equals("1")) {
                                        btnSiram5.setText("OFF");
                                    } else if (Status5.equals("0")){
                                        btnSiram5.setText("ON");
                                    }
//                                    else {
////                                        btnSiram5.setVisibility(View.GONE);
//                                    }
                                    Log.v("value", String.valueOf(value.charAt(5)));
                                    Status6 = String.valueOf(value.charAt(5));
                                    if (Status6.equals("1")) {
                                        btnSiram6.setText("OF");
                                    } else if (Status6.equals("0"))  {
                                        btnSiram6.setText("ON");
                                    }
//                                    else {
//                                        btnSiram6.setVisibility(View.GONE);
//                                    }
                                    Log.v("value", String.valueOf(value.charAt(6)));
                                    Status7 = String.valueOf(value.charAt(6));
                                    if (Status7.equals("1")) {
                                        btnSiram7.setText("OFF");
                                    } else if  (Status7.equals("0")){
                                        btnSiram7.setText("ON");
                                    }
//                                    else {
////                                        btnSiram7.setVisibility(View.GONE);
//                                    }
                                    Log.v("value", String.valueOf(value.charAt(7)));
                                    Status8 = String.valueOf(value.charAt(7));
                                    if (Status8.equals("1")) {
                                        btnSiram8.setText("OFF");
                                    } else if(Status8.equals("0")) {
                                        btnSiram8.setText("ON");
                                    }
//                                    else {
//                                        btnSiram8.setVisibility(View.GONE);
//                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }

    public void setupConnectionFactory() {
        try {
            factory.setAutomaticRecoveryEnabled(false);
            factory.setUri("amqp://"+user+":"+pass+"@"+host);
            factory.setVirtualHost(vhost);
        } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
    public  void  publish(String message) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        String macAddres = getIntent().getStringExtra( "mac");
        Log.i("pesan",macAddres);
        Log.d("ini",message);
        String queue_name_publish ="mqtt-subscription-"+macAddres+"qos0";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = factory.newConnection();
        Log.d("ConnectionRMQ", "publish: "+connection.isOpen());
        Channel channel = connection.createChannel();
        Log.d("ChannelRMQ", "publish: "+channel.isOpen());
        if (channel.isOpen()== true) {
//            loading.dismiss();
            Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LihatZona.this, MenuUtama.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show();
        }
        String messageOn = message ;
        channel.basicPublish("", queue_name_publish,null,messageOn.getBytes());
    }


}
package com.example.hans.agrigo.TriggerSiram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.ResponseDelivery;
import com.example.hans.agrigo.Config.AdapterConfig;
import com.example.hans.agrigo.Config.AdapterDevice;
import com.example.hans.agrigo.Config.Item_Config;
import com.example.hans.agrigo.Config.Item_Device;
import com.example.hans.agrigo.Config.Response_Config;
import com.example.hans.agrigo.Config.Response_Device;
import com.example.hans.agrigo.Menu.MenuUtama;
import com.example.hans.agrigo.MenuScanBarcode.AddDevice;
import com.example.hans.agrigo.Network.InitRetrofit;
import com.example.hans.agrigo.Network.NetworkService;
import com.example.hans.agrigo.R;
import com.example.hans.agrigo.Storage.SharedPrefManager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class TriggerWaterLevel extends AppCompatActivity {
    String IsianSpinnerAct, IsianSpinnerSs, valueOn, valueOff;
    ProgressDialog Loading;
    SharedPrefManager sharedPrefManager;
    Button Simpan;
    TextView mac;
    ConnectionFactory factory = new ConnectionFactory();

    String user = "shadoofpertanian";
    String pass = "TaniBertani19";
    String host = "shadoofpertanian.pptik.id";
    String vhost = "/shadoofpertanian";

    String [] hidupWater = {"Full","Medium","Empty"};
    String [] hidupSoil = {"basah","Kering"};

    String [] matiWater = {"Full","Medium","Empty"};
    String [] matiSoil = {"basah","Kering"};

    private Spinner Act, Ss, value1, onOff, onOff2, value2;
    String Mac_Addreass,Mac_Addreass2,MacAct,MacAct2;
    LinearLayout satu,dua;
    TextView tiga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigger_water_level);

        Act = (Spinner) findViewById(R.id.spinnerAct);
        Ss = (Spinner) findViewById(R.id.spinnerSS);
        value1 = (Spinner) findViewById(R.id.nilaiSensor);
        value2 = (Spinner) findViewById(R.id.nilaiSensor2);
        sharedPrefManager = new SharedPrefManager(this);

        initSpinnerAct();
        Act.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedIsian = parent.getItemAtPosition(position).toString();
                IsianSpinnerAct = selectedIsian;
                if (selectedIsian.equals("Selenoid Level")){
                    IsianSpinnerAct=""+MacAct;
//                    Toast.makeText(TriggerWaterLevel.this, ""+IsianSpinnerAct, Toast.LENGTH_SHORT).show();
                }else if (selectedIsian.equals("Watering")){
                    IsianSpinnerAct=""+MacAct2;
//                    Toast.makeText(TriggerWaterLevel.this, ""+IsianSpinnerAct, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initSpinnerSs();
        Ss.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedIsian = parent.getItemAtPosition(position).toString();
                IsianSpinnerSs = selectedIsian;
                idup();
                mati();
                if (selectedIsian.equals("Sensor Soil")){
                IsianSpinnerSs=""+Mac_Addreass;
//                    Toast.makeText(TriggerWaterLevel.this, ""+IsianSpinnerSs, Toast.LENGTH_SHORT).show();
                }else if (selectedIsian.equals("Water Level")){
                    IsianSpinnerSs=""+Mac_Addreass2;
//                    Toast.makeText(TriggerWaterLevel.this, ""+IsianSpinnerSs, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String b = value1.getSelectedItem().toString();
                String selectedOn = parent.getItemAtPosition(position).toString();
                valueOn = selectedOn;
//                Toast.makeText(TriggerWaterLevel.this, "Kondisi Hidup Ketika : " + selectedOn, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        value2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOff = parent.getItemAtPosition(position).toString();
                valueOff = selectedOff;
//                Toast.makeText(TriggerWaterLevel.this, "Kondisi Mati Ketika : " + selectedOff, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Simpan = (Button)findViewById(R.id.Simpan_config) ;
        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(TriggerWaterLevel.this, ""+Mac_Addreass, Toast.LENGTH_SHORT).show();
                Simpan();
            }
        });

        satu=(LinearLayout)findViewById(R.id.gone);
        dua=(LinearLayout)findViewById(R.id.gone1);
        tiga=(TextView)findViewById(R.id.gone0);
        satu.setVisibility(View.GONE);
        dua.setVisibility(View.GONE);
        tiga.setVisibility(View.GONE);
        Simpan.setVisibility(View.GONE);


    }

    private void mati() {
        String a = Ss.getSelectedItem().toString();
//        value2.setOnItemSelectedListener(this);
        if(a.equals("Sensor Soil")){
            ArrayAdapter aa = new ArrayAdapter(TriggerWaterLevel.this,android.R.layout.simple_spinner_item,hidupSoil);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            value2.setAdapter(aa);
        } else if (a.equals("Water Level")){
            ArrayAdapter aa = new ArrayAdapter(TriggerWaterLevel.this,android.R.layout.simple_spinner_item,hidupWater);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            value2.setAdapter(aa);
        }
    }

    private void idup() {
        String a = Ss.getSelectedItem().toString();
//        value1.setOnItemSelectedListener(this);
        if(a.equals("Sensor Soil")){
            ArrayAdapter aa = new ArrayAdapter(TriggerWaterLevel.this,android.R.layout.simple_spinner_item,hidupSoil);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            value1.setAdapter(aa);

        } else if (a.equals("Water Level")){
            ArrayAdapter aa = new ArrayAdapter(TriggerWaterLevel.this,android.R.layout.simple_spinner_item,hidupWater);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            value1.setAdapter(aa);
//            Toast.makeText(TriggerWaterLevel.this, "Tidak Ada data Menu saat ini", Toast.LENGTH_SHORT).show();
        }
    }

    private void initSpinnerSs() {
//        Loading = ProgressDialog.show(TriggerWaterLevel.this, null, "harap tunggu...", true, false);
        String d_guid = sharedPrefManager.getSpGuid();
        String d_type="Sensor";
        NetworkService api = InitRetrofit.getInstance().getApi();
        retrofit2.Call<Response_Config> menuCall = api.getDeviceAktivasi(d_guid,d_type);
        menuCall.enqueue(new Callback<Response_Config>() {
            @Override
            public void onResponse(retrofit2.Call<Response_Config> call, Response<Response_Config> response) {
                if (response.isSuccessful()) {
                    Log.d("response api", response.body().toString());
                    List<Item_Config> data_menu = response.body().getMenu();
                    boolean status = response.body().isStatus();
                    if (status) {
                        satu.setVisibility(View.VISIBLE);
                        dua.setVisibility(View.VISIBLE);
                        tiga.setVisibility(View.VISIBLE);
                        Simpan.setVisibility(View.VISIBLE);
//                        Loading.dismiss();
                        List<Item_Config> semuadosenItems = response.body().getMenu();
                        List<String> listSpinner = new ArrayList<String>();
                        for (int i = 0; i < semuadosenItems.size(); i++) {
                            listSpinner.add(semuadosenItems.get(i).getNama());
                            try {
                                Mac_Addreass=(semuadosenItems.get(0).getMac());
                                Mac_Addreass2=semuadosenItems.get(1).getMac();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

//                            Toast.makeText(TriggerWaterLevel.this, ""+Fia, Toast.LENGTH_SHORT).show();
                        }
                        ArrayAdapter<String> adapteres = new ArrayAdapter<String>(TriggerWaterLevel.this,
                        android.R.layout.simple_spinner_item, listSpinner);
                        adapteres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Ss.setAdapter(adapteres);
//                        Adapter_Custome adapterlagi = new Adapter_Custome(Nasi_Kotak.this, data_menu);
//                        List<Menu_Item> dataMenu_items= response.body().getMenu();
                        AdapterConfig adapter_custome = new AdapterConfig(TriggerWaterLevel.this, data_menu);
//                        adapter_custome.onBindViewHolder(holder.harga.setT
                    } else {
//                        Toast.makeText(TriggerWaterLevel.this, "Tidak Ada data Menu saat ini", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response_Config> call, Throwable t) {
                try {
                    satu.setVisibility(View.GONE);
                    dua.setVisibility(View.GONE);
                    tiga.setVisibility(View.GONE);
                    Simpan.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initSpinnerAct() {

    String d_guid = sharedPrefManager.getSpGuid();
        String d_type="Actuator";
        NetworkService api = InitRetrofit.getInstance().getApi();
        retrofit2.Call<Response_Config> menuCall = api.getDeviceAktivasi(d_guid,d_type);
        menuCall.enqueue(new Callback<Response_Config>() {
            @Override
            public void onResponse(retrofit2.Call<Response_Config> call, Response<Response_Config> response) {
                if (response.isSuccessful()) {
//                    Log.d("response api", response.body().toString());
                    List<Item_Config> data_menu = response.body().getMenu();

                    boolean status = response.body().isStatus();
                    if (status) {
                        satu.setVisibility(View.VISIBLE);
                        dua.setVisibility(View.VISIBLE);
                        tiga.setVisibility(View.VISIBLE);
                        Simpan.setVisibility(View.VISIBLE);
//                        Loading.dismiss();
                        List<Item_Config> semuadosenItems = response.body().getMenu();
//                        Log.d("mac", String.valueOf(response.body().getMenu()+ " "+(semuadosenItems.get(i).getMac())));
                        List<String> listSpinner = new ArrayList<String>();
                        for (int i = 0; i < semuadosenItems.size(); i++) {
                            listSpinner.add(semuadosenItems.get(i).getNama());
                            try {
                                MacAct = semuadosenItems.get(0).getMac();
                                MacAct2 = semuadosenItems.get(1).getMac();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                        ArrayAdapter<String> adapteres = new ArrayAdapter<String>(TriggerWaterLevel.this,
                                android.R.layout.simple_spinner_item, listSpinner);
                        adapteres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Act.setAdapter(adapteres);
                        AdapterConfig adapter_custome = new AdapterConfig(TriggerWaterLevel.this, data_menu);

                    } else {
//                        Toast.makeText(TriggerWaterLevel.this, "Tidak Ada data Menu saat ini", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Response_Config> call, Throwable t) {
                try {
                    satu.setVisibility(View.GONE);
                    dua.setVisibility(View.GONE);
                    tiga.setVisibility(View.GONE);
                    Simpan.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Simpan() {
        Loading = ProgressDialog.show(TriggerWaterLevel.this, null, "harap tunggu...", true, false);
        String d_act = String.valueOf(IsianSpinnerAct);
        String d_ss = String.valueOf(IsianSpinnerSs);
        String d_on = String.valueOf(valueOn);
        String d_of = String.valueOf(valueOff);
        String d_guid = sharedPrefManager.getSpGuid();
        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().Simpan_Config(d_act, d_ss, d_on, d_of,d_guid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                        Loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("msg").equals("Berhasil")
//                                || d_act.equals("Watering") && d_ss.equals("Sensor Soil")
//                                || d_act.equals("Selenoid Level") && d_ss.equals("Water Level")
                        ) {
                            Toast.makeText(TriggerWaterLevel.this, "Pengaturan Trigger Berhasil", Toast.LENGTH_SHORT).show();
                            publishtrue();
                            Intent intent = new Intent(TriggerWaterLevel.this, MenuUtama.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(TriggerWaterLevel.this, "Gagal, Ulangi Kembali", Toast.LENGTH_SHORT).show();
                            String error_message = jsonRESULTS.getString("msg");
                            Toast.makeText(TriggerWaterLevel.this, error_message, Toast.LENGTH_SHORT).show();
                            Log.d("Pesan", error_message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(TriggerWaterLevel.this, "Gagal, Ulangi Kembali", Toast.LENGTH_SHORT).show();
//                        loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TriggerWaterLevel.this, "Gagal, Ulangi Kembali", Toast.LENGTH_SHORT).show();
                Log.d("responsenya", t.toString());
                Toast.makeText(TriggerWaterLevel.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void publishtrue() throws JSONException {
        String d_act = String.valueOf(IsianSpinnerAct);
        String d_ss = String.valueOf(IsianSpinnerSs);
        String d_on = String.valueOf(valueOn);
        String d_of = String.valueOf(valueOff);
        String d_guid = sharedPrefManager.getSpGuid();
        setupConnectionFactory();
        JSONObject obj =new JSONObject();
        obj.put("mac_act",d_act);
        obj.put("mac_ss",d_ss);
        obj.put("value_on",d_on);
        obj.put("value_off",d_of);
        obj.put("guid",d_guid);
        obj.put("pesan","true");
        try {
            publish(obj.toString());
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

    public  void  publish(String message) throws NoSuchAlgorithmException,
            KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
//        String macAddres =macSiram.getText().toString();
        String queue_name_publish ="trigercallback";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = factory.newConnection();
        Log.d("ConnectionRMQ", "publish: "+connection.isOpen());
        Channel channel = connection.createChannel();
        Log.d("ChannelRMQ", "publish: "+channel.isOpen());
        String messageOn = message ;
        channel.basicPublish("", queue_name_publish,null,messageOn.getBytes());
    }

    public void onBackPressed() {
        super.onBackPressed();
        goBackMenu();
    }

    public void goBackMenu() {
        startActivity(new Intent(TriggerWaterLevel.this, MenuUtama.class));
        finish();
    }
}
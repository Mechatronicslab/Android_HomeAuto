package com.example.hans.agrigo.SiramZona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hans.agrigo.LihatZona.LihatZona;
import com.example.hans.agrigo.SiramZona.GlobalVariablee.GlobalVariable;
import com.example.hans.agrigo.SiramZona.Helper.RabbitMq;
import com.example.hans.agrigo.Menu.MenuUtama;
import com.example.hans.agrigo.R;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;

public class SiramZona extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText edtTanggal, edtJam;
    TextView macSiram, nomorSiram, namaZonaSiram;

    private Spinner spinner1, spinner2;
    Button btnZona1, btnZona2, btnZona3;
    GlobalVariable gb = new GlobalVariable();
    RabbitMq rmq = new RabbitMq();
    ConnectionFactory factory = new ConnectionFactory();

    String user = "shadoofpertanian";
    String pass = "TaniBertani19";
    String host = "shadoofpertanian.pptik.id";
    String vhost = "/shadoofpertanian";

    String[] times1={"59","58","57","56","55","54","53","52","51","50","49","48","47","46","45","44","43","42","41","40","39",
            "38","37","36","35","34"
            ,"33","32","31","30","29","28","27","26","25","24","23","22","21","20","19","18","17","16","15","14",
            "13","12","11","10","9","8","7","6","5","4","3","2","1","0"};

    String[] times2={"59","58","57","56","55","54","53","52","51","50","49","48","47","46","45","44","43","42","41","40","39",
            "38","37","36","35","34"
            ,"33","32","31","30","29","28","27","26","25","24","23","22","21","20","19","18","17","16","15","14",
            "13","12","11","10","9","8","7","6","5","4","3","2","1","0"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siram_zona);

        macSiram = (TextView) findViewById(R.id.macSiram);
        nomorSiram = (TextView) findViewById(R.id.nomorSiram);
        namaZonaSiram = (TextView) findViewById(R.id.namaZonaSiram);
        tampil();

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        btnZona1 = (Button) findViewById( R.id.btnZona1 );
        btnZona1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setupConnectionFactory();
                    zona1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } );

        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner1.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,times1);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(aa);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,times2);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(aaa);

    }

    private void tampil() {
        String namaZona = getIntent().getStringExtra( "NamaZona" );
        String nomor = getIntent().getStringExtra( "NomorZona" );
        String mac = getIntent().getStringExtra( "mAC" );
        namaZonaSiram.setText( namaZona );
        nomorSiram.setText(nomor);
        macSiram.setText(mac);
    }



    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtTanggal.setText(sdf.format(myCalendar.getTime()));
    }

    private void zona1() throws InterruptedException, NoSuchAlgorithmException,
            KeyManagementException, TimeoutException, URISyntaxException, IOException{
        String waktu1 = spinner1.getSelectedItem().toString();
        String waktu2 = spinner2.getSelectedItem().toString();
        String pesan  = nomorSiram.getText().toString();
        if(pesan.equals( "1" )){
            pesan = "0011";
        } else if (pesan.equals( "2" )){
            pesan = "0101";
        } else if (pesan.equals( "3" )){
            pesan = "0110";
        }

        int a = Integer.parseInt( waktu1 );
        int b = Integer.parseInt( waktu2 );
        int detik = b * 1000;
        int menit = a * 60000;
        int total = menit + detik;


        if (times1.equals(null)){
            Toast.makeText( getApplicationContext(),"gagal", Toast.LENGTH_SHORT ).show();
        } else {
            Toast.makeText( getApplicationContext(),"Sukses", Toast.LENGTH_SHORT ).show();
        }

        publish( pesan+"#"+total);
        Log.d("pesan",pesan+"#"+total);
//        Toast.makeText(this, ""+pesan+"#"+total, Toast.LENGTH_SHORT).show();

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
        String macAddres =macSiram.getText().toString();
        String queue_name_publish ="mqtt-subscription-"+macAddres+"qos0";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = factory.newConnection();
        Log.d("ConnectionRMQ", "publish: "+connection.isOpen());
        Channel channel = connection.createChannel();
        Log.d("ChannelRMQ", "publish: "+channel.isOpen());
        String messageOn = message ;
        channel.basicPublish("", queue_name_publish,null,messageOn.getBytes());
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(), times3[position]+times2[position]+times[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    private void back() {
        Intent a = new Intent( SiramZona.this, MenuUtama.class );
        startActivity( a );
        finish();
    }
}
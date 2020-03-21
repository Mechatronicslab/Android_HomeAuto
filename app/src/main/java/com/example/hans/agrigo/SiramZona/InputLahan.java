package com.example.hans.agrigo.SiramZona;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hans.agrigo.Menu.MenuUtama;
import com.example.hans.agrigo.R;

public class InputLahan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_input_lahan );
    }

    public void onBackPressed() {
        super.onBackPressed();
        goBackMenu();
    }

    public void goBackMenu(){
        startActivity(new Intent(this, MenuUtama.class));
        finish();
    }
}

package com.example.hans.agrigo.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.hans.agrigo.SiramZona.SiramZona;
import com.example.hans.agrigo.MenuLogin.Login;
import com.example.hans.agrigo.R;
import com.example.hans.agrigo.Storage.SharedPrefManager;

public class AccountFragment extends Fragment {
    SharedPrefManager sharedPrefManager;
    public static String KEY_ACTIVITY = "msg_activity";
    public static String KEY_ACTIVITY1 = "msg_activity1";
    public static String KEY_ACTIVITY2 = "msg_activity2";

    public final static String TAG_EMAIL = "email";
    public final static String TAG_NAME = "name";
    public final static String TAG_NAME2 = "name2";
    SharedPreferences sharedPreferences;
    public static final String session_status = "session_status";

    String email, name, name2;
    TextView txtEmail;
    TextView txtUsername;
    Button btnLogout, btnTentang, btnSiram;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        sharedPrefManager = new SharedPrefManager(getActivity());
        txtEmail = (TextView) view.findViewById(R.id.txt_email);
        txtUsername = (TextView) view.findViewById(R.id.txt_name);
        btnLogout = (Button) view.findViewById(R.id.btn_logout);
//        btnJadwal = (Button) view.findViewById(R.id.btn_jadwal);
//        btnTentang = (Button) view.findViewById(R.id.btn_tentang);
//        btnSiram = (Button) view.findViewById(R.id.btn_siram);
        tampil_datauser();

//        try {
//            String email = getArguments().getString(KEY_ACTIVITY);
//            String name = getArguments().getString(KEY_ACTIVITY1);
//            String name2 = getArguments().getString(KEY_ACTIVITY2);
//            if (email != null) {
//                txtEmail.setText(email);
//                txtUsername.setText(name +" "+ name2);
//            } else {
//
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();

            }
        });

//        btnJadwal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), LihatZona.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });

//        btnSiram.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent tampil = new Intent(getActivity(), SiramZona.class);
//                startActivity(tampil);
//                getActivity().finish();
//            }
//        });

//        btnTentang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//                // set title dialog
//                // set pesan dari dialog
//                alertDialogBuilder
//                        .setMessage("SHADOOF adalah kata dari peradaban mesir kuno yang berarti pertanian pintar. "
//                                +
//                                "Yang hari ini dikenal dengan sebutan SMART AGRICULTURE"+"\n"+"\n"
//                                +"\n"+"\n"
//                                +"Supported by :"+"\n"
//                                +"PPTIK ITB, UBL, ITB, PT.LSKK"+"\n"+"\n"
//                                +"Created by : "+"\n"
//                                +"Handoko Bimawidjaya")
//                        .setIcon(R.mipmap.ic_launcher)
//                        .setCancelable(false)
//                        .setNegativeButton("Oke",new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // jika tombol ini diklik, akan menutup dialog
//                                // dan tidak terjadi apa2
//                                dialog.cancel();
//                            }
//
//                        });
//
//                // membuat alert dialog dari builder
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // menampilkan alert dialog
//                alertDialog.show();
//            }
//        });
        return view;

    }



//    private void logout() {
//        sharedPreferences = getActivity().getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(session_status, false);
//        editor.putString(TAG_EMAIL, null);
//        editor.putString(TAG_NAME, null );
//        editor.putString(TAG_NAME2, null );
//        editor.commit();
//
//        Intent a = new Intent(getActivity(), Login.class);
//        startActivity(a);
//        getActivity().finish();
//    }

    private void tampil_datauser() {
//        Log.d("response user", sharedPrefManager.getSPNama()+sharedPrefManager.getSPAlamat()+sharedPrefManager.getSPEmail()+sharedPrefManager.getSPTelpon().toString());
//        Toast.makeText(getActivity(), "ini datanya="+sharedPrefManager.getSPNama()+sharedPrefManager.getSPAlamat()+sharedPrefManager.getSPEmail()+sharedPrefManager.getSPTelpon(), Toast.LENGTH_SHORT).show();
        txtEmail.setText(sharedPrefManager.getSPEmail());
        txtUsername.setText(sharedPrefManager.getSPNama());
//        Telpon.setText(sharedPrefManager.getSPTelpon());
//        Alamat.setText(sharedPrefManager.getSPAlamat());

    }
}
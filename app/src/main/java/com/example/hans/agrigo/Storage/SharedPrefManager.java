package com.example.hans.agrigo.Storage;
/*
Create By Asep Trisna Setiawan
Bandung 2020
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_LOGIN_APP = "Sp_Iot";
    public static final String SP_ID= "spId";
    public static final String SP_GUID= "spGuid";
    public static final String SP_NIK= "spNik";
    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_TELPON= "spTelpon";
    public static final String SP_ALAMAT = "spAlamat";
    public static final String SP_SUDAH_LOGIN = "SudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_LOGIN_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public String getSPAlamat(){
        return sp.getString(SP_ALAMAT, "");
    }

    public String getSPTelpon(){
        return sp.getString(SP_TELPON, "");
    }

    public Boolean getSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public String getSpGuid(){
        return sp.getString(SP_GUID, "");
    }

    public String getSpId(){
        return sp.getString(SP_ID, "");
    }
    public String getSpNik(){
        return sp.getString(SP_NIK, "");
    }
}

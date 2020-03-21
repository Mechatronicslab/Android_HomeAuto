package com.example.hans.agrigo.Network;


/*
Create By Asep Trisna Setiawan
Bandung 2020
 */

import android.renderscript.Sampler;

import com.example.hans.agrigo.Config.Response_Config;
import com.example.hans.agrigo.Config.Response_Device;
import com.example.hans.agrigo.LihatZona.Support.ResponZona;
import com.example.hans.agrigo.MenuLogin.Login_Response;
import com.example.hans.agrigo.Storage.SharedPrefManager;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface NetworkService {
    SharedPrefManager sharedPrefManager = null;
    final String id=sharedPrefManager.getSpId();

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("ambil/zona")
    Call<ResponseBody>regisZona(
            @Field("mac") String mac,
            @Field("zona_name") String namaZona,
            @Field("zona_number") String nomorZona,
            @Field("nomor_zona") String nomor_Zona,
            @Field("nama") String nama2
    );

    //    regis user
    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseBody> RegsiterUser(
            @Field("guid") String d_guid,
            @Field("no_ktp") String d_ktp,
            @Field("nama") String d_nama,
            @Field("no_hp") String d_tlp,
            @Field("alamat") String d_alamat,
            @Field("email") String d_email,
            @Field("password") String d_password,
            @Field("latitude") String d_lat,
            @Field("longitude") String d_long
    );

    //   device
    @FormUrlEncoded
    @POST("devices")
    Call<ResponseBody>Device (
            @Field("devices_mac_address") String d_mac,
            @Field("devices_name") String d_namadevice,
            @Field("devices_code") String d_devicecode
    );
//    aktivasi device
    @FormUrlEncoded
    @POST("device/aktivasi")
    Call<ResponseBody>Aktivasi_Device(
            @Field("userId") String UserId,
            @Field("mac") String d_mac,
            @Field("deviceCode") String d_code,
            @Field("guid_id") String guid
    );

    @FormUrlEncoded
    @POST("ambil/userLogin")
    Call<Response_Device>Tampil_Device(
            @Field("email") String d_mail
    );

    @FormUrlEncoded
    @POST("ambil/getZona")
    Call<ResponseBody>TampilZona(
            @Field("mac") String mac
    );

    @FormUrlEncoded
    @POST("ambil/findMac")
    Call<ResponseBody>getmac(
            @Field("mac") String d_macA
    );

    @FormUrlEncoded
    @POST("ambil/waterlev")
    Call<ResponseBody>level(
            @Field("mac") String d_mac
    );
    @FormUrlEncoded
    @POST("ambil/soil")
    Call<ResponseBody>soil(
            @Field("mac") String d_mac
    );
    @FormUrlEncoded
    @POST("ambil/waterflow")
    Call<ResponseBody>flow(
            @Field("mac") String d_mac
    );

    @FormUrlEncoded
    @POST("ambil/addConfig")
    Call<ResponseBody>Simpan_Config(
            @Field("mac_actuator") String d_act,
            @Field("mac_sensor") String d_ss,
            @Field("value_on") String d_on,
            @Field("value_off") String _off,
            @Field("guid") String d_guid
    );

    @FormUrlEncoded
    @POST("ambil/getAct")
    Call<Response_Config>getDeviceAktivasi(
            @Field("guid") String d_guid,
            @Field("devices_type") String d_tipe
    );

    @FormUrlEncoded
    @POST("ambil/updateUser")
    Call<ResponseBody>upadatelokasi(
            @Field("email") String d_email,
            @Field("latitude") double d_lat,
            @Field("longitude") double d_long

    );

    @FormUrlEncoded
    @POST("ambil/getHistory")
    Call<ResponseBody>TampilStatus(
            @Field("mac") String mac
    );

    @FormUrlEncoded
    @POST("ambil/addhistori")
    Call<ResponseBody>regishistori(
            @Field("mac") String Mac,
            @Field("value") String Value

    );

    @FormUrlEncoded
    @POST("ambil/addjadwal")
    Call<ResponseBody>regisjdwal(
            @Field("mac") String Mac,
            @Field("value") String Value
    );

}


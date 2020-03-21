package com.example.hans.agrigo.Config;

import com.google.gson.annotations.SerializedName;

public class Item_Device {

    @SerializedName("mac")
    private String mac;

    @SerializedName("devices_name")
    private String nama;

    @SerializedName("devices_code")
    private String code;


    public void setMac(String mac){

        this.mac = mac;
    }

    public String getMac(){
        return mac;
    }

    public void setNama(String nama){

        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }


    public void setCode(String code){

        this.code = code;
    }

    public String getCode(){
        return code;
    }

    @Override
    public String toString(){
        return
                "Item_Device{" +
                        "Mac= '" + mac+ '\'' +
                        "Code= '" + code+ '\'' +
                        ",nama= '" + nama+ '\'' +
                        "}";
    }

}

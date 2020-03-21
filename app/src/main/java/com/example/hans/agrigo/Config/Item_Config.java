package com.example.hans.agrigo.Config;

import com.google.gson.annotations.SerializedName;

public class Item_Config {
    @SerializedName("mac")
    private String mac;

    @SerializedName("devices_name")
    private String nama;

    @SerializedName("devices_code")
    private String code;

    @SerializedName("devices_type")
    private String type;


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


    public void setType(String type){

        this.type = type;
    }

    public String getType(){
        return type;
    }





    @Override
    public String toString(){
        return
                "Item_Config{" +
                        "Mac= '" + mac+ '\'' +
                        "Code= '" + code+ '\'' +
                        ",nama= '" + nama+ '\'' +
                        ",type= '" + type+ '\'' +
                        "}";
    }

}

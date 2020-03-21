package com.example.hans.agrigo.LihatZona.Support;

import com.google.gson.annotations.SerializedName;

public class Item_Zona {
    @SerializedName("mac")
    private String mac_address;

    @SerializedName("_id")
    private String nama;
//    @SerializedName("zona")
//    private String zona;


    public void setMac(String Mac_address){

        this.mac_address = Mac_address;
    }

    public String getMac_address(){
        return mac_address;
    }



    public void setNama(String nama){

        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }
    public void setZona(String zona){

//        this.zona = zona;
//    }
//
//    public String getZona(){
//        return zona;
    }

    @Override
    public String toString(){
        return
                "Item_Zona{" +
                        "Mac= '" + mac_address+ '\'' +
                        ",nama= '" + nama+ '\'' +
//                        ",zona= '" + zona+ '\'' +
                        "}";
    }

}
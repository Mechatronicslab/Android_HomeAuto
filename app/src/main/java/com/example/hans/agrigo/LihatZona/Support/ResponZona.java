package com.example.hans.agrigo.LihatZona.Support;

import com.example.hans.agrigo.Config.Item_Device;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponZona {
    @SerializedName("device")
    private List<Item_Zona> menu;
    @SerializedName("status")
    private boolean status;
    public void setMenu(List<Item_Zona> menu) {
        this.menu = menu;
    }

    public List<Item_Zona> getZona()
    {
        return menu;
    }

    public void setStatus(boolean status){

        this.status = status;
    }

    public boolean isStatus(){
        return status;
    }

    @Override
    public String toString(){
        return
                "ResponZona{" +
                        "menu = '" + menu+ '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}

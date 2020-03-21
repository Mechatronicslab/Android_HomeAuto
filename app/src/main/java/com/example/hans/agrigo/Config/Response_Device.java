package com.example.hans.agrigo.Config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response_Device {

    @SerializedName("device")
    private List<Item_Device> menu;
    @SerializedName("status")
    private boolean status;
    public void setMenu(List<Item_Device> menu) {
        this.menu = menu;
    }

    public List<Item_Device> getMenu()
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
                "Response_Device{" +
                        "menu = '" + menu+ '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }


}

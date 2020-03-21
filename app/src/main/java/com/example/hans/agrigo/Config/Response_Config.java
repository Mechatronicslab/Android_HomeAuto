package com.example.hans.agrigo.Config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response_Config {

    @SerializedName("deviceActivation")
    private List<Item_Config> menu;
    @SerializedName("status")
    private boolean status;
    public void setMenu(List<Item_Config> menu) {
        this.menu = menu;
    }

    public List<Item_Config> getMenu()
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
                "Response_Config{" +
                        "menu = '" + menu+ '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }




}

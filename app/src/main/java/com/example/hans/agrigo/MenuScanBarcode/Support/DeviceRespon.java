package com.example.hans.agrigo.MenuScanBarcode.Support;

import com.google.gson.annotations.SerializedName;

public class DeviceRespon {


    @SerializedName("err")
    private boolean err;

    @SerializedName("msg")
    private String msg;

    public DeviceRespon(boolean err, String msg){
        this.err = err;
        this.msg = msg;
    }

    public boolean isErr(){
        return err;
    }

    public String getMsg(){
        return msg;
    }
}

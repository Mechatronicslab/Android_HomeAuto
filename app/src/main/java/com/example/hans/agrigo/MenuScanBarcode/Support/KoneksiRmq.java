package com.example.hans.agrigo.MenuScanBarcode.Support;

import android.content.SharedPreferences;

import com.example.hans.agrigo.MenuScanBarcode.AddDevice;

public class KoneksiRmq {



    String prov,guid,tlp;
    String routingKey = "2c:f4:32:3c:61:dc";
    String queue_name_publish ="mqtt-subscription-"+routingKey+"qos0";
    public String queueReport(){
        String queue =queue_name_publish;
        return queue;
    }

    /**
     * get Value Username RMQ
     * @return
     */
    public String userQueue(){
        String userQueue="iot_pertanian";
        return userQueue;
    }


    /**
     * get Value password RMQ
     * @return
     */
    public String passQueue(){
        String passQueue="iotpertanian";
        return passQueue;
    }

    /**
     * get Value Hostname RMQ
     * @return
     */
    public String host(){
        String hostname ="167.205.7.226";
//                "rmq2.pptik.id";
        return hostname;
    }

    /**
     * get Value Virtual Host RMQ
     * @return
     */

    public String vhostRep(){
        String vhost="/iotpertanian";
        return vhost;
    }


    /**
     * get Value Exchange RMQ
     * @return
     */
    public String exchange(){
        String exchange ="";
        return exchange;
    }
    }
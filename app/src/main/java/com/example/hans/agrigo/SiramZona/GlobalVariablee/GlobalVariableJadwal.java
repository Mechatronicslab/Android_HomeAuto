package com.example.hans.agrigo.SiramZona.GlobalVariablee;

import android.content.SharedPreferences;

public class GlobalVariableJadwal {
    SharedPreferences pref;
    String prov,guid,tlp;
    String routingKey = "dc:4f:22:7e:3f:fd";
    String queue_name_publish ="jadwal";
    /**
     * get Value Queue RMQ
     * @return
     */
    public String getRoutingKey(){
        String rk = routingKey;
        return rk;
    }

    public String queueReport(){
        String queue =queue_name_publish;
        return queue;
    }

    /**
     * get Value Username RMQ
     * @return
     */
    public String userQueue(){
        String userQueue="homeauto";
        return userQueue;
    }

    /**
     * get Value password RMQ
     * @return
     */
    public String passQueue(){
        String passQueue="homeauto12345!";
        return passQueue;
    }

    /**
     * get Value Hostname RMQ
     * @return
     */
    public String host(){
        String hostname ="167.205.7.226";
//                "167.205.7.226";
//                "rmq2.pptik.id";
        return hostname;
    }

    /**
     * get Value Virtual Host RMQ
     * @return
     */

    public String vhostRep(){
        String vhost="/homeauto";
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

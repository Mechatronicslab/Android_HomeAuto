package com.example.hans.agrigo.SiramZona.GlobalVariablee;

import android.content.SharedPreferences;

public class GlobalVariable {
    SharedPreferences pref;
    String prov,guid,tlp;
    String routingKey = "dc:4f:22:7e:3f:fd";
    String queue_name_publish ="mqtt-subscription-"+routingKey+"qos0";

    /**
     * get Value Queue RMQ
     * @return
     */
    public String queueReport(){
        String queue =queue_name_publish;
        return queue;
    }

    /**
     * get Value Username RMQ
     * @return
     */
    public String userQueue(){
        String userQueue="shadoofpertanian";
        return userQueue;
    }

    /**
     * get Value password RMQ
     * @return
     */
    public String passQueue(){
        String passQueue="TaniBertani19";
        return passQueue;
    }

    /**
     * get Value Hostname RMQ
     * @return
     */
    public String host(){
        String hostname ="shadoofpertanian.pptik.id";
//                "167.205.7.226";
//                "rmq2.pptik.id";
        return hostname;
    }

    /**
     * get Value Virtual Host RMQ
     * @return
     */

    public String vhostRep(){
        String vhost="/shadoofpertanian";
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


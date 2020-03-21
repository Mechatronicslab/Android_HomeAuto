package com.example.hans.agrigo.SiramZona.Helper;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.hans.agrigo.SiramZona.GlobalVariablee.GlobalVariable;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeoutException;


public class RabbitMq {
    GlobalVariable gb = new GlobalVariable();
    ConnectionFactory factory = new ConnectionFactory();

    /**
     * Function Connect To RMQ
     */
    public void setupConnectionFactory() {
        try {
            factory.setAutomaticRecoveryEnabled(false);
            factory.setUri("amqp://"+gb.userQueue()+":"+gb.passQueue()+"@"+gb.host());
            factory.setVirtualHost(gb.vhostRep());
        } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    private BlockingDeque<String> queue = new LinkedBlockingDeque<String>();
    public void publishMessage(String message) {
        //Adds a message to internal blocking queue
        try {
            Log.d("","[q] " + message);
            queue.putLast(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function Publish data ke RMQ
     * @param message
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws URISyntaxException
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public  void  publish(String message) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = factory.newConnection();
        Log.d("ConnectionRMQ", "publish: "+connection.isOpen());
        Channel channel = connection.createChannel();
        Log.d("ChannelRMQ", "publish: "+channel.isOpen());
        String messageOn = message ;
        channel.basicPublish(gb.exchange(), gb.queueReport(),null,messageOn.getBytes());

    }

    public  void  publishJadwal(String message) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = factory.newConnection();
        Log.d("ConnectionRMQ", "jadwal: "+connection.isOpen());
        Channel channel = connection.createChannel();
        Log.d("ChannelRMQ", "jadwal: "+channel.isOpen());
        String messageOn = message ;
        channel.basicPublish(gb.exchange(), gb.queueReport(),null,messageOn.getBytes());

    }

    public void SendSpeed() throws InterruptedException {
        Thread.sleep(500); //0.5 sec
    }

    /**
     * Fungsi untuk subscribe data RMQ
     * @param handler
     * @param subscribeThread
     */
    public void subscribeNotification(final Handler handler, Thread subscribeThread)
    {
        subscribeThread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                while(true) {
                    try {
                        Connection connection = factory.newConnection();
                        Channel channel = connection.createChannel();
                        channel.basicQos(0);
                        AMQP.Queue.DeclareOk q = channel.queueDeclare();
                        channel.queueBind(q.getQueue(), "amq.fanout", "panwaslu_notification");
                        QueueingConsumer consumer = new QueueingConsumer(channel);
                        channel.basicConsume(q.getQueue(), true, consumer);
                        QueueingConsumer.Delivery delivery = consumer.nextDelivery();

                        if (delivery != null){

                            try{

                                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                                Log.d("ConsumeDataRMQ", "MessageConsumed" + message);

                                Message msg = handler.obtainMessage();
                                Bundle bundle = new Bundle();

                                bundle.putString("msg", message);
                                msg.setData(bundle);
                                handler.sendMessage(msg);

                                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                            }catch (Exception e){
                                channel.basicReject(delivery.getEnvelope().getDeliveryTag(),true);
                            }
                        }
                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e1) {
                        Log.d("", "Connection broken: " + e1.getClass().getName());
                        try {
                            Thread.sleep(4000); //sleep and then try again
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
        });
        subscribeThread.start();
    }
}

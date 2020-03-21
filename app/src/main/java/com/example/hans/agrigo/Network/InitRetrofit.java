package com.example.hans.agrigo.Network;

/*
Create By Asep Trisna Setiawan
Bandung 2020
 */
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class InitRetrofit {

    public static final String BASE_URL ="http://192.168.1.103:3000/";

    private static InitRetrofit mInstance;
    private Retrofit retrofit;

    private InitRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized InitRetrofit getInstance(){
        if (mInstance == null ){
            mInstance = new InitRetrofit();
        }
        return mInstance;
    }
    public NetworkService getApi(){
        return retrofit.create(NetworkService.class);
    }
}

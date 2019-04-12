package com.sinodom.elevator.single;

import com.sinodom.elevator.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {

    private static Server single;
    private ApiService service;

    //异步获取单实例
    public static synchronized Server getInstance() {
        if (single == null) {
            single = new Server();
        }
        return single;
    }

    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(20, TimeUnit.SECONDS).
            readTimeout(20, TimeUnit.SECONDS).
            writeTimeout(20, TimeUnit.SECONDS).build();

    public ApiService getService() {
        return service;
    }

    public void setService(ApiService service) {
        this.service = service;
    }

    public String getFileUrl(String url) {
        return BuildConfig.SERVER + url.substring(2);
    }
}
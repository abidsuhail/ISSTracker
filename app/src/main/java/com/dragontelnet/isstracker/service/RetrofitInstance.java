package com.dragontelnet.isstracker.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofitInstance=null;
   // private static GetIssInfo getIssInfo;
    private static final String BASE_URL="http://api.open-notify.org";

    public static GetIssInfo getRetrofitInstance()
    {
        if (retrofitInstance==null)
        {
            retrofitInstance=new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitInstance.create(GetIssInfo.class);

    }
}

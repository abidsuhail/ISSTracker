package com.dragontelnet.isstracker.di.retrofit;

import com.dragontelnet.isstracker.service.GetIssInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    private static final String BASE_URL="http://api.open-notify.org";

    @Singleton
    @Provides
    GetIssInfo providesRetrofitInstance()
    {
        return new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GetIssInfo.class);
    }
}

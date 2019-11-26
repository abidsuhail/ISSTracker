package com.dragontelnet.isstracker.di;

import android.app.Application;

import com.dragontelnet.isstracker.di.repository.DaggerReposComponent;
import com.dragontelnet.isstracker.di.repository.ReposComponent;
import com.dragontelnet.isstracker.di.retrofit.DaggerRetrofitComponent;
import com.dragontelnet.isstracker.di.retrofit.RetrofitComponent;

public class MyDaggerInjection extends Application {

    public static RetrofitComponent retrofitComponent;
    public static ReposComponent reposComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        retrofitComponent = DaggerRetrofitComponent.create();
        reposComponent= DaggerReposComponent.create();
    }

    public static RetrofitComponent getRetrofitInstance()
    {
        return retrofitComponent;
    }
    public static ReposComponent getReposComponent()
    {
        return reposComponent;
    }
}

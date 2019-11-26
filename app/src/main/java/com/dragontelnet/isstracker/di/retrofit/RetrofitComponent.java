package com.dragontelnet.isstracker.di.retrofit;

import com.dragontelnet.isstracker.repository.MapActivityRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {

    void inject(MapActivityRepository mapActivityRepository);
}

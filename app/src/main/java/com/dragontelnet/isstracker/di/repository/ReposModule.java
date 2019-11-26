package com.dragontelnet.isstracker.di.repository;

import com.dragontelnet.isstracker.repository.MapActivityRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ReposModule {

    @Singleton
    @Provides
    MapActivityRepository providesMapActivityRepository()
    {
        return new MapActivityRepository();
    }
}

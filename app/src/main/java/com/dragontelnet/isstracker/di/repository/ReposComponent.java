package com.dragontelnet.isstracker.di.repository;

import com.dragontelnet.isstracker.repository.MapActivityRepository;
import com.dragontelnet.isstracker.ui.MapsActivity;
import com.dragontelnet.isstracker.viewmodel.MapsActivityViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ReposModule.class)
public interface ReposComponent {

    void inject(MapsActivityViewModel mapsActivityViewModel);
}

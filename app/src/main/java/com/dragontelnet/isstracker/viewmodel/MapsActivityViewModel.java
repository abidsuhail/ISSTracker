package com.dragontelnet.isstracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dragontelnet.isstracker.di.MyDaggerInjection;
import com.dragontelnet.isstracker.models.AstroPeople;
import com.dragontelnet.isstracker.models.IssPosition;
import com.dragontelnet.isstracker.repository.MapActivityRepository;

import java.util.List;

import javax.inject.Inject;

public class MapsActivityViewModel extends ViewModel {

    @Inject
    public MapActivityRepository repository;

    public MapsActivityViewModel()
    {
        MyDaggerInjection.getReposComponent().inject(this);
    }

    public LiveData<List<AstroPeople>> getAstrosName()
    {
        return repository.getAstrosName();
    }
    public LiveData<IssPosition> getLocationUpdates()
    {
       return repository.getLocationUpdates();
    }
}

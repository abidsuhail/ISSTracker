package com.dragontelnet.isstracker.repository;

import androidx.lifecycle.MutableLiveData;

import com.dragontelnet.isstracker.di.MyDaggerInjection;
import com.dragontelnet.isstracker.models.AstroPeople;
import com.dragontelnet.isstracker.models.AstrosInSpace;
import com.dragontelnet.isstracker.models.IssInfo;
import com.dragontelnet.isstracker.models.IssPosition;
import com.dragontelnet.isstracker.service.GetIssInfo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class MapActivityRepository {
    private MutableLiveData<List<AstroPeople>> astrosNameMutable = new MutableLiveData<>();
    private MutableLiveData<IssPosition> locationUpdatesMutable = new MutableLiveData<>();

    @Inject
    public GetIssInfo getIssInfo;

    public MapActivityRepository() {
        MyDaggerInjection.getRetrofitInstance().inject(this);
    }

    public MutableLiveData<List<AstroPeople>> getAstrosName() {
        Call<AstrosInSpace> call = getIssInfo.getAstrosInSpace();
        call.enqueue(new Callback<AstrosInSpace>() {
            @Override
            public void onResponse(Call<AstrosInSpace> call, Response<AstrosInSpace> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AstroPeople> astroPeopleList = response.body().getAstroPeopleList();
                    astrosNameMutable.setValue(astroPeopleList);
                }
            }

            @Override
            public void onFailure(Call<AstrosInSpace> call, Throwable t) {
            }
        });
        return astrosNameMutable;
    }

    public MutableLiveData<IssPosition> getLocationUpdates() {
        Call<IssInfo> call = getIssInfo.getIssInfo();
        call.enqueue(new Callback<IssInfo>() {
            @Override
            public void onResponse(Call<IssInfo> call, Response<IssInfo> response) {

                if (response.isSuccessful() && response.body() != null) {
                    IssPosition issPosition = response.body().getIssPosition();
                    locationUpdatesMutable.setValue(issPosition);
                }

            }

            @Override
            public void onFailure(Call<IssInfo> call, Throwable t) {

            }
        });
        return locationUpdatesMutable;
    }

}

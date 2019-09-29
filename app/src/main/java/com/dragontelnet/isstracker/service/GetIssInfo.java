package com.dragontelnet.isstracker.service;

import com.dragontelnet.isstracker.models.AstrosInSpace;
import com.dragontelnet.isstracker.models.IssInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetIssInfo {

    @GET("iss-now")
    Call<IssInfo> getIssInfo();

    @GET("astros")
    Call<AstrosInSpace> getAstrosInSpace();
}

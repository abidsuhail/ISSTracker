package com.dragontelnet.isstracker.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AstrosInSpace {
    int number;
    String message;

    @SerializedName("people")
    List<AstroPeople> astroPeopleList;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AstroPeople> getAstroPeopleList() {
        return astroPeopleList;
    }

    public void setAstroPeopleList(List<AstroPeople> astroPeopleList) {
        this.astroPeopleList = astroPeopleList;
    }
}

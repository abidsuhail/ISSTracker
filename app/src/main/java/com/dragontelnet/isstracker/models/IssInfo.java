package com.dragontelnet.isstracker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssInfo {

    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("iss_position")
    @Expose
    private IssPosition issPosition;

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IssPosition getIssPosition() {
        return issPosition;
    }

    public void setIssPosition(IssPosition issPosition) {
        this.issPosition = issPosition;
    }

}

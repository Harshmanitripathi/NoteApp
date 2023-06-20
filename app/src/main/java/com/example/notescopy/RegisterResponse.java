package com.example.notescopy;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("error")
    String error;
    @SerializedName("message")
    String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RegisterResponse(String error, String message) {
        this.error = error;
        this.message = message;


    }
}

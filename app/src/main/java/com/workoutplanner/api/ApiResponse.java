package com.workoutplanner.api;

public class ApiResponse<T> {
    public T data;
    public String error = null;
    public Integer status = 200;

    public String getError() {
        return error;
    }
    public Integer getStatus() {
        return status;
    }
}
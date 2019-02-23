package com.workoutplanner.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.workoutplanner.MyApplication;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ErrorInterceptor implements Interceptor {
    public static final String errorCode = "ERROR_CODE";
    public static final String errorString = "SERVER_ERROR";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);

            final int responseCode = response.code();
            if (responseCode == 404 || responseCode >= 500) {
                broadcastNetworkError(responseCode);
                return response;
            }
        }
        catch (ConnectException | SocketTimeoutException e) {
            broadcastNetworkError(502);
            throw e;
        }

        return response;
    }

    private void broadcastNetworkError(int code) {
        Context context = MyApplication.getAppContext();

        Intent it = new Intent(errorString);
        it.putExtra(errorCode, code);

        LocalBroadcastManager.getInstance(context).sendBroadcast(it);
    }
}

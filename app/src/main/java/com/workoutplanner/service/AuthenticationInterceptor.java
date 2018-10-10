package com.workoutplanner.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.workoutplanner.MyApplication;
import com.workoutplanner.R;
import com.workoutplanner.api.SimpleValueContainer;
import com.workoutplanner.api.interfaces.UsersAPI;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.workoutplanner.api.ApiConstants.API_BASE_URL;

public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken);

        Request request = builder.build();
        Response origResponse = chain.proceed(request);

        if (origResponse.code() == 403) {
            Context appContext = MyApplication.getAppContext();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
            String refreshToken = sharedPreferences.getString(appContext.getString(R.string.refresh_token), "");

            Retrofit.Builder retrofitBuilder =
                    new Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = retrofitBuilder.build();
            UsersAPI usersAPI = retrofit.create(UsersAPI.class);
            Call<Void> refreshCall = usersAPI.refreshAccessToken(new SimpleValueContainer<>(refreshToken));

            //make it as retrofit synchronous call
            retrofit2.Response<Void> refreshResponse = refreshCall.execute();
            if (refreshResponse != null && refreshResponse.code() == 200) {
                //read new JWT value from response body or interceptor depending upon your JWT availability logic
                String token = refreshResponse.headers().get("Authorization");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(appContext.getString(R.string.jwt_token), token);
                editor.commit();
                // make a new request with the new id token
                Request newAuthenticationRequest = original.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();

                // try again
                Response newResponse = chain.proceed(newAuthenticationRequest);

                // hopefully we now have a status of 200
                return newResponse;
            } else {
                return null;
            }

        } else {
            return origResponse;
        }
    }
}

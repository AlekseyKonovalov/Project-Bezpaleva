package com.example.bzp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HandlerUser  {

    public static void SendUser(final User newUser){

        String webServiceUrl="http://88.205.135.253:8080";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPIsend service = retrofit.create(UserAPIsend.class);
        Call<User> call= service.sendUser(newUser.getFirstName(), newUser.getLastName(), newUser.getVkId());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    newUser.setId(response.body().getId());
                    Log.i("bzp1", "new user " + Integer.toString(newUser.getId()));

                } else {
                    //request not successful (like 400,401,403 etc)
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("bzp1", t.getMessage());
            }
        });
    }

    public static void compareUser(final User newUser){
        String webServiceUrl="http://88.205.135.253:8080";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPIget service = retrofit.create(UserAPIget.class);

        Call<User> call= service.getUser(newUser.getVkId());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    newUser.setId(response.body().getId());
                    Log.i("bzp1", "User est " + Integer.toString(newUser.getId()));
                } else {
                    //request not successful (like 400,401,403 etc)
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("bzp1", "Usera net, sozdaem ili "+ t.getMessage());
                SendUser(newUser);
            }
        });
    }
}

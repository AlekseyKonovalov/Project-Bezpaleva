package com.example.bzp1;

import android.util.Log;

import java.util.*;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;



public class DownloaderMarks {


    public void Download(){
        String webServiceUrl="http://88.205.135.253:8080";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarksAPIget service = retrofit.create(MarksAPIget.class);
        Call<List<Mark>> call = service.getMarks();

        call.enqueue(new Callback<List<Mark>>() {

            @Override
            public void onResponse(Call<List<Mark>> call, Response<List<Mark>> response) {

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    List<Mark> result = response.body();

                    Log.i("bzp1", "result ok");


                } else {
                    System.out.println("err");
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Log.i("bzp1", "result not ok");
                }
            }

            @Override
            public void onFailure(Call<List<Mark>> call, Throwable t) {

                Log.i("bzp1", t.getMessage());

            }
        });



    }

}

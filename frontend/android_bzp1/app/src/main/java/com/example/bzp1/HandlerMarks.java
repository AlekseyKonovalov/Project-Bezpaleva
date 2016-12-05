package com.example.bzp1;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;


public class HandlerMarks{

    public void sendMark(ListMarks listMarks){
        String webServiceUrl="http://88.205.135.253:8080";

        Mark tempMark=new Mark(55.177635, 61.331487, "radar", "cops on the road brothers kashirins");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarkAPIsend service = retrofit.create(MarkAPIsend.class);


        Call<Mark> call = service.createMark(tempMark.getX(), tempMark.getY(),  tempMark.getType(), tempMark.getDescription(), null);

        call.enqueue(new Callback<Mark>() {
            @Override
            public void onResponse(Call<Mark> call, Response<Mark> response) {

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)

                    Mark result = response.body();
                    Log.i("bzp1", "result ok");
                    Log.i("bzp1", result.getDescription() + result.getType());


                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors

                    Log.i("bzp1", "result not ok");
                }
            }

            @Override
            public void onFailure(Call<Mark> call, Throwable t) {
                Log.i("bzp1", t.getMessage());
            }
        });

    }
}

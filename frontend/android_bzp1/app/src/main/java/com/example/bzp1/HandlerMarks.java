package com.example.bzp1;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.overlay.Overlay;


public class HandlerMarks  {

    public void sendMark(Mark tempMark, Context context){
        String webServiceUrl="http://88.205.135.253:8080";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarkAPIsend service = retrofit.create(MarkAPIsend.class);

        RequestBody temp1=RequestBody.create(MediaType.parse("text/plain"), tempMark.getType());
        RequestBody temp2=RequestBody.create(MediaType.parse("text/plain"),  tempMark.getDescription());

        Call<Mark> call = service.createMark(tempMark.getX(), tempMark.getY(),  temp1, temp2, tempMark.getUserId(), null);

        call.enqueue(new Callback<Mark>() {
            @Override
            public void onResponse(Call<Mark> call, Response<Mark> response) {
                if (response.isSuccessful()) {

                    Log.i("bzp1", "result ok");

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

    public void changeIrrelevanceLevel(int idMark, Context context){

        String webServiceUrl="http://88.205.135.253:8080";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MarkChangeLevelPost service = retrofit.create(MarkChangeLevelPost.class);

        Call <Mark> call = service.sendMark(idMark, 1);


        call.enqueue(new Callback<Mark>() {
            @Override
            public void onResponse(Call<Mark> call, Response<Mark> response) {
                if (response.isSuccessful()) {

                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                }
            }
            @Override
            public void onFailure(Call<Mark> call, Throwable t) {
                Log.i("bzp1", t.getMessage());
            }
        });

    }
}

package com.example.bzp1;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;

import static ru.yandex.core.CoreApplication.getApplicationContext;


public class HandlerMarks extends Overlay {

    public HandlerMarks(MapController mapController) {
        super(mapController);
    }

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
                    Mark newMark=response.body();

                    Resources res = getMapController().getContext().getResources();
                    Overlay overlay = new Overlay(getMapController());

                    OverlayManager mOverlayManager;
                    mOverlayManager =getMapController().getOverlayManager();
                    final OverlayItem mrk;
                    switch (newMark.getType()){
                        case "dps":
                            mrk = new OverlayItem(new GeoPoint(newMark.getX(), newMark.getY()), res.getDrawable(R.drawable.dps));
                            break;
                        case "camera":
                            mrk = new OverlayItem(new GeoPoint(newMark.getX(), newMark.getY()),  res.getDrawable(R.drawable.camera));
                            break;
                        case "help":
                            mrk = new OverlayItem(new GeoPoint(newMark.getX(), newMark.getY()),  res.getDrawable(R.drawable.help));
                            break;
                        case "other":
                            mrk = new OverlayItem(new GeoPoint(newMark.getX(), newMark.getY()),  res.getDrawable(R.drawable.other));
                            break;
                        default:
                            mrk = new OverlayItem(new GeoPoint(newMark.getX(), newMark.getY()),  res.getDrawable(R.drawable.other));
                            break;
                    }
                    //!
                    ImageBalloonItem balloonMrk = new ImageBalloonItem(getMapController(), mrk.getGeoPoint(), newMark.getId(), newMark);

                    balloonMrk.setDescriptionOnBalloon(newMark.getDescription());
                    balloonMrk.setText(newMark.getDescription());
                    balloonMrk.setOnViewClickListener();

                    // Add the balloon model to the object
                    mrk.setBalloonItem(balloonMrk);
                    //Add the object to the layer
                    overlay.addOverlayItem(mrk);
                    // Add the layer to the map
                    mOverlayManager.addOverlay(overlay);

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

    public void changeMark(Mark mark, Context context){
        String webServiceUrl="http://88.205.135.253:8080";
        Log.i("bzp1","doshel");
        Log.i("bzp1", mark.getType());
        Log.i("bzp1", mark.getDescription());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MarkChange service = retrofit.create(MarkChange.class);

        RequestBody type=RequestBody.create(MediaType.parse("text/plain"), mark.getType());
        RequestBody desc=RequestBody.create(MediaType.parse("text/plain"),  mark.getDescription());

        Call <Mark> call = service.changeMark(mark.getId(), type , desc);

        call.enqueue(new Callback<Mark>() {
            @Override
            public void onResponse(Call<Mark> call, Response<Mark> response) {
                if (response.isSuccessful()) {

                    Log.i("bzp1","1");
                    Toast.makeText(
                            getApplicationContext(),
                            "Параметры метки изменены. Обновите карту",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Log.i("bzp1","2");
                }
            }
            @Override
            public void onFailure(Call<Mark> call, Throwable t) {
                Log.i("bzp1", t.getMessage());
            }
        });
    }
}

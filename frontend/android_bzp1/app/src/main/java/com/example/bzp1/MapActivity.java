package com.example.bzp1;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yandex.yandexmapkit.*;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.location.MyLocationItem;
import ru.yandex.yandexmapkit.utils.*;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;


public class MapActivity extends Activity  {
    MapController mMapController;
    OverlayManager mOverlayManager;
    private ArrayList<Mark> listMarks = new ArrayList<Mark>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_layout);

        final MapView mapView = (MapView) findViewById(R.id.map);
        mapView.showBuiltInScreenButtons(true);
//        mapView.showBuiltInScreenButtons(true);
        mMapController = mapView.getMapController();
        // determining the user's location
        mMapController.getOverlayManager().getMyLocation().setEnabled(true);
        mOverlayManager = mMapController.getOverlayManager();
        // Изменяем зум
        mMapController.setZoomCurrent(14);

        //add new mark
        mOverlayManager.addOverlay(new  DialogNewMark(mMapController, "Добавление новой метки"));

        showObject();
    }


    public void showObject(){

        String webServiceUrl="http://88.205.135.253:8080";
        Log.i("bzp1", "result  ok1");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarksAPIget service = retrofit.create(MarksAPIget.class);
        Call<List<Mark>> call = service.getMarks();

        Log.i("bzp1", "result  ok2");

        call.enqueue(new Callback<List<Mark>>() {
            @Override
            public void onResponse(Call<List<Mark>> call, Response<List<Mark>> response) {

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    List<Mark> result = response.body();

                    Log.i("bzp1", "result  ok3");

                    // Load required resources
                    Resources res = getResources();
                    // Create a layer of objects for the map
                    Overlay overlay = new Overlay(mMapController);

                    for( Mark t  : result) {

                        final OverlayItem mrk;

                        switch (t.getType()){
                            case "dps":
                                mrk = new OverlayItem(new GeoPoint(t.getX(), t.getY()), res.getDrawable(R.drawable.dps));
                                break;
                            case "camera":
                                mrk = new OverlayItem(new GeoPoint(t.getX(), t.getY()), res.getDrawable(R.drawable.camera));
                                break;
                            case "help":
                                mrk = new OverlayItem(new GeoPoint(t.getX(), t.getY()), res.getDrawable(R.drawable.help));
                                break;
                            case "other":
                                mrk = new OverlayItem(new GeoPoint(t.getX(), t.getY()), res.getDrawable(R.drawable.other));
                                break;
                            default:
                                mrk = new OverlayItem(new GeoPoint(t.getX(), t.getY()), res.getDrawable(R.drawable.other));
                                break;
                        }

                        //Create a balloon model for the object

                        ImageBalloonItem balloonMrk = new ImageBalloonItem(mMapController.getContext(), mrk.getGeoPoint());

                        balloonMrk.setDescriptionOnBalloon(t.getDescription());
                        balloonMrk.setText(t.getDescription());
                        balloonMrk.setOnViewClickListener();

                       // Add the balloon model to the object
                        mrk.setBalloonItem(balloonMrk);
                        //Add the object to the layer
                        overlay.addOverlayItem(mrk);

                        // Add the layer to the map
                        listMarks.add(t);
                        mOverlayManager.addOverlay(overlay);
                    }

                } else {
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




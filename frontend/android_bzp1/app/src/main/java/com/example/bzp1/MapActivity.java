package com.example.bzp1;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import ru.yandex.yandexmapkit.*;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.utils.*;

public class MapActivity extends AppCompatActivity {
    MapController mMapController;
    OverlayManager mOverlayManager;
    ListMarks listMarks;


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

        //
        showObject();
    }

    public void showObject(){
        // Load required resources
        Resources res = getResources();

        // Create a layer of objects for the map
        Overlay overlay = new Overlay(mMapController);

        //DownloaderMarks dm=new DownloaderMarks();
        //dm.Download();

        HandlerMarks hm=new HandlerMarks();
        hm.sendMark();

        // Create an object for the layer
        final OverlayItem kremlin = new OverlayItem(new GeoPoint(55.177635, 61.331487), res.getDrawable(R.drawable.mark));
        // Create a balloon model for the object
        BalloonItem balloonKremlin = new BalloonItem(this,kremlin.getGeoPoint());
        balloonKremlin.setText(getString(R.string.kremlin));
//        // Add the balloon model to the object
        kremlin.setBalloonItem(balloonKremlin);
        // Add the object to the layer
        overlay.addOverlayItem(kremlin);

        // Create an object for the layer
        final OverlayItem yandex = new OverlayItem(new GeoPoint(55.179453, 61.333633), res.getDrawable(R.drawable.mark));
        // Create the balloon model for the object
        BalloonItem balloonYandex = new BalloonItem(this,yandex.getGeoPoint());
        balloonYandex.setText(getString(R.string.yandex));
        // Add the balloon model to the object
        yandex.setBalloonItem(balloonYandex);
        // Add the object to the layer
        overlay.addOverlayItem(yandex);

        // Add the layer to the map
        mOverlayManager.addOverlay(overlay);

    }
}




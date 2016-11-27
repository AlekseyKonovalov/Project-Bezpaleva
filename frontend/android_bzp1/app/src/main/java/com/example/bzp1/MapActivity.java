package com.example.bzp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.yandex.yandexmapkit.*;


public class MapActivity extends AppCompatActivity {
    MapController mMapController;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.map_layout);

        final MapView mapView = (MapView) findViewById(R.id.map);
//        mapView.showBuiltInScreenButtons(true);
        mMapController = mapView.getMapController();
        // determining the user's location
        mMapController.getOverlayManager().getMyLocation().setEnabled(true);

        // Изменяем зум
        mMapController.setZoomCurrent(14);
    }


}




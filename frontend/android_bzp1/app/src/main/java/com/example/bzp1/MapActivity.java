package com.example.bzp1;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import ru.yandex.yandexmapkit.*;
import ru.yandex.yandexmapkit.map.MapEvent;
import ru.yandex.yandexmapkit.map.OnMapListener;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.drag.DragAndDropItem;
import ru.yandex.yandexmapkit.overlay.location.MyLocationItem;
import ru.yandex.yandexmapkit.utils.*;

import static ru.yandex.core.CoreApplication.getActivity;
//public class MapActivity extends Activity implements OnMapListener {

public class MapActivity extends Activity  {
    MapController mMapController;
    OverlayManager mOverlayManager;
    List<Mark> listMarks;
    MyLocationItem myLocationItem;

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

       //mMapController.addMapListener(this);

        //mapView.getMapController().getOverlayManager().addOverlay(new DialogNewMark(mapView.getMapController()));

        mOverlayManager.addOverlay(new  DialogNewMark(mMapController));
        showObject();
    }

    public void showObject(){
        // Load required resources
        Resources res = getResources();

        // Create a layer of objects for the map
        Overlay overlay = new Overlay(mMapController);

        //DownloaderMarks dm=new DownloaderMarks();
        //dm.Download(listMarks);

     //   HandlerMarks hm=new HandlerMarks();
      //  hm.sendMark(listMarks);

        final OverlayItem kremlin = new OverlayItem(new GeoPoint(55.177635, 61.331487), res.getDrawable(R.drawable.mark));
        // Create a balloon model for the object
        BalloonItem balloonKremlin = new BalloonItem(this,kremlin.getGeoPoint());
        balloonKremlin.setText("ff");
        // Add the balloon model to the object
        kremlin.setBalloonItem(balloonKremlin);
        // Add the object to the layer
        overlay.addOverlayItem(kremlin);


        // Add the layer to the map
        mOverlayManager.addOverlay(overlay);
    }


}




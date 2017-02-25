package com.example.bzp1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
import ru.yandex.yandexmapkit.overlay.location.OnMyLocationListener;
import ru.yandex.yandexmapkit.utils.*;

import static ru.yandex.core.CoreApplication.getApplicationContext;


public class MapNotAuthActivity extends AppCompatActivity implements OnMyLocationListener {

    MapController mMapController;
    OverlayManager mOverlayManager;
    MyLocationItem myLocationItem;
    private int radius;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("radius", radius);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        radius=getIntent().getExtras().getInt("MapRadius");
        Log.i("bzp1", "Maprad  start" + Integer.toString( radius));

        setContentView(R.layout.map_layout);
        final MapView mapView = (MapView) findViewById(R.id.map);
        mapView.showBuiltInScreenButtons(true);
//        mapView.showBuiltInScreenButtons(true);
        mMapController = mapView.getMapController();
        // determining the user's location
        //mMapController.getOverlayManager().getMyLocation().setEnabled(true);
        mMapController.setPositionAnimationTo(new GeoPoint(55.160026, 61.402590));

        mOverlayManager = mMapController.getOverlayManager();
        mMapController.getOverlayManager().getMyLocation().addMyLocationListener(this);
        // Изменяем зум
        mMapController.setZoomCurrent(14);

        Toast.makeText(
                getApplicationContext(),
                "Авторизуйтесь, чтобы иметь больше возможностей",
                Toast.LENGTH_SHORT).show();

        //showObject();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mapmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: {
                Toast.makeText(
                        getApplicationContext(),
                        "Обновление карты",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MapNotAuthActivity.class);
                intent.putExtra("MapRadius", radius);
                startActivity(intent);
                return true;
            }
            case R.id.action_revert:{
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("radius", radius);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMyLocationChange(MyLocationItem myLocationItem) {
        this.myLocationItem = myLocationItem;
       // int flag=0;
      //  while (flag==0){
            try{
                showObject(myLocationItem.getGeoPoint().getLat(), myLocationItem.getGeoPoint().getLon());
             //   flag=1;
               // break;
            }
            catch (Exception e){
                Log.i("bzp1", "catch");
            }
       // }

    }

    public void showObject(double x, double y){

        String webServiceUrl="http://88.205.135.253:80";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarksAPIget service = retrofit.create(MarksAPIget.class);

        Call<List<Mark>> call = service.getMarks(x , y , radius);
        Log.i("bzp1", Double.toString(x ));
        Log.i("bzp1", Double.toString(y ));

        call.enqueue(new Callback<List<Mark>>() {
            @Override
            public void onResponse(Call<List<Mark>> call, Response<List<Mark>> response) {

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    List<Mark> result = response.body();
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
                        BalloonItem balloonMrk = new BalloonItem(mMapController.getContext(), mrk.getGeoPoint());
                        balloonMrk.setText(t.getDescription());

                        // Add the balloon model to the object
                        mrk.setBalloonItem(balloonMrk);
                        //Add the object to the layer
                        overlay.addOverlayItem(mrk);

                        // Add the layer to the map
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


package com.example.bzp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;


public abstract  class ImageBalloonOverall extends BalloonItem implements OnBalloonListener {

    public ImageBalloonOverall(Context context, GeoPoint geoPoint) {
        super(context, geoPoint);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public void onBalloonViewClick(BalloonItem balloonItem, View view) {

    }

    @Override
    public void onBalloonShow(BalloonItem balloonItem) {

    }

    @Override
    public void onBalloonHide(BalloonItem balloonItem) {

    }

    @Override
    public void onBalloonAnimationStart(BalloonItem balloonItem) {

    }

    @Override
    public void onBalloonAnimationEnd(BalloonItem balloonItem) {

    }

    abstract  void setDescriptionOnBalloon(String description);

    abstract  void setOnViewClickListener();
}

package com.example.bzp1;



import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;

public class ImageBalloonItem extends ImageBalloonOverall{

    Context mContext;
    MapController mMapController;
    protected TextView textView;
    LayoutInflater inflater;
    int idMark;
    Mark mark;
    Boolean changeIrrel;


    public ImageBalloonItem(MapController mMapController, GeoPoint geoPoint, int idMark, Mark mark) {
        super(mMapController.getContext(), geoPoint);
        mContext = mMapController.getContext();
        this.mMapController=mMapController;
        this.idMark=idMark;
        this.mark=mark;
        changeIrrel=false;
    }

    @Override
    public void inflateView(Context context){
        inflater = LayoutInflater.from( context );
        model = (ViewGroup)inflater.inflate(R.layout.balloon_image_id, null);
    }

    public void setDescriptionOnBalloon(String description){
        TextView textView = (TextView) model.findViewById ( R.id.balloon_text_view1);
        textView.setText(description);
    }


    public void setOnViewClickListener(){
        setOnBalloonViewClickListener(R.id.balloon_images_view1, this);
        setOnBalloonViewClickListener(R.id.balloon_images_view2, this);
    }

    @Override
    public void onBalloonViewClick(BalloonItem item, View view) {
        OverlayManager mOverlayManager;
        mOverlayManager = mMapController.getOverlayManager();

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        switch (view.getId()) {
            case R.id.balloon_images_view1:
                mOverlayManager.addOverlay(new DialogChangeMark(mMapController, 1, mark, changeIrrel));
                changeIrrel=true;
                break;
            case R.id.balloon_images_view2:
                //change mark

                mOverlayManager.addOverlay(new DialogChangeMark(mMapController, 2, mark,changeIrrel));

                break;
        }

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

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
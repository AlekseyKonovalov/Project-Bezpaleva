package com.example.bzp1;



import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;


public class ImageBalloonItem extends BalloonItem implements OnBalloonListener{


    Context mContext;
    protected TextView textView;
    LayoutInflater inflater;

    public ImageBalloonItem(Context context, GeoPoint geoPoint) {
        super(context, geoPoint);
        mContext = context;
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

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        switch (view.getId()) {
            case R.id.balloon_images_view1:
                dialog.setTitle("Click delete");
                break;
            case R.id.balloon_images_view2:
                dialog.setTitle("Click change");
                break;
        }
        dialog.show();
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
package com.example.bzp1;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;
import ru.yandex.yandexmapkit.utils.ScreenPoint;
import android.content.res.Resources;
import static ru.yandex.core.CoreApplication.getApplicationContext;

public class DialogNewMark extends Overlay {

    private Mark newMark=new Mark();

    public DialogNewMark(MapController mapController, int userID) {
        super(mapController);
        newMark.setUserId(userID);
    }

    @Override
    public boolean onLongPress(float x, float y) {
        final String[] mChooseTypes = {"Пост ДПС", "Камера", "Help", "Другое"};
        final String[] mChooseTypesMark = {"dps", "camera", "help", "other"};

        getMapController().getGeoPoint(new ScreenPoint(x, y));

        //Получаем вид с файла dialognewmark.xml, который применим для диалогового окна:
        LayoutInflater li = LayoutInflater.from(getMapController().getContext());
        View dialognewmarkView = li.inflate(R.layout.dialognewmark, null);
        final EditText userInputDesc = (EditText)  dialognewmarkView.findViewById(R.id.editDesc);

        newMark.setX(getMapController().getGeoPoint(new ScreenPoint(x, y)).getLat());
        newMark.setY(getMapController().getGeoPoint(new ScreenPoint(x, y)).getLon());
        newMark.setType(mChooseTypesMark [0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getMapController().getContext());
        builder.setTitle("Добавление новой метки")
                .setCancelable(false)
                // добавляем переключатели
                .setSingleChoiceItems(mChooseTypes, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Вы выбрали тип метки: "
                                                + mChooseTypes[item],
                                        Toast.LENGTH_SHORT).show();
                                newMark.setType(mChooseTypesMark[item]);
                            }
                        })

                .setView( dialognewmarkView)

                .setPositiveButton("Готово",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                newMark.setDescription(userInputDesc.getText().toString());

                                //передаем данные
                                HandlerMarks hm=new HandlerMarks();
                                hm.sendMark(newMark, getMapController().getContext());

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Ваша метка добавлена на карту",
                                        Toast.LENGTH_SHORT).show();

                                //сразу добавим метку на карту

                                //ОСТОРОЖНО ДУБЛЯЖ КОДА С MAPACTIVITY с метода ShowObjects !!!!!!

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
                                ImageBalloonItem balloonMrk = new ImageBalloonItem(getMapController(), mrk.getGeoPoint(), 0);

                                balloonMrk.setDescriptionOnBalloon(newMark.getDescription());
                                balloonMrk.setText(newMark.getDescription());
                                balloonMrk.setOnViewClickListener();

                                // Add the balloon model to the object
                                mrk.setBalloonItem(balloonMrk);
                                //Add the object to the layer
                                overlay.addOverlayItem(mrk);
                                // Add the layer to the map
                                mOverlayManager.addOverlay(overlay);
                            }
                        })

                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

        builder.create().show();
        return true;
    }
}
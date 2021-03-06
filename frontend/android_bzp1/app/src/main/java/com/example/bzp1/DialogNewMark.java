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
        final String[] mChooseTypes = {"Патруль", "Камера", "Help", "Другое"};
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
                                HandlerMarks hm=new HandlerMarks(getMapController());
                                hm.sendMark(newMark, getMapController().getContext());


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
package com.example.bzp1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.map.MapEvent;
import ru.yandex.yandexmapkit.overlay.Overlay;

import static ru.yandex.core.CoreApplication.getApplicationContext;

public class DialogNewMark extends Overlay {

    private Mark newMark;

    public DialogNewMark(MapController mapController) {
        super(mapController);
    }

    @Override
    public boolean onLongPress(float x, float y) {

        final String[] mChooseTypes = { "Пост ДПС", "Камера", "Нужна помощь", "Другое" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getMapController().getContext());
        builder.setTitle("Выберите тип метки")
                .setCancelable(false)
                // добавляем переключатели
                .setSingleChoiceItems(mChooseTypes, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Вы выбрали тип метки: "
                                                + mChooseTypes[item],
                                        Toast.LENGTH_SHORT).show();
                            }
                        })

                .setPositiveButton("Готово",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                //передаем данные

                               //Log.i("bzp1", temp);
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
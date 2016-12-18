package com.example.bzp1;


import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;

import static ru.yandex.core.CoreApplication.getApplicationContext;

public class DialogChangeMark extends Overlay{
    public DialogChangeMark(MapController mapController, int typeChange) {
        super(mapController);
        switch (typeChange) {
            case 1:
                buildDialogChangeMarkIrrelevanceLevel();
                break;
            case 2:
                buildDialogChangeMarkParameters();
                break;
        }
    }
    private void buildDialogChangeMarkParameters(){
        final String[] mChooseTypes = {"Пост ДПС", "Камера", "Help", "Другое"};
        final String[] mChooseTypesMark = {"dps", "camera", "help", "other"};
        //Получаем вид с файла prompt.xml, который применим для диалогового окна:

        LayoutInflater li = LayoutInflater.from(getMapController().getContext());
        View promptsView = li.inflate(R.layout.dialognewmark, null);
        final EditText userInputDesc = (EditText) promptsView.findViewById(R.id.editDesc);

        AlertDialog.Builder builder = new AlertDialog.Builder(getMapController().getContext());
        builder.setTitle("Изменение параметров метки")
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
                              //  newMark.setType(mChooseTypesMark[item]);
                            }
                        })

                .setView(promptsView)

                .setPositiveButton("Готово",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {

                           //     newMark.setDescription(userInputDesc.getText().toString());
                            //    newMark.setUserId(1);
                                //передаем данные
                          //      HandlerMarks hm=new HandlerMarks();
                           //     hm.sendMark(newMark, getMapController().getContext() );

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Ваша метка добавлена на карту",
                                        Toast.LENGTH_SHORT).show();
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
    }

    private void buildDialogChangeMarkIrrelevanceLevel() {
        LayoutInflater li = LayoutInflater.from(getMapController().getContext());
        View promptsView = li.inflate(R.layout.dialogchangeirrelevancelevel, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getMapController().getContext());

        builder.setTitle("Изменение актуальности метки")
                .setCancelable(false)
                .setView(promptsView)
                .setPositiveButton("Да",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Актуальность метки изменена",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })

                .setNegativeButton("Нет",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

        builder.create().show();
    }
}
package com.example.bzp1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.map.MapEvent;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.utils.ScreenPoint;

import static ru.yandex.core.CoreApplication.getApplicationContext;

public class DialogNewMark extends Overlay {

    private Mark newMark=new Mark();

    public DialogNewMark(MapController mapController) {
        super(mapController);
    }

    @Override
    public boolean onLongPress(float x, float y) {
        final String[] mChooseTypes = { "Пост ДПС", "Камера", "Help", "Другое"};

        //Получаем вид с файла prompt.xml, который применим для диалогового окна:
        LayoutInflater li = LayoutInflater.from(getMapController().getContext());
        View promptsView = li.inflate(R.layout.dialognewmark, null);
        final EditText userInputDesc = (EditText) promptsView.findViewById(R.id.editDesc);

        newMark.setX(x);
        newMark.setY(y);
        newMark.setType(mChooseTypes [0]);

        Log.i("bzp1", Double.toString(newMark.getX()) );
        Log.i("bzp1", Double.toString(newMark.getY()) );

 
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
                                newMark.setType(mChooseTypes[item]);
                            }
                        })

                .setView(promptsView)

                .setPositiveButton("Готово",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                newMark.setDescription(userInputDesc.getText().toString());

                                //передаем данные
                                HandlerMarks hm=new HandlerMarks();
                                hm.sendMark(newMark);
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